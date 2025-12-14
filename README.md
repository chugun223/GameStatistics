АРХИТЕКТУРА ПРОЕКТА (MVC)  
  
Проект реализован с использованием паттерна Model-View-Controller(MVC) и поддерживает два независимых представления с одинаковой логикой.  
  
Model Отвечает за данные и логику:  
entity - сущностm gameEntity  
repository - доступ к данным  
DBmanager resolver - аналитика и вычисления Resolver  
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
Отображает:текстовые результаты  
графики через JFreeChart  
  
TG bot view  
Пакет view_bot  
Реализация через Telegram Bots APIКласс:  
GameBotПредоставляет тот же функционал, что и desktop-версия, через команды Telegram(график передается в виде картинки)  
  
****************************************************************************************************************  
  
ПОСЛЕДОВАТЕЛЬНОСТЬ РАБОТЫ ПРОЕКТА  
  
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
<img width="1284" height="996" alt="вывод графика_destop" src="https://github.com/user-attachments/assets/ed48aeee-8c8e-4a49-a188-c46c5914101b" />  
<img width="248" height="305" alt="пример выводов_desktop" src="https://github.com/user-attachments/assets/91b86695-9b9f-4244-be27-0d4bad965228" />  
<img width="546" height="199" alt="пример выводов_TGbot" src="https://github.com/user-attachments/assets/58890ef5-9bf7-47ea-9492-fb17e28f4d27" />  
  
****************************************************************************************************************  
  
ТЕСТИРОВАНИЕ  
  
Используется JUnit 5  
Mockito для моков БД  
Покрытие класса Resolver:  
Instructions: 100%  
Branches: 95%  
<img width="1166" height="138" alt="результаты тестирования games_project.model.resolver" src="https://github.com/user-attachments/assets/d9d15a88-fe56-4f6a-9cd8-9c1532e5b05f" />
