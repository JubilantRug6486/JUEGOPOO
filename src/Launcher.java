import javax.swing.*;
import java.awt.*;

public class Launcher extends JFrame {
    public Launcher() {
        setTitle("Lanzador de DJ Crawl");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("¡Bienvenido a DJ Crawl!", SwingConstants.CENTER);
        JButton playButton = new JButton("Jugar");

        playButton.addActionListener(e -> {
            // Cierra el launcher
            dispose();

            // Ejecuta el juego con GUI
            GameGUI.main(new String[0]);
        });

        add(label, BorderLayout.CENTER);
        add(playButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Launcher::new);
    }
}
