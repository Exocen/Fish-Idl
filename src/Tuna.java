public class Tuna extends Fish {


    public Tuna(Env e, int x, int y, int b) {

        super(e, x, y, b);

    }

    @Override
    public void reproduce(int posX, int posY) {
        Agent f = new Tuna(env, posX, posY, breeding_time);
        SMA.agents.add(f);

    }

    public String toString() {
        return "T";
    }

}
