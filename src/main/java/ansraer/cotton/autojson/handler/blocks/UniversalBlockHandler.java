package ansraer.cotton.autojson.handler.blocks;

import ansraer.cotton.autojson.handler.AutoJsonHandler;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SaplingBlock;
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

public class UniversalBlockHandler extends AutoJsonHandler<Block> {

    private LinkedHashMap<BiPredicate<String,Block>, AutoJsonHandler<Block>> subHandler= new LinkedHashMap<BiPredicate<String,Block>, AutoJsonHandler<Block>>();



    public UniversalBlockHandler(String modid, File resourcesFolder) {
        super(modid, resourcesFolder);

        //Cube handler. This is the fallback for every block, a simple cube
        this.registerSubHandler((s, b) -> true, new CubeBlockHandler(modid, resourcesFolder));

        //column handler. Used for logs and the like
        this.registerSubHandler((s, block) -> {
            if(block instanceof LogBlock){
                return true;
            }
            return false;
        }, new ColumnBlockHandler(modid,resourcesFolder));

        this.registerSubHandler((s,block)->{
            if(block instanceof LeavesBlock)
                return true;
            return false;
        }, new LeavesBlockHandler(modid,resourcesFolder));

        this.registerSubHandler((s,block)->{
            if(block instanceof SaplingBlock)
                return true;
            return false;
        }, new SaplingBlockHandler(modid,resourcesFolder));
    }


    /**This adds a Subhandler for a block to this Handler. The later a handler is added the higher it's prioority is.
     */
    public void registerSubHandler(BiPredicate<String,Block> typeDetector, AutoJsonHandler<Block> jsonHandler){
        this.subHandler.put(typeDetector, jsonHandler);
    }

    protected AutoJsonHandler<Block> getSubHandler(String name, Block block) {
        ArrayList<Map.Entry<BiPredicate<String, Block>, AutoJsonHandler<Block>>> entriesReversed = new ArrayList(subHandler.entrySet());
        Collections.reverse(entriesReversed);

        for (Map.Entry<BiPredicate<String,Block>, AutoJsonHandler<Block>> e : entriesReversed) {
            if(e.getKey().test(name, block))
                return e.getValue();
        }
        return null;
    }



    @Override
    protected boolean canHandle(Block object) {
        Identifier id= Registry.BLOCK.getId(object);
        String name = FilenameUtils.getName(id.getPath());

        for (BiPredicate<String,Block> predicate:this.subHandler.keySet()) {
            if(predicate.test(name, object)) return true;
        }

        return false;
    }

    @Override
    protected void handle(Block object) {
        Identifier id= Registry.BLOCK.getId(object);
        String name = FilenameUtils.getName(id.getPath());

        getSubHandler(name, object).handleObject(object);
    }


}
