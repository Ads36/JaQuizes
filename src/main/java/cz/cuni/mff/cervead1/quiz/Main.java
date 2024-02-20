package cz.cuni.mff.cervead1.quiz;

import java.util.ArrayList;
public class Main {
    
    public static void main(String[] args) {
        Reader reader = new Reader();
        if (args.length == 1) {
            String questionsPath = args[0];
            reader.ReadQuestionsExplicitly(questionsPath);
        }
        else {
            reader.ReadQuestionsImplicitly();
        }
        StatisticsReaderAndWriter writer = new StatisticsReaderAndWriter("statistics.txt");

        UserInterfaceHandler parser = new UserInterfaceHandler();

        Quiz quiz = new Quiz(reader.getQuestions(), parser, writer);

        System.out.println("Vítejte v kvízové aplikaci!");

        int numberOfQuestions = parser.ChooseNumberOfQuestions();
        ArrayList<String> categories = parser.ChooseCategories(quiz);

        ArrayList<Question> currentQuestions;
        currentQuestions = quiz.getQuestionsByCategories(categories);
        quiz.startQuiz(currentQuestions, categories, numberOfQuestions);

    }
}
