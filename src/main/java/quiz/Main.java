package quiz;

import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {

        Reader reader = new Reader("questions.txt");
        reader.ReadQuestions(reader.getPath());
        StatisticsReaderAndWriter writer = new StatisticsReaderAndWriter("statistics.txt");

        Parser parser = new Parser();

        Quiz quiz = new Quiz(reader.getQuestions(), parser, writer);

        System.out.println("Vítejte v kvízové aplikaci!");

        int numberOfQuestions = parser.chooseNumberOfQuestions();
        ArrayList<String> categories = parser.chooseCategories(quiz);

        ArrayList<Question> currentQuestions;
        currentQuestions = quiz.getQuestionsByCategories(categories);
        quiz.startQuiz(currentQuestions, categories, numberOfQuestions);

    }
}
