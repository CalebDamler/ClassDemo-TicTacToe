package edu.niu.cs.caleb.tictactoe;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button [][]buttons;
    private TicTacToe game;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new TicTacToe();

        buildGUI();

    }

    public void buildGUI(){
        //get the screen width
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x / TicTacToe.SIDE;

        //create gridlayout
        GridLayout gridLayout = new GridLayout(this);

        gridLayout.setColumnCount(TicTacToe.SIDE);
        //add an extra row to the gridLayout for textview
        gridLayout.setRowCount(TicTacToe.SIDE+1);

        //create the button array
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        //create buttonHandler obj
        ButtonHandler buttonHandler = new ButtonHandler();

        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++){
                buttons[row][col] = new Button(this);

                buttons[row][col].setTextSize((int)(screenWidth*0.2));
                buttons[row][col].setOnClickListener(buttonHandler);



                gridLayout.addView(buttons[row][col],screenWidth,screenWidth);

            }
        textView = new TextView(this);

        //text view dimensions
        GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE, 1), colSpec = GridLayout.spec(0,TicTacToe.SIDE);

        GridLayout.LayoutParams  layoutParams = new GridLayout.LayoutParams(rowSpec,colSpec);

        textView.setLayoutParams(layoutParams);

        textView.setWidth(TicTacToe.SIDE * screenWidth);
        textView.setHeight(screenWidth);

        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.CYAN);

        textView.setTextSize((int)(screenWidth * 0.15));

        textView.setText(game.result());

        gridLayout.addView(textView);

        setContentView(gridLayout);




    }//end GUI

    public void update(int row, int col){
        //Toast.makeText(this,"update "+ row +" , " + col, Toast.LENGTH_LONG).show();
        //buttons[row][col].setText("X");

        //determine who is playing p1 or p2
        int currentPlayer = game.play(row, col);
        if (currentPlayer == 1){
            buttons[row][col].setText("X");

        }else if (currentPlayer == 2){
            buttons[row][col].setText("O");

        }
        if(game.isGameOver()){
            textView.setBackgroundColor(Color.BLUE);
            enableButtons(false);
            textView.setText(game.result());
            showNewDialog();
        }

    }//end update

    public void enableButtons(Boolean enabled){
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++){
                buttons[row][col].setEnabled(enabled);
            }


    }// end enablebuttons


    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //Toast.makeText(MainActivity.this,"ButtonHandler: "+ v, Toast.LENGTH_LONG).show();
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int col = 0; col < TicTacToe.SIDE; col++){
                    if (v == buttons[row][col]){
                        update(row,col);
                    }
                }
        }
    }//end buttonHandle

    public void resetButtons(){
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++){
                buttons[row][col].setText("");
            }

    }

    public void showNewDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Tic Tac Toe");
        alert.setMessage("Do you want to play again?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game.resetGame();
                enableButtons(true);
                resetButtons();
                textView.setBackgroundColor(Color.CYAN);
                textView.setText(game.result());
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             MainActivity.this.finish();
            }
        });

        //make the box show on screen
        alert.show();

    }



}// end main
