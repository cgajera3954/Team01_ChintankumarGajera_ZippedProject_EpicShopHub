package com.example.team01_chintankumargajera_zippedproject_epicshophub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Fragment homefragment ;
    Toolbar toolbar;

    FirebaseAuth auth;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // Create a custom toggle that handles both the drawer and specific menu items
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close) {
            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Toggle the drawer when the menu icon is clicked
                if (id == android.R.id.home) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                    return true;
                }

                // Handle other menu items here if needed
                return super.onOptionsItemSelected(item);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        toolbar = findViewById(R.id.home_toolbar);
        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        navigationView.setNavigationItemSelectedListener(this);

        homefragment = new HomeFragment();
        loadFragment(homefragment);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Toggle the drawer and handle specific menu items
        return actionBarDrawerToggle.onOptionsItemSelected(item) || handleOtherMenuItems(item);
    }
    private boolean handleOtherMenuItems(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.menu_my_cart) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    private void loadFragment(Fragment homefragment) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homefragment);
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_logout) {
            auth.signOut();
            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        } else if (itemId == R.id.menu_my_cart) {
            Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(cartIntent);
        }

        // Close the drawer after handling the click
        drawerLayout.closeDrawer(GravityCompat.START);

        return true; // Return true to indicate that the click is handled

    }
}