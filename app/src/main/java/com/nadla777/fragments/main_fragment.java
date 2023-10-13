package com.nadla777.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nadla777.R;

public class main_fragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        view = inflater.inflate(R.layout.main_fragment, container, false);
        Log.d("fragmentTest", "fragment start");

        return view;
    }

}
