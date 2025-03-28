package unina.game.myapplication.logic.scenes;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.logic.common.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.logic.common.animations.WaitAnimation;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.Assets;
import unina.game.myapplication.core.rendering.CircleRenderer;
import unina.game.myapplication.logic.common.animations.ColorAnimation;
import unina.game.myapplication.core.rendering.RectRenderer;
import unina.game.myapplication.logic.common.renderers.DottedLineRenderer;
import unina.game.myapplication.logic.common.inputs.Button;

public class Level1 extends Level {

    private static final int PALETTE_BACKGROUND = 0xff005387;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    private AnimationSequence animator;
    private SpriteRenderer promptRenderer;

    public Level1(Game game) {
        super(game);
    }

    @Override
    protected void onInitialize() {
        Camera.getInstance().setSize(10);

        setClearColor(PALETTE_BACKGROUND);

        Pixmap spriteSheet = getImage(Assets.GRAPHICS_GAME_SPRITES_LIGHT);
        Pixmap uiSpriteSheet = getImage(Assets.GRAPHICS_UI_SPRITES);

        Sound buttonSound = getSound(Assets.SOUND_GAME_BUTTON_CLICK);
        Sound winSound = getSound(Assets.SOUND_GAME_WIN);
        Sound movingPlatformSound = getSound(Assets.SOUND_GAME_PLATFORM_MOVE);

        // Background
        SpriteRenderer backgroundRenderer = createGameObject().addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(getImage(Assets.GRAPHICS_BACKGROUND_LEVEL_1));
        backgroundRenderer.setSize(Camera.getInstance().getSizeX(), Camera.getInstance().getSizeY());
        backgroundRenderer.setLayer(16);

        // Transition Panel
        RectRenderer fullScreenRenderer = createGameObject().addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Prompt Renderer
        promptRenderer = createGameObject(-1, 2f).addComponent(SpriteRenderer.class);
        promptRenderer.setImage(uiSpriteSheet);
        promptRenderer.setSize(1.75f, 1.75f);
        promptRenderer.setSrcSize(128, 128);
        promptRenderer.setSrcPosition(0, 256);
        promptRenderer.setPivot(0.4f, 0);
        promptRenderer.setTint(Color.TRANSPARENT);
        promptRenderer.setLayer(8);

        // Bridge
        GameObject bridge = createGameObject(-2.5f, -1.35f);

        SpriteRenderer bridgeRenderComponent = bridge.addComponent(SpriteRenderer.class);
        bridgeRenderComponent.setImage(spriteSheet);
        bridgeRenderComponent.setSrcPosition(128, 48);
        bridgeRenderComponent.setSrcSize(128, 32);
        bridgeRenderComponent.setSize(2.2f, 2f / 4f);
        bridgeRenderComponent.setPivot(0.1f, 0f);
        bridgeRenderComponent.setLayer(0);

        // Character
        GameObject character = createGameObject(-43f, -1.3f);

        SpriteRenderer characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(spriteSheet);
        characterRenderer.setSize(1, 1);
        characterRenderer.setSrcPosition(128, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1f);
        characterRenderer.setLayer(0);

        // Button
        CircleRenderer circleRenderer = createGameObject(-1, 2.5f).addComponent(CircleRenderer.class);
        circleRenderer.setRadius(0.75f);
        circleRenderer.setColor(PALETTE_PRIMARY);

        GameObject button = createGameObject(-1f, 2.5f);

        SpriteRenderer buttonRenderComponent = button.addComponent(SpriteRenderer.class);
        buttonRenderComponent.setImage(spriteSheet);
        buttonRenderComponent.setSize(2.5f, 2.5f);
        buttonRenderComponent.setSrcPosition(0, 0);
        buttonRenderComponent.setSrcSize(128, 128);
        buttonRenderComponent.setLayer(0);

        Button buttonInputComponent = button.addComponent(Button.class);
        buttonInputComponent.setSize(3, 3);
        buttonInputComponent.setInteractable(true);

        GameObject lineRendererGO = createGameObject();
        DottedLineRenderer lineRenderer = lineRendererGO.addComponent(DottedLineRenderer.class);
        lineRenderer.setPointA(button.x, button.y - 1.25f);
        lineRenderer.setPointB(-1f, bridge.y);
        lineRenderer.setCount(8);
        lineRenderer.setRadius(0.1f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(-4);

        buttonInputComponent.setOnClick(() -> {
            buttonSound.play(1);

            lineRenderer.setColor(PALETTE_PRIMARY);
            circleRenderer.setColor(Color.GREY);

            setUIButtonsInteractable(false);

            animator.clear();
            animator.add(ParallelAnimation.build(
                    WaitAnimation.build(0.4f),
                    ColorAnimation.build(promptRenderer::setTint, promptRenderer.color, Color.TRANSPARENT, 0.1f)
            ), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge, -1, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 4f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
            animator.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.75f), this::loadNextLevel);
            animator.start();

            saveProgress();
        });

        GameObject animatorGO = createGameObject();
        animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(ParallelAnimation.build(
                        ColorAnimation.build(fullScreenRenderer::setColor, Color.BLACK, Color.TRANSPARENT, 0.4f),
                        MoveToAnimation.build(character, -2.5f, character.y, 0.25f, EaseFunction.CUBIC_IN_OUT)
                ),
                () -> characterRenderer.setSrcPosition(0, 128));
        animator.add(WaitAnimation.build(1), this::showPrompt);
        animator.start();
    }

    @Override
    public void dispose() {
        super.dispose();
        animator = null;
    }

    @Override
    protected int getLevelIndex() {
        return 0;
    }

    private void showPrompt() {
        promptRenderer.setTint(Color.TRANSPARENT);
        promptRenderer.getOwner().setTransform(-1, 2.25f, 0);

        animator.clear();
        animator.add(WaitAnimation.build(1f));
        animator.add(ParallelAnimation.build(
                ColorAnimation.build(promptRenderer::setTint, Color.TRANSPARENT, Color.WHITE, 0.2f),
                MoveToAnimation.build(promptRenderer.getOwner(), -1, 2.5f, 0.4f, EaseFunction.CUBIC_IN_OUT)
        ));
        animator.add(WaitAnimation.build(1));
        animator.add(ColorAnimation.build(promptRenderer::setTint, Color.WHITE, Color.TRANSPARENT, 0.2f), this::showPrompt);
        animator.start();
    }

}
