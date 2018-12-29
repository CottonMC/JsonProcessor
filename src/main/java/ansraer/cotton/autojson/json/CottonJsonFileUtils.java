package ansraer.cotton.autojson.json;

import ansraer.cotton.autojson.json.blockstates.BlockstateJson;
import ansraer.cotton.autojson.json.models.blocks.ModelBlockJson;
import ansraer.cotton.autojson.json.models.items.ModelItemJson;
import com.google.gson.*;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class CottonJsonFileUtils {

    protected final String modid;

    protected final File assetsModidFolder;
    protected final File dataModidFolder;

    protected final File blockstatesFolder;
    protected final File langFolder;
    protected final File modelsBlockFolder;
    protected final File modelsItemFolder;
    protected final File texturesFolder;

    protected final File dataLootBlocksFolder;
    protected final File dataLootItemsFolder;
    protected final File dataRecipesFolder;
    protected final File dataModTagsBlocksFolder;
    protected final File dataModTagsItemsFolder;

    protected final File dataMCRecipesFolder;
    protected final File dataMCTagsBlocksFolder;
    protected final File dataMCTagsItemsFolder;


    public CottonJsonFileUtils(String modid, File resources){
        this.modid=modid;
        assetsModidFolder = new File(resources, "/assets/"+modid+"/");
        assetsModidFolder.mkdirs();
        dataModidFolder = new File(resources, "/data/"+modid+"/");
        dataModidFolder.mkdirs();

        blockstatesFolder = new File(assetsModidFolder, "/blockstates/");
        blockstatesFolder.mkdirs();
        langFolder = new File(assetsModidFolder, "/lang/");
        langFolder.mkdirs();
        modelsBlockFolder = new File(assetsModidFolder, "/models/block/");
        modelsBlockFolder.mkdirs();
        modelsItemFolder = new File(assetsModidFolder, "/models/item/");
        modelsItemFolder.mkdirs();

        texturesFolder = new File(assetsModidFolder, "/textures/");
        texturesFolder.mkdirs();


        dataLootBlocksFolder = new File(dataModidFolder, "/loot_tables/blocks/");
        dataLootBlocksFolder.mkdirs();
        dataLootItemsFolder  = new File(dataModidFolder, "/loot_tables/items/");
        dataLootItemsFolder.mkdirs();
        dataRecipesFolder = new File(dataModidFolder, "/recipes/");
        dataRecipesFolder.mkdirs();
        dataModTagsBlocksFolder = new File(dataModidFolder, "/tags/blocks/");
        dataModTagsBlocksFolder.mkdirs();
        dataModTagsItemsFolder = new File(dataModidFolder, "/tags/items/");
        dataModTagsItemsFolder.mkdirs();



        dataMCRecipesFolder = new File(resources, "data/minecraft/recipes/");
        dataMCRecipesFolder.mkdirs();
        dataMCTagsBlocksFolder = new File(resources, "data/minecraft/tags/blocks/");
        dataMCTagsBlocksFolder.mkdirs();
        dataMCTagsItemsFolder = new File(resources, "data/minecraft/tags/items/");
        dataMCTagsItemsFolder.mkdirs();
    }


    public static Gson createGson(){
        GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();


        return gsonBuilder.create();
    }



    /**Attemots to load a given File. If the file can't be found returns an empty JsonObject instead.
     * @param file The file to load.
     * @return Either the file reporesented as a Json Object or a new JsonObect if the file can't be found.
     */
    private <T> T load(File file, Class<T> clazz){
        Gson gson = createGson();

        if(!file.exists()){
            File parent = file.getParentFile();
            parent.mkdirs();
        }

        final BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            return gson.fromJson(reader, clazz);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        }

        //if even that fails return null.
        return null;
    }



    private void writeJson(File file, Object object){
        if(!file.exists()){
            File parent = file.getParentFile();
            parent.mkdirs();
        }

        try (Writer writer = new FileWriter(file)) {
            Gson gson = createGson();
            gson.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setExecutable(true);
        file.setReadable(true);
        file.setWritable(true);
    }


    /**Attempts to load a blockstates.json from the modid/blockstatesFolder
     */
    public BlockstateJson loadBlockstate(String name){
        File input = new File(this.blockstatesFolder, "/"+name+".json");
        BlockstateJson res = load(input, BlockstateJson.class);
        return res == null ? new BlockstateJson() : res;
    }

    public void writeBlockstate(String name, BlockstateJson json){
        File f = new File(this.blockstatesFolder, "/"+name+".json");
        writeJson(f, json);
    }



    /**Attempts to load the en_us.json file from the modid/lang folder.
     **/
    public JsonObject loadLang(){
        JsonObject res= load(new File(this.langFolder, "en_us.json"), JsonObject.class);
        return res == null ? new JsonObject() : res;

    }

    public void writeLang(JsonObject json){
        writeJson(new File(this.langFolder, "en_us.json"), json);
    }

    /**Attempts to load a model.json from the modid/models/block Folder
     */
    public ModelBlockJson loadBlockModel(String name){
        ModelBlockJson res = load(new File(this.modelsBlockFolder,"/"+ name+".json"), ModelBlockJson.class);
        return res == null ? new ModelBlockJson() : res;
    }

    public void writeBlockModel(String name, ModelBlockJson json){
        File f = new File(this.modelsBlockFolder, "/"+name+".json");
        writeJson(f, json);
    }

    /**Attempts to load a model.json from the modid/models/block Folder
     */
    public ModelItemJson loadItemModel(String name){
        ModelItemJson res = load(new File(this.modelsItemFolder,"/"+ name+".json"), ModelItemJson.class);
        return res == null ? new ModelItemJson() : res;
    }

    public void writeItemModel(String name, ModelItemJson json){
        File f = new File(this.modelsItemFolder, "/"+name+".json");
        writeJson(f, json);
    }






    public void createPlaceholderTexture(int width, int height, String name){

        File f = new File(texturesFolder, "/"+name+".png");
        if(f.exists())
            return;

        File parent = f.getParentFile();
        parent.mkdirs();


        if(!f.exists()) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Random rdm = new Random();
            float hue = rdm.nextFloat();

            //since red and yellow are more rare due to the way hues work we give them a little boost
            if(hue>.2f && hue<.87f){
                if(rdm.nextFloat()<.35){
                    if(hue<.5){
                        hue=(float)Math.pow(hue, 2);
                    } else {
                        hue=(float)Math.pow(hue, 0.4);
                    }
                }
            }

            for(int x=0; x<width;x++){
               for(int y=0; y<height;y++){
                   img.setRGB(x, y, randomColor(hue));
               }
            }


            try{
                ImageIO.write(img, "png", f);
            }catch(IOException e){
                System.out.println("Error: " + e);
            }
        }
    }

    private int randomColor(){
        Random random = new Random();

        final float hue = random.nextFloat();

        // Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(800) + 7200) / 10000f;
        final float luminance = 0.85f;
        final Color color = Color.getHSBColor(hue, saturation, luminance);


        int a = (int)(255); //alpha
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int c = (a<<24) | (r<<16) | (g<<8) | b; //pixel
        //int c = (r<<16) | (g<<8) | b; //pixel
        return c;
    }

    /**
     * Creates a random color based on a hue. Slight variations of this hue will be used while generating the color
     * @param hue Value between 1 and 1.
     * @return A random int representing the color
     */
    private int randomColor(float hue){
        Random random = new Random();

        float offsetRange = 0.025f;
        float hueOffset = (random.nextFloat()*offsetRange)- offsetRange/2;
        hue += hueOffset;

        if(hue<0) hue+=1;
        if(hue>1) hue-=1;

        // Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(800) + 7200) / 10000f;
        final float luminance = (random.nextInt(200) + 8300) / 10000f;
        //        final float luminance = 0.84f;
        final Color color = Color.getHSBColor(hue, saturation, luminance);


        int a = (int)(255); //alpha
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int c = (a<<24) | (r<<16) | (g<<8) | b; //pixel
        //int c = (r<<16) | (g<<8) | b; //pixel
        return c;
    }


    /**Attempts to load a model.json from the modid/models/item Folder
     */
 /*   protected JsonObject loadItemModel(String name){
        return load(new File(this.modelsItemFolder, name+".json"));
    }
*/


}
