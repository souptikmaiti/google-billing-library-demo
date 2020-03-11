package com.souptik.maiti.devworld.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private Button btnProducts, btnSubscriptions;
    private FrameLayout fragmentContainer;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnProducts = findViewById(R.id.btn_products);
        btnSubscriptions = findViewById(R.id.btn_subscriptions);
        fragmentContainer = findViewById(R.id.fragment_container);

        manager = getSupportFragmentManager();

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = new ProductsFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        btnSubscriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment fragment = new SubscriptionsFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
    }
}
