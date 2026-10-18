package org.leavesmc.leaves.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

import static java.util.Map.entry;

public class WoolUtils {
    private static final Map<Block, DyeColor> WOOL_BLOCK_TO_DYE = Map.ofEntries(
        entry(wool(DyeColor.WHITE), DyeColor.WHITE),
        entry(wool(DyeColor.ORANGE), DyeColor.ORANGE),
        entry(wool(DyeColor.MAGENTA), DyeColor.MAGENTA),
        entry(wool(DyeColor.LIGHT_BLUE), DyeColor.LIGHT_BLUE),
        entry(wool(DyeColor.YELLOW), DyeColor.YELLOW),
        entry(wool(DyeColor.LIME), DyeColor.LIME),
        entry(wool(DyeColor.PINK), DyeColor.PINK),
        entry(wool(DyeColor.GRAY), DyeColor.GRAY),
        entry(wool(DyeColor.LIGHT_GRAY), DyeColor.LIGHT_GRAY),
        entry(wool(DyeColor.CYAN), DyeColor.CYAN),
        entry(wool(DyeColor.PURPLE), DyeColor.PURPLE),
        entry(wool(DyeColor.BLUE), DyeColor.BLUE),
        entry(wool(DyeColor.BROWN), DyeColor.BROWN),
        entry(wool(DyeColor.GREEN), DyeColor.GREEN),
        entry(wool(DyeColor.RED), DyeColor.RED),
        entry(wool(DyeColor.BLACK), DyeColor.BLACK)
    );

    private static Block wool(DyeColor color) {
        return BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(color.getName() + "_wool"));
    }

    public static DyeColor getWoolColorAtPosition(Level worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        return WOOL_BLOCK_TO_DYE.get(state.getBlock());
    }
}
