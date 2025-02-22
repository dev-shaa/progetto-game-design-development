package unina.game.myapplication.logic.common;

import android.util.Log;

import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

public class CollisionSoundPlayer extends BehaviourComponent {

    private Sound sound;
    private float volume = 1;

    @Override
    public void onRemove() {
        super.onRemove();

        sound = null;
        volume = 1;
    }

    @Override
    public void onCollisionEnter(RigidBody other) {
        super.onCollisionEnter(other);

        if (sound != null)
            sound.play(volume);
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

}
