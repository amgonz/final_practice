package com.example.recycle.final_assessment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class Check extends Activity implements View.OnClickListener{
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private Button checkBtn;
    private EditText nameTxt;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance();

        checkBtn = findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(this);

        nameTxt = findViewById(R.id.nameTxt);

    }


    // Checks for books by looking at UID,
    // and within the user id, it goes through each book and checks if the borrower is equal
    // to what the user submitted
    //NOTE
    // This only looks within the user's list of books they've entered in. It does not look
    // in other user's profile- only the books they're submitted in their own profile.
    // So if jimmy borrowed my book Moby Dick, I'd enter in that information, and when i check "jimmy",
    // the app will only look within my user ID.

    private void checkForBorrower(final String name){
        DatabaseReference rootRef = mDatabase.getReference().child("users").child(UID);
        //loop 1
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot bk : dataSnapshot.getChildren()) {
                        System.out.println("======================");
                        String borrower = bk.child("bookBorrowed").getValue().toString();
                        String title = bk.child("bookTitle").getValue().toString();
                        System.out.println(borrower.toUpperCase());
                        System.out.println(name.toUpperCase());
                        if(borrower.toUpperCase().equals(name.toUpperCase())){
                            Toast.makeText(Check.this, borrower+" has borrowed "+title, Toast.LENGTH_SHORT).show();

                        }

                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Check.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {

        if(v == checkBtn){
            checkForBorrower(nameTxt.getText().toString());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater optionsMenuInflator = getMenuInflater();
        optionsMenuInflator.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.homePage:
                startActivity(new Intent(Check.this, Home.class));
                return true;

            case R.id.addPage:
                startActivity(new Intent(Check.this, Add.class));
                return true;

            case R.id.updatePage:
                startActivity(new Intent(Check.this, Update.class));
                return true;

            case R.id.deletePage:
                startActivity(new Intent(Check.this, Delete.class));
                return true;

            case R.id.checkPage:
                startActivity(new Intent(Check.this, Check.class));
                return true;

            case R.id.logoutPage:
                startActivity(new Intent(Check.this, MainActivity.class));
                return true;

            default:
                return false;
        }

    }
}
