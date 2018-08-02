package com.example.len.coolstorybro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class StoryListActivity extends AppCompatActivity {

    String tag;
    private Toolbar tb;
    private RecyclerView rv;
    private ProgressBar pb;
    StoryListAdapter adapter;
    ArrayList<Story> stories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        tb = (Toolbar) findViewById(R.id.toolbar_main);
        rv = (RecyclerView) findViewById(R.id.rv_storylist);
        pb = (ProgressBar) findViewById(R.id.pb_storylist);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        tag = getIntent().getStringExtra("tag"); // Получаем тэг
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(tag); // Отображаем в тулбаре тэг
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Кнопка домой


        if(isOnline(StoryListActivity.this)){
            ProgressTask pt = new ProgressTask();
            pt.execute(); // Запускаем работу потока
        }else{
            Toast.makeText(this, "Проверьте ваше подключение к сети", Toast.LENGTH_SHORT).show();
        }
    }

    private class ProgressTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            // Начало загрузки
            rv.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stories = Parser.getStoriesByTag(tag);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pb.setVisibility(View.INVISIBLE);
            rv.setVisibility(View.VISIBLE);
            adapter = new StoryListAdapter(StoryListActivity.this,stories); // Установка адаптера
            rv.setAdapter(adapter);
        }
    }

    // Обработчик нажатий на кнопки тулбара
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}
