package com.infowithvijay.triviaquizapp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.infowithvijay.triviaquizapp2.AppController.StopSound;

public class PlayScreen extends AppCompatActivity implements View.OnClickListener {

    Button btPlayQuiz,btSettings;

    public static Context context;

    Animation rotate_anti_clock_wise,rotate_clock_wise;
    ImageView imgOuterView,imgInnerView;

    long backpressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);


        btPlayQuiz = findViewById(R.id.bt_playQuiz);
        btSettings = findViewById(R.id.bt_settings);
        imgInnerView = findViewById(R.id.imgInnerView);
        imgOuterView = findViewById(R.id.imgOuterView);

        rotate_anti_clock_wise = AnimationUtils.loadAnimation(this,R.anim.rotateanticlockwise);
        rotate_clock_wise = AnimationUtils.loadAnimation(this,R.anim.rotateclockwise);


        imgOuterView.startAnimation(rotate_anti_clock_wise);
        imgInnerView.startAnimation(rotate_clock_wise);


        btSettings.setOnClickListener(this);
        btPlayQuiz.setOnClickListener(this);





        context = getApplicationContext();
        AppController.currentActivity = this;
        if (SettingsPreferences.getMusicEnableDisable(context)){
            try {

                AppController.playMusic();

            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }

   }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.bt_playQuiz:


                Intent playIntent = new Intent(PlayScreen.this,QuizActivity.class);
                startActivity(playIntent);


                break;

            case R.id.bt_settings:
                Intent settingIntent = new Intent(PlayScreen.this,Settings.class);
                startActivity(settingIntent);
                break;

        }

    }

    @Override
    public void onBackPressed() {

        StopSound();

        if (backpressedTime + 2000 > System.currentTimeMillis()){

            new AlertDialog.Builder(this)
                    .setTitle("Do you want to exit ?")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            setResult(RESULT_OK,new Intent().putExtra("Exit",true));
                            onDestroy();
                            finish();

                        }
                    }).create().show();
        }else {
            Toast.makeText(context, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }


        backpressedTime = System.currentTimeMillis();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
