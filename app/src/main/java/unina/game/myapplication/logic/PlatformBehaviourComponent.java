package unina.game.myapplication.logic;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.Utility;

public class PlatformBehaviourComponent extends BehaviourComponent {

    public float destY;
    public boolean hasToMove = false;
    @Override
    public void update(float deltaTime) {
//        if (hasToMove) {
//            float y = getOwner().y;
//            getOwner().y = Utility.moveTowards(y,destY,deltaTime);
//            if (y == destY)
//                hasToMove = false;
//        }
        float y = getOwner().y;
        getOwner().y = Utility.moveTowards(y,destY,deltaTime);
    }
}
