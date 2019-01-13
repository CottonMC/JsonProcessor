package io.github.cottonmc.autojson.annotations.blockstate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE,FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface BlockStateTypeOverride {
    enum Type{
        VARIANT,MULTIPART
    }
    Type value() default Type.VARIANT;
}
