package org.leavesmc.leaves.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.CoralClawFeature;
import net.minecraft.world.level.levelgen.feature.CoralTreeFeature;
import net.minecraft.world.level.levelgen.feature.CuboidPlacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OverlayFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomChancePlacement;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

// Powered by fabric-carpet/src/main/java/carpet/helpers/FertilizableCoral.java
public interface FertilizableCoral extends BonemealableBlock {

    boolean isEnabled();

    @Override
    default boolean isValidBonemealTarget(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state) {
        return isEnabled() && state.getValue(BaseCoralPlantTypeBlock.WATERLOGGED) && world.getFluidState(pos.above()).is(FluidTags.WATER);
    }

    @Override
    default boolean isBonemealSuccess(@NotNull Level world, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        ((ServerLevel) world).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 8, 0.3, 0.5, 0.3, 0.0);
        return random.nextFloat() < 0.15D;
    }

    @Override
    default void performBonemeal(@NotNull ServerLevel worldIn, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState blockUnder) {
        int variant = random.nextInt(3);
        MapColor color = blockUnder.getMapColor(worldIn, pos);
        BlockState properBlock = blockUnder;
        HolderSet.Named<Block> coralBlocks = worldIn.registryAccess().lookupOrThrow(Registries.BLOCK).getOrThrow(BlockTags.CORAL_BLOCKS);
        for (Holder<Block> block : coralBlocks) {
            properBlock = block.value().defaultBlockState();
            if (properBlock.getMapColor(worldIn, pos) == color) {
                break;
            }
        }

        Feature blockFeature = new OverlayFeature(HolderSet.direct(
            PlacementUtils.inlinePlaced(new SimpleBlockFeature(BlockStateProvider.of(properBlock))),
            PlacementUtils.inlinePlaced(worldIn.registryAccess().lookupOrThrow(Registries.FEATURE).getOrThrow(AquaticFeatures.CORAL_BLOCK_DECORATION))
        ));
        BlockPredicateFilter coralAllowed = BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
            BlockPredicate.anyOf(BlockPredicate.matchesBlocks(Blocks.WATER), BlockPredicate.matchesTag(BlockTags.CORALS)),
            BlockPredicate.matchesBlocks(Direction.UP, Blocks.WATER)
        ));
        Holder<PlacedFeature> coralBlock = PlacementUtils.inlinePlaced(blockFeature, coralAllowed);
        Holder<PlacedFeature> coral = switch (variant) {
            case 0 -> PlacementUtils.inlinePlaced(new CoralClawFeature(coralBlock));
            case 1 -> PlacementUtils.inlinePlaced(new CoralTreeFeature(coralBlock));
            default -> PlacementUtils.inlinePlaced(
                blockFeature,
                OffsetPlacement.vertical(UniformInt.of(-3, -1)),
                new CuboidPlacement(UniformInt.of(3, 5), UniformInt.of(3, 5), false, false),
                new RandomChancePlacement(0.9F),
                coralAllowed
            );
        };
        worldIn.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_NONE);

        if (!coral.value().place(worldIn, worldIn.getChunkSource().getGenerator(), random, pos)) {
            worldIn.setBlock(pos, blockUnder, 3);
        } else {
            if (worldIn.random.nextInt(10) == 0) {
                BlockPos randomPos = pos.offset(worldIn.random.nextInt(16) - 8, worldIn.random.nextInt(8), worldIn.random.nextInt(16) - 8);
                if (coralBlocks.contains(worldIn.getBlockState(randomPos).getBlockHolder())) {
                    worldIn.setBlock(randomPos, Blocks.WET_SPONGE.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
    }
}
