package com.example.user.jhotel_android_whisnusamudra;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class BuatPesananRequest extends StringRequest {
    private static final String Pesan_URL = "http://192.168.43.3:8080/bookpesanan";
    private Map<String, String> params;

    public BuatPesananRequest(int jumlah_hari, int id_customer, int id_hotel, String nomor_kamar,
                           Response.Listener<String> listener) {
        super(Method.POST, Pesan_URL, listener, null);
        params = new HashMap<>();
        params.put("jumlah_hari", String.valueOf(jumlah_hari));
        params.put("id_customer", String.valueOf(id_customer) );
        params.put("id_hotel", String.valueOf(id_hotel));
        params.put("nomor_kamar", nomor_kamar);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
