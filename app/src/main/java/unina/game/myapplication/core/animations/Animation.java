package unina.game.myapplication.core.animations;

public interface Animation {

    void start();

    void dispose();

    void process(float deltaTime);

    boolean isFinished();

}
