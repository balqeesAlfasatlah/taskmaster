package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.services.s3.util.Mimetypes;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;

import java.io.File;

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


        Intent intent = getIntent();

        //******************/
        ImageView storedFile = findViewById(R.id.retrivedFile);
        if (intent.getExtras().getString("taskFileKey") != null) {
            Amplify.Storage.downloadFile(

                    intent.getExtras().getString("taskFileKey"),
                    new File(getApplicationContext().getFilesDir() + "/" + intent.getExtras().getString("taskFileKey") + ".jpg"),
                    StorageDownloadFileOptions.defaultInstance(),

                    progress -> Log.i("TaskMaster", "Fraction completed: " + progress.getFractionCompleted()),

                    result -> {

                        final Mimetypes mimetypes = Mimetypes.getInstance();
                        String fileType = mimetypes.getMimetype(result.getFile());
                        Log.i("TaskMaster", "FileType: " + fileType);

//
                        if (fileType.startsWith("image")) {
                            Bitmap bitmap = BitmapFactory.decodeFile(result.getFile().getPath());
                            storedFile.setImageBitmap(bitmap);
                            Log.i("TaskMaster", "Successfully downloaded: " + result.getFile().getName());
                        }
                    },
                    error -> Log.e("TaskMaster", "Download Failure", error)
            );
        }

        //**************/

    }
}