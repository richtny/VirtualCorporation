/*
 * JavaFX Application for Virtual Corporation
 * Uses Button, ComboBox, and JSON Object
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.io.*;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.net.MalformedURLException;

import java.net.URL;

import org.json.JSONObject;
import org.json.JSONException;

import javax.json.*;
import javax.json.spi.*;
import javax.json.stream.*;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Collection;

/**
 * @author DayTr
 */
public class JavaFXApplication1 extends Application {
    
    private class MyJsonClass {
        Map<String, String> headers;
        Map<String, String> args;
        String origin;
        String url;
    }
    
    public static boolean buttonClicked = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        //Create a button and a tooltip
        Button btn = new Button();
        Tooltip tt = new Tooltip("Camel says Hello World!!!");
        Tooltip.install(btn, tt);
        
        //Add image to button
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\DayTr\\OneDrive\\Documents\\NetBeansProjects\\JavaFXApplication1\\build\\Camel.png");
            Image image = new Image(inputstream);
            btn.setGraphic(new ImageView(image));
        }
        catch (FileNotFoundException e) {
            System.out.println("Image not found for button.");
        }
        
        //Create an Observable List and add it to a Combo Box
        ObservableList<String> options1 = 
            FXCollections.observableArrayList(
                "Item A",
                "Item B",
                "Item C",
                "Item D",
                "Item E",
                "Item F",
                "Item G"
            );
        final ComboBox comboBox = new ComboBox(options1);
        GridPane grid = new GridPane();
        
        //Set button clicking action
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Event to retrieve JSON Object from public website
                //String url = "https://www.nytimes.com/";
                //String jsonData = readUrl(url);
                if (buttonClicked == false) {
                    buttonClicked = true;
                    System.out.println("setting to option 2");
                    try {
                    URL url = new URL("https://httpbin.org/get?color=green&shape=oval&day=tuesday&font=TimesNewRoman&fontSize=12&month=May&year=2017");
                    InputStreamReader reader = new InputStreamReader(url.openStream());
                    MyJsonClass json = new Gson().fromJson(reader, MyJsonClass.class);

                    //Create an Observable List, modify it with JSON objects, and add it to a Combo Box
                    ObservableList<String> options2 = FXCollections.observableArrayList();
                    Collection collection = json.args.values();
                    options2.setAll(collection);
                    comboBox.setItems(options2);
                    
                    }
                    catch (MalformedURLException e) {
                        System.out.println("MalformedURLException caught.");
                    }
                    catch (IOException e) {
                        System.out.println("IOException caught.");
                    }
                }
                else {
                    buttonClicked = false;
                    System.out.println("setting to option 1");
                    comboBox.setItems(options1);
                }
            }
        });
        
        // Create a Grid Pane and add the Button and Combo Box
        grid.setVgap(15);
        grid.setHgap(5);
        grid.setPadding(new Insets(20,20,20,20));
        grid.add(btn, 0, 0);
        grid.add(comboBox, 0, 1);
        
        // Add Grid Pane to the scene's group
        Group group = new Group();
        Scene scene = new Scene(group);
        group.getChildren().add(grid);
        
        // Set and show the stage
        stage.setScene(scene);
        stage.show();
    }
}
