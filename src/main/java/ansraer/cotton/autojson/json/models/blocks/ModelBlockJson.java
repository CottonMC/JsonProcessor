package ansraer.cotton.autojson.json.models.blocks;

import ansraer.cotton.autojson.json.models.DisplayPositionJson;
import ansraer.cotton.autojson.json.models.ModelElementsJson;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.mojang.datafixers.types.templates.Named;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

@JsonAdapter(ModelBlockJson.class)
public class ModelBlockJson implements JsonSerializer<ModelBlockJson>, JsonDeserializer<ModelBlockJson> {

    /**Loads a different model from the given path, starting in assets/<namespace>/models.
     * If both "parent" and "elements" are set, the "elements" tag overrides the "elements" tag from the previous model.
     *
     */
    public String parent;

    /**
     * Whether to use ambient occlusion
     */
    public boolean ambientocclusion = true;


    /**
     * Holds the different places where item models are displayed.
     */
    public HashMap<String, DisplayPositionJson> display = new HashMap<String, DisplayPositionJson>();

    /**
     * Holds the textures of the model. Each texture starts in assets/<namespace>/textures or can be another texture variable.
     */
    public HashMap<String, String> textures = new HashMap<String, String>();

    /**
     * Contains all the elements of the model. they can only have cubic forms. If both "parent" and "elements" are set,
     * the "elements" tag overrides the "elements" tag from the previous model.
     */
    public ArrayList<ModelElementsJson> elements = new ArrayList<ModelElementsJson>();


    @Override
    public JsonElement serialize(ModelBlockJson src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();


        if(src.parent.length()>0)
            json.addProperty("parent", src.parent);

        if(!src.ambientocclusion)
            json.addProperty("ambientocclusion", src.ambientocclusion);

        if(src.display.size()>0)
            json.add("display", context.serialize(src.display));

        if(src.textures.size()>0)
            json.add("textures", context.serialize(src.textures));


        if(src.elements.size()>0)
            json.add("elements", context.serialize(src.elements));

        return json;
    }

    @Override
    public ModelBlockJson deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ModelBlockJson result = new ModelBlockJson();
        JsonObject json = jsonElement.getAsJsonObject();

        if(json.has("parent"))
            result.parent=json.get("parent").getAsString();

        if(json.has("ambientocclusion"))
            result.ambientocclusion = json.get("ambientocclusion").getAsBoolean();

        if(json.has("display"))
            result.display = context.deserialize(json.get("display"), HashMap.class);

        if(json.has("textures"))
            result.textures = context.deserialize(json.get("textures"), HashMap.class);

        if(json.has("elements"))
            result.elements = context.deserialize(json.get("elements"), ArrayList.class);


        return result;
    }


}