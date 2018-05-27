package com.example.user.jhotel_android_whisnusamudra;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class BuatPesananActivity extends AppCompatActivity {
    private int currentUserId;
    private int banyakHari;
    private int idHotel;
    private double tariff;
    private String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        currentUserId = getIntent().getIntExtra("ID customer", 0);
        roomNumber = getIntent().getStringExtra("room number");
        idHotel = getIntent().getIntExtra("ID hotel", 0);

        TextView roomNumberText = (TextView) findViewById(R.id.roomNumber);
        TextView tariffText = (TextView) findViewById(R.id.tariff);
        final EditText durasiText = (EditText) findViewById(R.id.durasi_hari);
        final TextView totalText = (TextView) findViewById(R.id.total_biaya);
        final Button hitungbtn = (Button) findViewById(R.id.hitung);
        final Button pesanbtn = (Button) findViewById(R.id.pesan);

        pesanbtn.setVisibility(View.INVISIBLE);
        roomNumberText.setText(roomNumber);
        tariffText.setText(Double.toString(tariff));
        totalText.setText("0");

        hitungbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banyakHari = Integer.parseInt(durasiText.getText().toString());
                totalText.setText(Double.toString(banyakHari*tariff));
                hitungbtn.setVisibility(View.INVISIBLE);
                pesanbtn.setVisibility(View.VISIBLE);
            }
        });

        pesanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(BuatPesananActivity.this);
                                builder.setMessage("Booking Success")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder.setMessage("Booking Failed.")
                                    .create()
                                    .show();
                        }
                    }
                };
                BuatPesananRequest pesananRequest = new BuatPesananRequest(banyakHari,currentUserId,idHotel,roomNumber,responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(pesananRequest);
            }
        });
    }
}
