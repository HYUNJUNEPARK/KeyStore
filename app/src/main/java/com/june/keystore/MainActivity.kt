package com.june.keystore

import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.june.keystore.databinding.ActivityMainBinding
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEYSTORE_ALIAS = "mKey"
        const val KEYSTORE_TYPE = "AndroidKeyStore"
    }
    //https://linsoo.pe.kr/archives/28119

    private var secretKey : SecretKey? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var keyStore : KeyStore
    private lateinit var iv: ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        keyStore = KeyStore.getInstance(KEYSTORE_TYPE).apply {
            load(null)
        }
        iv = ByteArray(16)

        initIsKeyView()
    }


    fun messageSendButtonClicked(v: View) {
        if(secretKey == null) {
            Toast.makeText(this, "키 필요", Toast.LENGTH_SHORT).show()
            return
        }

        val userInput = binding.messageEditText.text.toString()
        binding.userMessageTextView.text = userInput
        encryption(userInput)
    }

    private fun encryption(userInput: String) {
        val cipher_enc = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher_enc.init(Cipher.ENCRYPT_MODE, secretKey)
        //에러발생
        //cipher_enc.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        iv = cipher_enc.iv
        val byteEncryptedText = cipher_enc.doFinal(userInput.toByteArray())
        binding.encryptionTextView.text = String(Base64.encode(byteEncryptedText, Base64.DEFAULT))

        decryption(byteEncryptedText)
    }

    private fun decryption(byteEncryptedText: ByteArray) {
        val cipher_dec = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher_dec.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        val byteDecryptedText = cipher_dec.doFinal(byteEncryptedText)
        binding.decryptionTextView.text = String(byteDecryptedText)
    }


    fun keyGenButtonClicked(v: View) {
        try {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_TYPE)
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
            secretKey = keyGenerator.generateKey()
            Toast.makeText(this, "키 생성", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            Toast.makeText(this, "키 생성 실패", Toast.LENGTH_SHORT).show()
        }
        initIsKeyView()
    }

    fun keyLoadButtonClicked(v: View) {
        val secretKeyEntry = keyStore.getEntry(KEYSTORE_ALIAS, null) as KeyStore.SecretKeyEntry
        secretKey = secretKeyEntry.secretKey

        initIsKeyView()
    }

    fun keyStoreKeyDeleteButtonClicked(v: View) {
        keyStore.deleteEntry(KEYSTORE_ALIAS)
        secretKey = null

        initIsKeyView()
    }

    fun loadedKeyDeletedButtonClicked(v: View) {
        secretKey = null

        initIsKeyView()
    }

    private fun initIsKeyView() = with(binding) {
        if (secretKey != null) {
            keyStateTextView.text = "Applied"
            appliedKeyDeleteButton.isEnabled = true
        }
        else {
            keyStateTextView.text = "Null"
            appliedKeyDeleteButton.isEnabled = false
        }

        if (keyStore.containsAlias(KEYSTORE_ALIAS)) {
            keyStoreTextView.text = "Updated"
            keyLoadButton.isEnabled = true
            keyStoreKeyDeleteButton.isEnabled = true
        }
        else {
            keyStoreTextView.text = "Null"
            keyLoadButton.isEnabled = false
            keyStoreKeyDeleteButton.isEnabled = false
        }
    }
}