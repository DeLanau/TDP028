package com.nadla777;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DocumentReference firestore = FirebaseFirestore.getInstance().document("data/info");;
    private EditText mail, password;

    private static final int RC_SIGN_IN = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);

        ImageButton login = findViewById(R.id.letsgo);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("createUser", mail.getText().toString() + " " + password.getText().toString());

                try {
                    create_local_user(mail.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

             /*   main_fragment  fragment = new main_fragment();
                FragmentManager manager = getSupportFragmentManager();

                manager.beginTransaction().add(R.id.fragment_container, fragment, "test")
                        .addToBackStack(null)
                        .commit();*/

            }
        });

        //login.setOnClickListener(view -> startNewActivity());
    }

    private void create_local_user(String username) throws JSONException, IOException {
        Log.d("TEST", "IN creaste local user");
        JSONObject obj = new JSONObject();
        obj.put("Username", username);

        String obj_s = obj.toString();

        File f = new File(this.getFilesDir(), "local_user");
        FileWriter fileWriter = new FileWriter(f);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(obj_s);
        bufferedWriter.close();
        Log.d("TEST", "done with writing, saved at " + this.getFilesDir().toString());
    }

   private void check_user_exists() {

   }

    //Log in Activity
    private void startNewActivity() {
        Intent in = new Intent(this, LoggedinActivity.class);
        in.putExtra("mail", mail.getText().toString());
        in.putExtra("password", password.getText().toString());
        startActivity(in);
    }
}
