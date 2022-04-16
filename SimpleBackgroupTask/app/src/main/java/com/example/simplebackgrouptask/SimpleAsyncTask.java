package com.example.simplebackgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class SimpleAsyncTask extends AppCompatActivity {

    private TextView textView2;
    private Button button2;
    private ProgressBar progressBar;
    private Button buttonReset;
    private static final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_async_task);

        textView2 = this.findViewById(R.id.textView2);
        button2 = this.findViewById(R.id.button2);
        progressBar = this.findViewById(R.id.progressBar);
        buttonReset = this.findViewById(R.id.buttonReset);

        textView2.setText("Nothing is running");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setText("Napping...");
                startTask();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setProgress(0);
                textView2.setText("Nothing is running.");
            }
        });
    }

    private void startTask() {
        new Thread(() -> {
            int ms = new Random().nextInt(11) * 500;
            try {
                for (int i = 0; i < 1000; i++) {
                    Thread.sleep(ms / 1000);
                    int finalI = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalI);
                        }
                    });
                }
                handler.post(() -> {textView2.setText(String.format("Run in %s milliseconds", ms));});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();


    }
}