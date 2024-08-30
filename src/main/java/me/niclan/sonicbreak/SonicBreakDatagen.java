package me.niclan.sonicbreak;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.StairsBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class SonicBreakDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(BlockTagProvider::new);
    }

    private static class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public BlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(WrapperLookup lookup) {
            getOrCreateTagBuilder(SonicBreak.SONIC_BOOM_IMMUNE_START).addOptionalTag(BlockTags.WITHER_IMMUNE);

            getOrCreateTagBuilder(SonicBreak.SONIC_BOOM_IMMUNE_MIDDLE)
                    .addOptionalTag(SonicBreak.SONIC_BOOM_IMMUNE_START)
                    .addOptionalTag(ConventionalBlockTags.STORAGE_BLOCKS)
                    .addOptionalTag(BlockTags.DRAGON_IMMUNE)
                    .add(Registries.BLOCK.streamEntries()  // wonky way of adding all deepslate / tuff blocks
                                         .filter(ref -> ref.matches(
                                                 key -> key.getValue()
                                                           .getPath()
                                                           .contains("deepslate") ||
                                                        key.getValue()
                                                           .getPath()
                                                           .contains("tuff")))
                                         .map(ref -> ref.getKey().orElseThrow())
                                         .toList());

            getOrCreateTagBuilder(SonicBreak.SONIC_BOOM_IMMUNE_END)
                    .addOptionalTag(SonicBreak.SONIC_BOOM_IMMUNE_MIDDLE)
                    .addOptionalTag(ConventionalBlockTags.STONES)
                    .addOptionalTag(ConventionalBlockTags.COBBLESTONES)
                    .addOptionalTag(ConventionalBlockTags.ORES)
                    .addOptionalTag(BlockTags.DAMPENS_VIBRATIONS);
        }
    }
}
