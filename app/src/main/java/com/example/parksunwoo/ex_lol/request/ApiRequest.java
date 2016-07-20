package com.example.parksunwoo.ex_lol.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Park Sunwoo on 2016-07-17.
 */
public class ApiRequest {

    private RequestQueue queue;
    private Context context;
    private static final String API_KEY = "RGAPI-493BCAE9-B235-470B-A129-38E61746B782";
    private String region = "kr";

    public ApiRequest(RequestQueue queue, Context context) {
        this.queue = queue;
        this.context = context;
    }

    public void checkPlayerInfo(final String name, final CheckPlayerInfoCallback callback){
        String url = "https://"+region+".api.pvp.net/api/lol/"+region+"/v1.4/summoner/by-name/"+name+"?api_key="+API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response){
                Log.d("APP", response.toString());
                try {
                    JSONObject json = response.getJSONObject(name.toLowerCase());
                    String name = json.getString("name");
                    long id = json.getLong("id");
                    int profileIcon = json.getInt("profileIconId");
                    long summonerLevel = json.getLong("summonerLevel");
                    callback.onSuccess(name, id, profileIcon, summonerLevel);
                } catch (JSONException e) {
                    Log.d("APP", "EXCEPTION =" + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError("network error");
                }else if(error instanceof ServerError){
                    callback.dontExist("server error");
                }
                Log.d("APP", "ERROR = " + error);
            }
        });
       queue.add(request);
    }

    public interface CheckPlayerInfoCallback{
        void onSuccess(String name, long id, int profileIcon, long summonerLevel);
        void dontExist(String message);
        void onError(String message);
    }


}