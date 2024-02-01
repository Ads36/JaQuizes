package quiz;
import java.io.BufferedReader;
import java.util.ArrayList;
public class Reader {
    public ArrayList<Question> questions;
    public ArrayList<Question> ReadQuestions(String path) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {
            Question question;
            questions = new ArrayList<Question>();
            while ((question = readQuestion(reader)) != null){
                questions.add(question);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return questions;
    }
    public void printQuestions() {
        for (Question question : questions) {
            System.out.println(question);
        }
    }
    private Question readQuestion(BufferedReader reader) {
        try {
            String category = reader.readLine();
            String question = reader.readLine();
            String correctAnswer = reader.readLine();
            ArrayList<String> options = new ArrayList<String>();
            String line;
            while ((line = reader.readLine()) != null && !line.equals("")) {
                options.add(line);
            }
            return new Question(category, question, options.toArray(new String[options.size()]), correctAnswer);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
