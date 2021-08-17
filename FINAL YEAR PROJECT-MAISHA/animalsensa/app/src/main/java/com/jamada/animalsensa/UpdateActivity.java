package com.jamada.animalsensa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    EditText name,cow,sheep,goat,pig;
    String sname,scow,ssheep,sgoat,spig,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name = findViewById(R.id.ownerNameUpdate);
        cow = findViewById(R.id.cowNumberUpdate);
        sheep = findViewById(R.id.sheepNumberUpdate);
        goat = findViewById(R.id.goatNumberUpdate);
        pig = findViewById(R.id.pigNumberUpdate);
        final Button update = findViewById(R.id.updateBtn);

        getData();
        setData();

        update.setOnClickListener(View ->{
            String Owner = name.getText().toString();
            String Cow = cow.getText().toString();
            String Pig = pig.getText().toString();
            String Sheep = sheep.getText().toString();
            String Goat = goat.getText().toString();

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
                    paramV.put("act","upload");
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

    private void getData(){
        if(getIntent().hasExtra("name")){
            sname = getIntent().getStringExtra("name");
            scow = getIntent().getStringExtra("cow");
            sgoat = getIntent().getStringExtra("goat");
            ssheep = getIntent().getStringExtra("sheep");
            spig = getIntent().getStringExtra("pig");
            username = getIntent().getStringExtra("username");

        }else{
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }

    }

    private void setData(){
        name.setText(sname);
        cow.setText(scow);
        pig.setText(spig);
        goat.setText(sgoat);
        sheep.setText(ssheep);
    }
}