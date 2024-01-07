package com.nadla777;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nadla777.fragments.focus_fragment;
import com.nadla777.fragments.settings_fragment;
import com.nadla777.managers.FocusTimeManager;
import com.nadla777.managers.UserManager;

import org.json.JSONException;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private TextView welcome_text, name, points;

    private static final int RC_SIGN_IN = 77;
    private static boolean first_time = false;

    private Handler handler = new Handler(Looper.getMainLooper());
    //private FocusTimeManager t_manager;
    private StatsView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        welcome_text = findViewById(R.id.welcome);
        name = findViewById(R.id.name);

        points = findViewById(R.id.points);

        stats = findViewById(R.id.stats);

        Button login = findViewById(R.id.letsgo);
        UserManager u_manager = new UserManager(getBaseContext());
        //FocusTimeManager t_manager = new FocusTimeManager(R.layout.activity_main, R.layout.activity_main);
        //u_manager.clear();
        //u_manager.set_points(60);
        points.setText(String.valueOf(u_manager.get_points()));

        StatsView stats = findViewById(R.id.stats);
        stats.setActivated(true);

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

        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                u_manager.create_new_user(username.getText().toString());

                try {
                    show_welcome(u_manager.get_user_data().getString("username"));
                    username.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);

                    InputMethodManager input_manager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    input_manager.hideSoftInputFromWindow(requireViewById(R.id.fragment_container).getWindowToken(), 0);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void show_welcome(String username) {
        String welcome_msg = getString(R.string.welcome, username);
        welcome_text.setText(welcome_msg);
        welcome_text.setVisibility(View.VISIBLE);
        stats.setVisibility(View.VISIBLE);

        focus_fragment fragment = new focus_fragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fragment_container, fragment, "test")
                .addToBackStack(null)
                .commit();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                welcome_text.setVisibility(View.GONE);

                name.setText(username);
                name.setVisibility(View.VISIBLE);
                points.setVisibility(View.VISIBLE);

                Log.d("DEBUG_MAIN_ACTIVITY", "show username after welcome animation");
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentManager manager = getSupportFragmentManager();
        if(current instanceof focus_fragment){
            ((focus_fragment) current).onBackPressed();
        }else if(current instanceof settings_fragment) {
            manager.popBackStack();
        }else {
            moveTaskToBack(true);
        }
    }
}
