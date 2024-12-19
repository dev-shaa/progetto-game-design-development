package unina.game.myapplication.core;

public abstract class AnimationComponent extends Component {

    @Override
    public final Type getType() {
        return Type.ANIMATION;
    }

    public abstract void update(float deltaTime);

}
