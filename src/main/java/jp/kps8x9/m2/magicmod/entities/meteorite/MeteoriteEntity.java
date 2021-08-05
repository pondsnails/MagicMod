package jp.kps8x9.m2.magicmod.entities.meteorite;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

public class MeteoriteEntity extends AbstractMinecartEntity {
    protected MeteoriteEntity(EntityType<?> p_i48538_1_, World p_i48538_2_) {
        super(p_i48538_1_, p_i48538_2_);
    }

    @Override
    public Type getMinecartType() {
        return Type.TNT;
    }

}
