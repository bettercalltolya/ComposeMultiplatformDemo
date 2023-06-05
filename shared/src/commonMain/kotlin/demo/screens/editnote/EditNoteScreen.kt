package demo.screens.editnote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import demo.helpers.ShowDialog
import demo.navigation.components.EditNoteComponent
import demo.screens.createnote.AppBar
import demo.screens.createnote.NoteEditorContent
import demo.screens.createnote.store.CreateNoteStore.Label.NoteCreated
import demo.screens.createnote.store.CreateNoteStore.Label.NoteDeleted
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditNoteScreen(component: EditNoteComponent) {
    val state by component.state.collectAsState()

    var showDeleteConfirmation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(component) {
        component.labels.collectLatest {
            when (it) {
                is NoteCreated -> component.onNoteSaved()
                is NoteDeleted -> component.onNoteDeleted()
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "Edit note",
                onGoBack = component::onGoBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        NoteEditorContent(
            state = state,
            onTitleChanged = component::onTitleChanged,
            onBodyChanged = component::onBodyChanged,
            onSave = component::onSaveNote,
            onDelete = {
                showDeleteConfirmation = true
            },
            modifier = Modifier.padding(top = it.calculateTopPadding())
        )
    }


    if (showDeleteConfirmation) {
        ShowDialog(
            title = "Delete note?",
            message = "You won't be able to access this note anymore",
            positiveText = "Delete",
            negativeText = "Cancel",
            onConfirmed = {
                component.onDeleteNote()
                showDeleteConfirmation = false
            },
            onDismissed = {
                showDeleteConfirmation = false
            }
        )
    }
}