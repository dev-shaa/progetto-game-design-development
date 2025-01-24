package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Pool;

public class SpriteRenderer extends RenderComponent {

    private static final Pool<SpriteRenderer> pool = new Pool<>(SpriteRenderer::new, 32);

    public static SpriteRenderer build(Pixmap image, float width, float height) {
        SpriteRenderer renderer = pool.get();
        renderer.setImage(image);
        renderer.setSize(width, height);
        return renderer;
    }

    public int srcX, srcY;
    public int srcWidth, srcHeight;
    public float width, height;
    public int color = Color.WHITE;
    private Pixmap image;

    private SpriteRenderer() {

    }

    public void setImage(Pixmap image) {
        this.image = image;

        if (image != null) {
            setSrcPosition(0, 0);
            setSrcSize(image.getWidth(), image.getHeight());
        }
    }

    public void setSrcPosition(int x, int y) {
        this.srcX = x;
        this.srcY = y;
    }

    public void setSrcSize(int width, int height) {
        this.srcWidth = width;
        this.srcHeight = height;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        image = null;
        pool.free(this);
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        if (image == null)
            return;

        Camera camera = Camera.getInstance();

        float screenX = camera.worldToScreenX(getOwner().x - width / 2);
        float screenY = camera.worldToScreenY(getOwner().y + height / 2);
        float screenWidth = camera.worldToScreenSizeX(width);
        float screenHeight = camera.worldToScreenSizeY(height);

        graphics.drawPixmap(image, screenX, screenY, getOwner().angle, screenWidth, screenHeight, srcX, srcY, srcWidth, srcHeight, color);
    }

}
