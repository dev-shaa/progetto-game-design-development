package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.RenderComponent;

public final class CanvasRenderer extends RenderComponent {

    private static final Pool<CanvasRenderer> pool = new Pool<>(CanvasRenderer::new, 2);

    public static CanvasRenderer build(Canvas canvas) {
        CanvasRenderer renderer = pool.get();
        renderer.canvas = canvas;
        renderer.setLayer(Short.MAX_VALUE); // By default, the canvas should be rendered on top of everything else
        return renderer;
    }

    private Canvas canvas;

    private CanvasRenderer() {

    }

    @Override
    public void onRemove() {
        super.onRemove();
        pool.free(this);
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        for (Element element : canvas.getElements()) {
            if (element.isEnabled())
                element.draw(deltaTime, graphics);
        }
    }

}
