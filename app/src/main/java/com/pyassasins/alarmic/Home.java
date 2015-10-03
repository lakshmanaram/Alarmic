package com.pyassasins.alarmic;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bowyer.app.fabtransitionlayout.BottomSheetLayout;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {
    BottomSheetLayout mBottomSheetLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final HomeSqlHandler handler=new HomeSqlHandler(this);
        ListView list=(ListView)findViewById(R.id.homelist);
        Alarmy[] alarms= handler.getAlarmInfo();
        List<Alarmy> al=new ArrayList<>();
        for(Alarmy i : alarms){
            if(i.getHide()==0){
                al.add(i);
            }
        }
        Alarmy[] h=new Alarmy[al.size()];
        h=al.toArray(h);

        HomeListAdapter adapter=new HomeListAdapter(this,h);
        list.setAdapter(adapter);
        ListView listView = (ListView) findViewById(R.id.list_menu);
        String[] menu = {"Text","Picture","Video"};
        ListMenuAdapter lmadapter = new ListMenuAdapter(this,menu);
        listView.setAdapter(lmadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent intent = new Intent(getApplicationContext(),SetTextAlarm.class);
                    startActivity(intent);
                    //todo: toast to say that the alarm has been set.
                    mBottomSheetLayout.slideInFab();
                }
                else if(position==1){

                }
                else if (position==2){

                }

            }
        });
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.floating);
        mBottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottom_sheet);
        mBottomSheetLayout.setFab(fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mBottomSheetLayout.expandFab();
                        //TODO: Clock thingy
                    }
                }
        );
    }


}
