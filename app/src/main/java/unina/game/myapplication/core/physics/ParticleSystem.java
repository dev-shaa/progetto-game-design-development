package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.google.fpl.liquidfun.ParticleFlag;
import com.google.fpl.liquidfun.ParticleGroup;
import com.google.fpl.liquidfun.ParticleGroupDef;
import com.google.fpl.liquidfun.ParticleGroupFlag;
import com.google.fpl.liquidfun.ParticleSystemDef;
import com.google.fpl.liquidfun.PolygonShape;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.PhysicsComponent;

public final class ParticleSystem extends PhysicsComponent {

    public static final int FLAG_GROUP_SOLID = ParticleGroupFlag.solidParticleGroup;
    public static final int FLAG_GROUP_RIGID = ParticleGroupFlag.rigidParticleGroup;
    public static final int FLAG_PARTICLE_WATER = ParticleFlag.waterParticle;
    public static final int FLAG_PARTICLE_WALL = ParticleFlag.wallParticle;
    public static final int FLAG_PARTICLE_SPRING = ParticleFlag.springParticle;
    public static final int FLAG_PARTICLE_ELASTIC = ParticleFlag.elasticParticle;
    public static final int FLAG_PARTICLE_VISCOUS = ParticleFlag.viscousParticle;

    private com.google.fpl.liquidfun.ParticleSystem particleSystem;
    private float radius = 0.2f;
    private float width = 1, height = 1;
    private int groupFlags = 0;
    private int flags = 0;

    @Override
    public void onInitialize() {
        super.onInitialize();

        ParticleSystemDef def = new ParticleSystemDef();
        def.setRadius(radius / 2);

        particleSystem = world.createParticleSystem(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, height / 2);

        ParticleGroupDef groupDef = new ParticleGroupDef();
        groupDef.setShape(box);
        groupDef.setPosition(getOwner().x, getOwner().y);
        groupDef.setGroupFlags(groupFlags);
        groupDef.setFlags(flags);

        ParticleGroup pg = particleSystem.createParticleGroup(groupDef);

        box.delete();
        groupDef.delete();
        def.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        if (particleSystem != null) {
            world.destroyParticleSystem(particleSystem);
            particleSystem = null;
        }

        world = null;
        radius = 0.5f;
        width = height = 1;
        groupFlags = FLAG_GROUP_SOLID;
        flags = FLAG_PARTICLE_WATER;
    }

    @Override
    public void onDrawGizmos(Graphics graphics) {
        super.onDrawGizmos(graphics);

        int count = particleSystem.getParticleCount();

//        particlePositionsBuffer.clear();
//        particleSystem.copyPositionBuffer(0, count, particlePositionsBuffer);
//        particlePositionsBuffer.flip();

        float r = Camera.getInstance().worldToScreenSizeX(radius);

        for (int i = 0; i < count; i++) {
            float x = particleSystem.getParticlePositionX(i);
            float y = particleSystem.getParticlePositionY(i);

            x = Camera.getInstance().worldToScreenX(x);
            y = Camera.getInstance().worldToScreenY(y);
            graphics.drawWireCircle(x, y, r, Color.BLUE);
        }
    }

    public void setRadius(float radius) {
        this.radius = radius;

        if (particleSystem != null)
            particleSystem.setRadius(radius / 2);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setGroupFlags(int groupFlags) {
        this.groupFlags = groupFlags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

}
