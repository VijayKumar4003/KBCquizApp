package com.infowithvijay.triviaquizapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {


    Button buttonA,buttonB,buttonC,buttonD;
    TextView questionText,txtTotalQuesText;

    TriviaQuizHelper triviaQuizHelper;
    TriviaQuestion currentQuestion;
    List<TriviaQuestion> list;
    int qid = 1;
    int sizeofQuiz = 2; // total size of Quiz

    private final Handler handler = new Handler();
    private final Handler handler2 = new Handler();
    private final Handler handler3 = new Handler();

    AnimationDrawable anim;
    Animation correctAnsAnimation;
    Animation wrongAnsAnimation;

    int MUSIC_FLAG = -1;
    PlayAudio playAudio;


    int FLAG = 0;
    String moneyWon = "0";
    TextView txtMoneyWon;
    Handler moneyHandler = new Handler();

    String correctAns = "";

    private long backPressedTime;

    ImageView imgFiftyFifty,imgSkippable,imgAudienceVote,imgTelephone,imgHome;
    int MAX_OUT;
    int RANGE;

    Dialog dialog,dialog2;
    Button btAudienceOk,btTelePhoneOk;

    TakeMoneyDialog takeMoneyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.txtTriviaQuestion);
        txtTotalQuesText = findViewById(R.id.txtTotalQuestion);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        imgAudienceVote = findViewById(R.id.img_audience_vote);
        imgTelephone = findViewById(R.id.img_telephone);
        imgFiftyFifty = findViewById(R.id.img_fifty_fifty);
        imgSkippable = findViewById(R.id.img_skipable);
        txtMoneyWon = findViewById(R.id.txtMoneyWon);
        imgHome = findViewById(R.id.imghome);


        /// setting the listeners

        imgSkippable.setOnClickListener(this);
        imgFiftyFifty.setOnClickListener(this);
        imgTelephone.setOnClickListener(this);
        imgAudienceVote.setOnClickListener(this);
        imgHome.setOnClickListener(this);


        dialog = new Dialog(this);
        dialog2 = new Dialog(this);
        takeMoneyDialog = new TakeMoneyDialog(this);


        correctAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.correct_ans_animation);
        correctAnsAnimation.setRepeatCount(3);

        wrongAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.wrong_ans_animation);
        wrongAnsAnimation.setRepeatCount(3);

        playAudio = new PlayAudio(this);

        triviaQuizHelper = new TriviaQuizHelper(this);

        triviaQuizHelper.getReadableDatabase();

        list = triviaQuizHelper.getAllQuestions();




        Collections.shuffle(list);

        currentQuestion = list.get(qid);

        txtTotalQuesText.setText(qid + "/" + sizeofQuiz);

        updateQueAnsOptions();


    }

    private void updateQueAnsOptions() {

        buttonA.setBackgroundResource(R.drawable.default_option_a);
        buttonB.setBackgroundResource(R.drawable.default_option_b);
        buttonC.setBackgroundResource(R.drawable.default_option_c);
        buttonD.setBackgroundResource(R.drawable.default_option_d);


        questionText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOption1());
        buttonB.setText(currentQuestion.getOption2());
        buttonC.setText(currentQuestion.getOption3());
        buttonD.setText(currentQuestion.getOption4());


    }


    private void SetNewQuestion(){

        qid++;

        txtTotalQuesText.setText(qid + "/" + sizeofQuiz);

        currentQuestion = list.get(qid);

        enableOptions();

        displayOptionsAgain();

        updateQueAnsOptions();
    }




    public void buttonA(View view) {



        disableOptions();

        buttonA.setBackgroundResource(R.drawable.flash_background_a);
        anim = (AnimationDrawable) buttonA.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){

                    buttonA.setBackgroundResource(R.drawable.when_correct_a);
                    buttonA.startAnimation(correctAnsAnimation);



                    MUSIC_FLAG = 1;
                    playAudio.setAudioforEvent(MUSIC_FLAG);

                    FLAG++;
                    moneyWonbyUser();



                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){
                                SetNewQuestion();

                            }else {

                                Handler justDealy = new Handler();
                                justDealy.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(getApplicationContext(),GameWon.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },4000);

                            }


                        }
                    },3000);
                }else {

                    buttonA.setBackgroundResource(R.drawable.when_incorrect_a);
                    buttonA.startAnimation(wrongAnsAnimation);


                    MUSIC_FLAG = 2;
                    playAudio.setAudioforEvent(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                                buttonB.setBackgroundResource(R.drawable.when_correct_b);
                            }else if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                                buttonC.setBackgroundResource(R.drawable.when_correct_c);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.when_correct_d);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                               correctAns = currentQuestion.getAnswerNr();
                               Intent intent = new Intent(getApplicationContext(),GameOver.class);
                               intent.putExtra("CorrectAns",correctAns);
                               startActivity(intent);
                               finish();


                        }
                    },3000);


                }


            }
        },4000);

    }

    public void buttonB(View view) {


        disableOptions();
        buttonB.setBackgroundResource(R.drawable.flash_background_b);
        anim = (AnimationDrawable) buttonB.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){

                    buttonB.setBackgroundResource(R.drawable.when_correct_b);
                    buttonB.startAnimation(correctAnsAnimation);

                    MUSIC_FLAG = 1;
                    playAudio.setAudioforEvent(MUSIC_FLAG);

                    FLAG++;
                    moneyWonbyUser();



                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {

                                Handler justDealy = new Handler();
                                justDealy.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(getApplicationContext(),GameWon.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },4000);

                            }


                        }
                    },3000);
                }else {

                    buttonB.setBackgroundResource(R.drawable.when_incorrect_b);
                    buttonB.startAnimation(wrongAnsAnimation);

                    MUSIC_FLAG = 2;
                    playAudio.setAudioforEvent(MUSIC_FLAG);




                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){
                                buttonA.setBackgroundResource(R.drawable.when_correct_a);
                            }else if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                                buttonC.setBackgroundResource(R.drawable.when_correct_c);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.when_correct_d);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                correctAns = currentQuestion.getAnswerNr();
                                Intent intent = new Intent(getApplicationContext(),GameOver.class);
                                intent.putExtra("CorrectAns",correctAns);
                                startActivity(intent);
                                finish();
                            }
                        }
                    },3000);


                }


            }
        },4000);


    }

    public void buttonC(View view) {


        disableOptions();
        buttonC.setBackgroundResource(R.drawable.flash_background_c);
        anim = (AnimationDrawable) buttonC.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){

                    buttonC.setBackgroundResource(R.drawable.when_correct_c);
                    buttonC.startAnimation(correctAnsAnimation);

                    MUSIC_FLAG = 1;
                    playAudio.setAudioforEvent(MUSIC_FLAG);

                    FLAG++;
                    moneyWonbyUser();

                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {

                                Handler justDealy = new Handler();
                                justDealy.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(getApplicationContext(),GameWon.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },4000);

                            }


                        }
                    },3000);
                }else {

                    buttonC.setBackgroundResource(R.drawable.when_incorrect_c);
                    buttonC.startAnimation(wrongAnsAnimation);

                    MUSIC_FLAG = 2;
                    playAudio.setAudioforEvent(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                                buttonB.setBackgroundResource(R.drawable.when_correct_b);
                            }else if (currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){
                                buttonA.setBackgroundResource(R.drawable.when_correct_a);
                            }else {
                                buttonD.setBackgroundResource(R.drawable.when_correct_d);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                correctAns = currentQuestion.getAnswerNr();
                                Intent intent = new Intent(getApplicationContext(),GameOver.class);
                                intent.putExtra("CorrectAns",correctAns);
                                startActivity(intent);
                                finish();

                            }
                        }
                    },3000);


                }


            }
        },4000);

    }

    public void buttonD(View view) {


        disableOptions();
        buttonD.setBackgroundResource(R.drawable.flash_background_d);
        anim = (AnimationDrawable) buttonD.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOption4().equals(currentQuestion.getAnswerNr())){

                    buttonD.setBackgroundResource(R.drawable.when_correct_d);
                    buttonD.startAnimation(correctAnsAnimation);

                    MUSIC_FLAG = 1;
                    playAudio.setAudioforEvent(MUSIC_FLAG);

                    FLAG++;
                    moneyWonbyUser();


                    Log.i("QuizInfo","Correct");

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                SetNewQuestion();

                            }else {

                                Handler justDealy = new Handler();
                                justDealy.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(getApplicationContext(),GameWon.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },4000);

                            }


                        }
                    },3000);
                }else {

                    buttonD.setBackgroundResource(R.drawable.when_incorrect_d);
                    buttonD.startAnimation(wrongAnsAnimation);

                    MUSIC_FLAG = 2;
                    playAudio.setAudioforEvent(MUSIC_FLAG);

                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                                buttonB.setBackgroundResource(R.drawable.when_correct_b);
                            }else if (currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                                buttonC.setBackgroundResource(R.drawable.when_correct_c);
                            }else {
                                buttonA.setBackgroundResource(R.drawable.when_correct_d);
                            }
                        }
                    },2000);

                    Handler handler4 = new Handler();
                    handler4.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid != sizeofQuiz){

                                correctAns = currentQuestion.getAnswerNr();
                                Intent intent = new Intent(getApplicationContext(),GameOver.class);
                                intent.putExtra("CorrectAns",correctAns);
                                startActivity(intent);
                                finish();

                            }
                        }
                    },3000);


                }


            }
        },4000);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        finish();
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(QuizActivity.this,PlayScreen.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }

         backPressedTime = System.currentTimeMillis();


    }

   private void disableOptions(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

   }

   private void enableOptions(){
       buttonA.setEnabled(true);
       buttonB.setEnabled(true);
       buttonC.setEnabled(true);
       buttonD.setEnabled(true);

   }



  private void moneyWonbyUser(){


        moneyHandler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (FLAG == 1){
                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",1);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "500";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 2){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",2);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "1000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 3){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",3);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "2000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 4){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",4);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "3000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 5){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",5);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "5000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 6){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",6);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "10,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 7){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",7);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "15,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 8){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",8);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "25,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 9){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",9);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "50,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 10){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",10);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "1,00,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 11){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",11);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "2,00,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 12){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",12);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "4,00,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 13){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",13);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "8,00,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 14){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",14);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "1,500,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }else if (FLAG == 15){

                    Intent intent = new Intent(QuizActivity.this,MoneyActivity.class);
                    intent.putExtra("MoneyContent",15);
                    startActivity(intent);

                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            moneyWon = "3,000,000";
                            txtMoneyWon.setText("$" + moneyWon);

                        }
                    },2000);

                }

            }
        },2500);

  }


    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.img_audience_vote:{

                audiencVoteSystem();

                break;
            }

            case R.id.img_fifty_fifty:{

                fiftyFifty();

                break;
            }

            case R.id.img_skipable:{

                skippableLifeLine();

                break;
            }

            case R.id.img_telephone:{

                telephoneLifeLine();

                break;
            }

            case R.id.imghome:{
                takeMoney();
                break;
            }
        }


    }




    private void audiencVoteSystem() {

        final Random random = new Random();
        final Set<Integer> mySet = new HashSet<>();
        RANGE = 15;
        int NOofRandomNOs = 4;
        MAX_OUT = 85;

        while (mySet.size() < NOofRandomNOs){

            int randomNum = random.nextInt(RANGE) + 1;
            mySet.add(randomNum);

        }

        ArrayList<Integer> elements = new ArrayList<>(mySet);

        showAudienceDialog(elements);

    }

    private void showAudienceDialog(ArrayList<Integer> AudienceAnswers) {


        dialog.setContentView(R.layout.audience_dialog);
        TextView txtA,txtB,txtC,txtD;
        btAudienceOk = dialog.findViewById(R.id.bt_audienceDialog);
        txtA = dialog.findViewById(R.id.txtA);
        txtB = dialog.findViewById(R.id.txtB);
        txtC = dialog.findViewById(R.id.txtC);
        txtD = dialog.findViewById(R.id.txtD);

        int SumofRandomNumbers = AudienceAnswers.get(0) + AudienceAnswers.get(1)
                                + AudienceAnswers.get(2) + AudienceAnswers.get(3);


        if (currentQuestion.getAnswerNr().contentEquals(buttonA.getText())){

            int IncreaseTheValueOf_A = (MAX_OUT - SumofRandomNumbers) + RANGE;

            int CorrectAnswerisA = IncreaseTheValueOf_A + AudienceAnswers.get(0);


            txtA.setText(Integer.toString(CorrectAnswerisA));
            txtB.setText(Integer.toString(AudienceAnswers.get(1)));
            txtC.setText(Integer.toString(AudienceAnswers.get(2)));
            txtD.setText(Integer.toString(AudienceAnswers.get(3)));

        }
        if (currentQuestion.getAnswerNr().contentEquals(buttonB.getText())){

            int IncreaseTheValueOf_B = (MAX_OUT - SumofRandomNumbers) + RANGE;

            int CorrectAnswerisB = IncreaseTheValueOf_B + AudienceAnswers.get(1);


            txtA.setText(Integer.toString(AudienceAnswers.get(0)));
            txtB.setText(Integer.toString(CorrectAnswerisB));
            txtC.setText(Integer.toString(AudienceAnswers.get(2)));
            txtD.setText(Integer.toString(AudienceAnswers.get(3)));

        }

        if (currentQuestion.getAnswerNr().contentEquals(buttonC.getText())){

            int IncreaseTheValueOf_C = (MAX_OUT - SumofRandomNumbers) + RANGE;

            int CorrectAnswerisC = IncreaseTheValueOf_C + AudienceAnswers.get(1);


            txtA.setText(Integer.toString(AudienceAnswers.get(0)));
            txtB.setText(Integer.toString(AudienceAnswers.get(1)));
            txtC.setText(Integer.toString(CorrectAnswerisC));
            txtD.setText(Integer.toString(AudienceAnswers.get(3)));

        }
        if (currentQuestion.getAnswerNr().contentEquals(buttonD.getText())){

            int IncreaseTheValueOf_D = (MAX_OUT - SumofRandomNumbers) + RANGE;

            int CorrectAnswerisD = IncreaseTheValueOf_D+ AudienceAnswers.get(1);


            txtA.setText(Integer.toString(AudienceAnswers.get(0)));
            txtB.setText(Integer.toString(AudienceAnswers.get(1)));
            txtC.setText(Integer.toString(AudienceAnswers.get(2)));
            txtD.setText(Integer.toString(CorrectAnswerisD));

        }

        btAudienceOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        disableAudienceVoteLifeLine();

    }

    
    private void telephoneLifeLine() {
        
        String mainAnswer = "";
        String theCorrectAnswer = currentQuestion.getAnswerNr();

        dialog2.setContentView(R.layout.telephone_dialog);

        TextView txtTelephoneAns;
        btTelePhoneOk = dialog2.findViewById(R.id.bt_telephoneDialog);
        txtTelephoneAns = dialog2.findViewById(R.id.txt_main_telephone_life_line);

        Random random = new Random();
        int i = random.nextInt(4);

        switch (i){

            case 0:
                mainAnswer = "I don't know the answer :(. Good Luck. ";
                break;
            case 1:
                mainAnswer = "I searched on the Inernet its actually : " + theCorrectAnswer;
                break;
            case 2:
                mainAnswer = "I am not sure but seems to be like ^^ " + theCorrectAnswer;
                break;
            case 3:
                mainAnswer = "If I were there then I would say " + theCorrectAnswer;
                break;
        }

        txtTelephoneAns.setText(mainAnswer);

        btTelePhoneOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });


        dialog2.show();
        dialog2.setCancelable(false);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        disableTelephoneLifeLine();

    }


    private void fiftyFifty() {

        if (currentQuestion.getAnswerNr().contentEquals(buttonD.getText())){

            buttonA.setVisibility(View.INVISIBLE);
            buttonB.setVisibility(View.INVISIBLE);
            buttonC.setVisibility(View.VISIBLE);
        }

        if (currentQuestion.getAnswerNr().contentEquals(buttonC.getText())){

            buttonA.setVisibility(View.INVISIBLE);
            buttonD.setVisibility(View.INVISIBLE);
            buttonB.setVisibility(View.VISIBLE);

        }

        if (currentQuestion.getAnswerNr().contentEquals(buttonB.getText())){

            buttonC.setVisibility(View.INVISIBLE);
            buttonD.setVisibility(View.INVISIBLE);
            buttonA.setVisibility(View.VISIBLE);
        }

        if (currentQuestion.getAnswerNr().contentEquals(buttonA.getText())){

            buttonD.setVisibility(View.INVISIBLE);
            buttonB.setVisibility(View.INVISIBLE);
            buttonC.setVisibility(View.VISIBLE);

        }

        disableFifityFiftyLifeLine();

    }


    private void displayOptionsAgain(){

        buttonA.setVisibility(View.VISIBLE);
        buttonB.setVisibility(View.VISIBLE);
        buttonC.setVisibility(View.VISIBLE);
        buttonD.setVisibility(View.VISIBLE);


    }


    private void skippableLifeLine() {

        SetNewQuestion();
        Toast.makeText(this, "Skippable is Used", Toast.LENGTH_SHORT).show();
        disableSkippableLifeLine();
    }

    private void disableSkippableLifeLine(){
        imgSkippable.setClickable(false);
        imgSkippable.setImageResource(R.drawable.unselect_skipable);
    }

    private void disableTelephoneLifeLine(){
        imgTelephone.setClickable(false);
        imgTelephone.setImageResource(R.drawable.unselect_telephone);
    }

    private void disableFifityFiftyLifeLine(){
        imgFiftyFifty.setClickable(false);
        imgFiftyFifty.setImageResource(R.drawable.unselected_fifity_fifty);
    }

    private void disableAudienceVoteLifeLine(){
        imgAudienceVote.setClickable(false);
        imgAudienceVote.setImageResource(R.drawable.unselect_audience_vote_system);
    }


    private void takeMoney(){

        String takeMoneyandQuit = txtMoneyWon.getText().toString();
        takeMoneyDialog.takeMoneyDialog(takeMoneyandQuit);

    }

}
