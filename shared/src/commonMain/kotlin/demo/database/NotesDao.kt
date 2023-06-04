package demo.database

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesDao(private val realm: Realm) {

    fun getNotesFlow(): Flow<List<NoteEntity>> {
        return realm.query<NoteEntity>().asFlow().map { it.list }
    }

    fun getNoteById(id: RealmUUID): NoteEntity? {
        return realm.query<NoteEntity>(
            "${NoteEntity::title.name} == $0",
            id.toString()
        ).first().find()
    }

    suspend fun insertNote(note: NoteEntity) {
        realm.write {
            copyToRealm(note)
        }
    }

    suspend fun updateNote(id: RealmUUID, title: String, body: String?) {
        realm.write {
            query<NoteEntity>(
                "${NoteEntity::title.name} == $0",
                id.toString()
            ).first().find()?.let {
                it.title = title
                it.body = body
            }
        }
    }

    suspend fun deleteNote(id: RealmUUID) {
        realm.write {
            query<NoteEntity>(
                "${NoteEntity::title.name} == $0",
                id.toString()
            ).first().find()?.let {
                delete(it)
            }
        }
    }
}