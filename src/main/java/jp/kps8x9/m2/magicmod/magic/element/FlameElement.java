package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface FlameElement extends ElementBase {
    Color DarkRed = new Color(102,0,0);
    Color LightRed = new Color(250,219,218);

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
        return this.DarkRed;
    }

    @Override
    default Color getLightColor() {
        return this.LightRed;
    }
}
