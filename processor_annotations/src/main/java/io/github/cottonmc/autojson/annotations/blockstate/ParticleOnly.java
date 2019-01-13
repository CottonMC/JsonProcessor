package io.github.cottonmc.autojson.annotations.blockstate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Used on blocks, will set the block model to being particle only
 * */
@Target({TYPE,FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface ParticleOnly {
    String domain = "minecraft";
    String path();
}
