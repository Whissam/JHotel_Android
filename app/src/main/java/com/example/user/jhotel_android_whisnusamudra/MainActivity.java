package com.example.user.jhotel_android_whisnusamudra;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Hotel> listHotel = new ArrayList<>();
    private ArrayList<Room> listRoom = new ArrayList<>();
    private HashMap<Hotel, ArrayList<Room>> childMapping = new HashMap<>();
    private int i;
    private MenuListAdapter listAdapter;
    private ExpandableListView expListView;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshList();
        listAdapter = new MenuListAdapter(MainActivity.this, listHotel, childMapping);
        expListView = (ExpandableListView) findViewById(R.id.list_group);
        expListView.setAdapter(listAdapter);
        currentUserId = getIntent().getIntExtra("ID customer",0);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Room selected = childMapping.get(listHotel.get(groupPosition)).get(childPosition);
                Hotel hotel = (Hotel) listHotel.get(groupPosition);
                String nomor_kamar = selected.getRoomNumber();
                Intent childIntent = new Intent(MainActivity.this,BuatPesananActivity.class);
                childIntent.putExtra("ID customer", currentUserId);
                childIntent.putExtra("room number", nomor_kamar);
                childIntent.putExtra("ID hotel",hotel.getId());
                startActivity(childIntent);
                return true;
            }
        });

        Button pesananbtn = (Button) findViewById(R.id.pesananBtn);
        pesananbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selesaiintent = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                selesaiintent.putExtra("ID customer", currentUserId);
                startActivity(selesaiintent);
            }
        });
    }

    public void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null){

                        JSONObject e = jsonResponse.getJSONObject(0).getJSONObject("hotel");
                        JSONObject lokasi = e.getJSONObject("lokasi");
                        Hotel hotel = new Hotel(e.getInt("id"),
                                                e.getString("nama"),
                                                new Lokasi(lokasi.getDouble("x"),
                                                            lokasi.getDouble("y"),
                                                            lokasi.getString("deskripsi")),
                                                e.getInt("bintang"));
                     listHotel.add(hotel);
                       /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("ok.")
                                .create()
                                .show(); */
                     for (i=0; i<jsonResponse.length();i++){
                        JSONObject r = jsonResponse.getJSONObject(i);
                        listRoom.add(new Room(r.getString("nomorKamar"),
                                                r.getString("statusKamar"),
                                                r.getDouble("dailyTariff"),
                                                r.getString("tipeKamar")));

                    }
                    childMapping.put((Hotel) listHotel.get(0), listRoom);
                    }
                }
                catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Failed to load.")
                            .create()
                            .show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
