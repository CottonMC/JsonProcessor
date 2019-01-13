package io.github.cottonmc.autojson

import io.github.cottonmc.autojson.annotations.InstanceContainer
import io.github.cottonmc.autojson.annotations.exclusions.*
import io.github.cottonmc.autojson.generators.SimpleGenerator
import io.github.cottonmc.jsonfactory.data.Identifier
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter

class InstanceContainerProcessor : AbstractProcessor() {

    override fun process(set: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        for (el in roundEnv.getElementsAnnotatedWith(InstanceContainer::class.java)) {
            val instanceContainer = el.getAnnotation(InstanceContainer::class.java)
            val modid = instanceContainer.value

            val variableElements = ElementFilter.fieldsIn(el.enclosedElements)

            val blockType = processingEnv.elementUtils.getTypeElement("net.minecraft.block.Block").asType()
            val itemType = processingEnv.elementUtils.getTypeElement("net.minecraft.item.Item").asType()
            AnnotationWalker.apply {
                for (variableElement in variableElements) {
                    val type = variableElement.asType()

                    val name = variableElement.simpleName.toString()
                    val id = Identifier(modid, name.toLowerCase())

                    if (processingEnv.typeUtils.isAssignable(type, blockType)) {
                        SimpleGenerator.apply {
                            val noBlockModel = getAnnotation(variableElement,NoBlockModel::class.java as Class<Annotation>,processingEnv)
                            if (!noBlockModel.isPresent) {
                                SIMPLE_BLOCK_MODEL.generate(id, processingEnv)
                            }

                            val noBlockState = getAnnotation(variableElement,NoBlockstate::class.java as Class<Annotation>,processingEnv)
                            if (!noBlockState.isPresent) {
                                SIMPLE_BLOCK_STATE.generate(id, processingEnv)
                            }

                            val noItemModel = getAnnotation(variableElement,NoItemModel::class.java as Class<Annotation>,processingEnv)
                            if (!noItemModel.isPresent) {
                                SIMPLE_BLOCK_ITEM_MODEL.generate(id, processingEnv)
                            }

                            val noLootTable = getAnnotation(variableElement,NoLootTable::class.java as Class<Annotation>,processingEnv)
                            val noItemForm = getAnnotation(variableElement,NoItemForm::class.java as Class<Annotation>,processingEnv)

                            if (!noLootTable.isPresent || !noItemForm.isPresent) {
                                SIMPLE_LOOT_TABLE.generate(id, processingEnv)
                            }
                        }
                    } else if (processingEnv.typeUtils.isAssignable(type, itemType)) {
                        val noItemModel = getAnnotation(variableElement,NoItemModel::class.java as Class<Annotation>,processingEnv)
                        if (!noItemModel.isPresent) {
                            SimpleGenerator.SIMPLE_ITEM_MODEL.generate(id, processingEnv)
                        }
                    }
                }
            }
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(InstanceContainer::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }
}
