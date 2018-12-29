package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.json.models.blocks.ModelBlockJson;

import java.io.File;

public class LeavesBlockHandler extends CubeBlockHandler {

    public LeavesBlockHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);
    }

    @Override
    protected void handleBlockModel(String name) {
        ModelBlockJson model = fileUtils.loadBlockModel(name);

        //System.out.println(model.parent);

        if(model.elements.size()==0 && model.parent == null){
            logger.info("Creating new blockmodel.json for "+name);
            model.parent="block/leaves";
            model.textures.put("all",MODID+":block/"+name);

            fileUtils.writeBlockModel(name, model);
        }
    }
}
