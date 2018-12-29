package ansraer.cotton.blocks;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.ItemGroup;

import static ansraer.cotton.blocks.CottonBlockHelper.registerBlock;

public class AutoTreeBlocks extends AutoWoodBlocks {

    public final Block SAPLING;
    public final Block LOG;
    public final Block WOOD;
    public final Block STRIPPED_LOG;
    public final Block STRIPPED_WOOD;
    public final Block LEAVES;
    public final Block POTTED_SAPLING;


    public AutoTreeBlocks(String modid, String woodName, ItemGroup itemGroup, MaterialColor color, SaplingGenerator saplingGenerator) {
        super(modid, woodName, itemGroup, color);

        SAPLING = registerBlock(modid, woodName+"_sapling", new CottonSaplingBlock(saplingGenerator, Block.Settings.copy(Blocks.OAK_SAPLING)), itemGroup);
        LOG = registerBlock(modid,woodName+"_log", new LogBlock(color,Block.Settings.copy(Blocks.OAK_LOG)), itemGroup);
        WOOD = registerBlock(modid,woodName+"_wood", new LogBlock(color,Block.Settings.copy(Blocks.OAK_WOOD)), itemGroup);
        STRIPPED_LOG = registerBlock(modid,"stripped_"+woodName+"_log", new LogBlock(color,Block.Settings.copy(Blocks.STRIPPED_OAK_LOG)), itemGroup);
        STRIPPED_WOOD = registerBlock(modid,"stripped_"+woodName+"_wood", new LogBlock(color,Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD)), itemGroup);

        LEAVES = registerBlock(modid,woodName+"_leaves", new LeavesBlock(Block.Settings.copy(Blocks.JUNGLE_LEAVES)),itemGroup);
        POTTED_SAPLING = registerBlock(modid,"potted_"+woodName+"_sapling", new FlowerPotBlock(SAPLING, FabricBlockSettings.copy(Blocks.POTTED_OAK_SAPLING).build()),itemGroup);

    }
}
