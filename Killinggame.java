package killinggame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Killinggame extends JFrame {

    private JButton[][] buttons;
    private int[][] centipedeSegments;
    private int[][] playerAttempts;
    private int[] livesArray; 
    private int lives = 3;
    private int attemptCount;
    private JLabel livesLabel;

    public Killinggame() {
        setTitle("Killing the Centipede");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        livesLabel = new JLabel("Lives remaining: " + lives, SwingConstants.CENTER);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(livesLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        centipedeSegments = new int[3][2];
        playerAttempts = new int[9][2]; 
        livesArray = new int[lives]; 
        attemptCount = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                gridPanel.add(buttons[row][col]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        placeCentipedeRandomly();
    }

    private void placeCentipedeRandomly() {
        Random random = new Random();
        for (int i = 0; i < centipedeSegments.length; i++) {
            centipedeSegments[i][0] = random.nextInt(3);
            centipedeSegments[i][1] = random.nextInt(3);
        }
    }

    private class ButtonClickListener implements ActionListener {

        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean hit = false;
            for (int i = 0; i < centipedeSegments.length; i++) {
                if (row == centipedeSegments[i][0] && col == centipedeSegments[i][1]) {
                    buttons[row][col].setText("O");
                    buttons[row][col].setEnabled(false);
                    hit = true;
                    break;
                }
            }

            if (hit) {
                JOptionPane.showMessageDialog(null, "You hit the centipede! You won!");
                restartGame();
            } else {
                buttons[row][col].setText("X");
                buttons[row][col].setEnabled(false);
                playerAttempts[attemptCount][0] = row;
                playerAttempts[attemptCount][1] = col;
                attemptCount++;
                livesArray[lives - 1] = 0;
                lives--;

                livesLabel.setText("Lives remaining: " + lives);
                if (lives == 0) {
                    JOptionPane.showMessageDialog(null, "Game over! You've run out of lives.");
                    restartGame();
                }
            }
        }
    }

    private void restartGame() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Restart Game", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            lives = 3;
            attemptCount = 0;
            livesLabel.setText("Lives remaining: " + lives);
            placeCentipedeRandomly();

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttons[row][col].setText("");
                    buttons[row][col].setEnabled(true);
                }
            }
        } else {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Killinggame game = new Killinggame();
            game.setVisible(true);
        });
    }
}
