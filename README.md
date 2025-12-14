Архитектура проекта (MVC)

Проект реализован с использованием паттерна Model-View-Controller(MVC) и поддерживает два независимых представления с одинаковой логикой.

Model
Отвечает за данные и логику:
entity - сущностm gameEntity
repository - доступ к данным DBmanager
resolver - аналитика и вычисления Resolver
game - сама модель

Controller
Связывает Model и View:
controller - вызывает методы Resolver`а и возвращает результат представлениям

View
Проект содержит два отдельных представления, использующих один Controller:

Desktop View
Пакет view_desktop
Реализация на Swing
Класс: MainWindow
Отображает:
  текстовые результаты
  графики через JFreeChart

TG bot view
Пакет view_bot
Реализация через Telegram Bots API
Класс: GameBot
Предоставляет тот же функционал, что и desktop-версия, через команды Telegram(график передается в виде картинки)

****************************************************************************************************************

Последовательность работы проекта

1. При запуске создаётся Model:
   DBmanager загружает данные
   Resolver инициализируется списком игр

2. Создаётся Controller, которому передаётся Resolver

3. Далее запускаются представления:
   view_desktop.MainWindow
   view_bot.GameBot

4. Пользователь инициирует действие:
   нажатием кнопки (desktop)
   командой в Telegram (bot)

5. View вызывает соответствующий метод Controller

6. Controller обращается к Resolver

7. Resolver:
   выполняет вычисления
   возвращает результат (объект game, строку или Map)

8. View отображает результат:
   текст в GUI / сообщение в Telegram
   либо строит график (desktop-версия) или отправляет фото графика (tgbot-версия)

   <img width="1024" height="1024" alt="изображение" src="https://github.com/user-attachments/assets/c3ad38d6-ca79-4ee8-b8b3-0041783f578f" />
