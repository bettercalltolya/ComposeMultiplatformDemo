package demo.helpers

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
        title = { Text(title, style = MaterialTheme.typography.titleMedium) },
        text = { Text(message, style = MaterialTheme.typography.bodyMedium) },
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissed,
        dismissButton = {
            TextButton(onClick = onDismissed) {
                Text(
                    text = negativeText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmed) {
                Text(positiveText, style = MaterialTheme.typography.titleMedium, color = Color.Red)
            }
        }
    )
}