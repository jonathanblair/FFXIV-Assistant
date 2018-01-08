/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import api.Search;
import api.searchResult;
/**
 * FXML Controller class
 *
 * @author barab
 */
public class SearchUIController implements Initializable {

    @FXML
    private ComboBox<String> oneSelection;
    @FXML
    private TextField searchText;
    @FXML
    private Button searchButton;
    @FXML
    private ImageView imageDisplay;
    @FXML
    private Label textDisplay;
    
    private Search searchAPI;

    /**
     * Initializes the controller class.
     * One: "all","items","recipes","quests","actions","instances",
     * "achievements","fates","leves","places","gathering","npcs","enemies",
     * "emotes","status","titles","minions","mounts","weather","characters"
     * @param url
     * @param rb
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchAPI = new Search();
        oneSelection.getItems().addAll(Search.ONETYPES);
        textDisplay.setText("");
        lastSearchTime = System.currentTimeMillis();
    }    

    
    static long lastSearchTime;
    static searchResult display;
    
    @FXML
    private void search(ActionEvent event) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - 200 > lastSearchTime){
            display = searchAPI.execute(searchText.getText(), oneSelection.getValue())[0];
            textDisplay.setText(display.getName());
            imageDisplay.setImage(display.getImage());
            lastSearchTime = currentTime;
        }else{
            textDisplay.setText("Please wait before searching again");
        }
    }
    
}