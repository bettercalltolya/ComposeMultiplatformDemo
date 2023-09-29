package demo.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import demo.database.NoteEntity
import demo.navigation.components.HomeComponent
import demo.ui.AppImages
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(component: HomeComponent) {
    val state by component.state.collectAsState()

    val isEmpty by remember {
        derivedStateOf { state.notes.isEmpty() }
    }

    Scaffold(
        topBar = { AppBar() },
        floatingActionButton = { Fab(component::onCreateNote) },
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = isEmpty,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) { empty ->
            if (empty) {
                EmptyScreen()
            } else {
                NotesGrid(
                    notes = state.notes,
                    onEditNote = component::onEditNote
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Notes",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
private fun Fab(onAdd: () -> Unit) {
    FloatingActionButton(
        onClick = onAdd,
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add note"
        )
    }
}

@Composable
private fun NotesGrid(
    notes: List<NoteEntity>,
    onEditNote: (NoteEntity) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
    ) {
        items(
            notes.size,
            key = { notes[it].id }
        ) {
            NoteItem(
                note = notes[it],
                onEditNote = onEditNote
            )
        }
    }
}

@Composable
private fun NoteItem(
    note: NoteEntity,
    onEditNote: (NoteEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onEditNote(note) }
            .padding(all = 16.dp)
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = note.body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            maxLines = 4,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(AppImages.NOTES_EMPTY),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = null,
                modifier = Modifier.size(width = 96.dp, height = 128.dp)
            )
            Text(
                text = "Looks like you don't have any notes yet!\nCreate one by pressing on \"+\" button!",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}