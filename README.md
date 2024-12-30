# Spring Boot Asynchronous Tasks Demo

Програма демонструє використання асинхронних задач у Spring. Вона реалізує два приклади:

## Виконання задачі з кількома спробами

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

## Планування задачі через 15 секунд

Через 15 секунд після запуску програми виконується задача, яка виводить повідомлення "15 секунд від запуску програми".

```java
@Scheduled(initialDelay = 15000, fixedRate = Long.MAX_VALUE)
public void printMessageAfter15Seconds() {
    System.out.println("15 секунд від запуску програми");
}
```

## Запуск програми

1. Скомпілюйте та побудуйте програму за допомогою Gradle:
   ```bash
   ./gradlew build
   ```

2. Запустіть програму:
   ```bash
   ./gradlew bootRun
   ```

## Основний клас програми

```java
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TaskService taskService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        taskService.executeTaskWithRetries(); // Виконання задачі з повторними спробами
    }
}
```

