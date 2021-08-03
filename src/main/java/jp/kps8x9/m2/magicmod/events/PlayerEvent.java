package jp.kps8x9.m2.magicmod.events;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerEvent {

    @SubscribeEvent
    public void onUseItemEvent(LivingEntityUseItemEvent event) {

    }

    @SubscribeEvent
    public void onMagicReleasedEvent(ArrowNockEvent event) {

    }
}
