package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class WhackGame extends AppCompatActivity {
    TextView textView;
    ImageView ball1, ball2, ball3, ball4, ball5, ball6, ball7, ball8, ball9;
    int score = 0;
    TextView textViewInCode;
    ConstraintLayout layout;
    TextView timerText;
    AtomicInteger time = new AtomicInteger(60);
    ImageView current;
    final ScaleAnimation hit = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        

    final ScaleAnimation down = new ScaleAnimation(1.0f,0f,0.5f,0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
       

    final ScaleAnimation up = new ScaleAnimation(0f,1.0f,0f,1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    Timer timerAnim = new Timer();
    TimerTask timerAnimTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whack_game);
        Bundle extras = getIntent().getExtras();

        layout = findViewById(R.id.layout);

        //Create view programmatically
        textViewInCode = new TextView(WhackGame.this);
        textViewInCode.setId(View.generateViewId());
        textViewInCode.setText("Score: 0");

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        textViewInCode.setLayoutParams(params);

        //After setting constraints width and height, add views to root layout
        layout.addView(textViewInCode);

        //Allow us to save the constraints from the current layout and then add new ones
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        
        hit.setDuration(500);
        down.setDuration(500);
        up.setDuration(500);





        ball1 = findViewById(R.id.imageView3);
        ball2 = findViewById(R.id.imageView5);
        ball3 = findViewById(R.id.imageView7);
        ball4 = findViewById(R.id.imageView9);
        ball5 = findViewById(R.id.imageView11);
        ball6 = findViewById(R.id.imageView13);
        ball7 = findViewById(R.id.imageView);
        ball8 = findViewById(R.id.imageView17);
        ball9 = findViewById(R.id.imageView19);

        //Scoring Textview initialized Programmatically
        constraintSet.connect(textViewInCode.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
        constraintSet.connect(textViewInCode.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewInCode.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(textViewInCode.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
        constraintSet.setVerticalBias(textViewInCode.getId(), .85f);
        constraintSet.setHorizontalBias(textViewInCode.getId(), .5f);
        textViewInCode.setTextSize(16);
        textViewInCode.setTypeface(textViewInCode.getTypeface(), Typeface.BOLD);
        textViewInCode.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        constraintSet.applyTo(layout);

        timerText = findViewById(R.id.timerText);
        timerText.setText("Time: " +time);

        textView = findViewById(R.id.textView4);
        textView.setText("Welcome to Whack A Mole " + extras.getCharSequence("name"));

        








        //Set Onclick Listeners and Scoring
        ball1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             handleClick(ball1);
            }

        });
        ball2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball2);
            }

        });
        ball3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball3);
            }
        });
        ball4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball4);
            }
        });
        ball5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball5);
            }
        });
        ball6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball6);
            }
        });
        ball7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball7);
            }
        });
        ball8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball8);
            }
        });
        ball9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(ball9);
            }
        });


        //Timer and Timer Task Initializations
        //timer for the game


        timerAnimTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        disappearCurrent();
                        showNext();
                    }
                });

            }

        };

        //timer for the game
        Timer timer = new Timer();

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                time.decrementAndGet();
                if (time.get() <= 0){
                    timer.cancel();
                    timerAnim.cancel();

                    callFirstActivity();
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerText.setText("Time Remaining: " + time.get());
                        }
                    });
                }


            }

        };
        timerAnim.schedule(timerAnimTask,0, 4*1000);
        //showNext();
        timer.schedule(timerTask1,0, 1000);

    }

    public void callFirstActivity(){
        Looper.prepare();
        Toast.makeText(getApplicationContext(), "Time's Up!", Toast.LENGTH_LONG).show();
        Intent sendBack = new Intent();
        sendBack.putExtra("score", score + "");
        setResult(RESULT_OK, sendBack);
        finish();
    }

    public void disappearCurrent(){

        if (current != null){
            current.startAnimation(down);
            current.setVisibility(View.INVISIBLE);
            current = null;
        }
    }

    public void showNext(){
        disappearCurrent();

            int which = (int) (Math.random() * 9) + 1;
            switch(which){
                case 1:
                    current = ball1;
                    break;
                case 2:
                    current = ball2;
                    break;
                case 3:
                    current = ball3;
                    break;
                case 4:
                    current = ball4;
                    break;
                case 5:
                    current = ball5;
                    break;
                case 6:
                    current = ball6;
                    break;
                case 7:
                    current = ball7;
                    break;
                case 8:
                    current = ball8;
                    break;
                case 9:
                    current = ball9;
                    break;
            }
            double diff = Math.random();
            if (diff <= 0.2)
                current.setImageResource(R.drawable.bomb);
            else
                current.setImageResource(R.drawable.ball2);
            current.setVisibility(View.VISIBLE);
            current.startAnimation(up);
    }

    public void handleClick(ImageView clicked){

        if(clicked.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.bomb).getConstantState()){
            if(score>=1)
                score--;
            time.getAndDecrement();
        }
        else{
            score+=2;
        }

        textViewInCode.setText("Score: " + score);
        //showNext();
        timerAnim.cancel();
        timerAnim.purge();
        timerAnim = new Timer();
        timerAnimTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        disappearCurrent();
                        showNext();
                    }
                });

            }

        };
        timerAnim.schedule(timerAnimTask,0, 4*1000);



    }
}