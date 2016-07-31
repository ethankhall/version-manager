package tech.crom.model.security.authorization

import java.io.Serializable

interface AuthorizedObject {
    fun getId(): Serializable?
}
