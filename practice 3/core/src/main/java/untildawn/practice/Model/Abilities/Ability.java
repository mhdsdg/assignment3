package untildawn.practice.Model.Abilities;

import com.badlogic.gdx.graphics.Texture;

public abstract class Ability {
    public String name;
    public Texture texture;
    public abstract void doEffect();
}
