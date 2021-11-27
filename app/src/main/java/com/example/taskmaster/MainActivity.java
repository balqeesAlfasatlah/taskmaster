package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.service.controls.actions.ModeAction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



//    private TaskAdapter.RecyclerViewClickListener recyclerViewClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect with server
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("TaskMaster", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("TaskMaster", "Could not initialize Amplify", error);
        }






        Button goToActivityTwo = findViewById(R.id.addTaskBtn);
        Button goToActivityThree = findViewById(R.id.allTasksBtn);
//        Button task1 = findViewById(R.id.newTitle);
//        Button task2 = findViewById(R.id.task2btn);
//        Button task3 = findViewById(R.id.task3btn);
        Button settingPage = findViewById(R.id.settingbtn);

//        setOnClickListener();

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                allTasks = taskRoomDatabase.taskDao().getAll();
//                System.out.println(allTasks);
//                RecyclerView recyclerView = findViewById(R.id.recycleId);
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.setAdapter(new TaskAdapter(allTasks,recyclerViewClickListener));
//            }
//        });




        goToActivityTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToActivityTwoIntent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(goToActivityTwoIntent);
            }
        });

        goToActivityThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToActivityThreeIntent = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(goToActivityThreeIntent);
            }
        });

//        task1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent taskOneIntent = new Intent(MainActivity.this, MainActivity4.class);
//                taskOneIntent.putExtra("title", "Task1");
//                startActivity(taskOneIntent);
//            }
//        });
//
//        task2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent taskTwoIntent = new Intent(MainActivity.this, MainActivity4.class);
//                taskTwoIntent.putExtra("title", "Task2");
//                startActivity(taskTwoIntent);
//            }
//        });
//
//        task3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent taskThreeIntent = new Intent(MainActivity.this, MainActivity4.class);
//                taskThreeIntent.putExtra("title", "Task3");
//                startActivity(taskThreeIntent);
//            }
//        });

        settingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSettingPage = new Intent(MainActivity.this, MainActivity5.class);
                startActivity(goToSettingPage);
            }
        });



    }

//    private void setOnClickListener() {
//        recyclerViewClickListener = new TaskAdapter.RecyclerViewClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity4.class);
//               // intent.putExtra("title",allTasks.get(position).getTitle());
////                intent.putExtra("body",allTasks.get(position).getBody());
////                intent.putExtra("state",allTasks.get(position).getState());
//
//                startActivity(intent);
//            }
//        };


   // }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = sharedPreferences.getString("usernameInput", "Balqees");
        TextView textView = findViewById(R.id.userInput);
        textView.setText(name);



        RecyclerView recyclerView = findViewById(R.id.recycleId);

        // to display

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {

            @Override
            public boolean handleMessage(@NonNull Message message) {
                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });


        List<Task> allTask = new ArrayList<Task>();

        // to read from db, after the class we add coma and we write the condition)
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (Task task : response.getData()) {
                        allTask.add(task);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("TaskMaster", error.toString(), error)
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new TaskAdapter(allTask));


    }
}