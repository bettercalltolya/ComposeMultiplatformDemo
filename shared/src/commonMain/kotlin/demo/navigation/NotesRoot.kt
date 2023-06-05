package demo.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import demo.core.ValueCallback
import demo.database.NoteEntity
import demo.navigation.NotesRoot.Child
import demo.navigation.components.CreateNoteComponent
import demo.navigation.components.CreateNoteComponent.Action.PopScreen
import demo.navigation.components.EditNoteComponent
import demo.navigation.components.HomeComponent
import demo.navigation.components.HomeComponent.Action.CreateNote
import demo.navigation.components.HomeComponent.Action.EditNote
import demo.screens.createnote.CreateNoteComponentImpl
import demo.screens.editnote.EditNoteComponentImpl
import demo.screens.home.HomeComponentImpl

interface NotesRoot {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Home(val component: HomeComponent) : Child()
        data class CreateNote(val component: CreateNoteComponent) : Child()
        data class EditNote(val component: EditNoteComponent) : Child()
    }
}

class NotesRootComponent internal constructor(
    componentContext: ComponentContext,
    private val home: (ComponentContext, ValueCallback<HomeComponent.Action>) -> HomeComponent,
    private val createNote: (ComponentContext, ValueCallback<CreateNoteComponent.Action>) -> CreateNoteComponent,
    private val editNote: (ComponentContext, NoteEntity, ValueCallback<EditNoteComponent.Action>) -> EditNoteComponent,
) : NotesRoot {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
        home = { context, actions ->
            HomeComponentImpl(
                context,
                storeFactory,
                actions
            )
        },
        createNote = { context, actions ->
            CreateNoteComponentImpl(
                context,
                storeFactory,
                actions
            )
        },
        editNote = { context, note, actions ->
            EditNoteComponentImpl(
                context,
                storeFactory,
                note,
                actions
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        componentContext.childStack(
            source = navigation,
            initialConfiguration = Configuration.Home,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Home -> {
                Child.Home(home(componentContext, ::onHomeAction))
            }
            is Configuration.CreateNote -> {
                Child.CreateNote(createNote(componentContext, ::onCreateNoteAction))
            }
            is Configuration.EditNote -> {
                Child.EditNote(editNote(componentContext, configuration.note, ::onEditNoteAction))
            }
        }

    private fun onHomeAction(action: HomeComponent.Action) {
        when (action) {
            is CreateNote -> navigation.push(Configuration.CreateNote)
            is EditNote -> navigation.push(Configuration.EditNote(action.note))
        }
    }

    private fun onCreateNoteAction(action: CreateNoteComponent.Action) {
        when (action) {
            is PopScreen -> navigation.pop()
        }
    }

    private fun onEditNoteAction(action: EditNoteComponent.Action) {
        when (action) {
            is EditNoteComponent.Action.PopScreen -> navigation.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize object Home : Configuration()
        @Parcelize object CreateNote : Configuration()
        @Parcelize data class EditNote(val note: NoteEntity) : Configuration()
    }
}