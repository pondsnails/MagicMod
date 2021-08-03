package jp.kps8x9.m2.magicmod.items;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import jp.kps8x9.m2.magicmod.events.RenderEvent;
import jp.kps8x9.m2.magicmod.magic.MagicBase;
import jp.kps8x9.m2.magicmod.particle.particle_data.MagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particle_data.SuperMagicParticleData;
import jp.kps8x9.m2.magicmod.particle.particles.MagicParticle;
import jp.kps8x9.m2.magicmod.particle.particles.SuperMagicParticle;
import net.minecraft.client.GameSettings;
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
import net.minecraft.util.math.vector.Vector3i;
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
    List<MagicParticle> particles;
    List<SuperMagicParticle> superParticles;
    int times;
    List<Integer> timesList;
    int superMagicCircleCount;
    Vector3d superMagicCirclePos;
    BooleanArrayList timerBool;
    Vector3d beamEndPos;

    public MagicWandBase(Properties properties) {
        super(properties);
        times = 0;
        timesList = new ArrayList<Integer>();
        particles = new ArrayList<MagicParticle>();
        superParticles = new ArrayList<SuperMagicParticle>();
        superMagicCircleCount = 0;
        timerBool = new BooleanArrayList();
        timerBool.add(0,true);
        timerBool.add(1,true);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        float mahoujinDistance = 3.0f;
        float mahoujinXDistance = 3.0F;
        float endBeamDistance = 100f;

        times++;
        for (int p = 1; p < times; p++) {
            if (!timesList.contains(p)) {
                System.out.println("This number(" + p + ") does not exist in list!!");
                times = p;
            }
        }

        timesList.add(times);
        isUsing = true;

        Vector3d vector = playerEntity.getLookAngle();

        float superMagicDistance = 100f;
        float superMagicX = (float) (playerEntity.getX() + vector.x * superMagicDistance);
        float superMagicZ = (float) (playerEntity.getZ() + vector.z * superMagicDistance);

        superMagicCirclePos = new Vector3d(superMagicX,160,superMagicZ);

        particlePos = new Vector3d(playerEntity.getX() + vector.x*mahoujinDistance,playerEntity.getY() + playerEntity.getEyeHeight() + vector.y*mahoujinDistance,playerEntity.getZ() + vector.z*mahoujinDistance);

        boolean isBeamEndPosReady = true;
        int IBEPRcount = 0;
        while (isBeamEndPosReady) {
            if (IBEPRcount++ > 100) {
                isBeamEndPosReady = false;
            }
            BlockPos tentativePos = new BlockPos((int)(playerEntity.getX() + vector.x * IBEPRcount), (int)(playerEntity.getY() + vector.y * IBEPRcount), (int)(playerEntity.getZ() + vector.z * IBEPRcount));

            if (!world.isEmptyBlock(tentativePos)) {
                beamEndPos = new Vector3d(playerEntity.getX() + vector.x * IBEPRcount,playerEntity.getY() + vector.y * IBEPRcount,playerEntity.getZ() + vector.z * IBEPRcount);
                isBeamEndPosReady = false;
                System.out.println("IBEPRCount : "+IBEPRcount);
            }
        }

        MagicParticleData magicParticleData = new MagicParticleData(0, 0, 0, 100, 5);

        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundCategory.PLAYERS, 1.0F, 1.0F);

        particles.add(summonMagicParticle(particlePos));
        for (int t = 0;t < particles.size();t++) {
            particles.get(t).setPlayerEntity(playerEntity);
            particles.get(t).setKeepAlive(true);
        }

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
                    RenderEvent.Beam beam = new RenderEvent.Beam(10,this.particlePos,this.beamEndPos,Color.blue);
                    world.playSound(null,beamEndPos.x,beamEndPos.y,beamEndPos.z,SoundEvents.GENERIC_EXPLODE,SoundCategory.PLAYERS, 10.0F, 20.0F);
                    world.addParticle(ParticleTypes.EXPLOSION,beamEndPos.x,beamEndPos.y,beamEndPos.z,0,0,0);
                } else if (i < 200) {
                    magic.thirdMagic();
                    System.out.println("Second Magic was shot");
                    beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
                    RenderEvent.Beam beam = new RenderEvent.Beam(20, this.superMagicCirclePos,beamEndPos,Color.blue);
                } else {
                    magic.fourthMagic(superMagicCirclePos);
                    System.out.println("Third Magic was shot");
                    beamEndPos = new Vector3d(superMagicCirclePos.x, 0,superMagicCirclePos.z);
                    RenderEvent.Beam beam = new RenderEvent.Beam(40, this.superMagicCirclePos,beamEndPos,Color.blue);
                }
                System.out.println("Count(second):" + i);

            }

        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BEACON_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

        for (int q = 0; q < particles.size(); q++) {
            particles.get(q).setMagicReleased(true);
        }
        for (int r = 0; r < superParticles.size(); r++) {
            superParticles.get(r).setMagicReleased(true);
        }

        particles.clear();
        superParticles.clear();
        timesList.clear();
    }

    public void summonParticle(World world,PlayerEntity playerEntity, Vector3d particlePos,Hand hand){
        Timer timer = new Timer();


            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (superMagicCircleCount > 20000) {

                        if (timerBool.getBoolean(1)) {
                            for (int r = 0; r < superParticles.size(); r++) {
                                superParticles.get(r).increaseSize();
                            }

                            timerBool.set(1,false);
                        }
                    } else if (superMagicCircleCount > 10000) {

                        if (timerBool.getBoolean(0)) {
                            superParticles.add(summonSuperMagicParticle(superMagicCirclePos));

                            timerBool.set(0,false);

                            for (int q = 0; q < particles.size(); q++) {
                                particles.get(q).setMagicReleased(true);
                            }

                            particles.clear();
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
            return;
    }

    public static float getPowerForTime(int count) {
        int power = count / 2;
        return power;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 36000;
    }
}
