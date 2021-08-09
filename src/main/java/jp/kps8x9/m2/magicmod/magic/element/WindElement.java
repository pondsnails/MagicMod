package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface WindElement extends ElementBase {
    Color DarkWind = new Color(105,176,118);
    Color LightWind = new Color(224,255,224);

    int additionalDamage = 2;

    @Override
    default void firstSideEffect(Vector3d point, float diameter) {
        ElementBase.super.firstSideEffect(point, diameter);
    }

    @Override
    default void secondSideEffect(Vector3d point, float diameter) {
        ElementBase.super.secondSideEffect(point, diameter);
    }

    @Override
    default void thirdSideEffect(Vector3d point, float diameter) {
        ElementBase.super.thirdSideEffect(point, diameter);
    }

    @Override
    default int getAdditionalDamage() {
        return this.additionalDamage;
    }

    @Override
    default Color getDarkColor() {
        return this.DarkWind;
    }

    @Override
    default Color getLightColor() {
        return this.LightWind;
    }
}
