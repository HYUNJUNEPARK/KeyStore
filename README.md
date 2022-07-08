
//https://linsoo.pe.kr/archives/28119

keystore
https://maivve.tistory.com/167




KeyGenParameterSpec
```kotlin
val parameterSpec = KeyGenParameterSpec.Builder(
    MainActivity.KEYSTORE_ALIAS,
    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
).run {
    setBlockModes(KeyProperties.BLOCK_MODE_CBC)
    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
    setDigests(KeyProperties.DIGEST_SHA256)
    setUserAuthenticationRequired(false)
    build()
}

```





CBC(Cipher-Block Chaining, 암호 블록 체인)
-최초 평문 한 블럭과 IV 를 XOR 연산한 다음 암호화하고 다음 블럭은 앞에서 암호화된 결과 블러과 XOR 연산하여 다시 암호화 이 과정을 끝까지 반복
-초기화 벡터가 같은 경우 출력 결과가 항상 같기 때문에, 매 암호화마다 다른 초기화 벡터를 사용해야 함

IV(initialization vector, 초기화 벡터)
-첫 블록을 암호화할 때 사용되는 값

PKCS5Padding
-블록사이즈가 16바이트 크기로 16바이트의 배수가 아니라면 마지막 블록의 빈 부분을 채워주는 패딩옵션





초기화 벡터 위키백과
https://ko.wikipedia.org/wiki/%EC%B4%88%EA%B8%B0%ED%99%94_%EB%B2%A1%ED%84%B0

현대 대칭키 암호를 이용한 암호화 기법
https://yjshin.tistory.com/entry/%EC%95%94%ED%98%B8%ED%95%99-%ED%98%84%EB%8C%80-%EB%8C%80%EC%B9%AD%ED%82%A4-%EC%95%94%ED%98%B8%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%95%94%ED%98%B8%ED%99%94-%EA%B8%B0%EB%B2%95-ECB-CBC-CFB-OFB-CTR-%EB%AA%A8%EB%93%9C