package com.example.hw5

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ContactTable")
class Contact (var icon: Int, var line1: String, var line2: String) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (icon != other.icon) return false
        if (line1 != other.line1) return false
        if (line2 != other.line2) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = icon
        result = 31 * result + line1.hashCode()
        result = 31 * result + line2.hashCode()
        result = 31 * result + id
        return result
    }

    override fun toString(): String {
        return "Contact(icon=$icon, line1='$line1', line2='$line2', id=$id)"
    }
}