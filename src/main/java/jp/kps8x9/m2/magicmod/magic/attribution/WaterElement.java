package jp.kps8x9.m2.magicmod.magic.attribution;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface WaterElement extends ElementBase {
    Color DarkWater = new Color(0,0,225);
    Color LightWater = new Color(221,238,255);

    int additionalDamage = 1;

    default void sideEffect(Vector3d waterPos,int diameter) {

    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }
}
