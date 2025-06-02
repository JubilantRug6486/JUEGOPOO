public class NoMuerto extends Char {
    private int tiempoRestante = 3;

    public NoMuerto(String name, boolean tea) {
        super(name, 1, 6, 0, 0, 2, tea, false);
    }

    @Override
    public void atacar(Char objetivo) {
        frase(5);
        Output.println(name + " araña a " + objetivo.name);
        int covv = objetivo.armo();
        if (covv == 1) return;
        int dano = Fue - (objetivo.Res / 2);
        if (dano < 1) dano = 1;
        objetivo.Hp -= dano;
        Output.println(objetivo.name + " recibe " + dano + " de daño! HP restante: " + objetivo.Hp);
    }

    @Override
    public void tick() {
        super.tick();
        tiempoRestante--;
        if (tiempoRestante <= 0) {
            frase(6);
            Output.println(name + " se descompone y deja de existir.");
            this.Hp = 0;
        }
    }
}