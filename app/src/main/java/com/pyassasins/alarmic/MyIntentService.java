package com.pyassasins.alarmic;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MyIntentService extends IntentService {
    private static final String ACTION_TEXT = "com.pyassasins.alarmic.action.TEXT";
    private static final String ACTION_BAZ = "com.pyassasins.alarmic.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.pyassasins.alarmic.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.pyassasins.alarmic.extra.PARAM2";
    private static final String EXTRA_PARAM3 = "com.pyassasins.alarmic.extra.PARAM3";
    private static final String EXTRA_PARAM4 = "com.pyassasins.alarmic.extra.PARAM4";
    private static final String EXTRA_PARAM5 = "com.pyassasins.alarmic.extra.PARAM5";
    private static final String EXTRA_PARAM6 = "com.pyassasins.alarmic.extra.PARAM6";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionText(Context context, String msg, String to,String from,String time,String id,int hide) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_TEXT);
        intent.putExtra(EXTRA_PARAM1, msg);
        intent.putExtra(EXTRA_PARAM2, to);
        intent.putExtra(EXTRA_PARAM3, from);
        intent.putExtra(EXTRA_PARAM4,time);
        intent.putExtra(EXTRA_PARAM5,id);
        intent.putExtra(EXTRA_PARAM6,hide);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_TEXT.equals(action)) {
                final String msg = intent.getStringExtra(EXTRA_PARAM1);
                final String to= intent.getStringExtra(EXTRA_PARAM2);
                final String from=intent.getStringExtra(EXTRA_PARAM3);
                final String time=intent.getStringExtra(EXTRA_PARAM4);
                final String id=intent.getStringExtra(EXTRA_PARAM5);
                final int hide=intent.getIntExtra(EXTRA_PARAM6,-1);

                handleActionText(msg,to,from,time,id,hide);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);

            }
        }
    }

    private void handleActionText(String msg, String to, String from, String time, String id, int hide) {
        JSONObject json= new JSONObject();
        try {
            json.put("time",time);
            json.put("msg",msg);
            json.put("to",to);
            json.put("from",from);
            json.put("id",id);
            json.put("hide",String.valueOf(hide));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://6a6a4748.ngrok.com/app/newalarm/");
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

        Log.i("responsee", httpResponse.toString());
    }


}
