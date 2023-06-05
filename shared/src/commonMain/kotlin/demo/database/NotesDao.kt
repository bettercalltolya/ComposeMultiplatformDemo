package demo.database

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesDao(private val realm: Realm) {

    fun getNotesFlow(): Flow<List<NoteEntity>> {
        return realm.query<NoteEntity>().asFlow().map { it.list }
    }

    suspend fun insertNote(note: NoteEntity) {
        realm.write {
            copyToRealm(note)
        }
    }

    suspend fun updateNote(id: String, title: String, body: String) {
        realm.write {
            query<NoteEntity>(
                "${NoteEntity::id.name} == $0",
                id
            ).first().find()?.let {
                it.title = title
                it.body = body
            }
        }
    }

    suspend fun deleteNote(id: String) {
        realm.write {
            query<NoteEntity>(
                "${NoteEntity::id.name} == $0",
                id
            ).first().find()?.let {
                delete(it)
            }
        }
    }
}