package quiz;

import java.util.ArrayList;

public class Question {
    String category;
    String question;
    ArrayList<String> options;
    int correctAnswer;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(category + "\n");
        sb.append(question + "\n");
        for (int i = 0; i < options.size(); i++) {

            if (i < 26) {
                sb.append((char)('A' + i) + ") ");
            }
            else {
                sb.append((char)('A' + (i / 26) - 1) + "" + (char)('A' + (i % 26)) + ") ");
            }
            sb.append(options.get(i));
            if (i != options.size() - 1) {
                sb.append("\n");
            }
        }
        //sb.append("Answer: " + (char)('A' + correctAnswer) + "\n");
        return sb.toString();
    }
    public Question(String category, String question, ArrayList<String> options) {
        this.category = category;
        this.question = question;
        this.options = options;
        arrangeQuestion();
    }
    private void arrangeQuestion() {
        String correctAnswer = options.get(0);
        shuffleQuestions();
        this.correctAnswer = options.indexOf(correctAnswer);
    }
    private void shuffleQuestions() {
        ArrayList<String> shuffledQuestions = new ArrayList<String>();
        while (options.size() > 0) {
            int randomIndex = (int)(Math.random() * options.size());
            shuffledQuestions.add(options.get(randomIndex));
            options.remove(randomIndex);
        }
        options = shuffledQuestions;
    }
    private int getCorrectAnswerAsInt(String answer) throws IllegalArgumentException {
        int answerAsInt = 0;
        if (answer.matches("\\d+")) {
            answerAsInt =  Integer.parseInt(answer) - 1;
        } 
        else if (answer.length() == 1) {
            if (answer.charAt(0) >= 'A' && answer.charAt(0) <= 'Z') {
                answer = answer.toLowerCase();
            }
            answerAsInt = answer.charAt(0) - 'a';
        } 
        else if (answer.length() == 2) {
            if (answer.charAt(0) >= 'A' && answer.charAt(0) <= 'Z') {
                answer = answer.toLowerCase();
            }
            char firstChar = answer.charAt(0);
            char secondChar = answer.charAt(1);
            
            if (firstChar < 'a' || firstChar > 'z' || secondChar < 'a' || secondChar > 'z') {
                throw new IllegalArgumentException("Invalid answer: " + answer);
            }
            
            answerAsInt = (firstChar - 'a' + 1) * 26 + (secondChar - 'a');
        }
        else {
            throw new IllegalArgumentException("Invalid answer: " + answer);
        }

        // Check if the answer is in the range of the options
        if (answerAsInt < 0 || answerAsInt > this.options.size()-1) {
            throw new IllegalArgumentException("Invalid answer: " + answer);
        }
        return answerAsInt;
    }
    public boolean isCorrectAnswer(String answer) { 
        return getCorrectAnswerAsInt(answer)== correctAnswer;
    }
    public boolean isValidAnswer(String answer) {
        try {
            getCorrectAnswerAsInt(answer);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}