# Лабораторна робота №4 Tetris++
## Опис проєкту
Ця лабораторна робота покращує простий тетріс з попередньої лабораторної роботи. Проєкт включає можливість зчитування конфігурації з текстового файлу та опустити фігуру до моменту, поки вона більше не може. Метою цієї роботи є створення інтеграційних тестів для нової логіки та покращення тестів заміною саморобних mock'ів на mock’и з бібліотеки.

## Автор
ПІБ: Сачко Максим Євгенійович  
Група: ІМ-22

## TODO list цієї лабораторної роботи
- [x] Скопіюйте увесь вміст репозиторію Lab3 у ваш новий репозиторій;
- [x] Продивіться відео з прикладом виконання і репозиторій з прикладом;
- [x] Виберіть бібліотеку,що полегшує роботу з test doubles. Приклади:
    1. JavaScript: Jest;
    2. Java: Mockito;
    3. TypeScript: Jest, ts-mockito;
    4. Rust: mockall;
- [x] Замініть mock’и, що були створені у Lab3 на mock’и з бібліотеки;
- [x] Виконайте алгоритмічне завдання: потрібно доробити програму зроблему у лабораторній #3 і додати параметр при передачі якого, буде виведено не тільки останій екран гри, а і екрани на кожному кроці гри. При цьому потрібно зберегти і початковий режим роботи (коли виводиться тільки останій екран).

## Налаштування середовища та запуск тестів

### 1. Клонування репозиторію
В командному рядку написати таке:
```bash
git clone https://github.com/SachkoMaxim/tetrisSimple-plus.git
```

```bash
cd your_path_to/tetrisSimple-plus
```

### 2. Встановлення залежностей та налаштування середовища
Для цього проєкту використовується Kotlin та Gradle. Щоб встановити всі залежності, виконайте наступну команду:
```bash
gradle build
```

### 3. Запуск тестів
Для запуску всіх тестів використовуйте наступну команду:
```bash
gradle test
```

## Як перевірити, чи працює програма?

### 1. Налаштування вхідного файлу
Додайте вхідний файл з наведеним нижче вмістом:
> **NOTE:** вміст можете змінити, нижче наведено лише приклад
```md
5 3
pp.
p..
...
.##
..#
```

На першому рядку вказується першим числом є **висота (кількість рядків)**, а другим числом є **ширина (кількість стовпців)** поля. Далі вказується поле і розташування «підвішеної» фігури, «ландшафт» та порожні пікселі.

- «.» - порожній піксель;
- «p» - піксель «підвішеної» фігури ("p" ві дслова "piece");
- «#» -піксель «ландшафта» (залишків фігур, які впали раніше).

### 2. Запуск команди
Далі в консолі прописується така команда:
```bash
gradle run --args="input.txt [-printEachStep]"
```

Де:
- ```--args``` - параметр для передачі аргументів у програму. У нашому випадку - це вхідний файл, з якого береться інформація. Вхідний файл потрібно вказувати обов'язково. У [] знаходяться аргументи, які можна не писати і програма все рівно буде працювати. При введенні опціонального аргументу **-printEachStep** у консоль буде виводитись кожен крок падіння фігури, починаючи з початкового (нульового) кроку.