import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GameGUI {

    private JFrame frame;
    private JTextArea textArea;
    private JButton[] botones = new JButton[4];

    private final ArrayList<Char> equipo1 = new ArrayList<>();
    private final ArrayList<Char> equipo2 = new ArrayList<>();

    private int turno = 0;
    private int fase = 0;
    private int seleccionados = 0;
    private Char personajeActual;
    private ArrayList<Char> enemigos;

    public GameGUI() {
        frame = new JFrame("DJCrawl GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4));
        for (int i = 0; i < 4; i++) {
            final int index = i;
            botones[i] = new JButton(String.valueOf(i));
            botones[i].addActionListener((ActionEvent e) -> manejarEntrada(index));
            panelBotones.add(botones[i]);
        }

        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.setVisible(true);

        mostrarTexto("=== Bienvenido a DJ Crawl ===\nJugador 1: selecciona 3 personajes. Opciones:\n0. Bárbaro\n1. Caballero\n2. Nigromante\n3. Arquero");
    }

    public void mostrarTexto(String texto) {
        textArea.append(texto + "\n");
    }

    private void manejarEntrada(int valor) {
        if (fase == 0) { // Selección de personajes jugador 1 y 2
            Char nuevo = switch (valor) {
                case 0 -> new Barbaro("Barbaro" + (seleccionados + 1), turno == 1);
                case 1 -> new caballero("Caballero" + (seleccionados + 1), turno == 1);
                case 2 -> new Nigromante("Nigromante" + (seleccionados + 1), turno == 1);
                case 3 -> new Arquero("Arquero" + (seleccionados + 1), turno == 1);
                default -> null;
            };

            if (nuevo != null) {
                if (turno == 0) equipo1.add(nuevo);
                else equipo2.add(nuevo);

                seleccionados++;
                if (seleccionados == 3) {
                    seleccionados = 0;
                    turno++;
                    if (turno == 1) {
                        mostrarTexto("\nJugador 2: selecciona 3 personajes. Opciones:\n0. Bárbaro\n1. Caballero\n2. Nigromante\n3. Arquero");
                    } else {
                        fase = 1;
                        turno = 0;
                        mostrarTexto("\n=== Empieza el combate ===");
                        mostrarTurno();
                    }
                }
            }
        } else if (fase == 1) { // Elegir personaje activo
            ArrayList<Char> activos = (turno % 2 == 0) ? equipo1 : equipo2;
            if (valor >= 0 && valor < activos.size() && activos.get(valor).Hp > 0) {
                personajeActual = activos.get(valor);
                enemigos = (turno % 2 == 0) ? equipo2 : equipo1;
                fase = 2;
                mostrarTexto("\n1. Atacar  2. Usar habilidad especial");
            }
        } else if (fase == 2) { // Elegir acción
            if (valor == 1 || valor == 2) {
                fase = 3;
                mostrarTexto("\nElige objetivo enemigo (0-2):");
                seleccionados = valor; // reutilizamos esta variable para guardar acción
            }
        } else if (fase == 3) { // Elegir objetivo
            if (valor >= 0 && valor < enemigos.size()) {
                boolean exito = personajeActual.ente(seleccionados, valor, enemigos);
                if (exito) {
                    for (Char c : equipo1) c.tick();
                    for (Char c : equipo2) c.tick();
                    turno++;
                    fase = 1;
                    mostrarTurno();
                } else {
                    mostrarTexto("Acción inválida. Intenta otra vez.");
                    fase = 1;
                    mostrarTurno();
                }
            }
        }
    }

    private void mostrarTurno() {
        if (juegoTerminado()) {
            mostrarGanador();
            for (JButton b : botones) b.setEnabled(false);
            return;
        }

        mostrarTexto("\n--- Turno " + (turno + 1) + " ---");
        ArrayList<Char> activos = (turno % 2 == 0) ? equipo1 : equipo2;
        mostrarTexto("Elige personaje para actuar:");
        for (int i = 0; i < activos.size(); i++) {
            Char c = activos.get(i);
            if (c.Hp > 0)
                mostrarTexto("" + i + ". " + c.name + " (HP: " + c.Hp + ", CD: " + c.cooldown + ")");
        }
    }

    private boolean juegoTerminado() {
        boolean vivos1 = equipo1.stream().anyMatch(c -> c.Hp > 0);
        boolean vivos2 = equipo2.stream().anyMatch(c -> c.Hp > 0);
        return !(vivos1 && vivos2);
    }

    private void mostrarGanador() {
        boolean vivos1 = equipo1.stream().anyMatch(c -> c.Hp > 0);
        boolean vivos2 = equipo2.stream().anyMatch(c -> c.Hp > 0);

        if (vivos1 && !vivos2) mostrarTexto("\n¡Gana el Jugador 1!");
        else if (!vivos1 && vivos2) mostrarTexto("\n¡Gana el Jugador 2!");
        else mostrarTexto("\nEmpate.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }


}
