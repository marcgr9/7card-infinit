package ro.marc.sevencard.generator.impl

import android.util.Base64
import ro.marc.sevencard.generator.QrDataGenerator
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Arrays
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.internal.Intrinsics
import kotlin.text.Charsets.UTF_8

class SevenCardQrDataGeneratorImpl: QrDataGenerator {

    companion object {
        private const val PLAIN_SECRET = "salamdesibiu"
        private const val SECRET_LENGTH = 32
    }

    override fun encode(userId: Long): String {
        val plain = String.format(
            QrDataGenerator.UNENCRYPTED_FORMAT,
            System.currentTimeMillis(),
            userId,
        )

        return Base64
            .encodeToString(
                encryptText(
                    plain,
                    generateSecret(),
                    "2007012007012007".toByteArray(UTF_8),
                ),
                0,
            )
    }

    override fun getIdFrom(base64: String): String {
        return decryptText(
            Base64.decode(base64, 0),
            generateSecret(),
            "2007012007012007".toByteArray(UTF_8),
        ).split(" ").last()
    }

    private fun decryptText(bArr: ByteArray, bArr2: ByteArray?, bArr3: ByteArray?): String {
        val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
        instance.init(2, SecretKeySpec(bArr2, "AES"), IvParameterSpec(bArr3))
        val doFinal = instance.doFinal(bArr)
        return String(doFinal, UTF_8)
    }
    private fun encryptText(str: String, bArr: ByteArray?, bArr2: ByteArray?): ByteArray {
        val bArr3: ByteArray
        val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
        if (bArr2 != null) {
            bArr3 = bArr2
        } else {
            bArr3 = ByteArray(16)
            SecureRandom().nextBytes(bArr3)
        }
        instance.init(1, SecretKeySpec(bArr, "AES"), IvParameterSpec(bArr3))
        val bytes = str.toByteArray(UTF_8)

        val doFinal = instance.doFinal(bytes)
        if (bArr2 == null) {
            Intrinsics.checkExpressionValueIsNotNull(doFinal, "encrypted")
            return bArr3.plus(doFinal)
        }
        Intrinsics.checkExpressionValueIsNotNull(doFinal, "encrypted")
        return doFinal
    }

    private fun generateSecret(): ByteArray {
        val instance = MessageDigest.getInstance("SHA-256")
        instance.update(PLAIN_SECRET.toByteArray(UTF_8))
        val digest = instance.digest()

        val hexString = digest.joinToString(separator = "") {
            "%02x".format(it)
        }

        val trimmedHexString = hexString.take(SECRET_LENGTH)

        return trimmedHexString.toByteArray(UTF_8)
    }

}
