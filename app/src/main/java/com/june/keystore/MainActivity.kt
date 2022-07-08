package com.june.keystore

import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.june.keystore.databinding.ActivityMainBinding
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEYSTORE_ALIAS = "mKey"
        const val KEYSTORE_TYPE = "AndroidKeyStore"
        const val CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding"
        var secretKey : SecretKey? = null
        lateinit var iv: ByteArray
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initKeyView()
    }

    fun keyGenButtonClicked(v: View) {
        try {
            secretKey = KeyUtil().secretKeyGen()
            Toast.makeText(this, "키 생성", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            Toast.makeText(this, "키 생성 실패", Toast.LENGTH_SHORT).show()
        }
        initKeyView()
    }

    fun keyLoadButtonClicked(v: View) {
        secretKey = KeyUtil().secretKeyFromKeyStore()
        initKeyView()
    }

    fun keyStoreKeyDeleteButtonClicked(v: View) {
        KeyUtil().deleteKeyStoreSecretKey()
        secretKey = null
        initKeyView()
    }

    fun loadedKeyDeletedButtonClicked(v: View) {
        secretKey = null
        initKeyView()
    }

    fun messageSendButtonClicked(v: View) {
        if(secretKey == null) {
            Toast.makeText(this, "키 필요", Toast.LENGTH_SHORT).show()
            return
        }

        val userInput = binding.messageEditText.text.toString()
        binding.userMessageTextView.text = userInput

        val encryptionText: ByteArray = AESUtil().encryption(userInput)
        binding.encryptionTextView.text = String(Base64.encode(encryptionText, Base64.DEFAULT))

        val decryptionText: ByteArray = AESUtil().decryption(encryptionText)
        binding.decryptionTextView.text = String(decryptionText)
    }


    private fun initKeyView() = with(binding) {
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
}