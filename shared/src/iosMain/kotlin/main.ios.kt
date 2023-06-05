import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import demo.RootContent
import demo.helpers.initKoin
import demo.navigation.NotesRootComponent
import platform.UIKit.UIViewController
import demo.ui.theme.AppTheme

fun MainViewController(
    lifecycle: LifecycleRegistry,
    topSafeArea: Float,
    bottomSafeArea: Float
): UIViewController {
    initKoin()

    val rootComponent = NotesRootComponent(
        componentContext = DefaultComponentContext(lifecycle),
        storeFactory = DefaultStoreFactory(),
    )

    return ComposeUIViewController {
        val density = LocalDensity.current

        val topSafeAreaDp = with(density) { topSafeArea.toDp() }
        val bottomSafeAreaDp = with(density) { bottomSafeArea.toDp() }
        val safeArea = PaddingValues(top = topSafeAreaDp + 20.dp, bottom = bottomSafeAreaDp + 10.dp)

        AppTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                RootContent(
                    component = rootComponent,
                    modifier = Modifier.padding(safeArea)
                )
            }
        }
    }
}