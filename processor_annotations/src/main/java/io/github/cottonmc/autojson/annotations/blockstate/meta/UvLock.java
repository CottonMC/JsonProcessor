package io.github.cottonmc.autojson.annotations.blockstate.meta;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Repeatable(UvLocks.class)
@Target({TYPE,FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UvLock {
    String key();
    String value();
}
