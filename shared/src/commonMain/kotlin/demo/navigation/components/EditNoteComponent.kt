package demo.navigation.components

import demo.screens.createnote.store.CreateNoteStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EditNoteComponent {
    val state: StateFlow<CreateNoteStore.State>
    val labels: Flow<CreateNoteStore.Label>

    fun onTitleChanged(title: String)
    fun onBodyChanged(body: String)
    fun onSaveNote()
    fun onNoteSaved()
    fun onDeleteNote()
    fun onNoteDeleted()
    fun onGoBack()

    sealed class Action {
        object PopScreen : Action()
    }
}