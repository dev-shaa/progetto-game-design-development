package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import unina.game.myapplication.core.RenderComponent;
import unina.game.myapplication.core.physics.ParticleSystem;

public class ParticleSystemRenderer extends RenderComponent {

    private static final int MAX_PARTICLES = 10240;
    private static final int PARTICLES_SIZE = 8;

    private ParticleSystem particleSystem;
    private int color = Color.WHITE;
    private ByteBuffer buffer;

    @Override
    public void onInitialize() {
        super.onInitialize();
        buffer = ByteBuffer.allocateDirect(MAX_PARTICLES * PARTICLES_SIZE);
        buffer.order(ByteOrder.nativeOrder());
    }

    @Override
    public void onRemove() {
        super.onRemove();

        buffer.clear();
        buffer = null;
        particleSystem = null;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        int count = Math.min(particleSystem.getParticlesCount(), MAX_PARTICLES);
        float radius = particleSystem.getRadius();

        buffer.clear();
        particleSystem.copyPositionBuffer(0, count, buffer);
        buffer.rewind();

        for (int i = 0; i < count; i++) {
            float x = buffer.getFloat();
            float y = -buffer.getFloat();
            graphics.drawCircle(x, y, radius, color);
        }
    }

    public void setParticleSystem(ParticleSystem particleSystem) {
        this.particleSystem = particleSystem;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
