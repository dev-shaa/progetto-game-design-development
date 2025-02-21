package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class SpriteRenderer extends RenderComponent {

    public int srcX, srcY;
    public int srcWidth, srcHeight;
    public float width, height;
    public int color;

    private Pixmap image;
    private float pivotX, pivotY;
    private Camera camera;

    public SpriteRenderer() {
        this.color = Color.WHITE;
        this.width = this.height = 1;
        this.pivotX = this.pivotY = 0.5f;
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

    /**
     * Sets the drawing origin of the sprite, where (0, 0) means the origin is the top-left corner and (1, 1) the bottom-right corner.
     *
     * @param x
     * @param y
     */
    public void setPivot(float x, float y) {
        this.pivotX = x;
        this.pivotY = y;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        image = null;
        camera = null;
        color = Color.WHITE;
        this.pivotX = this.pivotY = 0.5f;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        if (image == null)
            return;

        float screenWidth = camera.worldToScreenSizeX(width);
        float screenHeight = camera.worldToScreenSizeY(height);
        float screenX = camera.worldToScreenX(getOwner().x) - pivotX * screenWidth;
        float screenY = camera.worldToScreenY(getOwner().y) - pivotY * screenHeight;

        graphics.drawPixmap(image, screenX, screenY, getOwner().angle, screenWidth, screenHeight, srcX, srcY, srcWidth, srcHeight, color);
    }

}
