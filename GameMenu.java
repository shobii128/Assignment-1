package com.shoaib.snakesladders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
    }

    public void startGame(View view) {
        Intent message = new Intent(GameMenu.this, MainGame.class);
        startActivity(message);
    }
}
