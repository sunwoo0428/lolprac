package com.example.parksunwoo.ex_lol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.example.parksunwoo.ex_lol.request.ApiRequest;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class sub1Activity extends AppCompatActivity {

    private String playerName;
    private Long playerId;
    private int playerIcon;
    private Long playerLevel;
  //  private RecyclerView recyclerView;
    private ApiRequest request;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final ImageView imgView = (ImageView) findViewById(R.id.imgView);

//        queue = MySingleton.getInstance(this).getRequestQueue();
 //       request = new ApiRequest(queue, this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        playerName = extras.getString("NAME");
        playerId = extras.getLong("id");
        playerIcon = extras.getInt("profileIcon");
        playerLevel = extras.getLong("summonerLevel");

        TextView textView_name = (TextView)findViewById(R.id.searched_name);
        TextView textView_level = (TextView)findViewById(R.id.summoner_level);

        textView_name.setText(String.valueOf(playerName));
        textView_level.setText(String.valueOf(playerLevel));

        String url2 = "http://ddragon.leagueoflegends.com/cdn/6.14.2/img/profileicon/"+playerIcon+".png";

        new DownLoadImageTask(imgView).execute(url2);

    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
