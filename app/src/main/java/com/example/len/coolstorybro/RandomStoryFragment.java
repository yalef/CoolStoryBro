package com.example.len.coolstorybro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.len.coolstorybro.Parser;
import com.example.len.coolstorybro.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


public class RandomStoryFragment extends Fragment {
    ProgressBar pb;
    CardView cv1,cv2;
    TextView title_story,content_story;
    String title;
    String content;

    Context context;

    public RandomStoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_random_story, container, false);
        View view = inflater.inflate(R.layout.fragment_random_story, container, false);

        pb = (ProgressBar) view.findViewById(R.id.progressBar);
        cv1 = (CardView) view.findViewById(R.id.cv1);
        cv2 = (CardView) view.findViewById(R.id.cv2);
        title_story = (TextView) view.findViewById(R.id.title_story);
        content_story = (TextView) view.findViewById(R.id.story_content);
        title = " ";
        content = " ";

        context = getContext();

        if(context!= null){
            if (isOnline(context)) {
                ProgressTask pt = new ProgressTask();
                pt.execute();
            }else{
                cv1.setVisibility(View.INVISIBLE);
                cv2.setVisibility(View.INVISIBLE);
                title_story.setVisibility(View.INVISIBLE);
                content_story.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Проверьте ваше подключение к сети.", Toast.LENGTH_SHORT).show();
            }
        }


        return view;
    }
    private class ProgressTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            cv1.setVisibility(View.INVISIBLE);
            cv2.setVisibility(View.INVISIBLE);
            title_story.setVisibility(View.INVISIBLE);
            content_story.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
            //pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorRed), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        @Override
        protected Void doInBackground(Void... voids) {
                Story story = Parser.getRandomStory();
                title = story.getTitle();
                content = story.getContent();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cv1.setVisibility(View.VISIBLE);
            cv2.setVisibility(View.VISIBLE);
            title_story.setVisibility(View.VISIBLE);
            content_story.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            title_story.setText(title);
            content_story.setText(content);
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
