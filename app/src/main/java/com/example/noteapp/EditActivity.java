package com.example.noteapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText pNameEt, pAgeEt, pPhoneEt;
    Button saveInfoBt;
    ActionBar actionBar;

    private String id, name, age, phone, addTimeStamp, updateTimeStamp;
    private boolean editMode = false;
    private DatabaseHelper dbHelper;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        pNameEt = findViewById(R.id.personName);
        pAgeEt = findViewById(R.id.personAge);
        pPhoneEt = findViewById(R.id.personPhone);

        saveInfoBt = findViewById(R.id.addButton);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode", editMode);
        id = intent.getStringExtra("ID");
        name = intent.getStringExtra("NAME");
        age = intent.getStringExtra("AGE");
        phone = intent.getStringExtra("PHONE");
        addTimeStamp = intent.getStringExtra("ADD_TIMESTAMP");
        updateTimeStamp = intent.getStringExtra("UPDATE_TIMESTAMP");

        if(editMode) {

            actionBar.setTitle("Update Information");

            editMode = intent.getBooleanExtra("editMode", editMode);
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            age = intent.getStringExtra("AGE");
            phone = intent.getStringExtra("PHONE");
            addTimeStamp = intent.getStringExtra("ADD_TIMESTAMP");
            updateTimeStamp = intent.getStringExtra("UPDATE_TIMESTAMP");

            pNameEt.setText(name);
            pAgeEt.setText(age);
            pPhoneEt.setText(phone);
        }else{

            actionBar.setTitle("Add Information");
        }

        //initiate database object
        dbHelper = new DatabaseHelper(this);

        saveInfoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                Toast.makeText(EditActivity.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        name = ""+pNameEt.getText().toString().trim();
        age = ""+pAgeEt.getText().toString().trim();
        phone = ""+pPhoneEt.getText().toString().trim();

        if(editMode){

            String newUpdateTime = ""+System.currentTimeMillis();

            dbHelper.updateInfo(""+id, ""+name,""+age,""+phone, ""+addTimeStamp, ""+newUpdateTime);
        }else{

            String timeStamp = ""+System.currentTimeMillis();

            dbHelper.insertInfo(""+name,
                    ""+age,
                    ""+phone,
                    ""+timeStamp,
                    ""+timeStamp);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //this function moves our addRecord activity to main activity when back button pressed
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}