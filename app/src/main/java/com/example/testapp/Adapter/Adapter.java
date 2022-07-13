package com.example.testapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.testapp.Data.ApiModel.Item;
import com.example.testapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<Item>items;
    public Adapter(Context ctx, ArrayList<Item>items){
        this.layoutInflater=LayoutInflater.from(ctx);
        this.items=items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.itemrecycler,parent,false);
        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       String title=items.get(position).getTitle();
        String subtitle=items.get(position).getSub_title();
        String date=items.get(position).getExpire_date();
        boolean status=items.get(position).isIs_completed();
        String resource=items.get(position).getImage();
        holder.setData(resource,title,subtitle,date,status);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titl,subtitl,dateview,stat;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titl=itemView.findViewById(R.id.titletext);
            subtitl=itemView.findViewById(R.id.subtitle);
            dateview=itemView.findViewById(R.id.date);
            stat=itemView.findViewById(R.id.iscompleted);
            img=itemView.findViewById(R.id.imageview);
        }
        public void setData(String resource, String title, String subtitle, String date, boolean status) {
            Picasso.get().load(resource).fit().centerCrop().into(img);
            titl.setText(title);
            subtitl.setText(subtitle);
            String strDate = date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
            try {
                Date da = dateFormat.parse(strDate);
                dateview.setText("Expires on "+da);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (status==true){
                stat.setText("Completed");
                stat.setBackgroundResource(R.drawable.statusshape);
            }
            else {
                stat.setText("Not completed ");
                stat.setBackgroundResource(R.drawable.statusnotshape);
            }
        }
    }
}
