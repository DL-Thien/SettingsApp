package com.longthien.locationwificam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static int REQUEST_CODE_WIFI_SETTINGS = 123456;
    public static int REQUEST_CODE_LOCATION_SETTINGS = 654321;
    public static int REQUEST_CODE_CAMERA_PERMISSION = 789789;

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

        Button btnWiFiSettings = findViewById(R.id.btn_wifi_settings);
        Button btnLocationSettings = findViewById(R.id.btn_location_settings);
        Button btnCameraPermission = findViewById(R.id.btn_camera_permission);

        btnWiFiSettings.setOnClickListener(v -> {
            Intent settingsWifiIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivityForResult(settingsWifiIntent, REQUEST_CODE_WIFI_SETTINGS);
        });

        btnLocationSettings.setOnClickListener(v -> {
            Intent settingsLocationIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(settingsLocationIntent, REQUEST_CODE_LOCATION_SETTINGS);
        });

        btnCameraPermission.setOnClickListener(v -> {
            Intent permissionCameraIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            permissionCameraIntent.setData(uri);
            startActivityForResult(permissionCameraIntent, REQUEST_CODE_CAMERA_PERMISSION);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_WIFI_SETTINGS) {
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            boolean isWifiEnabled = wifiManager.isWifiEnabled();
            if (isWifiEnabled) {
                Toast.makeText(MainActivity.this, "Enabled Wifi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Disabled Wifi", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_LOCATION_SETTINGS) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGpsEnabled) {
                Toast.makeText(MainActivity.this, "Enabled GPS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Disabled GPS", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Camera permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

//Homework 1: Open and Return from WiFi Settings
//Objective:
//Write an Android app that opens the WiFi settings screen using startActivityForResult. After returning to the app, check if WiFi is enabled and display a Toast message.
//
//Requirements:
//
//Create a button that, when clicked, opens the WiFi settings screen.
//        Use startActivityForResult() to launch the WiFi settings.
//        After returning, check if WiFi is enabled (WifiManager).
//Show a Toast message indicating whether WiFi is on or off.
//📌 Hint: Use Settings.ACTION_WIFI_SETTINGS and WifiManager to check status.
//
//Homework 2: Check GPS Status after Opening Location Settings
//Objective:
//Develop an app that allows users to turn on GPS and detects whether it was enabled after returning to the app.
//
//        Requirements:
//
//Create a button that opens the Location Settings (Settings.ACTION_LOCATION_SOURCE_SETTINGS).
//Use startActivityForResult() to navigate to the settings.
//When returning to the app, check if GPS is enabled.
//Display a Toast message to inform users whether GPS is enabled or not.
//📌 Hint: Use LocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER).
//
//Homework 3: Request Camera Permission via App Settings
//Objective:
//Write an Android app that requests camera permission from users. If denied, guide users to the App Settings and check if they enabled it manually.
//
//Requirements:
//
//Request CAMERA permission in your app.
//If permission is denied, show a button to open App Settings (Settings.ACTION_APPLICATION_DETAILS_SETTINGS).
//Use startActivityForResult() to navigate to settings.
//        After returning, check if permission was granted and show a Toast message.
//        📌 Hint: Use ContextCompat.checkSelfPermission() and PackageManager.PERMISSION_GRANTED to check permission status.