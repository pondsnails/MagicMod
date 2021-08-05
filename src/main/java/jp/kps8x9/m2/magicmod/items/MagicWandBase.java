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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MagicWandBase extends BowItem {
    boolean isUsing;
    boolean INBEPRBool;
    BasicParticleType particleType = ParticleTypes.ENCHANT;
    Vector3d particlePos;
    MagicParticle particles;
    SuperMagicParticle superParticles;
    Vector3d superMagicCirclePos;
    BooleanArrayList timerBool;
    Vector3d beamEndPos;
    Vector3d explodePos;
    int particleCount;

    public MagicWandBase(Properties properties) {
        super(properties);
        particleCount = 0;
        INBEPRBool = false;
        //timerBoolはsummonParticleの際に行われる処理の終了確認の為のフィールドであってリストである必要はない。
        timerBool = new BooleanArrayList();
        timerBool.add(0,true);
        timerBool.add(1,true);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        float mahoujinDistance = 3.0f;
        float superMagicDistance = 70f;
        particleCount = 0;

        isUsing = true;

        Vector3d vector = playerEntity.getLookAngle();

        //Beamの終了地点の計算
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

            if (INBEPRcount > superMagicDistance && INBEPRBool == false) {
                INBEPRBool = true;
            }
        }

        float superMagicX;
        float superMagicZ;
        if (INBEPRBool) {
            superMagicX = (float) beamEndPos.x;
            superMagicZ = (float) beamEndPos.z;
        } else {
            superMagicX = (float) (playerEntity.getX() + vector.x * superMagicDistance);
            superMagicZ = (float) (playerEntity.getZ() + vector.z * superMagicDistance);
        }
        superMagicCirclePos = new Vector3d(superMagicX,playerEntity.getY() + 70,superMagicZ);

        particlePos = new Vector3d(playerEntity.getX() + vector.x*mahoujinDistance,playerEntity.getY() + playerEntity.getEyeHeight() + vector.y*mahoujinDistance,playerEntity.getZ() + vector.z*mahoujinDistance);


        if (INBEPRcount > superMagicDistance) {
            INBEPRBool = true;
        }

        //Beamの爆発地点の計算
        boolean isNotExplodePosReady = true;
        int INEPRCount = 0;
        while (isNotExplodePosReady) {
            INEPRCount++;
            BlockPos tentativePos = new BlockPos(superMagicCirclePos.x,superMagicCirclePos.y - INEPRCount,superMagicCirclePos.z);
            if (!world.isEmptyBlock(tentativePos)) {
                explodePos = new Vector3d(superMagicCirclePos.x,superMagicCirclePos.y - INEPRCount,superMagicCirclePos.z);
                isNotExplodePosReady = false;
            }
            if ((superMagicCirclePos.y - INEPRCount) < 1){
                explodePos = new Vector3d(superMagicCirclePos.x,0,superMagicCirclePos.z);
                isNotExplodePosReady = false;
            }
        }

        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundCategory.PLAYERS, 1.0F, 1.0F);

        if (particles != null) {
            particles.remove();
        }
        particles = summonMagicParticle(particlePos);

        particles.setPlayerEntity(playerEntity);
        particles.setKeepAlive(true);

        String message = particles.toString();

        System.out.println(message);

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
        int time = this.particleCount;
        System.out.println("Time : " + time);
        float f = getPowerForTime(i);

        MagicBase magic = new MagicBase(playerEntity, world, itemStack);
        magic.setAttackPoint(f);

        if (!world.isClientSide) {

            if (time < 10000) {
                magic.firstMagic();
                System.out.println("First Magic was shot");
                RenderEvent.Beam beam = new RenderEvent.Beam(10,this.particlePos,this.beamEndPos,Color.black);
                world.playSound(null,beamEndPos.x,beamEndPos.y,beamEndPos.z,SoundEvents.GENERIC_EXPLODE,SoundCategory.PLAYERS, 5.0F, 5.0F);
                world.explode(null,beamEndPos.x,beamEndPos.y,beamEndPos.z,20.0F, Explosion.Mode.BREAK);

            } else if (time < 20000) {
                magic.secondMagic();
                System.out.println("Second Magic was shot");
                beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
                RenderEvent.Beam beam = new RenderEvent.Beam(20, this.superMagicCirclePos,beamEndPos,Color.black);
                world.explode(null,explodePos.x,explodePos.y,explodePos.z,40.0F, Explosion.Mode.BREAK);

            } else {
                magic.thirdMagic(superMagicCirclePos);
                System.out.println("Third Magic was shot");
                beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
                RenderEvent.Beam beam = new RenderEvent.Beam(40, this.superMagicCirclePos,beamEndPos,Color.black);
                world.explode(null,explodePos.x,explodePos.y,explodePos.z,160.0F, Explosion.Mode.BREAK);

            }

        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

        if (particles != null) {
            particles.setMagicReleased(true);
        }
        if (superParticles != null) {
            superParticles.setMagicReleased(true);
        }
    }

    public void summonExplodeParticles() {

    }

    public void summonParticle(World world,PlayerEntity playerEntity, Vector3d particlePos,Hand hand){
        Timer timer = new Timer();


            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    //20秒以上経過時
                    if (particleCount > 20000) {

                        if (timerBool.getBoolean(1)) {
                            System.out.println("SMCCount : " + particleCount);
                            superParticles.increaseSize();

                            timerBool.set(1,false);
                        }
                    }
                    //10秒から20秒までの間
                    else if (particleCount > 10000) {

                        if (timerBool.getBoolean(0)) {
                            System.out.println("SMCCount : " + particleCount);
                            if (superParticles != null) {
                                superParticles.remove();
                            }
                            superParticles = summonSuperMagicParticle(superMagicCirclePos);

                            particles.setMagicReleased(true);

                            superParticles.setKeepAlive(true);
                            timerBool.set(0,false);
                        } else {
                        }
                    }
                    //それ以外
                    else {
                        world.addParticle(particleType, particlePos.x, particlePos.y, particlePos.z, (Math.random() - 0.5) * 15, (Math.random() - 0.5) * 15, (Math.random() - 0.5) * 15);
                    }
                    particleCount++;
                    if (!isUsing) {
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
