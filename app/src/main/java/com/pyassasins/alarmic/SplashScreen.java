package com.pyassasins.alarmic;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    GoogleCloudMessaging gcmObj;
    static String GOOGLE_PROJ_ID="858719501366";
    String regId="";
    String URL="http://40a7e077.ngrok.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startActivity(new Intent(this, MainActivity.class));
        //todo:UPDATE CONTACT
        ContactsHandler handler =new ContactsHandler(this);
        if(handler.getContactslist().size()==0){
           final EditText no=(EditText)findViewById(R.id.phone);
           final EditText nick=(EditText)findViewById(R.id.nick);
            FloatingActionButton f=(FloatingActionButton)findViewById(R.id.splashf) ;
            f.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                doit(no.getText().toString(),nick.getText().toString());

                        }
                    }
            );
           
        }
        else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    private void doit( final String number, final String nick) {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                HttpClient httpclient = new DefaultHttpClient();
                int sasl=10;
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(SplashScreen.this);
                    }
                    regId = gcmObj
                            .register(GOOGLE_PROJ_ID);
                }catch(IOException e){
                    e.printStackTrace();
                }

                HttpPost httpPost = new HttpPost("https://40a7e077.ngrok.io/app/register/");
                JSONObject json=new JSONObject();
                try {
                    json.put("gcm",regId);
                    json.put("pno",number);
                    json.put("nick",nick);
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                StringEntity s= null;
                try {
                    s = new StringEntity(json.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                httpPost.setEntity(s);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = null;
                try {
                    httpResponse = httpclient.execute(httpPost);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("responsee",httpResponse.toString());
                MyIntentService.startActionText(SplashScreen.this, "adhsv", number, number, "8:30", "dj", 0);
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                return null;
            }
        }.execute();
    }


}
