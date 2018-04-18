package com.example.android.androidgametictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ThreeByThreeTwoPlayers extends AppCompatActivity {

    Button button;
    int o_score, x_score, draw_score;
    String side = "";

    // O is for X  and 1 is for O
    int currentPlayer = 0;

    boolean gameIsOn = true;

    /* gameStatus array keeps tab on all the button with the use of tag
        10 means the button has not been tapped
       once a button is tapped, it cannot be change again
    */
    int[] gameStatus = {10, 10, 10, 10, 10, 10, 10, 10, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_by_three_two_players);

        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
        current_layout.setVisibility(View.VISIBLE);
        TextView current = (TextView) findViewById(R.id.current_player);
        current.setTextColor(Color.BLUE);
        current.setText("Player X turn");


        ////set listener to the radio button that select side
        final RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);

                switch (index) {
                    case 0: //first button
                        side = "X";
                        clearBoard();
                        clearScore();
                        currentPlayer = 0;
                        TextView current = (TextView) findViewById(R.id.current_player);
                        current.setTextColor(Color.BLUE);
                        current.setText("Player X turn");
                        break;
                    case 1://second button
                        side = "O";
                        clearBoard();
                        clearScore();
                        currentPlayer = 1;
                        current = (TextView) findViewById(R.id.current_player);
                        current.setTextColor(Color.RED);
                        current.setText("Player O turn");
                        break;
                }
            }
        });


        Button twoPlayer_button = (Button) findViewById(R.id.twoPlayer_button);
        Button singlePlayer_button = (Button) findViewById(R.id.singlePlayer_button);

        //set two player button background when clicked
        twoPlayer_button.setBackgroundResource(R.drawable.o_background);


        //onClickListener for Single Player Button
        singlePlayer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ThreeByThreeSinglePlayer.class);
                startActivity(i);
            }
        });

        //OnclickListener for two players button
        twoPlayer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ThreeByThreeTwoPlayers.class);
                startActivity(i);
            }
        });


    }//end onCreate


    // onClick method for the 9 buttons for 3*3 board
    public void playGame(View view) {

        button = (Button) view;

        //get button tag set in xml, tag starts from 0 to 8 for all the 9 buttons
        int tag = (Integer.parseInt(button.getTag().toString()));

        //set button to X when tapped if the tapped button is empty and gameIsOn = true
        if (gameStatus[tag] == 10 && gameIsOn) {
            gameStatus[tag] = currentPlayer;
            if (currentPlayer == 0) { // 0 means X_player and 1 means O_player
                button.setTextColor(Color.BLUE);
                button.setText("X");
                TextView current = (TextView) findViewById(R.id.current_player);
                current.setTextColor(Color.RED);
                current.setText("Player O turn");
                currentPlayer = 1;

            } else { // set button to O if current player = 1
                button.setTextColor(Color.RED);
                button.setText("O");
                TextView current = (TextView) findViewById(R.id.current_player);
                current.setTextColor(Color.BLUE);
                current.setText("Player X turn");
                currentPlayer = 0;

            }


            int[][] boardStatus; //declare two dimensional array

            //split gameStatus linear array to two dimensional array to check for winner
            boardStatus = convertToThreeByThree(gameStatus);


            if (!checkForWinner(boardStatus).isEmpty()) {
                gameIsOn = false;
                if (checkForWinner(boardStatus) == "X") { // check if X is a winner when board is not empty
                    LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
                    current_layout.setVisibility(View.INVISIBLE);
                    x_score += 1;
                    Toast.makeText(getApplicationContext(), "Player X wins", Toast.LENGTH_SHORT).show();
                    TextView x_score_textView = (TextView) findViewById(R.id.x_score_textView);
                    x_score_textView.setText(String.valueOf(x_score));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAgain();
                        }
                    }, 3000);
                }

                if (checkForWinner(boardStatus) == "O") { // check if O is a winner when board is not empty
                    LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
                    current_layout.setVisibility(View.INVISIBLE);
                    o_score += 1;
                    Toast.makeText(getApplicationContext(), "Player O wins", Toast.LENGTH_SHORT).show();
                    TextView o_score_textView = (TextView) findViewById(R.id.o_score_textView);
                    o_score_textView.setText(String.valueOf(o_score));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAgain();
                        }
                    }, 3000);
                }


            } else { //if the board is empty

                boolean gameIsOver = true;
                for (int button : gameStatus) { //loop through the board to see if any button is not tapped
                    if (button == 10)
                        gameIsOver = false;
                }
                if (gameIsOver) { //all button tapped and no winner
                    LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
                    current_layout.setVisibility(View.INVISIBLE);
                    draw_score += 1;
                    Toast.makeText(getApplicationContext(), "Its a draw", Toast.LENGTH_SHORT).show();
                    TextView draw_score_textView = (TextView) findViewById(R.id.draw);
                    draw_score_textView.setText(String.valueOf(draw_score));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAgain();
                        }
                    }, 3000);
                }
            }
        }
    }//end method playGame


    // method to convert linear array to two dimensional array
    public int[][] convertToThreeByThree(int[] boardStatus) {

        int[][] gameStatus3By3 = new int[3][3];
        for (int i = 0; i < 9; i++) {
            if (i == 0)
                gameStatus3By3[0][0] = boardStatus[i];
            if (i == 1)
                gameStatus3By3[0][1] = boardStatus[i];
            if (i == 2)
                gameStatus3By3[0][2] = boardStatus[i];
            if (i == 3)
                gameStatus3By3[1][0] = boardStatus[i];
            if (i == 4)
                gameStatus3By3[1][1] = boardStatus[i];
            if (i == 5)
                gameStatus3By3[1][2] = boardStatus[i];
            if (i == 6)
                gameStatus3By3[2][0] = boardStatus[i];
            if (i == 7)
                gameStatus3By3[2][1] = boardStatus[i];
            if (i == 8)
                gameStatus3By3[2][2] = boardStatus[i];
        } // next i
        return gameStatus3By3;
    }//end method


    // method to reset the game
    public void playAgain() {

        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
        current_layout.setVisibility(View.VISIBLE);

        // if current player = 1, then O_player plays in the next turn
        if (currentPlayer == 1)
            currentPlayer = 1;
        else
            currentPlayer = 0;

        gameIsOn = true;


        //loop through the gameStatus linear array and set to default value of 10
        for (int i = 0; i < 9; i++) {
            gameStatus[i] = 10;
        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //loop through the table layout and set all buttons text to null
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("");
            }
        }
    }//end method playAgain

    public void clearBoard() {

        gameIsOn = true;
        //loop through the gameStatus linear array and set to default value of 10
        for (int i = 0; i < 9; i++) {
            gameStatus[i] = 10;
        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //loop through the table layout and set all buttons text to null
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("");
            }
        }
    }


    //this method check for the winner and return a string
    public String checkForWinner(int[][] board) {
        String winner = "";
        //check for horizontal rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 10) {
                if (board[i][0] == 1)
                    winner = "O";
                else
                    winner = "X";
                break;
            }
        }

        //check for columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != 10) {
                if (board[0][i] == 1)
                    winner = "O";
                else
                    winner = "X";
                break;
            }
        }

        //check for first diagonal
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != 10) {
            if (board[0][0] == 1)
                winner = "O";
            else
                winner = "X";
        }

        //check for second diagonal
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != 10) {
            if (board[0][2] == 1)
                winner = "O";
            else
                winner = "X";

        }

        return winner;
    }//end method checkForWinner()

    public void clearScore() {
        o_score = 0;
        draw_score = 0;
        x_score = 0;
        TextView x_score_textView = (TextView) findViewById(R.id.x_score_textView);
        TextView o_score_textView = (TextView) findViewById(R.id.o_score_textView);
        TextView draw_score_textView = (TextView) findViewById(R.id.draw);

        x_score_textView.setText(String.valueOf(x_score));
        o_score_textView.setText(String.valueOf(o_score));
        draw_score_textView.setText(String.valueOf(draw_score));
    }//end method


    public void resetGame(View view) {
        clearBoard();
    }

    public void play5By5(View view) {
        Intent i = new Intent(getApplicationContext(), FiveByFiveTwoPlayers.class);
        startActivity(i);
    }


}

