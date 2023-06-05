package demo.screens.createnote.store

import com.arkivanov.mvikotlin.core.store.Store
import demo.screens.createnote.store.CreateNoteStore.Intent
import demo.screens.createnote.store.CreateNoteStore.Label
import demo.screens.createnote.store.CreateNoteStore.State

interface CreateNoteStore : Store<Intent, State, Label> {

    sealed class Intent {
        data class TitleChanged(val title: String): Intent()
        data class BodyChanged(val body: String): Intent()
        data class UpdateNote(val id: String) : Intent()
        data class DeleteNote(val id: String) : Intent()
        object CreateNote : Intent()
    }

    sealed class Label {
        object NoteCreated : Label()
        object NoteDeleted : Label()
    }

    data class State(
        val title: String = "",
        val body: String = "",
        val canSave: Boolean = false
    )
}