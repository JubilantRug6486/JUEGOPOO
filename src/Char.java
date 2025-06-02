import java.util.ArrayList;
import java.util.Random;

public class Char {
    protected String name;  // nombre
    protected int Hp;       // Vida
    protected int MeS;      // Habilidad Melee (ahora general)
    protected int Res;      // Resistencia a impactos
    protected int Arm;      // Armadura
    protected int Fue;      // Fuerza del ataque
    protected boolean tea;  // Equipo (false = aliados, true = enemigos)
    protected boolean typ;  // funcionamiento, ignoren

    protected int cooldown = 0;
    protected final int MAX_COOLDOWN = 2;

    public Char(String name, int Hp, int MeS, int Res, int Arm, int Fue, boolean tea, boolean typ) {
        this.name = name;
        this.Hp = Hp;
        this.MeS = MeS;
        this.Res = Res;
        this.Arm = Arm;
        this.Fue = Fue;
        this.tea = tea;
        this.typ = typ;
    }

    public boolean ente(int accion, int objetivo, ArrayList<Char> enemigos) {
        if (accion < 1 || accion > 2) {
            frase(2);
            Output.println("Ese comando no existe, solo ingresa 1 o 2");
            return false;
        }

        switch (accion) {
            case 1:
                if (objetivo < 0 || objetivo >= enemigos.size()) {
                    Output.println("Ese objetivo no existe.");
                    return false;
                }
                Char objetivoAtaque = enemigos.get(objetivo);

                if (this.tea != objetivoAtaque.tea) {
                    atacar(objetivoAtaque);
                    return true;
                } else {
                    frase(1);
                    Output.println("Ese personaje es de tu equipo, selecciona de nuevo");
                    return false;
                }

            case 2:
                Char objetivoHabilidad = null;
                if (!this.typ) {
                    if (objetivo < 0 || objetivo >= enemigos.size()) {
                        Output.println("Ese objetivo no existe.");
                        return false;
                    }
                    objetivoHabilidad = enemigos.get(objetivo);
                }
                return aco(objetivoHabilidad);
        }
        return false;
    }

    public void tick() {
        if (cooldown > 0) cooldown--;
    }

    public void atacar(Char objetivo) {
        Random rand = new Random();
        int dad = rand.nextInt(6) + 1;

        if (dad >= MeS) {
            Output.println(name + " impacta a " + objetivo.name + "!");

            int covv = objetivo.armo();
            if (covv == 1) return;

            int dano = Fue - (objetivo.Res / 2);
            if (dano < 1) dano = 1;

            objetivo.Hp -= dano;
            Output.println(objetivo.name + " recibe " + dano + " de daño! HP restante: " + objetivo.Hp);

            objetivo.tiend();
            if (objetivo.Hp <= 0) {
                objetivo.Hp = 0;
                frase(6);
            }
        } else {
            Output.println(name + " falla el ataque contra " + objetivo.name + ".");
        }
    }

    protected int armo() {
        Random rand = new Random();
        if (Arm > 0) {
            int dad3 = rand.nextInt(4) + 1;
            if (dad3 == 1) {
                Arm--;
                Output.println(name + " bloqueó el daño con su armadura! (Quedan " + Arm + " usos)");
                return 1;
            }
        }
        return 0;
    }

    protected boolean aco(Char asa) {
        Output.println("Este personaje no tiene activa");
        return false;
    }

    protected void frase(int sit) {
        String[] frases;
        switch (sit) {
            case 1 -> frases = new String[]{
                    "¡Ey! ¿te pegaban de niño acaso?",
                    "Ese es de los tuyos, genio.",
                    "Nop",
                    "¿Qué tienes en mente?",
                    "Tus neuronas pueden ser contadas con los dedos de un pingüino, y terminan sobrando",
                    "Fuiste amamantado con Red Bull apenas naciste",
                    "Atención de fuego amigo, selecciona a alguien más para atacar"
            };
            case 2 -> frases = new String[]{
                    "¿Ocupas lentes para leer bien?",
                    "Típico error de dedo",
                    "Whoops, he revisado en la biblioteca de Alejandría y el comando que proporcionaste no aparece",
                    "¿Estás prestando atención al menos?"
            };
            case 3 -> frases = new String[]{
                    "Me parece que no le ha gustado",
                    "Y murió... pero se rehusó",
                    "Oh oh",
                    "- Prepara el trasero, perra"
            };
            case 4 -> frases = new String[]{
                    "Esa armadura sigue como siempre brillante",
                    "Ni un solo rasguño",
                    "¿Le pegaste con fuerza, no?",
                    "- Diría que pegas como una damisela, pero creo que les estaría faltando el respeto a las damas"
            };
            case 5 -> frases = new String[]{
                    "La noche de los no muertos",
                    "¿Eso rimará con Grog?",
                    "- GRahhh!"
            };
            case 6 -> frases = new String[]{
                    "Vaya que muerden rápido el polvo",
                    "Definitivamente rimaba con Grog",
                    "Qué asco, quítenlo de mi vista"
            };
            default -> frases = new String[]{
                    "Tú no has visto nada"
            };
        }
        Random rand = new Random();
        Output.println(frases[rand.nextInt(frases.length)]);
    }

    public void tiend() {
        // Por defecto no hace nada
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return Hp;
    }

    public boolean isEnemy() {
        return tea;
    }
}
