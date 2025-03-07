package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.Assets;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.common.RectRenderer;

public class Level5 extends Scene {

    private static final int PALETTE_BACKGROUND = 0xffF9A900;
    private static final int PALETTE_PRIMARY = 0xff2B2B2C;

    private Music backgroundMusic;
    private AnimationSequence animator;
    private SpriteRenderer promptRenderer;
    public Level5(Game game) {
        super(game);
    }

    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(30);

        setClearColor(PALETTE_BACKGROUND);

        backgroundMusic = getMusic(Assets.SOUND_MUSIC_LEVELS);
        backgroundMusic.setLooping(true);
        if (MUSIC_ON)
            backgroundMusic.setVolume(0.5f);
        else
            backgroundMusic.setVolume(0);

        Pixmap spriteSheet = getImage(Assets.GRAPHICS_GAME_SPRITES_LIGHT);
        Pixmap uiSpriteSheet = getImage(Assets.GRAPHICS_UI_SPRITES);

        Sound uiButtonSound = getSound(Assets.SOUND_UI_BUTTON_CLICK);
        Sound buttonSound = getSound(Assets.SOUND_GAME_BUTTON_CLICK);
        Sound winSound = getSound(Assets.SOUND_GAME_WIN);
        Sound movingPlatformSound = getSound(Assets.SOUND_GAME_PLATFORM_MOVE);

        // Background
        SpriteRenderer backgroundRenderer = createGameObject().addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(getImage(Assets.GRAPHICS_BACKGROUND_LEVEL_3));
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

        //Pavimento Character
        float floorW = 23;
        float floorH = 1;
        GameObject floorCharacter = createGameObject(0,-20);

        PlatformRenderComponent floorCharacterRenderComponent = floorCharacter.addComponent(PlatformRenderComponent.class);
        floorCharacterRenderComponent.setSize(floorW,floorH);
        floorCharacterRenderComponent.color = PALETTE_PRIMARY;
        floorCharacterRenderComponent.setLayer(20);

        RigidBody floorCharacterRigidBody = floorCharacter.addComponent(RigidBody.class);
        floorCharacterRigidBody.setType(RigidBody.Type.STATIC);
        floorCharacterRigidBody.addCollider(BoxCollider.build(floorW,floorH));
    }
}
