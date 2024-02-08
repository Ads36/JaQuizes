package quiz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsReaderAndWriter {

    private String path;
    private ArrayList<OneQuizResult> results;
    public StatisticsReaderAndWriter(String path) {
        this.path = path;
    }
    public void ReadStatistics(String path) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {

            OneQuizResult result;
            
            while ((result = readOneQuizResult(reader)) != null) {
                results.add(result);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private OneQuizResult readOneQuizResult(BufferedReader reader) {
        try {
            String date = reader.readLine();
            if (date == null) {
                return null;
            }
            LocalDateTime time = LocalDateTime.parse(date);
            String score = reader.readLine();
            String[] scoreParts = score.split(" ");
            int correctAnswers = Integer.parseInt(scoreParts[0]);
            int numberOfQuestions = Integer.parseInt(scoreParts[1]);
            HashMap<String, Integer> categoryCorrectCount = new HashMap<String, Integer>();
            HashMap<String, Integer> categoryQuestionsCount = new HashMap<String, Integer>();
            String line;
            while ((line = reader.readLine()) != null && !line.equals("")) {
                String[] parts = line.split(" ");
                categoryCorrectCount.put(parts[0], Integer.parseInt(parts[1]));
                categoryQuestionsCount.put(parts[0], Integer.parseInt(parts[2]));
            }
            return new OneQuizResult(time, correctAnswers, numberOfQuestions, categoryCorrectCount, categoryQuestionsCount);
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public void WriteTestResults(int correctAnswers, int numberOfQuestions, HashMap<String, Integer> categoryCorrectCount, HashMap<String, Integer> categoryQuestionsCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(java.time.LocalDate.now() + "\n");
            writer.write(java.time.LocalTime.now() + "\n");
            writer.write(correctAnswers + " " + numberOfQuestions + "\n");
            for (String category : categoryCorrectCount.keySet()) {
                writer.write(category + " " + categoryCorrectCount.get(category) + " " + categoryQuestionsCount.get(category) + "\n");
            }
            writer.write("\n");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class OneQuizResult {
    LocalDateTime time;
    int correctAnswers;
    int numberOfQuestions;
    HashMap<String, Integer> categoryCorrectCount;
    HashMap<String, Integer> categoryQuestionsCount;
    public OneQuizResult(LocalDateTime time, int correctAnswers, int numberOfQuestions,
                        HashMap<String, Integer> categoryCorrectCount, HashMap<String, Integer> categoryQuestionsCount) {
        
        this.time = time;
        this.correctAnswers = correctAnswers;
        this.numberOfQuestions = numberOfQuestions;
        this.categoryCorrectCount = categoryCorrectCount;
        this.categoryQuestionsCount = categoryQuestionsCount;
    }
    @Override
    public String toString() {
        return time + " " + (float)correctAnswers/numberOfQuestions;
    }

}
class OneQuizResultCategories extends OneQuizResult {
    public OneQuizResultCategories(LocalDateTime time, int correctAnswers, int numberOfQuestions,
                        HashMap<String, Integer> categoryCorrectCount, HashMap<String, Integer> categoryQuestionsCount) {
        super(time, correctAnswers, numberOfQuestions, categoryCorrectCount, categoryQuestionsCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(time + " " + (float)correctAnswers/numberOfQuestions + " ");
        for (String category : categoryCorrectCount.keySet()) {
            sb.append(category + " " + categoryCorrectCount.get(category) + " " + categoryQuestionsCount.get(category) + " ");
        }
        return sb.toString();
    }
}