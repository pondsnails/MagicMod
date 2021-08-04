package jp.kps8x9.m2.magicmod.magic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class MagicBase {
    private float attackPoint;
    private PlayerEntity playerEntity;
    private World world;
    private ItemStack itemStack;

    public MagicBase(PlayerEntity playerEntity, World world, ItemStack itemStack) {
        this.playerEntity = playerEntity;
        this.world = world;
        this.itemStack = itemStack;
    }

    public void firstMagic() {}

    public void secondMagic() {}

    public void thirdMagic() {}

    public void fourthMagic(Vector3d pos) {
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