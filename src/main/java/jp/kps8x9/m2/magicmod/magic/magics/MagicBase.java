package jp.kps8x9.m2.magicmod.magic.magics;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class MagicBase {
    private float attackPoint;

    public MagicBase() {}

    //現在、属性のクラスを制作するまでに至っていないのでここはまだ作っていない。
    public void firstMagic() {

    }

    public void secondMagic() {

    }

    public void thirdMagic(Vector3d pos) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            }
        }, 0, 1);
    }

    public void setAttackPoint(float attackPoint) {
        this.attackPoint = attackPoint;
    }
}