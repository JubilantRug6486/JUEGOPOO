
public class Output {
    public static GameGUI gui;

    public static void println(String msg) {
        if (gui != null) {
            gui.mostrarTexto(msg);
        } else {
            System.out.println(msg);
        }
    }
}