package demo.helpers

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
actual fun ShowDialog(
    title: String,
    message: String,
    positiveText: String,
    negativeText: String,
    onConfirmed: () -> Unit,
    onDismissed: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissed,
        dismissButton = {
            TextButton(onClick = onDismissed) {
                Text(
                    text = negativeText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmed) {
                Text(
                    text = positiveText,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red
                )
            }
        }
    )
}