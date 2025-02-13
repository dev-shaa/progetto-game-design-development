package unina.game.myapplication.core.animations;

public abstract class Animation {

    public abstract void start();

    public abstract void dispose();

    public abstract void process(float deltaTime);

    public abstract boolean isFinished();

}
