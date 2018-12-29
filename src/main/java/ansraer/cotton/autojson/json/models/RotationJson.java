package ansraer.cotton.autojson.json.models;

import com.google.common.collect.Sets;

public class RotationJson {


    /**
     * Sets the center of the rotation according to the scheme [x, y, z].
     */
    public int[] origin = new int[3];

    /**
     * Specifies the direction of rotation, can be "x", "y" or "z".
     */
    public int[] axis = new int[3];

    /**
     *  Specifies the angle of rotation. Can be 45 through -45 degrees in 22.5 degree increments.
     */
    public float angle;

    /**
     * Specifies whether or not to scale the faces across the whole block. Can be true or false. Defaults to false.
     */
    public boolean rescale= false;

}
