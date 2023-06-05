package demo.navigation.components

import demo.database.NoteEntity
import demo.screens.home.store.HomeStore
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {
    val state: StateFlow<HomeStore.State>

    fun onCreateNote()

    fun onEditNote(note: NoteEntity)

    sealed class Action {
        object CreateNote : Action()
        data class EditNote(val note: NoteEntity) : Action()
    }
}