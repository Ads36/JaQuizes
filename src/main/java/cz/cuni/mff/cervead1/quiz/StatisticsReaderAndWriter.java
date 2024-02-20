package cz.cuni.mff.cervead1.quiz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;

/**
 * Class for reading and writing statistics
 */
public class StatisticsReaderAndWriter {
    private String path;
    private ArrayList<OneQuizResult> results;
    public StatisticsReaderAndWriter() {
        this.path = path();
    }
    /**
     * Returns the path of the statistics file, which is in the user's 
     * home directory and its name is "JaQuizesStatistics.txt"
     * @return {@code String} - the path of the file
     */
    private String path() {
        String homeDir = System.getProperty("user.home");
        return homeDir + File.separator + "JaQuizesStatistics.txt";
    }
    /** 
     * Reads statistics from file
     * @param path {@code String} - the path of the file
     */
    public void ReadStatistics(String path) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {
            results = new ArrayList<OneQuizResult>();
            OneQuizResult result;
            
            while ((result = readOneQuizResult(reader)) != null) {
                results.add(result);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Reads one quiz result from the file
     * @param reader {@code BufferedReader} - opened reader of the file
     * @return {@code OneQuizResult} - the quiz result
     */
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
    /**
     * Writes the results of the quiz to the statistics file
     * @param correctAnswers {@code int} - the number of correct answers
     * @param numberOfQuestions {@code int} - the number of questions
     * @param categoryCorrectCount {@code HashMap<String, Integer>} - the number of correct answers in each category
     * @param categoryQuestionsCount {@code HashMap<String, Integer>} - the number of questions in each category
     */
    public void WriteTestResults(int correctAnswers, int numberOfQuestions, HashMap<String, Integer> categoryCorrectCount, HashMap<String, Integer> categoryQuestionsCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(java.time.LocalDateTime.now() + "\n");
            writer.write(correctAnswers + " " + numberOfQuestions + "\n");
            for (String category : categoryCorrectCount.keySet()) {
                writer.write(category.replace(' ', '_') + " " + categoryCorrectCount.get(category) + " " + categoryQuestionsCount.get(category) + "\n");
            }
            writer.write("\n");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * Prints the best score for each category
     */
    private void PrintBestCategoryScore() {
        ArrayList<String> categories = new ArrayList<String>();
        HashMap<String, Integer> categoryCorrectCount = new HashMap<String, Integer>();
        HashMap<String, Integer> categoryQuestionsCount = new HashMap<String, Integer>();

        // Get all categories
        for (OneQuizResult result : results) {
            for (String category : result.categoryCorrectCount.keySet()) {
                if (!categories.contains(category)) {
                    categories.add(category);
                }
            }
        }
        for (OneQuizResult result : results) {
            
            // Add the category to the disctionaries if it is not there and add the correct and question count
            for (String category : result.categoryQuestionsCount.keySet()) {
                if (categoryQuestionsCount.containsKey(category)) {
                    categoryQuestionsCount.put(category, categoryQuestionsCount.get(category) + result.categoryQuestionsCount.get(category));
                } else {
                    categoryQuestionsCount.put(category, result.categoryQuestionsCount.get(category));
                }
            }
            for (String category : result.categoryCorrectCount.keySet()) {
                if (categoryCorrectCount.containsKey(category)) {
                    categoryCorrectCount.put(category, categoryCorrectCount.get(category) + result.categoryCorrectCount.get(category));
                } else {
                    categoryCorrectCount.put(category, result.categoryCorrectCount.get(category));
                }
            }
        }

        System.out.println("Nejlepší skóre v kategoriích:");
        for (String category : categories) {
            System.out.println(category + ": " + categoryCorrectCount.get(category) + "/" + 
            categoryQuestionsCount.get(category) + ", " + 
            (int)((double)categoryCorrectCount.get(category) / categoryQuestionsCount.get(category) * 100) + "%");
        }
    }
    /**
     * Prints the best score
     */
    public void PrintBestScore() {

        // Load statistics
        try {
            ReadStatistics(path);
        }
        // If the statistics file is in bad format, print an error
        catch (Exception e) {
            System.out.println("Chyba v načtení výsledků kvízů");
            return;
        }
        if (results.size() == 0) {
            System.out.println("Nebyly nalezeny žádné výsledky");
            return;
        }

        // Find the best score
        float bestScore = 0;
        LocalDateTime bestTime = LocalDateTime.now();
        for (OneQuizResult result : results) {
            float score = (float)result.correctAnswers/result.numberOfQuestions;
            if (score >= bestScore) {
                bestScore = score;
                bestTime = result.time;
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        System.out.println("Nejlepší skóre: " + bestScore*100 + "% dosažené " + bestTime.format(formatter));

        this.PrintBestCategoryScore();
        
        System.out.println();
    }
}

/**
 * Class for one quiz result
 */
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
