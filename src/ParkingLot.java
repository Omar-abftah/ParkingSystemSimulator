import java.util.concurrent.Semaphore;
public class ParkingLot {
    private final Semaphore parkingSpots;
    private int currentOccupied = 0;

    public ParkingLot(int totalSpots){
        parkingSpots = new Semaphore(totalSpots);

    }

    public synchronized boolean park(Car car){
        if(parkingSpots.tryAcquire()){
            currentOccupied++;
            int t = car.getWaitingTime();
            if(t > 0){
                System.out.println(car + " parked after waiting for " + t + " units of time. (Parking Status: " + currentOccupied + " spots occupied)");
            }
            else{
                System.out.println(car + " parked. (Parking Status: " + currentOccupied + " spots occupied)");
            }
            return true;
        }
        else{
            System.out.println(car + " waiting for a spot.");
            return false;
        }
    }

    public synchronized void leave(Car car){
        parkingSpots.release();
        currentOccupied--;
        System.out.println(car + " left after " + car.getParkDuration() + " units of time. (Parking Status: " + currentOccupied + " spots occupied)");
    }
}
