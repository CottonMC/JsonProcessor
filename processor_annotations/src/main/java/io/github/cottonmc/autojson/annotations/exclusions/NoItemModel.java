package io.github.cottonmc.autojson.annotations.exclusions;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Disables block model generation on the selected annotated element
 * */
@Target({TYPE,FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface NoItemModel {
}
