package io.realworld

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("micronaut.kotlin.jwt")
                .mainClass(Application.javaClass)
                .start()
    }
}
