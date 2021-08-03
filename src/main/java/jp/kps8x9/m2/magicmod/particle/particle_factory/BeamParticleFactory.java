package jp.kps8x9.m2.magicmod.particle.particle_factory;

import jp.kps8x9.m2.magicmod.particle.particles.BeamParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import javax.annotation.Nullable;

public class BeamParticleFactory implements IParticleFactory<BasicParticleType> {
    private final IAnimatedSprite sprite;

    public BeamParticleFactory(IAnimatedSprite sprite) {
        this.sprite = sprite;
    }
    @Nullable
    @Override
    public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
        BeamParticle beamParticle = new BeamParticle(world, x, y, z, xv, yv, zv);
        beamParticle.setSpriteFromAge(sprite);
        return beamParticle;
    }
}
