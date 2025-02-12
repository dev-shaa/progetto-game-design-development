package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.CompositeAnimation;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.Sequence;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.common.CircleRenderer;
import unina.game.myapplication.logic.common.ColorAnimation;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.menu.MainMenu;

public class Level1 extends Scene {

    private static final int PALETTE_BACKGROUND = 0xff005387;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    private Pixmap backgroundImage;
    private Pixmap elementsImage;

    private Sound buttonSound;
    private Sound buttonsAppearSound;
    private Sound movingPlatformSound;
    private Sound winSound;

    private AnimationSequence animator;

    public Level1(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(10);

        backgroundImage = game.getGraphics().newPixmap("graphics/background-level1.png", Graphics.PixmapFormat.ARGB8888);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-white.png", Graphics.PixmapFormat.ARGB8888);

        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        buttonsAppearSound = game.getAudio().newSound("sounds/kenney-ui-sounds/switch4.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");

        // Background
        SpriteRenderer backgroundRenderer = SpriteRenderer.build();
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(10, 10 / backgroundImage.getAspectRatio());
        backgroundRenderer.setLayer(-10);
        createGameObject(backgroundRenderer);

        // Rectangle to hide the character when it enters the door
        RectRenderer rectRenderer = RectRenderer.build();
        rectRenderer.setPivot(0, 1);
        rectRenderer.setSize(1, 2);
        rectRenderer.setColor(PALETTE_BACKGROUND);
        rectRenderer.setLayer(10);
        createGameObject(3.4f, -1.25f, rectRenderer);

        // Transition
        RectRenderer fullScreenRenderer = RectRenderer.build();
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);
        GameObject fade = createGameObject(fullScreenRenderer);

        // Bridge
        SpriteRenderer bridgeRenderComponent = SpriteRenderer.build();
        bridgeRenderComponent.setImage(elementsImage);
        bridgeRenderComponent.setSrcPosition(128, 48);
        bridgeRenderComponent.setSrcSize(128, 32);
        bridgeRenderComponent.setSize(2.2f, 2f / 4f);
        bridgeRenderComponent.setPivot(0.1f, 0f);
        bridgeRenderComponent.setLayer(-3);
        GameObject bridge = createGameObject(-2.5f, -1.3f, bridgeRenderComponent);

        // Character
        SpriteRenderer characterRenderer = SpriteRenderer.build();
        characterRenderer.setImage(elementsImage);
        characterRenderer.setSize(1, 1);
        characterRenderer.setSrcPosition(128, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1f);
        characterRenderer.setLayer(8);
        GameObject character = createGameObject(-43f, -1.25f, characterRenderer);

        RectRenderer menuButtonRenderer = RectRenderer.build();
        menuButtonRenderer.setSize(2f, 2);
        menuButtonRenderer.setColor(Color.GREEN);
        menuButtonRenderer.setLayer(10);
        menuButtonRenderer.setPivot(0f, 0f);


        // Button
        DottedLineRenderer lineRenderer = DottedLineRenderer.build();
        lineRenderer.setCount(16);
        lineRenderer.setRadius(0.1f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(-4);
        createGameObject(lineRenderer);

        CircleRenderer circleRenderer = CircleRenderer.build();
        circleRenderer.setRadius(0.75f);
        circleRenderer.setColor(PALETTE_PRIMARY);
        createGameObject(-1, 5, circleRenderer);

        SpriteRenderer buttonRenderComponent = SpriteRenderer.build();
        buttonRenderComponent.setImage(elementsImage);
        buttonRenderComponent.setSize(2.5f, 2.5f);
        buttonRenderComponent.setSrcPosition(0, 0);
        buttonRenderComponent.setSrcSize(128, 128);
        buttonRenderComponent.setLayer(3);

        Button buttonInputComponent = Button.build();
        buttonInputComponent.setSize(3, 3);

        Button menuButton = Button.build();
        menuButton.setSize(3, 3);
        menuButton.setInteractable(false);
        menuButton.setOnClick(() -> {
            menuButton.setInteractable(false);
            buttonInputComponent.setInteractable(false);
            
            animator.clear();
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
            animator.start();
        });

        GameObject menuButtonGO = createGameObject(-4, Camera.getInstance().getSizeY() / 2 + 4, menuButtonRenderer, menuButton);

        buttonInputComponent.setOnClick(() -> {


//            RectRenderer retryButtonRenderer = RectRenderer.build();
//            retryButtonRenderer.setSize(1.5f, 2f);
//            retryButtonRenderer.setPivot(1, 0f);
//            retryButtonRenderer.setLayer(Integer.MAX_VALUE);
//
//            Button retryButton = Button.build();
//            retryButton.setSize(1.5f, 2);
//            retryButton.setOnClick(() -> loadScene(Level1.class));
//            GameObject retryButtonGO = createGameObject(2, Camera.getInstance().getSizeY() / 2 + 3, retryButtonRenderer, retryButton);

//            RectRenderer nextLevelButtonRenderer = RectRenderer.build();
//            nextLevelButtonRenderer.setSize(1.5f, 2f);
//            nextLevelButtonRenderer.setPivot(1, 0f);
//            nextLevelButtonRenderer.setLayer(Integer.MAX_VALUE);
//
//            Button nextLevelButton = Button.build();
//            nextLevelButton.setSize(1.5f, 2);
//            nextLevelButton.setOnClick(() -> {
//                loadScene(Level2.class);
//            });

//            GameObject nextLevelButtonGO = createGameObject(4, Camera.getInstance().getSizeY() / 2 + 3, nextLevelButtonRenderer, nextLevelButton);

//            Sequence sequence = Sequence.build();
//            sequence.addAnimation(WaitAnimation.build(0.05f));
//            sequence.addAnimation(MoveToAnimation.build(nextLevelButtonGO, nextLevelButtonGO.x, Camera.getInstance().getSizeY() / 2, 0.1f));

            buttonSound.play(1);

            lineRenderer.setColor(PALETTE_PRIMARY);
            circleRenderer.setColor(Color.GREY);

            menuButton.setInteractable(false);
            buttonInputComponent.setInteractable(false);

            animator.clear();
//            animator.add(CompositeAnimation.build(
//                    ColorAnimation.build(circleRenderer::setColor, PALETTE_PRIMARY, Color.GREY, 0.2f),
//                    ColorAnimation.build(lineRenderer::setColor, Color.GREY, PALETTE_PRIMARY, 0.2f)
//            ));
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge, -1, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 4f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
//            animator.add(WaitAnimation.build(0.3f));
//            animator.add(CompositeAnimation.build(
//                    MoveToAnimation.build(retryButtonGO, retryButtonGO.x, Camera.getInstance().getSizeY() / 2, 0.1f),
//                    sequence
//            ));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> {
                loadScene(Level2.class);
            });
            animator.start();
        });
        buttonInputComponent.setInteractable(false);

        GameObject button = createGameObject(-1f, 5f, buttonRenderComponent, buttonInputComponent);

        lineRenderer.setPointA(button.x, button.y - 1.25f);
        lineRenderer.setPointB(-1f, bridge.y);

        animator = AnimationSequence.build();
        animator.add(CompositeAnimation.build(
                        FadeAnimation.build(fullScreenRenderer, Color.BLACK, Color.TRANSPARENT, 0.4f),
                        MoveToAnimation.build(character, -2.5f, character.y, 0.25f, EaseFunction.CUBIC_IN_OUT)
                ),
                () -> characterRenderer.setSrcPosition(0, 128));
        animator.add(MoveToAnimation.build(menuButtonGO, menuButtonGO.x, Camera.getInstance().getSizeY() / 2, 0.25f, EaseFunction.CUBIC_IN_OUT),
                () -> {
                    buttonInputComponent.interactable = true;
                    menuButton.interactable = true;
                });
        animator.start();
        createGameObject(animator);
    }

    @Override
    public void dispose() {
        super.dispose();

        backgroundImage.dispose();
        elementsImage.dispose();

        buttonSound.dispose();
        buttonsAppearSound.dispose();
        movingPlatformSound.dispose();
        winSound.dispose();
    }

    private float easeOutBack(float x) {
        float c1 = 1.70158f;
        float c3 = c1 + 1;

        return (float) (1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2));

    }

}
