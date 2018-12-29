package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import ansraer.cotton.blocks.AutoWoodBlocks;
import net.minecraft.block.Block;

import java.io.File;

public class AutoWoodBlocksHandler extends AutoJsonHandler<AutoWoodBlocks> {

    UniversalBlockHandler universal;

    public AutoWoodBlocksHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);
        universal = new UniversalBlockHandler(modid,resourcesFolder);

    }

    @Override
    protected boolean canHandle(AutoWoodBlocks object) {
        return true;
    }

    @Override
    protected void handle(AutoWoodBlocks object) {

        universal.handle(object.PLANKS);
        //public final Block SIGN;
        //public final Block WALL_SIGN;
        /*
        universal.handle(object.PRESSURE_PLATE);
        universal.handle(object.TRAPDOOR);
        universal.handle(object.BUTTON);
        universal.handle(object.STAIRS);
        universal.handle(object.SLAB);
        universal.handle(object.FENCE_GATE);
        universal.handle(object.FENCE);
        universal.handle(object.DOOR);
*/
        //TODO: register tags, loottables and crafting

    }

}
