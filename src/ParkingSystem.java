import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;

public class ParkingSystem {
    public static void main(String []args) throws InterruptedException {
        int totalSpaces = 4;
        ParkingLot parkingLot= new ParkingLot(totalSpaces);
        Queue<Car> cars = new LinkedList<>();
        Gate gate1 = new Gate(1,parkingLot);
        Gate gate2 = new Gate(2,parkingLot);
        Gate gate3 = new Gate(3,parkingLot);
        int [] gateCounter = new int[3];
        int totalCars = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("car_schedule.txt"))) {
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(", ");
                int gateId = Integer.parseInt(parts[0].split(" ")[1]);
                int carId = Integer.parseInt(parts[1].split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkDuration = Integer.parseInt(parts[3].split(" ")[1]);
                switch (gateId){
                    case 1:
                        cars.add(gate1.processCar(carId,arrivalTime,parkDuration));
                        gateCounter[0]++;
                        break;
                    case 2:
                        cars.add(gate2.processCar(carId,arrivalTime,parkDuration));
                        gateCounter[1]++;
                        break;
                    case 3:
                        cars.add(gate3.processCar(carId,arrivalTime,parkDuration));
                        gateCounter[2]++;
                        break;
                    default:
                        return;
                }
                totalCars++;
            }
        }catch (IOException e){
            System.err.println("Error reading the car schedule file.");
        }
        while(!cars.isEmpty()){
            Car car = cars.poll();
            car.join();
        }
        System.out.println("...");
        System.out.println("Total Cars Served: "+totalCars);
        System.out.println("Current Cars in Parking: "+parkingLot.countCarsInParking());
        System.out.println("Details: ");
        System.out.println("-Gate 1 served: " + gateCounter[0] +" cars.");
        System.out.println("-Gate 2 served: " + gateCounter[1] +" cars.");
        System.out.println("-Gate 3 served: " + gateCounter[2] +" cars.");
    }
}