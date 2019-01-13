package io.github.cottonmc.autojson

import java.util.*
import javax.annotation.processing.ProcessingEnvironment

import javax.lang.model.element.Element

object AnnotationWalker {
    fun getAnnotation(element: Element, annotation: Class<Annotation>,processingEnv:ProcessingEnvironment): Optional<Annotation> {
        val elementAnnotation = element.getAnnotation(annotation)
        if (elementAnnotation != null)
            return Optional.of(elementAnnotation)


        val parentAnnotation = getFromParent(element, annotation,processingEnv)
        if (parentAnnotation.isPresent) {
            return Optional.of(parentAnnotation.get())
        }

        return Optional.empty()
    }

    private fun getFromParent(element: Element, annotation: Class<Annotation>,processingEnv:ProcessingEnvironment): Optional<Annotation> {
        val enclosingElement = element.enclosingElement ?: return Optional.empty()

        val enclosingAnnotation = enclosingElement.getAnnotation(annotation)
        return if (enclosingAnnotation != null)
            Optional.of(enclosingAnnotation)
        else {
            return getFromParent(enclosingElement, annotation,processingEnv)
        }
    }
}