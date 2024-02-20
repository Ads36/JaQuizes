package cz.cuni.mff.cervead1.quiz;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.InputStream;
/**
 * Reader class, reads questions from file
 */
public class Reader {
    private ArrayList<Question> questions;
    private String path = "questions.txt";
    public Reader() {
        
    }
    
    /** 
     * Returns the path of the file
     * @return {@code String} - the path of the file
     */
    public String getPath() {
        return path;
    }
    /** 
     * Returns the questions
     * @return {@code ArrayList<Question>} - the questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /** 
     * Reads questions from file, location is implicitly set, /src/main/java/questions.txt
     */
    public void ReadQuestionsImplicitly() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(getPath());
        ReadQuestions(inputStream);
    }
    
    /** 
     * Reads questions from file
     * @param path {@code String} - the path of the file
     */
    public void ReadQuestionsExplicitly(String path) {
        this.path = path;
        try {
            InputStream inputStream = new java.io.FileInputStream(path);
            ReadQuestions(inputStream);
        } 
        catch (IOException e) {
            System.out.println("Otázky musí být v souboru " + path);
            System.exit(1);
        }
    }
    /** 
     * Reads questions from file
     * @param path {@code String} - the path of the file
     */
    public void ReadQuestions(InputStream stream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

            Question question;
            questions = new ArrayList<Question>();
            
            while ((question = readQuestion(reader)) != null){
                questions.add(question);
            }

        } catch (IOException e) {
            
            System.out.println("Otázky musí být v souboru " + path);
            System.exit(1);
        }
        catch (Exception e) {
            e.printStackTrace();

            System.out.println("Nebylo možné načíst otázky");
            System.out.println("V souboru " + path + " jsou otázky v chybném formátu");
            System.exit(1);
        }
    }
    /** 
     * Prints all questions
     */
    public void printAllQuestions() {
        for (Question question : questions) {
            System.out.println(question);
        }
    }

    /**
     * Reads a question from the file
     * @param reader {@code BufferedReader} - opened reader of the file
     * @return {@code Question} - read question
     */
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
            return null;
        }
    }
}
