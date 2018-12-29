package ansraer.cotton.autojson.handler.items;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import ansraer.cotton.autojson.json.models.items.ModelItemJson;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;

public class GenericItemHandler extends AutoJsonHandler<Item> {
    public GenericItemHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);
    }

    @Override
    protected boolean canHandle(Item object) {
        return true;
    }

    @Override
    protected void handle(Item object) {
        Identifier id = Registry.ITEM.getId(object);
        if(!id.getNamespace().equalsIgnoreCase(MODID))
            return;

        String name = id.getPath();

        registerName(name);

        handleItemModel(name);

        fileUtils.createPlaceholderTexture(16, 16, "item/" + name);
    }

    private void registerName(String name) {
        JsonObject en = fileUtils.loadLang();

        if(!en.has("item."+MODID+"."+name)){
            String format = name.replace("_", " ");
            format = WordUtils.capitalize(format);

            en.addProperty("item."+MODID+"."+name, format);
        }
        fileUtils.writeLang(en);
    }

    private void handleItemModel(String name) {
        ModelItemJson model = fileUtils.loadItemModel(name);

        if(model.parent==null && model.elements.size()==0){
            logger.info("Creating new itemmode.json for "+name);
            model.parent="item/generated";
            model.textures.put("layer0", MODID+":item/"+name);
        }

        fileUtils.writeItemModel(name, model);
    }
}
