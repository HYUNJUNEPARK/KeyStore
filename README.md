<img src="https://github.com/HYUNJUNEPARK/ImageRepository/blob/master/AES/keyStore.png" height="400"/>

---
1. <a href = "#content1">keystore</a></br>
2. <a href = "#content2">CBC/IV/Padding</a></br>
* <a href = "#ref">참고링크</a>
---
><a id = "content1">**1. keystore**</a></br>


-java.security.KeyStore 내장 라이브러리</br>
-안드로이드 컨테이너에 암호키, IV 등을 저장해두고 암복호화 시 가져옴</br>
-컨테이너에 키를 저장하면 앱단에서 직접 접근이 어려워지며 시스템 측에서만 접근이 가능해져 보안성이 높아짐</br>
-Keystore 자체는 사용자의 lockscreen pin/password로 암호화 되어 있기 때문에, 화면 잠금 장치로 lock 되어 있는 경우는 Keystore 자체를 access 할 수 없음</br>

<br></br>
<br></br>

><a id = "content2">**2. CBC/IV/Padding**</a></br>

CBC(Cipher-Block Chaining, 암호 블록 체인)
-최초 평문 한 블럭과 IV 를 XOR 연산한 다음 암호화하고 다음 블럭은 앞에서 암호화된 결과 블러과 XOR 연산하여 다시 암호화 이 과정을 끝까지 반복
-초기화 벡터가 같은 경우 출력 결과가 항상 같기 때문에, 매 암호화마다 다른 초기화 벡터를 사용해야 함
<br></br>

IV(initialization vector, 초기화 벡터)
-첫 블록을 암호화할 때 사용되는 값
<br></br>

PKCS5Padding
-블록사이즈가 16바이트 크기로 16바이트의 배수가 아니라면 마지막 블록의 빈 부분을 채워주는 패딩옵션
<br></br>
<br></br>
---

><a id = "ref">**참고링크**</a></br>

AES/CBC 암복호화 참고 코드</br>
http://www.fun25.co.kr/blog/java-aes128-cbc-encrypt-decrypt-example</br>

Android KeyStore 와 Android Keystore</br>
https://blog.naver.com/PostView.nhn?blogId=aepkoreanet&logNo=221429089807&categoryNo=0&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView</br>

초기화 벡터 위키백과</br>
https://ko.wikipedia.org/wiki/%EC%B4%88%EA%B8%B0%ED%99%94_%EB%B2%A1%ED%84%B0</br>

현대 대칭키 암호를 이용한 암호화 기법</br>
https://yjshin.tistory.com/entry/%EC%95%94%ED%98%B8%ED%95%99-%ED%98%84%EB%8C%80-%EB%8C%80%EC%B9%AD%ED%82%A4-%EC%95%94%ED%98%B8%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%95%94%ED%98%B8%ED%99%94-%EA%B8%B0%EB%B2%95-ECB-CBC-CFB-OFB-CTR-%EB%AA%A8%EB%93%9C</br>














