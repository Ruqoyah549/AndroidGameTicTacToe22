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
import android.widget.TextView;
import android.widget.Toast;

public class FiveByFiveSinglePlayer extends AppCompatActivity {

    private PlayWithComputerFiveByFive mGame;

    private Button mBoardButtons[];

    private TextView mHumanCount;
    private TextView mTieCount;
    private TextView mAndroidCount;

    private int mHumanCounter = 0;
    private int mTieCounter = 0;
    private int mAndroidCounter = 0;

    private boolean mHumanFirst = true;
    private boolean mGameOver = false;

    String side = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_by_five_single_player);

        Button twoPlayer_button = (Button) findViewById(R.id.twoPlayer_button);
        Button singlePlayer_button = (Button) findViewById(R.id.singlePlayer_button);

        //set two player button background when clicked
        singlePlayer_button.setBackgroundResource(R.drawable.o_background);


        //onClickListener for Single Player Button
        singlePlayer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FiveByFiveSinglePlayer.class);
                startActivity(i);
            }
        });

        //OnclickListener for two players button
        twoPlayer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FiveByFiveTwoPlayers.class);
                startActivity(i);
            }
        });

        //set the visibility of this layout to visible
        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
        current_layout.setVisibility(View.VISIBLE);
        TextView current = (TextView) findViewById(R.id.current_player);
        current.setTextColor(Color.BLUE);
        current.setText("Your turn");

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
                        TextView current = (TextView) findViewById(R.id.current_player);
                        current.setTextColor(Color.BLUE);
                        current.setText("Your turn");
                        mHumanFirst = true;
                        mGame.HUMAN_PLAYER = 'X';
                        mGame.COMPUTER_PLAYER = 'O';
                        startNewGame();
                        break;

                    case 1://second button
                        side = "O";
                        current = (TextView) findViewById(R.id.current_player);
                        current.setTextColor(Color.RED);
                        current.setText("Your turn");
                        mHumanFirst = true;
                        mGame.HUMAN_PLAYER = 'O';
                        mGame.COMPUTER_PLAYER = 'X';
                        startNewGame();
                        break;
                }
            }
        });


        mBoardButtons = new Button[mGame.getBOARD_SIZE()];
        mBoardButtons[0] = (Button) findViewById(R.id.button_1);
        mBoardButtons[1] = (Button) findViewById(R.id.button_2);
        mBoardButtons[2] = (Button) findViewById(R.id.button_3);
        mBoardButtons[3] = (Button) findViewById(R.id.button_4);
        mBoardButtons[4] = (Button) findViewById(R.id.button_5);
        mBoardButtons[5] = (Button) findViewById(R.id.button_6);
        mBoardButtons[6] = (Button) findViewById(R.id.button_7);
        mBoardButtons[7] = (Button) findViewById(R.id.button_8);
        mBoardButtons[8] = (Button) findViewById(R.id.button_9);
        mBoardButtons[9] = (Button) findViewById(R.id.button_10);
        mBoardButtons[10] = (Button) findViewById(R.id.button_11);
        mBoardButtons[11] = (Button) findViewById(R.id.button_12);
        mBoardButtons[12] = (Button) findViewById(R.id.button_13);
        mBoardButtons[13] = (Button) findViewById(R.id.button_14);
        mBoardButtons[14] = (Button) findViewById(R.id.button_15);
        mBoardButtons[15] = (Button) findViewById(R.id.button_16);
        mBoardButtons[16] = (Button) findViewById(R.id.button_17);
        mBoardButtons[17] = (Button) findViewById(R.id.button_18);
        mBoardButtons[18] = (Button) findViewById(R.id.button_19);
        mBoardButtons[19] = (Button) findViewById(R.id.button_20);
        mBoardButtons[20] = (Button) findViewById(R.id.button_21);
        mBoardButtons[21] = (Button) findViewById(R.id.button_22);
        mBoardButtons[22] = (Button) findViewById(R.id.button_23);
        mBoardButtons[23] = (Button) findViewById(R.id.button_24);
        mBoardButtons[24] = (Button) findViewById(R.id.button_25);


        mHumanCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mAndroidCount = (TextView) findViewById(R.id.androidCount);

        mHumanCount.setText(Integer.toString(mHumanCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mAndroidCount.setText(Integer.toString(mAndroidCounter));

        //create an object from PlayWithComputerFiveByFive
        mGame = new PlayWithComputerFiveByFive();

        //call this startNewGame method
        startNewGame();

    }// end onCreate

    //this method start a new game
    private void startNewGame() {
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener2(i));
        }

        if (mHumanFirst) {
            TextView current = (TextView) findViewById(R.id.current_player);
            current.setTextColor(Color.BLUE);
            current.setText("Your turn");
            mHumanFirst = false;
        } else {
            TextView current = (TextView) findViewById(R.id.current_player);
            current.setTextColor(Color.BLUE);
            current.setText("Computer turn");
            int move = mGame.getComputerMove();
            setMove(mGame.COMPUTER_PLAYER, move);
            mHumanFirst = true;
        }

        mGameOver = false;
    }

    private class ButtonClickListener2 implements View.OnClickListener {
        int location;

        public ButtonClickListener2(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(mGame.HUMAN_PLAYER, location);

                    int winner = mGame.checkForWinner();

                    if (winner == 0) {
                        TextView current = (TextView) findViewById(R.id.current_player);
                        current.setTextColor(Color.BLUE);
                        current.setText("computer turn");
                        int move = mGame.getComputerMove();
                        setMove(mGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }

                    if (winner == 0) {
                        TextView current = (TextView) findViewById(R.id.current_player);
                        current.setTextColor(Color.BLUE);
                        current.setText("Your turn");
                    } else if (winner == 1) {
                        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
                        current_layout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "It's a draw", Toast.LENGTH_SHORT).show();
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                        mGameOver = true;
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playAgain();
                            }
                        }, 3000);

                    } else if (winner == 2) {
                        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
                        current_layout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "You win", Toast.LENGTH_SHORT).show();
                        mHumanCounter++;
                        mHumanCount.setText(Integer.toString(mHumanCounter));
                        mGameOver = true;
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playAgain();
                            }
                        }, 3000);
                    } else {
                        LinearLayout current_layout = (LinearLayout) findViewById(R.id.current_layout);
                        current_layout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Computer wins", Toast.LENGTH_SHORT).show();
                        mAndroidCounter++;
                        mAndroidCount.setText(Integer.toString(mAndroidCounter));
                        mGameOver = true;
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
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == mGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.BLUE);
        else
            mBoardButtons[location].setTextColor(Color.RED);
    }

    public void playAgain() {
        startNewGame();
    }

    public void resetGame(View view) {
        mGameOver = true;
        startNewGame();
    }

    public void play3By3(View view) {
        Intent i = new Intent(getApplicationContext(), ThreeByThreeSinglePlayer.class);
        startActivity(i);
    }
}