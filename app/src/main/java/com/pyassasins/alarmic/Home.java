package com.pyassasins.alarmic;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Home extends Activity {

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
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.floating);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Clock thingy
                    }
                }
        );
    }


}
