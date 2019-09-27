import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class myWork {
    public static void main(String[] args) throws InterruptedException {
        myJobs();

    }

    private static void myJobs() throws InterruptedException {
        Random r = new Random();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);//in a fixed size (size = 5) thread pool
        for (int i = 1; i <= 10; i++) {
            Order curOrder = new Order(i);
            Thread t = new Worker(curOrder);
            Thread.sleep(r.nextInt(200)); //sleep for random seconds to asynchronously generate orders
            fixedThreadPool.execute(t);
        }
        fixedThreadPool.shutdown();

    }
}

class Worker extends Thread {
    private Order order;
    Worker(Order order){
        this.order = order;
    }
    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " is working on order no." + order.getNumber());
        try {
            Thread.sleep(200); //Worker is working on current order. Working speeding is 200.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.setStatus(Status.FULFILLED);
        System.out.println(order.getNumber()+ " is fulfilled.");

    }
}


class Order{
    private int number;
    private Status status;
    public Order(int number){
        this.number = number;
        this.status = Status.NEW;
    }

    public int getNumber() {
        return number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

enum Status
{
    NEW,
    FULFILLED;
}