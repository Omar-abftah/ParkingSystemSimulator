public class Gate {
    private int gateId;
    private ParkingLot parkingLot;

    public Gate(int gateId, ParkingLot parkingLot){
        this.gateId = gateId;
        this.parkingLot = parkingLot;
    }

    public void processCar(int carId, int arrivalTime, int parkDuration){
        Car car = new Car(gateId, carId, arrivalTime, parkDuration,parkingLot);
        car.start();
    }
}
