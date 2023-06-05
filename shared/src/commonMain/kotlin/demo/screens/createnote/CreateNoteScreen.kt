package demo.screens.createnote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import demo.navigation.components.CreateNoteComponent
import demo.screens.createnote.store.CreateNoteStore.Label.NoteCreated
import demo.screens.createnote.store.CreateNoteStore.State
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateNoteScreen(component: CreateNoteComponent) {
    val state by component.state.collectAsState()

    LaunchedEffect(component) {
        component.labels.collectLatest {
            when (it) {
                is NoteCreated -> component.onNoteCreated()
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "Create note",
                onGoBack = component::onGoBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        NoteEditorContent(
            state = state,
            onTitleChanged = component::onTitleChanged,
            onBodyChanged = component::onBodyChanged,
            onSave = component::onCreateNote,
            modifier = Modifier.padding(top = it.calculateTopPadding())
        )
    }
}

@Composable
fun NoteEditorContent(
    state: State,
    onTitleChanged: (String) -> Unit,
    onBodyChanged: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            InputField(
                value = state.title,
                placeholder = "Title...",
                textColor = MaterialTheme.colorScheme.primary,
                textStyle = MaterialTheme.typography.headlineMedium,
                imeAction = ImeAction.Next,
                onValueChange = onTitleChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
            InputField(
                value = state.body,
                placeholder = "Body...",
                textColor = MaterialTheme.colorScheme.primary,
                textStyle = MaterialTheme.typography.titleMedium,
                imeAction = ImeAction.Default,
                onValueChange = onBodyChanged,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(all = 16.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            onDelete?.let {
                DeleteButton(
                    onDelete = it,
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.05f))
            }
            SaveButton(
                enabled = state.canSave,
                onSave = onSave,
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onGoBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(
                onClick = onGoBack
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputField(
    value: String,
    placeholder: String,
    textStyle: TextStyle,
    textColor: Color,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle
            )
        },
        onValueChange = onValueChange,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = textColor,
            placeholderColor = textColor.copy(alpha = 0.4f),
        ),
        modifier = modifier
    )
}

@Composable
private fun SaveButton(
    enabled: Boolean,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSave,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp,
        ),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
        ),
        modifier = modifier
    ) {
        Text(
            text = "Save note",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun DeleteButton(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onDelete,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp,
        ),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier
    ) {
        Text(
            text = "Delete note",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}