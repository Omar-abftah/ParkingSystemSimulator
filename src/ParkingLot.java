import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
public class ParkingLot {
    private final Semaphore parkingSpots;
    private int currentOccupied = 0;

    public ParkingLot(int totalSpots){
        parkingSpots = new Semaphore(totalSpots);
    }

    public boolean park(Car car) throws InterruptedException {
        if (!parkingSpots.tryAcquire()) {
            System.out.println(car + " is waiting for a spot.");
            parkingSpots.acquire();
        }
        synchronized (this){
            currentOccupied++;
            long waitingTime = car.getWaitingTime();
            if (waitingTime > 0L) {
                System.out.println(car + " parked after waiting for " + waitingTime + " units of time. (Parking Status: " + currentOccupied + " spots occupied)");
            } else {
                System.out.println(car + " parked. (Parking Status: " + currentOccupied + " spots occupied)");
            }
        }
        return true;
    }

    public void leave(Car car) {
        synchronized (this) {
            currentOccupied--;
            System.out.println(car + " left after " + car.getParkDuration() + " units of time. (Parking Status: " + currentOccupied + " spots occupied)");
        }
        parkingSpots.release();
    }

    public int countCarsInParking(){
        return currentOccupied;
    }
}
