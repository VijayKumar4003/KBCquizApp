package com.infowithvijay.triviaquizapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameWon extends AppCompatActivity {


    Button bt_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamewon);


        bt_ok = findViewById(R.id.bt_gameOver);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new  Intent(GameWon.this,PlayScreen.class);
                startActivity(intent);

                }
        });

    }
}
