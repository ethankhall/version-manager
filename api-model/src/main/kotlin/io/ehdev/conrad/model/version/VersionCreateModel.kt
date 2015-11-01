package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import javax.validation.constraints.NotNull

class VersionCreateModel public @JsonCreator constructor(
    commits: List<String>,
    @NotNull val message: String,
    @NotNull val commitId: String,
    @NotNull val token: String
): VersionSearchModel(commits);
