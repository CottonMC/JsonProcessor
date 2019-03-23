package io.github.cottonmc.autojson.generators

import io.github.cottonmc.jsonfactory.data.Identifier
import io.github.cottonmc.jsonfactory.gens.*
import io.github.cottonmc.jsonfactory.util.Serializer
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.StandardLocation

class SimpleGenerator(val generator : ContentGenerator) {

    fun generate(identifier: Identifier, environment: ProcessingEnvironment) {
        val generate = generator.generate(identifier)
        environment.filer.apply {
            generate.forEach {
                val resourceFile = "${generator.resourceRoot.path}/${identifier.namespace}/${generator.path}/${identifier.path}.json"
                val toJson = Serializer.toJson(it)
                println("creating resource file $resourceFile")
                val existingFile = getResource(StandardLocation.CLASS_OUTPUT, "", resourceFile)
                //if the file exists, than we can open the stream, and do nothing, because we don't want to override
                try {
                    existingFile.openOutputStream().close()
                } catch (e: Exception) {
                    println("resource file does not exist, creating")
                    val resource = createResource(StandardLocation.CLASS_OUTPUT,
                            "", resourceFile)
                    val out = resource.openOutputStream()
                    val writer = BufferedWriter(OutputStreamWriter(out, StandardCharsets.UTF_8))
                    writer.write(toJson)
                    writer.close()
                    out.close()
                }
            }
        }
    }

    companion object {
        val SIMPLE_BLOCK_MODEL = SimpleGenerator(Gens.basicBlockModel)
        val SIMPLE_BLOCK_STATE = SimpleGenerator(Gens.basicBlockState)
        val SIMPLE_ITEM_MODEL = SimpleGenerator(Gens.basicItemModel)
        val SIMPLE_BLOCK_ITEM_MODEL = SimpleGenerator(Gens.basicBlockItemModel)
        val SIMPLE_LOOT_TABLE = SimpleGenerator(Gens.basicLootTable)
    }
}