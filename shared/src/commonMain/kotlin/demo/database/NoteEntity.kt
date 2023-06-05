package demo.database

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

@Parcelize
class NoteEntity private constructor(): RealmObject, Parcelable {

    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var body: String = ""

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