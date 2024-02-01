package quiz;

public class Question {
    String category;
    String question;
    String[] options;
    int correctAnswer;
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(category + "\n");
        sb.append(question + "\n");
        for (int i = 0; i < options.length; i++) {
            sb.append((char)('A' + i) + ") " + options[i]);
            if (i != options.length - 1) {
                sb.append("\n");
            }
        }
        //sb.append("Answer: " + (char)('A' + correctAnswer) + "\n");
        return sb.toString();
    }
    public Question(String category, String question, String[] options, String correctAnswer) {
        this.category = category;
        this.question = question;
        this.options = options;
        this.correctAnswer = getCorrectAnswerAsInt(correctAnswer)-1;
    }
    private int getCorrectAnswerAsInt(String answer) {
        int answerAsInt = 0;
        if (answer.matches("\\d+")) {
            answerAsInt =  Integer.parseInt(answer);
        } else if (answer.length() == 1) {
            if (answer.charAt(0) >= 'A' && answer.charAt(0) <= 'Z') {
                answer = answer.toLowerCase();
            }
            answerAsInt = answer.charAt(0) - 'a';
        } 
        if (answerAsInt < 1 || answerAsInt > this.options.length) {
            throw new IllegalArgumentException("Invalid answer: " + answer);
        }
        return answerAsInt;
    }
}