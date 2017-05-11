package tech.crom.business.exception

class CommitNotFoundException(val commitId: String) : RuntimeException("No such commit $commitId")
