package com.jamada.animalsensa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> cows = new ArrayList<>();
    ArrayList<String> pigs = new ArrayList<>();
    ArrayList<String> goats= new ArrayList<>();
    ArrayList<String> sheeps = new ArrayList<>();
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent i = getIntent();
        String username = i.getStringExtra("username");

        recyclerView = findViewById(R.id.recyclerView);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.20.10.4/android/index.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONObject obj;
                    JSONObject sums;
                    try {
                        obj = new JSONObject(response);
                        sums = obj.getJSONObject("sum");
                        int sum = sums.getInt("sum");
                        if (sum > 0) {
                            for (int x = 0; x < sum; x++) {
                                String y = String.valueOf(x + 1);
                                JSONObject info = obj.getJSONObject(y);
                                userName = username;
                                String name = info.getString("name");
                                String cow = info.getString("cow");
                                String pig = info.getString("pig");
                                String sheep = info.getString("sheep");
                                String goat = info.getString("goat");

                                names.add(x,name);
                                cows.add(x,cow);
                                pigs.add(x,pig);
                                goats.add(x,goat);
                                sheeps.add(x,sheep);

                            }
                            MyAdapter myAdapter = new MyAdapter(this,names,cows,pigs,goats,sheeps,userName);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            Log.d("TAG", response);
                        } else {
                            Log.d("TAG", "null");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("TAG", String.valueOf(error));
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("act", "all");
                paramV.put("username", username);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}