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
import javafx.scene.Node;

public class JavaFXTesting extends Application{


    private Parent root;
    
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        String warningMessage = "";


        root = loader.load();

        ControllerLogin menu1 = loader.getController();
        menu1.menuname(warningMessage);


        //root = FXMLLoader.load(getClass().getResource("mainscreenmanagement.fxml"));
        Scene scene = new Scene(root);

        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css); //if got other scene just use this template

        Image icon = new Image("LogoOnly.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("LeCars Management System");

        primaryStage.setScene(scene);
        primaryStage.show();}


    
}
