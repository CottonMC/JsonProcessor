package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.json.blockstates.BlockstateJson;
import ansraer.cotton.autojson.json.blockstates.ModelJson;
import ansraer.cotton.autojson.json.models.blocks.ModelBlockJson;

import java.io.File;
import java.util.ArrayList;

public class ColumnBlockHandler extends CubeBlockHandler{
    public ColumnBlockHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);
    }

    @Override
    protected void handleBlockState(String name) {
        BlockstateJson blockstate = fileUtils.loadBlockstate(name);

        if(blockstate.variants.size()==0){
            logger.info("Creating new blockstate.json for "+name);

            ArrayList<ModelJson> modelList = new ArrayList<>();
            ModelJson model = new ModelJson();
            model.model = MODID+":block/"+name;
            modelList.add(model);

            ArrayList<ModelJson> modelListx = new ArrayList<>();
            ModelJson modelx = new ModelJson();
            modelx.model = MODID+":block/"+name;
            modelx.x=90;
            modelListx.add(modelx);

            ArrayList<ModelJson> modelListxy = new ArrayList<>();
            ModelJson modelxy = new ModelJson();
            modelxy.model = MODID+":block/"+name;
            modelxy.x=90;
            modelxy.y=90;
            modelListxy.add(modelxy);

            blockstate.variants.put("axis=y", modelList);
            blockstate.variants.put("axis=z", modelListx);
            blockstate.variants.put("axis=x", modelListxy);


            fileUtils.writeBlockstate(name, blockstate);
        }
    }

    @Override
    protected void handleBlockModel(String name) {
        ModelBlockJson model = fileUtils.loadBlockModel(name);

        //System.out.println(model.parent);

        if(model.elements.size()==0 && model.parent == null){
            logger.info("Creating new blockmodel.json for "+name);
            model.parent="block/cube_column";
            model.textures.put("end",MODID+":block/"+name+"_top");
            model.textures.put("side",MODID+":block/"+name);

            fileUtils.writeBlockModel(name, model);
        }
    }

    @Override
    protected void handleTexture(String name) {
        fileUtils.createPlaceholderTexture(16, 16, "block/" + name + "_top");
        fileUtils.createPlaceholderTexture(16, 16, "block/" + name);

    }
}
