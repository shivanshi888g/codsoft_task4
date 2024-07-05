package Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Quiz window = new Quiz();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] optionsButtons;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JLabel timerLabel;
    private int currentQuestionIndex;
    private int score;
    private int correctAnswers;
    private int incorrectAnswers;
    private Timer timer;
    private int timeRemaining;

    private Question[] questions = {
        new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, "Paris"),
        new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Mark Twain", "Ernest Hemingway", "F. Scott Fitzgerald"}, "Harper Lee"),
        new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, "Jupiter"),
        new Question("What is the chemical symbol for water?", new String[]{"O2", "H2O", "CO2", "NaCl"}, "H2O")
    };

    public Quiz() {
        initialize();
        loadQuestion();
    }

    private void initialize() {
        frame = new JFrame("Quiz Application");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel questionPanel = new JPanel();
        frame.getContentPane().add(questionPanel, BorderLayout.CENTER);
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel("Question");
        questionPanel.add(questionLabel);

        optionsButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionsButtons[i] = new JRadioButton();
            optionsGroup.add(optionsButtons[i]);
            questionPanel.add(optionsButtons[i]);
        }

        JPanel controlPanel = new JPanel();
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

        timerLabel = new JLabel("Time: 30");
        controlPanel.add(timerLabel);

        submitButton = new JButton("Submit");
        controlPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });

        currentQuestionIndex = 0;
        score = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        timeRemaining = 30;
        startTimer();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            Question question = questions[currentQuestionIndex];
            questionLabel.setText(question.getQuestion());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                optionsButtons[i].setText(options[i]);
            }
            optionsGroup.clearSelection();
            timeRemaining = 30;
        } else {
            showResults();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time: " + timeRemaining);
                if (timeRemaining <= 0) {
                    submitAnswer();
                }
            }
        });
        timer.start();
    }

    private void submitAnswer() {
        timer.stop();
        Question question = questions[currentQuestionIndex];
        for (JRadioButton button : optionsButtons) {
            if (button.isSelected() && button.getText().equals(question.getCorrectAnswer())) {
                score++;
                correctAnswers++;
            } else if (button.isSelected()) {
                incorrectAnswers++;
            }
        }
        currentQuestionIndex++;
        loadQuestion();
        startTimer();
    }

    private void showResults() {
        JOptionPane.showMessageDialog(frame, "Quiz Over!\n\nYour score is: " + score +
                "\n\nCorrect Answers: " + correctAnswers +
                "\nIncorrect Answers: " + incorrectAnswers);
        frame.dispose();
    }

    class Question {
        private String question;
        private String[] options;
        private String correctAnswer;

        public Question(String question, String[] options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
