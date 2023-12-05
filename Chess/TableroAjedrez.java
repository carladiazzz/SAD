import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableroAjedrez extends JFrame {

    private JPanel[][] casillas = new JPanel[8][8];

    public TableroAjedrez() {
        setTitle("Tablero de Ajedrez");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarTablero();

        // Layout con GridLayout para organizar los paneles como un tablero de ajedrez
        setLayout(new GridLayout(8, 8));

        // Agrega los paneles al JFrame
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                add(casillas[i][j]);
            }
        }
    }

    private void inicializarTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new JPanel();
                casillas[i][j].setBackground(obtenerColorCasilla(i, j));

                // Coloca una Piece en las casillas iniciales
                if (i == 0 && j == 0) {
                    //Piece br = new Piece(Constants.BROOK);
                    Piece wr = new Piece(Constants.WROOK);
                    colocarPieceEnCasilla(wr, i, j);
                }
                // Puedes añadir más Pieces según tus necesidades...
            }
        }
    }

    private void colocarPieceEnCasilla(Piece Piece, int fila, int columna) {
        JLabel pieza = new JLabel(new ImageIcon(getClass().getResource("/Resources/"+Constants.WROOK)));
        casillas[fila][columna].add(pieza);
    }

    private Color obtenerColorCasilla(int fila, int columna) {
        // Alterna el color de las casillas para simular un tablero de ajedrez
        if((fila+columna)%2==0)
            return Color.WHITE;
        else
            return Color.BLACK;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TableroAjedrez().setVisible(true));
    }
}
