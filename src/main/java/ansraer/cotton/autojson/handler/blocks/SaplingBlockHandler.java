package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.handler.items.GenericItemHandler;
import ansraer.cotton.autojson.json.models.blocks.ModelBlockJson;
import net.minecraft.block.Block;

import java.io.File;

public class SaplingBlockHandler extends CubeBlockHandler {

    public SaplingBlockHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);
    }

    @Override
    protected void handleBlockModel(String name) {
        ModelBlockJson model = fileUtils.loadBlockModel(name);
        //System.out.println(model.parent);

        if(model.elements.size()==0 && model.parent == null){
            logger.info("Creating new blockmodel.json for "+name);
            model.parent="block/cross";
            model.textures.put("cross",MODID+":block/"+name);

            fileUtils.writeBlockModel(name, model);
        }
    }

    @Override
    protected void handleBlockItem(Block object) {
        GenericItemHandler handler = new GenericItemHandler(this.MODID, this.resourcesFolder);
        handler.handleObject(object.getItem());
    }
}
