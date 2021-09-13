package com.efortshub.youtube.tictactoe;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.efortshub.youtube.tictactoe.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String player1 = "Player 1";
    String player2 = "Player 2";
    PlayerTurn playerTurn;
    String gameSymbolPlayerOne = "X";
    String gameSymbolPlayerTwo = "O";

    enum PlayerTurn{
        PLAYER_ONE,
        PLAYER_TWO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());








        playerTurn = PlayerTurn.PLAYER_ONE;

        binding.btnResetGame.setOnClickListener((v)->{
            recreate();
        });

        initializeListener(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9);




    }

    private void initializeListener(TextView... btns) {

        for (TextView btn: btns){
            btn.setOnClickListener(view -> {

                if (binding.tvWinner.getText().toString().equals("N/A")) {
                    gameClicked(btns, btn);
                }else {
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
                binding.tvTurn.setText(player2);
                break;
            case PLAYER_TWO:
                btn.setText(gameSymbolPlayerTwo);
                playerTurn = PlayerTurn.PLAYER_ONE;
                binding.tvTurn.setText(player1);

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
        String result1 = checkCondition(condition1, btns);
        String result2 = checkCondition(condition2, btns);
        String result3 = checkCondition(condition3, btns);
        String result4 = checkCondition(condition4, btns);
        String result5 = checkCondition(condition5, btns);
        String result6 = checkCondition(condition6, btns);
        String result7 = checkCondition(condition7, btns);
        String result8 = checkCondition(condition8, btns);
        if (!result1.isEmpty()){
            binding.tvWinner.setText(result1);

        }else if (!result2.isEmpty()){
            binding.tvWinner.setText(result2);

        }else if (!result3.isEmpty()){
            binding.tvWinner.setText(result3);

        }else if (!result4.isEmpty()){
            binding.tvWinner.setText(result4);
        }else if (!result5.isEmpty()){
            binding.tvWinner.setText(result5);

        }else if (!result6.isEmpty()){
            binding.tvWinner.setText(result6);

        }else if (!result7.isEmpty()){
            binding.tvWinner.setText(result7);
        }else if (!result8.isEmpty()){
            binding.tvWinner.setText(result8);


        }
/*        if (!binding.tvWinner.getText().toString().equals("N/A")){
            new AlertDialog.Builder(MainActivity.this)
            .setTitle("Winner Found")
            .setMessage(binding.tvWinner.getText().toString()+" is winner.")
            .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    recreate();

                }
            })
            .setCancelable(false)
                    .create().show();

        }*/


    }
    private String checkCondition(int[] condition, TextView[] btns) {
        String result = "";
        List<TextView> tvlist = new ArrayList<>();

        for (int i: condition){
            TextView tv = btns[i-1];
           result = result.concat( tv.getText().toString());
           tvlist.add(tv);
        }

        Log.d("hhhh", "checkCondition: "+result);

        if (result.equals("XXX")){
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_matches);

            for (TextView v: tvlist) {
                v.startAnimation(animation);

                v.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));
                v.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));

            }

            return player1;
        }else if (result.equals("OOO")){
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_matches);

            for (TextView v: tvlist) {
                v.startAnimation(animation);

                v.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.bg_black_clicked));
                v.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));

            }



            return player2;
        }else return "";

    }
}