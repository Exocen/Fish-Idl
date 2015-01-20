public class Fish  extends Agent{

    protected final int breeding_time;
    protected int breeding_state = 0;
    protected int nb_shark = 0;
    protected int nb_tuna = 0;
    protected int empty_case = 0;


    public Fish(Env e, int x, int y, int b) {
        super(e, x, y);
        this.breeding_time = b;
    }



    protected void doIt() {
        super.doIt();
        if (empty_case != 0) {
            int alea_pos = get_alea(1, empty_case);

            int ite = 1;
            int x = 0;
            int y = 0;
            for (int i = 0; i < zone.length; i++) {
                if (zone[i] != null && zone[i].equals("E")) {
                    if (ite == alea_pos) {
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


    protected void zone_build() {
        zone = new String[8];
        nb_tuna = 0;
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
                    } else if (zone[i].equals("T")) {
                        nb_tuna++;
                    }
                }
            }
        }
    }



    public String toString() {
        return "F";
    }

}
