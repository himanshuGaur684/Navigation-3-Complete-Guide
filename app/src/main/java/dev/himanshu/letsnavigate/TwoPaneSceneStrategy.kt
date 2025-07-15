package dev.himanshu.letsnavigate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.Scene
import androidx.navigation3.ui.SceneStrategy
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

class TwoPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val firstEntry: NavEntry<T>,
    val secondEntry: NavEntry<T>,
) : Scene<T> {
    override val entries: List<NavEntry<T>>
        get() = listOf(firstEntry, secondEntry)

    override val content: @Composable (() -> Unit) = {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.weight(0.5f)) {
                    firstEntry.content.invoke(firstEntry.key)
                }
                Column(modifier = Modifier.weight(0.5f)) {
                    secondEntry.content.invoke(secondEntry.key)
                }
            }
        }

    companion object {
        const val TWO_PANE = "TwoPane"
        fun twoPane() = mapOf(TWO_PANE to true)
    }
}

class TwoPaneSceneStrategy<T : Any> : SceneStrategy<T> {
    @Composable
    override fun calculateScene(
        entries: List<NavEntry<T>>, onBack: (Int) -> Unit
    ): Scene<T>? {
        val windowInfoClass = currentWindowAdaptiveInfo().windowSizeClass

        if (windowInfoClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND).not()) return null

        val lastTwoEntries = entries.takeLast(2)
        val firstEntry = lastTwoEntries.first()
        val secondEntry = lastTwoEntries.last()

        val sceneKey = Pair(firstEntry, secondEntry)

        return if (lastTwoEntries.size == 2 && firstEntry.metadata[TwoPaneScene.TWO_PANE] == true
            && secondEntry.metadata[TwoPaneScene.TWO_PANE] == true
        ) {
            TwoPaneScene(
                key = sceneKey,
                previousEntries = entries.dropLast(1),
                firstEntry = lastTwoEntries[0],
                secondEntry = lastTwoEntries[1]
            )
        } else {
            null
        }


    }
}







