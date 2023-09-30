package com.example.whackamole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView ball1;

    Button play;
    EditText editText;
    TextView score;
    static final int NUMBER_CODE = 1234;
    CharSequence name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ball1 = findViewById(R.id.imageView15);
        score = findViewById(R.id.textView3);


        play = findViewById(R.id.button);
        editText = findViewById(R.id.editText);




        final ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(1000);

        ball1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(scaleAnimation1);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText.getText();
                callSecondActivity(view);
            }
        });



    }

    public void callSecondActivity(View view){
        Intent sendInfo = new Intent(MainActivity.this, WhackGame.class);
        sendInfo.putExtra("name", name);
        startActivityForResult(sendInfo, NUMBER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NUMBER_CODE && resultCode == RESULT_OK){
            String one = data.getStringExtra("score");
            score.setText("The Score From the Previous Game Was: " + one);
        }
    }
}