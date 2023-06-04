package demo.navigation.components

import demo.screens.home.store.HomeStore
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {
    val state: StateFlow<HomeStore.State>
}