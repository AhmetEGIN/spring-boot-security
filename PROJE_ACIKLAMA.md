# 🔐 Spring Boot Security Projesi - Detaylı Açıklama

Bu proje, **JWT (JSON Web Token)** tabanlı kimlik doğrulama ve yetkilendirme sistemi uygulayan bir **Spring Boot 3.5.9** uygulamasıdır. OAuth2 Resource Server kullanarak token bazlı güvenlik sağlar.

---

## 📁 Proje Yapısı ve Dosyalar

### **1. Konfigürasyon Dosyaları**

#### `pom.xml` (Maven Proje Dosyası)
- **Amaç**: Projenin bağımlılıklarını ve yapısını tanımlar
- **Ana Bağımlılıklar**:
  - `spring-boot-starter-web` - REST API oluşturma
  - `spring-boot-starter-security` - Güvenlik işlemleri
  - `spring-boot-starter-data-jpa` - Veritabanı işlemleri
  - `spring-boot-starter-oauth2-resource-server` - OAuth2 token doğrulama
  - `jjwt` (JSON Web Token kütüphanesi) - JWT token oluşturma ve doğrulama
  - `mysql-connector-j` - MySQL veritabanı sürücüsü
  - `lombok` - Boilerplate kod azaltma
  - `spring-security-test` - Güvenlik testleri

#### `docker-compose.yml`
- **Amaç**: MySQL 8.0 veritabanı konteynerini çalıştırır
- **Ayarlar**:
  - Container adı: `mysql_database`
  - Port: 3306
  - Veritabanı: `spring-boot-security`
  - Kullanıcı: `egin` / Şifre: `test`
  - Kalıcı veri: `./db_data` klasöründe kaydedilir

#### `application.properties`
- **Veritabanı Bağlantısı**:
  - URL: `jdbc:mysql://localhost:3306/spring-boot-security`
  - JPA otomatik schema güncellemesi etkindir
  - SQL sorguları konsolda gösterilir

---

## 🏗️ Proje Mimarisi

### **2. Ana Uygulama Dosyası**

#### `SpringBootSecurityApplication.java`
- **Amaç**: Spring Boot uygulamasının giriş noktası
- **İşlev**: `@SpringBootApplication` anotasyonu ile tüm Spring konfigürasyonlarını etkinleştir

---

### **3. AUTH Modülü (Kimlik Doğrulama)**

#### **A. Config (Konfigürasyon)**

##### `SecurityConfig.java`
- **Amaç**: Güvenlik yapılandırmasını tanımlar
- **Ana Özellikler**:
  - **CSRF Devre Dışı**: Stateless API için CSRF koruması kapatılır
  - **CORS Yapılandırması**: Tüm originlere izin verir
  - **Yetkilendirme Kuralları**:
    - POST `/api/v1/authentication/**` - Herkese açık (login, refresh token)
    - Diğer istekler - Kimlik doğrulama gerekli
  - **Session Yönetimi**: STATELESS (her istek bağımsız)
  - **Özel Filter**: `CustomBearerTokenAuthenticationFilter` Bearer token işlenmesi için
  - **Password Encoder**: BCrypt kullanılır
  - **Authentication Entry Point**: Auth hataları özel JSON formatında döner

##### `TokenConfigurationParameter.java`
- **Amaç**: JWT token oluşturma için gerekli parametreleri yönetir
- **İçerdikleri**:
  - **Issuer**: Token vericisi (kim tarafından oluşturulduğu)
  - **Access Token Süresi**: Kaç dakika geçerli (genellikle 15-30 dakika)
  - **Refresh Token Süresi**: Kaç gün geçerli (genellikle 7 gün)
  - **Public Key**: Token doğrulama için
  - **Private Key**: Token imzalama için

#### **B. Model (Veri Modelleri)**

##### `Token.java`
- **Amaç**: Token bilgilerini taşır
- **Alanlar**:
  - `accessToken` - Kısa ömürlü erişim tokenı
  - `refreshToken` - Uzun ömürlü yenileme tokenı
  - `accessTokenExpiresAt` - Token sona erme zamanı
- **Yardımcı Metodlar**:
  - `isBearerToken()` - Bearer token mi kontrol eder
  - `getJwt()` - "Bearer " ön ekini kaldırır

##### `Identity.java`
- **Amaç**: Şu anki kimlik doğrulanmış kullanıcının bilgilerini sağlar
- **Scope**: Request-scoped (her istek için yeni)
- **Metodlar**:
  - `getAccessToken()` - Şu anki token'ı alır
  - `getUserId()` - Şu anki kullanıcının ID'sini alır

##### `InvalidTokenEntity.java` (JPA Entity)
- **Amaç**: Geçersiz kılınan tokenları veritabanında saklar
- **Tablo**: `invalid_tokens`
- **Alanlar**:
  - `id` - Benzersiz ID (UUID)
  - `tokenId` - Geçersiz kılınan tokenın ID'si

#### **C. DTO (Data Transfer Objects)**

##### `LoginRequest.java`
- **Amaç**: Login endpoint'ine gelen veriyi taşır
- **Alanlar**:
  - `email` (@NotBlank) - Kullanıcı e-postası
  - `password` (@NotBlank) - Kullanıcı şifresi

##### `TokenResponse.java`
- **Amaç**: Token endpoint'inin çıkış verisini taşır
- **Alanlar**:
  - `accessToken` - Erişim tokenı
  - `refreshToken` - Yenileme tokenı
  - `accessTokenExpiresAt` - Token sona erme zamanı (Unix timestamp)

##### `TokenRefreshRequest.java` ve `TokenInvalidateRequest.java`
- Token yenileme ve geçersiz kılma istekleri için (klasörlerde tanımlı)

#### **D. Servisler (İş Mantığı)**

##### `TokenService` (Interface)
- **Metodlar**:
  - `generateToken()` - Yeni access + refresh token oluştur
  - `getAuthentication()` - JWT'den Spring Security authentication nesnesi al
  - `verifyAndValidate()` - Token'ın geçerli olduğunu kontrol et
  - `getClaims()` - Token içeriğini al
  - `getId()` - Token ID'sini al

##### `TokenServiceImpl.java` (Uygulama)
- **JWT Oluşturma**: JJWT kütüphanesi kullanarak RS256 (RSA) imzalama
- **Token Bilgileri**:
  - Access Token: 15-30 dakika geçerli
  - Refresh Token: 7 gün geçerli
  - Token ID: UUID (benzersiz kimlik)
- **Claim'ler** (Token içinde saklanan veri):
  - `userId` - Kullanıcı ID'si
  - Diğer kullanıcı bilgileri

##### `InvalidTokenService` (Interface)
- **Metodlar**:
  - `invalidateTokens()` - Token'ları geçersiz kıl (logout)
  - `checkForInvalidityOfToken()` - Token geçersiz mi kontrol et

##### `InvalidTokenServiceImpl.java` (Uygulama)
- **Logout İşlemi**: Token ID'sini veritabanına kaydederek geçersiz kıl
- **Token Doğrulama**: Token veritabanında varsa hata fırlat

#### **E. Filter (İstek Filtreleri)**

##### `CustomBearerTokenAuthenticationFilter.java`
- **Amaç**: Her HTTP isteğinde Bearer token'ı doğrular
- **İşlem Sırası**:
  1. Authorization header'ından token'ı çıkart
  2. Token'ın geçerli olduğunu kontrol et
  3. Token'ın geçersiz kılınmış olup olmadığını kontrol et
  4. Token'dan kullanıcı bilgilerini çıkart
  5. Spring Security context'ine kimlik doğrulamasını kaydet

#### **F. Security (Güvenlik)**

##### `CustomAuthenticationEntryPoint.java`
- **Amaç**: Kimlik doğrulama hatalarının JSON formatında döndürülmesini sağlar
- **HTTP Status**: 401 Unauthorized
- **Yanıt Formatı**: CustomError nesnesi (başlık, mesaj, timestamp)

#### **G. Repository (Veritabanı)**

##### `InvalidTokenRepository.java`
- **Amaç**: Geçersiz tokenları veritabanında sorgulamak
- **Metodlar**:
  - `findByTokenId()` - Token ID'sine göre ara

#### **H. Enums (Sabitler)**

- **`TokenType`** - Token türü (BEARER)
- **`TokenClaims`** - Token içinde kullanılan claim adları
- **`UserType`** - Kullanıcı türü (ADMIN, USER, vb.)
- **`UserStatus`** - Kullanıcı durumu (ACTIVE, BLOCKED, vb.)
- **`ConfigurationParameters`** - Konfigürasyon parametreleri (issuer, key'ler, süreler)

#### **I. Utilities (Yardımcı Sınıflar)**

##### `KeyConverter.java`
- **Amaç**: PEM formatındaki RSA key'lerini Java Security key'lerine dönüştürür
- **Metodlar**:
  - `convertPublicKey()` - PEM public key → Java PublicKey
  - `convertPrivateKey()` - PEM private key → Java PrivateKey

#### **J. Mapper (Dönüştürücüler)**

##### `TokenToTokenResponseMapper.java`
- **Amaç**: Token nesnesini TokenResponse DTO'suna dönüştürür

#### **K. Exception (Hatalar)**

- **`PasswordNotValidException`** - Şifre geçersiz
- **`TokenAlreadyInvalidatedException`** - Token zaten geçersiz kılınmış
- **`UserStatusNotValidException`** - Kullanıcı durumu geçersiz

---

### **4. COMMON Modülü (Ortak Fonksiyonlar)**

#### **A. Exception**

##### `GlobalExceptionHandler.java`
- **Amaç**: Uygulamanın tamamında meydana gelen hataları merkezi olarak işler
- **Şu anki İşlevler**:
  - `@ExceptionHandler(MethodArgumentNotValidException)` - Form doğrulama hatalarını işler
  - Hata alanlarını ve mesajlarını JSON formatında döner

#### **B. Model**

##### `BaseModel.java`
- **Amaç**: Tüm JPA entityleri tarafından kalıtılan temel sınıf
- **Ortak Alanlar**:
  - `createdAt` - Oluşturma tarihi
  - `updatedAt` - Güncelleme tarihi
  - `createdBy` - Oluşturan kullanıcı
  - `updatedBy` - Güncelleyen kullanıcı

##### `CustomError.java`
- **Amaç**: Standardize hata yanıtı formatı
- **Alanlar**:
  - `httpStatus` - HTTP durum kodu
  - `header` - Hata kategorisi (Enum)
  - `message` - Hata mesajı
  - `timestamp` - Hata zamanı
  - `isSuccess` - Başarı durumu (false)
- **İç Sınıf**: `CustomSubError` - Alt hatalar için

---

### **5. USER, PRODUCT, ADMIN Modülleri**

- **Durum**: Henüz implementasyon aşamasında (kontroller boş)
- **Yapı**: Controller, Service, Repository, Model, Exception
- **Amaç**: Kullanıcı, Ürün ve Admin yönetimi işlemleri için kullanılacak

---

## 🔄 İş Akışları

### **Login Akışı**
```
1. İstemci POST /api/v1/authentication/login gönderir
   - LoginRequest: { email, password }

2. SecurityConfig login endpoint'ini permitAll (herkese açık) yapar

3. AuthService:
   - Kullanıcıyı email ile bulur
   - Şifreyi BCrypt ile doğrular
   - TokenService.generateToken() çağırır

4. TokenService:
   - Access Token (15-30 dakika) oluşturur
   - Refresh Token (7 gün) oluşturur
   - JJWT ile RS256 imzası atar

5. TokenResponse döner:
   - accessToken: "eyJ..."
   - refreshToken: "eyJ..."
   - accessTokenExpiresAt: 1704067200
```

### **İstek Doğrulama Akışı**
```
1. İstemci Authorization: Bearer <token> gönderir

2. CustomBearerTokenAuthenticationFilter:
   - Header'ından token'ı çıkartır
   - TokenService.verifyAndValidate() ile token'ı doğrular
   - InvalidTokenService.checkForInvalidityOfToken() ile geçersiz mi diye kontrol eder
   - Token'dan userId ve authorities al

3. SecurityContextHolder'a authentication kaydet

4. İstek protected endpoint'e ulaşır
```

### **Logout Akışı**
```
1. İstemci POST /api/v1/authentication/logout gönderir
   - Token ID'si gönderilir

2. InvalidTokenService.invalidateTokens():
   - Token ID'sini invalid_tokens tablosuna kaydeder

3. Sonraki isteklerde:
   - InvalidTokenService.checkForInvalidityOfToken():
   - Token ID database'de varsa TokenAlreadyInvalidatedException fırlatır
```

---

## 🔐 Güvenlik Özellikleri

### JWT Token Yapısı (RS256 - RSA)
```
Header:
{
  "alg": "RS256",
  "typ": "Bearer"
}

Payload:
{
  "jti": "uuid...",           // Token ID
  "iss": "com.egin",          // Issuer
  "iat": 1704067200,          // Issued at
  "exp": 1704068100,          // Expiration
  "userId": "user-123",       // Custom claim
  ...diğer bilgiler
}

Signature: RSA Private Key ile imzalanır
```

### Doğrulama
- Public Key ile token imzası doğrulanır
- Expiration zamanı kontrol edilir
- Invalid tokens tablosunda aranır

---

## 📊 Veritabanı

### Tablolar
- **invalid_tokens**: Geçersiz kılınan tokenlar
  - id (UUID)
  - tokenId (String)
  - createdAt, updatedAt
  - createdBy, updatedBy

---

## 🚀 Kullanılan Teknolojiler

| Teknoloji | Versiyon | Amaç |
|-----------|---------|------|
| Java | 17 | Programlama dili |
| Spring Boot | 3.5.9 | Framework |
| Spring Security | - | Güvenlik |
| Spring Data JPA | - | ORM |
| OAuth2 Resource Server | - | Token doğrulama |
| JJWT | 0.12.6 | JWT işlemleri |
| MySQL | 8.0 | Veritabanı |
| Lombok | 1.18.42 | Boilerplate azaltma |
| BouncyCastle | - | RSA key işlemleri |

---

## 📝 Örnek API İstekleri

### Login
```bash
POST /api/v1/authentication/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGc...",
  "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGc...",
  "accessTokenExpiresAt": 1704067200
}
```

### Protected Endpoint
```bash
GET /api/v1/users
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGc...

Response: 200 OK (kimlik doğrulanırsa)
Response: 401 Unauthorized (kimlik doğrulanmamış token)
```

### Token Yenileme
```bash
POST /api/v1/authentication/refresh
Content-Type: application/json

{
  "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGc..."
}

Response:
{
  "accessToken": "yeni access token...",
  "refreshToken": "aynı refresh token...",
  "accessTokenExpiresAt": 1704067800
}
```

### Logout
```bash
POST /api/v1/authentication/logout
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGc...

Response: 204 No Content
```

---

## 📌 Özet

Bu proje, **modern JWT tabanlı REST API güvenlik** uygulamasıdır. Access token ile API erişimi sağlanır, refresh token ile uzun süreli oturum yönetimi yapılır. Logout sırasında token'lar veritabanında kaydedilerek geçersiz kılınır. CORS, CSRF devre dışı bırakılmış, stateless session policy uygulanmıştır. Global exception handling ile merkezi hata yönetimi sağlanmaktadır.

