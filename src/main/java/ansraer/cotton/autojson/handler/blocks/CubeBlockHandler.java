package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import ansraer.cotton.autojson.handler.items.BlockItemHandler;
import ansraer.cotton.autojson.json.blockstates.BlockstateJson;
import ansraer.cotton.autojson.json.blockstates.ModelJson;
import ansraer.cotton.autojson.json.models.blocks.ModelBlockJson;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.util.ArrayList;

public class CubeBlockHandler extends AutoJsonHandler<Block> {

    BlockItemHandler defaultItemHandler;


    public CubeBlockHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);
        defaultItemHandler = new BlockItemHandler(modid,resourcesFolder);
    }

    @Override
    protected boolean canHandle(Block object) {
        return true;
    }

    @Override
    protected void handle(Block object) {
        Identifier id = Registry.BLOCK.getId(object);
        if(!id.getNamespace().equalsIgnoreCase(MODID))
            return;

        String name = id.getPath();

        registerName(name);

        handleBlockState(name);
        handleBlockModel(name);
        handleBlockItem(object);

        handleTexture(name);


    }


    protected void handleBlockItem(Block object) {
        defaultItemHandler.handleObject(object.getItem());
    }

    protected void registerName(String name) {
        JsonObject en = fileUtils.loadLang();

        if(!en.has("block."+MODID+"."+name)){
            String format = name.replace("_", " ");
            format = WordUtils.capitalize(format);

            en.addProperty("block."+MODID+"."+name, format);
        }
        fileUtils.writeLang(en);
    }

    protected void handleBlockState(String name){
        BlockstateJson blockstate = fileUtils.loadBlockstate(name);

        if(blockstate.variants.size()==0){
            logger.info("Creating new blockstate.json for "+name);
            ArrayList<ModelJson> modelList = new ArrayList<>();

            ModelJson model = new ModelJson();
            model.model = MODID+":block/"+name;
            modelList.add(model);


            blockstate.variants.put("", modelList);


            fileUtils.writeBlockstate(name, blockstate);
        }
    }

    protected void handleBlockModel(String name){
        ModelBlockJson model = fileUtils.loadBlockModel(name);

        //System.out.println(model.parent);

        if(model.elements.size()==0 && model.parent == null){
            logger.info("Creating new blockmodel.json for "+name);
            model.parent="block/cube_all";
            model.textures.put("all",MODID+":block/"+name);

            fileUtils.writeBlockModel(name, model);
        }
    }

    protected void handleTexture(String name) {
        fileUtils.createPlaceholderTexture(16, 16, "block/" + name);
    }

}
