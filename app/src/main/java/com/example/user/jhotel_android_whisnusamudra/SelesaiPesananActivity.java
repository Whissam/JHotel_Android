package com.example.user.jhotel_android_whisnusamudra;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiPesananActivity extends AppCompatActivity {
    private int id_cust;
    private int id_pesan_fetch;
    /* TextView idstatic;
    TextView idpesan;
    TextView biayastatic;
    TextView biaya;
    TextView haristatic;
    TextView hari;
    TextView tanggalstatic;
    TextView tanggal;
    Button batal;
    Button selesai; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);
        id_cust = getIntent().getIntExtra("ID customer",1);
        TextView idstatic = (TextView) findViewById(R.id.idPesanStatic);
        TextView idpesan = (TextView) findViewById(R.id.idPesan);
        TextView biayastatic = (TextView) findViewById(R.id.biayaStatic);
        TextView biaya = (TextView) findViewById(R.id.biaya);
        TextView haristatic = (TextView) findViewById(R.id.jmlHariStatic);
        TextView hari = (TextView) findViewById(R.id.jmlHari);
        TextView tanggalstatic = (TextView) findViewById(R.id.tanggalStatic);
        TextView tanggal = (TextView) findViewById(R.id.tanggal);
        Button batal = (Button) findViewById(R.id.batal);
        Button selesai = (Button) findViewById(R.id.selesai);

       /* idstatic.setVisibility(View.INVISIBLE);
        idpesan.setVisibility(View.INVISIBLE);
        biaya.setVisibility(View.INVISIBLE);
        biayastatic.setVisibility(View.INVISIBLE);
        haristatic.setVisibility(View.INVISIBLE);
        hari.setVisibility(View.INVISIBLE);
        tanggalstatic.setVisibility(View.INVISIBLE);
        tanggal.setVisibility(View.INVISIBLE);
        batal.setVisibility(View.INVISIBLE);
        selesai.setVisibility(View.INVISIBLE); */

        fetchPesanan();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Cancel success.")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Failed to cancel.")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananBatalRequest batalRequest = new PesananBatalRequest(id_pesan_fetch,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(batalRequest);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Finish success.")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Failed to finish.")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananSelesaiRequest selesaiRequest = new PesananSelesaiRequest(id_pesan_fetch,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(selesaiRequest);
            }
        });
    }

    public void fetchPesanan(){
        Response.Listener<String> responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse!=null) {
                        id_pesan_fetch = jsonResponse.getInt("id");
                        double biaya_fetch = jsonResponse.getDouble("biaya");
                        int hari_fetch = jsonResponse.getInt("jumlahHari");
                        String tanggal_fetch = jsonResponse.getString("tanggalPesan");

                       /* idstatic.setVisibility(View.VISIBLE);
                        idpesan.setVisibility(View.VISIBLE);
                        biaya.setVisibility(View.VISIBLE);
                        biayastatic.setVisibility(View.VISIBLE);
                        haristatic.setVisibility(View.VISIBLE);
                        hari.setVisibility(View.VISIBLE);
                        tanggalstatic.setVisibility(View.VISIBLE);
                        tanggal.setVisibility(View.VISIBLE);
                        batal.setVisibility(View.VISIBLE);
                        selesai.setVisibility(View.VISIBLE); */

                        TextView idpesan = (TextView) findViewById(R.id.idPesan);
                        TextView biaya = (TextView) findViewById(R.id.biaya);
                        TextView hari = (TextView) findViewById(R.id.jmlHari);
                        TextView tanggal = (TextView) findViewById(R.id.tanggal);

                        idpesan.setText(Integer.toString(id_pesan_fetch));
                        biaya.setText(Double.toString(biaya_fetch));
                        hari.setText(Integer.toString(hari_fetch));
                        tanggal.setText(tanggal_fetch);

                    }
                    else if(jsonResponse==null){
                        Intent backIntent = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                        startActivity(backIntent);
                    }
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                    builder.setMessage("Failed to load.")
                            .create()
                            .show();
                }
            }
        };
        PesananFetchRequest fetchRequest = new PesananFetchRequest(id_cust,responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(fetchRequest);
    }
}
