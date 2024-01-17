# Explore with me
Платформа для публикации событий и поиска товарищей для посещения мероприятий.

<div>
      <img src="assets/landing.png" title="EWM landing" alt="explore with me landing"/>
</div>


## Функционал программы:

Explore with me - микросервисное приложение. В программу входят два микросервиса:
* main-service - основная часть приложения, содержащая бизнес-логику
* stats-service - сервис для сбора статистики просмотра событий по ip

Микросервисная архитектура Explore with me выглядит следующим образом:

<div>
      <img src="assets/micro-services architecture.png" title="microservice architecture" alt="microservice architecture"/>
</div>

Доступная функциональность зависит от уровней доступа пользователей:

### Неавторизованные пользователи
* просматривать все события, в том числе по категориям
* видеть детали отдельных событий
* видеть закрепленные подборки событий

### Авторизованные пользователи
* добавление в приложение новые мероприятия, редактировать их и просматривать после добавления
* подача заявок на участие в интересующих мероприятиях
* создатель мероприятия может подтверждать заявки, которые отправили другие пользователи сервиса

### Администраторы
* добавление, изменение и удаление категорий для событий
* добавление, удаление и закрепление на главной странице подборки мероприятий
* модерация событий, размещённых пользователями, — публикация или отклонение
* управление пользователями — добавление, активация, просмотр и удаление

## Инструкция по развёртыванию ▶️

1) Склонируйте репозиторий:
   https://github.com/Zholtikov-A/java-explore-with-me.git
2) Откройте программу Docker
3) В терминале или командной строке перейдите в папку проекта (где лежит файл docker-compose.yml) и выполните команду: docker-compose up
4) В программе Docker должны появиться 4 контейнера
5) Программа доступна по ниже описанному API

## API

Примеры использования программы можно увидеть в приложенных Postman тестах в директории: postman

### main-service

* POST /users/{userId}/events - добавить новое событие
* GET /users/{userId}/events/{eventId} - получить событие
* PATCH /users/{userId}/events/{eventId} - изменить событие
* GET /users/{userId}/events - получить события пользователя
* GET /users/{userId}/events/{eventId}/requests - получить запросы пользователя на участие в событии
* PATCH /users/{userId}/events/{eventId}/requests - изменить статус (подтверждение, отмена) заявок на участие пользователя в событии

* GET /categories - получить все категории
* GET /categories/{catId} - получить категорию

* GET /compilations - получить все подборки событий
* GET /compilations/{compId} - получить подборку событий

* GET /admin/events - получить события по любым параметрам:
    * users - список id пользователей
    * states - список статусов события (PENDING, PUBLISHED, CANCELED)
    * categories - список id категорий событий
    * rangeStart - начало временного отрезка в формате yyyy-MM-dd HH:mm:ss
    * rangeEnd - конец временного отрезка в формате yyyy-MM-dd HH:mm:ss
    * from - параметр для пагинации
    * size - параметр для пагинации
* PATCH /admin/events/{eventId} - изменить событие

* GET /events - получить события по любым параметрам:
    * text - текст для поиска в названии и описании событий
    * categories - список id категорий событий
    * paid - только платные события (true/false)
    * rangeStart - начало временного отрезка в формате yyyy-MM-dd HH:mm:ss
    * rangeEnd - конец временного отрезка в формате yyyy-MM-dd HH:mm:ss
    * onlyAvailable - только доступные события, т.е. у которых еще не исчерпан лимит участников (true/false)
    * sort - способ сортировки событий (EVENT_DATE, VIEWS)
    * from - параметр для пагинации
    * size - параметр для пагинации
* GET /events/{id} - получить событие

* POST /users/{userId}/requests - добавить запрос на участие в событии
* GET /users/{userId}/requests - получить запросы пользователя на участие в событиях
* PATCH /users/{userId}/requests/{requestId}/cancel - отменить запрос на участие в событии

* POST /admin/users - добавить пользователя
* GET /admin/users - получить всех пользователей
* DELETE /admin/users/{userId} - удалить пользователя
* POST /admin/compilations - добавить подборку событий
* DELETE /admin/compilations/{compId} - удалить подборку событий
* PATCH /admin/compilations/{compId} - обновить подборку событий
* POST /admin/categories - добавить новую категорию
* DELETE /admin/categories/{catId} - удалить категорию
* GET /admin/moderation - получить список событий, требующих модерации
* GET /admin/moderation/{modId} - получить модерацию
* POST /admin/{eventId}/moderation - создать модерацию

### stats-service

* GET /stats - Получение статистики по посещениям
* POST /hit - Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем

## Testing

В папке postman находятся коллекции тестов, запускаемых в следующем порядке:
1. EWM - Main service
2. EWM - API статистика
3. EWM - moderation enhancement

## 🛠 Tech & Tools

<div>
      <img src="https://github.com/Salaia/icons/blob/main/green/Java.png?raw=true" title="Java" alt="Java" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/SPRING%20boot.png?raw=true" title="Spring Boot" alt="Spring Boot" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/SPRING%20MVC.png?raw=true" title="Spring MVC" alt="Spring MVC" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/Maven.png?raw=true" title="Apache Maven" alt="Apache Maven" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/Rest%20API.png?raw=true" title="Rest API" alt="Rest API" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/Microservice.png?raw=true" title="Microservice" alt="Microservice" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/Hibernate.png?raw=true" title="Hibernate" alt="Hibernate" height="40"/>
      <img src="https://github.com/Salaia/icons/blob/main/green/Lombok.png?raw=true" title="Lombok" alt="Lombok" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/PostgreSQL.png?raw=true" alt="PostgreSQL" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/Postman.png?raw=true" title="Postman" alt="Git" height="40"/>
<img src="https://github.com/Salaia/icons/blob/main/green/Docker.png?raw=true" title="Docker" alt="Docker" height="40"/>
</div>

## Статус и планы по доработке проекта

На данный момент проект проверен и зачтен ревьюером. Планов по дальнейшему развитию проекта нет.
