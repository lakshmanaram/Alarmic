package com.pyassasins.alarmic;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;


public class SetTextAlarm extends ActionBarActivity {

    Context appContext;
    Button setalarm;
    ContactsHandler contactsHandler;
    EditText title;
    Spinner spinner,repeat;
    ImageButton tpb;
    TimePicker timePicker;
    int hour,min;
    TextView clock;
    //AnalogClock analogClock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_text_alarm);
        contactsHandler = new ContactsHandler(SetTextAlarm.this);
        appContext  = this.getApplicationContext();
        //analogClock = (AnalogClock) findViewById(R.id.analogClock);
//        analogClock.onT
        title = (EditText) findViewById(R.id.texttitle);
        spinner = (Spinner) findViewById(R.id.todropdown);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Cont> temp = contactsHandler.getAuthenticatedcontacts(1);
        for(Cont i: temp)
        {
            list.add(i.getName());

        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        repeat = (Spinner) findViewById(R.id.repeat);
        clock = (TextView) findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SetTextAlarm.this);
                dialog.setContentView(R.layout.timepicker);
                dialog.setTitle("Alarm Time");
                timePicker = (TimePicker)dialog.findViewById(R.id.timePicker);
                tpb = (ImageButton)dialog.findViewById(R.id.tpbu);
                tpb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hour = timePicker.getCurrentHour();
                        min = timePicker.getCurrentMinute();
                        clock.setText(String.valueOf(hour)+":"+String.valueOf(min));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        setalarm = (Button) findViewById(R.id.setAlarm);
        setalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: api integration
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_text_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
