package demo.navigation.components

import demo.screens.createnote.store.CreateNoteStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CreateNoteComponent {
    val state: StateFlow<CreateNoteStore.State>
    val labels: Flow<CreateNoteStore.Label>

    fun onTitleChanged(title: String)
    fun onBodyChanged(body: String)
    fun onCreateNote()
    fun onNoteCreated()
    fun onGoBack()

    sealed class Action {
        object PopScreen : Action()
    }
}