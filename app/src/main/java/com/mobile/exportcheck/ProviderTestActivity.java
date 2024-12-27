package com.mobile.exportcheck;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ProviderTestActivity extends Activity {
    private ArrayAdapter<String> componentAdapter;
    private Spinner componentSpinner;
    private PackageManager packageManager;
    private String packageName;
    private ArrayList<String> componentNames = new ArrayList<>();
    private ArrayList<ComponentName> components = new ArrayList<>();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_test);
        this.packageName = getIntent().getStringExtra("packageName");
        this.packageManager = getPackageManager();
        this.componentSpinner = (Spinner) findViewById(R.id.component_spinner_provider);
        this.componentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.componentNames);
        this.componentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.componentSpinner.setAdapter((SpinnerAdapter) this.componentAdapter);
        loadComponents();
        this.componentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.test.jianshen.ProviderTestActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                ProviderTestActivity.this.findViewById(R.id.send_intent_button_provider).setOnClickListener(new View.OnClickListener() { // from class: com.test.jianshen.ProviderTestActivity.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        try {
                            ComponentName selectedComponent = (ComponentName) ProviderTestActivity.this.components.get(position);
                            String authority = selectedComponent.getPackageName() + ".provider";
                            Uri uri = Uri.parse("content://" + authority);
                            ContentResolver resolver = ProviderTestActivity.this.getContentResolver();
                            Cursor cursor = resolver.query(uri, null, null, null, null);
                            if (cursor == null) {
                                Toast.makeText(ProviderTestActivity.this, "Query failed on " + authority, 1).show();
                            } else {
                                cursor.close();
                                Toast.makeText(ProviderTestActivity.this, "Query successful on " + authority, 1).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(ProviderTestActivity.this, "Failed to query, error: " + e.getMessage(), 1).show();
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
            PackageInfo packageInfo = this.packageManager.getPackageInfo(this.packageName, 8);
            this.components.clear();
            this.componentNames.clear();
            if (packageInfo.providers != null) {
                for (PackageItemInfo item : packageInfo.providers) {
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