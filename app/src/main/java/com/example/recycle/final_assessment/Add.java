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
import com.google.firebase.database.FirebaseDatabase;

public class Add extends Activity implements View.OnClickListener{
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    private Button AddBookBtn;
    private EditText TitleTxt, AuthorTxt, ConditionTxt, BorrowedTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        AddBookBtn = findViewById(R.id.AddBookBtn);
        AddBookBtn.setOnClickListener(this);

        TitleTxt = findViewById(R.id.TitleTxt);
        AuthorTxt = findViewById(R.id.AuthorTxt);
        ConditionTxt = findViewById(R.id.ConditionTxt);
        BorrowedTxt = findViewById(R.id.BorrowedTxt);


    }

    private void writeNewBook(String Title, String Author, String Condition, String Borrowed, String UID){
        Book book = new Book(Author, Borrowed, Condition, Title);
        // creates new book class and puts it in the db
        mDatabase.getReference("users").child(UID).child(Title).setValue(book);

    }

    @Override
    public void onClick(View v) {
        if(v == AddBookBtn){
            FirebaseUser user = mAuth.getCurrentUser();

            writeNewBook(TitleTxt.getText().toString(),
                    AuthorTxt.getText().toString(),
                    ConditionTxt.getText().toString(),
                    BorrowedTxt.getText().toString(),
                    user.getUid());
            Toast.makeText(this, "Book added to your profile", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Add.this, Home.class));
                return true;

            case R.id.addPage:
                startActivity(new Intent(Add.this, Add.class));
                return true;

            case R.id.updatePage:
                startActivity(new Intent(Add.this, Update.class));
                return true;

            case R.id.deletePage:
                startActivity(new Intent(Add.this, Delete.class));
                return true;

            case R.id.checkPage:
                startActivity(new Intent(Add.this, Check.class));
                return true;

            case R.id.logoutPage:
                startActivity(new Intent(Add.this, MainActivity.class));
                return true;

            default:
                return false;
        }

    }
}
