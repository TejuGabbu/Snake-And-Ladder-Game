package com.example.snakeandladder;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player
{
    private Circle coin;
    private int currPosition;

    private String name;

    private static Board gameboard = new Board();
    public Player(int tileSize , Color coincolor , String playerName)
    {
        coin = new Circle(tileSize/2);
//        coin.setLayoutX(0);
//        coin.setLayoutY(360);
        coin.setFill(coincolor);
        currPosition = 0;
        movePlayer(1);
        name = playerName;
    }

    public void movePlayer(int diceValue)
    {

        if(currPosition+diceValue<=100)
        {
            currPosition+=diceValue;
            TranslateTransition secondMove = null , firstMove = translateAnimation(diceValue);

            int newPosition = gameboard.getNewPosition(currPosition);
            if(newPosition!=currPosition && newPosition!=-1)
            {
                currPosition = newPosition;
                secondMove = translateAnimation(6);
            }
            if(secondMove==null)
            {
                firstMove.play();
            }
            else
            {
                SequentialTransition sequentialTransition = new SequentialTransition(firstMove ,
                        new PauseTransition(Duration.millis(1000)) , secondMove);
                sequentialTransition.play();
            }
        }

//        int x = gameboard.getXCoordinate(currPosition);
//        int y = gameboard.getYCoordinate(currPosition);
//
//        coin.setTranslateX(x);
//        coin.setTranslateY(y);

        translateAnimation(diceValue);
    }

    private TranslateTransition translateAnimation(int diceValue)
    {
        TranslateTransition animate = new TranslateTransition(Duration.millis(200*diceValue),coin);
        animate.setToX(gameboard.getXCoordinate(currPosition));
        animate.setToY(gameboard.getYCoordinate(currPosition));
//        System.out.println(gameboard.getXCoordinate(currPosition));
//        System.out.print(gameboard.getYCoordinate(currPosition));
        animate.setAutoReverse(false);
        return animate;
    }

    public  void startingPosition()
    {
        currPosition = 0;
        movePlayer(1);
    }
    boolean isWinner()
    {
        if(currPosition==100)
        {
            return true;
        }
        return  false;
    }
    public Circle getCoin()
    {
        return coin;
    }

    public int getCurrPosition()
    {
        return currPosition;
    }

    public String getName()
    {
        return name;
    }
}
