
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Semaphore {
    private int maxSpaces;
    private int availableSpaces;
    private Queue<Car> waitingQueue = new LinkedList<>();
    private Lock lock;
    private Condition spotAvailable;

    public Semaphore(int maxSpaces) {
        this.maxSpaces = maxSpaces;
        this.availableSpaces = maxSpaces;
        this.lock = new ReentrantLock();
        this.spotAvailable = lock.newCondition();
    }

    public void acquire(Car car){
        lock.lock();
        try{
            if(availableSpaces > 0){
                availableSpaces--;
            }
            else{
                waitingQueue.add(car);
                while(waitingQueue.peek() != car ||availableSpaces == 0){
                    spotAvailable.await();
                }
                waitingQueue.poll();
                availableSpaces--;
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            lock.unlock();
        }
    }

    public boolean tryAcquire(){
        lock.lock();
        try{
            if (availableSpaces > 0) {
                availableSpaces--;
                return true;
            } else {
                return false;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void release(){
        lock.lock();
        try {
            availableSpaces++;
            spotAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
