package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Pool;

import java.util.ArrayList;

import unina.game.myapplication.core.InputComponent;

public final class Canvas extends InputComponent {

    private static final Pool<Canvas> pool = new Pool<>(Canvas::new, 1);

    /**
     * Creates a new canvas to display the UI.
     *
     * @return new canvas
     */
    public static Canvas build() {
        return pool.get();
    }

    private final ArrayList<Element> elements = new ArrayList<>();
    private boolean wasTouchDown = false;

    private Canvas() {

    }

    @Override
    public void onRemove() {
        super.onRemove();

        for (Element element : elements)
            element.onRemove();

        elements.clear();
        pool.free(this);
    }

    @Override
    public void process(Input input) {
        boolean isTouchDown = input.isTouchDown(0);
        float touchX = input.getTouchX(0);
        float touchY = input.getTouchY(0);

        // TODO: for now it only detects clicks
        if (!wasTouchDown && isTouchDown) {

            for (int i = elements.size() - 1; i >= 0; i--) {
                Element element = elements.get(i);

                if (element.isEnabled() && element.isPointerOver(touchX, touchY)) {
                    element.onClick();
                    break;
                }
            }

        }

        wasTouchDown = isTouchDown;
    }

    /**
     * Adds an element to the canvas.
     *
     * @param element element to add
     */
    public void addElement(Element element) {
        if (elements.add(element))
            element.onAdd();
    }

    /**
     * Removes an element from the canvas.
     *
     * @param element element to remove
     */
    public void removeElement(Element element) {
        if (elements.remove(element))
            element.onRemove();
    }

    /**
     * Returns all elements of the canvas.
     *
     * @return elements of the canvas
     */
    public Iterable<Element> getElements() {
        return elements;
    }

}
