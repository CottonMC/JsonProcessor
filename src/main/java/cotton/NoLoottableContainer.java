package cotton;

import io.github.cottonmc.autojson.annotations.InstanceContainer;
import io.github.cottonmc.autojson.annotations.exclusions.NoItemModel;
import io.github.cottonmc.autojson.annotations.exclusions.NoLootTable;
import net.minecraft.block.Block;

@NoLootTable
@InstanceContainer("cotton")
public class NoLoottableContainer {
    public static final Block SIMPLE_BLOCK_NO_LOOT = new Block();

    @NoItemModel
    public static final Block SIMPLE_BLOCK_NO_ITEM_NO_LOOT = new Block();
}
