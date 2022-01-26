package GUIs;

import Game.Board.Square;
import Game.*;
import Game.Move.Move;
import Game.Piece.Piece;
import Game.Piece.PieceType;
import Game.Piece.Pieces.*;
import LibaryFunctions.MultiThread;
import LibaryFunctions.Repository;
import LibaryFunctions.Utility;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI_BoardPanel extends JPanel {

    private List<Tile> Tiles;
    private Square selectedSquare = null;
    private Square destinationSquare;
    private final MultiThread multiThread;

    /**
     * Constructor for the board JPanel
     */
    public GUI_BoardPanel() {
        int Size = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * (0.89));
        multiThread = new MultiThread(new Minimax(GUI_GamePanel.getGame()));

        this.setSize(Size, Size);
        this.setLayout(null);

        InitialiseTiles();


    }

    /**
     * Initializes the board tiles to be displayed. Each tile is a JPanel which is displayed on the board JPanel
     */
    private void InitialiseTiles() {
        Colour colour = Colour.BLACK;
        int TileSize = (this.getSize().height) / 8;
        Tile tile;
        Tiles = new ArrayList<>();
        Game game = GUI_GamePanel.getGame();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                Color tileColour;
                if (i % 2 != j % 2) {
                    tileColour = new Color(234, 182, 118);
                } else {
                    tileColour = new Color(148, 85, 9);
                }

                tile = new Tile(game.getBoard().getBoardArray()[(7 - (i - 1))][(7 - (j - 1))], TileSize, tileColour);
                tile.setBackground(tileColour);

                if (game.getSelectedColour() == Colour.WHITE) {
                    tile.setBounds((7 - (j - 1)) * (TileSize), this.getSize().height - ((8 - (i - 1)) * TileSize), TileSize, TileSize);
                } else {
                    tile.setBounds((j - 1) * (TileSize), this.getSize().height - (i * TileSize), TileSize, TileSize);
                }
                tile.setLayout(null);

                if (tile.getSquare().SquareOccupied()) {
                    if (tile.getSquare().ReturnPiece().getColour() == Colour.WHITE) {
                        tile.getIcon().setForeground(new Color(247, 229, 195));
                    } else tile.getIcon().setForeground(new Color(59, 40, 4));
                    String Icon = tile.getSquare().ReturnPiece().ReturnPieceIcon();
                    tile.getIcon().setText(Icon);
                }
                tile.getIcon().setFont(new Font("", Font.PLAIN, TileSize));

                Tile finalTile = tile;
                final Border[] previousBorder = new Border[1];
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if ((game.getPlayerToMove().getPlayingColour() == game.getSelectedColour() && game.getGameType() == GameType.VERSES_COMPUTER)
                                || (game.getGameType() == GameType.LOCAL_MULTIPLAYER)) {
                            if (selectedSquare == null) { //Selecting a square
                                SelectOriginSquare(finalTile, game);

                            } else { //Selecting a destination
                                List<Move> moves = selectedSquare.ReturnPiece().CalculateValidMoves(game.getBoard());
                                boolean valid = SelectDestinationSquare(finalTile, game, moves);

                                if (valid) {
                                    //todo Update Board
                                    UpdateBoard();

                                    game.setMovesMade(game.getMovesMade() + 1);
                                    previousBorder[0] = null;
                                    //Make king Square red if checked
                                    game.UpdatePlayerToMove();
                                    for (Tile tile1 : Tiles){
                                        if (tile1.getSquare().SquareOccupied() && tile1.getSquare().ReturnPiece() instanceof King && tile1.getSquare().ReturnPiece().getColour() == game.getPlayerToMove().getPlayingColour()){
                                            if (Game.isKingChecked(game.getPlayerToMove().getPlayingColour(), game.getBoard())){
                                                tile1.setBackground(new Color(255,77,77));
                                            }
                                        } else {
                                            tile1.setBackground(tile1.getColour());
                                            tile1.setBorder(null);
                                        }
                                    }
                                    destinationSquare = null;
                                    selectedSquare = null;

                                    GameOver();
                                    if (game.getGameType() == GameType.VERSES_COMPUTER) {
                                        ComputerTurn(game);
                                    }

                                } else if ((finalTile.getSquare().SquareOccupied() && finalTile.getSquare().ReturnPiece().getColour() == game.getSelectedColour() && game.getGameType() == GameType.VERSES_COMPUTER)
                                        || (finalTile.getSquare().SquareOccupied() && finalTile.getSquare().ReturnPiece().getColour() == game.getPlayerToMove().getPlayingColour() && game.getGameType() == GameType.LOCAL_MULTIPLAYER)) {
                                    SelectOriginSquare(finalTile, game);
                                    destinationSquare = null;
                                    selectedSquare = null;
                                }
                            }
                        } else System.out.println("not your turn");
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        previousBorder[0] = finalTile.getBorder();
                        if (finalTile.getBorder() == null){
                            finalTile.setBorder(new LineBorder(Color.RED, 3));
                        } else {
                            finalTile.setBorder(new LineBorder(finalTile.getColour(), 35));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        finalTile.setBorder(previousBorder[0]);
                    }
                });

                Tiles.add(tile);
                this.add(tile);
                colour = Colour.GetOtherColour(colour);
            }
        }

        if (GUI_GamePanel.getGame().getGameType() == GameType.VERSES_COMPUTER && GUI_GamePanel.getGame().getSelectedColour() != Colour.WHITE) {
            ComputerTurn(game);
        }
    }

    private void ComputerTurn(Game game) {
        Thread newThread = new Thread(multiThread);
        newThread.start();
        game.setMovesMade(game.getMovesMade() + 1);
        GameOver();
    }

    private Piece choosePromotionPiece(Move move) {
        JComboBox<String> piece = new JComboBox<>(new String[]{"Queen", "Knight", "Bishop", "Rook"});

        final JComponent[] Options = new JComponent[]{
                new JLabel("Piece"),
                piece,
        };

        int result = JOptionPane.showConfirmDialog(this, Options, "Promote Pawn", JOptionPane.DEFAULT_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (piece.getItemAt(piece.getSelectedIndex()).equals("Queen")) {
                return new Queen(move.getEndPosition().ReturnCoordinate(), move.getMovedPiece().getColour(), PieceType.QUEEN);
            } else if (piece.getItemAt(piece.getSelectedIndex()).equals("Knight")) {
                return new Knight(move.getEndPosition().ReturnCoordinate(), move.getMovedPiece().getColour(), PieceType.KNIGHT);
            } else if (piece.getItemAt(piece.getSelectedIndex()).equals("Bishop")) {
                return new Bishop(move.getEndPosition().ReturnCoordinate(), move.getMovedPiece().getColour(), PieceType.BISHOP);
            } else if (piece.getItemAt(piece.getSelectedIndex()).equals("Rook")) {
                return new Rook(move.getEndPosition().ReturnCoordinate(), move.getMovedPiece().getColour(), PieceType.ROOK, CastlingAvailability.NEITHER);
            }
        }
        return null;
    }

    private void GameOver() {
        if (GUI_GamePanel.getGame().isGameOver()) {

            Player winner = GUI_GamePanel.getGame().gameOver();
            GameOutcome gameOutcome;

            if (winner instanceof Player.Human) {
                if (((Player.Human) winner).getUser() != null && ((Player.Human) winner).getUser().getUserID().equals(Repository.getCurrentUser().getUserID())) {
                    gameOutcome = GameOutcome.WIN;
                    Repository.getCurrentUser().getStatistics().setWins(Repository.getCurrentUser().getStatistics().getWins() + 1);
                } else {
                    gameOutcome = GameOutcome.LOSS;
                    Repository.getCurrentUser().getStatistics().setLosses(Repository.getCurrentUser().getStatistics().getLosses() + 1);
                }
            } else if (winner instanceof Player.Computer) {
                gameOutcome = GameOutcome.LOSS;
                Repository.getCurrentUser().getStatistics().setLosses(Repository.getCurrentUser().getStatistics().getLosses() + 1);
            } else {
                gameOutcome = GameOutcome.DRAW;
                Repository.getCurrentUser().getStatistics().setDraws(Repository.getCurrentUser().getStatistics().getDraws() + 1);
            }

            System.out.println(gameOutcome);

            if (gameOutcome == GameOutcome.DRAW) {
                JOptionPane.showMessageDialog(this, "Draw by Stalemate");
            } else {
                JOptionPane.showMessageDialog(this, ("Checkmate! " + winner.getPlayingColour() + " Wins"));
            }
            this.setVisible(false);
            this.setEnabled(false);

            if (!Repository.getCurrentUser().getUserID().equals("Guest")) {
                int PlayerELO = Repository.getCurrentUser().getStatistics().getELO();
                if (GUI_GamePanel.getGame().getGameType() == GameType.LOCAL_MULTIPLAYER) {
                    Repository.getCurrentUser().getStatistics().setELO(
                            Utility.CalculateNew_ELO(PlayerELO, PlayerELO, gameOutcome));
                } else {
                    int ComputerELO;
                    if (Repository.getCurrentUser().getStatistics().getELO() <= 800) {
                        ComputerELO = 700;
                    } else if (Repository.getCurrentUser().getStatistics().getELO() <= 1300) {
                        ComputerELO = 1200;
                    } else if (Repository.getCurrentUser().getStatistics().getELO() <= 1600) {
                        ComputerELO = 1500;
                    } else if (Repository.getCurrentUser().getStatistics().getELO() <= 1900) {
                        ComputerELO = 1800;
                    } else if (Repository.getCurrentUser().getStatistics().getELO() <= 2400) {
                        ComputerELO = 2300;
                    } else {
                        ComputerELO = 2700;
                    }
                    Repository.getCurrentUser().getStatistics().setELO(
                            Utility.CalculateNew_ELO(PlayerELO, ComputerELO, gameOutcome));
                }
                Repository.updateUsersStats();
                Repository.AddGame(GUI_GamePanel.getGame(), GUI_GamePanel.getGame().getSelectedColour());
            }
        }
    }

    private void SelectOriginSquare(Tile finalTile, Game game) {
        selectedSquare = finalTile.getSquare();
        if (selectedSquare.SquareOccupied()) { //If the square is occupied
            if ((selectedSquare.ReturnPiece().getColour() == game.getSelectedColour() && game.getGameType() == GameType.VERSES_COMPUTER)
                    || (selectedSquare.ReturnPiece().getColour() == game.getPlayerToMove().getPlayingColour() && game.getGameType() == GameType.LOCAL_MULTIPLAYER)) { //then if the piece is the players piece DO..
                List<Move> moves = selectedSquare.ReturnPiece().CalculateValidMoves(game.getBoard());
                for (Tile tile1 : Tiles) {
                    tile1.setBorder(null);

                    if (tile1.getSquare().SquareOccupied() && tile1.getSquare().ReturnPiece() instanceof King && tile1.getSquare().ReturnPiece().getColour() == game.getPlayerToMove().getPlayingColour()){
                        if (Game.isKingChecked(game.getPlayerToMove().getPlayingColour(), game.getBoard())){
                            tile1.setBackground(new Color(255,77,77));
                        }
                    } else{
                        tile1.setBackground(tile1.getColour());
                    }
                }
                for (Move move : moves) {
                    for (Tile tile1 : Tiles) {
                        if (tile1.getSquare().ReturnCoordinate().CompareCoordinates(move.getEndPosition())) {
                            tile1.setBorder(new LineBorder(tile1.getColour(), 22));
                            if (move instanceof Move.CapturingMove || move instanceof Move.PawnPromotionCapture){
                                tile1.setBackground(new Color(189, 120, 171));
                            } else {
                                if (!tile1.getBackground().equals(new Color(255,77,77))){
                                    tile1.setBackground(new Color(178,190,181));
                                }
                            }
                        }
                    }
                }
            } else selectedSquare = null;
        } else selectedSquare = null;
    }

    private boolean SelectDestinationSquare(Tile finalTile, Game game, List<Move> moveList) {
        destinationSquare = finalTile.getSquare();
        boolean valid = false;

        for (Move move : moveList) {
                if (move.getEndPosition().ReturnCoordinate().CompareCoordinates(destinationSquare)) {
                    valid = true;

                    //todo remove print
                    System.out.println();
                    System.out.println(game.MoveToNotation(move));

                    if (move instanceof Move.PawnPromotion) {
                        Move pawnPromotionMove = new Move.PawnPromotion(move.getStartPosition(), move.getEndPosition(), choosePromotionPiece(move));
                        Game.MakeMove(pawnPromotionMove, game.getBoard());

                    } else if (move instanceof Move.PawnPromotionCapture) {
                        Move pawnPromotionMove = new Move.PawnPromotionCapture(move.getStartPosition(), move.getEndPosition(), choosePromotionPiece(move), move.getCapturedPiece());
                        Game.MakeMove(pawnPromotionMove, game.getBoard());

                    } else Game.MakeMove(move, game.getBoard());

                    //todo remove print
                    game.getBoard().PrintBoard();

                    break;
                }
        }
        return valid;
    }

    private void UpdateBoard() {
        for (Tile tile : Tiles) {
            this.remove(tile);
            if (tile.getSquare().SquareOccupied()) {
                if (tile.getSquare().ReturnPiece().getColour() == Colour.WHITE) {
                    tile.getIcon().setForeground(new Color(247, 229, 195));
                } else tile.getIcon().setForeground(new Color(59, 40, 4));

                String Icon = tile.getSquare().ReturnPiece().ReturnPieceIcon();
                tile.setIconText(Icon);
            } else {
                tile.setIconText("");
            }
            this.add(tile);
        }
    }
}
