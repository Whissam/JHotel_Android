package com.example.user.jhotel_android_whisnusamudra;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class MenuRequest extends StringRequest {
    private static final String Menu_URL = "http://192.168.43.3:8080/vacantrooms";

    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, Menu_URL, listener, null);
    }
}
