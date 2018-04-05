package com.example.comp.dicegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Looser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looser);

        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    startActivity(new Intent(Looser.this,MainActivity.class));
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }.start();

    }
}
