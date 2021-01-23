package com.infowithvijay.triviaquizapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MoneyActivity extends AppCompatActivity {


    TextView txt[] = new TextView[16];
    String money_won = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        set_All_TextViews();

        Intent intent = getIntent();

        int FLAG = intent.getIntExtra("MoneyContent",0);
        
        if (FLAG == 1){
            moneyIndicator(0);
            moneyIndicator2(1);
            money_won = "500";

        }else if (FLAG == 2){
            moneyIndicator(1);
            moneyIndicator2(2);
            money_won = "1,000";
        }else if (FLAG == 3){
            moneyIndicator(2);
            moneyIndicator2(3);
            money_won = "2,000";
        }else if (FLAG == 4){
            moneyIndicator(3);
            moneyIndicator2(4);
            money_won = "3,000";
        }else if (FLAG == 5){
            moneyIndicator(4);
            moneyIndicator2(5);
            money_won = "5,000";
        }else if (FLAG == 6){
            moneyIndicator(5);
            moneyIndicator2(6);
            money_won = "10,000";
        }else if (FLAG == 7){
            moneyIndicator(6);
            moneyIndicator2(7);
            money_won = "15,000";
        }else if (FLAG == 8){
            moneyIndicator(7);
            moneyIndicator2(8);
            money_won = "25,000";
        }else if (FLAG == 9){
            moneyIndicator(8);
            moneyIndicator2(9);
            money_won = "50,000";
        }else if (FLAG == 10){
            moneyIndicator(9);
            moneyIndicator2(10);
            money_won = "100,000";
        }else if (FLAG == 11){
            moneyIndicator(10);
            moneyIndicator2(11);
            money_won = "200,000";
        }else if (FLAG == 12){
            moneyIndicator(11);
            moneyIndicator2(12);
            money_won = "400,000";
        }else if (FLAG == 13){
            moneyIndicator(12);
            moneyIndicator2(13);
            money_won = "800,000";
        }else if (FLAG == 14){
            moneyIndicator(13);
            moneyIndicator2(14);
            money_won = "1,5000,000";
        }else if (FLAG == 15){
            moneyIndicator(14);
            moneyIndicator2(15);
            money_won = "3,0000,000";
        }




    }


    private void GoToQuiz() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                goToPreviousActivity();

            }
        },3000);

    }

    private void goToPreviousActivity() {

        Intent intent = new Intent(MoneyActivity.this,QuizActivity.class);
        //intent.putExtra("MoneyWon",money_won);  //  up to you
        startActivity(intent);

    }


    private void set_All_TextViews() {

        txt[0] = findViewById(R.id.txt);
        txt[1] = findViewById(R.id.txt0);
        txt[2] = findViewById(R.id.txt1);
        txt[3] = findViewById(R.id.txt2);
        txt[4] = findViewById(R.id.txt3);
        txt[5] = findViewById(R.id.txt4);
        txt[6] = findViewById(R.id.txt5);
        txt[7] = findViewById(R.id.txt6);
        txt[8] = findViewById(R.id.txt7);
        txt[9] = findViewById(R.id.txt8);
        txt[10] = findViewById(R.id.txt9);
        txt[11] = findViewById(R.id.txt10);
        txt[12] = findViewById(R.id.txt11);
        txt[13] = findViewById(R.id.txt12);
        txt[14] = findViewById(R.id.txt13);
        txt[15] = findViewById(R.id.txt14);


        txt[15].bringToFront();
        txt[14].bringToFront();
        txt[13].bringToFront();
        txt[12].bringToFront();
        txt[11].bringToFront();
        txt[10].bringToFront();
        txt[9].bringToFront();
        txt[8].bringToFront();
        txt[7].bringToFront();
        txt[6].bringToFront();
        txt[5].bringToFront();
        txt[4].bringToFront();
        txt[3].bringToFront();
        txt[2].bringToFront();
        txt[1].bringToFront();
        txt[0].bringToFront();



    }

    private void moneyIndicator(int number){

        Drawable firstDrawable = getDrawable(R.drawable.new_state);
        Drawable secondDrawable = getDrawable(R.drawable.original_state);

        TransitionDrawable transition = new TransitionDrawable(new Drawable[]{
                firstDrawable,secondDrawable
        });

        txt[number].setBackground(transition);
        transition.startTransition(1000);

    }


    private void moneyIndicator2(int number){

        Drawable firstDrawable = getDrawable(R.drawable.new_state);
        Drawable secondDrawable = getDrawable(R.drawable.original_state);

        TransitionDrawable transition = new TransitionDrawable(new Drawable[]{
                firstDrawable,secondDrawable
        });

        txt[number].setBackground(transition);
        transition.startTransition(1000);
        transition.reverseTransition(1000);

        GoToQuiz();
    }


}
