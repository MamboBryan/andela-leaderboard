package com.andelaexample.leaderboard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.andelaexample.leaderboard.R;
import com.andelaexample.leaderboard.adapter.ViewPagerAdapter;
import com.andelaexample.leaderboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initViews();
    }

    private void initViews() {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        binding.viewPagerMain.setAdapter(viewPagerAdapter);
        binding.tabLayoutMain.setupWithViewPager(binding.viewPagerMain);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_submit, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_item_submit) {
            Intent intent = new Intent(this, SubmitActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}