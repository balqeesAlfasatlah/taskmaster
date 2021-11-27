package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

public class MainActivity2 extends AppCompatActivity {
//   private TaskRoomDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText editText1 = findViewById(R.id.editTitleId);
        EditText editText2 = findViewById(R.id.bodyId);
        EditText editText3 = findViewById(R.id.stateId2);



        Button submitted = findViewById(R.id.button3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Taskmaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editText1.getText().toString();
                String body = editText2.getText().toString();
                String state = editText3.getText().toString();

                Toast.makeText(getApplicationContext(),"Submittet",Toast.LENGTH_LONG).show();
               // startActivity(new Intent(MainActivity2.this,MainActivity.class));

                // builder method to create an object to take the user title , body,state (that entered from the form and pass them here)
                Task task = Task.builder().title(title).body(body).state(state).build();

                // mutate to write to the db (save the data)
                Amplify.API.mutate(
                        ModelMutation.create(task),
                        response -> Log.i("TaskMaster", "Added Task with id: " + response.getData().getId()),
                        error -> Log.e("TaskMaster", "Create failed", error)
                );
            }
        });

    }


}