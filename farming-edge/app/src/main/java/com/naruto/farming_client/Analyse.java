package com.naruto.farming_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.farming_client.env.Logger;
import com.naruto.farming_client.tflite.Classifier;
import com.naruto.farming_client.tflite.Classifier.Device;

import java.io.InputStream;
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

    /////////////////////////////////////////////
    Button Select,Take,Detect;
    ImageView imageView;
    Bitmap bitmap;
    TextView resultat1,resultat2,resultat3,resultat4;
    static final int LOAD_IMG = 1,TAKE_PICTURE=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);
        Select = findViewById(R.id.selectPic);
        Take = findViewById(R.id.takePic);
        Detect = findViewById(R.id.analyse);
        imageView = findViewById(R.id.imageView);
        resultat1 = findViewById(R.id.Resultat);
        resultat2 = findViewById(R.id.Resultat2);
        resultat3 = findViewById(R.id.Resultat3);
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
                resultat2.setText(results.get(1).toString());
                resultat3.setText(results.get(2).toString());
                resultat4.setText(lastProcessingTimeMs + "ms");
            }else System.out.println("Classifier not created or somthing wrong happened");
        }
    }


}
