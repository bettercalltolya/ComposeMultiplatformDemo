package demo.screens.home.store

import com.arkivanov.mvikotlin.core.store.Store
import demo.database.NoteEntity
import demo.screens.home.store.HomeStore.Intent
import demo.screens.home.store.HomeStore.State

interface HomeStore : Store<Intent, State, Nothing> {

    sealed class Intent {

    }

    data class State(
        val notes: List<NoteEntity> = emptyList()
    )
}