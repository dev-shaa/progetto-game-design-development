package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.ParticleSystem;
import unina.game.myapplication.core.physics.PrismaticJoint;
import unina.game.myapplication.core.physics.RevoluteJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.physics.TriangleCollider;
import unina.game.myapplication.logic.CursorJointInput;
import unina.game.myapplication.logic.common.CircleRenderer;
import unina.game.myapplication.logic.common.DraggablePlatformLineRenderer;
import unina.game.myapplication.logic.common.RectRenderer;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        ParticleSystem particles = createGameObject(0, 8).addComponent(ParticleSystem.class);
        particles.setRadius(0.15f);
        particles.setSize(4, 4);
        particles.setGroupFlags(ParticleSystem.FLAG_GROUP_SOLID);
        particles.setFlags(ParticleSystem.FLAG_PARTICLE_WATER);

        GameObject anchor = createGameObject(0, 0);
        RigidBody anchorRigidBody = anchor.addComponent(RigidBody.class);
        anchorRigidBody.setType(RigidBody.Type.STATIC);

        GameObject platform = createGameObject(0, 0, 0);

        RectRenderer renderer = platform.addComponent(RectRenderer.class);
        renderer.setSize(8, 0.25f);

        BoxCollider b = BoxCollider.build(0.25f, 0.25f, 0.1f, 0, 0, false);
        b.setCenter(-4f, 0.25f);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(8, 0.25f));
        rigidBody.addJoint(RevoluteJoint.build(anchorRigidBody));

        createWall(-5f, 0, 0, 0.5f, 40);
        createWall(5f, 0, 0, 0.5f, 40);
        createWall(0, -5f, 0, 10, 0.5f);

        createBox(-3, 14, 1, 1, 10);
        createBall(0f, -1, 0.5f, 1f);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void createBox(float x, float y, float width, float height, float density) {
        GameObject platform = createGameObject(x, y);

        RectRenderer renderer = platform.addComponent(RectRenderer.class);
        renderer.setSize(width, height);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(width, height, density, 0.2f, 1, false));
    }

    private void createBall(float x, float y, float radius, float density) {
        GameObject platform = createGameObject(x, y);

        CircleRenderer renderer = platform.addComponent(CircleRenderer.class);
        renderer.setRadius(radius);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(CircleCollider.build(radius, density, 0.2f, 0, false));
    }

    private void createWall(float x, float y, float angle, float width, float height) {
        GameObject platform = createGameObject(x, y, angle);

        RectRenderer renderer = platform.addComponent(RectRenderer.class);
        renderer.setSize(width, height);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.STATIC);
        rigidBody.addCollider(BoxCollider.build(width, height));
    }

    private void createPlatform(float startX, float startY, float endX, float endY, float angle, float width, float height) {
        float centerX = startX + (endX - startX) / 2;
        float centerY = startY + (endY - startY) / 2;

        GameObject anchor = createGameObject(centerX, centerY);

        RigidBody anchorRigidBody = anchor.addComponent(RigidBody.class);
        anchorRigidBody.setType(RigidBody.Type.STATIC);

        GameObject platform = createGameObject(startX, startY, angle);

        RectRenderer renderer = platform.addComponent(RectRenderer.class);
        renderer.setSize(width, height);

        float halfDistance = Utility.distance(startX, startY, endX, endY) / 2;
        float axisX = endX - startX;
        float axisY = endY - startY;

        PrismaticJoint joint = PrismaticJoint.build(anchorRigidBody, axisX, axisY);
        joint.setEnableLimit(true);
        joint.setLowerLimit(-halfDistance);
        joint.setUpperLimit(halfDistance);

        CursorJoint cursorJoint = CursorJoint.build();
        cursorJoint.setTarget(platform.x, platform.y);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(width, height));
        rigidBody.addJoint(joint);
        rigidBody.addJoint(cursorJoint);

        CursorJointInput input = platform.addComponent(CursorJointInput.class);
        input.setJoint(cursorJoint);
        input.setSize(width, width);
        input.setMaxForce(100000);
        input.setSnap(true);

        DraggablePlatformLineRenderer line = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        line.setStart(startX, startY);
        line.setEnd(endX, endY);
        line.setRadius(0.25f);
        line.setColor(Color.WHITE);
        line.setWidth(0.1f);
        line.setLayer(-2);
    }

}
