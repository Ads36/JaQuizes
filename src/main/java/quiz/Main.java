package quiz;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.ReadQuestions("questions.txt");
        reader.printQuestions();
    }
}
