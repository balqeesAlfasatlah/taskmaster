package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Taskmaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView editForTitle = findViewById(R.id.textView7);
        String editedText = getIntent().getExtras().get("task detail").toString();
        editForTitle.setText(editedText);

    }
}