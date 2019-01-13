package io.github.cottonmc.autojson.annotations.blockstate.meta;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE,FIELD})
@Repeatable(Variants.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Variant {
    String key();
    String[] values();
}
