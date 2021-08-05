package jp.kps8x9.m2.magicmod.magic.attribution;

import java.awt.*;

public interface VoidAttribution extends AttributionBase{
    Color DarkPurple = new Color(35,0,19);
    Color LightPurple = new Color(235,179,255);

    int additionalDamage = 4;

    @Override
    default void sideEffect() {

    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }
}
