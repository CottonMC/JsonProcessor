package ansraer.cotton.items;

import net.minecraft.item.Item;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.registry.Registry;

/**
 * Projekt: CottonAPI
 */
public class CottonItemHelper {

    /**
     * Registers an item and adds it to the BLOCK_ITEM_MAP if it's a BlockItem
     * @param name The name that will be used to add this item. The modid will be prefixed.
     * @param item  The Item to register.
     * @return  The now registered Item.
     */
    public static Item registerItem(String MODID, String name, Item item){
        if (item instanceof BlockItem) {
            ((BlockItem)item).registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
        }
        return Registry.register(Registry.ITEM, MODID+":"+name, item);
    }
}
