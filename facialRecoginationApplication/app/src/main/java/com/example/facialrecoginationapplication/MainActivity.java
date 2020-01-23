package com.example.facialrecoginationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerturn = true;
    private int roundCount, playerOnePoints, playerTwoPoints;

    private TextView playerOne, playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOne = findViewById(R.id.player1);
        playerTwo = findViewById(R.id.player2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }
                        if (playerturn) {
                            ((Button) v).setText("X");
                        } else {
                            ((Button) v).setText("O");
                        }
                        roundCount++;

                        if (checkForWin()) {
                            if (playerturn) {
                                playerOneWins();
                            } else {
                                playerTwoWins();
                            }
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            playerturn = !playerturn;
                        }
                    }
                });
            }
        }

        Button buttonreset = findViewById(R.id.reset);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    private void resetGame() {
        playerOnePoints=0;
        playerTwoPoints=0;
        updatePoints();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Game Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void playerOneWins() {
        playerOnePoints++;
        Toast.makeText(this, "Player One Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void playerTwoWins() {
        playerTwoPoints++;
        Toast.makeText(this, "Player Two Wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerturn = true;
    }

    @SuppressLint("SetTextI18n")
    private void updatePoints() {
        playerOne.setText("Player One : " + playerOnePoints);
        playerTwo.setText("Player Two : " + playerTwoPoints);
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) { //for row
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //for column
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("playerOnePoints",playerOnePoints);
        outState.putInt("playerTwoPoints",playerTwoPoints);
        outState.putBoolean("playerturn",playerturn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount=savedInstanceState.getInt("roundCount");
        playerOnePoints=savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints=savedInstanceState.getInt("playerTwoPoints");
        playerturn=savedInstanceState.getBoolean("playerturn");
    }
}
