package com.naruto.farming_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.farming_client.env.Logger;
import com.naruto.farming_client.tflite.Classifier;
import com.naruto.farming_client.tflite.Classifier.Device;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Analyse extends AppCompatActivity {
    /////////////////////////////////////////////
    private static final Logger LOGGER = new Logger();
    private Integer sensorOrientation = 0;
    private Classifier classifier;
    private Device device = Device.CPU;
    private int numThreads = -1;
    Thread detectThread = null;
    private long lastProcessingTimeMs;
    private final int SOCKET_PORT = 13267;      // you may change this
    private final String SERVER = "192.168.43.144";
    Thread sendThread = null;
    DataOutputStream dos = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    Socket sock = null;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    String imageName;
    /////////////////////////////////////////////
    Button Select,Take,Detect,Send;
    ImageView imageView;
    Bitmap bitmap,saveBitmap;
    TextView resultat1,resultat4;
    static final int LOAD_IMG = 1,TAKE_PICTURE=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);
        Select = findViewById(R.id.selectPic);
        Take = findViewById(R.id.takePic);
        Detect = findViewById(R.id.analyse);
        Send = findViewById(R.id.buttonSend);
        imageView = findViewById(R.id.imageView);
        resultat1 = findViewById(R.id.Resultat);
        resultat4 = findViewById(R.id.Resultat4);

        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,LOAD_IMG);
            }
        });
        Take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, TAKE_PICTURE);
                }
            }
        });
        Detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = imageView2Bitmap(imageView);
                detectThread = new Thread(new DetectThread());
                detectThread.run();
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = imageView2Bitmap(imageView);
                imageName = randomAlphaNumeric(10);
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File xfile = new File(directory, imageName + ".jpg");
                if (!xfile.exists()) {
                    Log.d("path", xfile.toString());
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(xfile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
                sendThread = new Thread(new SendThread(xfile.toString(),imageName,resultat1.getText().toString(),resultat4.getText().toString()));
                sendThread.start();
            }
        });
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK && reqCode == LOAD_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch(Exception e){
                e.printStackTrace();
                Toast.makeText(Analyse.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        if (resultCode == RESULT_OK && reqCode ==TAKE_PICTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void recreateClassifier(Classifier.Device device, int numThreads) {
        if (classifier != null) {
            LOGGER.d("Closing classifier.");
            classifier.close();
            classifier = null;
        }
        try {
            LOGGER.d(
                    "Creating classifier (device=%s, numThreads=%d)", device, numThreads);
            classifier = Classifier.create(this, device, numThreads);
        } catch (Exception e) {
            LOGGER.e(e, "Failed to create classifier.");
        }
    }
    private Bitmap imageView2Bitmap(ImageView view){
        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }

    class DetectThread implements Runnable {
        public void run() {
            recreateClassifier(device, numThreads);
            if (classifier != null) {
                final long startTime = SystemClock.uptimeMillis();
                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap,sensorOrientation);
                lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
                LOGGER.v("Detect: %s", results);
                resultat1.setText(results.get(0).toString());
                resultat4.setText(lastProcessingTimeMs + "ms");
            }else System.out.println("Classifier not created or somthing wrong happened");
        }
    }

    public  String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    class SendThread implements Runnable {
        private String imagePath;
        private String imgName;
        private String result;
        private String timeInterval;
        SendThread(String Path,String imgName,String result,String timeInterval) {
            this.imagePath = Path;
            this.imgName = imgName;
            this.result = result;
            this.timeInterval = timeInterval;
        }
        @Override
        public void run() {
            try {
                sock = new Socket(SERVER, SOCKET_PORT);
                dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(imgName);
                dos.writeUTF(result);
                dos.writeUTF(timeInterval);
                System.out.println("Connecting...");
                // send file
                File myFile = new File (imagePath);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                System.out.println("Sending " +imagePath+ "(" + mybytearray.length + " bytes)");
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("Done.");
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock!=null) sock.close();
            }catch (Exception e){

            }
        }
    }

}
