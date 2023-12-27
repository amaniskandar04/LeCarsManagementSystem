/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javafxtesting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class JavaFXTesting extends Application{

    
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));       
        Scene scene1 = new Scene(root);
        
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene1.getStylesheets().add(css); //if got other scene just use this template
        
        Image icon = new Image("LogoOnly.png");       
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("LeCars Management System");
        
        
        primaryStage.setScene(scene1);
        primaryStage.show();
        }
        
        catch (Exception e){
            e.printStackTrace();
        }
        
    }


    
}
