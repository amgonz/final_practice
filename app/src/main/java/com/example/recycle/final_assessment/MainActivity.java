package com.example.recycle.final_assessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


// WHY A USER CANNOT DELETE BOOKS THAT DO NOT BELONG TO THEM
// The delete activity only looks for the book in the user's node. it does not look in
// other user's node. So We get their UID and pull the child node of that.. from there it searches through each book
// node for the title that the user wants deleted


//#################### DB STRUCTURE ##############################
//   Users : {
//              UID:{
//                      Book-title: {
//                                      bookAuthor: "",
//                                      bookBorrowed: "",
//                                      bookCondition: "",
//                                      bookTitle: ""
//                                  }
//                  }
//            }

public class MainActivity extends Activity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private Button LoginBtn, RegisterBtn;
    private EditText EmailTxt, PasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        EmailTxt = findViewById(R.id.EmailTxt);
        PasswordTxt = findViewById(R.id.PasswordTxt);


        LoginBtn = findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(this);

        RegisterBtn = findViewById(R.id.RegisterBtn);
        RegisterBtn.setOnClickListener(this);


    }

    private void registerUser(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Registration Success.",
                                    Toast.LENGTH_SHORT).show();




                            Intent i = new Intent(MainActivity.this,Home.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void loginUser(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                            // identify their account and update UI accordingly
                            Intent i = new Intent(MainActivity.this,Home.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {

        if(v == LoginBtn){

            loginUser(EmailTxt.getText().toString(), PasswordTxt.getText().toString());
            Toast.makeText(this, "Checking...", Toast.LENGTH_SHORT).show();
        }else if(v == RegisterBtn){
            if((PasswordTxt.getText().toString().length()) == 0 || (EmailTxt.getText().toString().length()) == 0) {

                Toast.makeText(this, "Enter your email and desired password to register", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show();
                registerUser(EmailTxt.getText().toString(), PasswordTxt.getText().toString());

            }
        }

    }
}
