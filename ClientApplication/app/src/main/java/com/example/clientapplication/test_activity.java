package com.example.clientapplication;

import androidx.appcompat.app.AppCompatActivity;
import java.net.Socket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class test_activity extends AppCompatActivity {
    Thread Thread1 = null;
    Button date, time;
    TextView msg;

    String SERVER_IP;
    int SERVER_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        msg = findViewById(R.id.msg);

        Thread1 = new Thread(new Thread1());
        Thread1.start();
        if(this.getIntent().getExtras()!= null){
            SERVER_IP = this.getIntent().getExtras().getString("ip");
            SERVER_PORT = this.getIntent().getExtras().getInt("port");
        }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Date";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                }
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Time";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                }
            }
        });
    }

    private PrintWriter output;
    private BufferedReader input;

    class Thread1 implements Runnable {

        @Override
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);

                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });
                new Thread(new Thread2()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String message = input.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread3 implements Runnable {
        private String message;

        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}