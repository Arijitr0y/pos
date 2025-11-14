package org.kitchen.pos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform