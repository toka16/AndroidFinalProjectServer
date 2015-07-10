/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import java.util.Date;
import model.json.News;

/**
 *
 * @author toka
 */
public class NewsManager {
    private ArrayList<News> newses;
    
    private static NewsManager manager;
    
    private int version;
    
    private NewsManager(){
        version = 1;
        
        newses = new ArrayList<>();
        int num = 25;
        for(int i=0; i<num; i++){
            News news = new News("name"+i, "description"+i);
            news.from_date = new Date(115, 3, 30-i).getTime();
            news.to_date = news.from_date + 20*24*60*60*1000;  //after 20 days
            news.id = i;
            newses.add(news);
        }
        
    }

    public static NewsManager getInstance() {
        if(manager == null)
            manager = new NewsManager();
        
        return manager;
    }
    
    public int getVersion(){
        return version;
    }
    

    public News[] getNews() {
        News[] arr = new News[newses.size()];
        return newses.toArray(arr);
    }
    
    public boolean addNews(News news){
        News n = findNews(news.name);
        if(n != null)
            return false;
        news.id = newses.size()+1;
        version++;
        return newses.add(news);
    }

    public void delete(int id) {
        News news = findNews(id);
        if(news != null){
            newses.remove(news);
            version++;
        }
    }
    
    private News findNews(String name){
        for (News news : newses) {
            if (news.name.equals(name)) {
                return news;
            }
        }
        return null;
    }
    
    private News findNews(int id){
        for (News news : newses) {
            if (news.id == id) {
                return news;
            }
        }
        return null;
    }

    public boolean updateNews(News news) {
        News n = findNews(news.id);
        if(n == null)
            return false;
        
        newses.remove(n);
        newses.add(news);
        version++;
        return true;
    }
    
}
