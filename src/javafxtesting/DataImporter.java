/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Aman and Rhanee
 */
public class DataImporter {
    public static String[] importData(File file) throws IOException {
        String[] employeeData;

        BufferedReader reader = new BufferedReader(new FileReader(file));
            // Count the number of lines in the file to determine array size
            long lineCount = reader.lines().count();
            employeeData = new String[(int) lineCount];

            // Reset the reader to the beginning of the file
            reader.close();
            reader = new BufferedReader(new FileReader(file));

            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                // Add additional validation or processing if needed
                employeeData[index++] = line;
            }
        

        return employeeData;
    }
}
