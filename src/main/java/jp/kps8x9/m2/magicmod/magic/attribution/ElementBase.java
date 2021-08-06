package jp.kps8x9.m2.magicmod.magic.attribution;

public interface ElementBase {

    int additionalDamage = 0;

    default void sideEffect() {}

    default int getAdditionalDamage() {
        return this.additionalDamage;
    }
}
