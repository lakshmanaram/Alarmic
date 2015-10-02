package com.pyassasins.alarmic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class RingingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);
        final ImageView clock=(ImageView)findViewById(R.id.alarmclock);
        new RotateAnimation(50f, 50f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        shake.setInterpolator(new CycleInterpolator(7));
        shake.setRepeatCount(Animation.INFINITE);
        shake.setDuration(200);
        clock.startAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                clock.startAnimation(shake);
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }});

    }


}
