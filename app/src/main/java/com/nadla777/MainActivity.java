package com.nadla777;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nadla777.fragments.focus_fragment;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

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
        Log.d("DEBUG", "START");
        focus_fragment fragment = new focus_fragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fragment_container, fragment, "test")
                .addToBackStack(null)
                .commit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_manager.create_new_user(username.getText().toString());

                try {
                    show_welcome(u_manager.get_user_data().getString("username"));
                    username.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                /*  u_manager.clear();*/



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
