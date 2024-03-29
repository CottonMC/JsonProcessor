package io.github.cottonmc.autojson.annotations.blockstate.meta;


import io.github.cottonmc.autojson.annotations.blockstate.meta.Variant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE,FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Variants {
    Variant[] value();
}