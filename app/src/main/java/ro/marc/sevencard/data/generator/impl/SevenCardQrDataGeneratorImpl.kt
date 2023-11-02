package ro.marc.sevencard.data.generator.impl

import android.util.Base64
import ro.marc.sevencard.data.generator.QrDataGenerator
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

class SevenCardQrDataGeneratorImpl: QrDataGenerator {

    companion object {
        private const val PLAIN_SECRET = "salamdesibiu"
        private const val SECRET_LENGTH_BYTES = 32  // sha-256 secret length
    }

    private val secret: ByteArray by lazy {
        val instance = MessageDigest.getInstance("SHA-256")
        instance.update(PLAIN_SECRET.toByteArray(UTF_8))
        val digest = instance.digest()

        val hexString = digest.joinToString(separator = "") {
            "%02x".format(it)
        }

        val trimmedHexString = hexString.take(SECRET_LENGTH_BYTES)

        trimmedHexString.toByteArray(UTF_8)
    }

    private val iv: ByteArray by lazy {
        "2007012007012007".toByteArray(UTF_8)
    }

    override fun encode(userId: Long): String {
        val plain = String.format(
            QrDataGenerator.UNENCRYPTED_FORMAT,
            System.currentTimeMillis(),
            userId,
        )

        return Base64
            .encodeToString(
                encryptText(plain),
                0,
            )
    }

    override fun getIdFrom(base64: String)
        = decryptText(Base64.decode(base64, 0))
            .split(" ")
            .last()
            .toLong()

    private fun decryptText(bArr: ByteArray): String {
        val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
        instance.init(
            DECRYPT_MODE,
            SecretKeySpec(secret, "AES"),
            IvParameterSpec(iv),
        )

        val doFinal = instance.doFinal(bArr)
        return doFinal.toString(UTF_8)
    }

    private fun encryptText(str: String): ByteArray {
        val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
        instance.init(
            ENCRYPT_MODE,
            SecretKeySpec(secret, "AES"),
            IvParameterSpec(iv),
        )

        val bytes = str.toByteArray(UTF_8)
        return instance.doFinal(bytes)
    }

}
