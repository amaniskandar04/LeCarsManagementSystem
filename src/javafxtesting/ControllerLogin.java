
package javafxtesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class ControllerLogin {
    @FXML
    TextField loginuserbox;
    
    @FXML
    PasswordField loginpasswordbox;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void loginbutton(ActionEvent event) throws IOException{
        
        String userid = loginuserbox.getText();
        String password = loginpasswordbox.getText().trim();
        File employee = new File("src\\employee.csv");
        BufferedReader reader = new BufferedReader(new FileReader(employee));
        String line = "";
        int i = 0;
        
        while((line = reader.readLine()) != null){
            if (i == 0){
                i++;
            }
            else{
                String[] row = line.split(",");
                if (row[0].equals(userid) && row[3].equals(password) && row[2].equals("1")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement.fxml"));
              
        
                    root = loader.load();

                    ControllerManagementMenu menu1 = loader.getController();
                    menu1.menuname(userid);

                    //root = FXMLLoader.load(getClass().getResource("mainscreenmanagement.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);

                    String css = this.getClass().getResource("application.css").toExternalForm();
                    scene.getStylesheets().add(css); //if got other scene just use this template

                    Image icon = new Image("LogoOnly.png");
                    stage.getIcons().add(icon);
                    stage.setTitle("LeCars Management System");

                    stage.setScene(scene);
                    stage.show();
                    
                }
                
                else if (row[0].equals(userid) && row[3].equals(password) && row[2].equals("0")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales.fxml"));
              
        
                    root = loader.load();

                    ControllerManagementMenu menu1 = loader.getController();
                    menu1.menuname(userid);

                    //root = FXMLLoader.load(getClass().getResource("mainscreenmanagement.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);

                    String css = this.getClass().getResource("application.css").toExternalForm();
                    scene.getStylesheets().add(css); //if got other scene just use this template

                    Image icon = new Image("LogoOnly.png");
                    stage.getIcons().add(icon);
                    stage.setTitle("LeCars Management System");

                    stage.setScene(scene);
                    stage.show();
                    
                }
            }
        }
        
        reader.close();
    }
    
    public void registrationbutton(ActionEvent event) throws IOException{
        String userid = loginuserbox.getText();
        String password = loginpasswordbox.getText().trim();
        File employee = new File("src\\employee.csv");
        FileWriter myWriter = new FileWriter(employee,true);
        
        myWriter.write(userid + "," + "name" + "," + 0 + "," + password);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales.fxml"));
              
        
        root = loader.load();

        ControllerManagementMenu menu1 = loader.getController();
        menu1.menuname(userid);

        //root = FXMLLoader.load(getClass().getResource("mainscreenmanagement.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css); //if got other scene just use this template

        Image icon = new Image("LogoOnly.png");
        stage.getIcons().add(icon);
        stage.setTitle("LeCars Management System");

        stage.setScene(scene);
        stage.show();        
        
        myWriter.close();

    }

}
