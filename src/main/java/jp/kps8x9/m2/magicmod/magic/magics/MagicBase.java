package jp.kps8x9.m2.magicmod.magic.magics;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MagicBase {
    protected World world;
    protected Vector3d particlePos;
    protected Vector3d beamEndPos;
    protected Vector3d explodePos;
    protected Vector3d superMagicCirclePos;

    public MagicBase() {}

    public void firstMagic(Vector3d point,float diameter) {

    }

    public void secondMagic(Vector3d point,float diameter) {

    }

    public void thirdMagic(Vector3d point,float diameter) {

    }

    public void setField(World world,Vector3d particlePos,Vector3d beamEndPos,Vector3d explodePos,Vector3d superMagicCirclePos) {
        this.world = world;
        this.particlePos = particlePos;
        this.beamEndPos = beamEndPos;
        this.explodePos = explodePos;
        this.superMagicCirclePos = superMagicCirclePos;
    }
}