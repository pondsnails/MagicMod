package jp.kps8x9.m2.magicmod.magic.attribution;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface WindAttribution extends AttributionBase{
    Color DarkWind = new Color(105,176,118);
    Color LightWind = new Color(224,255,224);

    int additionalDamage = 2;

    default void sideEffect(Vector3d windPos,float power) {

    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }
}
