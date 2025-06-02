import java.util.Random;

public class Barbaro extends Char{

    int rev = 1;

    public Barbaro(String name, boolean tea) {
        super(name, 5, 3, 1, 0, 3, tea, false);
    }

    @Override
    protected boolean aco(Char objetivo) {
        if (cooldown > 0) {
            Output.println(name + " aún no puede usar su habilidad. Cooldown restante: " + cooldown);
            return false;
        }

        cooldown = MAX_COOLDOWN;

        if (this.tea == objetivo.tea) {
            frase(1);
            Output.println("Ese personaje es de tu equipo, selecciona de nuevo.");
            return false;
        }

        // Ignora armadura
        Random rand = new Random();
        int dado = rand.nextInt(6) + 1;

        if (dado >= MeS) {
            Output.println(name + " impacta con un barbaro golpe a " + objetivo.name + "!");

            int dano = Fue - (objetivo.Res / 2);
            if (dano < 1) dano = 1;

            objetivo.Hp -= dano;
            Output.println(objetivo.name + " recibe " + dano + " de daño! HP restante: " + objetivo.Hp);
            objetivo.tiend();
        } else {
            Output.println(name + " falla el ataque especial contra " + objetivo.name + ".");
        }

        return true;
    }


    @Override
    public void tiend() {
        if(rev < 0){
            return;
        }
        if (this.Hp <= 0) {
            this.Hp = 1;
            this.MeS++;
            this.Fue++;
            frase(3);
            Output.println("El barbaro ha enfurecido, y da el ultimo haz de vida en su cuerpo para una ultima barbarie");
        }
    }
}
