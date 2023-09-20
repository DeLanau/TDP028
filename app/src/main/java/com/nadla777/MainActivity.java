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
                //comment createUser, massa problem med testing/emulering av firestore db osv. Just nu skapas 1 account och lagras lokalt.
                //createUser(mail.getText().toString(), password.getText().toString());
            }
        });

        //login.setOnClickListener(view -> startNewActivity());

        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_mail = mail.getText().toString();
                String str_password = password.getText().toString();
                check_user(str_mail, str_password);
            }
        });*/
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

    /*private void check_user(String mail, String password) {
        auth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    boolean check_new_user = task.getResult().getSignInMethods().isEmpty();
                    if (check_new_user) {
                        createUser(mail, password);
                    } else {
                        loginUser(mail, password);
                    }
                    startNewActivity();
                }else {
                    Log.e("Sign_Login_in_main_activity", "Error while checking user existence", task.getException());
                }
            }
        });
    }

    private void createUser(String mail, String password) {
        auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Sign_Login_in_main_activity", "New user successful created!");
                } else {
                    Log.e("Sign_Login_in_main_activity", "Error while creating new user", task.getException());
                }
            }
        });
    }

    private void loginUser(String mail, String password) {
        auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Sign_Login_in_main_activity", "Old user successful sign in!");
                } else {
                    Log.e("Sign_Login_in_main_activity", "Error while sign in old user", task.getException());
                }
            }
        });
    }*/

    //Log in Activity
    private void startNewActivity() {
        Intent in = new Intent(this, LoggedinActivity.class);
        in.putExtra("mail", mail.getText().toString());
        in.putExtra("password", password.getText().toString());
        startActivity(in);
    }
}