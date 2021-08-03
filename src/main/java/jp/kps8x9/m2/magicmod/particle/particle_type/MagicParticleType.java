package jp.kps8x9.m2.magicmod.particle.particle_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jp.kps8x9.m2.magicmod.particle.particle_data.MagicParticleData;
import net.minecraft.particles.ParticleType;

public class MagicParticleType extends ParticleType<MagicParticleData> {

    public MagicParticleType() {
        super(false, MagicParticleData.DESERIALIZER);
    }

    @Override
    public Codec<MagicParticleData> codec() {
        return RecordCodecBuilder.create((magicParticleDataInstance) -> magicParticleDataInstance.group(
                Codec.FLOAT.fieldOf("r").forGetter((magicParticleData) -> magicParticleData.getR()),
                Codec.FLOAT.fieldOf("g").forGetter((magicParticleData) -> magicParticleData.getG()),
                Codec.FLOAT.fieldOf("b").forGetter((magicParticleData) -> magicParticleData.getB()),
                Codec.FLOAT.fieldOf("scale").forGetter((magicParticleData) -> magicParticleData.getScale()),
                Codec.INT.fieldOf("lifeTime").forGetter((magicParticleData -> magicParticleData.getLifeTime())))
                .apply(magicParticleDataInstance, MagicParticleData::new));
    }
}
