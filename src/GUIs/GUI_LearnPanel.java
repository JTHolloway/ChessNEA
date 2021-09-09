package GUIs;

import javax.swing.*;
import java.awt.*;

public class GUI_LearnPanel extends JPanel {

    /**
     * Constructor for learn JPanel
     */
    public GUI_LearnPanel() {
        initComponents();
        this.setVisible(false);
    }

    /**
     * Initializes the components of the JPanel with each components properties.
     */
    private void initComponents() {
        int ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        this.setBounds((int) (ScreenWidth * 0.2d), 0, (int) (ScreenWidth * 0.8d), ScreenHeight);
        this.setBackground(new Color(160, 160, 160));
        this.setLayout(null);

        JTextPane textArea = new JTextPane();
        textArea.setText("The game of chess has a board consisting of 64 squares on an 8x8 grid. There are two players who play as either white or black, each player has 16 pieces.\n" +
                " \n" +
                "•\t ♟ 8 pawn pieces which can move forward 1 space at a time unless it is their first move in which they can move 2 spaces. Pawns take the opponents pieces diagonally. They cannot move if their path is obstructed unless they can take a piece diagonally.\n" +
                " \n" +
                "•\t ♜ 2 Rooks which can move any number of spaces forward, backward, or sideward if their path is not obstructed by one of their own pieces. They cannot move diagonally. \n" +
                " \n" +
                "•\t ♞ 2 Knights which can move in an ‘L’ shape, meaning they can move 2 spaces then across one, they can jump over other pieces and it is the only piece that can do this.\n" +
                " \n" +
                "•\t ♝ 2 Bishops which can move any number of spaces diagonally if their path is not obstructed by one of their own pieces. They cannot move left/right or up/down. \n" +
                " \n" +
                "•\t ♛ A Queen which can move any number of spaces up/down, left, and right and diagonally. The queen is the most powerful piece on the board, but it cannot jump over its own pieces.\n" +
                " \n" +
                "•\t ♚ A King which can move 1 space at a time in any direction, the king cannot be taken but it is the most important piece because if it is check mated the game is lost. A king is in check when an opponent's piece can attack the king in its next move, if this happens the king must get out of check. Checkmate is when the king cannot move out of check because it is surrounded by other checked squares or if it is surrounded by its own pieces.\n" +
                " \n" +
                " \n" +
                "•\tStalemate: occurs when neither side can win, the game results in a draw. For example, both sides only have kings left, or one player’s king cannot move because all squares around them are in check but the king itself is not in check (so this is not checkmate).\n" +
                "\n" +
                "\n" +
                "\n" +
                "The game also has three special moves:\n" +
                "•\tPawn promotion: If you or your opponent gets one or multiple of their pawns to the other side of the board then it can be promoted (swapped for another piece) to a queen, knight, bishop or rook.\n" +
                " \n" +
                " \n" +
                "•\tEn Passant: French for ‘in passing’ meaning that if the opponent moves a pawn two spaces on its first move and ends up next to one of your pawns (Assuming you have already moved the pawn far enough to get there) then you can take their pawn as if it only moved one space (as if it is in the forwards diagonal square to your pawn). En passant can only be done in the move immediately after the pawn moves two spaces (not at any point in the game).\n" +
                " \n" +
                " \n" +
                "•\tCastling: When the space between the king and the rook is empty and the king is not in check, and the spaces between the king and rook are not checked then the king can castle if it is the king and rooks first move. Castling means the king can move two spaces towards the rook and the rook can move next to the king on the opposite side that it originally was. This move helps get the rook into the middle of the board while protecting the king.\n" +
                "The Rook and king essentially move over and swap during a castle.\n");
        textArea.setEditable(false);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Text Area", Font.PLAIN, ScreenHeight / 30));
        textArea.setBackground(new Color(160, 160, 160));

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setSize((int) (ScreenWidth * 0.8d), ScreenHeight);
        scrollPane.setLocation(0, 0);
        this.add(scrollPane);
    }
}