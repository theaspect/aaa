# Reference implementation for AAA project

## Build instructions

To build project execute `./BUILD.sh`

## Run instructions

To run application execute `./RUN.sh`

## Test instructions

To run all tests execute `./TEST.sh`

## Requirements list

### Requirements 1 Revision 3

R1.1. Приложение должно иметь возможность аутентифицировать пользователя по логину и паролю

R1.2. Пароль должен храниться безопасно

R1.3. Приложение должно иметь возможность авторизовать пользователя к определенному ресурсу с определенной ролью

R1.4. Ресурсы должны представляться в виде дерева

R1.5. Возможные роли READ, WRITE, EXECUTE

R1.6. Доступ к ресурсу с определенной ролью автоматически предоставляет доступ ко всем потомкам с указанной ролью

R1.7. Приложение должно учитывать время доступа к заданному ресурсу и потребленный объем

R1.8. Приложение должно быть консольной утилитой принимающей на вход параметры:
-login "логин" (строка из букв и цифр)
-pass "пароля" (строка из букв и цифр)
-res "AB.CD.E" (Строка где уровни разделены точкой, сам уровень - строка из букв)
-role "РОЛЬ" (Где роль одно из: READ, WRITE, EXECUTE)
-ds "2015-12-31" (Строка даты в формате ГГГГ-ММ-ДД)
-de "2015-12-31" (Строка даты в формате ГГГГ-ММ-ДД)
-vol "число" (Целое число)
-h (без параметров, выводит справку по параметрам)
Запуск программы без параметров или с неизвестными параметрами эквивалентен запуску с параметром -h (вывод справки)

R1.9. Приложение должно возвращать следующие exit-коды:
0 - успех
1 - неизвестный логин
2 - неверный пароль
3 - неизвестная роль
4 - нет доступа
5 - некорректная активность (невалидная дата или объем)

### Requirements 2 Revision 1

R2.1 Каждый проект должен содержать README.md с описанием проект

R2.2 В README.md должна присутствовать инструкция по сборке проекта из консоли

R2.3 В README.md должна присутствовать инструкция по запуску собранного проекта из консоли

R2.4 В README.md должна присутствовать инструкция по тестированию собранного проекта

R2.5 В репозитории должен содержаться скрипт сборки проекта BUILD.bat или BUILD.sh в зависимости от системы

R2.6 В репозитории должен содержаться скрипт запуска проекта RUN.bat или RUN.sh в зависимости от системы

R2.7 В репозитории должен содержаться скрипт тестирования системы TEST.bat или TEST.sh в зависимости от системы, покрывающий все варианты использования (которые вытекают из требований)

R2.8 Код должен быть отформатирован согласно требованиям java coding conventions

R2.9 В репозитории должен содержаться файл .gitignore в котором будут отфильтрованы бинарные артефакты и файлы IDE
