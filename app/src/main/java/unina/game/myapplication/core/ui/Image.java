package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.Camera;

public final class Image extends Element {

    private static final Pool<Image> pool = new Pool<>(Image::new, 8);

    public static Image build() {
        return pool.get();
    }

    public static Image build(Pixmap image) {
        Image i = pool.get();
        i.setImage(image);
        return i;
    }

    private int srcX, srcY;
    private int srcWidth, srcHeight;
    private Pixmap image;

    public void setImage(Pixmap image) {
        this.image = image;
        setSrcPosition(0, 0);
        setSrcSize(image.getWidth(), image.getHeight());
    }

    public void setSrcPosition(int x, int y) {
        this.srcX = x;
        this.srcY = y;
    }

    public void setSrcSize(int width, int height) {
        this.srcWidth = width;
        this.srcHeight = height;
    }

    @Override
    protected void draw(Graphics graphics, Camera camera) {
        if (image == null)
            graphics.drawRect(getMinX(), getMinY(), getWidth(), getHeight(), Color.WHITE);
        else
            graphics.drawPixmap(image, getMinX(), getMinY(), getWidth(), getHeight(), srcX, srcY, srcWidth, srcHeight);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        image = null;
        pool.free(this);
    }

}
