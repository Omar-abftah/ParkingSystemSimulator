import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParkingSystem {
    public static void main(String []args){
        int totalSpaces = 4;
        ParkingLot parkingLot= new ParkingLot(totalSpaces);

        Gate gate1 = new Gate(1,parkingLot);
        Gate gate2 = new Gate(2,parkingLot);
        Gate gate3 = new Gate(3,parkingLot);

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
                        gate1.processCar(carId,arrivalTime,parkDuration);
                        break;
                    case 2:
                        gate2.processCar(carId,arrivalTime,parkDuration);
                        break;
                    case 3:
                        gate3.processCar(carId,arrivalTime,parkDuration);
                        break;
                    default:
                        return;
                }
            }
        }catch (IOException e){
            System.err.println("Error reading the car schedule file.");
        }
    }
}