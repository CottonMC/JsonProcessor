package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import ansraer.cotton.autojson.json.models.blocks.ModelBlockJson;
import ansraer.cotton.blocks.AutoTreeBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;

public class AutoTreeBlocksHandler extends AutoJsonHandler<AutoTreeBlocks> {

    AutoWoodBlocksHandler woodHandler;
    UniversalBlockHandler universal;

    public AutoTreeBlocksHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);

        universal = new UniversalBlockHandler(modid,resourcesFolder);
        woodHandler = new AutoWoodBlocksHandler(modid, resourcesFolder);
    }

    @Override
    protected boolean canHandle(AutoTreeBlocks object) {
        return true;
    }

    @Override
    protected void handle(AutoTreeBlocks object) {

        universal.handle(object.SAPLING);
        universal.handle(object.LOG);
        universal.handle(object.STRIPPED_LOG);
        universal.handle(object.LEAVES);
        universal.handle(object.POTTED_SAPLING);

        CubeBlockHandler cubeHandler = new CubeBlockHandler(MODID, resourcesFolder);



        //Manualy create wood block because it uses another blocks texture
        Identifier id = Registry.BLOCK.getId(object.WOOD);
        String name = id.getPath();
        cubeHandler.registerName(name);
        cubeHandler.handleBlockState(name);
        handleWoodModel(name, (Registry.BLOCK.getId(object.LOG)).getPath());
        cubeHandler.handleBlockItem(object.WOOD);


        //Manualy create stripped wood block because it uses another blocks texture
        id = Registry.BLOCK.getId(object.STRIPPED_WOOD);
        name = id.getPath();
        cubeHandler.registerName(name);
        cubeHandler.handleBlockState(name);
        handleWoodModel(name, (Registry.BLOCK.getId(object.STRIPPED_LOG)).getPath());
        cubeHandler.handleBlockItem(object.STRIPPED_WOOD);



        //TODO: register tags, loottables and crafting


        woodHandler.handleObject(object);
    }

    private void handleWoodModel(String blockname, String texturename) {
        ModelBlockJson model = fileUtils.loadBlockModel(blockname);

        //System.out.println(model.parent);

        if(model.elements.size()==0 && model.parent == null){
            logger.info("Creating new blockmodel.json for "+blockname);
            model.parent="block/cube_all";
            model.textures.put("all",MODID+":block/"+texturename);

            fileUtils.writeBlockModel(blockname, model);
        }
    }


}
