package com.example.android.androidgametictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FiveByFiveTwoPlayers extends AppCompatActivity {

    Button button;
    int o_score, x_score, draw_score;
    // O is for X  and 1 is for O
    int currentPlayer = 0;
    String side = "";

    boolean gameIsOn = true;

    /* gameStatus array keeps tab on all the button with the use of tag
        10 means the button has not been tapped
       once a button is tapped, it cannot be change again
    */
    int[] gameStatus = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_by_five_two_players);


        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
        current_layout.setVisibility(View.VISIBLE);
        TextView current = (TextView) findViewById(R.id.current_player);
        current.setTextColor(Color.BLUE);
        current.setText("Player X turn");


        //set listener to the radio button that select side
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

        Button singlePlayer_button2 = (Button) findViewById(R.id.singlePlayer_button);
        Button twoPlayer_button2 = (Button) findViewById(R.id.twoPlayer_button);

        //change the background of two players button when clicked
        twoPlayer_button2.setBackgroundResource(R.drawable.o_background);


        //onClickListener for single player button
        singlePlayer_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FiveByFiveSinglePlayer.class);
                startActivity(i);
            }
        });

        //onClickListener for two players button
        twoPlayer_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FiveByFiveTwoPlayers.class);
                startActivity(i);
            }
        });
    }//end onCreate

    // onClick method for the 25 buttons for 5*5 board
    public void play5By5Game(View view) {

        button = (Button) view;

        //get button tag set in xml, tag starts from 0 to 24 for all the 25 buttons
        int tag = (Integer.parseInt(button.getTag().toString()));

        //set button to X when tapped if the tapped button is empty and gameIsOn = true
        if (gameStatus[tag] == 10 && gameIsOn) {
            gameStatus[tag] = currentPlayer;
            if (currentPlayer == 0) {// 0 means X_player and 1 means O_player
                button.setTextColor(Color.BLUE);
                button.setText("X");
                TextView current2 = (TextView) findViewById(R.id.current_player);
                current2.setTextColor(Color.RED);
                current2.setText("player O ");
                currentPlayer = 1; // O_player turn

            } else { // set button to O if current player = 1
                button.setTextColor(Color.RED);
                button.setText("O");
                TextView current2 = (TextView) findViewById(R.id.current_player);
                current2.setTextColor(Color.BLUE);
                current2.setText("player X ");
                currentPlayer = 0; // X_player turn

            }
            int[][] gameStatus5By5; // declare two dimensional array

            //split gameStatus linear array to two dimensional array to check for winner
            gameStatus5By5 = convertToFiveByFive(gameStatus);


            if (!checkForWinnerIn5by5(gameStatus5By5).isEmpty()) {
                gameIsOn = false;
                if (checkForWinnerIn5by5(gameStatus5By5) == "X") { // check if x is a winner when board is not empty
                    x_score += 1;
                    Toast.makeText(getApplicationContext(), "Player X wins", Toast.LENGTH_SHORT).show();
                    TextView x_score_textView2 = (TextView) findViewById(R.id.x_score_textView);
                    x_score_textView2.setTextColor(Color.BLUE);
                    x_score_textView2.setText(String.valueOf(x_score));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAgain5by5();
                        }
                    }, 3000);
                }
                if (checkForWinnerIn5by5(gameStatus5By5) == "O") { //check if O is a winner when board is not empty
                    o_score += 1;
                    Toast.makeText(getApplicationContext(), "Player O wins", Toast.LENGTH_SHORT).show();
                    TextView o_score_textView2 = (TextView) findViewById(R.id.o_score_textView);
                    o_score_textView2.setTextColor(Color.RED);
                    o_score_textView2.setText(String.valueOf(o_score));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAgain5by5();
                        }
                    }, 3000);
                }


            } else {
                boolean gameIsOver = true;
                for (int button : gameStatus) { //loop through the board to see if any button is not tapped
                    if (button == 10)
                        gameIsOver = false;
                }
                if (gameIsOver) { //all button tapped and no winner
                    draw_score += 1;
                    Toast.makeText(getApplicationContext(), "Its a draw", Toast.LENGTH_SHORT).show();
                    TextView draw_score_textView = (TextView) findViewById(R.id.draw);
                    draw_score_textView.setText(String.valueOf(draw_score));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAgain5by5();
                        }
                    }, 3000);
                }
            }
        }
    }//end method playGame


    //this method check for the winner in two dimensional array and return a winner
    public String checkForWinnerIn5by5(int[][] gameStatus) {
        String winner = "";

        //check for all five rows
        for (int i = 0; i < 5; i++) {
            if (gameStatus[i][0] == gameStatus[i][1] && gameStatus[i][0] == gameStatus[i][2] && gameStatus[i][0] == gameStatus[i][3]
                    && gameStatus[i][0] == gameStatus[i][4] && gameStatus[i][0] != 10) {
                if (gameStatus[i][0] == 1)
                    winner = "O";
                else
                    winner = "X";
                break;
            }
        }

        //check for all five columns
        for (int i = 0; i < 5; i++) {
            if (gameStatus[0][i] == gameStatus[1][i] && gameStatus[0][i] == gameStatus[2][i] && gameStatus[0][i] == gameStatus[3][i]
                    && gameStatus[0][i] == gameStatus[4][i] && gameStatus[0][i] != 10) {
                if (gameStatus[0][i] == 1)
                    winner = "O";
                else
                    winner = "X";
                break;
            }
        }

        //check for first diagonal
        if (gameStatus[0][0] == gameStatus[1][1] && gameStatus[0][0] == gameStatus[2][2] && gameStatus[0][0] == gameStatus[3][3]
                && gameStatus[0][0] == gameStatus[4][4] && gameStatus[0][0] != 10) {
            if (gameStatus[0][0] == 1)
                winner = "O";
            else
                winner = "X";
        }

        //check for second diagonal
        if (gameStatus[0][4] == gameStatus[1][3] && gameStatus[0][4] == gameStatus[2][2] && gameStatus[0][4] == gameStatus[3][1]
                && gameStatus[0][4] == gameStatus[4][0] && gameStatus[0][4] != 10) {
            if (gameStatus[0][4] == 1)
                winner = "O";
            else
                winner = "X";

        }

        return winner;

    }//end method checkForWinner()


    // method to convert linear array to two dimensional array
    public int[][] convertToFiveByFive(int[] board) {

        int[][] gameStatus5By5 = new int[5][5];

        for (int i = 0; i < 25; i++) {
            if (i == 0)
                gameStatus5By5[0][0] = board[i];
            if (i == 1)
                gameStatus5By5[0][1] = board[i];
            if (i == 2)
                gameStatus5By5[0][2] = board[i];
            if (i == 3)
                gameStatus5By5[0][3] = board[i];
            if (i == 4)
                gameStatus5By5[0][4] = board[i];
            if (i == 5)
                gameStatus5By5[1][0] = board[i];
            if (i == 6)
                gameStatus5By5[1][1] = board[i];
            if (i == 7)
                gameStatus5By5[1][2] = board[i];
            if (i == 8)
                gameStatus5By5[1][3] = board[i];
            if (i == 9)
                gameStatus5By5[1][4] = board[i];
            if (i == 10)
                gameStatus5By5[2][0] = board[i];
            if (i == 11)
                gameStatus5By5[2][1] = board[i];
            if (i == 12)
                gameStatus5By5[2][2] = board[i];
            if (i == 13)
                gameStatus5By5[2][3] = board[i];
            if (i == 14)
                gameStatus5By5[2][4] = board[i];
            if (i == 15)
                gameStatus5By5[3][0] = board[i];
            if (i == 16)
                gameStatus5By5[3][1] = board[i];
            if (i == 17)
                gameStatus5By5[3][2] = board[i];
            if (i == 18)
                gameStatus5By5[3][3] = board[i];
            if (i == 19)
                gameStatus5By5[3][4] = board[i];
            if (i == 20)
                gameStatus5By5[4][0] = board[i];
            if (i == 21)
                gameStatus5By5[4][1] = board[i];
            if (i == 22)
                gameStatus5By5[4][2] = board[i];
            if (i == 23)
                gameStatus5By5[4][3] = board[i];
            if (i == 24)
                gameStatus5By5[4][4] = board[i];
        }//next i
        return gameStatus5By5;

    }//end method

    // method to reset the game
    public void playAgain5by5() {

        // if current player = 1, then O_player plays in the next turn
        if (currentPlayer == 1)
            currentPlayer = 1;
        else
            currentPlayer = 0;

        gameIsOn = true;

        //loop through the gameStatus linear array and set to default value of 10
        for (int i = 0; i < 25; i++) {
            gameStatus[i] = 10;
        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //loop through the table layout and set all buttons text to null
        for (int i = 0; i < 5; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 5; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("");
            }
        }
    }//end method playAgain

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


    public void clearBoard() {

        gameIsOn = true;
        //loop through the gameStatus linear array and set to default value of 10
        for (int i = 0; i < 25; i++) {
            gameStatus[i] = 10;
        }

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //loop through the table layout and set all buttons text to null
        for (int i = 0; i < 5; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 5; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("");
            }
        }
    }

    public void resetGame(View view) {
        clearBoard();

    }

    public void play3By3(View view) {

        Intent i = new Intent(getApplicationContext(), ThreeByThreeTwoPlayers.class);
        startActivity(i);

    }


}
