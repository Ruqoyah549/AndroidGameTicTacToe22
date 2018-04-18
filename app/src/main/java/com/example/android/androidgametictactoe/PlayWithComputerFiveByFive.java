package com.example.android.androidgametictactoe;

import java.util.Random;


/**
 * Created by Kolade on 07/04/2018.
 */

public class PlayWithComputerFiveByFive {

    private char mBoard[];
    private final static int BOARD_SIZE = 25;

    public char HUMAN_PLAYER = 'X';
    public char COMPUTER_PLAYER = '0';
    public static final char EMPTY_SPACE = ' ';

    private Random mRand;

    public static int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public PlayWithComputerFiveByFive() {

        mBoard = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            mBoard[i] = EMPTY_SPACE;

        mRand = new Random();
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public void setMove(char player, int location) {
        mBoard[location] = player;
    }

    public int getComputerMove() {
        int move;

        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    setMove(COMPUTER_PLAYER, i);
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }

        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    setMove(COMPUTER_PLAYER, i);
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }

        do {
            move = mRand.nextInt(getBOARD_SIZE());
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);

        setMove(COMPUTER_PLAYER, move);
        return move;
    }

    public int checkForWinner() {
        for (int i = 0; i <= 20; i += 5) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 1] == HUMAN_PLAYER &&
                    mBoard[i + 2] == HUMAN_PLAYER &&
                    mBoard[i + 3] == HUMAN_PLAYER &&
                    mBoard[i + 4] == HUMAN_PLAYER)
                return 2;

            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 1] == COMPUTER_PLAYER &&
                    mBoard[i + 2] == COMPUTER_PLAYER &&
                    mBoard[i + 3] == COMPUTER_PLAYER &&
                    mBoard[i + 4] == COMPUTER_PLAYER)
                return 3;
        }

        for (int i = 0; i <= 4; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 5] == HUMAN_PLAYER &&
                    mBoard[i + 10] == HUMAN_PLAYER &&
                    mBoard[i + 15] == HUMAN_PLAYER &&
                    mBoard[i + 20] == HUMAN_PLAYER)
                return 2;

            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 5] == COMPUTER_PLAYER &&
                    mBoard[i + 10] == COMPUTER_PLAYER &&
                    mBoard[i + 15] == COMPUTER_PLAYER &&
                    mBoard[i + 20] == COMPUTER_PLAYER)
                return 3;
        }

        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[6] == HUMAN_PLAYER &&
                mBoard[12] == HUMAN_PLAYER &&
                mBoard[18] == HUMAN_PLAYER &&
                mBoard[24] == HUMAN_PLAYER) ||
                (mBoard[4] == HUMAN_PLAYER &&
                        mBoard[8] == HUMAN_PLAYER &&
                        mBoard[12] == HUMAN_PLAYER &&
                        mBoard[16] == HUMAN_PLAYER &&
                        mBoard[20] == HUMAN_PLAYER))
            return 2;

        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[6] == COMPUTER_PLAYER &&
                mBoard[12] == COMPUTER_PLAYER &&
                mBoard[18] == COMPUTER_PLAYER &&
                mBoard[24] == COMPUTER_PLAYER) ||
                (mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[8] == COMPUTER_PLAYER &&
                        mBoard[12] == COMPUTER_PLAYER &&
                        mBoard[16] == COMPUTER_PLAYER &&
                        mBoard[20] == COMPUTER_PLAYER))
            return 3;
        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        return 1;
    }
}

