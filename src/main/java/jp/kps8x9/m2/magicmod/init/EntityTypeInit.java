package jp.kps8x9.m2.magicmod.init;

import jp.kps8x9.m2.magicmod.MagicMod;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MagicMod.MOD_ID);

    //Entities

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}
