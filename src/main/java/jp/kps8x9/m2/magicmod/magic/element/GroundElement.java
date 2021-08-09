package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface GroundElement extends ElementBase {
    Color DarkBrown = new Color(111,75,62);
    Color LightBrown = new Color(197,149,107);

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
        return this.DarkBrown;
    }

    @Override
    default Color getLightColor() {
        return this.LightBrown;
    }
}
