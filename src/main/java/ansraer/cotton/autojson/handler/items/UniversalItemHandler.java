package ansraer.cotton.autojson.handler.items;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class UniversalItemHandler extends AutoJsonHandler<Item> {

    private LinkedHashMap<BiPredicate<String,Item>, AutoJsonHandler<Item>> subHandler= new LinkedHashMap<BiPredicate<String,Item>, AutoJsonHandler<Item>>();

    public UniversalItemHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);

        //Create the default handlers:

        this.registerSubHandler(new BiPredicate<String, Item>() {
            @Override
            public boolean test(String s, Item item) {
                return true;
            }
        }, new GenericItemHandler(modid, resourcesFolder));

        this.registerSubHandler(new BiPredicate<String, Item>() {
            @Override
            public boolean test(String s, Item item) {
                if(item instanceof BlockItem)
                    return true;
                return false;
            }
        }, new BlockItemHandler(modid,resourcesFolder));

    }

    @Override
    protected boolean canHandle(Item object){
        Identifier id= Registry.ITEM.getId(object);
        String name = FilenameUtils.getName(id.getPath());

        for (BiPredicate<String,Item> predicate:this.subHandler.keySet()) {
            if(predicate.test(name, object)) return true;
        }

        return false;
    }

    @Override
    protected void handle(Item object) {
        Identifier id= Registry.ITEM.getId(object);
        String name = FilenameUtils.getName(id.getPath());

        getSubHandler(name, object).handleObject(object);
    }

    public void registerSubHandler(BiPredicate<String, Item> typeDetector, AutoJsonHandler<Item> jsonHandler){
        this.subHandler.put(typeDetector, jsonHandler);
    }

    protected AutoJsonHandler<Item> getSubHandler(String name, Item item) {
        ArrayList<Map.Entry<BiPredicate<String, Item>, AutoJsonHandler<Item>>> entriesReversed = new ArrayList(subHandler.entrySet());
        Collections.reverse(entriesReversed);

        for (Map.Entry<BiPredicate<String, Item>, AutoJsonHandler<Item>> e : entriesReversed) {
            if(e.getKey().test(name, item))
                return e.getValue();
        }
        return null;
    }


}
