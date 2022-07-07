package com.june.keystore

import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.june.keystore.databinding.ActivityMainBinding
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEYSTORE_ALIAS = "mKey"
        const val KEYSTORE_TYPE = "AndroidKeyStore"
    }

    private var secretKey : SecretKey? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var iv: ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //TODO 왜 16 ???????????
        iv = ByteArray(16)

        initIsKeyView()
    }

    fun keyGenButtonClicked(v: View) {
        try {
            secretKey = KeyUtil().secretKeyGen()
            Toast.makeText(this, "키 생성", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            Toast.makeText(this, "키 생성 실패", Toast.LENGTH_SHORT).show()
        }
        initIsKeyView()
    }

    fun keyLoadButtonClicked(v: View) {
        secretKey = KeyUtil().secretKeyFromKeyStore()
        initIsKeyView()
    }

    fun keyStoreKeyDeleteButtonClicked(v: View) {
        KeyUtil().deleteKeyStoreSecretKey()
        secretKey = null
        initIsKeyView()
    }

    fun loadedKeyDeletedButtonClicked(v: View) {
        secretKey = null
        initIsKeyView()
    }


    fun messageSendButtonClicked(v: View) {
        if(secretKey == null) {
            Toast.makeText(this, "키 필요", Toast.LENGTH_SHORT).show()
            return
        }

        val userInput = binding.messageEditText.text.toString()
        binding.userMessageTextView.text = userInput
        //TODO
        encryption(userInput)

        //TODO
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

        val keyStore = KeyUtil().keyStore
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


    private fun encryption(userInput: String) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        //에러발생
        //cipher_enc.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        iv = cipher.iv
        val byteEncryptedText = cipher.doFinal(userInput.toByteArray())
        binding.encryptionTextView.text = String(Base64.encode(byteEncryptedText, Base64.DEFAULT))

        decryption(byteEncryptedText)
    }

    private fun decryption(byteEncryptedText: ByteArray) {
        val cipher_dec = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher_dec.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        val byteDecryptedText = cipher_dec.doFinal(byteEncryptedText)
        binding.decryptionTextView.text = String(byteDecryptedText)
    }
}