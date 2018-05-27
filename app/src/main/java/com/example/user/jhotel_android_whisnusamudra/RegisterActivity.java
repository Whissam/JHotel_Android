package com.example.user.jhotel_android_whisnusamudra;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText newName = (EditText) findViewById(R.id.nameEditText);
        final EditText newEmail = (EditText) findViewById(R.id.newEmail);
        final EditText newPass = (EditText) findViewById(R.id.newPass);
        Button registbtn = (Button) findViewById(R.id.registButton);

        registbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namereg = newName.getText().toString();
                String emailreg = newEmail.getText().toString();
                String passreg = newPass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Success")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Registration Failed.")
                                    .create()
                                    .show();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(namereg,emailreg,passreg,responseListener);
                RequestQueue queue =Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
