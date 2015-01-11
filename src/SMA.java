import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SMA {

    public static ArrayList<Agent> agents = new ArrayList<Agent>();
    public ArrayList<Agent> agents_alea = new ArrayList<Agent>();
    public String fish_shark_pop = "";

    public static void main(String[] args) {
        SMA sm = new SMA();
        Env a = sm.constructor(100, 75, 4, 4, 30);
        sm.write_file("coucou\n toi!");
        for (int i = 0; i < 50; i++) {
            // sm.render(a);
            // System.out.println(agents.size());
            sm.dIt();
        }
    }

    public void dIt() {
        randomize_agents();
        for (int i = 0; i < agents_alea.size(); i++) {
            Agent a = agents_alea.get(i);
            a.doIt();
        }
    }

    public void render(Env e) {
        for (Agent i[] : e.map) {
            for (Agent j : i) {
                if (j == null) {
                    System.out.print(" |");
                } else {
                    System.out.print(j + "|");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------------");
    }

    public void fish_shark_pop_graph(String s) {
        fish_shark_pop += s;
    }

    public void write_file(String s) {
        try {
            PrintWriter writer = new PrintWriter("test.log", "UTF-8");
            writer.println(s);
            writer.close();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public Env constructor(int nb_fish, int nb_shark, int breeding_time, int feeding_time, int lenght_map) {
        Env env = new Env(lenght_map, lenght_map);
        for (int i = 0; i < nb_fish; i++) {
            boolean search = true;
            while (search) {
                int x = get_alea(0, lenght_map - 1);
                int y = get_alea(0, lenght_map - 1);
                if (env.map[x][y] == null) {
                    Agent f = new Fish(env, x, y, breeding_time);
                    agents.add(f);
                    search = false;
                }
            }
        }

        for (int i = 0; i < nb_shark; i++) {
            boolean search = true;
            while (search) {
                int x = get_alea(0, lenght_map - 1);
                int y = get_alea(0, lenght_map - 1);
                if (env.map[x][y] == null) {
                    Agent s = new Shark(env, x, y, breeding_time, feeding_time);
                    agents.add(s);
                    search = false;
                }
            }
        }

        return env;
    }

    private void randomize_agents() {
        this.agents_alea = agents;
        Collections.shuffle(agents_alea);
    }

    public int get_alea(int min, int max) {
        Random rand = new Random();
        return (rand.nextInt(max - min + 1) + min);
    }


}
