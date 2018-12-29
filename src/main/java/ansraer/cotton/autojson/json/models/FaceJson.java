package ansraer.cotton.autojson.json.models;

public class FaceJson {

    /**
     * Defines the area of the texture to use according to the scheme [x1, y1, x2, y2]. If unset, it defaults to
     * values equal to xyz position of the element. The texture behavior will be inconsistent if UV extends below 0 or above 16.
     * If the numbers of x1 and x2 are swapped (e.g. from 0, 0, 16, 16 to 16, 0, 0, 16), the texture will be flipped.
     * UV is optional, and if not supplied it will automatically generate based on the element's position.
     */
    public int[] uv = new int[4];

    /**
     * Specifies the texture in form of the texture variable prepended with a #.
     */
    public String texture;

    /**
     * Specifies whether a face does not need to be rendered when there is a block touching it in the specified position.
     * The position can be: down, up, north, south, west, or east. It will also determine which side of the block to use
     * the light level from for lighting the face, and if unset, defaults to the side.
     */
    public String cullface;


    /**
     * Rotates the texture by the specified number of degrees. Can be 0, 90, 180, or 270. Defaults to 0.
     */
    public int rotation;

    public int tintindex;//

}
