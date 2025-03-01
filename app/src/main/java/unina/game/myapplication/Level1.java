package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.Assets;
import unina.game.myapplication.logic.common.CircleRenderer;
import unina.game.myapplication.logic.common.ColorAnimation;
import unina.game.myapplication.logic.common.Level;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.SoundFadeAnimation;
import unina.game.myapplication.logic.menu.MainMenu;

public class Level1 extends Level {

    private static final int PALETTE_BACKGROUND = 0xff005387;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    private Pixmap backgroundImage;
    private Pixmap spriteSheet;
    private Pixmap uiSpriteSheet;

    private Sound uiButtonSound, buttonSound;
    private Sound movingPlatformSound;
    private Sound winSound;

    private Music backgroundMusic;

    private AnimationSequence animator;
    private SpriteRenderer promptRenderer;

    public Level1(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(10);

        setClearColor(PALETTE_BACKGROUND);

        backgroundMusic = game.getAudio().newMusic(Assets.SOUND_MUSIC_LEVEL_1);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);

        backgroundImage = game.getGraphics().newPixmap(Assets.GRAPHICS_BACKGROUND_LEVEL_1, Graphics.PixmapFormat.RGB565);
        spriteSheet = game.getGraphics().newPixmap(Assets.GRAPHICS_GAME_SPRITES_LIGHT, Graphics.PixmapFormat.RGB565);
        uiSpriteSheet = game.getGraphics().newPixmap(Assets.GRAPHICS_UI_SPRITES, Graphics.PixmapFormat.RGB565);

        uiButtonSound = game.getAudio().newSound(Assets.SOUND_UI_BUTTON_CLICK);
        buttonSound = game.getAudio().newSound(Assets.SOUND_GAME_BUTTON_CLICK);
        winSound = game.getAudio().newSound(Assets.SOUND_GAME_WIN);
        movingPlatformSound = game.getAudio().newSound(Assets.SOUND_GAME_PLATFORM_MOVE);

        // Background
        SpriteRenderer backgroundRenderer = createGameObject().addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
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
        buttonInputComponent.setInteractable(false);

        GameObject lineRendererGO = createGameObject();
        DottedLineRenderer lineRenderer = lineRendererGO.addComponent(DottedLineRenderer.class);
        lineRenderer.setPointA(button.x, button.y - 1.25f);
        lineRenderer.setPointB(-1f, bridge.y);
        lineRenderer.setCount(8);
        lineRenderer.setRadius(0.1f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(-4);

        // Level selection button
        GameObject menuButtonGO = createGameObject(-20, 9);

        SpriteRenderer menuButtonRenderer = menuButtonGO.addComponent(SpriteRenderer.class);
        menuButtonRenderer.setImage(uiSpriteSheet);
        menuButtonRenderer.setSrcPosition(0, 0);
        menuButtonRenderer.setSrcSize(128, 128);
        menuButtonRenderer.setSize(3f, 3f);
        menuButtonRenderer.setLayer(28);
        menuButtonRenderer.setPivot(0.5f, 0.5f);

        Button menuButton = menuButtonGO.addComponent(Button.class);
        menuButton.setSize(6, 3);
        menuButton.setInteractable(false);
        menuButton.setOnClick(() -> {
            menuButton.setInteractable(false);
            buttonInputComponent.setInteractable(false);
            uiButtonSound.play(1);

            animator.clear();
            animator.add(ParallelAnimation.build(
                            FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f),
                            SoundFadeAnimation.build(backgroundMusic, 1, 0, 0.75f)
                    ),
                    () -> loadScene(MainMenu.class));
            animator.start();
        });

        buttonInputComponent.setOnClick(() -> {
            buttonSound.play(1);

            lineRenderer.setColor(PALETTE_PRIMARY);
            circleRenderer.setColor(Color.GREY);

            menuButton.setInteractable(false);
            buttonInputComponent.setInteractable(false);

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
            animator.add(ParallelAnimation.build(
                    FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f),
                    SoundFadeAnimation.build(backgroundMusic, 1, 0, 0.75f)
            ), this::loadNextLevel);
            animator.start();

            saveProgress();
        });

        GameObject animatorGO = createGameObject();
        animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(ParallelAnimation.build(
                        FadeAnimation.build(fullScreenRenderer, Color.BLACK, Color.TRANSPARENT, 0.4f),
                        MoveToAnimation.build(character, -2.5f, character.y, 0.25f, EaseFunction.CUBIC_IN_OUT),
                        SoundFadeAnimation.build(backgroundMusic, 0, 1, 0.4f)
                ),
                () -> characterRenderer.setSrcPosition(0, 128));
        animator.add(MoveToAnimation.build(menuButtonGO, -Camera.getInstance().getSizeX() / 2 + 1.5f, menuButtonGO.y, 0.25f, EaseFunction.CUBIC_IN_OUT),
                () -> {
                    buttonInputComponent.interactable = true;
                    menuButton.interactable = true;
                });
        animator.add(WaitAnimation.build(1), this::showPrompt);
        animator.start();
    }

    @Override
    public void resume() {
        super.resume();
        backgroundMusic.play();
    }

    @Override
    public void pause() {
        super.pause();
        backgroundMusic.pause();
    }

    @Override
    public void dispose() {
        super.dispose();

        backgroundMusic.stop();
        backgroundMusic.dispose();

        backgroundImage.dispose();
        spriteSheet.dispose();
        uiSpriteSheet.dispose();

        uiButtonSound.dispose();
        buttonSound.dispose();
        movingPlatformSound.dispose();
        winSound.dispose();

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
