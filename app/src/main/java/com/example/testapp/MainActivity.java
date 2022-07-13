package com.example.testapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcelable;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.Adapter.Adapter;
import com.example.testapp.Adapter.Slideradapter;
import com.example.testapp.Data.Api.Api;
import com.example.testapp.Data.Api.VolleyCallBack;
import com.example.testapp.Data.ApiModel.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> itemList=new ArrayList<>();
    TabLayout tabLayout;
    Adapter adapter;
    private Parcelable recyclerViewState;
    SliderView sliderView;
    int[]images={R.drawable.ptwo,
            R.drawable.p1,
            R.drawable.pthree};
    RecyclerView recyclerView;

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        tabLayout=findViewById(R.id.tab_layout);
        recyclerView=findViewById(R.id.rectclerviewimg);
        sliderView = findViewById(R.id.imageSlider);

        itemList=new ArrayList<>();
        Imageslider(sliderView);
        Bottomnavigation();
        VolleyCallBack();
    }
    //Image slide
    private void Imageslider(SliderView sliderView) {
        Slideradapter slideradapter = new Slideradapter(images);
        sliderView.setSliderAdapter(slideradapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.setAutoCycle(true);
        sliderView.setScrollTimeInSec(4);
    }

    //Fetch data
    public  void VolleyCallBack(){
        Api api=new Api(MainActivity.this);
        final ArrayList<Item> list = new ArrayList<>();
        api.getValue(new VolleyCallBack() {
            @Override
            public void onSuccess(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject= response.getJSONObject(i);
                        Item item=new Item();
                        item.setId(jsonObject.getString("id").toString());
                        item.setTitle(jsonObject.getString("title").toString());
                        item.setExpire_date(jsonObject.getString("expire_date").toString());
                        item.setSub_title(jsonObject.getString("sub_title").toString());
                        item.setImage(jsonObject.getString("image").toString());
                        item.setIs_completed(jsonObject.getBoolean("is_completed"));
                        itemList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(layoutManager);
                adapter=new Adapter(getApplicationContext(),itemList);
                recyclerView.setAdapter(adapter);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                recyclerView.smoothScrollToPosition(0);
                recyclerView.post(new Runnable()
                {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    //Bottom navigation items
    public void Bottomnavigation(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.rewards);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sliderView.startAutoCycle();
    }
}