package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Pool;

import java.util.ArrayList;

import unina.game.myapplication.core.InputComponent;

public final class Canvas extends InputComponent {

    private static final Pool<Canvas> pool = new Pool<>(Canvas::new, 2);

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

        for (int i = elements.size() - 1; i >= 0; i--) {
            Element element = elements.get(i);

            // TODO: handle other states: pointer down, pointer up, pointer enter, pointer leave...
            if (wasTouchDown && !isTouchDown && element.isPointerOver(touchX, touchY)) {
                element.onClick();
                break;
            }
        }

        wasTouchDown = isTouchDown;
    }

    public void addElement(Element element) {
        if (elements.add(element))
            element.onAdd();
    }

    public void removeElement(Element element) {
        if (elements.remove(element))
            element.onRemove();
    }

    public Iterable<Element> getElements() {
        return elements;
    }

}
