package ansraer.cotton.autojson;

import ansraer.cotton.autojson.handler.*;
import ansraer.cotton.autojson.handler.blocks.AutoTreeBlocksHandler;
import ansraer.cotton.autojson.handler.blocks.AutoWoodBlocksHandler;
import ansraer.cotton.autojson.handler.blocks.UniversalBlockHandler;
import ansraer.cotton.autojson.handler.items.UniversalItemHandler;
import ansraer.cotton.blocks.AutoTreeBlocks;
import ansraer.cotton.blocks.AutoWoodBlocks;
import ansraer.cotton.logging.Ansi;
import ansraer.cotton.logging.ModLogger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class AutoJson {

    public static boolean isDev = Boolean.parseBoolean(System.getProperty("fabric.development", "true"));


    private final String MODID;

    private File resourceFolder;


    public static ModLogger LOGGER = new ModLogger("AutoJson", "AJ");;

    private List<Class> classesToScan = new ArrayList<Class>();
    private List<Object> objectToScan = new ArrayList<Object>();

    //this will map handlers to every supported class
    Map<Class<?>, AutoJsonHandler<?>> handlers = new LinkedHashMap<>();

    //A list of all supported classes. Note that this is reversed, so that later classes will be checked first.
    List<Class<?>> supportedClasses = new ArrayList<Class<?>>();
    Map<Class<?>,List<Object>> foundObjects = new LinkedHashMap<>();


    private List<Block> blocks= new ArrayList<Block>();
    private List<Item> items= new ArrayList<Item>();
    private List<AutoTreeBlocks> autoTreeBlocks= new ArrayList<AutoTreeBlocks>();
    private List<AutoWoodBlocks> autoWoodBlocks= new ArrayList<AutoWoodBlocks>();


    public AutoJson(String modid){
        this.MODID=modid;
        LOGGER.setPrefixFormat(Ansi.HighIntensity.and(Ansi.Magenta));
        resourceFolder = findResourcesFolder();



        //Prepare the default handlers.
        this.addClassHandler(Block.class, new UniversalBlockHandler(MODID, resourceFolder));
        this.addClassHandler(Item.class, new UniversalItemHandler(MODID, resourceFolder));
        this.addClassHandler(AutoWoodBlocks.class, new AutoWoodBlocksHandler(MODID, resourceFolder));
        this.addClassHandler(AutoTreeBlocks.class, new AutoTreeBlocksHandler(MODID, resourceFolder));





    }

    /**Navigates from the current folder to the src/main/resources folder and returns it.
     */
    private File findResourcesFolder(){
        Path current = Paths.get(".").toAbsolutePath();

        for(int i=0; i<3; i++){
            current=current.getParent();
            File possibleSource = new File(current.toString(), "src/");
            if(possibleSource.exists()){
                File resources = new File(possibleSource.getAbsolutePath(), "main/resources/");
                resources.mkdirs(); //make sure it exists
                return resources;
            }
        }

        File temp = new File(current.toAbsolutePath().toString(), "tempOutput/");
        temp.mkdirs();
        return temp;
    }

    /**Adds a class to the class handle list.
     * Once scanning starts all static fields in this class will be detected and handled.
     * @param clazz
     */
    public void addClassToScan(Class clazz){
        classesToScan.add(clazz);
    }

    /**Adds an object to the objects handle list.
     * Once scanning starts all non-static fields in this object will be detected and gandled.
     * @param object
     */
    public void addObjectToScan(Object object){
        objectToScan.add(object);
    }


    /**Add an handler for you own classes to AutoJson. Please note that the later a handler is added to this Map the higher his priority is
     *
     * @param key The Class this handler is going to handle
     * @param handler The handler for the key
     * @param <T> The Class you want to handle
     */
    @SuppressWarnings("unchecked")
    public <T> AutoJsonHandler<? super T> addClassHandler(Class<T> key, AutoJsonHandler<? super T> handler) {
        return (AutoJsonHandler<? super T>) handlers.put(key, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> AutoJsonHandler<? super T> getClassHandler(Class<T> key) {
        return (AutoJsonHandler<? super T>) handlers.get(key);
    }

    public <T> AutoJsonHandler<? super T> findApplicable(Class<T> key) {
        AutoJsonHandler<? super T> c = getClassHandler(key);
        if (c == null) {
            ArrayList<Map.Entry<Class<?>, AutoJsonHandler<?>>> entriesReversed = new ArrayList<>(handlers.entrySet());
            Collections.reverse(entriesReversed);

            for (Map.Entry<Class<?>, AutoJsonHandler<?>> e : entriesReversed) {
                if (e.getKey().isAssignableFrom(key)) {
                    @SuppressWarnings("unchecked")
                    AutoJsonHandler<? super T> value =
                            (AutoJsonHandler<? super T>) e.getValue();
                    c = value;
                    break;
                }
            }
        }
        return c;
    }

    private <T> boolean handleClasses(Class<T> key, Object obj, String name) {
        AutoJsonHandler<? super T> c = findApplicable(key);
        if (c != null && obj!=null) {
            LOGGER.info("handling "+name);
            return c.handleObject(key.cast(obj));
        }
        return false;
    }

    public void start(){
        if(!isDev){
            LOGGER.warn("Auto Json Creation is only meant to be used during development. Aborting.");
            return;
        }


        LOGGER.info("Starting AutoJson, an automatic JsonFile generator");
        LOGGER.warn("THIS TOOL CAN CORRUPT YOU PROJECT! Make sure to have a Backup");
        LOGGER.info("Are you sure you wish to continue? [y/n]");
        Scanner scanner = new Scanner(System. in);
        String input = scanner. nextLine();
        while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")){
            input = scanner.nextLine();
        }
        if(input.equalsIgnoreCase("n")){
            LOGGER.info("Exiting AutoJson...");
            return;
        }


        LOGGER.info("Set resources folder to: "+resourceFolder.getAbsolutePath().toString());


        supportedClasses = new ArrayList<Class<?>>(handlers.keySet());
        Collections.reverse(supportedClasses);
        handle();


        LOGGER.info("");
        LOGGER.success("DONE!");
        LOGGER.info("All files have been created, exiting now. Please restart Minecraft.");
        System.exit(0);
    }

    /**Goes through all provided classes and objects and adds their fields to the correct list.
     */
    private void handle(){

        LOGGER.info("Started Scanning...");

        LOGGER.infoBig("Scanning Classes:");
        handleClasses();
        LOGGER.infoBig("Scanning Objects:");
        handleObjects();

    }






    private void handleClasses(){
        //get all static fields from all our classes
        for(Class clazz : classesToScan){

            Field[] fields = clazz.getFields();
            for(Field f : fields){


                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    Class<?> t = f.getType();
                    try {
                        handleClasses(t,f.get(null), f.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void handleObjects(){
        //get all static fields from all our classes
        for(Object object: objectToScan){

            if(handleClasses(object.getClass(), object, "object of type "+object.getClass().getSimpleName())){
                //we handled the entire object at once
                continue;
            } else {
                Class clazz = object.getClass();

                Field[] fields = clazz.getFields();
                for(Field f : fields) {

                    //onmly get the non static fields
                    if (!java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                        try {
                            handleClasses(f.getType(), f.get(object), f.getName());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
