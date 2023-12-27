/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxtesting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Aman and Rhanee
 */
public class DataExporter {
    public static void exportData(String[] data, File destinationFilePath) throws IOException {
        try (FileWriter writer = new FileWriter(destinationFilePath)) {
            for (String row : data) {
                    writer.write(row + "\n");
            }
                writer.append("\n");
        }
    }
}

