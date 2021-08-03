package com.palo.palonote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.palo.palonote.R;

import androidx.appcompat.app.AppCompatActivity;

import static com.palo.palonote.utils.Constants.SPLASH_DISPLAY_LENGTH;


public class NotesSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
                    startActivity(new Intent(NotesSplashActivity.this,
                            NoteListActivity.class));
                    finish();
                },

                SPLASH_DISPLAY_LENGTH);
    }
}