package com.example.guessnumber;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText start, end, number;
    Button clear, check, random;
    TextView result, gameName, suggest;
    static int randInt;
    static int endNum;
    static int startNum;
    int steps = 0;
//    Random rand = new Random();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//            // Accept Intent--
//            Intent i = getIntent();
//            String userName = i.getStringExtra("UserNameData");
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);

        clear = findViewById(R.id.clear);
        check = findViewById(R.id.check);
        random = findViewById(R.id.random);
        result = findViewById(R.id.result);
        suggest = findViewById(R.id.textView);
        gameName = findViewById(R.id.textView3);
        number = findViewById(R.id.number);

        startNum = Integer.parseInt(start.getText().toString());
        endNum = Integer.parseInt(end.getText().toString());
        randInt = generateRandom(startNum, endNum);

        result.setText("Guess Number Between the Range\n in 10 attempts!\n\nPress Generate Random to Start Playing...");
        check.setEnabled(false);

        start.setOnClickListener(v -> {
            start.setText("");
        });
        end.setOnClickListener(v -> {
            end.setText("");
        });
        number.setOnClickListener(v -> {
            number.setText("");
        });

        clear.setOnClickListener(v -> {
            result.setText("Guess Number\n Between the Range");
            start.setText("");
            end.setText("");
            number.setText("");
            check.setEnabled(false);
            Toast.makeText(this, "Data Erased! ", Toast.LENGTH_SHORT).show();
        });
//
        random.setOnClickListener(v -> {
            try {
                endNum = Integer.parseInt(end.getText().toString());
                startNum = Integer.parseInt(start.getText().toString());
                randInt = generateRandom(startNum, endNum);
                Toast.makeText(this, "Random Number generated Successfully!", Toast.LENGTH_SHORT).show();
                result.setText("🔔 Random Number generated \nSuccessfully!");
                steps = 0;
                check.setEnabled(true);
                check.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Please Fill the Numbers", Toast.LENGTH_SHORT).show();
            }

        });
//            }
//
        check.setOnClickListener(v -> {
            boolean won = false;
            try {
                endNum = Integer.parseInt(end.getText().toString());
                startNum = Integer.parseInt(start.getText().toString());

//                if (start.getText().toString() != "" || end.getText().toString() != "" || number.getText().toString() != "") {
                int Num = Integer.parseInt(number.getText().toString());

                if (Num > endNum || Num < startNum) {
                    result.setText("🔺 Out of Range! 🔺\n");

                } else if (Num == randInt) {
                    // score --
                    result.setText("😄🍁 Congratulations! 🍁😄\n");
                    result.append("You Guessed it \nCorrectly! " + "in " + (steps + 1) + " Attempts" + "\n\n🧭Your Score: " + ScoreGeneration(10, (10 - steps)));
                    result.append("\n\n🔔\nClick 'Generate Random' to Start Again!");
                    Toast.makeText(this, "Well Done!", Toast.LENGTH_SHORT).show();
                    check.setVisibility(View.GONE);
                    won = true;

                } else if (Num < randInt) {
                    result.setText("⚠️ Go Higher\n Please!");
                } else if (Num > randInt) {
                    result.setText("⚠️ Go Lower\n Please!");
                } else {
                    result.setText("⚠️ Unknown Err!\n");
                }
                steps++;
                result.append("\n\n🔔\nChances left: " + (10 - steps));
                if (steps >= 10 && !won) {
                    result.setText("🔺 You've Reached the Limits! 🔺" + "\nThe Number was: " + randInt +
                            "\n\nTry Again!\nAll Luck to you!");
                    result.append("\n\n🔔\nClick 'Generate Random' to Start Again!");
                    check.setVisibility(View.GONE);
                }

//                }
            } catch (Exception e) {
                Toast.makeText(this, "Please Fill the Numbers", Toast.LENGTH_SHORT).show();
            }

        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.warn).setTitle("Exit!")
                .setMessage("Are you Sure ?!! ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Thanks For Confirming! ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).setNeutralButton("Help!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Press Yes to Exit the Application!", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }
    // catch (
//                Exception e) {
//            Toast.makeText(this, "Err+ " + e, Toast.LENGTH_SHORT).show();
//        }


    int generateRandom(int startNum, int endNum) {
        Random r = new Random();
        int rand = r.nextInt(endNum - startNum + 1) + startNum;
        return rand;
    }

    int ScoreGeneration(int totalSteps, int userSteps) {
        int score = 0;
        if (userSteps >= (totalSteps * 0.8)) {
            score = 100;
        } else if (userSteps >= (totalSteps * 0.4)) {
            score = 50;
        } else if (userSteps >= (totalSteps * 0.1)) {
            score = 20;
        }
        return score;
    }

}