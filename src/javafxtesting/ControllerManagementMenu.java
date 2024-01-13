/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtesting;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ControllerManagementMenu {
    
    @FXML
    Label name1;
    
    @FXML
    Label name2;
    
    @FXML
    Label id1;
    
    @FXML
    Label OperationalRevenue;
    
    @FXML
    Label EmployeeNum;
    
    @FXML
    Label wageAmount;
    
    @FXML
    Label profitPercentageAmount;
    
    @FXML
    Label companyNetWorthAmount;
    
    @FXML
    Label netProfitAmount;
    
    @FXML
    Label extraAmount;
    
    @FXML
    Label expenseAmount;
    
    @FXML
    Label vehicleAmount;
    
    @FXML
    Label OperationalRevenue1;
    
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private LineChart<String, Number> lineChart;

    
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
           
        reader = new BufferedReader(new FileReader(employee));
        
        String[] employeeId = new String[100];
        double[] employeeIdProfit = new double[100];
        String[] employeeIdStatus = new String[100];
        int[] employeeCarAmount = new int[100];
        int employeeNumber = 0;
        double wageCost = 0;
        i = 0;
        
        while((line = reader.readLine()) != null){
            if (i == 0){
                i++;
            }
            else{
                String[] row = line.split(",");
                employeeId[employeeNumber] = row[0];
                employeeIdStatus[employeeNumber] = row[2];
                employeeNumber++;
                }

        }
        
        i=0;
        
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
                                    employeeIdProfit[j] += Double.parseDouble(data_vehicle[4]); //add the profit tally of said employee
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
        
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        while(j <= employeeNumber && employeeId[j] != null){
            series2.getData().add(new XYChart.Data<>(employeeId[j], employeeIdProfit[j]));
            if (employeeIdStatus[j].equals("1")){
                wageCost += 2550*5;
                if(employeeIdProfit[j] <= 800000){
                    wageCost += (employeeIdProfit[j]/100);}
                else if(employeeIdProfit[j] <= 1600000){
                    wageCost += (employeeIdProfit[j]/100)*1.15;}
                else if(employeeIdProfit[j] <= 2500000){
                    wageCost += (employeeIdProfit[j]/100)*1.25;}
                else if(employeeIdProfit[j] > 2500000){
                    wageCost += (employeeIdProfit[j]/100)*1.35;}
            }
            
            else if(employeeIdStatus[j].equals("0")){
                wageCost += (1450*5) + (employeeIdProfit[j]/100);
                if (employeeIdProfit[j] > 1000000 || employeeCarAmount[j] >= 15){
                    wageCost += 500;
                }
            }
            j++;
        }
        

        barChart.getData().add(series2);
        barChart.setTitle("Total Sales by employee");
        
        reader.close();
        
        
        
        double June2023 = 0;
        double July2023 = 0;
        double August2023 = 0;
        double September2023 = 0;
        double October2023 = 0; 
        double January2024 = 0;
        i = 0;
        
        try(BufferedReader reader_sales = new BufferedReader(new FileReader("src\\sales.csv"))){
            while((line = reader_sales.readLine()) != null){
                if (i == 0){
                    i++;
                }
                else{
                    String[] data_sales = line.split(",");
                    String month = data_sales[1].substring(5, 7);
                    String year = data_sales[1].substring(0, 4);
                    int monthNum = Integer.parseInt(month);

                    BufferedReader reader_vehicle = new BufferedReader(new FileReader("src\\vehicle.csv"));
                    String line2;
                    while ((line2 = reader_vehicle.readLine()) != null) {
                        String[] data_vehicle = line2.split(",");
                        if (year.equals("2023") || year.equals("2024")) {                           
                            if (data_sales[2].equals(data_vehicle[0])) {
                                switch (monthNum) { //again, I would make it bigger if I had more data. Making it bigger by default would make the graph ugly
                                    case 6 -> June2023 += Integer.parseInt(data_vehicle[4]);
                                    case 7 -> July2023 += Integer.parseInt(data_vehicle[4]);
                                    case 8 -> August2023 += Integer.parseInt(data_vehicle[4]);
                                    case 9 -> September2023 += Integer.parseInt(data_vehicle[4]);
                                    case 10 -> October2023 += Integer.parseInt(data_vehicle[4]);
                                    default -> January2024 += Integer.parseInt(data_vehicle[4]);
                                   

                                }
                                //again, I would make it bigger if I had more data. Making it bigger by default would make the graph ugly
                            }
                        }
                    }
                }
            }
        }
        
        catch (IOException e){ 
        }
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Jun", June2023));
        series.getData().add(new XYChart.Data<>("Jul", July2023));
        series.getData().add(new XYChart.Data<>("Aug", August2023));
        series.getData().add(new XYChart.Data<>("Sept", September2023));
        series.getData().add(new XYChart.Data<>("Oct", October2023));

        lineChart.getData().add(series);
        lineChart.setTitle("Monthly Company Revenue");
        
        
        
        double totalRevenue = June2023 + July2023 + August2023 + September2023 + October2023;
        
        DecimalFormat decimalFormat = new DecimalFormat("#.000");
        DecimalFormat decimalFormatTwoDecimals = new DecimalFormat("#.00");
        DecimalFormat decimalFormatOneDecimal = new DecimalFormat("#.0");
        
        
        
        
        double vehicleCost = 0;
        double carValue = 0;
        i = 0;
        try(BufferedReader reader_vehicle = new BufferedReader(new FileReader("src\\vehicle.csv"))){
            while((line = reader_vehicle.readLine()) != null){
                if (i == 0){
                    i++;
                }
                else{
                    String[] vehicle_data = line.split(",");
                    vehicle_data = Arrays.copyOf(vehicle_data, 5);
                    vehicleCost += Double.parseDouble(vehicle_data[2]);
                    if (vehicle_data[3].equals("1")){
                        carValue += Double.parseDouble(vehicle_data[2]);
                    }
                    
                }
            }
        }
        catch(Exception E){
            
        }
        
        OperationalRevenue.setText("RM" + decimalFormat.format(totalRevenue/1000000) + " mil.");
        OperationalRevenue1.setText("RM" + decimalFormat.format(totalRevenue/1000000) + " mil.");
        EmployeeNum.setText(Integer.toString(employeeNumber));
        
        
        wageAmount.setText("RM" + decimalFormatOneDecimal.format(wageCost/1000) + "k");
        
        vehicleAmount.setText("RM" + decimalFormatTwoDecimals.format(vehicleCost/1000000) + "m");
        
        double extraCost = 50000;    
        extraAmount.setText("RM" + decimalFormatOneDecimal.format(extraCost/1000) + "k");
        
        double expense = extraCost + vehicleCost + wageCost;     
        expenseAmount.setText("RM" + decimalFormat.format(expense/1000000) + " mil.");
        
        double netProfit = totalRevenue - expense;        
        netProfitAmount.setText("RM" + decimalFormat.format(netProfit/1000000) + " mil.");
        
        profitPercentageAmount.setText(decimalFormatOneDecimal.format((netProfit/totalRevenue)*100) + "%");
        
        companyNetWorthAmount.setText("RM" + decimalFormat.format((netProfit + carValue)/1000000) + " mil.");
        
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
    private void customersidebar(ActionEvent event) throws IOException {
        
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
