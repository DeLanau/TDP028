package com.nadla777.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nadla777.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class focus_fragment extends Fragment {

    private TextView timerTextView;
    private long timerMilliseconds = 0;
    private CountDownTimer countDownTimer;

    private float prevY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.focus_fragment, container, false);
        timerTextView = rootView.findViewById(R.id.focus_timer);
        Log.d("DEBUG", "SETUP FRAGMENT");
        setupTimer();
        setupScrollBehavior(rootView);
        return rootView;
    }

    private void setupTimer() {
        countDownTimer = new CountDownTimer(timerMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerMilliseconds = millisUntilFinished;
                //dateTimerText();
            }

            @Override
            public void onFinish() {
                // Timer finished
            }
        };
        countDownTimer.start();
    }

    private void setupScrollBehavior(View rootView) {
        ScrollView scrollView = rootView.findViewById(R.id.scroll);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // Use a constant value for both scrolling up and down
                    int scrollDelta = 100;

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String timer_txt = timerTextView.getText().toString();
                    Date time;

                    try {
                        time = sdf.parse(timer_txt);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if (event.getY() > prevY) {
                        // Scrolling down
                        timerMilliseconds -= scrollDelta;
                        time.setTime(time.getTime() - 1000);
                        Log.d("DEBUG", "SCROLL DOWN");
                    } else {
                        // Scrolling up
                        timerMilliseconds += scrollDelta;
                       /* long newTime = time.getTime() - 1000;
                        if (newTime >= 0) {
                            time.setTime(newTime);
                        }*/
                        time.setTime(time.getTime()+1000);
                        Log.d("DEBUG", "SCROLL UP");
                    }
                    timerTextView.setText(sdf.format(time));
                    prevY = event.getY();

                    // Restart the countdown timer to reflect the new time
                    countDownTimer.cancel();
                    setupTimer();
                }
                return true;
            }
        });
    }

    public static String millisecondsToTimeString(long milliseconds) {
        // Calculate seconds, minutes, and hours
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        // Format the time as "HH:MM:SS"
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }

    private void updateTimerText() {/*    int scrollDelta = (int) event.getY() - prevY;
                    timerMilliseconds += scrollDelta * 100;*/
        String time = timerTextView.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date t = sdf.parse(time);/*    int scrollDelta = (int) event.getY() - prevY;
                    timerMilliseconds += scrollDelta * 100;*/
            t.setTime(t.getTime() + 1000);
            timerTextView.setText(sdf.format(t));
            //Log.d("DEBUG", sdf.format(t));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        /*        timerTextView.setText("00:00:01");*/
    }

    private void update_time(boolean up) {
        String time = timerTextView.getText().toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date t = sdf.parse(time);
            if (up) {
                t.setTime(t.getTime() + 1000);
            } else {
                long newTime = t.getTime() - 1000;
                if (newTime >= 0) {
                    t.setTime(newTime);
                }
                Log.d("DEBUG", String.valueOf(t.getTime()));
            }
            timerTextView.setText(sdf.format(t));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}