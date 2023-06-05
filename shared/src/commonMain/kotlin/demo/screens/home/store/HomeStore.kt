package demo.screens.home.store

import com.arkivanov.mvikotlin.core.store.Store
import demo.database.NoteEntity
import demo.screens.home.store.HomeStore.State

interface HomeStore : Store<Nothing, State, Nothing> {

    data class State(
        val notes: List<NoteEntity> = emptyList()
    )
}