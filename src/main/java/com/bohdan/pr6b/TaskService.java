package com.bohdan.pr6b;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {

    private final AtomicInteger attemptCounter = new AtomicInteger(0);

    public void executeTaskWithRetries() {
        boolean taskSuccess = false;
        while (attemptCounter.get() < 3) {
            try {
                System.out.println("Спроба виконання задачі номер: " + (attemptCounter.get() + 1));
                if (performTask()) {
                    taskSuccess = true;
                    System.out.println("Задача виконана успішно!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Помилка виконання задачі: " + e.getMessage());
            }

            attemptCounter.incrementAndGet();
            if (attemptCounter.get() < 3) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!taskSuccess) {
            System.err.println("Не вдалося виконати задачу після 3 спроб.");
        }
    }

    private boolean performTask() throws Exception {
        if (Math.random() > 0.5) {
            return true;
        } else {
            throw new Exception("Випадкова помилка під час виконання задачі.");
        }
    }

    @Scheduled(initialDelay = 15000, fixedRate = Long.MAX_VALUE)
    public void printMessageAfter15Seconds() {
        System.out.println("15 секунд від запуску програми");
    }
}
