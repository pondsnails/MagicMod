package jp.kps8x9.m2.magicmod.particle.particle_factory;

import jp.kps8x9.m2.magicmod.particle.particle_data.MagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particles.MagicParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class MagicParticleFactory implements IParticleFactory<MagicParticleData> {
    private final IAnimatedSprite sprites;

    public MagicParticleFactory(IAnimatedSprite sprites) {
        this.sprites = sprites;
    }

    @Nullable
    @Override
    public Particle createParticle(MagicParticleData data, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        MagicParticle particle = new MagicParticle(world,x,y,z,xSpeed,ySpeed,zSpeed,data,sprites);
        particle.setSpriteFromAge(sprites);
        return particle;
    }


}
