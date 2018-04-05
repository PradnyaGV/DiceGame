/*Scarne's Dice Game
By Pradnya Valsangkar
*/
package com.example.comp.dicegame;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userOverallSocre;
    private int userTurnScore;
    private int compOverallScore;
    private int compTurnScore;
    private int randomNum;
    private boolean userTurn;
    Random random = new Random();
    Button roll,reset,hold;
    ImageView dice;
    TextView score;
    AlertDialog.Builder builder;
    Handler handler = new Handler();

    int[] diceImages = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score=findViewById(R.id.text1);
        userTurn=true;
        dice=findViewById(R.id.img);
        roll=findViewById(R.id.roll_button);
        roll.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                rollingDice();
            }
        });
        reset=findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                resetDice();
            }
        });
        hold=findViewById(R.id.hold_button);
        hold.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                holdDice();
            }
        });

    }

    int i ;
    Dialog dialog;
    protected void computerTurn(){
        i=0;

    final Handler handler=new Handler();
    final Runnable runnable=new Runnable() {

     @Override
     public void run() {
         int timesOfCompRolling = random.nextInt(6);
         roll.setEnabled(false);
         hold.setEnabled(false);
         userTurn =false;
         while(i<timesOfCompRolling && !userTurn) {
             rollingDice();
             i++;
             if(compTurnScore==0)
                 break;
             handler.postDelayed(this,500);
         }

         userTurn =false;
         holdDice();

         if(compOverallScore>=100)
         {
             finish();
             handler.removeCallbacks(this);
             Intent intent= new Intent(MainActivity.this,Looser.class);
             startActivity(intent);
         }
         userTurn= true;
         roll.setEnabled(true);
         hold.setEnabled(true);

     }


 };

        handler.postDelayed(runnable,500);


    }



    protected void holdDice(){
        userOverallSocre+=userTurnScore;
        compOverallScore+=compTurnScore;
        userTurnScore=0;
        compTurnScore=0;
        score.setText("Your Score:"+userOverallSocre+ " Computer Score:"+compOverallScore );
        if(userTurn){
            userTurn=false;
            computerTurn();
        }

    }

    protected void resetDice(){
        userTurnScore=0;
        userOverallSocre=0;
        compOverallScore=0;
        compTurnScore=0;
        userTurn=true;
        score.setText("Your Score:"+userOverallSocre+ " Computer Score:"+compOverallScore );
        roll.setEnabled(true);
        hold.setEnabled(true);
    }


    protected void rollingDice() {
        Log.d("MyApp","roll");
        if(userOverallSocre>=100) {
            finish();
            startActivity(new Intent(this,winner.class));
        }

        else {
            randomNum = random.nextInt(6);
            dice.setImageResource(diceImages[randomNum]);
        /*Drawable d=getResources().getDrawable(randomNum,getTheme());
        dice.setImageDrawable(d);*/
                if (userTurn) {
                    if (randomNum != 0) {
                        userTurnScore += randomNum + 1;
                        score.setText("Your Score:" + userOverallSocre + " Computer Score:" + compOverallScore +
                            "\n User Turn Score : " + userTurnScore);
                        score.append("\n You rolled : " + (randomNum + 1));
                    }
                    else {
                    userTurnScore = 0;
                    score.setText("Your Score:" + userOverallSocre + " Computer Score:" + compOverallScore +
                            "\n User Turn Score : " + userTurnScore);
                    userTurn=false;
                    computerTurn();
                    }

                }
                else {
                    if (randomNum != 0) {
                        compTurnScore += randomNum + 1;
                        score.setText("Your Score :" + userOverallSocre + "  Computer Score :" + compOverallScore +
                            "\n Computer Turn Score : " + compTurnScore);
                        score.append("\n Computer rolled : " + (randomNum + 1));
                    } else {
                    compTurnScore = 0;
                    userTurn= true;
                    score.setText("Your Score :" + userOverallSocre + "  Computer Score :" + compOverallScore +
                            "\n Computer Turn Score : " + compTurnScore);

                    }

                }

            }
    }

}

