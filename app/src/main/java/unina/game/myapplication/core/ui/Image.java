package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Pool;

public class Image extends PixmapElement {

    private static final Pool<Image> pool = new Pool<>(Image::new, 8);

    public static Image build() {
        return pool.get();
    }

    private Image() {

    }

    @Override
    public void onRemove() {
        super.onRemove();
        pool.free(this);
    }

}
