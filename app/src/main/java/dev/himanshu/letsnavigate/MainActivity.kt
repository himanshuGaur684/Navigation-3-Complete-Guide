package dev.himanshu.letsnavigate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import dev.himanshu.letsnavigate.ui.theme.LetsNavigateTheme
import kotlinx.serialization.Serializable


sealed interface Dest : NavKey {
    @Serializable
    object List : Dest

    @Serializable
    data class Details(
        val text: String
    ) : Dest
}


class MainActivity : ComponentActivity() {

    val activityMainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LetsNavigateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    LaunchedEffect(Unit) {
                        activityMainViewModel.updateTag("Activity")
                    }
                    MainNavigation(modifier = Modifier.padding(it), activityMainViewModel)
                }
            }
        }
    }
}


@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    val backStack = rememberNavBackStack<Dest>(Dest.List)
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Dest.List>(
                metadata = TwoPaneScene.twoPane()
            ) {
                val vm = viewModel<MainViewModel>()
                LaunchedEffect(Unit) {
                    vm.updateTag("Dest.List")
                    mainViewModel.updateTag("Activity VM with in Dest.List")
                }
                ListScreen {
                    backStack.add(Dest.Details(it))
                }
            }
            entry<Dest.Details>(
                metadata = TwoPaneScene.twoPane()
            ) { key ->
                DetailsScreen(
                    text = key.text
                )
            }
        },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        sceneStrategy = TwoPaneSceneStrategy<Any>(),
        onBack = { backStack.removeLastOrNull() }
    )
}
