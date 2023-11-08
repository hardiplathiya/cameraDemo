package plant.testtree.camerademo.activity.gallary

import android.database.Cursor

interface CursorHandler<T> {
    object CC {
        val projection: Array<String?>
            get() = arrayOfNulls(0)
    }

    fun handle(cursor: Cursor?): T
}