package com.example.taskmaster;
import com.amplifyframework.datastore.generated.model.Team;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    //   private TaskRoomDatabase db;
    //String teamName;
    private Intent chooseFile;
    private String fileName;
    private Uri fileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



//        RadioButton radioButton1 = findViewById(R.id.team1);
//        RadioButton radioButton2 = findViewById(R.id.team2);
//        RadioButton radioButton3 = findViewById(R.id.team3);


        Button submitted = findViewById(R.id.button3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Taskmaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText title = findViewById(R.id.editTitleId);
        EditText body = findViewById(R.id.bodyId);
        EditText state = findViewById(R.id.stateId2);

        //////////////////////////////////////////////////////////////////////////////////////

        Map<String, String> teamList = new HashMap<>();
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Team.class),
                response -> {

                    Log.i("response", response.toString());
                    for (Team oneTeam : response.getData()) {
                        teamList.put(oneTeam.getName(), oneTeam.getId());

                    }
                },
                error -> Log.e("TaskMaster", error.toString(), error)
        );

        Log.i("teamlist", teamList.toString());



        Button addFile = findViewById(R.id.uploadButton);
        addFile.setOnClickListener(v -> {

            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(chooseFile, 1234);

        });




        //**/
        submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fileData != null) {
                    try {
                        InputStream exampleInputStream = getContentResolver().openInputStream(fileData);
                        Amplify.Storage.uploadInputStream(
                                fileName,
                                exampleInputStream,
                                result -> Log.i("TaskMaster", "Successfully uploaded: " + result.getKey()),
                                storageFailure -> Log.e("TaskMaster", "Upload failed", storageFailure)
                        );
                    } catch (FileNotFoundException error) {
                        Log.e("TaskMaster", "Could not find file to open for input stream.", error);
                    }
                }
                //**************/

                RadioGroup radioGroup = findViewById(R.id.group2);
                int chosenButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton chosenButton = findViewById(chosenButtonId);
                String chosenTeam = chosenButton.getText().toString();


                Amplify.API.query(
                        ModelQuery.get(Team.class, teamList.get(chosenTeam)),
                        response1 -> {
                            Log.i("response1", response1.getData().toString());

                            Task task = Task.builder()
                                    .title(title.getText().toString())
                                    .body(body.getText().toString())
                                    .state(state.getText().toString())
                                    .teamId(response1.getData().getId())
                                    .fileKey(fileName)
                                    .build();

                            Amplify.API.mutate(
                                    ModelMutation.create(task),
                                    response3 -> Log.i("TaskMaster", "Added Task with id: " + response3.getData().getId()),
                                    error -> Log.e("TaskMaster", "Create failed", error));
                        }, error -> Log.e("TaskMaster", error.toString(), error)
                );

                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_SHORT).show();
                Intent goToHome = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(goToHome);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = new File(data.getData().getPath());
        fileName = file.getName();
        fileData = data.getData();
    }

}
