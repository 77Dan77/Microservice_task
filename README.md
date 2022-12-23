# Тестовое задание Junior Java разработчика Solva.kz

- Проект написан на Java 11 .
- Для хранения данных использована база данных PostgreeSQL.
- Для создания API использована REST архитектура.
- Для управления локальной бд использовался pgAdmin4.
- Для тестирования API - Postman.
- Для сохранения миграций - Liquibase(+ плагин liquibase-maven-plugin).
- Для получения курса валют использовался сервис twelvedata.
- Для тестирования - JUnit5.
- Для сохранения тестовых данных - встроенная бд h2.
- Для генерации и управления данными - Hibernate, JPA.

###Что позволяет делать система?
 - Получать актуальный на момент запроса курс валютной пары KZT/USD, сохраняя его в базу данных.
 - Принимать транзакции от пользователей и в зависисмости от валюты транзакции, при необходимости, проводить ее конвертацию.
 - Хранить лимиты пользователей в USD, отдельно для двух категорий трат: товаров и услуг.
 - Пемечать транзакции превысившие лимит флагом limit_exceeded.
 - Динамически изменять оставшийся лимит пользователя в зависимости от категории затрат после проведенной транзакции.
 - Позволяет пользователю изменить текущий лимит в зависимости от категории расходов(отдельно изменяется лимит на товары, и отдельно лимит на услуги).
 - Позволяет пользователю запрашивать список транзакций, первысивших лимит.
 
###Классы, и их предназначение:
  - ####Модели:
    - [Currency](src/main/java/daniyal/teast_task/models/Currency.java) - Модель для хранения текущего курса валют
    - [Limit](src/main/java/daniyal/teast_task/models/Limit.java) - Модель для хранения лимитов полльзователя
    - [ReplyTransaction](src/main/java/daniyal/teast_task/models/ReplyTransaction.java) - Класс для ответа на запрос пользователя 
    - [Transaction](src/main/java/daniyal/teast_task/models/Transaction.java) - Модель совершаемой пользователем транзакции
    - [User](src/main/java/daniyal/teast_task/models/User.java) - Модель пользователя
  - ####JPA репозитории
    - [CurrencyRepository](src/main/java/daniyal/teast_task/repository/CurrencyRepository.java) - Реализует методы JPA репозитория, и кастомные JPQL запросы в БД для *Currency*
    - [LimitRepository](src/main/java/daniyal/teast_task/repository/LimitRepository.java) - Реализует методы JPA репозитория, и кастомные JPQL запросы в БД для *Limit*
    - [TransactionRepository](src/main/java/daniyal/teast_task/repository/TransactionRepository.java) - Реализует методы JPA репозитория, и кастомные JPQL запросы в БД для *Transaction*
    - [UserRepository](src/main/java/daniyal/teast_task/repository/UserRepository.java) - Реализует методы JPA репозитория, и кастомные JPQL запросы в БД для *User*
  - ####Сервисы
    - [CurrencyService](src/main/java/daniyal/teast_task/service/CurrencyService.java) - вызывает методы *CurrencyRepository*, используется непосредственно в бизнес-логике, связанной с Currency
    - [LimitService](src/main/java/daniyal/teast_task/service/LimitService.java) - вызывает методы *LimitRepository*, используется непосредственно в бизнес-логике, связанной с Limit
    - [TransactionService](src/main/java/daniyal/teast_task/service/TransactionService.java) - вызывает методы *TransactionRepository*, используется непосредственно в бизнес-логике, связанной с Transaction
    - [UserService](src/main/java/daniyal/teast_task/service/UserService.java) - вызывает методы *UserRepository*, используется непосредственно в бизнес-логике, связанной с User
  - ####Основные
    - [Runner](src/main/java/daniyal/teast_task/Runner.java) - Используется как основной запускаемый(здесь проверялась логика, создавались объекты)
    - [TeastTaskApplication](src/main/java/daniyal/teast_task/TeastTaskApplication.java) - Main класс приложения
  - ####Контроллеры
    - [ClientController](src/main/java/daniyal/teast_task/controller/ClientController.java) - Контроллер API клиента, реализует запросы клиента(получить список транзакций, изменить лимит и тд.)
    - [CurrencyController](src/main/java/daniyal/teast_task/controller/CurrencyController.java) - Контроллер вызова внешнего API, для получения курса валют
    - [TransactionController](src/main/java/daniyal/teast_task/controller/TransactionController.java) - Контроллер API Транзакций, реализует создание транзакций и сопуствующие изменения других данных(остаток лимита)

###Файлы:
 - [application.properties](src/main/resources/application.properties) - основной конфигурационный файл, для подключения к PostgreeSQL, настройки  Hibernate и Liquibase
 - [application-test.properties](src/main/resources/application-test.properties) - вспомогательный конф. файл для тестирования (*Profile - test*), настравивает и делает активной БД h2(только во время запусков тестов) 
 - [liquibase.properties](src/main/resources/liquibase.properties) - файл для настройки Liquibase, нужен для работы liquibase-maven-plugin
 - [liquibase-outputChangeLog.xml](src/main/resources/liquibase-outputChangeLog.xml) - файл миграций, хранит миграции таблиц бд
 - [user-insertion.sql](src/main/resources/db/changelog/user-insertion.sql) - файл миграций, хранит данные для вставки в таблицы бд


##API
###Client API
- Получение всех лимитов конкретного пользователя:
  - GET http://localhost:9009/api/client/getLimit/{{accNumber}}  - *accNumber*(int) - номер банковского счета пользователя, список лимитов которого нужно получить(пример - **123456789**)
  - CURL:
  ````
  curl --location --request GET 'http://localhost:9009/api/client/getLimit/123456789'
  ````

- Получние списка транзакций, которые превысили лимит, для конкретного пользователя:
  - GET http://localhost:9009/api/client/getLimitExceededTransactions/{{accNumber}}  - *accNumber*(int) - номер банковского счета пользователя
  - CURL:
  ````
  curl --location --request GET 'http://localhost:9009/api/client/getLimitExceededTransactions/123456789'
  ````

- Обновление лимита по категории затрат(отдельно для **service** и для **goods**) конкретного пользователя
  - PUT http://localhost:9009/api/client/updateLimit/{{accNumber}}/{{excCat}}  - *accNumber*(int) - номер банковского счета пользователя, *excCat*(String) - категория затрат лимита(**service** или **goods**) 
  - Пример тела JSON запроса:
  ````
    2000.0
    ````
  - CURL:
  ````
  curl --location --request PUT 'http://localhost:9009/api/client/updateLimit/123456789/service' \
  --header 'Content-Type: application/json' \
  --data-raw '5000.0'
  ````
###Transactions API
- Создание транзакции для конкретного пользователя(имитация того, что пользователь совершил покупку)
  - POST http://localhost:9009/api/transactions/createTransaction/{{accNumber}} - *accNumber*(int) - номер банковского счета пользователя
  - Пример тела JSON запроса:
  ````
    {
    "accountTo":31234,
    "currencyShortname":"KZT",
    "sum":206000,
    "expense_category":"service"
    }
    ````
  - CURL:
  ````
  curl --location --request POST 'http://localhost:9009/api/transactions/createTransaction/123456789' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "accountTo":31234,
    "currencyShortname":"KZT",
    "sum":206000,
    "expense_category":"service"
  }'
  ````

##Как заупустить проект?
- Скачать проект с гит
- Создать пустую реляционную базу данных(MySQL, PostgreSQL etc.), в моем случае это PostgreSQL, используя любую СУБД
- Открыть проект в среде разработки, и в файле src/main/resources/application.properties и относительно своего типа БД изменить поля:
````
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect   #Диалект БД для hibernate 
spring.datasource.url=jdbc:postgresql://localhost:5432/Bank_DB                      #URL и название БД  
spring.datasource.username=postgres                                                 #Имя пользователя БД 
spring.datasource.password=Www.dan.com7                                             #Пароль от БД 
````
- Поставить флаг **spring.liquibase.enabled=true** для применения миграций(создание таблиц и заполение данными)
- Запустить проект
    - После запуска создадутся и успешного пдключения к бд, создадутся таблицы и добавятся 3 записи - Пользователь и два его базовых лимита. 
    Банковский счет пользователя - 123456789.
    Имя - Dan.
- Далее с помощью Postman/Swagger/Jmeter можно протестировать приведенные выше API

###Возможные ошибки
- Несовместимость версий Java
- Ошибки в изменении application.properties, при использовании отличной от использованной здесь БД(Не забыть поменять название БД) 
