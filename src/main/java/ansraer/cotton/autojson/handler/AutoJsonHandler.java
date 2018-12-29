package ansraer.cotton.autojson.handler;

import ansraer.cotton.autojson.AutoJson;
import ansraer.cotton.autojson.json.CottonJsonFileUtils;
import ansraer.cotton.logging.ModLogger;
import java.io.File;

public abstract class AutoJsonHandler<T> {
    public final String MODID;
    public final ModLogger logger;
    protected final File resourcesFolder;
    protected CottonJsonFileUtils fileUtils;//

    public AutoJsonHandler(String modid, File resourcesFolder){
        this.MODID=modid;
        this.logger= AutoJson.LOGGER;
        this.resourcesFolder = resourcesFolder;
        fileUtils = new CottonJsonFileUtils(modid, resourcesFolder);

    }

    /**This function will be called by other classes that want to have an object handled.
     * It checks if the object can be handled and if so handles it.
     * @return Returns true if this object was successfully handled, false otherwise
     */
    public boolean handleObject(T object){
        if(canHandle(object)) {
            this.handle(object);
            return true;
        }
        return false;
    }

    /**Check if a given Object can be handled, e.g. if an Item is actually registered and has a matching modid
     * @param object The object to check
     * @return Wether this is a real object for which json files should be generated.
     */
    protected abstract boolean canHandle(T object);


    /**This method will be started once we have made sure that our object actually can and should be handled by this class.
     */
    protected abstract void handle(T object);
}
