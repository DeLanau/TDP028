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

public class focus_fragment extends Fragment {

    private TextView timerTextView;
    private long timerMilliseconds = 0;
    private CountDownTimer countDownTimer;

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
                updateTimerText();
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
                    int scrollDelta = (int) event.getY();
                    timerMilliseconds += scrollDelta * 100;
                    updateTimerText();
                    countDownTimer.cancel();
                    setupTimer();
                }
                return true;
            }
        });
    }

    private void updateTimerText() {
        timerTextView.setText("00:00:01");
    }
}