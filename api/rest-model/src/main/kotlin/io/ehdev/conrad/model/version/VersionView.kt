package io.ehdev.conrad.model.version

class VersionView {
    open inner class UnreleasedVersionView

    open inner class ReleasedVersionView : UnreleasedVersionView()
}
