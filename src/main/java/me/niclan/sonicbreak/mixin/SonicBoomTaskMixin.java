package me.niclan.sonicbreak.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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
        Box box = Box.from(center).expand(10);
        BlockPos.iterate(BlockPos.ofFloored(box.getMinPos()), BlockPos.ofFloored(box.getMaxPos())).forEach(pos -> {
            if (pos.getSquaredDistance(center) <= 9 && !serverWorld.getBlockState(pos).isIn(BlockTags.WITHER_IMMUNE)) {
                serverWorld.breakBlock(pos, true);
            }
        });
        serverWorld.playSound(
                null,
                BlockPos.ofFloored(center),
                SoundEvent.of(SoundEvents.ENTITY_WITHER_BREAK_BLOCK.getId()),
                SoundCategory.HOSTILE
                             );
    }
}
