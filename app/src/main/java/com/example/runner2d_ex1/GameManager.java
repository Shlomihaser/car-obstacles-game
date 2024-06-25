package com.example.runner2d_ex1;

import java.util.Arrays;
import java.util.Random;

public class GameManager {
    private final int ROWS = 9,COLS = 3;
    private int obstaclesPos[][] = new int[ROWS][COLS]; // 1 indicate there is obstacle in the cell
    private int carPos, lifeCount; // presents the car positon 0 - Left /1 - Middle /2 - Right
    private Random random;

    public GameManager() {
        random = new Random();
        lifeCount = 3;
        carPos = 1;
    }

    public void updateObstacles(){
        int randNum;
        boolean isValidPostion = false;

        do {
            randNum = random.nextInt(3);
            isValidPostion = isValid(randNum);
        } while(!isValidPostion);

        // Moving rows downwards
        for (int i = obstaclesPos.length - 1; i > 0; i--)
            for (int j = 0; j < obstaclesPos[i].length; j++)
                obstaclesPos[i][j] = obstaclesPos[i-1][j];

        // Setting the first row base on the random number
        Arrays.fill(obstaclesPos[0], 0);
        if(random.nextInt(2) == 1)
             obstaclesPos[0][randNum] = 1;
    }

    public boolean isCrash(){
        return obstaclesPos[7][carPos] == 1;
    }
    private boolean isValid(int num){
        if(obstaclesPos[0][1] == 1 && obstaclesPos[1][2] == 1 && num == 0) // Diagonal Right
            return false;
        if(obstaclesPos[0][1] == 1 && obstaclesPos[1][0] == 1 && num == 2) // Diagonal Left
            return false;
        return true;
    }

    public int[][] getObstaclesPos() {
        return obstaclesPos;
    }


    public int getROWS() {
        return ROWS;
    }
    public int getCOLS() {return COLS;}

    public int getCarPos() {
        return carPos;
    }

    public GameManager setCarPos(int carPos) {
        this.carPos = carPos;
        return this;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public void updateLife() {
        lifeCount--;
    }

}
