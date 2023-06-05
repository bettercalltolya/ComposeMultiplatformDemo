package demo.screens.editnote

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import demo.core.ValueCallback
import demo.database.NoteEntity
import demo.database.NotesDao
import demo.navigation.components.EditNoteComponent
import demo.navigation.components.EditNoteComponent.Action
import demo.navigation.components.EditNoteComponent.Action.PopScreen
import demo.screens.createnote.store.CreateNoteStore
import demo.screens.createnote.store.CreateNoteStore.Intent.BodyChanged
import demo.screens.createnote.store.CreateNoteStore.Intent.DeleteNote
import demo.screens.createnote.store.CreateNoteStore.Intent.TitleChanged
import demo.screens.createnote.store.CreateNoteStore.Intent.UpdateNote
import demo.screens.createnote.store.CreateNoteStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
class EditNoteComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val note: NoteEntity,
    private val action: ValueCallback<Action>
) : EditNoteComponent, KoinComponent, ComponentContext by componentContext {

    private val notesDao by inject<NotesDao>()

    private val store = instanceKeeper.getStore {
        CreateNoteStoreProvider(
            storeFactory = storeFactory,
            notesDao = notesDao,
            initialNote = note
        ).provide()
    }

    override val state: StateFlow<CreateNoteStore.State> = store.stateFlow
    override val labels: Flow<CreateNoteStore.Label> = store.labels

    override fun onTitleChanged(title: String) {
        store.accept(TitleChanged(title))
    }

    override fun onBodyChanged(body: String) {
        store.accept(BodyChanged(body))
    }

    override fun onSaveNote() {
        store.accept(UpdateNote(note.id))
    }

    override fun onDeleteNote() {
        store.accept(DeleteNote(note.id))
    }

    override fun onNoteDeleted() {
        action(PopScreen)
    }

    override fun onNoteSaved() {
        action(PopScreen)
    }

    override fun onGoBack() {
        action(PopScreen)
    }
}