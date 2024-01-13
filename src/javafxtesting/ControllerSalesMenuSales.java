/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtesting;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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


public class ControllerSalesMenuSales {
    
    @FXML
    Label name1;
    
    @FXML
    Label name2;
    
    @FXML
    Label id1;
    
    @FXML
    Label warning;
    
    @FXML
    TextField carPlateTextBox;
    
    @FXML
    TextField custIdTextBox;
    
    @FXML
    TextField salesPriceTextBox;
    
    @FXML
    private TableView<String[]> tableViewSales;

    @FXML
    private TableColumn<String[], String> salesId;

    @FXML
    private TableColumn<String[], String> dateTime;

    @FXML
    private TableColumn<String[], String> carPlate;

    @FXML
    private TableColumn<String[], String> custId;
    
    @FXML
    private TableColumn<String[], String> employeeId;
    
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
        
        salesId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        dateTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        carPlate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        custId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        employeeId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
        
        reader.close();
        
        String csvFilePath = "src/sales.csv";
        try (BufferedReader reader2 = new BufferedReader(new FileReader(csvFilePath))) {
            line = "";
            
            while ((line = reader2.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equals("salesId") && data[4].equals(userid)){
                    tableViewSales.getItems().add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    

    
    @FXML
    private void custsidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_cust.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerSalesMenuCust menu1 = loader.getController();
        menu1.menuname(userid,warningMessage);


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
    private void vehiclesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_vehicle.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerSalesMenuVehicle menu1 = loader.getController();
        menu1.menuname(userid,warningMessage);


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
    private void entersalesbutton(ActionEvent event) throws FileNotFoundException, IOException {
        
        String carPlate = carPlateTextBox.getText();
        String custId = custIdTextBox.getText();
        String employeeId = id1.getText();
        String salesPrice = salesPriceTextBox.getText();
        File sales = new File("src\\sales.csv");
        File vehicle = new File("src\\vehicle.csv");
        File cust = new File("src\\cust.csv");
        
        //generating date and time based on computer time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String formattedDateTime = currentDateTime.format(formatter);
                
        String line;
        
        int lineCounterVehicle = 0;
        int lineCounterSales = 0;
        int i = 0;
        
        //checkers and records
        //checking if the entered car Plate is available or not
        BufferedReader reader = new BufferedReader(new FileReader(vehicle)); 
        boolean vehicle_available = false;
        while((line = reader.readLine()) != null){
            lineCounterVehicle++;
            String[] split_vehicle = line.split(",");
            if (split_vehicle[0].equals(carPlate) && split_vehicle[3].equals("1")){
                vehicle_available = true;
            }
        }
        
        if (vehicle_available == false){
            String userid = id1.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_sales.fxml"));
            String warningMessage = "Vehicle doesn't exist or is not available!";      

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
        
        //record the whole vehicle file
        String[] records_vehicle = new String[lineCounterVehicle];
        
        reader.close();
        
        reader = new BufferedReader(new FileReader(vehicle));
        
        while((line = reader.readLine()) != null){
            records_vehicle[i] = line;
            i++;
        }
        
        reader.close();
        
        

            
        i = 0;
        
        
        
        //checking if the entered custId is already there or not
        reader = new BufferedReader(new FileReader(cust)); 
        boolean cust_available = false;
        
        while((line = reader.readLine()) != null){
            String[] split_cust = line.split(",");
            if (split_cust[0].equals(custId)){
                cust_available = true;
            }
        }        
        if (cust_available == false){
            String userid = id1.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_sales.fxml"));
            String warningMessage = "Customer isn't on record!";      

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
        
        reader.close();
        
        
        //checking if the entered car Plate is already there or not
        reader = new BufferedReader(new FileReader(sales)); 
        
        while((line = reader.readLine()) != null){
            lineCounterSales++; 
            String[] split_sales = line.split(",");
            if (split_sales[2].equals(carPlate)){ 
                String userid = id1.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_sales.fxml"));
                String warningMessage = "Sale is already on record!";      

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
        }
        
        reader.close();
        
        //record the whole sales file
        String[] records_sales = new String[lineCounterSales];
        
        reader = new BufferedReader(new FileReader(sales));
        
        while((line = reader.readLine()) != null){
            records_sales[i] = line;
            i++;
        }
        
        reader.close();
        
       //override the vehicle file with new info
        FileWriter writer = new FileWriter(vehicle, false);
        for (i = 0; i < records_vehicle.length; i++) {
            String[] elements = records_vehicle[i].split(",");
            if (elements.length < 5) {
                  elements = Arrays.copyOf(elements, 5);
                  elements[4] = "";
            }
            if (elements[0].equals(carPlate)) {
                elements[3] = "0";
                elements[4] = salesPrice;
            }
            writer.write(String.join(",", elements) + "\n");
        }
        
        writer.close();
        
        
        
        
        //this is all to write new data in sales
        writer = new FileWriter(sales, true);
        
        String lastLineSales = records_sales[lineCounterSales - 1]; //taking the last record line
        String[] parts = lastLineSales.split(",");
        String prefix = parts[0].substring(0, 1);
        int lastId = Integer.parseInt(parts[0].substring(1));
        int nextId = lastId + 1;
        String formattedNumber = String.format("%04d", nextId);
        
        
        String newline = prefix + formattedNumber + "," + formattedDateTime + "," + carPlate + "," + custId + "," + employeeId;
            
        
        writer.write(newline);
        writer.write("\n");
      
        writer.close();
 
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreensales_sales.fxml"));
        String warningMessage = "Sales recorded succesfully!";      
        
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
    
}
