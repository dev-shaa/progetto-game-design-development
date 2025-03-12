package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Font;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Localization;
import com.badlogic.androidgames.framework.Pixmap;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.ParticleSystem;
import unina.game.myapplication.core.physics.PrismaticJoint;
import unina.game.myapplication.core.physics.RevoluteJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.physics.TriangleCollider;
import unina.game.myapplication.core.rendering.ParticleSystemRenderer;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.core.rendering.TextRenderer;
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

        Localization localization = game.getLocalization();
        Font playButtonFont = game.getGraphics().newFont("fonts/RG2014D.ttf");

//        TextRenderer playButtonRenderer = createGameObject(0, 0).addComponent(TextRenderer.class);
//        playButtonRenderer.setHorizontalAlign(Graphics.Align.CENTER);
//        playButtonRenderer.setVerticalAlign(Graphics.Align.CENTER);
//        playButtonRenderer.setColor(0xffECECE7);
//        playButtonRenderer.setSize(24);
//        playButtonRenderer.setFont(playButtonFont);
//        playButtonRenderer.setText(localization.getString(R.string.menu_button_play));
//        RectRenderer renderer = createGameObject(0, 0).addComponent(RectRenderer.class);
//        renderer.setSize(1, 1);
//        renderer.setColor(Color.RED);
//
//
//        RectRenderer bar = createGameObject(5, 2).addComponent(RectRenderer.class);
//        bar.setSize(1, 1);
//        bar.setColor(Color.BLUE);

//        TextRenderer foo = createGameObject(10, 0).addComponent(TextRenderer.class);
//        foo.setHorizontalAlign(Graphics.Align.CENTER);
//        foo.setVerticalAlign(Graphics.Align.CENTER);
//        foo.setColor(0xffECECE7);
//        foo.setSize(24);
//        foo.setFont(playButtonFont);
//        foo.setText(localization.getString(R.string.menu_button_play));

//        AnimationSequence sequence = createGameObject().addComponent(AnimationSequence.class);
//        sequence.add(WaitAnimation.build(3));
//        sequence.add(MoveToAnimation.build(Camera.getInstance().getOwner(), 10, 2, 1, EaseFunction.CUBIC_IN_OUT));
//        sequence.add(MoveToAnimation.build(Camera.getInstance().getOwner(), 0, 0, 1, EaseFunction.CUBIC_IN_OUT));
//        sequence.start();

//        Camera.getInstance().setSize(20);
//
//        for (int i = 0; i < 10; i++) {
//            createGameObject(i - 5, 0).addComponent(TestSquare.class);
//        }

//        Pixmap pixmap = getImage("graphics/elements-light.png");

//        GameObject go = createGameObject(0, 0, 30);
//
//        SpriteRenderer renderer = go.addComponent(SpriteRenderer.class);
//        renderer.setImage(pixmap);
//        renderer.setSrcPosition(0, 0);
//        renderer.setSrcSize(128, 128);
//        renderer.setSize(2, 2);
//        renderer.setPivot(0.5f, 0.5f);
//
//        RigidBody rigidBody = go.addComponent(RigidBody.class);
//        rigidBody.addCollider(BoxCollider.build(2, 2));


//        ParticleSystem foo = createGameObject(0, 0).addComponent(ParticleSystem.class);
//        foo.setRadius(0.1f);
//        foo.setSize(8, 1);
//        foo.setGroupFlags(ParticleSystem.FLAG_GROUP_SOLID);
//        foo.setFlags(ParticleSystem.FLAG_PARTICLE_WATER);

//        GameObject anchor = createGameObject(0, 0);
//        RigidBody anchorRigidBody = anchor.addComponent(RigidBody.class);
//        anchorRigidBody.setType(RigidBody.Type.STATIC);
//
//        GameObject platform = createGameObject(0, 0, 0);
//
//        RectRenderer renderer = platform.addComponent(RectRenderer.class);
//        renderer.setSize(8, 0.25f);
//
//        BoxCollider b = BoxCollider.build(0.25f, 0.25f, 0.1f, 0, 0, false);
//        b.setCenter(-4f, 0.25f);
//
//        RigidBody rigidBody = platform.addComponent(RigidBody.class);
//        rigidBody.setType(RigidBody.Type.DYNAMIC);
//        rigidBody.addCollider(BoxCollider.build(8, 0.25f));
//        rigidBody.addJoint(RevoluteJoint.build(anchorRigidBody));
//

        ParticleSystem system = createParticles(0.25f, Color.BLUE);
        system.addParticlesGroup(-2, 5, 3, 15, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);
        system.addParticlesGroup(2, 0, 2, 2, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);
//        createParticles(-2, -2, 3, 3, 0.25f, Color.BLUE);
//        createParticles(3, -2, 3, 5, 0.25f, Color.RED);

        RigidBody anchor = createGameObject(0, -6).addComponent(RigidBody.class);
        anchor.setType(RigidBody.Type.STATIC);

        GameObject go = createGameObject(0, -6);

        PrismaticJoint joint = PrismaticJoint.build(anchor, 0, -1);
        joint.setEnableLimit(true);
        joint.setLowerLimit(10);
        joint.setUpperLimit(20);

        CursorJoint cursorJoint = CursorJoint.build();

        RigidBody rigidBody = go.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(0.5f, 15));
        rigidBody.addJoint(joint);
        rigidBody.addJoint(cursorJoint);

        CursorJointInput cursorJointInput = go.addComponent(CursorJointInput.class);
        cursorJointInput.setJoint(cursorJoint);
        cursorJointInput.setMaxForce(10000);
        cursorJointInput.setSnap(false);
        cursorJointInput.setSize(1, 15);

        createWall(-5f, 0, 0, 0.5f, 40);
        createWall(5f, 0, 0, 0.5f, 40);
        createWall(0, -5f, 0, 10, 0.5f);
//        createWall(0, 0, 0, 1, 40);

        createBox(3, 5, 1, 1, 1f);
//        createBall(1f, 5, 1f, 1f);
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
