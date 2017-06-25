import java.awt.Point;

public abstract class AI {

    //state is the real-time chess state
    //color is the color of the next chess
    //the function should return the place you want insert
    //
    //you can't change state, so you must make a copy of 'state' at first
    //
    public abstract Point calStep(State state, int color);
}
