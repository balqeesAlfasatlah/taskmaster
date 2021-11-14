package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToActivityTwo = findViewById(R.id.addTaskBtn);
        goToActivityTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToActivityTwoIntent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(goToActivityTwoIntent);
            }
        });


        Button goToActivityThree = findViewById(R.id.allTasksBtn);
        goToActivityThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToActivityThreeIntent = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(goToActivityThreeIntent);
            }
        });

    }
}