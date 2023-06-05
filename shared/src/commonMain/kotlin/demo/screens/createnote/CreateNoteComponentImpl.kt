package demo.screens.createnote

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import demo.core.ValueCallback
import demo.database.NotesDao
import demo.navigation.components.CreateNoteComponent
import demo.navigation.components.CreateNoteComponent.Action
import demo.screens.createnote.store.CreateNoteStore
import demo.screens.createnote.store.CreateNoteStore.Intent.BodyChanged
import demo.screens.createnote.store.CreateNoteStore.Intent.CreateNote
import demo.screens.createnote.store.CreateNoteStore.Intent.TitleChanged
import demo.screens.createnote.store.CreateNoteStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
class CreateNoteComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: ValueCallback<Action>
) : CreateNoteComponent, KoinComponent, ComponentContext by componentContext {

    private val notesDao by inject<NotesDao>()

    private val store = instanceKeeper.getStore {
        CreateNoteStoreProvider(
            storeFactory = storeFactory,
            notesDao = notesDao
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

    override fun onCreateNote() {
        store.accept(CreateNote)
    }

    override fun onNoteCreated() {
        action(Action.PopScreen)
    }

    override fun onGoBack() {
        action(Action.PopScreen)
    }
}