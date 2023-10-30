package ro.marc.sevencard.generator.impl

import android.util.Base64
import ro.marc.sevencard.generator.QrDataGenerator
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.internal.Intrinsics

class QrDataGeneratorImpl: QrDataGenerator {

    override fun encode(userId: Long): String {
        val plain = String.format(
            QrDataGenerator.UNENCRYPTED_FORMAT,
            System.currentTimeMillis(),
            userId,
        )

        return Base64
            .encodeToString(
                encryptText(
                    "1698656871533   302437",
                    "ab17be2db66a7f90d8b375bcfd0159af".toByteArray(Charsets.UTF_8),
                    "2007012007012007".toByteArray(Charsets.UTF_8),
                ),
                0,
            )
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
        val bytes = str.toByteArray(Charsets.UTF_8)

        val doFinal = instance.doFinal(bytes)
        if (bArr2 == null) {
            Intrinsics.checkExpressionValueIsNotNull(doFinal, "encrypted")
            return bArr3.plus(doFinal)
        }
        Intrinsics.checkExpressionValueIsNotNull(doFinal, "encrypted")
        return doFinal
    }

}