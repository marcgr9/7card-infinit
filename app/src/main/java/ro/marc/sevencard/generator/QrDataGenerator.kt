package ro.marc.sevencard.generator

interface QrDataGenerator {

    companion object {
        const val UNENCRYPTED_FORMAT = "%d   %d"
    }

    fun encode(userId: Long): String

    fun getIdFrom(base64: String): Long

}
