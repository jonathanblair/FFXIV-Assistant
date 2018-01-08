/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import javafx.scene.image.Image;

/**
 *
 * @author barab
 */
public class searchResult {
    String name;
    Image img;
    public searchResult(String name, Image img){
        this.name = name;
        this.img = img;
    }
    public searchResult(String name){
        this.name = name;
        try{
            this.img = new Image(getClass().getResourceAsStream("/resources/images/notFound128.png"));
        }catch(Exception e){
            System.err.println("Error on load resource for image icon");
            System.err.println(e);
        }
    }
    public String getName(){
        return this.name;
    }
    public Image getImage(){
        return this.img;
    }
}
