package com.example.ritam.dstress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url_signup ="http://192.168.137.80:5000/signup";
        final EditText new_user = findViewById(R.id.new_username);
        final EditText new_pass = findViewById(R.id.new_password);
        Button signup_button = findViewById(R.id.signup_button);

        /*final String new_username=new_user.getText().toString();
        final String new_password=new_pass.getText().toString();*/



// Request a string response from the provided URL.



        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignupActivity.this,"Username = "+new_user.getText().toString()+" Password"+new_pass.getText().toString(),Toast.LENGTH_SHORT).show();
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", new_user.getText().toString());
                    jsonBody.put("password", new_pass.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,url_signup, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //Process os success response

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

                queue.add(postRequest);
            }
        });
    }
}
