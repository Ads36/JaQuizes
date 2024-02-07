package quiz;

import java.util.ArrayList;

public class Quiz {
    ArrayList<Question> allQuestions;
    public Quiz(ArrayList<Question> questions) {
        this.allQuestions = questions;
    }
    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<String>();
        for (Question question : allQuestions) {
            if (!categories.contains(question.category)) {
                categories.add(question.category);
            }
        }
        return categories;
    }
    public ArrayList<Question> getQuestionsByCategories(ArrayList<String> categories) {
        ArrayList<Question> questionsByCategories = new ArrayList<Question>();
        for (Question question : allQuestions) {
            if (categories.contains(question.category)) {
                questionsByCategories.add(question);
            }
        }
        return questionsByCategories;
    }
    public ArrayList<Question> shuffleQuestions(ArrayList<Question> questions) {
        ArrayList<Question> shuffledQuestions = new ArrayList<Question>();
        while (questions.size() > 0) {
            int randomIndex = (int)(Math.random() * questions.size());
            shuffledQuestions.add(questions.get(randomIndex));
            questions.remove(randomIndex);
        }
        return shuffledQuestions;
    }
    public void startQuiz(ArrayList<Question> questions) {
        int score = 0;
        for (Question question : questions) {
            System.out.println(question);
            System.out.print("Answer: ");
            String answer = System.console().readLine();
            while (!question.isValidAnswer(answer)) {
                System.out.println("Invalid answer: " + answer);
                System.out.print("Zkuste to znovu: ");
                answer = System.console().readLine();
            }
            if (question.isCorrectAnswer(answer)) {
                score++;
            }
            
        }
        System.out.println("Your score: " + score + "/" + questions.size() + ", " + (int)((double)score / questions.size() * 100) + "%");
    }

}