package me.niclan.sonicbreak;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class SonicBreak implements ModInitializer {
    public static final String MOD_ID = "sonic-break";
    public static final TagKey<Block> SONIC_BOOM_IMMUNE = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, "sonic_boom_immune"));
    @Override
    public void onInitialize() {

    }
}
