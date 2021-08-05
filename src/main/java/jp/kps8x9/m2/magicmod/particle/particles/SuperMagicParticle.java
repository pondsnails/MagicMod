package jp.kps8x9.m2.magicmod.particle.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import jp.kps8x9.m2.magicmod.particle.particle_data.SuperMagicParticleData;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import static jp.kps8x9.m2.magicmod.particle.RenderType.MAGIC_PARTICLE;

public class SuperMagicParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite sprites;
    private boolean initialized;
    private Quaternion firstQuaternion;
    protected float n;
    protected boolean sized;
    protected boolean isMagicReleased;
    protected boolean increased;
    protected int count;
    boolean waving = false;
    protected boolean keepAlive;
    protected boolean willDisplay;

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float floatValue) {
        Vector3d vector3d = renderInfo.getPosition();
        float f = (float)(MathHelper.lerp(floatValue, this.xo, this.x) - vector3d.x());
        float f1 = (float)(MathHelper.lerp(floatValue, this.yo, this.y) - vector3d.y());
        float f2 = (float)(MathHelper.lerp(floatValue, this.zo, this.z) - vector3d.z());

        if (!initialized) {
            firstQuaternion = new Quaternion(0.5F,0.5F,-0.5F,0.5F);

            initialized = true;
        }

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.transform(firstQuaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getQuadSize(floatValue) / n;

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(firstQuaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }


        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int j = this.getLightColor(floatValue);
        if (willDisplay) {
            vertexBuilder.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            vertexBuilder.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            vertexBuilder.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            vertexBuilder.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        }
    }

    public SuperMagicParticle(ClientWorld world, double v1, double v2, double v3, double v4, double v5, double v6, SuperMagicParticleData data, IAnimatedSprite sprite) {
        super(world, v1, v2, v3, v4, v5, v6);
        this.sprites = sprite;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.rCol = data.getR();
        this.gCol = data.getG();
        this.bCol = data.getB();
        this.quadSize = (float) (0.75 * data.getScale());
        int i = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.lifetime = data.getLifeTime();
        this.initialized = false;
        this.increased = false;
        this.setSpriteFromAge(sprite);
        this.n = 20;
        this.sized = true;
        this.count = 0;
        this.keepAlive = false;
        this.willDisplay = true;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (increased && n > 0.2) {
            n *= 0.9;
        } else if (increased){
            increased = false;
            waving = true;
        }

        count++;
        if (waving) {
            if (count % 500 < 250) {
                n += 0.0001;
            } else {
                n -= 0.0001;
            }
        } else {
            if (count % 50 < 25) {
                n += 0.001;
            } else {
                n -= 0.001;
            }
        }

        if (isMagicReleased && this.age + 5 >= this.lifetime) {
            if (n < 10) {
                n++;
            } else {
                this.remove();
            }
            return;
        }

        if (n > 1 && sized) {
            n -= 0.5;
        } else if (n < 1){
            sized = false;
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }

        if (this.age + 5 > this.lifetime){
            if (keepAlive) {
                lifetime += 6;
            } else {
                n = 5 - (this.lifetime - this.age);
            }
        }
    }

    public void setMagicReleased(boolean magicReleased) {
        isMagicReleased = magicReleased;
        this.age = this.lifetime - 10;
        this.keepAlive = false;

    }

    public void increaseSize() {
        this.increased = true;
    }

    public void changeLifeTime(int lifetime) {
        this.lifetime = lifetime;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public void setWillDisplay(boolean willDisplay) {
        this.willDisplay = willDisplay;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return MAGIC_PARTICLE;
    }
}
