package com.mobile.exportcheck;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ActivityTestActivity extends Activity {
    private ArrayAdapter<String> componentAdapter;
    private Spinner componentSpinner;
    private PackageManager packageManager;
    private String packageName;
    private ArrayList<String> componentNames = new ArrayList<>();
    private ArrayList<ComponentName> components = new ArrayList<>();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.packageName = getIntent().getStringExtra("packageName");
        this.packageManager = getPackageManager();
        this.componentSpinner = (Spinner) findViewById(R.id.component_spinner1);
        this.componentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.componentNames);
        this.componentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.componentSpinner.setAdapter((SpinnerAdapter) this.componentAdapter);
        loadComponents();
        this.componentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.test.jianshen.ActivityTestActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                ActivityTestActivity.this.findViewById(R.id.send_intent_button1).setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.ActivityTestActivity.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        try {
                            ComponentName selectedComponent = (ComponentName) ActivityTestActivity.this.components.get(position);
                            Intent intent = new Intent();
                            intent.setComponent(selectedComponent);
                            ActivityTestActivity.this.startActivity(intent);
                            Toast.makeText(ActivityTestActivity.this, "Intent sent to " + selectedComponent.getClassName(), 1).show();
                        } catch (Exception e) {
                            Toast.makeText(ActivityTestActivity.this, "Failed to send intent, error: " + e.getMessage(), 1).show();
                        }
                    }
                });
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadComponents() {
        try {
            PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 1);
            this.components.clear();
            this.componentNames.clear();
            if (packageInfo.activities != null) {
                for (PackageItemInfo item : packageInfo.activities) {
                    ComponentName componentName = new ComponentName(packageInfo.packageName, item.name);
                    this.components.add(componentName);
                    this.componentNames.add(item.name);
                }
            }
            this.componentAdapter.notifyDataSetChanged();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading components: " + e.getMessage(), 1).show();
        }
    }
}