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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delete extends Activity implements View.OnClickListener{

    private Button deleteBtn;
    private EditText titleTxt;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance();

        titleTxt = findViewById(R.id.titleTxt);
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == deleteBtn){
            final String bookTitle = titleTxt.getText().toString();

            // We get the database reference according to the user's unique id
            // once we have that node, we only look inside that node (myRef)
            // so if the book is indeed inside the user's node, we delete it.
            // if the book is not inside the users node, or if it's in another user's node, we don't do anything
            final DatabaseReference myRef = mDatabase.getReference().child("users").child(UID).child(bookTitle);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String title = dataSnapshot.child("bookTitle").getValue(String.class);

                    if(title != null){

                        myRef.removeValue();

                        Toast.makeText(Delete.this, title + " has been deleted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Delete.this, "That book does not exist in your records", Toast.LENGTH_SHORT).show();

                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Delete.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });

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
                startActivity(new Intent(Delete.this, Home.class));
                return true;

            case R.id.addPage:
                startActivity(new Intent(Delete.this, Add.class));
                return true;

            case R.id.updatePage:
                startActivity(new Intent(Delete.this, Update.class));
                return true;

            case R.id.deletePage:
                startActivity(new Intent(Delete.this, Delete.class));
                return true;

            case R.id.checkPage:
                startActivity(new Intent(Delete.this, Check.class));
                return true;

            case R.id.logoutPage:
                startActivity(new Intent(Delete.this, MainActivity.class));
                return true;

            default:
                return false;
        }

    }
}
