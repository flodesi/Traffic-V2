package com.example.starl.hackcodeproject;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.*;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.Camera;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    CameraSource cameraSource;
    SurfaceView cameraView;
    TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    Socket socket;
    DataOutputStream DOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        barcodeInfo = (TextView) findViewById(R.id.code_info);
        barcodeDetector = new BarcodeDetector.Builder(this).
                setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1280, 960)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        //Frame myFrame = new Frame.Builder().setBitmap(myQRCode).build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                    if (barcodes.valueAt(0).displayValue.equals("Traffic Light 74")) {
                        try {
                            //String sentence;
                            String modifiedSentence;
                            //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                            Socket clientSocket = new Socket("172.17.74.171", 140); // insert your own code here
                            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            //sentence = inFromUser.readLine();
                            outToServer.writeBytes("10");
                            modifiedSentence = "1";
                            System.out.println("FROM SERVER: " + modifiedSentence);
                            clientSocket.close();
                        } catch (Exception IO) {

                        }
                    }
                }
            }
        });
    }
}
