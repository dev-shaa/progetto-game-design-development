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
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.CircleRenderer;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.Button;

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
        elementsImage = game.getGraphics().newPixmap("graphics/elements-light.png", Graphics.PixmapFormat.ARGB8888);

        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        buttonsAppearSound = game.getAudio().newSound("sounds/kenney-ui-sounds/switch4.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");

        // Background
        GameObject backgroundGO = createGameObject();

        SpriteRenderer backgroundRenderer = backgroundGO.addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(10, 10 / backgroundImage.getAspectRatio());
        backgroundRenderer.setLayer(-10);

        // Rectangle to hide the character when it enters the door
        GameObject rectRendererGO = createGameObject(3.4f, -1.25f);

        RectRenderer rectRenderer = rectRendererGO.addComponent(RectRenderer.class);
        rectRenderer.setPivot(0, 1);
        rectRenderer.setSize(1, 2);
        rectRenderer.setColor(PALETTE_BACKGROUND);
        rectRenderer.setLayer(10);

        // Transition
        GameObject fade = createGameObject();
        RectRenderer fullScreenRenderer = fade.addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Bridge
        GameObject bridge = createGameObject(-2.5f, -1.3f);

        SpriteRenderer bridgeRenderComponent = bridge.addComponent(SpriteRenderer.class);
        bridgeRenderComponent.setImage(elementsImage);
        bridgeRenderComponent.setSrcPosition(128, 48);
        bridgeRenderComponent.setSrcSize(128, 32);
        bridgeRenderComponent.setSize(2.2f, 2f / 4f);
        bridgeRenderComponent.setPivot(0.1f, 0f);
        bridgeRenderComponent.setLayer(-3);


        // Character
        GameObject character = createGameObject(-43f, -1.25f);
        SpriteRenderer characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(elementsImage);
        characterRenderer.setSize(1, 1);
        characterRenderer.setSrcPosition(128, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1f);
        characterRenderer.setLayer(8);

        // Button
        CircleRenderer circleRenderer = createGameObject(-1, 5).addComponent(CircleRenderer.class);
        circleRenderer.setRadius(0.75f);
        circleRenderer.setColor(PALETTE_PRIMARY);

        GameObject button = createGameObject(-1f, 5f);

        SpriteRenderer buttonRenderComponent = button.addComponent(SpriteRenderer.class);
        buttonRenderComponent.setImage(elementsImage);
        buttonRenderComponent.setSize(2.5f, 2.5f);
        buttonRenderComponent.setSrcPosition(0, 0);
        buttonRenderComponent.setSrcSize(128, 128);
        buttonRenderComponent.setLayer(3);

        Button buttonInputComponent = button.addComponent(Button.class);
        buttonInputComponent.setSize(3, 3);
        buttonInputComponent.setInteractable(false);

        GameObject lineRendererGO = createGameObject();
        DottedLineRenderer lineRenderer = lineRendererGO.addComponent(DottedLineRenderer.class);
        lineRenderer.setPointA(button.x, button.y - 1.25f);
        lineRenderer.setPointB(-1f, bridge.y);
        lineRenderer.setCount(16);
        lineRenderer.setRadius(0.1f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(-4);

        // Level selection button
        GameObject menuButtonGO = createGameObject(-4, Camera.getInstance().getSizeY() / 2 + 4);

        RectRenderer menuButtonRenderer = menuButtonGO.addComponent(RectRenderer.class);
        menuButtonRenderer.setSize(2f, 2);
        menuButtonRenderer.setColor(Color.GREEN);
        menuButtonRenderer.setLayer(10);
        menuButtonRenderer.setPivot(0f, 0f);

        Button menuButton = menuButtonGO.addComponent(Button.class);
        menuButton.setSize(3, 3);
        menuButton.setInteractable(false);
        menuButton.setOnClick(() -> {
            menuButton.setInteractable(false);
            buttonInputComponent.setInteractable(false);

            animator.clear();
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(Level1.class));
            animator.start();
        });


        buttonInputComponent.setOnClick(() -> {
            buttonSound.play(1);

            lineRenderer.setColor(PALETTE_PRIMARY);
            circleRenderer.setColor(Color.GREY);

            menuButton.setInteractable(false);
            buttonInputComponent.setInteractable(false);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge, -1, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 4f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(Level2.class));
            animator.start();
        });

        GameObject animatorGO = createGameObject();
        animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(ParallelAnimation.build(
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

        animator = null;
    }

}
