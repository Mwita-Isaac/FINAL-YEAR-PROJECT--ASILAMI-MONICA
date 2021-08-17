package com.jamada.animalsensa;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> name;
    ArrayList<String> sheep;
    ArrayList<String> goat;
    ArrayList<String> pig;
    ArrayList<String> cow;
    String user;
    Context context;

    public MyAdapter(Context ct, ArrayList<String> S1, ArrayList<String>S2, ArrayList<String>S3, ArrayList<String>S4, ArrayList<String>S5, String users){
        context = ct;
        name = S1;
        cow = S2;
        pig = S3;
        goat = S4;
        sheep = S5;
        user = users;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.names.setText(name.get(position));
        holder.cows.setText(cow.get(position));
        holder.pigs.setText(pig.get(position));
        holder.sheeps.setText(sheep.get(position));
        holder.goats.setText(goat.get(position));
        holder.updateScreen.setOnClickListener(v -> {
            Intent a = new Intent(context, UpdateActivity.class);
            a.putExtra("name", name.get(position));
            a.putExtra("cow", cow.get(position));
            a.putExtra("pig", pig.get(position));
            a.putExtra("sheep", sheep.get(position));
            a.putExtra("goat", goat.get(position));
            a.putExtra("username", user);
            context.startActivity(a);
        });
    }

    @Override
    public int getItemCount() {
        return pig.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView names, cows, pigs, goats, sheeps;
        ConstraintLayout updateScreen;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.zizi_name);
            cows = itemView.findViewById(R.id.cow_num);
            pigs = itemView.findViewById(R.id.pig_num);
            goats = itemView.findViewById(R.id.goat_num);
            sheeps = itemView.findViewById(R.id.sheep_num);
            updateScreen = itemView.findViewById(R.id.UpdateScreen);
        }
    }
}
