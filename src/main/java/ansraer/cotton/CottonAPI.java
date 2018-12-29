package ansraer.cotton;

import ansraer.cotton.autojson.AutoJson;
import ansraer.cotton.blocks.AutoTreeBlocks;
import ansraer.cotton.blocks.AutoWoodBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.registry.Registry;
import sun.reflect.generics.tree.Tree;

public class CottonAPI implements ModInitializer {

    public static String MODID = "cotton";

    public static String test;
    public static String test2 = "asdasd";

    public static Block testBlock = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock1 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock2 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock3 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock4 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock5 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock6 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));
    public static Block testBlock7 = new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS));

    public static AutoTreeBlocks test_tree;

    //Items

    public static Item testItem = null;

    @Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        //AutoJson json = new AutoJson(MODID);

        testBlock=Registry.register(Registry.BLOCK, MODID+":"+"test_block", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block", new BlockItem(testBlock, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock1=Registry.register(Registry.BLOCK, MODID+":"+"test_block1", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block1", new BlockItem(testBlock1, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock2=Registry.register(Registry.BLOCK, MODID+":"+"test_block2", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block2", new BlockItem(testBlock2, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock3=Registry.register(Registry.BLOCK, MODID+":"+"test_block3", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block3", new BlockItem(testBlock3, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock4=Registry.register(Registry.BLOCK, MODID+":"+"test_block4", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block4", new BlockItem(testBlock4, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock5=Registry.register(Registry.BLOCK, MODID+":"+"test_block5", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block5", new BlockItem(testBlock5, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock6=Registry.register(Registry.BLOCK, MODID+":"+"test_block6", new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block6", new BlockItem(testBlock6, (new Item.Settings().itemGroup(ItemGroup.MISC))));

        testBlock7=Registry.register(Registry.BLOCK, MODID+":"+"test_block7", new LogBlock(MaterialColor.BROWN, Block.Settings.copy(Blocks.BIRCH_PLANKS)));
        Registry.register(Registry.ITEM, MODID+":"+"test_block7", new BlockItem(testBlock7, (new Item.Settings().itemGroup(ItemGroup.MISC))));


        testItem=Registry.register(Registry.ITEM, MODID+":"+"test_item", new FoodItem(4, 0.3F, false, (new Item.Settings()).itemGroup(ItemGroup.MISC)));

        AutoTreeBlocks test_tree = new AutoTreeBlocks(MODID, "mangrove", ItemGroup.MISC, MaterialColor.BROWN, new BirchSaplingGenerator());

        AutoJson autoJson = new AutoJson(MODID);
        autoJson.addObjectToScan(test_tree);

        autoJson.addClassToScan(this.getClass());
        autoJson.addObjectToScan(this);
        autoJson.start();

	}

}
