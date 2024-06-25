package com.example.runner2d_ex1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class GameOverActivity extends AppCompatActivity {

    private MaterialButton gameover_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        findViews();
        initViews();
    }

    private void findViews(){
        gameover_BTN = findViewById(R.id.gameover_BTN);
    }

    private void initViews(){
        gameover_BTN.setOnClickListener(v -> resetGame());
    }

    private void resetGame(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}