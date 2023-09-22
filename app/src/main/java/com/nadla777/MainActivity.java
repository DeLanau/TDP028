package com.nadla777;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

import org.w3c.dom.Document;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DocumentReference firestore = FirebaseFirestore.getInstance().document("data/info");;
    private EditText mail, password;

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
                startNewActivity();

                createUser(mail.getText().toString(), password.getText().toString());
            }
        });

        //login.setOnClickListener(view -> startNewActivity());
    }
    private void createUser(String mail, String password) {
        firestore.collection("users")
                .document(mail) // Use the email as the document ID
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // User with the same email already exists
                                Log.d("createUser", "User with this email already exists.");
                            } else {
                                // User with the same email does not exist, create a new user
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", mail);
                                user.put("password", password);

                                firestore.collection("users")
                                        .document(mail) // Use the email as the document ID
                                        .set(user) // Set data in the document
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("createUser", "User successfully added to Firestore!");
                                                } else {
                                                    Log.e("createUser", "Error adding user to Firestore", task.getException());
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.e("createUser", "Error checking for existing user", task.getException());
                        }
                    }
                });
    }

    //Log in Activity
    private void startNewActivity() {
        Intent in = new Intent(this, LoggedinActivity.class);
        in.putExtra("mail", mail.getText().toString());
        in.putExtra("password", password.getText().toString());
        startActivity(in);
    }
}
