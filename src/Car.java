
public class Car extends Thread{

    private final int gateId;
    private final int carId;
    private final int arrivalTime;
    private final int parkDuration;
    private final ParkingLot parkingLot ;
    private long waitingTime = 0 ;

    public Car(int gateId, int carId, int arrivalTime, int parkDuration, ParkingLot parkingLot) {
        this.gateId = gateId;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
        this.parkingLot = parkingLot;
    }

    @Override
    public void run(){
        try{
            Thread.sleep(arrivalTime* 1000L);
            System.out.println(this + " arrived at time " + arrivalTime);

            long startingTime = System.currentTimeMillis();
            parkingLot.park(this);
            waitingTime = (long)Math.ceil((System.currentTimeMillis() - startingTime) / 1000L);

            Thread.sleep(parkDuration * 1000L);
            parkingLot.leave(this);


        } catch (InterruptedException e) {
            System.err.println("Car " + carId + " was interrupted.");
        }
    }

    @Override
    public String toString(){
        return "Car " + carId + " from Gate " + gateId;
    }

    public int getParkDuration(){
        return this.parkDuration;
    }

    public long getWaitingTime(){
        return this.waitingTime;
    }
}
