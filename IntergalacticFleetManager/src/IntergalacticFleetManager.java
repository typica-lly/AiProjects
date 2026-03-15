import java.util.ArrayList;
import java.util.Scanner;

interface Flyable{
    void launchSequence();
    void statusCheck();
}

abstract class Spacecraft{
    private String shipName;
    private double fuelLevel;
    private static int totalShips = 0;

    public Spacecraft(String shipName, double fuelLevel){
        this.shipName = shipName;
        this.fuelLevel = fuelLevel;
        totalShips++;
    }

    public String getShipName(){
        return shipName;
    }
    public double getFuelLevel(){
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel ){
        if (fuelLevel < 0) this.fuelLevel = 0;
        else this.fuelLevel = fuelLevel;
    }

    public static int getTotalShips(){
        return Spacecraft.totalShips;
    }

    abstract void performMission();

    public void refill(double amount){
        if(amount < 0){
            System.out.println("Error: Cannot refill negative amount!");
            return;
        }

        this.fuelLevel += amount ;

        if(this.fuelLevel > 100){
            this.fuelLevel = 100;
            System.out.printf("[TANK FULL] %s is filled to 100%" , shipName);
        }else System.out.printf("[REFILLED] %s current fuel level: %.1f%%/n", shipName, fuelLevel);
    }

}

class Fighter extends Spacecraft implements  Flyable{
    private int laserPower;
    public Fighter(String shipName , double fuelLevel ,int laserPower){
        super(shipName, fuelLevel);
        this.laserPower = laserPower;
    }

    @Override
    public void launchSequence(){
        System.out.printf("\n[SYSTEM]: Fighter %s is warming up ion engines...\n", getShipName());
    }

    @Override
    public void statusCheck(){
        System.out.printf("[STATUS]: Target system active. Laser power: %d GW.\n", laserPower);
    }

    public void performMission(){
        if (getFuelLevel() < 10){
            System.out.printf("[WARNING] %s has insufficient fuel for the mission!\n", getShipName());
            return;
        }

        System.out.printf("[LOG]: Ship %s delivered cargo to the moon base.\n" , getShipName());

        setFuelLevel(getFuelLevel() - 10);
        System.out.printf("[FUEL]: Fuel level after mission: %.1f%%\n", getFuelLevel());

    }


}

class CargoShip extends Spacecraft implements Flyable{
    private double capacity = 0;

    public CargoShip(String shipName, double fuelLevel, double capacity){
        super(shipName, fuelLevel);
        this.capacity = capacity;
    }

    @Override
    public void launchSequence(){
        System.out.printf("\n[SYSTEM]: Cargo ship %s is checking airlock seals.\n" , getShipName());
    }

    @Override
    public void statusCheck(){
        System.out.printf("[STATUS]: Weight check: %f tons loaded.\n" , capacity);
    }

    public void performMission(){
        if (getFuelLevel() < 10){
            System.out.printf("[WARNING] %s has insufficient fuel for the mission!\n", getShipName());
            return;
        }

        System.out.printf("[LOG]: Ship %s delivered cargo to the moon base.\n" , getShipName());

        setFuelLevel(getFuelLevel() - 10);
        System.out.printf("[FUEL]: Fuel level after mission: %.1f%%\n", getFuelLevel());

    }
}

public class IntergalacticFleetManager {
    public static void main(String[] args) {
        ArrayList<Spacecraft> fleet = new ArrayList<>();
        Scanner userInput = new Scanner(System.in);
        int choice = 0;

        while(choice != 6){
            System.out.println("\n=== STARFLEET COMMAND CONSOLE ===");
            System.out.println("1. Build New Spacecraft.");
            System.out.println("2. Display Fleet Status.");
            System.out.println("3. Launch Fleet (Execute Missions.)");
            System.out.println("4. Show Fleet Statistics.");
            System.out.println("5. Refill.");
            System.out.println("6. Exit System.");
            System.out.print("Your choice: ");
            try {
                choice = Integer.parseInt(userInput.nextLine());
            }catch (Exception e){
                System.out.println("Invalid input! Please enter number (1-5)");
                continue;
            }
            int type = 0 ;
            if (choice == 1){
                System.out.println("Choose type: 1. Fighter / 2. Cargo");
                System.out.print("Your choice: ");
                try {
                    type = Integer.parseInt(userInput.nextLine());
                }catch (Exception e){
                    System.out.println("Invalid input! Please choose your type 1/2");
                    continue;
                }

                System.out.print("Enter ship name: ");
                String name = userInput.nextLine();

                double fuel = 0;
                while(true){
                    try{
                        System.out.println("Enter Fuel Level (0-100)");
                        fuel = Double.parseDouble(userInput.nextLine());

                        if (fuel >= 0  && fuel <= 100) break;
                        else System.out.println("ERROR: Fuel must be between 0 and 100!");
                    }catch (NumberFormatException e) {
                        System.out.println("Error: Please enter valid numeric value!");
                    }
                }

                if (type == 1){

                    int power = 0;
                    while(true){
                        try{
                            System.out.println("Enter Laser Power (0-100)");
                            power = Integer.parseInt(userInput.nextLine());

                            if (power >= 0 && power <= 100) break;
                            else System.out.println("Error: Power must be between 0 and 100");

                        }catch (NumberFormatException e){
                            System.out.println("Error: Please enter valid numeric value!");
                        }
                    }

                    fleet.add(new Fighter(name , fuel , power));
                }else{
                    double cap = 0;
                    while(true){
                        try{
                            System.out.println("Enter Cargo Capacity(0-1000)kg: ");
                            cap = Double.parseDouble(userInput.nextLine());

                            if (cap >= 0 && cap <= 1000) break;
                            else System.out.println("Error: Cargo's capacity must be between 0 and 1000 kg.");

                        }catch (NumberFormatException e){
                            System.out.println("Error: Please enter valid numeric value!");
                        }
                    }


                    fleet.add(new CargoShip(name , fuel , cap));
                }
                System.out.println("Ship constructed successfully!");
            } else if (choice == 2) {
                System.out.printf("\n%-15s | %-10s | %-10s\n" , "NAME" , "TYPE" , "FUEL");
                System.out.println("______________________________________________");
                for (Spacecraft s : fleet){
                    String typeLabel = (s instanceof Fighter) ? "Fighter" : "Cargo";
                    System.out.printf("%-15s | %-10s | %-10.1f%%\n\n", s.getShipName() , typeLabel , s.getFuelLevel() );

                }
            } else if (choice == 3){
                for (Spacecraft s : fleet){
                    if (s instanceof Flyable){
                        ((Flyable) s).launchSequence();
                        ((Flyable) s).statusCheck();
                    }
                    s.performMission();
                    System.out.println("---");
                }
            } else if (choice == 4) {
                System.out.printf("Total ship in Starfleet: %d ." , Spacecraft.getTotalShips());
            } else if (choice ==5) {
                if (fleet.isEmpty()) System.out.println("Fleet is empty!");
                else {
                    System.out.printf("Enter ship index to refill (0 to %d%%)", (fleet.size()-1));
                    int index = Integer.parseInt(userInput.nextLine());

                    if (index >= 0 && index < fleet.size()){
                        System.out.println("Enter amount to refill: ");
                        double amount = Double.parseDouble(userInput.nextLine());
                        fleet.get(index).refill(amount);
                    }else System.out.println("Invalid index!");
                }
            }
        }
    }
}

