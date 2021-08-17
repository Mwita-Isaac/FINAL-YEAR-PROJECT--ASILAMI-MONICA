package com.jamada.animalsensa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    String username;
    TextView userName, userMail, cows, goats, sheeps, pigs;
    Button regNew, viewward, logout;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userName = findViewById(R.id.userText);
        userMail = findViewById(R.id.levelText);
        cows = findViewById(R.id.cowNum);
        goats = findViewById(R.id.goatNum);
        sheeps = findViewById(R.id.sheepNum);
        pigs = findViewById(R.id.pigNum);
        regNew = findViewById(R.id.newAccountBtn);
        viewward = findViewById(R.id.viewWardBtn);
        logout = findViewById(R.id.logOutBtn);

        queue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        username = i.getStringExtra("username");

        regNew.setOnClickListener(View -> {
            Intent a = new Intent(getApplicationContext(), SignupActivity.class);
            a.putExtra("username", username);
            startActivity(a);
        });

        viewward.setOnClickListener(View -> {
            Intent a = new Intent(getApplicationContext(), AdminListActivity.class);
            a.putExtra("username", username);
            startActivity(a);
        });

        logout.setOnClickListener(View -> {
            Intent a = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(a);
        });

        String url = "http://172.20.10.4/android/index.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONObject obj;
                    JSONObject animaldata;
                    try {
                        Log.d("TAG",response);
                        obj = new JSONObject(response);
                        animaldata = obj.getJSONObject("animaldata");
                        String userward = obj.getString("location");
                        String fullname = obj.getString("uname");
                        String level = obj.getString("level");
                        String cow = animaldata.getString("cow");
                        String sheep = animaldata.getString("sheep");
                        String pig = animaldata.getString("pig");
                        String goat = animaldata.getString("goat");

                        cows.setText(cow);
                        goats.setText(goat);
                        sheeps.setText(sheep);
                        pigs.setText(pig);
                        userName.setText(fullname);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("TAG", String.valueOf(error));
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("act", "admin_read");
                paramV.put("username", username);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}