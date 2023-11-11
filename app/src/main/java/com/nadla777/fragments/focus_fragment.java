package com.nadla777.fragments;

import android.annotation.SuppressLint;
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
import java.util.TimeZone;

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
        Button focus_stop = rootView.findViewById(R.id.stop_focus);
        Button focus_pause = rootView.findViewById(R.id.pause_focus);
        Button focus_resume = rootView.findViewById(R.id.resume_focus);

        View.OnClickListener buttonHandler = view -> {
            int id = view.getId();
            scrolling = false;
            Log.d("before button starrt", String.valueOf(get_time()));
            if (id == R.id.focus_up) {
                set_time(900000);
            } else if (id == R.id.focus_down) {
                set_time(-900000);
            } else if (id == R.id.start_focus && get_time() != 0){
                focus_start.setVisibility(View.GONE);
                focus_pause.setVisibility(View.VISIBLE);
                focus_stop.setVisibility(View.VISIBLE);
                startTimer(get_time());
                Log.d("BUTTON", "start focus with time" + get_time());
            } else if (id == R.id.stop_focus) {
                focus_start.setVisibility(View.VISIBLE);
                focus_pause.setVisibility(View.GONE);
                focus_stop.setVisibility(View.GONE);
                focus_resume.setVisibility(View.GONE);
                countDownTimer.cancel();
                timerTextView.setText("00:00:00");
                Log.d("BUTTON", "stop focus time");
            }else if (id == R.id.pause_focus){
                countDownTimer.cancel();
                focus_pause.setVisibility(View.GONE);
                focus_resume.setVisibility(View.VISIBLE);
                Log.d("BUTTON", "pause focus");
            } else if (id == R.id.resume_focus && countDownTimer != null) {
                startTimer(get_time());
                focus_pause.setVisibility(View.VISIBLE);
                focus_resume.setVisibility(View.GONE);
                Log.d("BUTTON", "resume focus with time" + get_time());
            }
        };

        focus_up.setOnClickListener(buttonHandler);
        focus_down.setOnClickListener(buttonHandler);
        focus_start.setOnClickListener(buttonHandler);
        focus_stop.setOnClickListener(buttonHandler);
        focus_pause.setOnClickListener(buttonHandler);
        focus_resume.setOnClickListener(buttonHandler);

        setupScrollBehavior(rootView);
        return rootView;
    }

    private void startTimer(long init_time) {
        countDownTimer = new CountDownTimer(init_time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("TIMER", "tick event with time " + millisUntilFinished);
                set_time(-1000);
            }

            @Override
            public void onFinish() {
                Log.d("TIMER", "FINISHED!");
            }
        };
        countDownTimer.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupScrollBehavior(View rootView) {
        ScrollView scrollView = rootView.findViewById(R.id.scroll);
        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                scrolling = true;
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE && scrolling) {
                int scrollDelta = 100;
                int new_time = 0;
                if (event.getY() > prevY) {
                    // Scrolling down
                    new_time = -1000;
                    Log.d("DEBUG", "SCROLL DOWN");
                } else {
                    // Scrolling up
                    new_time = 1000;
                    Log.d("DEBUG", "SCROLL UP");
                }
                set_time(new_time);
                prevY = event.getY();
            }
            return true;
        });
    }

    private void set_time(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timer_txt = timerTextView.getText().toString();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time;

        try {
            time = sdf.parse(timer_txt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        time.setTime(time.getTime() + n);
        timerTextView.setText(sdf.format(time));
    }

    private long get_time() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timer_txt = timerTextView.getText().toString();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time;
        try {
            time = sdf.parse(timer_txt);
            return time.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
