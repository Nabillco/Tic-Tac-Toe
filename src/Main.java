    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.Random;

    public class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new WelcomeWindow();
            });
        }
    }

    class WelcomeWindow extends JFrame {
        public WelcomeWindow() {
            setTitle("XO not Tic tac toe");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(50, 50, 50));

            JLabel titleLabel = new JLabel("Tic Tac Toe", JLabel.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
            titleLabel.setForeground(Color.WHITE);

            JButton twoPlayerButton = new JButton("2 Player");
            JButton onePlayerButton = new JButton("1 Player vs AI");
            JButton exitButton = new JButton("Exit");

            Font buttonFont = new Font("SansSerif", Font.PLAIN, 20);
            twoPlayerButton.setFont(buttonFont);
            onePlayerButton.setFont(buttonFont);
            exitButton.setFont(buttonFont);

            twoPlayerButton.setBackground(new Color(80, 80, 80));
            onePlayerButton.setBackground(new Color(80, 80, 80));
            exitButton.setBackground(new Color(80, 80, 80));

            twoPlayerButton.setForeground(Color.WHITE);
            onePlayerButton.setForeground(Color.WHITE);
            exitButton.setForeground(Color.WHITE);

            twoPlayerButton.addActionListener(e -> {
                new TicTacToe(false).setVisible(true);
                this.dispose();
            });

            onePlayerButton.addActionListener(e -> {
                new TicTacToe(true).setVisible(true);
                this.dispose();
            });

            exitButton.addActionListener(e -> System.exit(0));

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
            buttonPanel.setBackground(new Color(50, 50, 50));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

            buttonPanel.add(twoPlayerButton);
            buttonPanel.add(onePlayerButton);
            buttonPanel.add(exitButton);

            add(titleLabel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.CENTER);

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    class TicTacToe extends JFrame {
        private JButton[] buttons = new JButton[9];
        private JLabel statusLabel = new JLabel("Dorak 2asa7by", JLabel.CENTER);
        private char currentPlayer = 'X';
        private boolean gameWon = false;
        private JButton resetButton = new JButton("Reset");
        private boolean playWithAI;

        public TicTacToe(boolean playWithAI) {
            this.playWithAI = playWithAI;
            setTitle("XO not Tic tac toe");
            setSize(400, 450);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(50, 50, 50));

            JPanel boardPanel = new JPanel();
            boardPanel.setLayout(new GridLayout(3, 3));
            boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            boardPanel.setBackground(new Color(30, 30, 30));

            Font buttonFont = new Font("SansSerif", Font.BOLD, 60);
            for (int i = 0; i < 9; i++) {
                buttons[i] = new JButton("");
                buttons[i].setFont(buttonFont);
                buttons[i].setFocusPainted(false);
                buttons[i].setBackground(new Color(60, 60, 60));
                buttons[i].setForeground(Color.WHITE);
                buttons[i].addActionListener(new ButtonClickListener());
                boardPanel.add(buttons[i]);
            }

            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new BorderLayout());
            controlPanel.setBackground(new Color(50, 50, 50));
            controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
            statusLabel.setForeground(Color.WHITE);

            resetButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
            resetButton.setBackground(new Color(80, 80, 80));
            resetButton.setForeground(Color.WHITE);
            resetButton.addActionListener(e -> resetGame());

            controlPanel.add(statusLabel, BorderLayout.CENTER);
            controlPanel.add(resetButton, BorderLayout.EAST);

            add(boardPanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.SOUTH);
        }

        private class ButtonClickListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton buttonClicked = (JButton) e.getSource();
                if (buttonClicked.getText().equals("") && !gameWon) {
                    buttonClicked.setText(String.valueOf(currentPlayer));
                    buttonClicked.setForeground(currentPlayer == 'X' ? Color.CYAN : Color.PINK);
                    checkForWin();
                    if (!gameWon) {
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        statusLabel.setText("lsa bardo Dorak 2asa7by");
                        if (playWithAI && currentPlayer == 'O') {
                            aiMove();
                        }
                    }
                }
            }
        }

        private void checkForWin() {
            String[][] board = new String[3][3];
            for (int i = 0; i < 9; i++) {
                board[i / 3][i % 3] = buttons[i].getText();
            }

            for (int i = 0; i < 3; i++) {
                if (checkLine(board[i][0], board[i][1], board[i][2])) {
                    highlightWinningLine(buttons[i * 3], buttons[i * 3 + 1], buttons[i * 3 + 2]);
                    return;
                }
                if (checkLine(board[0][i], board[1][i], board[2][i])) {
                    highlightWinningLine(buttons[i], buttons[i + 3], buttons[i + 6]);
                    return;
                }
            }

            if (checkLine(board[0][0], board[1][1], board[2][2])) {
                highlightWinningLine(buttons[0], buttons[4], buttons[8]);
            } else if (checkLine(board[0][2], board[1][1], board[2][0])) {
                highlightWinningLine(buttons[2], buttons[4], buttons[6]);
            } else if (isBoardFull()) {
                gameWon = true;
                statusLabel.setText("2el regala mabtd3adlsh");
            }
        }

        private boolean checkLine(String s1, String s2, String s3) {
            return !s1.equals("") && s1.equals(s2) && s2.equals(s3);
        }

        private void highlightWinningLine(JButton b1, JButton b2, JButton b3) {
            b1.setBackground(new Color(50, 205, 50));
            b2.setBackground(new Color(50, 205, 50));
            b3.setBackground(new Color(50, 205, 50));
            gameWon = true;
            if(currentPlayer == 'X')statusLabel.setText("Rgloa yad");
            else statusLabel.setText("8afelk ya hafa2");

        }

        private void resetGame() {
            currentPlayer = 'X';
            gameWon = false;
            statusLabel.setText("shaklk fady w ht2adeha l3b");
            for (JButton button : buttons) {
                button.setText("");
                button.setBackground(new Color(60, 60, 60));
                button.setForeground(Color.WHITE);
            }
        }


        private void aiMove() {
            if (gameWon) return;

            // AI logic to make a move
            int move = findBestMove();
            buttons[move].doClick();
        }

        private int findBestMove() {
            // Check if AI can win in the next move
            for (int i = 0; i < 9; i++) {
                if (buttons[i].getText().equals("")) {
                    buttons[i].setText("O");
                    if (isWinningMove()) {
                        buttons[i].setText("");
                        return i;
                    }
                    buttons[i].setText("");
                }
            }

            // Check if the player can win in the next move and block them
            for (int i = 0; i < 9; i++) {
                if (buttons[i].getText().equals("")) {
                    buttons[i].setText("X");
                    if (isWinningMove()) {
                        buttons[i].setText("");
                        return i;
                    }
                    buttons[i].setText("");
                }
            }

            // Otherwise, make a random move
            Random rand = new Random();
            int move;
            do {
                move = rand.nextInt(9);
            } while (!buttons[move].getText().equals(""));

            return move;
        }

        private boolean isWinningMove() {
            String[][] board = new String[3][3];
            for (int i = 0; i < 9; i++) {
                board[i / 3][i % 3] = buttons[i].getText();
            }

            // Check rows, columns, and diagonals for a winning move
            for (int i = 0; i < 3; i++) {
                if (checkLine(board[i][0], board[i][1], board[i][2])) return true;
                if (checkLine(board[0][i], board[1][i], board[2][i])) return true;
            }
            if (checkLine(board[0][0], board[1][1], board[2][2])) return true;
            if (checkLine(board[0][2], board[1][1], board[2][0])) return true;

            return false;
        }

        private boolean isBoardFull() {
            for (JButton button : buttons) {
                if (button.getText().equals("")) {
                    return false;
                }
            }
            return true;
        }
    }
