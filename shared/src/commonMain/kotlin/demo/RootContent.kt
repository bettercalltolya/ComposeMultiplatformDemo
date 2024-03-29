package demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import demo.navigation.NotesRoot
import demo.navigation.NotesRoot.Child.CreateNote
import demo.navigation.NotesRoot.Child.EditNote
import demo.navigation.NotesRoot.Child.Home
import demo.screens.createnote.CreateNoteScreen
import demo.screens.editnote.EditNoteScreen
import demo.screens.home.HomeScreen

@Composable
fun RootContent(
    component: NotesRoot,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
        modifier = modifier
    ) {
        when (val child = it.instance) {
            is Home -> HomeScreen(child.component)
            is CreateNote -> CreateNoteScreen(child.component)
            is EditNote -> EditNoteScreen(child.component)
        }
    }
}