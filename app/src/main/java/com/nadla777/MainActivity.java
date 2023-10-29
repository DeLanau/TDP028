package com.nadla777;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nadla777.fragments.main_fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DocumentReference firestore = FirebaseFirestore.getInstance().document("data/info");;
    private EditText username;
    private TextView welcome_text, name;

    private static final int RC_SIGN_IN = 77;
    private static boolean first_time = false;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        welcome_text = findViewById(R.id.welcome);
        name = findViewById(R.id.name);

        ImageButton login = findViewById(R.id.letsgo);
        UserManager u_manager = new UserManager(getBaseContext());

        if (u_manager.get_user_data() == null) {
            username.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        }else{
            username.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            try {
                show_welcome(u_manager.get_user_data().getString("username"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_manager.create_new_user(username.getText().toString());

                try {
                    show_welcome(u_manager.get_user_data().getString("username"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                /*  u_manager.clear();*/

                /*   main_fragment  fragment = new main_fragment();
                FragmentManager manager = getSupportFragmentManager();

                manager.beginTransaction().add(R.id.fragment_container, fragment, "test")
                        .addToBackStack(null)
                        .commit();*/

            }
        });

        //login.setOnClickListener(view -> startNewActivity());
    }

    private void show_welcome(String username) {
        String welcome_msg = getString(R.string.welcome, username);
        welcome_text.setText(welcome_msg);
        welcome_text.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcome_text.setVisibility(View.GONE);

                name.setText(username);
                name.setVisibility(View.VISIBLE);
            }
        }, 5000);

    }

/*    private void startNewActivity() {
        Intent in = new Intent(this, LoggedinActivity.class);
        in.putExtra("mail", mail.getText().toString());
        in.putExtra("password", password.getText().toString());
        startActivity(in);
    }*/
}
