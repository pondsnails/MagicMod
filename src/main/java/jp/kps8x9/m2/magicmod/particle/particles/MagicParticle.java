package jp.kps8x9.m2.magicmod.particle.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import jp.kps8x9.m2.magicmod.particle.particle_data.MagicParticleData;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import static jp.kps8x9.m2.magicmod.particle.RenderType.MAGIC_PARTICLE;

public class MagicParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite sprites;
    private boolean initialized;
    private Quaternion firstQuaternion;
    private float i;
    private float j;
    private float k;
    private float r;
    protected float n;
    protected boolean sized;
    protected PlayerEntity playerEntity;
    protected boolean isMagicReleased;
    protected int m;
    protected int count;
    protected boolean keepAlive;
    protected boolean willDisplay;

    @Override
    public void render(IVertexBuilder vertexBuilder, ActiveRenderInfo renderInfo, float floatValue) {
        Vector3d vector3d = renderInfo.getPosition();
        float f = (float)(MathHelper.lerp(floatValue, this.xo, this.x) - vector3d.x());
        float f1 = (float)(MathHelper.lerp(floatValue, this.yo, this.y) - vector3d.y());
        float f2 = (float)(MathHelper.lerp(floatValue, this.zo, this.z) - vector3d.z());
        Quaternion quaternion;
        if (this.roll == 0.0F) {
            quaternion = renderInfo.rotation();
        } else {
            quaternion = new Quaternion(renderInfo.rotation());
            float f3 = MathHelper.lerp(floatValue, this.oRoll, this.roll);
            quaternion.mul(Vector3f.ZP.rotation(f3));
        }

        if (!initialized) {

            sized = true;
            i = quaternion.i();
            j = quaternion.j();
            k = quaternion.k();
            r = quaternion.r();
            firstQuaternion = quaternion.copy();

            initialized = true;
        }

         if (n > 1 && sized) {
            n -= 0.2;
        } else if (n == 1){

             sized = false;
         }

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.transform(quaternion);
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

    public MagicParticle(ClientWorld world, double v1, double v2, double v3, double v4, double v5, double v6, MagicParticleData data, IAnimatedSprite sprite) {
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
        this.setSpriteFromAge(sprite);
        this.n = 7;
        this.i = 1;
        this.count = 0;
        this.keepAlive = false;
        this.willDisplay = false;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        count++;

        if (count % 50 < 25) {
            n += 0.001;
        } else {
            n -= 0.001;
        }

        if (this.age++ >= this.lifetime) {
            if (keepAlive) {
                age -= 5;
            } else {
                this.remove();
            }
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

    @Override
    public IParticleRenderType getRenderType() {
        return MAGIC_PARTICLE;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setMagicReleased(boolean magicReleased) {
        this.age = this.lifetime - 5;
        this.keepAlive = false;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public void setWillDisplay(boolean willDisplay) {
        this.willDisplay = willDisplay;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }
}
