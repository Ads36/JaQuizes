package quiz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StatisticsReaderAndWriter {
    private String path;

    public void ReadStatistics(String path) {

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path)); 
                BufferedWriter writer = new BufferedWriter(new FileWriter("statistics.txt", true))) {

            Question question;
            questions = new ArrayList<Question>();
            
            while ((question = readQuestion(reader)) != null){
                questions.add(question);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void WriteTestResults(ArrayList<String> result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            //TODO: write date, time, score, etc.
            for (String res : result) {
                writer.write(res);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
}
