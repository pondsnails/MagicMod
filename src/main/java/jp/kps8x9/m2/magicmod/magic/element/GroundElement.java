package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface GroundElement extends ElementBase {
    Color DarkBrown = new Color(111,75,62);
    Color LightBrown = new Color(197,149,107);

    int additionalDamage = 2;

    default void sideEffect(Vector3d growingPos,float diameter) {

    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }
}
