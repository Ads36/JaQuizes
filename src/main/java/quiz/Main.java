package quiz;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.ReadQuestions("questions.txt");
        //reader.printAllQuestions();
        Quiz quiz = new Quiz(reader.getQuestions());
        quiz.startQuiz(quiz.shuffleQuestions(quiz.getQuestionsByCategories(quiz.getAllCategories())));
    }
}
