package com.example.android.androidgametictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button start_button;
    String select_board = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_for_select, null);

                final Button threeByThreeBoard = mView.findViewById(R.id.threeByThree);
                final Button fiveByFiveBoard = mView.findViewById(R.id.fiveByFive);


                threeByThreeBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_board = "three";
                        threeByThreeBoard.setBackgroundResource(R.drawable.x_background);
                        fiveByFiveBoard.setBackgroundResource(0);
                    }
                });

                fiveByFiveBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_board = "five";
                        fiveByFiveBoard.setBackgroundResource(R.drawable.x_background);
                        threeByThreeBoard.setBackgroundResource(0);
                    }
                });


                mBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_for_select);
                View v = getWindow().getDecorView();
                v.setBackgroundResource(android.R.color.transparent);
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (select_board == "five") {
                            Intent i = new Intent(getApplicationContext(), FiveByFiveSinglePlayer.class);
                            startActivity(i);
                        } else if (select_board == "three") {
                            Intent i = new Intent(getApplicationContext(), ThreeByThreeSinglePlayer.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this, "please select a board", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}

