package nl.bouwman.marc.news.ui.utils.fluidresize

/** https://github.com/saket/FluidKeyboardResize */
data class KeyboardVisibilityChanged(
    val visible: Boolean,
    val contentHeight: Int,
    val contentHeightBeforeResize: Int
)
