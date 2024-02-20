package cz.cuni.mff.cervead1.quiz;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Quiz class, represents the quiz
 */
public class Quiz {
    private ArrayList<Question> allQuestions;
    private UserInterfaceHandler handler;
    private StatisticsReaderAndWriter writer;

    public Quiz (ArrayList<Question> questions, UserInterfaceHandler handler, StatisticsReaderAndWriter writer) {
        this.allQuestions = questions;
        this.handler = handler;
        this.writer = writer;
    }
    
    /** 
     * Returns all loaded questions
     * @return The {@code ArrayList<Question>} of categories
     */
    public ArrayList<Question> getQuestions() {
        return allQuestions;
    }
    /** 
     * Returns all categories of questions
     * @param questions {@code ArrayList<String>}  - list of questions
     * @return The {@code ArrayList<String>} of categories
     */
    public ArrayList<String> getCategories(ArrayList<Question> questions) {
        ArrayList<String> categories = new ArrayList<String>();
        for (Question question : questions) {
            if (!categories.contains(question.category)) {
                categories.add(question.category);
            }
        }
        return categories;
    }
    
    /** 
     * @param categories
     * @return ArrayList<Question>
     */
    public ArrayList<Question> getQuestionsByCategories(ArrayList<String> categories) {
        ArrayList<Question> questionsByCategories = new ArrayList<Question>();
        for (Question question : allQuestions) {
            if (categories.contains(question.category)) {
                questionsByCategories.add(question);
            }
        }
        return questionsByCategories;
    }

    /** 
     * Mixes order of questions
     * @param questions {@code ArrayList<Question>} - list of questions
     * @return {@code ArrayList<Question>} - shuffled questions
     */
    private ArrayList<Question> shuffleQuestions(ArrayList<Question> questions) {
        ArrayList<Question> shuffledQuestions = new ArrayList<Question>();
        while (questions.size() > 0) {
            int randomIndex = (int)(Math.random() * questions.size());
            shuffledQuestions.add(questions.get(randomIndex));
            questions.remove(randomIndex);
        }
        return shuffledQuestions;
    }
   
    /** 
     * Starts the quiz, main method
     * @param questions {@code ArrayList<Question>} - list of questions
     * @param categories {@code ArrayList<String>} - list of categories
     * @param numberOfQuestions {@code int} - number of questions
     */
    public void startQuiz(ArrayList<Question> questions, ArrayList<String> categories, int numberOfQuestions) {
        
        int score = 0;

        questions = shuffleQuestions(questions);

        HashMap<String, Integer> categoryScoresCount = new HashMap<String, Integer>();
        HashMap<String, Integer> categoryQuestionsCount = new HashMap<String, Integer>();

        
        numberOfQuestions = Math.min(numberOfQuestions, questions.size());

        for (int i = 0; i < numberOfQuestions; i++) {
            Question question = questions.get(i);

            // If the category is not in the map, add it
            if (!categoryScoresCount.containsKey(question.category)) {
                categoryScoresCount.put(question.category, 0);
                categoryQuestionsCount.put(question.category, 0);
            }
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
                // Increment the score for the category
                categoryScoresCount.put(question.category, categoryScoresCount.get(question.category) + 1);
                System.out.println("Správně!");
            }
            else {
                System.out.println("Špatně, správná odpověď: " + question.getCorrectAnswer());
            }
            
        }
        if (categoryScoresCount.size() > 0) {
            System.out.println();
            System.out.println("Skóre na kategorie:");
            printResultsPerCategory(categoryScoresCount, categoryQuestionsCount);
        }
        
        writer.WriteTestResults(score, numberOfQuestions, categoryScoresCount, categoryQuestionsCount);

        System.out.println();
        System.out.println("Skóre: " + score + "/" + numberOfQuestions + ", " + (int)((double)score / numberOfQuestions * 100) + "%");
        System.out.println();
        handler.ChooseWhatToDoAfterQuiz(this, questions, categories, numberOfQuestions);
    }
    
    /** 
     * Prints the statistics
     */
    public void printStatistics() {
        writer.PrintBestScore();
    }
    
    /** 
     * Prints the results per category
     * @param correctCount {@code HashMap<String, Integer>} - correct answers count
     * @param questionsCount {@code HashMap<String, Integer>} - questions count
     */
    private void printResultsPerCategory(HashMap<String, Integer> correctCount, HashMap<String, Integer> questionsCount) {
        for (String category : correctCount.keySet()) {
            System.out.println(category + ": " + correctCount.get(category) + "/" + 
            questionsCount.get(category) + ", " + 
            (int)((double)correctCount.get(category) / questionsCount.get(category) * 100) + "%");
        }
    }

}