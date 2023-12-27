/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtesting;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ControllerManagementMenuVehicle {
    
    @FXML
    Label name1;
    
    @FXML
    Label name2;
    
    @FXML
    Label id1;
    
    @FXML
    private TableView<String[]> tableViewVehicle;

    @FXML
    private TableColumn<String[], String> carPlate;

    @FXML
    private TableColumn<String[], String> carModel;

    @FXML
    private TableColumn<String[], String> acquirePrice;

    @FXML
    private TableColumn<String[], String> carStatus;
    
    @FXML
    private TableColumn<String[], String> salesPrice;
    
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
    }
    
    @FXML
    private void initialize() {
        
        carPlate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
    carModel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
    acquirePrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
    carStatus.setCellValueFactory(cellData -> new SimpleStringProperty(handleCarStatus(cellData.getValue()[3])));
    salesPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().length > 4 ? cellData.getValue()[4] : ""));

        String csvFilePath = "src/vehicle.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (!data[0].equals("carPlate")){
                    tableViewVehicle.getItems().add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String handleCarStatus(String status) {
    if ("1".equals(status)) {
        return "available";
    } else if ("0".equals(status)) {
        return "sold";
    }
    return "";
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
              
        
        root = loader.load();
        ControllerManagementMenuCust menu1 = loader.getController();
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
    private void salessidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_sales.fxml"));
              
        
        root = loader.load();
        ControllerManagementMenuSales menu1 = loader.getController();
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
    private void employeesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_employee.fxml"));
              
        
        root = loader.load();
        ControllerManagementMenuEmployee menu1 = loader.getController();
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

    
}
