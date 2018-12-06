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

public class Update extends Activity implements View.OnClickListener {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    private Button UpdateBookBtn;
    private EditText TitleTxt, AuthorTxt, ConditionTxt, BorrowedTxt;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance();

        UpdateBookBtn = findViewById(R.id.updateBookBtn);
        UpdateBookBtn.setOnClickListener(this);

        TitleTxt = findViewById(R.id.titleTxt);
        AuthorTxt = findViewById(R.id.authorTxt);
        ConditionTxt = findViewById(R.id.conditionTxt);
        BorrowedTxt = findViewById(R.id.borrowedTxt);

    }

    private void writeNewBook(String Title, String Author, String Condition, String Borrowed, String UID){
        //creates book class based off new user-inputted updates
        Book book = new Book(Author, Borrowed, Condition, Title);
        mDatabase.getReference("users").child(UID).child(Title).setValue(book);

    }

    @Override
    public void onClick(View v) {
        if(v == UpdateBookBtn){


            String Title = TitleTxt.getText().toString();
            DatabaseReference myRef = mDatabase.getReference("users").child(UID).child(Title);


            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String bookTitle = dataSnapshot.child("bookTitle").getValue(String.class);
                    String bookAuthor = dataSnapshot.child("bookAuthor").getValue(String.class);

                    if(bookTitle != null){ //if the book they are inquireing is in the user's checked-out book log,
                        //Update it according to new values
                        writeNewBook(bookTitle, //old value
                                bookAuthor, //old value
                                ConditionTxt.getText().toString(), //new value
                                BorrowedTxt.getText().toString(), //new value
                                UID);
                        Toast.makeText(Update.this, bookTitle + "'s Condition and Borrower has been updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Update.this, "Book title and author combination not found in your records", Toast.LENGTH_SHORT).show();

                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Update.this, "Error", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Update.this, Home.class));
                return true;

            case R.id.addPage:
                startActivity(new Intent(Update.this, Add.class));
                return true;

            case R.id.updatePage:
                startActivity(new Intent(Update.this, Update.class));
                return true;

            case R.id.deletePage:
                startActivity(new Intent(Update.this, Delete.class));
                return true;

            case R.id.checkPage:
                startActivity(new Intent(Update.this, Check.class));
                return true;

            case R.id.logoutPage:
                startActivity(new Intent(Update.this, MainActivity.class));
                return true;

            default:
                return false;
        }

    }
}
