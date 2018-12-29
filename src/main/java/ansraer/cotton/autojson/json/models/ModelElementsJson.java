package ansraer.cotton.autojson.json.models;

import com.google.common.collect.Sets;

import java.util.ArrayList;

public class ModelElementsJson {

    /**
     * Start point of a cube according to the scheme [x, y, z]. Values must be between -16 and 32.
     */
    public int[] from = new int[3];

    /**
     * Stop point of a cube according to the scheme [x, y, z]. Values must be between -16 and 32.
     */
    public int[] to = new int[3];

    /**
     * Defines the rotation of an element.
     */
    public RotationJson rotation = new RotationJson();


    /**
     * Defines if shadows are rendered (true - default), not (false).
     */
    public boolean shade = true;

    public ArrayList<FaceJson> faces = new ArrayList<FaceJson>();


}
