package com.mobile.exportcheck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* loaded from: classes3.dex */
public class MainActivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_MANAGE_EXTERNAL_STORAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final String TAG = "MainActivity";
    private ImageView displayImage;
    private TextView displayText;
    private String inputStream;
    private Button submitButton;

    @SuppressLint("MissingInflatedId")
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.displayText = (TextView) findViewById(R.id.display_text);
        this.displayImage = (ImageView) findViewById(R.id.display_image);
        this.submitButton = (Button) findViewById(R.id.submit_button);
        //checkAndRequestPermissions();
        this.submitButton.setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.MainActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EditText editText = (EditText) MainActivity.this.findViewById(R.id.package_name_input);
                MainActivity.this.inputStream = editText.getText().toString().trim();
                if (!MainActivity.this.inputStream.isEmpty()) {
                    Log.d(MainActivity.TAG, "onClick: Package name is: " + MainActivity.this.inputStream);
                    boolean isInstalled = MainActivity.this.isPackageInstalled(MainActivity.this.inputStream);
                    Log.d(MainActivity.TAG, "onClick: isPackageInstalled result: " + isInstalled);
                    if (!isInstalled) {
                        Toast.makeText(MainActivity.this, "Package not found: " + MainActivity.this.inputStream, 1).show();
                        Log.d(MainActivity.TAG, "onClick: Package name not found: " + MainActivity.this.inputStream);
                        return;
                    } else {
                        Intent intent = new Intent(MainActivity.this, (Class<?>) TestActivity.class);
                        intent.putExtra("packageName", MainActivity.this.inputStream);
                        MainActivity.this.startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(MainActivity.this, "请输入包名", 0).show();
            }
        });
    }



    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageInstalled(String packageName) {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            Log.d(TAG, "isPackageInstalled: Package name is: " + packageName);
            if (packageInfo != null) {
                Log.d(TAG, "isPackageInstalled: Package exists");
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "isPackageInstalled: Package not found: " + packageName);
        }
        return false;
    }
}