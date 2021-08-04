package jp.kps8x9.m2.magicmod.init;

import jp.kps8x9.m2.magicmod.particle.particle_factory.MagicParticleFactory;
import jp.kps8x9.m2.magicmod.particle.particle_factory.SuperMagicParticleFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ParticleFactoryInit {

    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.magicParticle.get(), (sprite) -> new MagicParticleFactory(sprite));
        Minecraft.getInstance().particleEngine.register(ParticleInit.superMagicParticle.get(), (sprite) -> new SuperMagicParticleFactory(sprite));
    }
}
