package demo.screens.createnote.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import demo.database.NoteEntity
import demo.database.NotesDao
import demo.screens.createnote.store.CreateNoteStore.Intent
import demo.screens.createnote.store.CreateNoteStore.Label
import demo.screens.createnote.store.CreateNoteStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class CreateNoteStoreProvider(
    private val storeFactory: StoreFactory,
    private val notesDao: NotesDao,
    private val initialNote: NoteEntity? = null
) {

    fun provide(): CreateNoteStore =
        object : CreateNoteStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "CraeteNoteStore",
                initialState = State(),
                bootstrapper = initialNote?.let { SimpleBootstrapper(it) },
                executorFactory = ::CreateNoteExecutor,
                reducer = CreateNoteReducer
            ) {}

    // Message to be dispatched to Reducer to update State
    private sealed class Message {
        data class PrefillNote(val title: String, val body: String) : Message()
        data class TitleChanged(val title: String) : Message()
        data class BodyChanged(val body: String) : Message()
    }

    // Executor handles Intents and Actions
    private inner class CreateNoteExecutor :
        CoroutineExecutor<Intent, NoteEntity, State, Message, Label>() {

        override fun executeAction(action: NoteEntity, getState: () -> State) {
            dispatch(Message.PrefillNote(action.title, action.body))
        }

        // Intents coming from UI (Component) are handled here
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.TitleChanged -> dispatch(Message.TitleChanged(intent.title))
                is Intent.BodyChanged -> dispatch(Message.BodyChanged(intent.body))
                is Intent.CreateNote -> createNote(getState())
                is Intent.UpdateNote -> updateNote(intent.id, getState())
                is Intent.DeleteNote -> deleteNote(intent.id)
            }
        }

        private fun createNote(state: State) {
            scope.launch(Dispatchers.IO) {
                notesDao.insertNote(NoteEntity.create(state.title, state.body))

                withContext(Dispatchers.Main) {
                    publish(Label.NoteCreated)
                }
            }
        }

        private fun updateNote(id: String, state: State) {
            scope.launch(Dispatchers.IO) {
                notesDao.updateNote(id, state.title, state.body)

                withContext(Dispatchers.Main) {
                    publish(Label.NoteCreated)
                }
            }
        }

        private fun deleteNote(id: String) {
            scope.launch(Dispatchers.IO) {
                notesDao.deleteNote(id)

                withContext(Dispatchers.Main) {
                    publish(Label.NoteDeleted)
                }
            }
        }
    }

    private object CreateNoteReducer : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.PrefillNote -> {
                    copy(title = msg.title, body = msg.body, canSave = msg.title.isNotBlank())
                }

                is Message.TitleChanged -> {
                    copy(title = msg.title, canSave = msg.title.isNotBlank())
                }

                is Message.BodyChanged -> {
                    copy(body = msg.body)
                }
            }
    }
}