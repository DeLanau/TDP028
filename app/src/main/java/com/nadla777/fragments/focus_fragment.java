package com.nadla777.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nadla777.R;
import com.nadla777.StatsView;
import com.nadla777.managers.FocusTimeManager;
import com.nadla777.managers.UserManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class focus_fragment extends Fragment {

    private TextView timerTextView, points;
    private long timerMilliseconds = 0;
    private CountDownTimer countDownTimer;

    private Button focus_start, focus_stop, focus_pause, focus_resume, settings;

    private float prevY;
    private boolean scrolling = false;

    private long time_holder;

    private boolean paused = false;

    private FocusTimeManager t_manager;
    private UserManager u_manager;

    private StatsView update_stats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.focus_fragment, container, false);
        timerTextView = rootView.findViewById(R.id.focus_timer);

        t_manager = new FocusTimeManager(rootView, timerTextView);
        u_manager = new UserManager(getContext());

        update_stats = getActivity().findViewById(R.id.stats);

        ImageButton focus_up = rootView.findViewById(R.id.focus_up);
        ImageButton focus_down = rootView.findViewById(R.id.focus_down);
        focus_start = rootView.findViewById(R.id.start_focus);
        focus_stop = rootView.findViewById(R.id.stop_focus);
        focus_pause = rootView.findViewById(R.id.pause_focus);
        focus_resume = rootView.findViewById(R.id.resume_focus);

        points = getActivity().findViewById(R.id.points);

        settings = getActivity().findViewById(R.id.settings);
        settings.setVisibility(View.VISIBLE);

        View.OnClickListener buttonHandler = view -> {
            int id = view.getId();
            scrolling = false;
            Log.d("before button starrt", String.valueOf(t_manager.get_time()));
            if (id == R.id.focus_up) {
                t_manager.update_time(900000);
            } else if (id == R.id.focus_down) {
                t_manager.update_time(-900000);
            } else if (id == R.id.start_focus && t_manager.get_time() != 0){
                focus_start.setVisibility(View.GONE);
                focus_pause.setVisibility(View.VISIBLE);
                focus_stop.setVisibility(View.VISIBLE);
                time_holder = t_manager.get_time();
                startTimer(t_manager.get_time());
                Log.d("BUTTON", "start focus with time" + t_manager.get_time());
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
                paused = true;
                Log.d("BUTTON", "pause focus");

            } else if (id == R.id.resume_focus && countDownTimer != null) {
                startTimer(t_manager.get_time());
                focus_pause.setVisibility(View.VISIBLE);
                focus_resume.setVisibility(View.GONE);
                Log.d("BUTTON", "resume focus with time" + t_manager.get_time());
            }else if(id == R.id.settings) {
                handleSettings();
                Log.d("Button", "Settings");
            }else if (id == R.id.points && u_manager.get_points() >= 60) {
                hanldePoints();
            }
        };

        focus_up.setOnClickListener(buttonHandler);
        focus_down.setOnClickListener(buttonHandler);
        focus_start.setOnClickListener(buttonHandler);
        focus_stop.setOnClickListener(buttonHandler);
        focus_pause.setOnClickListener(buttonHandler);
        focus_resume.setOnClickListener(buttonHandler);
        settings.setOnClickListener(buttonHandler);
        points.setOnClickListener(buttonHandler);

        setupScrollBehavior(rootView);
        return rootView;
    }

    private void startTimer(long init_time) {
        countDownTimer = new CountDownTimer(init_time, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("TIMER", "tick event with time " + millisUntilFinished);
                t_manager.update_time(-1000);
                if(t_manager.get_time() == 0) //double check, sometime miss
                    countDownTimer.onFinish();
            }

            @Override
            public void onFinish() {
                Log.d("TIMER", "FINISHED!");
                Toast.makeText(getContext(), "Focus finished!", Toast.LENGTH_SHORT).show();
                focus_start.setVisibility(View.VISIBLE);
                focus_pause.setVisibility(View.GONE);
                focus_stop.setVisibility(View.GONE);
                focus_resume.setVisibility(View.GONE);

                u_manager.add_value(time_holder);
                TextView points = getActivity().findViewById(R.id.points);
                points.setText(String.valueOf(u_manager.get_points()));
                update_stats.invalidate();

                countDownTimer.cancel();
                timerTextView.setText("00:00:00");
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
                t_manager.update_time(new_time);
                prevY = event.getY();
            }
            return true;
        });
    }

    public void onBackPressed() {
        if(t_manager.get_time() == 0 || paused)
            requireActivity().moveTaskToBack(true);
    }

    private void handleSettings() {
        settings_fragment fragment = new settings_fragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "test")
                .addToBackStack(null)
                .commit();
    }

    private AlertDialog pointsDialog;
    private List<Integer> strings_resources = new ArrayList<>();
    private void hanldePoints(){

        if (pointsDialog != null && pointsDialog.isShowing()) {
            // Dialog is already showing, do not create a new one
            return;
        }

        strings_resources.add(R.string.string1);
        strings_resources.add(R.string.string2);
        strings_resources.add(R.string.string3);
        Collections.shuffle(strings_resources);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 'this' refers to your Activity or Fragment context

        builder.setTitle(R.string.challange);

        int randomChallange = new Random().nextInt(strings_resources.size());
        int randomTime = (int) (Math.random() * ((60 - 15) / 5 + 1)) * 5 + 15;

        String text = getString(strings_resources.get(randomChallange), randomTime);

        builder.setMessage(text);

        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                u_manager.set_points(-60);
                points.setText(u_manager.get_points());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.decline, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        pointsDialog = builder.create();
        pointsDialog.show();
    }
}
