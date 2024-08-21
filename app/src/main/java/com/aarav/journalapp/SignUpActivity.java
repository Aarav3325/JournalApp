package com.aarav.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Field;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameCreate, emailCreate, passwordCreate;
    TextView tvSignIn;
    private Button createAccountBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userID;
    String id;
    static String usernameAdd;

    private CollectionReference collectionReference = db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameCreate = findViewById(R.id.usernameCreate);
        emailCreate = findViewById(R.id.emailCreate);
        passwordCreate = findViewById(R.id.passwordCreate);
        tvSignIn = findViewById(R.id.tvSignIn);
        createAccountBtn = findViewById(R.id.email_signUp);

        firebaseAuth = FirebaseAuth.getInstance();

        usernameAdd = usernameCreate.getText().toString();



        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserEmailAccount();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });


    }

    public void createUserEmailAccount(){
        String username = usernameCreate.getText().toString();
        String email = emailCreate.getText().toString();
        String password = passwordCreate.getText().toString();



        if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(username, email);
                        collectionReference.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                id = documentReference.getId();
                                Intent i = new Intent(SignUpActivity.this, JournalListActivity.class);
                                i.putExtra("username", username);
                                startActivity(i);
                                finish();
                                Toast.makeText(SignUpActivity.this, "Welcome " + username + " ", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }

    private void setUsernameCreate(){
        firebaseAuth.getCurrentUser().getUid();
    }
}