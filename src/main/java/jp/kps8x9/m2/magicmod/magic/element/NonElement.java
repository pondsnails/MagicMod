package jp.kps8x9.m2.magicmod.magic.element;

import java.awt.*;

public interface NonElement extends ElementBase {
    Color DarkPurple = new Color(35,0,19);
    Color LightPurple = new Color(235,179,255);

    int additionalDamage = 2;

    @Override
    default void sideEffect() {

    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }

    default Color getDarkColor() {
        return DarkPurple;
    }

    default Color getLightColor() {
        return LightPurple;
    }
}
