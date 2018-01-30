package com.example.ritam.dstress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url_login ="http://192.168.137.80:5000/login";
        Button login = findViewById(R.id.login_button);
        TextView signup_prompt = findViewById(R.id.prompt_signup);
        final EditText username = findViewById(R.id.username_field);
        final EditText password = findViewById(R.id.pass_field);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "username"+username.getText().toString()+" Password"+password.getText().toString(),Toast.LENGTH_SHORT).show();
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", username.getText().toString());
                    jsonBody.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT,url_login, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //Process os success response
                                    Toast.makeText(MainActivity.this, response.toString(),Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
                queue.add(putRequest);
                Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        signup_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
