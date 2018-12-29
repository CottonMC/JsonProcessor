package ansraer.cotton.blocks;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;

import static ansraer.cotton.blocks.CottonBlockHelper.registerBlock;

/**This is a simple class that automatically generates and stores all wood items for a given name and modid.
 * The following blocks will be generated: Sign, Wallsign, PressurePlate, Trapdoor, Button, Stairs, Slab, FenceGate, Fence and Door
 */
public class AutoWoodBlocks extends AutoTestBlocks{

    private final String MODID;
    private final String woodName;

    public final Block PLANKS;
    //public final Block SIGN;
    //public final Block WALL_SIGN;
    public final Block PRESSURE_PLATE;
    public final Block TRAPDOOR;
    public final Block BUTTON;
    public final Block STAIRS;
    public final Block SLAB;
    public final Block FENCE_GATE;
    public final Block FENCE;
    public final Block DOOR;
    
    public AutoWoodBlocks(String modid, String woodName, ItemGroup itemGroup, MaterialColor color){
        this.MODID = modid;
        this.woodName=woodName;

        PLANKS = registerBlock(modid, woodName+"_planks",new Block(Block.Settings.copy(Blocks.BIRCH_PLANKS)),itemGroup);
        //SIGN = registerBlock(modid, woodName+"_sign", new StandingSignBlock(FabricBlockSettings.of(Material.WOOD, color).collidable(false).strength(1.0F, 1.0F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);
        //WALL_SIGN = registerBlock(modid, woodName+"_wall_sign", new WallSignBlock(FabricBlockSettings.of(Material.WOOD).collidable(false).strength(1.0F, 1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(SIGN).build()),itemGroup);
        PRESSURE_PLATE = registerBlock(modid, woodName+"_pressure_plate", new CottonPressurePlateBlock(PressurePlateBlock.Type.WOOD, FabricBlockSettings.of(Material.WOOD, color).collidable(false).strength(0.5F,0.5F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);
        TRAPDOOR = registerBlock(modid, woodName+"_trapdoor", new CottonTrapdoorBlock(FabricBlockSettings.of(Material.WOOD, color).strength(3.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);
        BUTTON = registerBlock(modid, woodName+"_button", new CottonWoodButtonBlock(FabricBlockSettings.of(Material.PART).build()),itemGroup);
        STAIRS = registerBlock(modid, woodName+"_stairs", new CottonStairsBlock(PLANKS.getDefaultState(), FabricBlockSettings.copy(PLANKS).build()),itemGroup);
        SLAB = registerBlock(modid, woodName+"_slab", new SlabBlock(FabricBlockSettings.of(Material.WOOD, color).strength(2.0F, 2.0F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);
        FENCE_GATE = registerBlock(modid, woodName+"_fence_gate", new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, color).strength(2.0F, 2.0F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);
        FENCE = registerBlock(modid, woodName+"_fence", new FenceBlock(FabricBlockSettings.of(Material.WOOD, color).strength(2.0F, 2.0F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);
        DOOR = registerBlock(modid, woodName+"_door", new CottonDoorBlock(FabricBlockSettings.of(Material.WOOD, color).strength(3.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()),itemGroup);

    }

    public String getMODID() {
        return MODID;
    }

    public String getWoodName() {
        return this.woodName;
    }
}
