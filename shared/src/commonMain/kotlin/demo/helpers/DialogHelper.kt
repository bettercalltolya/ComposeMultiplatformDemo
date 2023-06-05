package demo.helpers

import androidx.compose.runtime.Composable

@Composable
expect fun ShowDialog(
    title: String,
    message: String,
    positiveText: String,
    negativeText: String,
    onConfirmed: () -> Unit,
    onDismissed: () -> Unit
)