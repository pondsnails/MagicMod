package jp.kps8x9.m2.magicmod.magic.element;

import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public interface ElementBase {

    int additionalDamage = 0;

    default void firstSideEffect(Vector3d point,float diameter) {}

    default void secondSideEffect(Vector3d point, float diameter) {}

    default void thirdSideEffect(Vector3d point, float diameter) {}

    default int getAdditionalDamage() {
        return this.additionalDamage;
    }

    default Color getDarkColor() {
        return null;
    }

    default Color getLightColor() {
        return null;
    }
}
