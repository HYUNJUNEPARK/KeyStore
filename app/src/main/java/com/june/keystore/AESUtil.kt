package com.june.keystore

import android.util.Base64
import com.june.keystore.MainActivity.Companion.iv
import com.june.keystore.MainActivity.Companion.secretKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class AESUtil {
    //private lateinit var iv: ByteArray

    fun encryption(userInput: String): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            secretKey
        )
        iv = cipher.iv

        val byteEncryptedText = cipher.doFinal(userInput.toByteArray())
        return byteEncryptedText
//
//        decryption(byteEncryptedText)
    }

    fun decryption(byteEncryptedText: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            secretKey,
            IvParameterSpec(iv)
        )

        val byteDecryptedText = cipher.doFinal(byteEncryptedText)
        return String(byteDecryptedText)
    }
}