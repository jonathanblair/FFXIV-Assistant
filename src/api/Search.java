/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author barab
 */
public class Search {
    private static final String APIURL = "http://api.xivdb.com/search?";
    public static final String[] ONETYPES = new String[]{"all","items","recipes","quests","actions","instances","achievements","fates","leves","places","gathering","npcs","enemies","emotes","status","titles","minions","mounts","weather","characters"};
    private long timer;
    private static searchResult timeout;
    public Search(){
        this.timer = System.currentTimeMillis();
        timeout = new searchResult("Please wait before searching again");
    }
    public searchResult[] execute(String query, String type){
        if(System.currentTimeMillis() - timer < 200){
            return new searchResult[]{timeout};
        }else{
            try{
   URL search = new URL(APIURL + queryFormat(query, type));
                String urlFetch = fetchFromURL(search);
                System.out.println(urlFetch);
            }catch(MalformedURLException e){
                System.err.println("Error on search: ");
                System.err.println(e);
            }
            timer = System.currentTimeMillis();
            return new searchResult[]{new searchResult("This is a valid search!")};
        }
    }
    
    public String fetchFromURL(URL address){
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(address.openStream()))) {
            String out = inputStream.readLine(); //api is made of 1 line returns, so we'll just use this  
            inputStream.close();
            return out;
        } catch (IOException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }

    private String queryFormat(String query, String type) {
        if(query == null || query.equals("")){
            if(type == null || type.equals("all")) return "";
            else return "one=" + type;
        }else{
            if(type == null || type.equals("all")) return "string=" + query;
            else return "string=" + type + "&one=" + type;
        }
    }
}
