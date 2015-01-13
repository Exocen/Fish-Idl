import java.util.Random;

public class Agent {

    protected final int breeding_time;
    protected int posX;
    protected int posY;
    protected Env env;
    protected int breeding_state = 0;
    protected String[] zone;
    protected int nb_shark = 0;
    protected int nb_fish = 0;
    protected int empty_case = 0;
    protected int age = 0;


    public Agent(Env e, int x, int y, int b) {
        this.zone = new String[8];
        this.env = e;
        this.posX = x;
        this.posY = y;
        this.breeding_time = b;
        env.map[posX][posY] = this;
    }


    /**
     * Le requin mange un poisson proche aléatoiresi possible
     * puis se deplace a son emplacement s'il n'y a pas de poisson
     * il cherche une case vide aléatoire proche et s'y déplace
     * s'il doit se reproduire il laisse un nouveau requin a son ancien emplacement
     * c-a-d que si aucune case proche n'est libre ou ne contient un poisson il ne peut se reproduire
     */
    protected void doIt() {
        age++;
        zone_build();
        if (empty_case != 0) {
            int alea_pos = get_alea(1, empty_case);

            int ite = 1;
            int x = 0;
            int y = 0;
            for (int i = 0; i < zone.length; i++) {
                if (zone[i] != null && zone[i].equals("E")) {
                    if (ite == alea_pos) {

                        //System.out.println("x=" + posX + " y=" + posY + " zone=" + zone[i] + " random=" + alea_pos + " nbfi=" + nb_fish);
                        int[] coord = coordinate(i);
                        x = coord[0];
                        y = coord[1];
                        i = zone.length + 1;//sortie de boucle
                    } else {
                        ite++;
                    }
                }
            }
            if (x == 0 && y == 0) {
                System.out.println("x=" + posX + " y=" + posY + " ERREUR");
            }
            int x2 = posX + x;
            int y2 = posY + y;
            //System.out.println("nx="+x2+" ny="+y2+" ox="+posX+" oy="+posY);
            int old_posX = posX;
            int old_posY = posY;
            set_pos(x2, y2);

            //reproduce her,e pas de reproduction si bloqué donc
            if (breeding_state >= breeding_time) {
                reproduce(old_posX, old_posY);
                breeding_state = 0;
            } else {
                breeding_state++;
            }

        }
    }

    protected void reproduce(int posX, int posY) {
    }

    protected int get_alea(int min, int max) {
        Random rand = new Random();
        return (rand.nextInt(max - min + 1) + min);
    }

    protected void set_pos(int x, int y) {
        env.map[posX][posY] = null;
        this.posX = x;
        this.posY = y;
        env.map[posX][posY] = this;
    }

    protected void zone_build() {
        zone = new String[8];
        nb_fish = 0;
        nb_shark = 0;
        empty_case = 0;
        for (int i = 0; i <= 7; i++) {
            int[] emp = coordinate(i);
            int x2 = posX + emp[0];
            int y2 = posY + emp[1];
            if (x2 < env.map_lenght
                    && x2 >= 0
                    && y2 < env.map_lenght
                    && y2 >= 0) {
                if (env.map[x2][y2] == null) {
                    zone[i] = "E";
                    empty_case++;
                } else {
                    zone[i] = env.map[x2][y2].toString();
                    if (zone[i].equals("S")) {
                        nb_shark++;
                    } else if (zone[i].equals("F")) {
                        nb_fish++;
                    }
                }
            }
        }
    }

    protected int[] coordinate(int x) {
        switch (x) {
            case 0:
                return new int[]{0, 1};
            case 1:
                return new int[]{1, 1};
            case 2:
                return new int[]{1, 0};
            case 3:
                return new int[]{1, -1};
            case 4:
                return new int[]{0, -1};
            case 5:
                return new int[]{-1, -1};
            case 6:
                return new int[]{-1, 0};
            case 7:
                return new int[]{-1, 1};
            default:
                return new int[]{0, 0};
        }
    }

    public int get_age() {
        return age;
    }

    public String toString() {
        return "A";
    }

}
