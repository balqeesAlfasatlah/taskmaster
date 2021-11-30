package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.amplifyframework.datastore.generated.model.Team;

import java.util.HashMap;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {
    String id;
    String teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Button save = findViewById(R.id.savebtn);
        Button home = findViewById(R.id.homebtn);

        RadioButton team1 = findViewById(R.id.team1);
        RadioButton team2 = findViewById(R.id.team2);
        RadioButton team3 = findViewById(R.id.team3);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToHomeIntent = new Intent(MainActivity5.this, MainActivity.class);
                startActivity(goToHomeIntent);

            }
        });


        //  sharedPreferencesEditor.putString("Team", id);


        Map<String, String> teamList = new HashMap<>();
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Team.class),
                response -> {
                    for (Team team : response.getData()) {

                        teamList.put(team.getName(), team.getId());

                    }
                },
                error -> Log.e("MasterTask", error.toString(), error)
        );

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity5.this);

                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.apply();
                Toast.makeText(getApplicationContext(), "submmited!", Toast.LENGTH_SHORT).show();

                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                int chosenButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton chosenButton = findViewById(chosenButtonId);


                String teamName = chosenButton.getText().toString();
                sharedPreferencesEditor.putString("teamName", teamName);
                sharedPreferencesEditor.putString("teamId", teamList.get(teamName));
                sharedPreferencesEditor.apply();
                Intent goHomePage = new Intent(MainActivity5.this, MainActivity.class);
                startActivity(goHomePage);

            }
        });
    }
}