package com.acadview.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.networkutil.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView nameText,bioText;
    EditText nameEt;
    Button goBtn;

    String BASE_URL = "https://api.github.com/users/";
    String data = null;

    String githubName,gitBio,Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        nameText =  findViewById(R.id.name);
        bioText = findViewById(R.id.bio);
        nameText = findViewById(R.id.userEt);
        goBtn = findViewById(R.id.GoBtn);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadData().execute();
            }
        });
    }

    class LoadData extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String userName = nameText.getText().toString();

            if(userName == null){
                return null;
            }
            String url = BASE_URL+userName;

            data = NetworkUtil.makeServiceCall(url);

            try {

                JSONObject jsonObject = new JSONObject(data);
                githubName = jsonObject.getString("name");
                gitBio = jsonObject.getString("bio");
                Image = jsonObject.getString("avatar_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (githubName.equals("null")) {

                nameText.setText(null);
            }else{
                nameText.setText(githubName);
            }

            if (bioText.equals("null")) {

                bioText.setText(null);
            }else{
                bioText.setText(gitBio);
            }

            Glide.with(MainActivity.this).load(Image).into(imageView);

        }
    }



    }
