package com.example.clientapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import android.content.Intent;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    Thread Thread1 = null;

    EditText etIP, etPort;
    Button connect;

    String SERVER_IP;
    int SERVER_PORT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etIP = findViewById(R.id.ipaddr);
        etPort = findViewById(R.id.port);
        connect = findViewById(R.id.connect);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this,test_activity.class);
                SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
                Thread1 = new Thread(new Thread1());
                Thread1.start();
                startActivity(mainIntent);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


