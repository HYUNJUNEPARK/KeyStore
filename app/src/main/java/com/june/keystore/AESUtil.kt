package com.june.keystore

import com.june.keystore.MainActivity.Companion.CIPHER_ALGORITHM
import com.june.keystore.MainActivity.Companion.iv
import com.june.keystore.MainActivity.Companion.secretKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class AESUtil {
    fun encryption(userInput: String): ByteArray {
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        cipher.init(
            Cipher.ENCRYPT_MODE,
            secretKey
        )
        iv = cipher.iv

        val byteEncryptedText = cipher.doFinal(userInput.toByteArray())
        return byteEncryptedText
    }

    fun decryption(byteEncryptedText: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        cipher.init(
            Cipher.DECRYPT_MODE,
            secretKey,
            IvParameterSpec(iv)
        )

        val byteDecryptedText = cipher.doFinal(byteEncryptedText)
        return byteDecryptedText
    }
}