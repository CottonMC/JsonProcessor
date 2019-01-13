package cotton;

import cotton.blocks.BlockWithNoItem;
import cotton.blocks.CottonStairsBlock;
import io.github.cottonmc.autojson.annotations.InstanceContainer;
import io.github.cottonmc.autojson.annotations.exclusions.NoItemModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@InstanceContainer("cotton")
public class SimpleContainer {
    public static final Block SIMPLE_BLOCK = new Block();
    @NoItemModel
    public static final Block SIMPLE_BLOCK_NO_ITEM = new Block();
    public static final Item SIMPLE_ITEM = new Item();
    public static final CottonStairsBlock STAIRS = new CottonStairsBlock();

   public static final BlockWithNoItem SIMPLE_BLOCK_NO_ITEM_ON_CLASS = new BlockWithNoItem();
}
