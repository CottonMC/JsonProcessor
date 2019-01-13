package io.github.cottonmc.autojson.annotations.blockstate.defaults;

import io.github.cottonmc.autojson.annotations.blockstate.meta.Transform;
import io.github.cottonmc.autojson.annotations.blockstate.meta.UvLock;
import io.github.cottonmc.autojson.annotations.blockstate.meta.Variant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.SOURCE)
@Target({TYPE, FIELD})
@Variant(key = "face", values = {"floor", "wall", "ceiling"})
@Variant(key = "powered", values = {"true", "false"})
@Variant(key = "facing", values = {"east", "west", "north", "south"})
@UvLock(key = "face", value = "wall")
@Transform(key = "facing", value = "east", transformValue = 90, type = Transform.Axis.Y)
@Transform(key = "facing", value = "west", transformValue = 270, type = Transform.Axis.Y)
@Transform(key = "facing", value = "south", transformValue = 180, type = Transform.Axis.Y)
@Transform(key = "face", value = "wall", transformValue = 90, type = Transform.Axis.X)
@Transform(key = "face", value = "ceiling", transformValue = 180, type = Transform.Axis.X)
public @interface Button {

}
