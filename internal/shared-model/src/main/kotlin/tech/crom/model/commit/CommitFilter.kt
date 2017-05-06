package tech.crom.model.commit

data class CommitFilter(val history: List<CommitIdContainer>, val state: String?) {
    constructor(history: List<CommitIdContainer>): this(history, null)
}
