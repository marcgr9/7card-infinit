package ro.marc.sevencard.data

import java.io.Serializable

data class User(
    val id: Long,
    val alias: String?,
): Serializable
