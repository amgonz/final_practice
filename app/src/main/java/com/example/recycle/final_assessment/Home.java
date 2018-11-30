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

public class Home extends Activity implements View.OnClickListener{

    private Button AddPageBtn, UpdatePageBtn, DeletePageBtn, CheckPageBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        AddPageBtn = findViewById(R.id.AddPageBtn);
        AddPageBtn.setOnClickListener(this);

        UpdatePageBtn = findViewById(R.id.UpdatePageBtn);
        UpdatePageBtn.setOnClickListener(this);

        DeletePageBtn = findViewById(R.id.DeletePageBtn);
        DeletePageBtn.setOnClickListener(this);

        CheckPageBtn = findViewById(R.id.CheckPageBtn);
        CheckPageBtn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if(v == AddPageBtn){
            startActivity(new Intent(Home.this, Add.class));

        }
        if(v == UpdatePageBtn){
            startActivity(new Intent(Home.this, Update.class));

        }
        if(v == CheckPageBtn){
            startActivity(new Intent(Home.this, Check.class));

        }
        if(v == DeletePageBtn){
            startActivity(new Intent(Home.this, Delete.class));

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
                startActivity(new Intent(Home.this, Home.class));
                return true;

            case R.id.addPage:
                startActivity(new Intent(Home.this, Add.class));
                return true;

            case R.id.updatePage:
                startActivity(new Intent(Home.this, Update.class));
                return true;

            case R.id.deletePage:
                startActivity(new Intent(Home.this, Delete.class));
                return true;

            case R.id.checkPage:
                startActivity(new Intent(Home.this, Check.class));
                return true;

            case R.id.logoutPage:
                startActivity(new Intent(Home.this, MainActivity.class));
                return true;

            default:
                return false;
        }

    }

}
