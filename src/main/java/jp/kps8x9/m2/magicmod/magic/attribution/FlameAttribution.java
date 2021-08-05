package jp.kps8x9.m2.magicmod.magic.attribution;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface FlameAttribution extends AttributionBase{
    Color DarkRed = new Color(102,0,0);
    Color LightRed = new Color(250,219,218);

    int additionalDamage = 2;

    default void sideEffect(Vector3d burningPosition, float diameter) {

    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }
}
