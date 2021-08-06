package jp.kps8x9.m2.magicmod.items;

import jp.kps8x9.m2.magicmod.magic.magics.NonMagic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class NonMagicWand extends MagicWandBase {

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        return super.use(world, playerEntity, hand);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int count) {
        super.releaseUsing(itemStack, world, livingEntity, count);
    }

    public NonMagicWand(Properties properties) {
        super(properties);
        super.magic = new NonMagic();
    }

}
