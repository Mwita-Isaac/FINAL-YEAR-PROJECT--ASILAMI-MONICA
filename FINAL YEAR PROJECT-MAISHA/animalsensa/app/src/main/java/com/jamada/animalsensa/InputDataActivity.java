package com.jamada.animalsensa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InputDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        final TextView ownername = findViewById(R.id.ownerName);
        final TextView cownumber = findViewById(R.id.cowNumber);
        final TextView goatnumber = findViewById(R.id.goatNumber);
        final TextView sheepnumber = findViewById(R.id.sheepNumber);
        final TextView pignumber = findViewById(R.id.pigNumber);
        final Button save = findViewById(R.id.saveBtn);

        Intent i = getIntent();
        String username = i.getStringExtra("username");

        save.setOnClickListener(View ->{
            String Owner = ownername.getText().toString();
            String Cow = cownumber.getText().toString();
            String Pig = pignumber.getText().toString();
            String Sheep = sheepnumber.getText().toString();
            String Goat = goatnumber.getText().toString();

            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://172.20.10.4/android/index.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response ->{
                       Log.d("TAG",response);
                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        a.putExtra("username",username);
                        startActivity(a);
                        finish();
                    },
                    error ->{
                        Log.d("TAG", String.valueOf(error));
                    }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("act","register");
                    paramV.put("username",username);
                    paramV.put("cow",Cow);
                    paramV.put("sheep",Sheep);
                    paramV.put("pig",Pig);
                    paramV.put("goat",Goat);
                    paramV.put("owner",Owner);
                    return paramV;
                }
            };
            queue.add(stringRequest);

        });
    }
}