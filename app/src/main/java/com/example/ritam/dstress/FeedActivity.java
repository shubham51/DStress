package com.example.ritam.dstress;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OpinionDialog.OpinionDialogListener,PositiveOpinionDialog.PositiveOpinionDialogListener {

    ListView feedView;
    List<CustomPost> list;
    FeedAdapter adapter;
    EditText editText;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final RequestQueue queue= Volley.newRequestQueue(this);
        final String url_sentiment ="http://192.168.137.80:5000/sentiment";
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        editText=(EditText)findViewById(R.id.text);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonBody=new JSONObject();
                try {
                    jsonBody.put("text",editText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, url_sentiment, jsonBody, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                //Toast.makeText(FeedActivity.this,"hey",Toast.LENGTH_SHORT).show();
                                try {
                                    //Toast.makeText(FeedActivity.this,response.getString("negative"),Toast.LENGTH_SHORT).show();
                                    if(response.getString("negative").equals("yes")){
                                        //Toast.makeText(FeedActivity.this,"negative",Toast.LENGTH_SHORT).show();
                                        confirmNegativeDialog();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(FeedActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.e("ERROR", "Error occurred ", error);

                            }
                        });
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(jsObjRequest);

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        feedView = findViewById(R.id.feedList);
        list=new ArrayList<CustomPost>();
        adapter=new FeedAdapter(this,list);

        final String url_login ="http://192.168.137.80:5000/feed";
        /*Uri.Builder builder=new Uri.Builder();
        builder.scheme("https").authority("newsapi.org").appendEncodedPath("v2").appendEncodedPath("top-headlines").appendQueryParameter("country","in").appendQueryParameter("apiKey","dafe84df59ff49b6ac60758549584cbd");
        final String url_login=builder.build().toString();*/
        /*Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority("192.168.137.80:5000").appendEncodedPath("feed");
        String url_login = builder.build().toString();*/
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url_login, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(FeedActivity.this,response.length(),Toast.LENGTH_SHORT).show();
                        processResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("ERROR", "Error occurred ", error);

                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_History) {

        } else if (id == R.id.nav_good_deeds) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void processResponse(JSONObject response){
        try {
            JSONArray array = response.getJSONArray("posts");
            for(int i=0;i<array.length();i++)
            {   CustomPost post=new CustomPost(array.getJSONObject(i).getString("title"),array.getJSONObject(i).getString("url"));
                list.add(post);
            }
        }
        catch (JSONException e)
        {
            Toast.makeText(FeedActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        feedView.setAdapter(adapter);
    }

    public void confirmNegativeDialog(){
        DialogFragment dialog = new OpinionDialog();
        dialog.show(getFragmentManager(),"dialog");
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        confirmPositiveDialog();
        Toast.makeText(FeedActivity.this,"Positive Button Clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(FeedActivity.this,"Negative Button Clicked",Toast.LENGTH_SHORT).show();

    }

    public void confirmPositiveDialog(){
        DialogFragment positiveDialog = new PositiveOpinionDialog();
        positiveDialog.show(getFragmentManager(),"positiveDialog");
    }

    @Override
    public void onPositiveDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(FeedActivity.this,"Good Memories Archived xD",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPositiveDialogNegativeClick(DialogFragment dialog) {

    }
}
