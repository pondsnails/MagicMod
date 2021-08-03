package jp.kps8x9.m2.magicmod.particle.particle_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jp.kps8x9.m2.magicmod.particle.particle_data.SuperMagicParticleData;
import net.minecraft.particles.ParticleType;

public class SuperMagicParticleType extends ParticleType<SuperMagicParticleData> {

    public SuperMagicParticleType() {
        super(false, SuperMagicParticleData.DESERIALIZER);
    }

    @Override
    public Codec<SuperMagicParticleData> codec() {
        return RecordCodecBuilder.create((magicParticleDataInstance) -> magicParticleDataInstance.group(
                Codec.FLOAT.fieldOf("r").forGetter((magicParticleData) -> magicParticleData.getR()),
                Codec.FLOAT.fieldOf("g").forGetter((magicParticleData) -> magicParticleData.getG()),
                Codec.FLOAT.fieldOf("b").forGetter((magicParticleData) -> magicParticleData.getB()),
                Codec.FLOAT.fieldOf("scale").forGetter((magicParticleData) -> magicParticleData.getScale()),
                Codec.INT.fieldOf("lifeTime").forGetter((magicParticleData -> magicParticleData.getLifeTime())))
                .apply(magicParticleDataInstance, SuperMagicParticleData::new));
    }
}
