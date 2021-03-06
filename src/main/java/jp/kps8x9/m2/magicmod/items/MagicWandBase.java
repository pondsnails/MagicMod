package jp.kps8x9.m2.magicmod.items;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import jp.kps8x9.m2.magicmod.events.RenderEvent;
import jp.kps8x9.m2.magicmod.magic.magics.MagicBase;
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
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class MagicWandBase extends BowItem {
    boolean isUsing;
    boolean INBEPRBool;
    boolean processed;
    BasicParticleType particleType = ParticleTypes.ENCHANT;
    Vector3d particlePos;
    MagicParticle particles;
    SuperMagicParticle superParticles;
    Vector3d superMagicCirclePos;
    BooleanArrayList timerBool;
    Vector3d beamEndPos;
    Vector3d explodePos;
    int particleCount;
    MagicBase magic;

    public MagicWandBase(Properties properties) {
        super(properties);
        particleCount = 0;
        INBEPRBool = false;
        processed = true;
        magic = new MagicBase();
        timerBool = new BooleanArrayList();
        timerBool.add(0,true);
        timerBool.add(1,true);
        timerBool.add(2,true);
        particles = null;
        superParticles = null;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        float magicCircleDistance = 3.0f;
        float superMagicDistance = 70f;
        particleCount = 0;

        isUsing = true;

        Vector3d vector = playerEntity.getLookAngle();

        //Beam????????????????????????
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

        //superMagicParticle???????????????
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

        particlePos = new Vector3d(playerEntity.getX() + vector.x*magicCircleDistance,playerEntity.getY() + playerEntity.getEyeHeight() + vector.y*magicCircleDistance,playerEntity.getZ() + vector.z*magicCircleDistance);


        if (INBEPRcount > superMagicDistance) {
            INBEPRBool = true;
        }

        //Beam????????????????????????
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
        summonParticle(world, playerEntity, particlePos, hand);
        playerEntity.getCooldowns().addCooldown(this, (int) (0.3 * 20));

        return super.use(world, playerEntity, hand);
    }

    public MagicParticle summonMagicParticle(Vector3d particlePos) {
        return (MagicParticle) Minecraft.getInstance().particleEngine.createParticle(MagicParticleData.NORMAL_MAGIC_PARTICLE, particlePos.x, particlePos.y, particlePos.z, 0D, 0D, 0D);
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
        float f = getPowerForTime(i);
        this.magic.setField(world,particlePos,beamEndPos,explodePos,superMagicCirclePos);

        if (!world.isClientSide) {

            if (time < 10000) {
                magic.firstMagic(beamEndPos,5);
                System.out.println("First Magic was shot");

            } else if (time < 20000) {
                magic.secondMagic(explodePos,10);
                System.out.println("Second Magic was shot");

            } else {
                magic.thirdMagic(explodePos,20);
                System.out.println("Third Magic was shot");

            }

        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
    }

    public void summonExplodeParticles() {

    }

    public void summonParticle(World world,PlayerEntity playerEntity, Vector3d particlePos,Hand hand){
        Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    //20?????????????????? superParticle?????????????????????
                    if (particleCount > 20000) {

                        if (timerBool.getBoolean(2)) {
                            if (superParticles instanceof SuperMagicParticle) {
                                superParticles.increaseSize();
                            }

                            timerBool.set(2,false);
                        }
                    }

                    //10?????????????????? superParticle??????????????????
                    else if (particleCount > 10000) {

                        if (!timerBool.getBoolean(1)) {
                            if (superParticles instanceof SuperMagicParticle) {
                                superParticles.setWillDisplay(true);
                                superParticles.setKeepAlive(true);
                                superParticles.setSized(true);
                            }
                        }
                    }
                    //9.5?????????????????? superParticle?????????
                    else if (particleCount > 9500) {

                        if (timerBool.getBoolean(1)) {
                            timerBool.set(1,false);
                            if (superParticles instanceof SuperMagicParticle){
                                superParticles.remove();
                            }
                            superParticles = summonSuperMagicParticle(superMagicCirclePos);

                            if (particles instanceof MagicParticle) {
                                particles.setMagicReleased(true);
                            }

                        }
                    }
                    //????????????
                    else {

                        //???????????????????????????MagicParticle?????????
                        if (timerBool.getBoolean(0)) {
                            if (particles instanceof MagicParticle) {
                                particles.remove();
                            }
                            if (superParticles instanceof SuperMagicParticle) {
                                superParticles.setMagicReleased(true);
                            }

                            particles = summonMagicParticle(particlePos);

                            if (particles instanceof MagicParticle) {
                                particles.setPlayerEntity(playerEntity);
                                particles.setKeepAlive(true);
                                particles.setWillDisplay(true);
                            }

                            timerBool.set(0,false);
                        } else

                        //??????????????????????????????????????????????????????????????????????????????????????????????????????
                        {
                            world.addParticle(particleType, particlePos.x, particlePos.y, particlePos.z, (Math.random() - 0.5) * 15, (Math.random() - 0.5) * 15, (Math.random() - 0.5) * 15);
                        }
                    }
                    particleCount++;

                    if (!isUsing) {
                        timerBool.set(0,true);
                        timerBool.set(1,true);
                        timerBool.set(2,true);
                        if (particles instanceof MagicParticle) {
                            particles.setMagicReleased(true);
                        }
                        if (superParticles instanceof SuperMagicParticle) {
                            superParticles.setMagicReleased(true);
                        }
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
