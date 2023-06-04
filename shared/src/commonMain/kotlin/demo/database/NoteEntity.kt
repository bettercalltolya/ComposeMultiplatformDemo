package demo.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class NoteEntity private constructor(): RealmObject {

    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var body: String? = null

    companion object {
        fun create(
            title: String,
            body: String
        ): NoteEntity {
            return NoteEntity().apply {
                this.id = RealmUUID.random().toString()
                this.title = title
                this.body = body
            }
        }
    }
}