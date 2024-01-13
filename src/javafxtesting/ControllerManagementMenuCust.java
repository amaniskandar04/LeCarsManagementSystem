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


public class ControllerManagementMenuCust {
    
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
    }
    
    @FXML
    private void initialize() {
        
        custId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        custName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        phoneNum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        postcode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        

        String csvFilePath = "src/cust.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equals("custId")){
                    tableViewCust.getItems().add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    private void vehiclesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_vehicle.fxml"));
              
        
        root = loader.load();
        ControllerManagementMenuVehicle menu1 = loader.getController();
        String warningMessage;
        warningMessage = "";
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_cust.fxml"));
                String warningMessage = "Customer already exists!";      

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_cust.fxml"));
        String warningMessage = "Customer recorded!";      
        
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
    private void wagesidebar(ActionEvent event) throws IOException {
        
        String userid = id1.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreenmanagement_wage.fxml"));
              
        
        root = loader.load();
        ControllerManagementMenuWage menu1 = loader.getController();
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
