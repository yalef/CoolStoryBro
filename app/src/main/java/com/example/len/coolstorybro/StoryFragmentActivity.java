package com.example.len.coolstorybro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class StoryFragmentActivity extends AppCompatActivity {
    private FragmentTransaction ft;
    private Toolbar tb;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = getSupportFragmentManager().beginTransaction();
            setSupportActionBar(tb);
            switch (item.getItemId()) {
                case R.id.search_navigation:
                        ft.replace(R.id.fragment_container,new SearchFragment()).commit();
                        getSupportActionBar().setTitle("Поиск");
                    return true;
                case R.id.tags_navigation:
                        ft.replace(R.id.fragment_container,new TagsFragment()).commit();
                        getSupportActionBar().setTitle("Тэги");
                    return true;
                case R.id.random_navigation:
                        ft.replace(R.id.fragment_container,new RandomStoryFragment()).commit();
                        getSupportActionBar().setTitle("Рандом");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_fragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,new TagsFragment()).commit();

        tb = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Тэги");
    }
}
