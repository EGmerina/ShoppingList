package com.example.shoppinglist;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jspecify.annotations.NonNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
// 1. Находим DrawerLayout
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ImageButton optionsButton = findViewById(R.id.options_button);
        NavigationView navigationView = findViewById(R.id.navigation_view);

// 2. Слушатель нажатия на кнопку в тулбаре
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открываем Drawer (меню) с левой стороны
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

// 3. Обработка нажатий на пункты внутри самого выдвижного меню
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_settings) {
                    // Действие для настроек
                }

                // Закрываем меню после выбора пункта
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }
}