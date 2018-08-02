package com.example.len.coolstorybro;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static Story getRandomStory(){
        String link = "https://pastach.ru/p/random";
        String title = "";
        String story = "";
        Story result = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Element title_text = doc.getElementsByClass("header-title").get(0);
            title += title_text.text();
            Element story_text = doc.getElementsByClass("paste-content").get(0);
            story += story_text.text();
            result = new Story(title,null,story);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return result;
    }
    public static ArrayList<String> getTags(){
        String link = "https://pastach.ru/tag";
        ArrayList<String> result = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements hrefs = doc.getElementsByTag("a");
            for(int i = 0;i<hrefs.size();i++){
                if(hrefs.get(i).attr("href").contains("/tag/")){
                    result.add(hrefs.get(i).attr("href"));
                    System.out.println("ВНИМАНИЕ!!! "+ hrefs.get(i).attr("href"));
                }
            }
        } catch (IOException e) {
            System.out.println("CONNECTION HAS BEEN LOST");
            e.printStackTrace();
        }
        return result;
    }
    public static ArrayList<Story> getStoriesByTag(String tag){
        ArrayList<Story> result = new ArrayList<>();
        String link = "https://pastach.ru";
        ArrayList<String> numbers_list = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(link+tag).get();
            Element title = doc.getElementsByTag("h1").get(0);
            Elements story_titles = doc.getElementsByTag("h3");
            Elements story_numbers = doc.getElementsByTag("a");
            Elements story_contents = doc.getElementsByClass("paste-content");
            System.out.println(title.text());
            for(int j = 0;j<story_numbers.size();j++){
                if(story_numbers.get(j).text().contains("#")){
                    numbers_list.add(story_numbers.get(j).text());
                }
            }
            for(int i = 0;i<story_titles.size();i++){
                Story result_item = new Story(story_titles.get(i).text(),numbers_list.get(i),
                        story_contents.get(i).text());
                result.add(result_item);
                System.out.println(numbers_list.get(i)+"---"+story_titles.get(i).text());

            }
        } catch (IOException e) {
            result.add(new Story("CONNECTION HAS BEEN LOST","CONNECTION HAS BEEN LOST",null));
            e.printStackTrace();
        }
        return result;
    }
    public  static Story getStoryByNumber(String number){
        String link = "https://pastach.ru/p/";
        Story result = null;
        try {
            Document doc  = Jsoup.connect(link+number).get();
            Element title = doc.getElementsByClass("header-title").get(0);
            Element story = doc.getElementsByClass("paste-content").get(0);
/*            System.out.println("Title: \n"+title.text());
            System.out.println("Story: \n"+story.text());*/
            result = new Story(title.text(),number,story.text());
        } catch (IOException e) {
            System.out.println("CONNECTION HAS BEEN LOST");
            e.printStackTrace();
        }
        return result;
    }
    public static ArrayList<Story> searchStory(String query){
        String link = "https://pastach.ru/search?q=";
        ArrayList<Story> result = new ArrayList<>();
        ArrayList<String> numbers_list = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(link+query).get();
            Elements title = doc.getElementsByTag("h1");
            Elements story_titles = doc.getElementsByTag("h3");
            Elements story_numbers = doc.getElementsByTag("a");
            Elements story_contents = doc.getElementsByClass("paste-content");
            System.out.println(title.text());
            for(int j = 0;j<story_numbers.size();j++){
                if(story_numbers.get(j).text().contains("#")){
                    numbers_list.add(story_numbers.get(j).text());
                }
            }
            for(int i = 0;i<story_titles.size();i++){
                System.out.println(numbers_list.get(i)+"---"+story_titles.get(i).text());
                result.add(new Story(story_titles.get(i).text(),numbers_list.get(i),story_contents.get(i).text()));
            }
        } catch (IOException e) {
            System.out.println("CONNECTION HAS BEEN LOST");
            e.printStackTrace();
        }
        return result;
    }
}
