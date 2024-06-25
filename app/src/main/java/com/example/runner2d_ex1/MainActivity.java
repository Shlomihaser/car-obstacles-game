package com.example.runner2d_ex1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView obstacles[][] = new AppCompatImageView[9][3];
    private AppCompatImageView hearts[] = new AppCompatImageView[3];
    private AppCompatImageView cars[] = new AppCompatImageView[3];
    private ExtendedFloatingActionButton leftButton,rightButton;
    private GameManager gameManager;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager();
        SignalManager.init(this);
        findViews();
        initViews();
        startGame();
    }

    private void findViews() {
        leftButton = findViewById(R.id.main_FAB_move_left);
        rightButton = findViewById(R.id.main_FAB_move_right);

        int resId;
        for(int i = 0;i < cars.length ;i++){
            resId = getResources().getIdentifier("car" + i,"id",getPackageName());
            cars[i] = findViewById(resId);
        }

        for(int i = 0 ;i < hearts.length ;i++){
            resId = getResources().getIdentifier("main_IMG_heart" + i,"id",getPackageName());
            hearts[i] = findViewById(resId);
        }

        for(int i = 0 ; i < gameManager.getROWS() ;i++){
            for (int j = 0; j < gameManager.getCOLS(); j++){
                    resId = getResources().getIdentifier("img" + i + j,"id",getPackageName());
                    obstacles[i][j] = findViewById(resId);
            }
        }
    }

    private void initViews(){
        leftButton.setOnClickListener(v -> moveCar(-1));
        rightButton.setOnClickListener(v -> moveCar(1));

        cars[0].setVisibility(View.INVISIBLE);
        cars[2].setVisibility(View.INVISIBLE);
    }

    private void startGame(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()-> {
                    gameManager.updateObstacles();
                    updateUI();
                    checkCrash();
                });
            }
        },0L,500L);
    }

    private void checkCrash() {
        if(!gameManager.isCrash())
            return;

        SignalManager.getInstance().toast("Collision");
        SignalManager.getInstance().vibrate(500);
        updateLife();
    }

    private void updateUI(){
        for(int i = 0; i < gameManager.getROWS(); i++)
            for(int j = 0 ; j < gameManager.getCOLS();j++)
                    obstacles[i][j].setVisibility(gameManager.getObstaclesPos()[i][j] == 1 ? View.VISIBLE : View.INVISIBLE);
    }



    private void updateLife(){
        int life = gameManager.getLifeCount();

        if(life == 1) {
            timer.cancel();
            timer = null;
            Intent i = new Intent(this,GameOverActivity.class);
            startActivity(i);
            finish();
        } else {
            hearts[life-1].setVisibility(View.INVISIBLE);
            gameManager.updateLife();
        }
    }

    private void moveCar(int side){
        int carPosition = gameManager.getCarPos();
        cars[carPosition].setVisibility(View.INVISIBLE);
        if( (side == -1 && carPosition == 0) || (side == 1 && carPosition == 2))
            return;
        cars[carPosition + side].setVisibility(View.VISIBLE);
        gameManager.setCarPos(carPosition + side);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startGame();
    }
}