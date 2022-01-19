# Deep Link Converter

Linkleri, mobil ve web uygulamaları için web url ile deeplink arasında dönüştüren bir web servistir.

## Technologies
* JDK 11
* Java 11
* Kotlin 1.4.32
* Kotlinx Serialization 1.1.0
* Spring Boot 2.4.5
* Maven 3.6.3
* Mysql 8.0
* Docker Compose

## Architecture
* Hexagonal Architecture

## Design Pattern
* Strategy Design Pattern

## Algorithm
* Linkleri web url ile deeplink arasında dönüştürülme işlemi belirli kurallara bağlı şekilde yapılmaktadır. Bu kurallar bir şema haline getirilerek standart geliştirildi. Link, bu şemadaki hangi kural ile eşleşiyor ise dinamik olarak veriler ile değiştirilerek istenilen tipteki linke dönüştürülür. Şemada, “Path”,  “Query” ve “Default” koşul tipleri ile sayısına, sıralamasına ve Regex patternına göre yeni kural tanımlaması yapılabilir.

## Build and run project with Docker Compose
* “app” ve “db” isminde iki service üzerine kurgulanmıştır. “app” servisi 8080 portundan, “db” servisi 3306 portundan çalışmaktadır. Bu portlar kullanılıyor ise uygun port ile “docker-compose.yml” ve “application.properties” dosyalarında değişiklik yapılması.

### Build

* Dependency indirilmesi ve projenin build edilmesi.

```
mvn clean install -DskipTests
```

* Projenin docker image haline getirilmesi.

```
docker build --tag=link-converter:latest .
```

### Run

* Docker compose ile “app” ve “db” servislerinin çalıştırılması.

```
docker-compose up
```

### Mysql Database Info
* “link_conversion” veritabanı altında ve conversions tablosu oluşturulduğunun kontrol edilmesi.
> DATASOURCE_URL: “127.0.0.1:3306”

> MYSQL_DATABASE: “link_conversion”

> MYSQL_USER: “med”

> MYSQL_PASSWORD: “password”

> TABLE_NAME: ”conversions”

### Stop Service
* Servislerin durdurulması ve kalıntıların temizlenmesi.

```
docker-compose down -v
```

* Not: Mysql servisi çalışırken oluşturulan veritabanı ve tabloda değişiklik yapılır ise yeni değişikliğin geçerliliği için kalıntıların temizlenmesi önemlidir. 


## Web Url to Deeplink Endpoint

> Request Type: GET

> Request Url: http://127.0.0.1:8080/link/convert/webURL/to/deeplink

> Request Param: webUrl: String

> Request Sample: http://127.0.0.1:8080/link/convert/webURL/to/deeplink?webUrl=https://www.med.com/koton/kadin-gomlek-p-1925869

## Deeplink to Web Url Endpoint

> Request Type: GET

> Request Url: http://127.0.0.1:8080/link/convert/deeplink/to/webURL

> Request Param: deeplink: String

> Request Sample: http://127.0.0.1:8080/link/convert/deeplink/to/webURL?deeplink=ty://?Page=Search%26Query=ütü


## Run Unit Tests With Docker Compose
* Database bağlantısı gereksinimi olduğundan servisler çalışır durumdayken test edilmesi gerekmektedir. 

* Teminal komutu ile test edilmesi

```
mvn test
```

* Doğrudan IntelliJ IDEA üzerinden de testler çalıştırılabilir.

## Run Unit Tests Without Docker Compose
* Diğer bir yöntem olarak var olan bir Mysql server bilgilerini “application.properties” dosyasında değiştirerek yapılabilir.

```
mvn test
```
---

Teşekkürler :)
