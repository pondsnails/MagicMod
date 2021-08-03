package jp.kps8x9.m2.magicmod.particle.particle_data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import jp.kps8x9.m2.magicmod.init.ParticleInit;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.util.Locale;
import java.util.UUID;

public class SuperMagicParticleData implements IParticleData {
    public static final SuperMagicParticleData SUPER_MAGIC_CIRCLE = new SuperMagicParticleData(65, 191, 240, 50F, 10);

    private UUID entity;

    public static final IDeserializer<SuperMagicParticleData> DESERIALIZER = new IDeserializer<SuperMagicParticleData>() {
        public SuperMagicParticleData fromCommand(ParticleType<SuperMagicParticleData> typeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float)reader.readDouble();
            reader.expect(' ');
            float f1 = (float)reader.readDouble();
            reader.expect(' ');
            float f2 = (float)reader.readDouble();
            reader.expect(' ');
            float f3 = (float)reader.readDouble();
            reader.expect(' ');
            int f4 = (int)reader.readInt();
            return new SuperMagicParticleData(f, f1, f2, f3, f4);
        }

        public SuperMagicParticleData fromNetwork(ParticleType<SuperMagicParticleData> typeIn, PacketBuffer buffer) {
            return new SuperMagicParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt());
        }
    };

    private float r;
    private float g;
    private float b;
    private float scale;
    private int lifeTime;

    public SuperMagicParticleData(float r, float g, float b, float diameter, int lifeTime) {
        this.r = b;
        this.g = g;
        this.b = r;
        this.scale = diameter;
        this.lifeTime = lifeTime;
    }

    @Override
    public ParticleType<SuperMagicParticleData> getType() {
        return ParticleInit.superMagicParticle.get();
    }

    @Override
    public void writeToNetwork(PacketBuffer buffer) {
        buffer.writeFloat(this.r);
        buffer.writeFloat(this.g);
        buffer.writeFloat(this.b);
        buffer.writeFloat(this.scale);
        buffer.writeInt(this.lifeTime);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %d", ParticleInit.superMagicParticle.get(), this.r, this.g, this.b, this.scale,this.lifeTime);
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getScale() {
        return scale;
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
