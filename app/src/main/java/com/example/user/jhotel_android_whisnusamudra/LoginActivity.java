package com.example.user.jhotel_android_whisnusamudra;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailInput = (EditText) findViewById(R.id.emailEt);
        final EditText passInput = (EditText) findViewById(R.id.passEt);
        final Button loginbtn = (Button) findViewById(R.id.loginBtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailInput.getText().toString();
                final String pass = passInput.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null) {
                                int id = jsonResponse.getInt("id");
                                Intent menuInt = new Intent(LoginActivity.this, MainActivity.class);
                                menuInt.putExtra("ID customer",id);
                                LoginActivity.this.startActivity(menuInt);
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Login Failed.")
                                    .create()
                                    .show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email,pass,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

        final TextView registerClickable = (TextView) findViewById(R.id.registerText);
        registerClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regisInt = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(regisInt);

            }
        });

    }

}
