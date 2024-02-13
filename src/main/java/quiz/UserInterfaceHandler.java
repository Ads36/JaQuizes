package quiz;

import java.util.ArrayList;

public class UserInterfaceHandler {

    /** 
     * Asks the user how many questions they want in the quiz
     * @return {@code int} - number of questions
     */
    public int ChooseNumberOfQuestions() {
        System.out.println("Kolik otázek chcete mít v kvízu?");
        int numberOfQuestions = 0;
        boolean validChoice = false;
        while (!validChoice) {
            try {
                numberOfQuestions = Integer.parseInt(System.console().readLine());
                if (numberOfQuestions < 1) {
                    throw new NumberFormatException();
                }
                validChoice = true;
            } catch (NumberFormatException e) {
                System.out.println("Neplatný výběr počtu otázek");
                validChoice = false;
            }
        }
        return numberOfQuestions;
    }
    
    /** 
     * Asks the user to choose categories for the quiz
     * @param quiz {@code Quiz} - the quiz
     * @return {@code ArrayList<String>} - list of categories
     */
    public ArrayList<String> ChooseCategories(Quiz quiz) {
        
        System.out.println("Vyberte si kategorie, pokud jich vybíráte více, pište mezery mezi čísla:");
        System.out.println("0. Všechny kategorie");

        ArrayList<String> categories = quiz.getCategories(quiz.getQuestions());
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        
        ArrayList<String> categoryChoicesList = new ArrayList<String>();

        // Check if the user input is valid
        boolean validChoice = false;
        while (!validChoice) {
            try {
                String categoryChoice = System.console().readLine();
                String[] categoryChoices = categoryChoice.split(" ");

                for (String choice : categoryChoices) {
                    int choiceInt = Integer.parseInt(choice);
                    if (choiceInt == 0) {
                        categoryChoicesList = categories;
                        validChoice = true;
                        break;
                    }
                    if (choiceInt < 0 || choiceInt > categories.size()) {
                        throw new NumberFormatException(); 
                    }
                    categoryChoicesList.add(categories.get(choiceInt - 1));
                    validChoice = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Neplatný výběr kategorie");
                validChoice = false;
            }
        }
        return categoryChoicesList;
    }

    
    /** 
     * Asks the user what to do after the quiz
     * @param quiz {@code Quiz} - the quiz
     * @param questions {@code ArrayList<Question>} - list of questions
     * @param categories {@code ArrayList<String>} - list of categories
     * @param numberOfQuestions {@code int} - number of questions
     */
    public void ChooseWhatToDoAfterQuiz(Quiz quiz, ArrayList<Question> questions, ArrayList<String> categories, int numberOfQuestions) {
        System.out.println("Přejete si další kvíz?");
        System.out.println("0. Ano");
        System.out.println("1. Ano, ale jiné kategorie");
        System.out.println("2. Přeji si zobrazit statistiky kvízů");
        System.out.println("3. Ne");
        boolean validChoice = false;
        int nextQuizInt = 0;
        while (!validChoice) {
            try {
                nextQuizInt = Integer.parseInt(System.console().readLine());
                if (nextQuizInt < 0 || nextQuizInt > 3) {
                    throw new NumberFormatException();
                }
                validChoice = true;
            } catch (NumberFormatException e) {
                System.out.println("Neplatný výběr možnosti pokračování kvízu, zadejte 0, 1, 2 nebo 3");
                validChoice = false;
            }
        }
        switch (nextQuizInt) {
            case 0 -> {
                System.out.println();
                System.out.println("Začíná další kvíz!");
                ArrayList<Question> newQuestions = quiz.getQuestionsByCategories(categories);
                quiz.startQuiz(newQuestions, categories, numberOfQuestions);
            }
            case 1 -> {
                int newNumberOfQuestions = ChooseNumberOfQuestions();
                ArrayList<String> categoryChoicesList = ChooseCategories(quiz);
                ArrayList<Question> questionsByCategories = quiz.getQuestionsByCategories(categoryChoicesList);
                quiz.startQuiz(questionsByCategories, categoryChoicesList, newNumberOfQuestions);
            }
            case 2 -> {
                System.out.println();
                quiz.printStatistics();
                this.ChooseWhatToDoAfterQuiz(quiz, questions, categories, numberOfQuestions);
            }
            case 3 -> {
                System.out.println("Děkujeme za účast v kvízu!");
            }
        }
    }
}
