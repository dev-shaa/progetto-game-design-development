package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.logic.common.animations.MoveRigidBodyTo;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.logic.common.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.ParticleSystem;
import unina.game.myapplication.core.physics.PrismaticJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.ParticleSystemRenderer;
import unina.game.myapplication.logic.common.inputs.CursorJointInput;
import unina.game.myapplication.core.rendering.CircleRenderer;
import unina.game.myapplication.logic.common.renderers.DraggablePlatformLineRenderer;
import unina.game.myapplication.core.rendering.RectRenderer;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(20);

        createWall(0, -10, 0, 40, 1);
        createWall(-10, 0, 0, 1, 100);
        createWall(10, 0, 0, 1, 100);

//        createWall(-5, -3, -30, 11, 1);
//        createWall(-1, -8, 0, 1, 4);
//        createWall(-5, 12, 0, 0.4f, 30);

        createWall(-3, -9, 0, 0.5f, 2);
        createWall(3, -9, 0, 0.5f, 2);
        createWall(0, -8, 0, 6, 0.5f);
        createWall(0, -6, 0, 6, 0.5f);
//        createWall(3, 9, 0, 0.4f, 30f);

//        createWall(0, 22, 0, 40, 0.5f);

        GameObject go2 = createGameObject(3, 7);
//
        RigidBody rigidBody2 = go2.addComponent(RigidBody.class);
        rigidBody2.setType(RigidBody.Type.KINEMATIC);
        rigidBody2.addCollider(BoxCollider.build(0.4f, 30f, 1, 0, 0, false));

        ParticleSystem particleSystem = createParticles(0.25f, Color.BLUE);
        particleSystem.addParticlesGroup(-6, -3, 5, 6, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);
        particleSystem.addParticlesGroup(6, 6, 5, 25, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);

        GameObject go = createGameObject(-3, 7);
//
        RigidBody rigidBody = go.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.KINEMATIC);
        rigidBody.addCollider(BoxCollider.build(0.4f, 30f, 1, 0, 0, false));

        RectRenderer renderer = go.addComponent(RectRenderer.class);
        renderer.setSize(0.4f, 30f);
        renderer.setColor(Color.WHITE);

        RigidBody f = createGameObject(6f, 90, 0).addComponent(RigidBody.class);
        f.setType(RigidBody.Type.DYNAMIC);
        f.addCollider(BoxCollider.build(5.5f, 5, 100f, 0, 1, false));

//
//        GameObject ballGO = createGameObject(-5, -10);
//
//        CircleRenderer ballRenderer = ballGO.addComponent(CircleRenderer.class);
//        ballRenderer.setRadius(1);
//
//        RigidBody ballRigidBody = ballGO.addComponent(RigidBody.class);
//        ballRigidBody.setType(RigidBody.Type.DYNAMIC);
//        ballRigidBody.setLinearDamping(0.2f);
//        ballRigidBody.addCollider(CircleCollider.build(0.5f, 1, 0, 0, false));
//        ballRigidBody.addJoint(RopeJoint.build(rigidBody, 5));
//
        AnimationSequence sequence = createGameObject().addComponent(AnimationSequence.class);
        sequence.add(WaitAnimation.build(1));
        sequence.add(ParallelAnimation.build(
                MoveRigidBodyTo.build(rigidBody, rigidBody.getPositionX(), 9, 1f, EaseFunction.LINEAR),
                MoveRigidBodyTo.build(rigidBody2, rigidBody2.getPositionX(), 9, 1f, EaseFunction.LINEAR)
        ));
        sequence.start();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private ParticleSystem createParticles(float radius, int color) {
        GameObject particlesGO = createGameObject();

        ParticleSystem particles = particlesGO.addComponent(ParticleSystem.class);
        particles.setRadius(radius);

        ParticleSystemRenderer particlesRenderer = particlesGO.addComponent(ParticleSystemRenderer.class);
        particlesRenderer.setParticleSystem(particles);
        particlesRenderer.setColor(color);

        return particles;
    }

    private void createBox(float x, float y, float width, float height, float density) {
        GameObject platform = createGameObject(x, y);

        RectRenderer renderer = platform.addComponent(RectRenderer.class);
        renderer.setSize(width, height);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(width, height, density, 0.2f, 1, false));
    }

    private RigidBody createBall(float x, float y, float radius, float density) {
        GameObject platform = createGameObject(x, y);

        CircleRenderer renderer = platform.addComponent(CircleRenderer.class);
        renderer.setRadius(radius);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(CircleCollider.build(radius, density, 0.2f, 0, false));

        return rigidBody;
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
