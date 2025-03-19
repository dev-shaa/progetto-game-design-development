package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.ParticleSystem;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.physics.TriangleCollider;
import unina.game.myapplication.core.rendering.ParticleSystemRenderer;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.Assets;
import unina.game.myapplication.logic.common.inputs.PlatformDraggingComponent;
import unina.game.myapplication.logic.common.renderers.DraggablePlatformLineRenderer;
import unina.game.myapplication.logic.scenes.Level;
import unina.game.myapplication.core.rendering.RectRenderer;

public class Level5 extends Level {

    private static final int PALETTE_BACKGROUND = 0xffF9A900;
    private static final int PALETTE_PRIMARY = 0xff2B2B2C;

    public Level5(Game game) {
        super(game);
    }

    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(30);

        setClearColor(PALETTE_BACKGROUND);

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
        backgroundRenderer.setLayer(128);

        // Transition Panel
        RectRenderer fullScreenRenderer = createGameObject().addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        //Pavimento Character
        float floorW = 16;
        float floorH = 1;
        GameObject floorCharacter = createGameObject(3.5f, -20);

        if (DEBUG) {
            RectRenderer floorCharacterRenderComponent = floorCharacter.addComponent(RectRenderer.class);
            floorCharacterRenderComponent.setSize(floorW, floorH);
            floorCharacterRenderComponent.color = PALETTE_PRIMARY;
            floorCharacterRenderComponent.setLayer(20);
        }

        RigidBody floorCharacterRigidBody = floorCharacter.addComponent(RigidBody.class);
        floorCharacterRigidBody.setType(RigidBody.Type.STATIC);
        floorCharacterRigidBody.addCollider(BoxCollider.build(floorW, floorH));

        //Muro sinistra Character
        float wallLeftW = 12;
        float wallH = 1;
        GameObject wallCharacterLeft = createGameObject(-11, -13.5f, 90);

        if (DEBUG) {
            RectRenderer wallCharacterLeftRenderComponent = wallCharacterLeft.addComponent(RectRenderer.class);
            wallCharacterLeftRenderComponent.setSize(wallLeftW, wallH);
            wallCharacterLeftRenderComponent.color = PALETTE_PRIMARY;
            wallCharacterLeftRenderComponent.setLayer(20);
        }

        RigidBody wallCharacterLeftRigidBody = wallCharacterLeft.addComponent(RigidBody.class);
        wallCharacterLeftRigidBody.setType(RigidBody.Type.STATIC);
        wallCharacterLeftRigidBody.addCollider(BoxCollider.build(wallLeftW, wallH));

        //Muro destra Character
        float wallRightW = 6;
        GameObject wallCharacterRight = createGameObject(-5, -10.5f, 90);

        if (DEBUG) {
            RectRenderer wallCharacterRightRenderComponent = wallCharacterRight.addComponent(RectRenderer.class);
            wallCharacterRightRenderComponent.setSize(wallRightW, wallH);
            wallCharacterRightRenderComponent.color = PALETTE_PRIMARY;
            wallCharacterRightRenderComponent.setLayer(20);
        }

        RigidBody wallCharacterRightRigidBody = wallCharacterRight.addComponent(RigidBody.class);
        wallCharacterRightRigidBody.setType(RigidBody.Type.STATIC);
        wallCharacterRightRigidBody.addCollider(BoxCollider.build(wallRightW, wallH));

        //Character
        GameObject character = createGameObject(-8, -18.5f);

        SpriteRenderer characterRenderComponent = character.addComponent(SpriteRenderer.class);
        characterRenderComponent.setImage(spriteSheet);
        characterRenderComponent.setSrcPosition(0, 128);
        characterRenderComponent.setSrcSize(128, 128);
        characterRenderComponent.setSize(3, 3);
        characterRenderComponent.setPivot(0.5f, 1);
        characterRenderComponent.setLayer(20);

        //Ponte Character
        GameObject bridgeCharacter = createGameObject(-5, -16.5f, 90);

        SpriteRenderer bridgeCharacterRenderComponent = bridgeCharacter.addComponent(SpriteRenderer.class);
        bridgeCharacterRenderComponent.setImage(spriteSheet);
        bridgeCharacterRenderComponent.setSrcPosition(128, 48);
        bridgeCharacterRenderComponent.setSrcSize(128, 32);
        bridgeCharacterRenderComponent.setSize(wallRightW, wallH);
        bridgeCharacterRenderComponent.setLayer(20);

        RigidBody bridgeCharacterRigidBody = bridgeCharacter.addComponent(RigidBody.class);
        bridgeCharacterRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeCharacterRigidBody.addCollider(BoxCollider.build(wallRightW, wallH));

        //Ponte floor Character
        float bridgeFloorW = 7;
        GameObject bridgeFloorCharacter = createGameObject(-8, -20);

        SpriteRenderer bridgeFloorCharacterRenderComponent = bridgeFloorCharacter.addComponent(SpriteRenderer.class);
        bridgeFloorCharacterRenderComponent.setImage(spriteSheet);
        bridgeFloorCharacterRenderComponent.setSrcPosition(128, 48);
        bridgeFloorCharacterRenderComponent.setSrcSize(128, 32);
        bridgeFloorCharacterRenderComponent.setSize(bridgeFloorW, wallH);
        bridgeFloorCharacterRenderComponent.setLayer(20);

        RigidBody bridgeFloorCharacterRigidBody = bridgeFloorCharacter.addComponent(RigidBody.class);
        bridgeFloorCharacterRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeFloorCharacterRigidBody.addCollider(BoxCollider.build(bridgeFloorW, wallH));

        //Pavimento Vasca principale
        float poolFloorW = 11;
        GameObject floorPool = createGameObject(5, 0);

        RectRenderer floorPoolRenderComponent = floorPool.addComponent(RectRenderer.class);
        floorPoolRenderComponent.setSize(poolFloorW, floorH);
        floorPoolRenderComponent.color = PALETTE_PRIMARY;
        floorPoolRenderComponent.setLayer(20);

        RigidBody floorPoolRigidBody = floorPool.addComponent(RigidBody.class);
        floorPoolRigidBody.setType(RigidBody.Type.STATIC);
        floorPoolRigidBody.addCollider(BoxCollider.build(poolFloorW, floorH));

        //Muro Vasca destra
        float poolWallW = 11;
        GameObject wallRightPool = createGameObject(10, 5, 90);

        RectRenderer wallRightPoolRenderComponent = wallRightPool.addComponent(RectRenderer.class);
        wallRightPoolRenderComponent.setSize(poolWallW, wallH);
        wallRightPoolRenderComponent.color = PALETTE_PRIMARY;
        wallRightPoolRenderComponent.setLayer(20);

        RigidBody wallRightPoolRigidBody = wallRightPool.addComponent(RigidBody.class);
        wallRightPoolRigidBody.setType(RigidBody.Type.STATIC);
        wallRightPoolRigidBody.addCollider(BoxCollider.build(poolWallW, wallH));

        //Muro Vasca destra
        GameObject wallLeftPool = createGameObject(0, 5, 90);

        RectRenderer wallLeftPoolRenderComponent = wallLeftPool.addComponent(RectRenderer.class);
        wallLeftPoolRenderComponent.setSize(poolWallW, wallH);
        wallLeftPoolRenderComponent.color = PALETTE_PRIMARY;
        wallLeftPoolRenderComponent.setLayer(20);

        RigidBody wallLeftPoolRigidBody = wallLeftPool.addComponent(RigidBody.class);
        wallLeftPoolRigidBody.setType(RigidBody.Type.STATIC);
        wallLeftPoolRigidBody.addCollider(BoxCollider.build(poolWallW, wallH));

        //Acqua vasca
        GameObject water = createGameObject(5, 2);

        ParticleSystem waterParticleSystem = water.addComponent(ParticleSystem.class);
        waterParticleSystem.setRadius(0.5f);
        waterParticleSystem.addParticlesGroup(5, 2, 4, 2, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);
        waterParticleSystem.addParticlesGroup(-4, 30, 8, 20, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);
//        waterParticleSystem.setSize(4, 2);
//        waterParticleSystem.setGroupFlags(ParticleSystem.FLAG_GROUP_SOLID);
//        waterParticleSystem.setFlags(ParticleSystem.FLAG_PARTICLE_WATER);

        ParticleSystemRenderer waterParticlesRenderer = water.addComponent(ParticleSystemRenderer.class);
        waterParticlesRenderer.setParticleSystem(waterParticleSystem);
        waterParticlesRenderer.setColor(Color.BLUE);

        //Piattaforma Triangolo
        GameObject trianglePlatform = createGameObject(5, 10);

        RigidBody trianglePlatformRigidBody = trianglePlatform.addComponent(RigidBody.class);
        trianglePlatformRigidBody.setType(RigidBody.Type.DYNAMIC);
        trianglePlatformRigidBody.addCollider(TriangleCollider.build(-3, -1, 3, -1, -3, 2, 0.5f, 0, 1, false));

        //Piattaforma scorrevole acqua destra
        float draggingSizeW = 14;
        float draggingRightX = 0;
        GameObject draggingPlatformRight = createGameObject(draggingRightX, 20, 40);

        RigidBody draggingPlatformRightRigidBody = draggingPlatformRight.addComponent(RigidBody.class);
        draggingPlatformRightRigidBody.setType(RigidBody.Type.KINEMATIC);
        draggingPlatformRightRigidBody.addCollider(BoxCollider.build(draggingSizeW, 1));

        PlatformDraggingComponent draggingPlatformRightDraggingComponent = draggingPlatformRight.addComponent(PlatformDraggingComponent.class);
        draggingPlatformRightDraggingComponent.setRigidBody(draggingPlatformRightRigidBody);
        draggingPlatformRightDraggingComponent.setSize(draggingSizeW / 2, draggingSizeW / 2);
        draggingPlatformRightDraggingComponent.setStart(draggingRightX, 20);
        draggingPlatformRightDraggingComponent.setEnd(draggingRightX, 16);

        DraggablePlatformLineRenderer draggingPlatformRightLineRender = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        draggingPlatformRightLineRender.setStart(draggingRightX, 20);
        draggingPlatformRightLineRender.setEnd(draggingRightX, 16);
        draggingPlatformRightLineRender.setRadius(0.25f);
        draggingPlatformRightLineRender.setColor(Color.WHITE);
        draggingPlatformRightLineRender.setLayer(-2);

        //Piattaforma scorrevole acqua sinistra
        float draggingLeftX = -8;
        GameObject draggingPlatformLeft = createGameObject(draggingLeftX, 20, 140);

        RigidBody draggingPlatformLeftRigidBody = draggingPlatformLeft.addComponent(RigidBody.class);
        draggingPlatformLeftRigidBody.setType(RigidBody.Type.KINEMATIC);
        draggingPlatformLeftRigidBody.addCollider(BoxCollider.build(draggingSizeW, 1));

        PlatformDraggingComponent draggingPlatformLeftDraggingComponent = draggingPlatformLeft.addComponent(PlatformDraggingComponent.class);
        draggingPlatformLeftDraggingComponent.setRigidBody(draggingPlatformLeftRigidBody);
        draggingPlatformLeftDraggingComponent.setSize(draggingSizeW / 2, draggingSizeW / 2);
        draggingPlatformLeftDraggingComponent.setStart(draggingLeftX, 20);
        draggingPlatformLeftDraggingComponent.setEnd(draggingLeftX, 16);

        DraggablePlatformLineRenderer draggingPlatformLeftLineRender = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        draggingPlatformLeftLineRender.setStart(draggingLeftX, 20);
        draggingPlatformLeftLineRender.setEnd(draggingLeftX, 16);
        draggingPlatformLeftLineRender.setRadius(0.25f);
        draggingPlatformLeftLineRender.setColor(Color.WHITE);
        draggingPlatformLeftLineRender.setLayer(-2);

        //Muro acqua destra
        float wallWaterW = 20;
        GameObject wallWaterRight = createGameObject(2, 30, 90);

        RigidBody wallWaterRightRigidBody = wallWaterRight.addComponent(RigidBody.class);
        wallWaterRightRigidBody.setType(RigidBody.Type.STATIC);
        wallWaterRightRigidBody.addCollider(BoxCollider.build(wallWaterW, wallH));

        //Muro acqua sinistra
        GameObject wallWaterLeft = createGameObject(-10, 30, 90);

        RigidBody wallWaterLeftRigidBody = wallWaterLeft.addComponent(RigidBody.class);
        wallWaterLeftRigidBody.setType(RigidBody.Type.STATIC);
        wallWaterLeftRigidBody.addCollider(BoxCollider.build(wallWaterW, wallH));

        //Acqua piattaforme
        GameObject waterPlatform = createGameObject(-4, 30);

        ParticleSystem waterPlatformParticleSystem = waterPlatform.addComponent(ParticleSystem.class);
        waterPlatformParticleSystem.setRadius(0.5f);
//        waterPlatformParticleSystem.setSize(8, 20);
//        waterPlatformParticleSystem.setGroupFlags(ParticleSystem.FLAG_GROUP_SOLID);
//        waterPlatformParticleSystem.setFlags(ParticleSystem.FLAG_PARTICLE_WATER);

        ParticleSystemRenderer waterPlatformRenderer = waterPlatform.addComponent(ParticleSystemRenderer.class);
        waterPlatformRenderer.setParticleSystem(waterPlatformParticleSystem);
        waterPlatformRenderer.setColor(Color.BLUE);

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
