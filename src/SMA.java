import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SMA extends JFrame implements ActionListener {

    public static ArrayList<Agent> agents = new ArrayList<Agent>();
    public final int length_map = 150;
    public ArrayList<Agent> agents_alea = new ArrayList<Agent>();
    public String fish_shark_pop = "F S\n";
    public String fish_shark_overTime = "T F S \n";
    public String fish_shark_age= "A F S\n";
    public int time = 0;
    public int nb_shark = 0;
    public int nb_fish = 0;
    public int slow = 20;
    public Object[][] data;
    public Env env;
    public boolean play = true;
    public boolean launch = true;
    public JButton ss;
    public JButton arret;
    //plot "graph_pop_time.log" u 1:2 w l, "graph_pop_time.log" u 1:3 w l

    public SMA() {
        super();
        setTitle("Fish & Shark");
        this.setLayout(new BorderLayout());
        ss = new JButton("Pause");
        this.add(ss, BorderLayout.EAST);
        arret = new JButton("Stop");
        this.add(arret, BorderLayout.SOUTH);
        arret.addActionListener(this);
        ss.addActionListener(this);
        //nb_fish,  nb_shark,  f/s_breeding_time,  feeding_time,  length_map
        //(length_map * 5, length_map * 5, 3, 8, 2, length_map);
        env = constructor(length_map * 5, length_map * 5, 3, 8, 2, length_map);
        data = new Object[length_map][length_map];
        Rect jc = new Rect();
        jc.Set_Rect(data);
        this.getContentPane().add(jc);
        this.setSize(length_map * 8, length_map * 8);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SMA sm = new SMA();
        while (sm.launch) {
            sm.play_it();
        }
        sm.write_file(sm.fish_shark_pop, "graph_pop.log");
        sm.write_file(sm.fish_shark_overTime, "graph_pop_time.log");
        sm.make_age();
        sm.write_file(sm.fish_shark_age,"graph_pop_age.log");
        sm.dispose();
        System.out.println("Sortie...");
    }

    public void play_it() {

        if (play) {
            if (!this.getFocusableWindowState()){
                render_console(env);
            }

            dIt();
            /*if (nb_fish == 0 || nb_shark == 0) {
                launch = false;
                play = false;
            }*/
        }
    }

    public void dIt() {
        readable_env();
        randomize_agents();
        int i = 0;
        while (i < agents_alea.size()) {
            Agent a = agents_alea.get(i);
            a.doIt();
            i++;
        }
        time++;
        get_pop();
        fish_shark_pop += nb_fish + " " + nb_shark + "\n";
        fish_shark_overTime += time + " " + nb_fish + " " + nb_shark + "\n";

        try {
            Thread.sleep(slow);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();

    }

    public void make_age(){
        int l = 101;
        int fa[]=new int[l];
        int sa[]=new int[l];

        for (Agent a: agents){
            if (a.toString().equals("F")){
                if (a.get_age()<l){
                    fa[a.get_age()]++;
                }
            }
            if (a.toString().equals("S")){
                if (a.get_age()<l){
                    sa[a.get_age()]++;
                }
            }
        }

        for (int i=0; i<l;i++){
            fish_shark_age+=i+" "+fa[i]+" "+sa[i]+"\n";
        }
        //System.out.println(fish_shark_age);


    }

    public void readable_env() {
        int i2 = 0;
        int j2 = 0;
        for (Agent i[] : env.map) {
            for (Agent j : i) {
                if (j == null) {
                    data[i2][j2] = " ";
                } else {
                    data[i2][j2] = "" + j.toString();
                }
                j2++;
            }
            i2++;
            j2 = 0;
        }
    }

    public void render_console(Env e) {
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

    public void get_pop() {
        nb_fish = 0;
        nb_shark = 0;
        for (int i = 0; i < agents_alea.size(); i++) {
            if (agents.get(i).toString().equals("S")) {
                nb_shark++;
            } else if (agents.get(i).toString().equals("F")) {
                nb_fish++;
            }
        }
    }

    public void write_file(String s, String file_name) {
        try {
            PrintWriter writer = new PrintWriter(file_name, "UTF-8");
            writer.println(s);
            writer.close();
        } catch (IOException ignored) {
        }
    }

    public Env constructor(int nb_fish, int nb_shark, int fish_breeding_time, int shark_breeding_time, int feeding_time, int lenght_map) {
        Env env = new Env(lenght_map, lenght_map);
        for (int i = 0; i < nb_fish; i++) {
            boolean search = true;
            while (search) {
                int x = get_alea(0, lenght_map - 1);
                int y = get_alea(0, lenght_map - 1);
                if (env.map[x][y] == null) {
                    Agent f = new Fish(env, x, y, fish_breeding_time);
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
                    Agent s = new Shark(env, x, y, shark_breeding_time, feeding_time);
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

    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == ss) {
            play = !play;
            if (play) {
                ss.setText("Start");
            } else {
                ss.setText("Pause");
            }
        } else if (arg0.getSource() == arret) {
            launch = false;
            play = false;

        }
    }

}
