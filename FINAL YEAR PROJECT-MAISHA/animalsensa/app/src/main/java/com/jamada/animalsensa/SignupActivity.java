package com.jamada.animalsensa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText username = findViewById(R.id.emailInput);
        final EditText password = findViewById(R.id.passwordInput);
        final EditText repeat_password = findViewById(R.id.passwordInputReapet);
        final EditText location = findViewById(R.id.ward);
        final EditText Name = findViewById(R.id.fullname);
        final TextView Error = findViewById(R.id.errorText);
        final Button signup = findViewById(R.id.signupBtn);

        RequestQueue queue = Volley.newRequestQueue(this);

        signup.setOnClickListener(view -> {
            String Username = username.getText().toString();
            String Password = password.getText().toString();
            String rePassword = repeat_password.getText().toString();
            String Location = location.getText().toString();
            String name = Name.getText().toString();


            //check fields are not empty
            if(Username.isEmpty() || Password.isEmpty() || rePassword.isEmpty() || Location.isEmpty() || name.isEmpty()){
                Error.setText("All fields are required");
                Error.setVisibility(View.VISIBLE);
            }else {
                if(!rePassword.equals(Password)){
                    Error.setText("make sure password are same");
                    Error.setVisibility(View.VISIBLE);
                }else{
                    String url ="http://172.20.10.4/android/signup.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            response -> {
                                if(response.equals("Sign Up Success")){
                                    Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                                    i.putExtra("username", (Parcelable) username);
                                    startActivity(i);
                                }else {
                                    Error.setText(response);
                                    Error.setVisibility(View.VISIBLE);
                                }

                            }, error -> {
                        Error.setText("fail to connect to database check network");
                        Error.setVisibility(View.VISIBLE);
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("username", Username );
                            paramV.put("password", Password );
                            paramV.put("location", Location );
                            paramV.put("name", name );
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });
    }
}