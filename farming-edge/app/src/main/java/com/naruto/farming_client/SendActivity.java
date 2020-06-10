package com.naruto.farming_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Locale;


public class SendActivity extends AppCompatActivity {
    EditText ipAdress, Port;
    Button Send, NewScan;
    Thread sendThread = null;
    DataOutputStream dos = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    Socket sock = null;
    String path, name, result, time, ADDR;
    int PORT;
    String Label;
    Double Latitude;
    Double Longitude;
    private FusedLocationProviderClient fusedLocationClient;
    Geocoder geocoder;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        ipAdress = findViewById(R.id.ipadress);
        Port = findViewById(R.id.portnumber);
        Send = findViewById(R.id.btnsend);
        NewScan = findViewById(R.id.newscan);
        NewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent analyseIntent = new Intent(SendActivity.this,ScanActivity.class);
                startActivity(analyseIntent);
            }
        });
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    Latitude = location.getLatitude();
                    Longitude = location.getLongitude();
                    try {
                        addresses  = geocoder.getFromLocation(Latitude,Longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String zip = addresses.get(0).getPostalCode();
                    String country = addresses.get(0).getCountryName();
                    Label = address +"-"+city+"-"+state+"-"+zip+"-"+country;
                }
            }
        });
        if(this.getIntent().getExtras() != null){
            Intent myintent = getIntent();
            path = myintent.getStringExtra("path");
            name = myintent.getStringExtra("name");
            result = myintent.getStringExtra("result");
            time = myintent.getStringExtra("time");
        }
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDR = ipAdress.getText().toString().trim();
                PORT = Integer.parseInt(Port.getText().toString().trim());
                if(!ADDR.matches("") && !(PORT+"").matches("")){
                    String addr = ADDR;
                    int port = PORT;
                    System.out.println(addr+port+"procces sending!");
                    sendThread = new Thread(new SendThread(path,name,result,time,Latitude,Longitude,Label,addr,port));
                    sendThread.start();
                }
                else{
                    System.out.println("Error try aggain!");
                }
            }
        });
    }
    class SendThread implements Runnable {
        private String imagePath;
        private String imgName;
        private String result;
        private String timeInterval;
        private String address;
        private Double lat;
        private Double lng;
        private String label;
        private int portn;
        SendThread(String Path,String imgName,String result,String timeInterval,Double lat,Double lng, String label,String address,int portn) {
            this.imagePath = Path;
            this.imgName = imgName;
            this.result = result;
            this.timeInterval = timeInterval;
            this.address = address;
            this.portn= portn;
            this.lat=lat;
            this.lng = lng;
            this.label = label;
        }
        @Override
        public void run() {
            try {
                sock = new Socket(address,portn);
                dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(imgName);
                dos.writeUTF(result);
                dos.writeUTF(timeInterval);
                dos.writeUTF(String.valueOf(lat));
                dos.writeUTF(String.valueOf(lng));
                dos.writeUTF(label);

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
