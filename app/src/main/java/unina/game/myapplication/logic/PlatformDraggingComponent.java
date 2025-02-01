package unina.game.myapplication.logic;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.physics.RigidBody;

public class PlatformDraggingComponent extends PressableComponent {

    public RigidBody rigidBody;

    public float x0;
    public float y0;
    public float angle;

    @Override
    protected void onPointerDrag(int pointer, float x, float y) {
        super.onPointerDrag(pointer, x, y);
        float x0 = this.x0;
        float y0 = this.y0;
        float angle = - this.angle;
        float t = Utility.calcolaTproiezione(x0,y0,x,y,angle);
        float xProj = (float) (x0 + t * Math.cos(angle));
        float yProj = (float) (y0 + t * Math.sin(angle));
        rigidBody.setTransform(xProj,yProj);
    }

}
