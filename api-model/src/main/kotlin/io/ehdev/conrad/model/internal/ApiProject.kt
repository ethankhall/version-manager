package io.ehdev.conrad.model.internal

import java.util.*

class ApiProject(val id: UUID, val name: String, val repos: List<ApiRepo>? = null)
