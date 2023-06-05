package demo.screens.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import demo.core.ValueCallback
import demo.database.NoteEntity
import demo.database.NotesDao
import demo.navigation.components.HomeComponent
import demo.navigation.components.HomeComponent.Action
import demo.screens.home.store.HomeStore
import demo.screens.home.store.HomeStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: ValueCallback<Action>
) : HomeComponent, KoinComponent, ComponentContext by componentContext {

    private val notesDao by inject<NotesDao>()

    private val store = instanceKeeper.getStore {
        HomeStoreProvider(
            storeFactory = storeFactory,
            notesDao = notesDao
        ).provide()
    }

    override val state: StateFlow<HomeStore.State> = store.stateFlow

    override fun onCreateNote() {
        action(Action.CreateNote)
    }

    override fun onEditNote(note: NoteEntity) {
        action(Action.EditNote(note))
    }
}