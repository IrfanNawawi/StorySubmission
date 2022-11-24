package id.heycoding.storysubmission.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Story(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val lat: Double,
    val lng: Double,
    val photoUrl: String,
    val createdAt: String
)