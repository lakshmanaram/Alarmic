package com.pyassasins.alarmic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class HomeSqlHandler extends SQLiteOpenHelper {
    public static final int VERSION=2;
    public static final String DBNAME="alarm.db";
    public static final String TABLENAME="ALARMS";
    public static final String ALARMTEXT="alarm";
    public static final String ALARMTIME="time";
    public static final String ALARMTO="lto";
    public static final String ALARMHIDE="hide";
    public HomeSqlHandler(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLENAME+"(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ALARMTEXT+" TEXT," +
                ALARMTIME+" TEXT," +
                ALARMTO+" TEXT," +
                ALARMHIDE+" INTEGER" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }


    public String addAlarmInfo(String text,String time,String to,int hide){
        ContentValues v=new ContentValues();
        v.put(ALARMTEXT,text);
        v.put(ALARMTIME,time);
        v.put(ALARMTO, to);
        v.put(ALARMHIDE,hide);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLENAME, null, v);
        SQLiteDatabase dbs=getReadableDatabase();
        Cursor c=dbs.query(true, TABLENAME, new String[] {
                        "_id"},
                null,
                null,
                null, null, null ,null);
        c.moveToFirst();
        int id=0;
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("_id"))!=null){
                String temp=c.getString(c.getColumnIndex("_id"));
                int no=Integer.parseInt(temp);
                if(id<no){
                    id=no;
                }
            }
            c.moveToNext();

        }
        c.close();
        return String.valueOf(id);
    }

    public Alarmy[] getAlarmInfo(){
        List<Alarmy> all=new ArrayList<>();
        Alarmy a;
        SQLiteDatabase dbs=getReadableDatabase();
        Cursor c=dbs.query(true, TABLENAME, new String[] {
                        "_id",ALARMTEXT,ALARMTIME,ALARMTO,ALARMHIDE},
                null,
                null,
                null, null, null ,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            a=new Alarmy();
            if(c.getString(c.getColumnIndex("_id"))!=null){
                String id=c.getString(c.getColumnIndex("_id"));
                String text=c.getString(c.getColumnIndex(ALARMTEXT));
                String time=c.getString(c.getColumnIndex(ALARMTIME));
                String to=c.getString(c.getColumnIndex(ALARMTO));
                int hide=c.getInt(c.getColumnIndex(ALARMHIDE));
                a.setTime(time);
                a.setMsg(text);
                a.setId(id);
                a.setTo(to);
                a.setHide(hide);

            }
            c.moveToNext();
            all.add(a);
        }
        c.close();
        Alarmy[] al=new Alarmy[all.size()];
        al=all.toArray(al);
        return al;

    }

}
