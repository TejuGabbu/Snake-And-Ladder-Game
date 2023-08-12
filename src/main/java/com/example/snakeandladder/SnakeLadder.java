package com.example.snakeandladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {
    public static final int tileSize = 40, width = 10, height = 10;

    public  static final int buttonLine = height * tileSize + 50 , infoLine = buttonLine - 30;

    private static  Dice dice = new Dice();

    private Player playerOne , playerTwo;

    private  boolean gameStarted = false , playerOneTurn = false , playerTwoTurn = false;
    private Pane createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize + 100);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }

        Image img = new Image("C:\\Users\\Tejas\\snakeandladder\\src\\main\\img.png");
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        // Button
        Button playerOneButton = new Button("Player One");
        Button playerTwoButton = new Button("Player Two");
        Button StartButton = new Button("Start");

        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setTranslateX(20);
        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(300);
        StartButton.setTranslateY(buttonLine);
        StartButton.setTranslateX(160);

        // info display
        Label playeronelabel = new Label("");
        Label playertwolabel = new Label("");
        Label dicelabel = new Label("Start the Game");

        playeronelabel.setTranslateY(infoLine);
        playeronelabel.setTranslateX(20);
        playerOneButton.setDisable(true);
        playertwolabel.setTranslateY(infoLine);
        playertwolabel.setTranslateX(300);
        playerTwoButton.setDisable(true);
        dicelabel.setTranslateY(infoLine);
        dicelabel.setTranslateX(160);

        playerOne = new Player(tileSize , Color.BLACK , "Teju");
        playerTwo = new Player(tileSize-5 , Color.WHITE , "Gabbu");

        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted)
                {
                    if(playerOneTurn)
                    {
                        int diceValue = dice.getRolledDiceValue();
                        dicelabel.setText("Dice Value "+diceValue);
                        playerOne.movePlayer(diceValue);
                        ///
                        if(playerOne.isWinner())
                        {
                            dicelabel.setText("Winner Is "+playerOne.getName());
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playeronelabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(true);
                            playertwolabel.setText("");

                            StartButton.setDisable(false);
                            StartButton.setText("Restart");
                            gameStarted = false;
                        }
                        else
                        {
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playeronelabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(false);
                            playertwolabel.setText("Your Turn "+playerTwo.getName());
                        }

                    }
                }

            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted)
                {
                    if(playerTwoTurn)
                    {
                        int diceValue = dice.getRolledDiceValue();
                        dicelabel.setText("Dice Value "+diceValue);
                        playerTwo.movePlayer(diceValue);
                        ///winning condition
                        if(playerTwo.isWinner())
                        {
                            dicelabel.setText("Winner Is "+playerTwo.getName());
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playeronelabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(true);
                            playertwolabel.setText("");
                            StartButton.setDisable(false);
                            StartButton.setText("Restart");
                        }
                        else
                        {
                            playerOneTurn = true;
                            playerOneButton.setDisable(false);
                            playeronelabel.setText("Your Turn "+playerOne.getName());

                            playerTwoTurn = false;
                            playerTwoButton.setDisable(true);
                            playertwolabel.setText("");
                        }

                    }
                }

            }
        });

        StartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                dicelabel.setText("Game Started");
                StartButton.setDisable(true);
                playerOneTurn = true;
                playeronelabel.setText("Your Turn " + playerOne.getName());
                playerOneButton.setDisable(false);
                playerOne.startingPosition();

                playerTwoTurn = false;
                playertwolabel.setText("");
                playerTwoButton.setDisable(true);
                playerTwo.startingPosition();
            }
        });
        root.getChildren().addAll(board,playerOneButton,playerTwoButton,StartButton,playeronelabel , playertwolabel,dicelabel , playerOne.getCoin() , playerTwo.getCoin());

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}