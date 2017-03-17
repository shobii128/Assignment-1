package com.shoaib.snakesladders;


import android.widget.ImageView;


public class Dice {

    private final ImageView dice;





    public Dice(ImageView dice) {
        this.dice = dice;
    }



    public int roll(){
        int dice = (int) (Math.random() * 6) + 1;
       /* rollcount = 0;

        if(dice== 6)
        {  dice=(int) (Math.random() * 6) + 1;
            rollcount++;}
        if (rollcount>2)
            dice=1;*/

        switch (dice) {
            case 1:
                this.dice.setImageResource(R.drawable.d1);
                break;

            case 2:
                this.dice.setImageResource(R.drawable.d2);
                break;

            case 3:
                this.dice.setImageResource(R.drawable.d3);
                break;
            case 4:
                this.dice.setImageResource(R.drawable.d4);
                break;
            case 5:
                this.dice.setImageResource(R.drawable.d5);
                break;
            case 6:
                this.dice.setImageResource(R.drawable.d6);
                break;
        }
        return dice;
    }
}
