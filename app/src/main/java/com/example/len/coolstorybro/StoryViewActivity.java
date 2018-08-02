package com.example.len.coolstorybro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class StoryViewActivity extends AppCompatActivity {

    private Toolbar tb;
    private CardView cv1,cv2;
    private TextView title, content;
    private ProgressBar pb;

    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_view);

        cv1 = (CardView) findViewById(R.id.cv1_storyview);
        cv2 = (CardView) findViewById(R.id.cv2_storyview);
        title = (TextView) findViewById(R.id.title_story_storyview);
        content = (TextView) findViewById(R.id.story_content_storyview);
        pb = (ProgressBar) findViewById(R.id.pb_storyView);
        tb = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Паста");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Кнопка домой

        number = getIntent().getStringExtra("number");

        //Toast.makeText(this, number, Toast.LENGTH_SHORT).show();

        if(isOnline(StoryViewActivity.this)){
            ProgressTask pt = new ProgressTask();
            pt.execute();
        }else{
            cv1.setVisibility(View.INVISIBLE);
            cv2.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
            content.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Проверьте ваше подключение к сети", Toast.LENGTH_SHORT).show();
        }
    }

    private class ProgressTask extends AsyncTask<Void,Void,Void>{
        String content_str, title_str;
        //String number = getIntent().getStringExtra("number");

        @Override
        protected void onPreExecute() {
            cv1.setVisibility(View.INVISIBLE);
            cv2.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
            content.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);

            //Toast.makeText(StoryViewActivity.this, "Number is: "+number.substring(1,number.length()), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            title_str = Parser.getStoryByNumber(number.substring(1,number.length())).getTitle();
            content_str = Parser.getStoryByNumber(number.substring(1,number.length())).getContent();
            System.out.println("ВНМИАНИЕ "+title_str);
            System.out.println("ВНМИАНИЕ "+content_str);
            //Toast.makeText(StoryViewActivity.this, title_str, Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            title.setText(title_str);
            content.setText(content_str);

            cv1.setVisibility(View.VISIBLE);
            cv2.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
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
