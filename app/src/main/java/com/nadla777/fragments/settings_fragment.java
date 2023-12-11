package com.nadla777.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nadla777.R;

public class settings_fragment extends Fragment {

        public settings_fragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

            View view = inflater.inflate(R.layout.settings_fragment, container, false);

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });

            return view;
        }



}
