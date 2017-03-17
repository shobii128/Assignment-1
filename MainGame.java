package com.shoaib.snakesladders;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainGame extends AppCompatActivity {

    int player1Position;
    int player2Position;
    private Dice dice;


    private TextView player1Score;
    private TextView player2Score;
    private int activePlayer = 1;
    private int width;
    private int height;

    private ImageView image;
    private boolean snakebite = false;
    private boolean snakebitecom = false;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private ArrayList<Rocket> rockets;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            takeTurn(R.id.computer, 2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        // Create snakes
        snakes = new ArrayList<>();
        snakes.add(new Snake(98, 40));
        snakes.add(new Snake(84, 58));
        snakes.add(new Snake(87, 49));
        snakes.add(new Snake(73, 15));
        snakes.add(new Snake(50, 5));
        snakes.add(new Snake(56, 8));
        snakes.add(new Snake(43, 17));
        // Create ladders
        ladders = new ArrayList<>();
        ladders.add(new Ladder(2, 23));
        ladders.add(new Ladder(20, 59));
        ladders.add(new Ladder(57, 96));
        ladders.add(new Ladder(6, 45));
        ladders.add(new Ladder(71, 92));
        ladders.add(new Ladder(52, 69));
        // Create Rockets
        rockets = new ArrayList<>();
        rockets.add(new Rocket(4, 68));
        rockets.add(new Rocket(30, 95));


        player1Position = 0;
        player2Position = 0;

        dice = new Dice((ImageView) findViewById(R.id.dice));
        player1Score = (TextView) findViewById(R.id.humanScore);
        player2Score = (TextView) findViewById(R.id.computerScore);
        image = (ImageView) findViewById(R.id.image);
        findViewById(R.id.imageView13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeTurn(R.id.player, 1);

            }
        });

        findViewById(R.id.board).post(new Runnable() {
            @Override
            public void run() {
                width = findViewById(R.id.board).getWidth();
                height = findViewById(R.id.board).getHeight();
            }
        });

       findViewById(R.id.computer).setVisibility(View.GONE);
        findViewById(R.id.player).setVisibility(View.GONE);
    }

    private int getX(int position) {
        int col = position % 10;
        col = (position % 10 == 0) ? 10 : col;

        int row = position / 10;
        row = (position % 10 == 0) ? row - 1 : row;

        col = (row % 2 != 0) ? (10 - col) : col - 1;
        return (int) (col / 10.f * width);
    }

    private int getY(int position) {
        int row = position / 10;
        row = (position % 10 == 0) ? row - 1 : row;
        return height- (int) (row / 10.f * width);
    }

    private void moveOnScreen(final int id, final int oldPosition, final int newPosition) {

        final int[] current = {oldPosition};

        Animation.AnimationListener onHop = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(id).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (++current[0] < newPosition) {
                    int x1 = getX(++current[0]);
                    int y1 = getY(current[0]);

                    int x2 = getX(current[0] + 1);
                    int y2 = getY(current[0] + 1);

                    animation = new TranslateAnimation(x1, x2, y1, y2);
                    animation.setDuration(500);
                    animation.setFillAfter(true);

                    animation.setAnimationListener(this);
                    findViewById(id).startAnimation(animation);
                } else {
                    if (activePlayer == 1) {
                        activePlayer = 2;

                        Handler h = new Handler();
                        h.postDelayed(r, 3000);
                    } else if (activePlayer == 2) {
                        activePlayer = 1;
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

      int x1 = getX(current[0]);
        int y1 = getY(current[0]);

        int x2 = getX(current[0] + 1);
        int y2 = getY(current[0] + 1);

        TranslateAnimation animation = new TranslateAnimation(x1, x2, y1, y2);
        animation.setDuration(500);
        animation.setAnimationListener(onHop);
        animation.setFillAfter(true);
        findViewById(id).startAnimation(animation);
    }

    public void takeTurn(int imageId, int player) {

        if (activePlayer != player) return;

        int oldPosition = 0;
        int newPosition = 0;
        int diceValue = dice.roll();




        Toast.makeText(this, String.valueOf(activePlayer) + " threw " + String.valueOf(diceValue), Toast.LENGTH_SHORT).show();
        switch (player) {
            case 1:


                if(snakebite==true&&diceValue!=6)
                {
                    oldPosition = player1Position;
                    newPosition = player1Position;
                    break;
                }
                if(snakebite==true&&diceValue==6)
                {
                    snakebite=false;
                }
                oldPosition = player1Position;
                player1Position += diceValue;
                for (Snake s : snakes) {
                    if (player1Position == s.head) {
                        snakebite=true;
                        player1Position = s.tail;

                        Toast.makeText(this, "you Bitten by snake", Toast.LENGTH_SHORT).show();

                    }
                    //if(snakebite==true&&diceValue!=6)
                      //  break;

                }
                for (Ladder l : ladders) {
                    if (player1Position == l.down) {
                        player1Position = l.up;
                        dice.roll();
                        player1Position += diceValue;

                        Toast.makeText(this, "Got the ladder", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Dice will roll automatically for you again ", Toast.LENGTH_SHORT).show();
                    }

                }
                for (Rocket r : rockets) {
                    if (player1Position == r.land) {
                        player1Position = r.fly;
                        Toast.makeText(this, "Ride the rocket", Toast.LENGTH_SHORT).show();
                    }

                }




                if (player1Position > 100) {
                    player1Position -= diceValue;
                }
                if (player1Position == 100 && player2Position != 100)

                {
                    Toast.makeText(this, "You Won", Toast.LENGTH_SHORT).show();

                    finish();
                }

                player1Score.setText(String.valueOf(player1Position));
                newPosition = player1Position;

                break;

            case 2:


                if(snakebitecom==true&&diceValue!=6) {
                    oldPosition = player2Position;
                    newPosition = player2Position;
                    break;
                }
                if(snakebitecom==true&&diceValue==6)
                {
                    snakebitecom=false;
                }
                oldPosition = player2Position;

                player2Position += diceValue;


                for (Snake s : snakes) {
                    if (player2Position == s.head) {
                        player2Position = s.tail;
                        snakebitecom=true;
                        System.out.print("Bitten by Snake,Falling Down");
                    }
                }
                for (Ladder l : ladders) {
                    if (player2Position == l.down) {
                        player2Position = l.up;
                        System.out.print("Got the ladder");
                    }

                }
                for (Rocket r : rockets) {
                    if (player2Position == r.land) {
                        player2Position = r.fly;
                        System.out.print("You Ride on a Rocket");
                    }

                }
                if (player2Position > 100) {
                    player2Position -= diceValue;
                }
                if (player2Position == 100 && player1Position != 100) {
                    Toast.makeText(this, "Computer Won", Toast.LENGTH_SHORT).show();

                    finish();
                }
                player2Score.setText(String.valueOf(player2Position));
                newPosition = player2Position;
                break;
        }

        moveOnScreen(imageId, oldPosition, newPosition);

    }

}
