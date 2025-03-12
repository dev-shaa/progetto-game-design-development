package unina.game.myapplication.core.physics;

import android.util.ArraySet;

import com.google.fpl.liquidfun.ParticleFlag;
import com.google.fpl.liquidfun.ParticleGroup;
import com.google.fpl.liquidfun.ParticleGroupDef;
import com.google.fpl.liquidfun.ParticleGroupFlag;
import com.google.fpl.liquidfun.ParticleSystemDef;
import com.google.fpl.liquidfun.PolygonShape;

import java.nio.ByteBuffer;
import java.util.LinkedList;

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
    private final LinkedList<ParticleGroupDef> groupDefs = new LinkedList<>();
    private final ArraySet<ParticleGroup> groups = new ArraySet<>(3);

    @Override
    public void onInitialize() {
        super.onInitialize();

        ParticleSystemDef def = new ParticleSystemDef();
        def.setRadius(radius / 2);

        particleSystem = world.createParticleSystem(def);

        while (!groupDefs.isEmpty()) {
            ParticleGroupDef groupDef = groupDefs.poll();
            ParticleGroup group = particleSystem.createParticleGroup(groupDef);
            groups.add(group);
            groupDef.delete();
        }

        def.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        for (ParticleGroupDef def : groupDefs)
            def.delete();

        for (ParticleGroup group : groups)
            group.delete();

        if (particleSystem != null) {
            world.destroyParticleSystem(particleSystem);
            particleSystem = null;
        }

        world = null;
        radius = 0.5f;

        groupDefs.clear();
        groups.clear();
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;

        if (particleSystem != null)
            particleSystem.setRadius(radius / 2);
    }

    public void copyPositionBuffer(int startIndex, int particlesCount, ByteBuffer buffer) {
        if (particleSystem != null)
            particleSystem.copyPositionBuffer(startIndex, particlesCount, buffer);
    }

    public int getParticlesCount() {
        return particleSystem == null ? 0 : particleSystem.getParticleCount();
    }

    public void addParticlesGroup(float x, float y, float width, float height, int groupFlags, int flags) {
        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, height / 2);

        ParticleGroupDef groupDef = new ParticleGroupDef();
        groupDef.setShape(box);
        groupDef.setPosition(x, y);
        groupDef.setGroupFlags(groupFlags);
        groupDef.setFlags(flags);

        if (particleSystem == null) {
            groupDefs.add(groupDef);
        } else {
            ParticleGroup group = particleSystem.createParticleGroup(groupDef);
            groups.add(group);
            
            box.delete();
            groupDef.delete();
        }
    }

}
