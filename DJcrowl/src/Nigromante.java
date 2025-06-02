import java.util.ArrayList;
import java.util.Random;

public class Nigromante extends Char {
    private final ArrayList<Char> muertos = new ArrayList<>();

    public Nigromante(String name, boolean tea) {
        super(name, 4, 2, 2, 1, 1, tea, false);
    }

    @Override
    public void atacar(Char objetivo) {
        super.atacar(objetivo);
        if (objetivo.Hp <= 0 && !muertos.contains(objetivo)) {
            muertos.add(objetivo);
            Output.println(objetivo.name + " ha sido marcado para resurrección.");
        }
    }

    @Override
    protected boolean aco(Char objetivo) {
        if (cooldown > 0) {
            Output.println(name + " aún no puede usar su habilidad. Cooldown restante: " + cooldown);
            return false;
        }

        if (!muertos.contains(objetivo) || objetivo.Hp > 0) {
            Output.println("Ese objetivo no está marcado para revivir.");
            return false;
        }

        cooldown = MAX_COOLDOWN;
        Output.println(name + " revive a " + objetivo.name + " como no-muerto de su equipo!");
        objetivo = new NoMuerto("Zombie de " + objetivo.name, this.tea);
        return true;
    }
}