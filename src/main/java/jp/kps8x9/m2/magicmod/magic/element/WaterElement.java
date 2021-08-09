package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface WaterElement extends ElementBase {
    Color DarkWater = new Color(0,0,225);
    Color LightWater = new Color(221,238,255);

    int additionalDamage = 1;

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
        return this.DarkWater;
    }

    @Override
    default Color getLightColor() {
        return this.LightWater;
    }
}
