package ansraer.cotton.autojson.json.blockstates;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;

@JsonAdapter(ModelJson.class)
public class ModelJson implements JsonSerializer<ModelJson>, JsonDeserializer<ModelJson> {

    /**
     * Specifies the path to the model file of the block, starting in assets/<namespace>/models.
     */
    public String model;

    /**
     * Rotation of the model on the x-axis in increments of 90 degrees.
     */
    public int x;

    /**
     * Rotation of the model on the y-axis in increments of 90 degrees.
     */
    public int y;

    /**
     *  Can be true or false (default). Locks the rotation of the texture of a block, if set to true.
     *  This way the texture will not rotate with the block when using the x and y-tags above.
     */
    public boolean uvlock;

    /**Sets the probability of the model for being used in the game, defaults to 1 (=100%).
     * If more than one model is used for the same variant, the probability will be calculated by dividing the
     * individual model’s weight by the sum of the weights of all models.
     */
    public int weight = 1;

    @Override
    public JsonElement serialize(ModelJson src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("model", src.model);

        if(src.x!=0)
            json.addProperty("x", src.x);

        if(src.y!=0)
            json.addProperty("y", src.y);

        if(src.uvlock)
            json.addProperty("uvlock", src.uvlock);

        if(src.weight!=1)
            json.addProperty("weight", src.weight);

        return json;
    }

    @Override
    public ModelJson deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ModelJson result = new ModelJson();
        JsonObject json = jsonElement.getAsJsonObject();


        if(json.has("model"))
            result.model=json.get("model").getAsString();

        if(json.has("x"))
            result.x=json.get("x").getAsInt();

        if(json.has("y"))
            result.y=json.get("y").getAsInt();

        if(json.has("uvlock"))
            result.uvlock=json.get("uvlock").getAsBoolean();

        if(json.has("weight"))
            result.weight=json.get("weight").getAsInt();

        return result;
    }


}
