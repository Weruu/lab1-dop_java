# Лабораторна робота №1 — Сучасні можливості Java та Data-Oriented Programming

**Дисципліна:** Технології розробки на платформі Java  
**Університет:** КПІ ім. Ігоря Сікорського  
**Студент:** Мальцева Софія, група ІП-41

## Опис проєкту

Проєкт демонструє підхід Data-Oriented Programming (DOP) у Java 21:

- **незмінні дані** — `record`-класи (`Deposit`, `Withdrawal`, `Transfer`) з валідацією в компактних конструкторах;
- **закрита ієрархія** — `sealed interface Transaction permits Deposit, Withdrawal, Transfer`;
- **розділення даних і алгоритмів** — уся логіка в окремому класі `TransactionProcessor`;
- **вичерпний `switch` expression з pattern matching** без `default`;
- **Text Blocks** для формування результату.

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.

## Розрахунок варіанта

Варіант: `V = N mod 3`, де `N = 13`.

`V = 13 mod 3 = 1` (13 = 3 · 4 + 1) → **Варіант 1: Фінансова система транзакцій**.

## Структура

```
src/main/java/ua/kpi/comsys/dop/
├── Transaction.java            // sealed interface
├── Deposit.java                // record
├── Withdrawal.java             // record
├── Transfer.java               // record
└── TransactionProcessor.java   // process(Transaction)
src/test/java/ua/kpi/comsys/dop/
└── TransactionProcessorTest.java
```

## Валідація

- `amount` — не `null` і `> 0`;
- текстові поля (`accountId`, `category`, `fromAccount`, `toAccount`) — не `null` і не `isBlank()`;
- порушення → `IllegalArgumentException`;
- `process(null)` → `IllegalArgumentException("Transaction cannot be null")`.

## Приклад виводу

```
Transaction Type: TRANSFER
From: ACC-3
To: ACC-4
Amount: USD 1000.00
```

## Запуск тестів

Потрібно: JDK 21+ та Maven 3.8+.

```bash
mvn test
```

Повна збірка з тестами:

```bash
mvn clean verify
```
