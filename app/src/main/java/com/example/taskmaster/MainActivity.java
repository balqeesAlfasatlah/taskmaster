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
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String teamName = "";
    Button signIn;
    Button logout;

//    private TaskAdapter.RecyclerViewClickListener recyclerViewClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("TaskMaster", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("TaskMaster", "Could not initialize Amplify", error);
        }

       

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );
        //hard coded teams
//        Team team1 = Team.builder().name("team1").build();
//        Team team2 = Team.builder().name("team2").build();
//        Team team3 = Team.builder().name("team3").build();
//
//        Amplify.API.mutate(
//                ModelMutation.create(team1),
//                response -> Log.i("TaskMaster", "Added Todo with id: " + response.getData().getId()),
//                error -> Log.e("TaskMaster", "Create failed", error)
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(team2),
//                response -> Log.i("TaskMaster", "Added Todo with id: " + response.getData().getId()),
//                error -> Log.e("TaskMaster", "Create failed", error)
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(team3),
//                response -> Log.i("TaskMaster", "Added Todo with id: " + response.getData().getId()),
//                error -> Log.e("TaskMaster", "Create failed", error)
//        );



        Button goToActivityTwo = findViewById(R.id.addTaskBtn);
        Button goToActivityThree = findViewById(R.id.allTasksBtn);
        Button settingPage = findViewById(R.id.settingbtn);

        signIn = findViewById(R.id.signbtn);
        logout = findViewById(R.id.logoutbtn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signInWithWebUI(
                        MainActivity.this,
                        result -> Log.i("AuthQuickStart", result.toString()),
                        error -> Log.e("AuthQuickStart", error.toString())
                );

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );

            }
        });




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


        settingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSettingPage = new Intent(MainActivity.this, MainActivity5.class);
                startActivity(goToSettingPage);
            }
        });

    }





    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = sharedPreferences.getString("usernameInput", "Balqees");
        TextView textView = findViewById(R.id.userInput);
        textView.setText(name);

        Log.i("this is a test", "after getting name");


        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        teamName = sharedPreferences.getString("teamId", "");


        Log.i("this is a test", "after getting team "+teamName);


        if(! teamName.equals("")){
            Log.i("hello", teamName);
            RecyclerView recyclerView = findViewById(R.id.recycleId);
            List<Task> allTask = new ArrayList<Task>();

            recyclerView.setAdapter(new TaskAdapter(allTask));
            Handler newHandler=new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    recyclerView.getAdapter().notifyDataSetChanged();
                    return false;
                }
            });

            Log.i("MyAmplifyApp",teamName);


            List<Task> tasktoViewd=new ArrayList<>();
            Amplify.API.query(
                    ModelQuery.get(Team.class,teamName),
                    response -> {

                        Log.i("MyAmplifyApp",response.toString());
                        for (Task taskModel : response.getData().getTasks()) {
                            tasktoViewd.add(taskModel);
                            Log.i("taskkssss",taskModel.toString());
                        }
                        newHandler.sendEmptyMessage(3);

                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );


            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(new TaskAdapter(tasktoViewd));

            Amplify.Auth.fetchAuthSession(
                    result -> {
                        if (result.isSignedIn()){
                            signIn.setVisibility(View.INVISIBLE);
                        }
                        else logout.setVisibility(View.INVISIBLE);
                    },
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );
        }




    }
}