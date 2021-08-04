package jp.kps8x9.m2.magicmod.items;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import jp.kps8x9.m2.magicmod.events.RenderEvent;
import jp.kps8x9.m2.magicmod.magic.MagicBase;
import jp.kps8x9.m2.magicmod.particle.particle_data.MagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particle_data.SuperMagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particles.MagicParticle;
import jp.kps8x9.m2.magicmod.particle.particles.SuperMagicParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MagicWandBase extends BowItem {
    boolean isUsing;
    BasicParticleType particleType = ParticleTypes.ENCHANT;
    Vector3d particlePos;
    MagicParticle particles;
    SuperMagicParticle superParticles;
    int times;
    List<Integer> timesList;
    int superMagicCircleCount;
    Vector3d superMagicCirclePos;
    BooleanArrayList timerBool;
    Vector3d beamEndPos;
    Vector3d explodePos;

    public MagicWandBase(Properties properties) {
        super(properties);
        times = 0;
        timesList = new ArrayList<>();
        superMagicCircleCount = 0;
        timerBool = new BooleanArrayList();
        timerBool.add(0,true);
        timerBool.add(1,true);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        float mahoujinDistance = 3.0f;

        timesList.add(times);
        isUsing = true;

        Vector3d vector = playerEntity.getLookAngle();

        float superMagicDistance = 100f;
        float superMagicX = (float) (playerEntity.getX() + vector.x * superMagicDistance);
        float superMagicZ = (float) (playerEntity.getZ() + vector.z * superMagicDistance);

        superMagicCirclePos = new Vector3d(superMagicX,160,superMagicZ);

        particlePos = new Vector3d(playerEntity.getX() + vector.x*mahoujinDistance,playerEntity.getY() + playerEntity.getEyeHeight() + vector.y*mahoujinDistance,playerEntity.getZ() + vector.z*mahoujinDistance);

        boolean isNotBeamEndPosReady = true;
        int INBEPRcount = 20;
        while (isNotBeamEndPosReady) {
            INBEPRcount++;
            BlockPos tentativePos = new BlockPos((int)(playerEntity.getX() + vector.x * INBEPRcount), (int)(playerEntity.getY() + vector.y * INBEPRcount), (int)(playerEntity.getZ() + vector.z * INBEPRcount));

            if (!world.isEmptyBlock(tentativePos)) {
                beamEndPos = new Vector3d(playerEntity.getX() + vector.x * INBEPRcount,playerEntity.getY() + vector.y * INBEPRcount,playerEntity.getZ() + vector.z * INBEPRcount);
                isNotBeamEndPosReady = false;
            }
            if (INBEPRcount > 400) {
                beamEndPos = new Vector3d(playerEntity.getX() + vector.x * INBEPRcount,playerEntity.getY() + vector.y * INBEPRcount,playerEntity.getZ() + vector.z * INBEPRcount);
                isNotBeamEndPosReady = false;
            }
        }

        boolean isNotExplodePosReady = true;
        int INEPRCount = 0;
        while (isNotExplodePosReady) {
            INEPRCount++;
            BlockPos tentativePos = new BlockPos(beamEndPos.x,superMagicCirclePos.y - INEPRCount,beamEndPos.z);
            if (!world.isEmptyBlock(tentativePos)) {
                explodePos = new Vector3d(beamEndPos.x,superMagicCirclePos.y - INEPRCount,beamEndPos.z);
                isNotExplodePosReady = false;
            }
            if ((superMagicCirclePos.y - INEPRCount) < 1){
                explodePos = new Vector3d(beamEndPos.x,0,beamEndPos.z);
                isNotExplodePosReady = false;
            }
        }

        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundCategory.PLAYERS, 1.0F, 1.0F);

        if (particles != null) {
            particles.setMagicReleased(true);
            particles.remove();
        }
        particles = summonMagicParticle(particlePos);

        particles.setPlayerEntity(playerEntity);
        particles.setKeepAlive(true);

        summonParticle(world, playerEntity, particlePos, hand);

        playerEntity.getCooldowns().addCooldown(this, 5 * 20);

        return super.use(world, playerEntity, hand);
    }

    public MagicParticle summonMagicParticle(Vector3d particlePos) {
        return (MagicParticle) Minecraft.getInstance().particleEngine.createParticle(MagicParticleData.NOT_REVERSED_NORMAL_LIGHT_BLUE_MAGIC_PARTICLE, particlePos.x, particlePos.y, particlePos.z, 0D, 0D, 0D);
    }

    public SuperMagicParticle summonSuperMagicParticle(Vector3d particlePos) {
        return (SuperMagicParticle) Minecraft.getInstance().particleEngine.createParticle(SuperMagicParticleData.SUPER_MAGIC_CIRCLE, particlePos.x, particlePos.y, particlePos.z, 0D, 0D,0D);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int count) {
        isUsing = false;
        PlayerEntity playerEntity = (PlayerEntity) livingEntity;
        int useDuration = this.getUseDuration(itemStack);

        int i = useDuration - count;
        float f = getPowerForTime(i);

        MagicBase magic = new MagicBase(playerEntity, world, itemStack);
        magic.setAttackPoint(f);

        if (!((double) f < 0.3D)) {
            if (!world.isClientSide) {

                if (i < 100) {
                    magic.secondMagic();
                    System.out.println("First Magic was shot");
                    RenderEvent.Beam beam = new RenderEvent.Beam(10,this.particlePos,this.beamEndPos,Color.black);
                    world.playSound(null,beamEndPos.x,beamEndPos.y,beamEndPos.z,SoundEvents.GENERIC_EXPLODE,SoundCategory.PLAYERS, 5.0F, 5.0F);
                    world.explode(null,beamEndPos.x,beamEndPos.y,beamEndPos.z,20.0F, Explosion.Mode.BREAK);

                } else if (i < 200) {
                    magic.thirdMagic();
                    System.out.println("Second Magic was shot");
                    beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
                    RenderEvent.Beam beam = new RenderEvent.Beam(20, this.superMagicCirclePos,beamEndPos,Color.black);
                    world.explode(null,explodePos.x,explodePos.y,explodePos.z,160.0F, Explosion.Mode.BREAK);


                } else {
                    magic.fourthMagic(superMagicCirclePos);
                    System.out.println("Third Magic was shot");
                    beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
                    RenderEvent.Beam beam = new RenderEvent.Beam(40, this.superMagicCirclePos,beamEndPos,Color.black);
                    world.explode(null,explodePos.x,explodePos.y,explodePos.z,320.0F, Explosion.Mode.BREAK);
                }
                System.out.println("Count(second):" + i);
                System.out.println("ExplodePos : " + explodePos);

            }

        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

        if (particles != null) {
            particles.setMagicReleased(true);
        }
        if (superParticles != null) {
            superParticles.setMagicReleased(true);
        }
        timesList.clear();
    }

    public void summonExplodeParticles() {

    }

    public void summonParticle(World world,PlayerEntity playerEntity, Vector3d particlePos,Hand hand){
        Timer timer = new Timer();


            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (superMagicCircleCount > 20000) {

                        if (timerBool.getBoolean(1)) {
                            superParticles.increaseSize();

                            timerBool.set(1,false);
                        }
                    } else if (superMagicCircleCount > 10000) {

                        if (timerBool.getBoolean(0)) {
                            if (superParticles != null) {
                                superParticles.setMagicReleased(true);
                                superParticles.remove();
                                System.out.println("SuperMagicParticle has been removed");
                            }
                            superParticles = summonSuperMagicParticle(superMagicCirclePos);
                            System.out.println("Summon SuperMagicParticle");

                            timerBool.set(0,false);
                            particles.setMagicReleased(true);
                        }
                    } else {
                        world.addParticle(particleType, particlePos.x, particlePos.y, particlePos.z, (Math.random() - 0.5) * 15, (Math.random() - 0.5) * 15, (Math.random() - 0.5) * 15);

                    }

                    superMagicCircleCount++;
                    if (!isUsing) {
                        superMagicCircleCount = 0;
                        timerBool.set(0,true);
                        timerBool.set(1,true);
                        timer.cancel();

                    }
                }
            }, 0, 1);
    }

    public static float getPowerForTime(int count) {
        return count / 2;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 36000;
    }
}
