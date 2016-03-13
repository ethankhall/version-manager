package io.ehdev.conrad.model

open class DefaultResourceSupport(override val links: MutableList<ResourceLink>) : ResourceSupport {
    constructor() : this(mutableListOf())

    fun addLink(link: ResourceLink) {
        links.add(link)
    }
}
