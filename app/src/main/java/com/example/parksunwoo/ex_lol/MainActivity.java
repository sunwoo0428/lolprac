package com.example.parksunwoo.ex_lol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.example.parksunwoo.ex_lol.request.ApiRequest;

public class MainActivity extends AppCompatActivity {

    private EditText playerName;
    private Button searchButton;
    private RequestQueue queue;
    private ApiRequest request;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue, this);
        handler = new Handler();

        playerName = (EditText) findViewById(R.id.playerName);
        searchButton = (Button) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pName = playerName.getText().toString().trim();

                if(pName.length() > 0){

                handler.postDelayed(new Runnable() {
                        public void run() {
                            request.checkPlayerInfo(pName, new ApiRequest.CheckPlayerInfoCallback() {
                                public void onSuccess(String name, long id, int profileIcon, long summonerLevel) {
                                    Intent intent = new Intent(getApplicationContext(), sub1Activity.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("NAME", name);
                                    extras.putLong("id", id);
                                    extras.putInt("profileIcon", profileIcon);
                                    extras.putLong("summonerLevel", summonerLevel);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }

                                public void dontExist(String message) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                                public void onError(String message) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },500);
                } else {
                    Toast.makeText(getApplicationContext(), "소환사명을 입력하시오", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
