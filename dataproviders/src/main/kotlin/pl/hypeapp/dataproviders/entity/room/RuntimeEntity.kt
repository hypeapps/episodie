package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo

data class RuntimeEntity(@ColumnInfo(name = "runtime")
                         var runtime: Long)
