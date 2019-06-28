package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
<<<<<<< Updated upstream
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.entity.EntityLivingBase;
=======
>>>>>>> Stashed changes
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAIDragonWhistle extends EntityAIDragonBase {

    public EntityAIDragonWhistle(EntityTameableDragon dragon) {
        super(dragon);
        setMutexBits(0);
    }

    @Override
    public boolean shouldExecute() {
        return dragon.getOwner() != null && dragon.getControllingPlayer() == null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return dragon.getControllingPlayer() == null; // theres a noPath check here before
    }

    @Override
    public void updateTask() {
        DMUtils.getLogger().info("whistle state " + dragon.getWhistleState());
        DMUtils.getLogger().info("fire support " + dragon.firesupport());

        if (dragon.firesupport() && dragon.getOwner() != null && dragon.isUsingBreathWeapon()) {
            dragon.getNavigator().clearPath();
            Vec3d dragonEyePos = dragon.getPositionVector().addVector(0, dragon.getEyeHeight(), 0);
            Vec3d lookDirection = dragon.getOwner().getLook(1.0F);
            Vec3d endOfLook = dragonEyePos.addVector(lookDirection.x, lookDirection.y, lookDirection.z);
            dragon.getLookHelper().setLookPosition(endOfLook.x, endOfLook.y, endOfLook.z,
                    90, 120);
            if (dragon.getOwner() instanceof EntityPlayer)
                dragon.updateIntendedRideRotation((EntityPlayer) dragon.getOwner());
        }
    }


    public boolean followPlayerFlying(EntityLivingBase entityLivingBase) {
        BlockPos midPoint = entityLivingBase.getPosition();
        double x = midPoint.getX();
        double y = midPoint.getY();
        double z = midPoint.getZ();
        boolean isMoving = entityLivingBase.motionX != 0 && entityLivingBase.motionY != 0 && entityLivingBase.motionZ != 0;
        double offset = 16D;
        x = midPoint.getX() + 0.5 - 12;
        y = midPoint.getY() + 0.5 + 24;
        z = midPoint.getZ() + 0.5 - offset;
        return dragon.getNavigator().tryMoveToXYZ(x, y, z, 2);
    }

    public boolean comeToPlayerFlying(EntityLivingBase owner) {
        float dist = dragon.getDistance(owner);
        if(dist <= 5) {
            dragon.inAirTicks = 0;
            dragon.setFlying(false);
            if(!dragon.isFlying()) {
                dragon.setnothing(true);
            }
        }

        if(dragon.getControllingPlayer() != null) {
            return false;
        }

        if(!dragon.isFlying() && dist >= 5) {
            dragon.liftOff();
        }

        if(dragon.isFlying()) {
            return dragon.getNavigator().tryMoveToXYZ(owner.getPosition().getX() + 2, owner.getPosition().getY() - 1, owner.getPosition().getZ(), 1);
        } else {
            return false;
        }

    }

    public boolean circleTarget2(BlockPos target, float height, float radius, float speed, boolean direction, float offset, float moveSpeedMultiplier) {
        int directionInt = direction ? 1 : -1;
        return dragon.getNavigator().tryMoveToXYZ(
                target.getX() + radius * Math.cos(directionInt * dragon.ticksExisted * 0.5 * speed / radius + offset),
                30 + target.getY(), // DragonMountsConfig.dragonFlightHeight
                target.getZ() + radius * Math.sin(directionInt * dragon.ticksExisted * 0.5 * speed / radius + offset),
                speed * moveSpeedMultiplier);
    }

<<<<<<< Updated upstream
    public boolean circleTarget1(BlockPos midPoint) {
        if(dragon.getControllingPlayer() != null) {
            return false;
        }

        Vec3d vec1 = dragon.getPositionVector().subtract(midPoint.getX(), midPoint.getY(), midPoint.getZ());
        Vec3d vec2 = new Vec3d(0,0,1);

        int directionInt = dragon.getRNG().nextInt(450) == 1 ? 1 : -1;
        double a = Math.acos((vec1.dotProduct(vec2)) / (vec1.lengthVector() * vec2.lengthVector()));
        double r = 0.9 * 30;  // DragonMountsConfig.dragonFlightHeight
        double x = midPoint.getX() + r * Math.cos(directionInt * a * dragon.ticksExisted * 3.5); // ()
        double y = midPoint.getY() + 45 + 0.5; // DragonMountsConfig.dragonFlightHeight
        double z = midPoint.getZ() + r * Math.sin(directionInt * a * dragon.ticksExisted * 3.5); //()

        return dragon.getNavigator().tryMoveToXYZ(x + 0.5, y + 0.5, z + 0.5, 1);
    }

=======
>>>>>>> Stashed changes
    @Override
    public void startExecuting() {
        //Commands Requiring Flight - if any is true, start flying
        if (!dragon.isFlying() && (dragon.circle() || dragon.follow() || dragon.come())) {
            dragon.liftOff();
        }

        if (dragon.isFlying()) {
            if (dragon.circle() && dragon.getOwner() != null && !this.circleTarget1(dragon.getOwner().getPosition())) {
                this.circleTarget1(dragon.getOwner().getPosition());
                dragon.setSitting(false);
            } else if (dragon.follow() && !this.followPlayerFlying(dragon.getOwner()) && dragon.getOwner() != null) {
                this.followPlayerFlying(dragon.getOwner());
                dragon.setSitting(false);
            } else if (dragon.come() && !this.comeToPlayerFlying(dragon.getOwner()) && dragon.getOwner() != null) {
                this.comeToPlayerFlying(dragon.getOwner());
                dragon.setSitting(false);
            } else if (dragon.homepos()) {
                BlockPos pos = new BlockPos(dragon);
                dragon.homePos = pos;
                dragon.hasHomePosition = true;
                ((EntityPlayer) dragon.getOwner()).sendStatusMessage(new TextComponentTranslation("dragon.command.new_home", dragon.homePos.getX(), dragon.homePos.getY(), dragon.homePos.getZ()), true);
            } else if (dragon.nothing()) {
                dragon.setnothing(true);
            }

        } else if (dragon.sit()) {
            dragon.getAISit().setSitting(!dragon.isSitting());
            dragon.getNavigator().clearPath();
            dragon.setnothing(true);
        }
    }
}
