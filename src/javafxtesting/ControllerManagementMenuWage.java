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
import java.text.DecimalFormat;
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
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ControllerManagementMenuWage {
    
    @FXML
    Label name1;
    
    @FXML
    Label name2;
    
    @FXML
    Label id1;
    
    @FXML
    private TableView<String[]> tableViewWage;

    @FXML
    private TableColumn<String[], String> employeeId;

    @FXML
    private TableColumn<String[], String> basicAllowance;

    @FXML
    private TableColumn<String[], String> commission;
    
    @FXML
    private TableColumn<String[], String> bonus;
    
    @FXML
    private TableColumn<String[], String> totalPayout;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void menuname(String userid) throws IOException{
        
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
        reader.close();
        
        employeeId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        basicAllowance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        commission.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        bonus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        totalPayout.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
        
        
        reader = new BufferedReader(new FileReader(employee));
        
        String[] employeeId = new String[100];
        int[] employeeIdProfit = new int[100];
        String[] employeeStatus = new String[100];
        int[] employeeCarAmount = new int[100];
        int employeeNumber = 0;
        i = 0;
        
        while((line = reader.readLine()) != null){
            if (i == 0){
                i++;
            }
            else{
                String[] row = line.split(",");
                employeeId[employeeNumber] = row[0];
                employeeStatus[employeeNumber] = row[2];
                employeeNumber++;
                }
        }
        
        try(BufferedReader reader_sales = new BufferedReader(new FileReader("src\\sales.csv"))){
            while((line = reader_sales.readLine()) != null){ //for each line
                if (i == 0){
                i++;
                }
                else{
                    String[] data_sales = line.split(",");
                    int j = 0;
                    while (j <= employeeNumber) { //checks which employee is there
                        if (data_sales[4].equals(employeeId[j])) { // found the employee
                            String currentCarPlate = data_sales[2];
                            BufferedReader reader_vehicle = new BufferedReader(new FileReader("src\\vehicle.csv"));
                            String line2;
                            while ((line2 = reader_vehicle.readLine()) != null) {//find the vehicle salesPrice based on the currentCarPlate
                                String[] data_vehicle = line2.split(",");
                                if (data_vehicle[0].equals(currentCarPlate) && data_vehicle.length == 5) {
                                    employeeIdProfit[j] += Integer.parseInt(data_vehicle[4]); //add the profit tally of said employee
                                    employeeCarAmount[j]++;
                                }
                            }
                        }
                        j++;
                    }
                }
            }
        }
        
        int j = 0;
        
        
        while(j <= employeeNumber && employeeId[j] != null){
            String[] data = new String[5];
            data[0] = employeeId[j];
            if(employeeStatus[j].equals("0")){
               data[1] = "7250"; //base + allowance
               DecimalFormat decimalFormat = new DecimalFormat("#.00");
               double commissionNum = (employeeIdProfit[j])/100.00;
               data[2] = decimalFormat.format(commissionNum);
               if(employeeIdProfit[j] >= 1000000 || employeeCarAmount[j] >= 15){
                   data[3] = "500";
               }
               
               else{
                   data[3] = "0";
               }
               
               data[4] = decimalFormat.format(7250 + Double.parseDouble(data[3]) + commissionNum);
               
            }
            
            else if(employeeStatus[j].equals("1")){
                data[1] = "12750";
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                double commissionNum = 0.00;
                if(employeeIdProfit[j] <= 800000){
                    commissionNum = (employeeIdProfit[j]/100);}
                else if(employeeIdProfit[j] <= 1600000){
                    commissionNum = (employeeIdProfit[j]/100)*1.15;}
                else if(employeeIdProfit[j] <= 2500000){
                    commissionNum = (employeeIdProfit[j]/100)*1.25;}
                else if(employeeIdProfit[j] > 2500000){
                    commissionNum = (employeeIdProfit[j]/100)*1.35;}
                data[2] = decimalFormat.format(commissionNum);
                data[3] = "N/A";
                data[4] = decimalFormat.format(12750 + commissionNum);
            }
            tableViewWage.getItems().add(data);
            j++;
        }
        
        
        
    }
            
    
    @FXML
    private void SalesImportButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data File");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String destinationPath = "src"; // Specify the desired directory within src
            String destinationFileName = "sales.csv"; // Specify the desired file name

            File destinationDirectory = new File(destinationPath);
            
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }
        
        File destinationFile = new File(destinationPath, destinationFileName);

        try {
            String[] employeeData = DataImporter.importData(file);
            DataExporter.exportData(employeeData, destinationFile);    
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        }
    }
    
    @FXML
    private void CustomerImportButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data File");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String destinationPath = "src"; // Specify the desired directory within src
            String destinationFileName = "cust.csv"; // Specify the desired file name

            File destinationDirectory = new File(destinationPath);
            
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }
        
        File destinationFile = new File(destinationPath, destinationFileName);

        try {
            String[] employeeData = DataImporter.importData(file);
            DataExporter.exportData(employeeData, destinationFile);    
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        }
    }

    @FXML
    private void VehicleImportButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Data File");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String destinationPath = "src"; // Specify the desired directory within src
            String destinationFileName = "vehicle.csv"; // Specify the desired file name

            File destinationDirectory = new File(destinationPath);
            
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }
        
        File destinationFile = new File(destinationPath, destinationFileName);

        try {
            String[] employeeData = DataImporter.importData(file);
            DataExporter.exportData(employeeData, destinationFile);    
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        }
    }

    @FXML
    private void financesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement.fxml"));
              
        
        root = loader.load();
        ControllerManagementMenu menu1 = loader.getController();
        menu1.menuname(userid);


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
    private void custsidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_cust.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerManagementMenuCust menu1 = loader.getController();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_sales.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerManagementMenuSales menu1 = loader.getController();
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
    private void employeesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_employee.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerManagementMenuEmployee menu1 = loader.getController();
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
    private void vehiclesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_vehicle.fxml"));
        String warningMessage = "";      
        
        root = loader.load();
        ControllerManagementMenuVehicle menu1 = loader.getController();
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
