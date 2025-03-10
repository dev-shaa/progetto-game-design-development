package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import kotlin.io.LineReader;
import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.ParticleSystem;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.physics.TriangleCollider;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.Assets;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.common.DraggablePlatformLineRenderer;
import unina.game.myapplication.logic.common.Level;
import unina.game.myapplication.logic.common.RectRenderer;

public class Level5 extends Level {

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

        Pixmap spriteSheet = getImage(Assets.GRAPHICS_GAME_SPRITES_DARK);
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
        float floorW = 16;
        float floorH = 1;
        GameObject floorCharacter = createGameObject(3.5f,-20);

        if (DEBUG) {
            PlatformRenderComponent floorCharacterRenderComponent = floorCharacter.addComponent(PlatformRenderComponent.class);
            floorCharacterRenderComponent.setSize(floorW,floorH);
            floorCharacterRenderComponent.color = PALETTE_PRIMARY;
            floorCharacterRenderComponent.setLayer(20);
        }

        RigidBody floorCharacterRigidBody = floorCharacter.addComponent(RigidBody.class);
        floorCharacterRigidBody.setType(RigidBody.Type.STATIC);
        floorCharacterRigidBody.addCollider(BoxCollider.build(floorW,floorH));

        //Muro sinistra Character
        float wallLeftW = 12;
        float wallH = 1;
        GameObject wallCharacterLeft = createGameObject(-11,-13.5f,90);

        if (DEBUG) {
            PlatformRenderComponent wallCharacterLeftRenderComponent = wallCharacterLeft.addComponent(PlatformRenderComponent.class);
            wallCharacterLeftRenderComponent.setSize(wallLeftW, wallH);
            wallCharacterLeftRenderComponent.color = PALETTE_PRIMARY;
            wallCharacterLeftRenderComponent.setLayer(20);
        }

        RigidBody wallCharacterLeftRigidBody = wallCharacterLeft.addComponent(RigidBody.class);
        wallCharacterLeftRigidBody.setType(RigidBody.Type.STATIC);
        wallCharacterLeftRigidBody.addCollider(BoxCollider.build(wallLeftW, wallH));

        //Muro destra Character
        float wallRightW = 6;
        GameObject wallCharacterRight = createGameObject(-5,-10.5f,90);

        if (DEBUG) {
            PlatformRenderComponent wallCharacterRightRenderComponent = wallCharacterRight.addComponent(PlatformRenderComponent.class);
            wallCharacterRightRenderComponent.setSize(wallRightW, wallH);
            wallCharacterRightRenderComponent.color = PALETTE_PRIMARY;
            wallCharacterRightRenderComponent.setLayer(20);
        }

        RigidBody wallCharacterRightRigidBody = wallCharacterRight.addComponent(RigidBody.class);
        wallCharacterRightRigidBody.setType(RigidBody.Type.STATIC);
        wallCharacterRightRigidBody.addCollider(BoxCollider.build(wallRightW,wallH));

        //Character
        GameObject character = createGameObject(-8,-18.5f);

        SpriteRenderer characterRenderComponent = character.addComponent(SpriteRenderer.class);
        characterRenderComponent.setImage(spriteSheet);
        characterRenderComponent.setSrcPosition(0,128);
        characterRenderComponent.setSrcSize(128,128);
        characterRenderComponent.setSize(2,2);
        characterRenderComponent.setLayer(20);

        //Ponte Character
        GameObject bridgeCharacter = createGameObject(-5,-16.5f,90);

        SpriteRenderer bridgeCharacterRenderComponent = bridgeCharacter.addComponent(SpriteRenderer.class);
        bridgeCharacterRenderComponent.setImage(spriteSheet);
        bridgeCharacterRenderComponent.setSrcPosition(128,48);
        bridgeCharacterRenderComponent.setSrcSize(128,32);
        bridgeCharacterRenderComponent.setSize(wallRightW,wallH);
        bridgeCharacterRenderComponent.setLayer(20);

        RigidBody bridgeCharacterRigidBody = bridgeCharacter.addComponent(RigidBody.class);
        bridgeCharacterRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeCharacterRigidBody.addCollider(BoxCollider.build(wallRightW,wallH));

        //Ponte floor Character
        float bridgeFloorW = 7;
        GameObject bridgeFloorCharacter = createGameObject(-8,-20);

        SpriteRenderer bridgeFloorCharacterRenderComponent = bridgeFloorCharacter.addComponent(SpriteRenderer.class);
        bridgeFloorCharacterRenderComponent.setImage(spriteSheet);
        bridgeFloorCharacterRenderComponent.setSrcPosition(128,48);
        bridgeFloorCharacterRenderComponent.setSrcSize(128,32);
        bridgeFloorCharacterRenderComponent.setSize(bridgeFloorW,wallH);
        bridgeFloorCharacterRenderComponent.setLayer(20);

        RigidBody bridgeFloorCharacterRigidBody = bridgeFloorCharacter.addComponent(RigidBody.class);
        bridgeFloorCharacterRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeFloorCharacterRigidBody.addCollider(BoxCollider.build(bridgeFloorW,wallH));

        //Pavimento Vasca principale
        float poolFloorW = 11;
        GameObject floorPool = createGameObject(5,0);

        PlatformRenderComponent floorPoolRenderComponent = floorPool.addComponent(PlatformRenderComponent.class);
        floorPoolRenderComponent.setSize(poolFloorW,floorH);
        floorPoolRenderComponent.color = PALETTE_PRIMARY;
        floorPoolRenderComponent.setLayer(20);

        RigidBody floorPoolRigidBody = floorPool.addComponent(RigidBody.class);
        floorPoolRigidBody.setType(RigidBody.Type.STATIC);
        floorPoolRigidBody.addCollider(BoxCollider.build(poolFloorW,floorH));

        //Muro Vasca destra
        float poolWallW = 11;
        GameObject wallRightPool = createGameObject(10,5,90);

        PlatformRenderComponent wallRightPoolRenderComponent = wallRightPool.addComponent(PlatformRenderComponent.class);
        wallRightPoolRenderComponent.setSize(poolWallW,wallH);
        wallRightPoolRenderComponent.color = PALETTE_PRIMARY;
        wallRightPoolRenderComponent.setLayer(20);

        RigidBody wallRightPoolRigidBody = wallRightPool.addComponent(RigidBody.class);
        wallRightPoolRigidBody.setType(RigidBody.Type.STATIC);
        wallRightPoolRigidBody.addCollider(BoxCollider.build(poolWallW,wallH));

        //Muro Vasca destra
        GameObject wallLeftPool = createGameObject(0,5,90);

        PlatformRenderComponent wallLeftPoolRenderComponent = wallLeftPool.addComponent(PlatformRenderComponent.class);
        wallLeftPoolRenderComponent.setSize(poolWallW,wallH);
        wallLeftPoolRenderComponent.color = PALETTE_PRIMARY;
        wallLeftPoolRenderComponent.setLayer(20);

        RigidBody wallLeftPoolRigidBody = wallLeftPool.addComponent(RigidBody.class);
        wallLeftPoolRigidBody.setType(RigidBody.Type.STATIC);
        wallLeftPoolRigidBody.addCollider(BoxCollider.build(poolWallW,wallH));

        //Acqua vasca
        GameObject water = createGameObject(5,2);

        ParticleSystem waterParticleSystem = water.addComponent(ParticleSystem.class);
        waterParticleSystem.setRadius(0.15f);
        waterParticleSystem.setSize(4,2);
        waterParticleSystem.setGroupFlags(ParticleSystem.FLAG_GROUP_SOLID);
        waterParticleSystem.setFlags(ParticleSystem.FLAG_PARTICLE_WATER);

        //Piattaforma Triangolo
        GameObject trianglePlatform = createGameObject(5,6);

        RigidBody trianglePlatformRigidBody = trianglePlatform.addComponent(RigidBody.class);
        trianglePlatformRigidBody.setType(RigidBody.Type.DYNAMIC);
        trianglePlatformRigidBody.addCollider(TriangleCollider.build(-3,-1,3,-1,-3,2,2,0,1,false));

        //Piattaforma scorrevole acqua destra
        float draggingSizeW = 8;
        GameObject draggingPlatformRight = createGameObject(-2,18,40);

        RigidBody draggingPlatformRightRigidBody = draggingPlatformRight.addComponent(RigidBody.class);
        draggingPlatformRightRigidBody.setType(RigidBody.Type.KINEMATIC);
        draggingPlatformRightRigidBody.addCollider(BoxCollider.build(draggingSizeW,1));

        PlatformDraggingComponent draggingPlatformRightDraggingComponent = draggingPlatformRight.addComponent(PlatformDraggingComponent.class);
        draggingPlatformRightDraggingComponent.setRigidBody(draggingPlatformRightRigidBody);
        draggingPlatformRightDraggingComponent.setSize(draggingSizeW,draggingSizeW);
        draggingPlatformRightDraggingComponent.setStart(-2,18);
        draggingPlatformRightDraggingComponent.setEnd(-4,10);

        DraggablePlatformLineRenderer draggingPlatformRightLineRender = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        draggingPlatformRightLineRender.setStart(-2,18.5f);
        draggingPlatformRightLineRender.setEnd(-4,10.5f);
        draggingPlatformRightLineRender.setRadius(0.25f);
        draggingPlatformRightLineRender.setColor(Color.WHITE);
        draggingPlatformRightLineRender.setLayer(-2);
    }

    @Override
    protected int getLevelIndex() {
        return 4;
    }

    @Override
    protected String getBackgroundMusic() {
        return Assets.SOUND_MUSIC_LEVELS;
    }
}
