package com.pyassasins.alarmic;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmService extends IntentService {
    private static final String ACTION_SET_CUSTOM_ALARM = "com.delta.attendancemanager.action.SET_CUSTOM_ALARM";
    private static final String CANCEL_ALARM = "com.delta.attendancemanager.action.CANCEL_ALARM";
    private static final String DATE = "com.delta.attendancemanager.date";
    private static final String MONTH = "com.delta.attendancemanager.month";
    private static final String YEAR = "com.delta.attendancemanager.year";
    private static final String HOUR = "com.delta.attendancemanager.hour";
    private static final String MIN = "com.delta.attendancemanager.min";
    public static void startActionSetCustomAlarm(Context context, int date, int month, int year, int hour, int min) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_SET_CUSTOM_ALARM);
        intent.putExtra(DATE, date);
        intent.putExtra(MONTH, month);
        intent.putExtra(YEAR, year);
        intent.putExtra(HOUR, hour);
        intent.putExtra(MIN, min);
        context.startService(intent);
    }

    public static void cancelAlarm(Context context){
        Intent intent = new Intent(context,AlarmService.class);
        intent.setAction(CANCEL_ALARM);
        context.startActivity(intent);
    }

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SET_CUSTOM_ALARM.equals(action)) {
                final int date1 = intent.getIntExtra(DATE,1);
                final int hour1 = intent.getIntExtra(HOUR,0);
                final int min1 = intent.getIntExtra(MIN,0);
                final int month1 = intent.getIntExtra(MONTH,1);
                final int year1 = intent.getIntExtra(YEAR, 2000);
                handleActionSetcustomalarm(date1,month1,year1,hour1, min1);
            }
            else if (CANCEL_ALARM.equals(action)){
                handleCancelAlarm();
            }
        }
    }
    private void handleActionSetcustomalarm(int ldate, int lmonth, int lyear, int lhour, int lmin) {
        Intent inten = new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,lhour);
        c.set(Calendar.MINUTE,lmin);
        c.set(Calendar.DAY_OF_MONTH,ldate);
        c.set(Calendar.MONTH,lmonth);
        c.set(Calendar.YEAR,lyear);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), PendingIntent.getBroadcast(this, 0, inten, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Custom alarm set", Toast.LENGTH_LONG).show();
    }


    private void handleCancelAlarm(){
        Intent inten = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,inten,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
        Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_LONG).show();
    }
}
