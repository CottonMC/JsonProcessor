package ansraer.cotton.blocks;

import ansraer.cotton.items.CottonItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CottonBlockHelper {


    /**Registers a Block without creating a matching ItemBlock for it. This is used for blocks without items or special
     * Items that are being created manualy.
     * @param MODID The modid this Block shall be registered under
     * @param name
     * @param b
     * @return
     */
    private static Block registerBlockWithoutItem(String MODID, String name, Block b){
        return Registry.register(Registry.BLOCK, MODID+":"+name, b);
    }

    /**Registers a Block and creates a matching Block Item
     * @param MODID The modid this Block shall be registered under
     * @param name The name this Block schall be reigsterd under.
     * @param block The Block to registerBlock
     * @param itemGroup The ItemGroup that shall be used to create the Item.Settings for this block.
     */
    public static Block registerBlock(String MODID, String name, Block block, ItemGroup itemGroup){
        return registerBlock(MODID, name, block, (new Item.Settings()).itemGroup(itemGroup));
    }

    /**Registers a Block and creates a matching Block Item*
     * @param MODID The modid this Block shall be registered under
     * @param name The name this Block schall be reigsterd under.
     * @param block The Block to registerBlock
     * @param settings Item.Settings used to create the matching BlockItem
     */
    public static Block registerBlock(String MODID, String name, Block block, Item.Settings settings){
        block = registerBlockWithoutItem(MODID, name, block);
        registerBlockItem(block, settings);
        return block;
    }

    /**Registers a BlockItem for a given Block and Item.Settings and automatically adds it to the BLOCK_ITEM_MAP.
     *
     * @param block The Block of the Block Item
     * @param settings The Item.Settings of the new BlockItem
     */
    private static Item registerBlockItem(Block block, Item.Settings settings){
        Identifier id = Registry.BLOCK.getId(block);
        return CottonItemHelper.registerItem(id.getNamespace(), id.getPath(), new BlockItem(block, settings));
    }
}
