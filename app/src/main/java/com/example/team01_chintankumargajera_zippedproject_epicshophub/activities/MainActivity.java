package com.example.team01_chintankumargajera_zippedproject_epicshophub.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {



    Fragment homefragment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        homefragment = new HomeFragment();
        loadFragment(homefragment);




    }

    private void loadFragment(Fragment homefragment) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homefragment);
        transaction.commit();

    }
}