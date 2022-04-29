package io.jamshid.memeng.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val uzbName:String,
    val engName:String
):Serializable