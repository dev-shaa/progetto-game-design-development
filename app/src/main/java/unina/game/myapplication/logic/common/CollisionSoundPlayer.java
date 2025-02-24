package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

public class CollisionSoundPlayer extends BehaviourComponent {

    private Sound sound;
    private float volume = 1;
    private float minVelocitySqrMagnitude = 20f;

    @Override
    public void onRemove() {
        super.onRemove();

        sound = null;
        volume = 1;
    }

    @Override
    public void onCollisionEnter(RigidBody other, float relativeVelocityX, float relativeVelocityY) {
        super.onCollisionEnter(other, relativeVelocityX, relativeVelocityY);
        float sqrMagnitude = (relativeVelocityX * relativeVelocityX) + (relativeVelocityY * relativeVelocityY);

        if (sound != null && sqrMagnitude > minVelocitySqrMagnitude) {
            sound.play(volume);
        }
    }

    @Override
    public int getComponentPoolSize() {
        return 2;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setMinVelocitySqrMagnitude(float minVelocitySqrMagnitude) {
        this.minVelocitySqrMagnitude = minVelocitySqrMagnitude;
    }

}
