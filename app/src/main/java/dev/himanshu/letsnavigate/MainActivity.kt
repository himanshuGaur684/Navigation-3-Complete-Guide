package dev.himanshu.letsnavigate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import dev.himanshu.letsnavigate.ui.theme.LetsNavigateTheme
import kotlinx.serialization.Serializable

sealed interface Dest : NavKey {
    @Serializable
    data object List : Dest

    @Serializable
    data class Details(val text: String) : Dest
}

class MainActivity : ComponentActivity() {

    private val mainActivityViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            mainActivityViewModel.updateTag("Activity")

            LetsNavigateTheme {

                val backStack = rememberNavBackStack<Dest>(Dest.List)


                NavDisplay(
                    backStack = backStack,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    sceneStrategy = TwoPaneSceneStrategy<Any>(),
                    entryDecorators = listOf(
                        rememberSceneSetupNavEntryDecorator(),
//                        rememberSavedStateNavEntryDecorator(),
//                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {

                        entry<Dest.List>(
                            metadata = TwoPaneScene.twoPane() + NavDisplay.transitionSpec {
                                slideInHorizontally { it } togetherWith slideOutHorizontally { it }
                            } + NavDisplay.popTransitionSpec {
                                slideInVertically { it } togetherWith slideOutVertically { it }
                            } + NavDisplay.predictivePopTransitionSpec {
                                slideInVertically { it } togetherWith slideOutVertically { it }
                            }
                        ) {

                            val viewModel = viewModel<MainViewModel>()
                            LaunchedEffect(Unit) {
                                viewModel.updateTag("Entry")
                            }
                            ListScreen(
                                onClick = { backStack.add(Dest.Details(it)) }
                            )
                        }

                        entry<Dest.Details>(
                            metadata = TwoPaneScene.twoPane()+ NavDisplay.transitionSpec {
                                slideInHorizontally { it } togetherWith slideOutHorizontally { it }
                            } + NavDisplay.popTransitionSpec {
                                slideInVertically { it } togetherWith slideOutVertically { it }
                            } + NavDisplay.predictivePopTransitionSpec {
                                slideInVertically { it } togetherWith slideOutVertically { it }
                            }
                        ) { key ->
                            DetailsScreen(
                                text = key.text
                            )
                        }
                    }
                )
            }
        }
    }
}

