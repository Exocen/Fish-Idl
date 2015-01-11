public class Shark extends Agent {

    private final int feeding_time;
    private int feeding_state = 0;

    public Shark(Env e, int x, int y, int b, int f) {
        super(e, x, y, b);
        this.feeding_time = f;

    }

    public void doIt() {
        zone_build();
        if (nb_fish != 0) {

            int random_fish = get_alea(1, nb_fish);
            int ite = 1;
            int x2 = 0;
            int y2 = 0;
            for (int i = 0; i < zone.length; i++) {
                 if (zone[i] != null && zone[i].equals("F")) {
                    if (ite == random_fish) {
                        //System.out.println("x=" + posX + " y=" + posY + " zone=" + zone[i] + " random=" + random_fish + " nbfi=" + nb_fish);
                        int[] coord = coordinate(i);
                        x2 = coord[0];
                        y2 = coord[1];
                        i=zone.length+1;
                    } else {
                        ite++;
                    }
                }
            }
            if (x2 == 0 && y2 == 0) {
                System.out.println("x=" + posX + " y=" + posY +" ERREUR");
            }
            x2 = posX + x2;
            y2 = posY + y2;

            //on enleve le poisson mangé
            SMA.agents.remove(env.map[x2][y2]);

            //on mange
            feeding_state = 0;

            int old_posX = posX;
            int old_posY = posY;
            set_pos(x2, y2);

            //reproduce here
            if (breeding_state >= breeding_time) {
                reproduce(old_posX, old_posY);
                breeding_state = 0;
            } else {
                breeding_state++;
            }
        } else //pas de poissons

        {

            if (++feeding_state > feeding_time) {
                SMA.agents.remove(this);
                env.map[posX][posY] = null;
            } else {
                super.doIt();
            }
        }

    }


    public void reproduce(int posX, int posY) {
        Agent s = new Shark(env, posX, posY, breeding_time, feeding_time);
        SMA.agents.add(s);
    }

    public String toString() {
        return "S";
    }
}
