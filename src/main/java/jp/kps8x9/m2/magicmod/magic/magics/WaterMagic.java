package jp.kps8x9.m2.magicmod.magic.magics;

import jp.kps8x9.m2.magicmod.events.RenderEvent;
import jp.kps8x9.m2.magicmod.magic.element.WaterElement;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;

public class WaterMagic extends MagicBase implements WaterElement {
    @Override
    public void firstMagic(Vector3d point, float diameter) {
        RenderEvent.Beam beam = new RenderEvent.Beam(10,this.particlePos,this.beamEndPos,this.getDarkColor());
        world.playSound(null,beamEndPos.x,beamEndPos.y,beamEndPos.z, SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS, 5.0F, 5.0F);
        world.explode(null,beamEndPos.x,beamEndPos.y,beamEndPos.z,20.0F, Explosion.Mode.BREAK);
    }

    @Override
    public void secondMagic(Vector3d point, float diameter) {
        beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
        RenderEvent.Beam beam = new RenderEvent.Beam(20, this.superMagicCirclePos,beamEndPos,this.getDarkColor());
        world.explode(null,explodePos.x,explodePos.y,explodePos.z,40.0F, Explosion.Mode.BREAK);
    }

    @Override
    public void thirdMagic(Vector3d point, float diameter) {
        beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
        RenderEvent.Beam beam = new RenderEvent.Beam(40, this.superMagicCirclePos,beamEndPos,this.getDarkColor());
        world.explode(null,explodePos.x,explodePos.y,explodePos.z,240.0F, Explosion.Mode.BREAK);
    }
}
