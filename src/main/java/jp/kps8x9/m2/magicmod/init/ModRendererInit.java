package jp.kps8x9.m2.magicmod.init;

import jp.kps8x9.m2.magicmod.MagicMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MagicMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModRendererInit {

    @SubscribeEvent
    public static void registerRenderer(final FMLClientSetupEvent event) {
    }
}