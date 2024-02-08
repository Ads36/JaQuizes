package quiz;

import java.util.ArrayList;
import java.util.HashMap;
public class Quiz {
    ArrayList<Question> allQuestions;
    Parser parser;
    StatisticsReaderAndWriter writer;
    public Quiz (ArrayList<Question> questions, Parser parser, StatisticsReaderAndWriter writer) {
        this.allQuestions = questions;
        this.parser = parser;
        this.writer = writer;
    }
    public ArrayList<Question> getQuestions() {
        return allQuestions;
    }
    public ArrayList<String> getCategories(ArrayList<Question> questions) {
        ArrayList<String> categories = new ArrayList<String>();
        for (Question question : questions) {
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
    private ArrayList<Question> shuffleQuestions(ArrayList<Question> questions) {
        ArrayList<Question> shuffledQuestions = new ArrayList<Question>();
        while (questions.size() > 0) {
            int randomIndex = (int)(Math.random() * questions.size());
            shuffledQuestions.add(questions.get(randomIndex));
            questions.remove(randomIndex);
        }
        return shuffledQuestions;
    }
   

    public void startQuiz(ArrayList<Question> questions, ArrayList<String> categories, int numberOfQuestions) {
        
        int score = 0;

        questions = shuffleQuestions(questions);

        HashMap<String, Integer> categoryScoresCount = new HashMap<String, Integer>();
        HashMap<String, Integer> categoryQuestionsCount = new HashMap<String, Integer>();

        for (String category : categories) {
            categoryScoresCount.put(category, 0);
            categoryQuestionsCount.put(category, 0);
        }
        numberOfQuestions = Math.min(numberOfQuestions, questions.size());
        for (int i = 0; i < numberOfQuestions; i++) {

            Question question = questions.get(i);

            categoryQuestionsCount.put(question.category, categoryQuestionsCount.get(question.category) + 1);
            System.out.println(question);
            System.out.print("Odpověď: ");
            String answer = System.console().readLine();

            while (!question.isValidAnswer(answer)) {
                System.out.println("Nevalidní odpověď, zkuste to znovu");
                System.out.print("Odpověď: ");
                answer = System.console().readLine();
            }

            if (question.isCorrectAnswer(answer)) {
                score++;
                categoryScoresCount.put(question.category, categoryScoresCount.get(question.category) + 1);
            }
            
        }
        if (categoryScoresCount.size() > 0) {
            System.out.println();
            System.out.println("Skóre na kategorie:");
            printResultsPerCategory(categoryScoresCount, categoryQuestionsCount);

        }
        
        writer.WriteTestResults(score, numberOfQuestions, categoryScoresCount, categoryQuestionsCount);

        System.out.println();
        System.out.println("Celkové skóre: " + score + "/" + questions.size() + ", " + (int)((double)score / questions.size() * 100) + "%");
        System.out.println();
        System.out.println("Stiskněte Enter pro pokračování");
        System.console().readLine();
        parser.chooseWhatToDoNext(this, questions, categories, numberOfQuestions);

    }
    private static void printResultsPerCategory(HashMap<String, Integer> correctCount, HashMap<String, Integer> questionsCount) {
        for (String category : correctCount.keySet()) {
            System.out.println(category + ": " + correctCount.get(category) + "/" + 
            questionsCount.get(category) + ", " + 
            (int)((double)correctCount.get(category) / questionsCount.get(category) * 100) + "%");
        }
    }

}