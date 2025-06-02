public class caballero extends Char {
    private boolean interceptado = false;
    private boolean protegiendo = false;
    private boolean escudoActivo = false;

    public caballero(String name, boolean tea) {
        super(name, 5, 3, 2, 2, 2, tea, false);
    }

    @Override
    public void atacar(Char objetivo) {
        if (objetivo.Hp <= 2 && !interceptado && objetivo.tea == this.tea && objetivo != this) {
            Output.println(name + " intercepta el golpe dirigido a " + objetivo.name + "!");
            this.Hp--;
            interceptado = true;
        } else {
            super.atacar(objetivo);
        }
    }

    @Override
    protected boolean aco(Char objetivo) {
        if (cooldown > 0) {
            Output.println(name + " aún no puede usar su habilidad. Cooldown restante: " + cooldown);
            return false;
        }

        cooldown = MAX_COOLDOWN;
        escudoActivo = true;
        Output.println(name + " se cubre con su escudo y protege a un aliado. Ambos recibirán la mitad del daño este turno.");
        return true;
    }

    @Override
    public int armo() {
        int resultado = super.armo();
        if ((protegiendo || escudoActivo) && resultado == 0) {
            this.Arm = Math.max(0, this.Arm - 1);
            Output.println(name + " reduce el daño con su escudo!");
            escudoActivo = false;
            return 1;
        }
        return resultado;
    }
}