package com.mobile.exportcheck;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* loaded from: classes3.dex */
public class TestActivity extends Activity {
    private Button activityTestButton;
    private Button broadcastTestButton;
    private String packageName;
    private Button providerTestButton;
    private Button serviceTestButton;

    @SuppressLint("MissingInflatedId")
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        this.packageName = getIntent().getStringExtra("packageName");
        this.activityTestButton = (Button) findViewById(R.id.activity_test_button);
        this.broadcastTestButton = (Button) findViewById(R.id.broadcast_test_button);
        this.serviceTestButton = (Button) findViewById(R.id.service_test_button);
        this.providerTestButton = (Button) findViewById(R.id.provider_test_button);
        this.activityTestButton.setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.TestActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, (Class<?>) ActivityTestActivity.class);
                intent.putExtra("packageName", TestActivity.this.packageName);
                TestActivity.this.startActivity(intent);
            }
        });
        this.broadcastTestButton.setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.TestActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, (Class<?>) BroadcastTestActivity.class);
                intent.putExtra("packageName", TestActivity.this.packageName);
                TestActivity.this.startActivity(intent);
            }
        });
        this.serviceTestButton.setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.TestActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, (Class<?>) ServiceTestActivity.class);
                intent.putExtra("packageName", TestActivity.this.packageName);
                TestActivity.this.startActivity(intent);
            }
        });
        this.providerTestButton.setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.TestActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, (Class<?>) ProviderTestActivity.class);
                intent.putExtra("packageName", TestActivity.this.packageName);
                TestActivity.this.startActivity(intent);
            }
        });
    }
}