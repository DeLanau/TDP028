package com.nadla777.fragments;

import android.annotation.SuppressLint;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nadla777.MainActivity;
import com.nadla777.R;

import java.util.Locale;

public class settings_fragment extends Fragment {

    private boolean dark_theme = false;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        View rootview = inflater.inflate(R.layout.settings_fragment, container, false);
        Button button = rootview.findViewById(R.id.settings_inv);
        Button theme = rootview.findViewById(R.id.theme);
        Button lang_se = rootview.findViewById(R.id.lang_se);
        Button lang_en = rootview.findViewById(R.id.lang_en);
           /* getActivity().getWindow().setBackgroundBlurRadius(8);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);*/

        View.OnClickListener buttonHandler = view -> {
            int id = view.getId();
            if (id == R.id.settings_inv){
                getActivity().getSupportFragmentManager().popBackStack();
                Log.d("settings fragment", "settings inv");
            }else if (id == R.id.theme) {
                Log.d("settings fragment", "theme");
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (currentNightMode == Configuration.UI_MODE_NIGHT_NO || currentNightMode == Configuration.UI_MODE_NIGHT_UNDEFINED) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Log.d("settings fragment", "theme yes");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Log.d("settings fragment", "theme no");
                }
                recrateApp();
            }else if (id == R.id.lang_se) {
                setLocale("se");
                Log.d("settings fragment", "lang se");
            }else if (id == R.id.lang_en) {
                setLocale("en");
                Log.d("settings fragment", "lang en");
            }
        };

        button.setOnClickListener(buttonHandler);
        theme.setOnClickListener(buttonHandler);
        lang_se.setOnClickListener(buttonHandler);
        lang_en.setOnClickListener(buttonHandler);
        rootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return rootview;
    }

    private void recrateApp()
    {
        TaskStackBuilder.create(getActivity())
                .addNextIntent(new Intent(getActivity(), MainActivity.class))
                .addNextIntent(getActivity().getIntent())
                .startActivities();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = getResources().getConfiguration();

        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        recrateApp();
    }

}
