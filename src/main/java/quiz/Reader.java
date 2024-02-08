package quiz;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
    private ArrayList<Question> questions;
    private String path;
    public Reader(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    public void ReadQuestions(String path) {

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {

            Question question;
            questions = new ArrayList<Question>();
            
            while ((question = readQuestion(reader)) != null){
                questions.add(question);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void printAllQuestions() {
        for (Question question : questions) {
            System.out.println(question);
        }
    }
    private Question readQuestion(BufferedReader reader) {
        try {
            String category = reader.readLine();

            // If the file ends, return null
            if (category == null) {
                return null;
            }
            // Skip empty lines
            while (category.matches("\\s*")) {
                category = reader.readLine();
                if (category == null) {
                    return null;
                }
            }

            String question = reader.readLine();
            ArrayList<String> options = new ArrayList<String>();
            String line;

            while ((line = reader.readLine()) != null && !line.equals("")) {
                options.add(line);
            }
            return new Question(category, question, options);
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
