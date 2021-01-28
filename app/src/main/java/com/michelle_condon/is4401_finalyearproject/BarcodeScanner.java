package com.michelle_condon.is4401_finalyearproject;

//Import Statements

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class BarcodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //Declaring Variables
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    public static String scanResult;

    //Code below ia based on a YouTube Video, by TechAcademy, youtube.com/watch?v=otkz5Cwdw38
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Implementing ZXing ScannerView
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);


        //If statement calls the checkPermission() method to check the status of permission access to the camera
        //If permission has not been granted the requestPermission() method is called
        if (checkPermission()) {
            Toast.makeText(BarcodeScanner.this, "Permission is granted!", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }

    }

    //Check the permission access rights to the device's camera
    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(BarcodeScanner.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    //Request permission for access rights to the devices camera
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    //The result of the permission request - granted or not granted
    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    //Camera permission has been granted
                    if (cameraAccepted) {
                        Toast.makeText(BarcodeScanner.this, "Permission To Access The Camera Has Been Granted", Toast.LENGTH_LONG).show();
                    }
                    //Permission to access the camera is denied
                    else {
                        Toast.makeText(BarcodeScanner.this, "Permission To Access The Camera Has Not Been Granted", Toast.LENGTH_LONG).show();
                        //Prompting the user to grant permission for access to the camera
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            displayAlertMessage("You need to allow access for both permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    //Camera for barcode scanner resumes
    @Override
    public void onResume() {
        super.onResume();
//Checks permission status of camera access
        if (checkPermission()) {
            if (scannerView == null) {
                scannerView = new ZXingScannerView(this);
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        } else {
            requestPermissions();
        }
    }

    //Destroying the event by stopping the camera
    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(BarcodeScanner.this)

                //Details of the alert message to be displayed
                .setMessage(message)
                .setPositiveButton("Ok", listener)
                .setNegativeButton("Cancel!", null)
                .create()
                .show();
    }

    //Method handles the result of the permissions
    @Override
    public void handleResult(Result result) {
        scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("Add to Inventory", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(BarcodeScanner.this, AddItems.class);
                startActivity(i);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(BarcodeScanner.this);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
        //End
    }
}