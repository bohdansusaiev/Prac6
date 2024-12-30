# Варіант 4

Програма має мати реалізовані наступні задачі:
- задачу, яка спробує виконати певний метод 3 рази з інтервалом 5 секунд між спробами. Якщо задача не успішно виконається після 3 спроб, вивести повідомлення про помилку;
- через 15 секунд після старту програми буде виконуватись задача виводу повідомлення (наприклад, «15 секунд від запуску»).

## №1. Виконання задачі з кількома спробами

Метод намагається виконати задачу три рази з інтервалом 5 секунд між спробами. Якщо після трьох спроб завдання не виконується успішно, виводиться повідомлення про помилку.

```java
@Service
public class TaskService {
    private final AtomicInteger attemptCounter = new AtomicInteger(0);

    public void executeTaskWithRetries() {
        boolean taskSuccess = false;
        while (attemptCounter.get() < 3) {
            try {
                if (performTask()) {
                    taskSuccess = true;
                    break;
                }
            } catch (Exception e) {
                // Логування помилок
            }

            attemptCounter.incrementAndGet();
            if (attemptCounter.get() < 3) {
                try {
                    Thread.sleep(5000); // Затримка між спробами
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!taskSuccess) {
            System.err.println("Не вдалося виконати задачу після 3 спроб.");
        }
    }

    private boolean performTask() {
        if (Math.random() > 0.5) {
            return true; // Успіх
        } else {
            throw new Exception("Помилка під час виконання задачі.");
        }
    }
}
```

## №2. Планування задачі через 15 секунд

Через 15 секунд після запуску програми виконується задача, яка виводить повідомлення "15 секунд від запуску програми".

```java
@Scheduled(initialDelay = 15000, fixedRate = Long.MAX_VALUE)
public void printMessageAfter15Seconds() {
    System.out.println("15 секунд від запуску програми");
}
```

## Запуск програми

1. Компіляція та побудова програми за допомогою Gradle:
   ```bash
   ./gradlew build
   ```

2. Запуск програми:
   ```bash
   ./gradlew bootRun
   ```

