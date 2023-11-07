package com.nadla777.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
    private boolean scrolling = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.focus_fragment, container, false);
        timerTextView = rootView.findViewById(R.id.focus_timer);

        ImageButton focus_up = rootView.findViewById(R.id.focus_up);
        ImageButton focus_down = rootView.findViewById(R.id.focus_down);
        Button focus_start = rootView.findViewById(R.id.start_focus);

        View.OnClickListener buttonHandler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                scrolling = false;
                Log.d("DEBUG BUTTONS", "Touch event");
                if (id == R.id.focus_up) {
                    Log.d("DEBUG BUTTONS", "Focus up pressed");
                } else if (id == R.id.focus_down) {
                    Log.d("DEBUG BUTTONS", "Focus down pressed");
                } else {
                    Log.d("DEBUG BUTTONS", "No button pressed");
                }
            }
        };

        focus_up.setOnClickListener(buttonHandler);
        focus_down.setOnClickListener(buttonHandler);

        setupTimer();
        setupScrollBehavior(rootView);
        return rootView;
    }

    private void setupTimer() {
        countDownTimer = new CountDownTimer(timerMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerMilliseconds = millisUntilFinished;
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
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scrolling = true;
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE && scrolling) {
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
                        time.setTime(time.getTime() + 1000);
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
}
