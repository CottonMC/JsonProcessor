package io.github.cottonmc.autojson.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Annotated classes will be used to get block/item instances from. They must be static.
 */
@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface InstanceContainer {
    /**
     * Your mod id
     */
    String value();
}
