package ansraer.cotton.autojson.handler.items;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import ansraer.cotton.autojson.json.models.items.ModelItemJson;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;

public class BlockItemHandler extends AutoJsonHandler<Item>  {
    public BlockItemHandler(String modid, File resourcesFolder) {
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

        ModelItemJson model = fileUtils.loadItemModel(name);

        if(model.parent==null && model.elements.size()==0){
            logger.info("Creating new itemmode.json for "+name);
            model.parent=MODID+":block/"+name;
        }

        fileUtils.writeItemModel(name, model);
    }
}
