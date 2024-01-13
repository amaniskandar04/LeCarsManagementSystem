/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtesting;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ControllerSalesMenuCust {
    
    @FXML
    Label name1;
    
    @FXML
    Label name2;
    
    @FXML
    Label id1;
    
    @FXML
    Label warning;
    
    @FXML
    TextField custNameTextBox;
    
    @FXML
    TextField postcodeTextBox;
    
    @FXML
    TextField phoneNumTextBox;
    
    @FXML
    private TableView<String[]> tableViewCust;

    @FXML
    private TableColumn<String[], String> custId;

    @FXML
    private TableColumn<String[], String> custName;

    @FXML
    private TableColumn<String[], String> phoneNum;

    @FXML
    private TableColumn<String[], String> postcode;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void menuname(String userid, String warningMessage) throws IOException{
        
        File employee = new File("src\\employee.csv");
        BufferedReader reader = new BufferedReader(new FileReader(employee));
        String line = "";
        int i = 0;
        
        String name = "";
        
        while((line = reader.readLine()) != null){
            if (i == 0){
                i++;
            }
            else{
                String[] row = line.split(",");
                if (row[0].equals(userid)){
                    name = row[1];
                    break;
                }

                    
            }
        }
        name1.setText(name);
        name2.setText(name);
        id1.setText(userid);
        warning.setText(warningMessage);
        
        line = "";
        int lineCounter = 0;
        
        BufferedReader reader_sales = new BufferedReader(new FileReader("src\\sales.csv"));
        
        while((line = reader_sales.readLine()) != null){
          String[] split_poggers = line.split(",");
          if(split_poggers[4].equals(userid)){
              lineCounter++;
          }
            
        }
        
        reader_sales = new BufferedReader(new FileReader("src\\sales.csv"));
        
        i = 0;
        String[] record_sales = new String[lineCounter];
        line = "";
        
        while((line = reader_sales.readLine()) != null){
          String[] split_epic = line.split(",");
          if(split_epic[4].equals(userid)){
             record_sales[i] = split_epic[3];
             i++; 
          }
        }
        
        reader_sales.close();
        
        custId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        custName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        phoneNum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        postcode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        
        
        String csvFilePath = "src/cust.csv";
        try (BufferedReader reader2 = new BufferedReader(new FileReader(csvFilePath))) {
            line = "";
            while ((line = reader2.readLine()) != null) {
                String[] data = line.split(",");
                for(int k = 0; k < record_sales.length; k++){
                    if (!data[0].equals("custId") && data[0].equals(record_sales[k])){
                        tableViewCust.getItems().add(data);
                        break;
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    @FXML
    private void vehiclesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_vehicle.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerSalesMenuVehicle menu1 = loader.getController();
        menu1.menuname(userid, warningMessage);


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
    
    @FXML
    private void salessidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_sales.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerSalesMenuSales menu1 = loader.getController();
        menu1.menuname(userid, warningMessage);


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
    
    
    
    @FXML
    private void entercustbutton(ActionEvent event) throws IOException {
        
        String custName = custNameTextBox.getText();
        String postcode = postcodeTextBox.getText();
        String phoneNum = phoneNumTextBox.getText();
        File cust = new File("src\\cust.csv");
        
        String line;
        int lineCounter = 0;
        int i = 0;
        
        BufferedReader reader = new BufferedReader(new FileReader(cust));
        
        while((line = reader.readLine()) != null){
            lineCounter++;
            String[] checker = line.split(",");
            if (checker[1].equals(custName) && checker[3].equals(postcode) && checker[2].equals(phoneNum)){
                String userid = id1.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_cust.fxml"));
                String warningMessage = "Customer already exists!";      

                root = loader.load();
                ControllerSalesMenuCust menu1 = loader.getController();
                menu1.menuname(userid, warningMessage);


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
        
        String[] records = new String[lineCounter];
        
        reader = new BufferedReader(new FileReader(cust));
        
        while((line = reader.readLine()) != null){
            records[i] = line;
            i++;
        }
        
        reader.close();
        
        String lastline = records[lineCounter - 1];
        
        FileWriter writer = new FileWriter(cust, true);
        

        String[] parts = lastline.split(",");
        String prefix = parts[0].substring(0, 1);
        int lastId = Integer.parseInt(parts[0].substring(1));
        int nextId = lastId + 1;
        String formattedNumber = String.format("%04d", nextId);
        String newline = prefix + formattedNumber + "," + custName + "," + phoneNum + "," + postcode;
            
        
        writer.write(newline); 
        writer.write("\n");
        writer.close();
        
        String userid = id1.getText();
        String warningMessage = "";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_cust.fxml"));
              
        
        root = loader.load();
        ControllerSalesMenuCust menu1 = loader.getController();
        menu1.menuname(userid, warningMessage);


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
