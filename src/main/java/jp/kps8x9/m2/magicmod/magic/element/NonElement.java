package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface NonElement extends ElementBase {
    Color DarkPurple = new Color(35,0,19);
    Color LightPurple = new Color(235,179,255);

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
        return this.DarkPurple;
    }

    @Override
    default Color getLightColor() {
        return this.LightPurple;
    }
}
