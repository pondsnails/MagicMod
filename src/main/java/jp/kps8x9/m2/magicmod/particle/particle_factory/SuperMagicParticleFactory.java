package jp.kps8x9.m2.magicmod.particle.particle_factory;

import jp.kps8x9.m2.magicmod.particle.particle_data.SuperMagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particles.SuperMagicParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class SuperMagicParticleFactory implements IParticleFactory<SuperMagicParticleData> {
    private final IAnimatedSprite sprites;

    public SuperMagicParticleFactory(IAnimatedSprite sprites) {
        this.sprites = sprites;
    }

    @Nullable
    @Override
    public Particle createParticle(SuperMagicParticleData data, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        SuperMagicParticle particle = new SuperMagicParticle(world,x,y,z,xSpeed,ySpeed,zSpeed,data,sprites);
        particle.setSpriteFromAge(sprites);
        return particle;

    }
}
