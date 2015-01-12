public class Env {

    public final int map_lenght;
    public Agent[][] map;

    public Env(int a, int b) {
        map_lenght = a;
        map = new Agent[a][b];
    }


}
