package com.jamada.animalsensa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.emailInput);
        final EditText password = findViewById(R.id.passwordInput);
        final TextView Error = findViewById(R.id.errorText);
        final Button login = findViewById(R.id.loginBtn);

        RequestQueue queue = Volley.newRequestQueue(this);

        login.setOnClickListener(view -> {
            String Email = email.getText().toString();
            String Password = password.getText().toString();

            //check fields are not empty
            if(Email.isEmpty() || Password.isEmpty()){
                Error.setText("All fields are required");
                Error.setVisibility(View.VISIBLE);
            }else {
                String url ="http://172.20.10.4/android/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            if(response.equals("ward")){
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("username",Email);
                                startActivity(i);
                            }else if(response.equals("admin")){
                                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                                i.putExtra("username",Email);
                                startActivity(i);
                            }
                            else {
                                Error.setText(response);
                                Error.setVisibility(View.VISIBLE);
                            }

                        }, error -> {
                    Error.setText("fail to connect to database check url!!!"+error.getMessage());
                    Error.setVisibility(View.VISIBLE);
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("username", Email );
                        paramV.put("password", Password );
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

}