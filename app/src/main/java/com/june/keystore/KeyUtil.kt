package com.june.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.june.keystore.MainActivity.Companion.KEYSTORE_ALIAS
import com.june.keystore.MainActivity.Companion.KEYSTORE_TYPE
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeyUtil {
    val keyStore = KeyStore.getInstance(KEYSTORE_TYPE).apply {
        load(null)
    }

    fun secretKeyGen(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE_TYPE
        )
        val parameterSpec = KeyGenParameterSpec.Builder(
            KEYSTORE_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            setDigests(KeyProperties.DIGEST_SHA256)
            setUserAuthenticationRequired(false)
            build()
        }
        keyGenerator.init(parameterSpec)
        val secretKey = keyGenerator.generateKey()
        return secretKey
    }

    fun secretKeyFromKeyStore(): SecretKey {
        val secretKeyEntry = keyStore.getEntry(KEYSTORE_ALIAS, null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey
        return secretKey
    }

    fun deleteKeyStoreSecretKey() {
        keyStore.deleteEntry(KEYSTORE_ALIAS)
    }
}