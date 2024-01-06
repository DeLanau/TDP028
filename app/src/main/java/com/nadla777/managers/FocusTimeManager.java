package com.nadla777.managers;

import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FocusTimeManager {

    private View view;
    private TextView timerTextView;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private Date current_timer;

    public FocusTimeManager(View view, TextView textView) {
        this.view = view;
        this.timerTextView = textView;
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    //update time value and visualy.
    public void update_time(int n) {
        try {
            current_timer = sdf.parse(timerTextView.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        current_timer.setTime(current_timer.getTime() + n);
        timerTextView.setText(sdf.format(current_timer));
    }

    //get current time value as long
    public long get_time() {
        try {
            current_timer = sdf.parse(timerTextView.getText().toString());
            return current_timer.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
