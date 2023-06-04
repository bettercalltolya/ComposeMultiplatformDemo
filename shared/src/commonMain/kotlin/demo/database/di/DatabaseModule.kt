package demo.database.di

import demo.database.NoteEntity
import demo.database.NotesDao
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val databaseModule = module {
    single {
        val config = RealmConfiguration.Builder(setOf(NoteEntity::class))
            .schemaVersion(1)
            .name("notes-realm")
            .build()

        Realm.open(config)
    } withOptions {
        createdAtStart()
    }

    single { NotesDao(get()) }
}