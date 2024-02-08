package quiz;

import java.util.ArrayList;

public class Parser {
    public int chooseNumberOfQuestions() {
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
    public ArrayList<String> chooseCategories(Quiz quiz) {
        
        System.out.println("Vyberte si kategorie, pokud jich vybíráte více, pište mezery mezi čísla:");
        System.out.println("0. Všechny kategorie");

        ArrayList<String> categories = quiz.getCategories(quiz.getQuestions());
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        ArrayList<String> categoryChoicesList = new ArrayList<String>();
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
    public void chooseWhatToDoNext(Quiz quiz, ArrayList<Question> questions, ArrayList<String> categories, int numberOfQuestions) {
        System.out.println("Přejete si další kvíz?");
        System.out.println("0. Ano");
        System.out.println("1. Ano, ale jiné kategorie");
        System.out.println("2. Ne");
        String nextQuiz = System.console().readLine();
        System.out.println();
        if (nextQuiz.equals("0")) {
            quiz.startQuiz(questions, categories, numberOfQuestions);
        }
        if (nextQuiz.equals("1")) {
            int newNumberOfQuestions = chooseNumberOfQuestions();
            ArrayList<String> categoryChoicesList = chooseCategories(quiz);
            ArrayList<Question> questionsByCategories = quiz.getQuestionsByCategories(categoryChoicesList);
            quiz.startQuiz(questionsByCategories, categoryChoicesList, newNumberOfQuestions);
        }
    }
}
