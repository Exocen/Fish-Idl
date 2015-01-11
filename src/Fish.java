public class Fish extends Agent {


    public Fish(Env e, int x, int y, int b) {

        super(e, x, y, b);

    }

    public void doIt() {
        super.doIt();
    }

    @Override
    public void reproduce(int posX, int posY) {
        Agent f = new Fish(env, posX, posY, breeding_time);
        SMA.agents.add(f);

    }

    public String toString() {
        return "F";
    }

}
