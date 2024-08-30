package me.niclan.sonicbreak;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class SonicBreak implements ModInitializer {
    public static final String MOD_ID = "sonic-break";
    public static final TagKey<Block> SONIC_BOOM_IMMUNE_START =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "sonic_boom_immune_start"));
    public static final TagKey<Block> SONIC_BOOM_IMMUNE_MIDDLE =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "sonic_boom_immune_middle"));
    public static final TagKey<Block> SONIC_BOOM_IMMUNE_END =
            TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "sonic_boom_immune_end"));

    @Override
    public void onInitialize() {

    }
}
