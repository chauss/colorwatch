package com.chhauss.colorwatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.chhauss.colorwatch.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private boolean active;
    private View clockView;
    private TextView clockString;
    private TextView clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        clockView = getWindow().getDecorView().getRootView();
        
        clockString = (TextView) findViewById(R.id.timeCodeTW);
        clockString.setTextColor(Color.WHITE);
        
        clock = (TextView) findViewById(R.id.clockTW);
        clock.setTextColor(Color.WHITE);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        this.active = true;
        BackgroundAdjuster ba = new BackgroundAdjuster();
        ba.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.active = false;
    }

    class BackgroundAdjuster extends Thread {

        @Override
        public void run() {
            super.run();
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", Locale.GERMANY);
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);
            
            while(active) {
                try {
                    Calendar cal = Calendar.getInstance(Locale.GERMANY);
                    final String timeCode = "#" + sdf.format(cal.getTime());
                    final String time = sdf2.format(cal.getTime());
                    runOnUiThread(new Runnable() {
						@Override
						public void run() {
							clockView.setBackgroundColor(Color.parseColor(timeCode));
							clock.setText(time);
		                    clockString.setText(timeCode);
						}
                    	
                    });
                    
                    System.out.println("Sleeping for 1 s");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        
    }
}
