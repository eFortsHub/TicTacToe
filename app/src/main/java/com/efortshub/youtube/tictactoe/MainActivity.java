package com.efortshub.youtube.tictactoe;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.efortshub.youtube.tictactoe.databinding.ActivityMainBinding;
import com.efortshub.youtube.tictactoe.databinding.DialogSetPayersNameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by H. Bappi on  12:08 PM 9/13/21.
 * Contact email:
 * contact@efortshub.com
 * bappi@efortshub.com
 * contact.efortshub@gmail.com
 * Copyright (c) 2021 eFortsHub . All rights reserved.
 **/
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String player1;
    String player2 ;
    PlayerTurn playerTurn;
    String gameSymbolPlayerOne = "X";
    String gameSymbolPlayerTwo = "O";
    MediaPlayer mpClicked, mpError, mpWinner;
    private boolean isGameOver = false;
    List<PlayerTurn> winnerList = new ArrayList<>();



    enum PlayerTurn{
        PLAYER_ONE,
        PLAYER_TWO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isGameOver = false;
         mpClicked = MediaPlayer.create(MainActivity.this, R.raw.click);
         mpError = MediaPlayer.create(MainActivity.this, R.raw.click_error);
         mpWinner = MediaPlayer.create(MainActivity.this, R.raw.game_over);


        player1 = getIntent().getStringExtra("p1");
        player2 = getIntent().getStringExtra("p2");


        if (player1==null || player2==null){

            showAlertDialog();

        }else startGame();




    }

    private void showAlertDialog() {
        DialogSetPayersNameBinding spb = DialogSetPayersNameBinding.inflate(getLayoutInflater(), null, false);
        AlertDialog alertDialog;
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setView(spb.getRoot());
        b.setCancelable(false);
        alertDialog = b.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        spb.btnSetPlayer.setOnClickListener(view -> {

            String playerOne = spb.tiePlayerOne.getText().toString();
            String playerTwo = spb.tiePlayerTwo.getText().toString();

            if (playerOne.trim().isEmpty() || playerTwo.trim().isEmpty()){
                Toast.makeText(MainActivity.this, "Please set player name", Toast.LENGTH_SHORT).show();
            }else if (playerOne.trim().toLowerCase(Locale.ROOT).equals(playerTwo.trim().toLowerCase(Locale.ROOT))){
                Toast.makeText(MainActivity.this, "Two player's can't have same name", Toast.LENGTH_SHORT).show();

            }else {
                this.player1 = playerOne;
                this.player2 = playerTwo;
                if (alertDialog!=null) if (alertDialog.isShowing()) alertDialog.dismiss();
                startGame();
            }


        });

        alertDialog.show();



    }

    private void startGame() {

        playerTurn = PlayerTurn.PLAYER_ONE;
        binding.llBgPlayerTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.transparent));
        binding.llBgPlayerOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_player_turn));

        binding.tvPlayerOne.setText(player1);
        binding.tvPlayerTwo.setText(player2);

       // binding.tvTurn.setText(player1);


        binding.btnResetGame.setOnClickListener((v)->{/*
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            i.putExtra("p1", player1);
            i.putExtra("p2", player2);
            startActivity(i);
            finish();
*/

            isGameOver= false;
            startGame();

        });

        initializeListener(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9);



    }

    private void initializeListener(TextView... btns) {

        for (TextView btn: btns){
            btn.setEnabled(true);
            btn.setText("");
            btn.setTextColor(Color.WHITE);
            btn.setBackground(ContextCompat.getDrawable(MainActivity.this,
                    R.drawable.bg_clicable_black));

            btn.setOnClickListener(view -> {

                if (!isGameOver) {
                    gameClicked(btns, btn);

                    mpClicked.seekTo(0);
                    mpClicked.start();
                }else {

                    mpError.seekTo(0);
                    mpError.start();
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_matches);
                    binding.btnResetGame.startAnimation(animation);
                    binding.btnResetGame.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            binding.btnResetGame.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_clicable_black));


                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {


                        }
                    });

                }


            });
        }
    }
    private void gameClicked(TextView[] btns, TextView btn) {
        switch (playerTurn){
            case PLAYER_ONE:
                btn.setText(gameSymbolPlayerOne);
                playerTurn = PlayerTurn.PLAYER_TWO;
               // binding.tvTurn.setText(player2);
                binding.llBgPlayerTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_player_turn));

                binding.llBgPlayerOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.transparent));


                break;
            case PLAYER_TWO:
                btn.setText(gameSymbolPlayerTwo);
                playerTurn = PlayerTurn.PLAYER_ONE;
                binding.llBgPlayerTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.transparent));

                binding.llBgPlayerOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_player_turn));

                // binding.tvTurn.setText(player1);



                break;
            default:
        }
        btn.setEnabled(false);
        int[] condition1 = {1,2,3};
        int[] condition2 = {4,5,6};
        int[] condition3 = {7,8,9};
        int[] condition4 = {1,4,7};
        int[] condition5 = {2,5,8};
        int[] condition6 = {3,6,9};
        int[] condition7 = {1,5,9};
        int[] condition8 = {3,5,7};


        checkCondition(condition1, btns);
        checkCondition(condition2, btns);
        checkCondition(condition3, btns);
        checkCondition(condition4, btns);
        checkCondition(condition5, btns);
        checkCondition(condition6, btns);
        checkCondition(condition7, btns);
        checkCondition(condition8, btns);

    }
    private void checkCondition(int[] condition, TextView[] btns) {
        String result = "";
        List<TextView> tvlist = new ArrayList<>();

        for (int i: condition){
            TextView tv = btns[i-1];
           result = result.concat( tv.getText().toString());
           tvlist.add(tv);
        }

        Log.d("hhhh", "checkCondition: "+result);

        if (result.equals("XXX")){
            isGameOver = true;
            mpWinner.seekTo(0);
            mpWinner.start();
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_matches);

            binding.llBgPlayerOne.startAnimation(animation);
            for (TextView v: tvlist) {
                v.startAnimation(animation);

                v.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));
                v.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));

            }

            binding.llBgPlayerTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.transparent));
            binding.llBgPlayerOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));

            setGameOverView(PlayerTurn.PLAYER_ONE);


        }else if (result.equals("OOO")){
            isGameOver = true;
            if (mpWinner.isPlaying()){
                mpWinner.seekTo(0);
            }
            mpWinner.start();
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_matches);
            binding.llBgPlayerTwo.startAnimation(animation);
            for (TextView v: tvlist) {
                v.startAnimation(animation);

                v.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));
                v.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));

            }

            binding.llBgPlayerTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));
            binding.llBgPlayerOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.transparent));

            setGameOverView(PlayerTurn.PLAYER_TWO);

        }
    }

    @SuppressLint("SetTextI18n")
    private void setGameOverView(PlayerTurn player) {
        winnerList.add(player);
        binding.tvTotalGamePlayerOne.setText(winnerList.size()+"");
        binding.tvTotalGamePlayerTwo.setText(winnerList.size()+"");
        int winnerCountPlayerOne=0, winnerCountPlayerTwo =0;

        for (PlayerTurn plr: winnerList){
            switch (plr){
                case PLAYER_ONE:
                    winnerCountPlayerOne++;
                    break;
                case PLAYER_TWO:
                    winnerCountPlayerTwo++;
            }
        }

        int losePlayerOne = winnerList.size()-winnerCountPlayerOne;
        int losePlayerTwo = winnerList.size()-winnerCountPlayerTwo;


        binding.tvWinPlayerOne.setText(String.valueOf(winnerCountPlayerOne));
        binding.tvLosePlayerOne.setText(String.valueOf(losePlayerOne));

        binding.tvWinPlayerTwo.setText(String.valueOf(winnerCountPlayerTwo));
        binding.tvLosePlayerTwo.setText(String.valueOf(losePlayerTwo));




    }
}