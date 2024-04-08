package org.example.dz_source;

import java.util.concurrent.Semaphore;
public class Parking {
    private static final boolean[] PARKING_PLACES = new boolean[5];
    // определите ваш семафор
    private static final Semaphore numberOfParkingLots = new Semaphore(5, true) ; // true  - типо чтоб в порядке очереди брали
    public static void main(String[] args) throws InterruptedException {
        // запустите процесс парковки
        for (int i = 0; i < 10; i++) {
            Thread.sleep(5);
            new Thread(new Car(i)).start(); // пытаемся запустить одновременно 10 потоков
        }
    }

    public static class Car implements Runnable {
        private int carNumber;
        public Car(int carNumber) {
            this.carNumber = carNumber;
        }
        @Override
        public void run() {
            // здесь ваше решение
            int currentPlace = -1; // Начальная инициализация на несуществующий индекс массива
            try {
                numberOfParkingLots.acquire(); // Пробуем захватить семафор
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (PARKING_PLACES) { // Чтоб в разных потоках массив асинхронно не менялся
                for (int i = 0; i < PARKING_PLACES.length; i++) {
                    if (!PARKING_PLACES[i]) {
                        PARKING_PLACES[i] = true; // Занимаем место
                        currentPlace = i; // Запоминаем парковочное место
                        System.out.println("Авто  #" + carNumber + " запарковался на место "
                                + currentPlace + " .");
                        break; // Завершаем цикл
                    }
                }
            }
            // Постояли случайное количество времени
            try {

                int time = 3 + (int) (Math.random() * 4.0);
                System.out.println("Авто #" + carNumber + " простоит " + time + "  сек .");
                Thread.sleep(time * 1000); // делаем вид, что поток выполняет важную задачу
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (PARKING_PLACES) { // Чтоб в разных потоках массив асинхронно не менялся
                PARKING_PLACES[currentPlace] = false; // Освобождаем занятое место
            }
            numberOfParkingLots.release(); // освобождаем семафор
            System.out.println("Авто  #" + carNumber + " освободил место "
                    + currentPlace + " .");
        }
    }
}