package jp.kps8x9.m2.magicmod.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface RenderType {
    IParticleRenderType MAGIC_PARTICLE = new IParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.disableCull();
            textureManager.bind(AtlasTexture.LOCATION_PARTICLES);
            bufferBuilder.begin(7, DefaultVertexFormats.PARTICLE);
        }

        public void end(Tessellator tessellator) {
            tessellator.end();
        }

        public String toString() {
            return "MAGIC_PARTICLE";
        }
    };
}
