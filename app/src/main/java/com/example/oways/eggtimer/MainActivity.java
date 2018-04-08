package com.example.oways.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterActive=false;
    CountDownTimer countDownTimer;
Button controllerButton;
    public void updateTimer(int secondsLeft){
        int minutes=(int)secondsLeft/60;  //dividing seconds with 1 second to get minute
        int seconds=secondsLeft-minutes*60;

        String secondString=Integer.toString(seconds);
        if(seconds<=9){
            secondString="0"+secondString;
        }


        timerTextView.setText(Integer.toString(minutes)+":"+secondString);

    }
    public void controlTimer(View view) {
        if(counterActive==false) {
            counterActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
            countDownTimer=new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
resetTimer();
                    Log.i("Finished", "times up");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();

                }
            }.start();
        }
        else{
            resetTimer();
                    }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar= (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView=(TextView)findViewById(R.id.timerTextView);
        controllerButton=(Button)findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600); //10 minutes
        timerSeekBar.setProgress(30); //curent time
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              updateTimer(progress);
                 }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    void resetTimer(){
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        controllerButton.setText("go!");
        timerTextView.setText("0:30");
        countDownTimer.cancel();
        counterActive=false;

    }

}
