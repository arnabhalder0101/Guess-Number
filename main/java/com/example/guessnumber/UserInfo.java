package com.example.guessnumber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserInfo extends AppCompatActivity {
    EditText name;
    Button start;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.userName);
        start = findViewById(R.id.startGame);

        String userName = name.getText().toString();
        start.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfo.this, MainActivity.class);
            intent.putExtra("UserNameData", userName);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            startActivity(intent);

        });

    }
}