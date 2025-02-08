package unina.game.myapplication.core.animations;

import unina.game.myapplication.core.Utility;

public interface EaseFunction {

    EaseFunction LINEAR = x -> Utility.clamp(0, 1, x);
    EaseFunction CUBIC_IN_OUT = x -> x < 0.5 ? 4 * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 3) / 2);

    float evaluate(float x);

}
