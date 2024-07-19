Currency-converter - приложение для конвертации валют

Экран ввода данных:
    На экране доступны следующие элементы:
        - поле ввода суммы для конвертации
        - два выпадающих списка для выбора валюты
        - кнопка для запуска конвертации 
        - текстовый элемент для отображения сконвертированной валюты
        - кнопка обновления и текст в случае ошибки на сервере или отсутствия интернета у пользователя

Получение данных:
    - для получения текущих курсов валют используется публичный API Open Exchange Rates 
    (https://openexchangerates.org/) ограничение - 1000 бесплатных запросов в месяц 
    (~ 300 уже потрачено на этапе тестирования приложения)
    - работа с API реализована с использованием Retrofit

Реактивный подход: 
    - для асинхронной работы с сетью используются Coroutines