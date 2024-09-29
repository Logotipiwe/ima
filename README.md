<h1>Трекер задач IMA</h1>

![logo](readme/logo.svg)

<b>IMA - это трекер задач, позволяющий управлять проектами и задачами. Сервис имеет весь функционал, чтобы команда могла начать его использовать для улучшения процессов разработки и внедрения продуктов.</b>

## Роадмап развития сервиса

- [x] Управление проектами
- [x] Управление задачами
  - статусы
  - описание
  - приоритет
  - дата окончания
- [x] Добавление участников в проект
  - просмотр своих проектов
- [x] Получение уведомлений по почте
  - [x] о регистрации
  - [ ] об изменении задач


## Технологии
<img src="https://user-images.githubusercontent.com/25181517/117201470-f6d56780-adec-11eb-8f7c-e70e376cfd07.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/183897015-94a058a6-b86e-4e42-a37f-bf92061753e5.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/192108376-c675d39b-90f6-4073-bde6-5a9291644657.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/117208740-bfb78400-adf5-11eb-97bb-09072b6bedfc.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/179090274-733373ef-3b59-4f28-9ecb-244bea700932.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/117207330-263ba280-adf4-11eb-9b97-0ac5b40bc3be.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/183890598-19a0ac2d-e88a-4005-a8df-1ee36782fde1.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/187896150-cc1dcb12-d490-445c-8e4d-1275cd2388d6.png" width=50>
<img src="https://github.com/marwin1991/profile-technology-icons/assets/136815194/7e9599e9-0570-4bb6-b17f-676ed589912f" width=50>
<img src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/183345125-9a7cd2e6-6ad6-436f-8490-44c903bef84c.png" width=50>
<img src="https://user-images.githubusercontent.com/25181517/186711335-a3729606-5a78-4496-9a36-06efcc74f800.png" width=50>

## Архитектура сервиса
![scheme](./readme/arc.jpg)

## Инфраструктура проекта

<a href="https://logotipiwe.ru/ima/swagger-ui"><b>Production swagger</b></a>

<a href="https://github.com/Baranovskiydev/ima-front"><b>Фронтенд репозиторий</b></a>

<a href="https://logotipiwe.ru:30134"><b>Мониторинг контейнеров</b></a>

<a href="http://logotipiwe.ru:30136/"><b>Серверный Jenkins</b></a>

## Техническая документация

### Запуск проекта

Предварительно необходимо скопировать в папки с соответствующими docker-compose конфигами .env файлы с нужной конфигурацией.

```
export IMAGE=...
docker build -t ${IMAGE}
docker push ${IMAGE}

docker compose -f ./deploy/ingress.yml up -d
docker compose -f ./deploy/jenkins.yml up -d
docker compose -f ./deploy/pg.yml up -d
docker compose -f ./deploy/pgadmin.yml up -d
docker compose -f ./deploy/portainer.yml up -d

docker compose up -d
```