package jp.kps8x9.m2.magicmod.init;

import jp.kps8x9.m2.magicmod.MagicMod;
import jp.kps8x9.m2.magicmod.particle.particle_data.MagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particle_data.SuperMagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particle_type.MagicParticleType;
import jp.kps8x9.m2.magicmod.particle.particle_type.SuperMagicParticleType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MagicMod.MOD_ID);

    public static RegistryObject<ParticleType<MagicParticleData>> magicParticle = PARTICLE_TYPE_DEFERRED_REGISTER.register("magic_particle", () -> new MagicParticleType());
    public static RegistryObject<ParticleType<SuperMagicParticleData>> superMagicParticle = PARTICLE_TYPE_DEFERRED_REGISTER.register("super_magic_particle", () -> new SuperMagicParticleType());
    public static final RegistryObject<BasicParticleType> beamParticle = PARTICLE_TYPE_DEFERRED_REGISTER.register("beam_particle", () -> new BasicParticleType(true));

    public static void register(IEventBus modEventBus) {
        PARTICLE_TYPE_DEFERRED_REGISTER.register(modEventBus);
    }
}
