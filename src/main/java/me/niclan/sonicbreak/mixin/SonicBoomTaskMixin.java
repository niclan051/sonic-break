package me.niclan.sonicbreak.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.niclan.sonicbreak.SonicBreak;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug
@Mixin(SonicBoomTask.class)
public abstract class SonicBoomTaskMixin {
    @Inject(method = "method_43265", at = @At(value = "INVOKE",
                                              target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles" +
                                                       "(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"))
    private static void breakBlocks(WardenEntity wardenEntity, ServerWorld serverWorld, LivingEntity target,
                                    CallbackInfo ci, @Local(ordinal = 3) Vec3d center) {
        double radius = 3;
        Box box = Box.from(center).expand(radius);
        BlockPos.iterate(BlockPos.ofFloored(box.getMinPos()), BlockPos.ofFloored(box.getMaxPos())).forEach(pos -> {
            if (pos.getSquaredDistance(center) <= radius * radius &&
                !serverWorld.getBlockState(pos).isIn(SonicBreak.SONIC_BOOM_IMMUNE)) {
                serverWorld.breakBlock(pos, true);
            }
        });
    }

    @Inject(method = "method_43265", at = @At(value = "INVOKE",
                                              target = "Lnet/minecraft/entity/mob/WardenEntity;playSound" +
                                                       "(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private static void playBreakSound(WardenEntity wardenEntity, ServerWorld serverWorld, LivingEntity target,
                                       CallbackInfo ci) {
        wardenEntity.playSound(SoundEvents.ENTITY_WITHER_BREAK_BLOCK);
    }
}
