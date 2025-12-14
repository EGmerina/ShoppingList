package com.example.shoppinglist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(new MainListFragment(), getString(R.string.app_name));

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        ImageButton btnMenu = findViewById(R.id.btn_menu);

        btnMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        findViewById(R.id.btn_nav_start).setOnClickListener(v -> {
            showFragment(new MainListFragment(), getString(R.string.app_name));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        findViewById(R.id.btn_nav_history).setOnClickListener(v -> {
            showFragment(new HistoryFragment(), getString(R.string.history_title));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        findViewById(R.id.btn_nav_diagram).setOnClickListener(v -> {
            showFragment(new DiagramFragment(), getString(R.string.analysis_title));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        findViewById(R.id.btn_nav_exit).setOnClickListener(v -> finish());
    }


    private void showFragment(Fragment fragment, String title) {
        if (findViewById(R.id.fragment_container) == null) {
            System.out.println("can't find fragment_container");
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }
}