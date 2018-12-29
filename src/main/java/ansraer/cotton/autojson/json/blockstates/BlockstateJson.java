package ansraer.cotton.autojson.json.blockstates;

import ansraer.cotton.autojson.json.CottonJsonFileUtils;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonAdapter(BlockstateJson.class)
public class BlockstateJson implements JsonSerializer<BlockstateJson>, JsonDeserializer<BlockstateJson> {

    public Map<String, ArrayList<ModelJson>> variants = new HashMap<String, ArrayList<ModelJson>>();
    public ArrayList<MultipartJson> multipart = new ArrayList<MultipartJson>();


    @Override
    public JsonElement serialize(BlockstateJson src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        //A block can either have variants or blockstates, if it has variants don't save blockstates
        if(src.variants.size()>0){
            JsonObject variantsElement = new JsonObject();

            //we save each variant individualy, if it has only one model don't use []
            src.variants.forEach((key, value) -> {
                if (value == null){
                    variantsElement.addProperty(key, "");
                }else if(value.size()>1){
                    variantsElement.add(key, context.serialize(value));
                } else {
                    variantsElement.add(key, context.serialize(value.get(0)));
                }
            });

            json.add("variants", variantsElement);
        } else {
            json.add("multipart", context.serialize(src.multipart));
        }



        return json;
    }


    @Override
    public BlockstateJson deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BlockstateJson result = new BlockstateJson();

        JsonObject json = jsonElement.getAsJsonObject();

        //A block can either have variants or blockstates, if it has variants don't load blockstates
        if(json.has("variants")){
            json.get("variants").getAsJsonObject().entrySet().forEach(entry -> {

                //each variant can either be a model or an array of models.
                if(entry.getValue().isJsonArray()){
                    result.variants.put(entry.getKey(), context.deserialize(entry.getValue(), ArrayList.class));
                } else {
                    ArrayList<ModelJson> temp = new ArrayList<>();
                    temp.add(context.deserialize(entry.getValue(), ModelJson.class));
                    result.variants.put(entry.getKey(), temp);

                }
            });
        } else if(json.has("multipart")){
            result.multipart = context.deserialize(json.get("multipart"), ArrayList.class);
        }

        return result;
    }
}
