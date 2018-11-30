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


    // nested database loop. It goes through each unique user id,
    // and within the user id, it goes through each book and checks if the borrower is equal
    // to the user-inputted name
    private void checkForBorrower(final String name){
        final Boolean[] wasBorrowed = {false};
        DatabaseReference rootRef = mDatabase.getReference().child("users");
        //loop 1
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot us : dataSnapshot.getChildren()) {
                    final String key = us.getKey();


                    //nested loop 2 start
                    DatabaseReference userRef = mDatabase.getReference().child("users").child(key);
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot book : dataSnapshot.getChildren()) {
                                String bookKey = book.getKey();
                                String bookBorrowed = book.child("bookBorrowed").getValue(String.class);
                                if(name.equals(bookBorrowed)){
                                    wasBorrowed[0] = true;
//                                    Toast.makeText(Check.this, wasBorrowed[0]+"", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Check.this, name+" has borrowed "+bookKey, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Check.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }); //nested loop 2 end

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Check.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }); //loop 1 end

        if(wasBorrowed[0] == false){
            Toast.makeText(this, wasBorrowed[0]+"", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {

        if(v == checkBtn){
            checkForBorrower(nameTxt.getText().toString());

            // if it doesn't find anything, it will proceed to this line
//            Toast.makeText(this, "User doesn't have anything checked out", Toast.LENGTH_SHORT).show();
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
