package com.example.testapp.Data.Api;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp.Data.Baseurl.Baseurl;
import com.example.testapp.Data.Endpoints.Endpoint;
import com.example.testapp.MainActivity;


import org.json.JSONArray;


public class Api {
    final MainActivity contex  ;

    public Api(MainActivity value){
        contex=value;
    };
    public void getValue(final VolleyCallBack myValue) {
        RequestQueue queue = Volley.newRequestQueue(contex);
        String url = Baseurl.baseurl+ Endpoint.rewards;
        final String[] currentResponse = new String[1];
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                myValue.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonArrayRequest);
    }
}
