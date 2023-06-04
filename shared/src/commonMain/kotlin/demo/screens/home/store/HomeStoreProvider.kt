package demo.screens.home.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import demo.database.NoteEntity
import demo.database.NotesDao
import demo.screens.home.store.HomeStore.Intent
import demo.screens.home.store.HomeStore.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class HomeStoreProvider(
    private val storeFactory: StoreFactory,
    private val notesDao: NotesDao
) {

    fun provide(): HomeStore =
        object : HomeStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = "HomeStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::HomeExecutor,
                reducer = HomeReducer
            ) {}

    // Message to be dispatched to Reducer to update State
    private sealed class Message {
        data class NotesLoaded(val notes: List<NoteEntity>) : Message()
    }

    // Executor handles Intents and Actions
    private inner class HomeExecutor : CoroutineExecutor<Intent, Unit, State, Message, Nothing>() {
        // Initial action is null to start observing database
        override fun executeAction(action: Unit, getState: () -> State) {
            notesDao.getNotesFlow()
                .map(Message::NotesLoaded)
                .onEach(::dispatch)
                .launchIn(scope)
        }

        // Intents coming from UI (Component) are handled here
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                else -> {

                }
            }
        }
    }

    // Reducer handles Messages to update State
    private object HomeReducer : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.NotesLoaded -> copy(notes = msg.notes)
            }
    }
}