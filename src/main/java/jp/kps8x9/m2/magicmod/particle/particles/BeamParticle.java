package jp.kps8x9.m2.magicmod.particle.particles;

import jp.kps8x9.m2.magicmod.particle.RenderType;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

public class BeamParticle extends SpriteTexturedParticle {
    public BeamParticle(ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
        super(world, x, y, z, xv, yv, zv);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return RenderType.MAGIC_PARTICLE;
    }
}
