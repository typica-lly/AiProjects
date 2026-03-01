import java.util.Scanner;
import java.util.ArrayList;

interface EnergyConsumer{
    void refill();
}

interface PublicTransport{
    void boardPassenger(int count);
}

abstract class Vehicle{
    protected String licensePlate;
    protected double fuelLevel;
    protected int maxCapacity;

    public Vehicle(String licensePlate, double fuelLevel, int maxCapacity){
        this.licensePlate = licensePlate;
        this.fuelLevel = fuelLevel;
        this.maxCapacity = maxCapacity;
    }

    public abstract void drive();

    public abstract void checkCapacity(int amount);
}

class ElectricBus extends Vehicle implements EnergyConsumer {
    public ElectricBus(String licensePlate , double fuelLevel , int maxCapacity){
        super(licensePlate, fuelLevel, 25);
    }

    @Override
    public void refill(){
        System.out.println("Charging batteries... Done.");
        fuelLevel = 100;
    }

    @Override
    public void checkCapacity(int count){
        if(count > this.maxCapacity) System.out.println("Too many people! Max Capacity is: " + maxCapacity);
        else System.out.println("Boarding success : " + count + " passengers are now on board.");
    }

    @Override
    public void drive(){
        System.out.println("Electric bus: " + licensePlate + " moves silently.");
    }
}

class DieselTruck extends Vehicle implements EnergyConsumer{
    public DieselTruck(String licensePlate , double fuelLevel, int maxCapacity){
        super(licensePlate, fuelLevel , maxCapacity);
    }



    @Override
    public void refill(){
        System.out.println("Filling tank with diesel");
    }

    @Override
    public void checkCapacity(int weight){
        if(weight > this.maxCapacity) System.out.println("Overload! Truck can't carry this.");
        else System.out.println("Cargo loaded successfully");
    }

    @Override
    public void drive(){
        System.out.println("Truck: " + licensePlate + " is roaring and hauling cargo!");
    }


}

class Bicycle extends Vehicle {
    public Bicycle(String licensePlate ,double fuelLevel,int maxCapacity){
        super(licensePlate, 100 ,  1);

    }

    @Override
    public void drive(){
        System.out.println("Bicycle: cycling " + licensePlate+ " through the streets");
    }

    @Override
    public void checkCapacity(int amount){
        if(amount > this.maxCapacity) System.out.println("Bicycle can only carry 1 person");
        else System.out.println("Bicycle is ready to go");
    }
}

class TransportHub{
    public static void main(String[] args){
        Scanner userInput = new Scanner(System.in);
        ArrayList<Vehicle> fleet = new ArrayList<>();
        int choice = 0;

        while(choice != 6){
            System.out.println("\n--- Transport Hub Menu ---");
            System.out.println("1. Add Electric Bus.");
            System.out.println("2. Run All Vehicles.");
            System.out.println("3. Show Fleet Status.");
            System.out.println("4. Add Bicycle.");
            System.out.println("5. Add Diesel Truck.");
            System.out.println("6. Exit.");
            System.out.print("Your choice: ");

            choice = userInput.nextInt();
            userInput.nextLine();

            if(choice == 1){
                System.out.print("Enter Plate: ");
                String plate = userInput.nextLine();
                System.out.print("Enter current charge(0-100): ");
                double fuel = userInput.nextDouble();
                while(fuel < 0 || fuel > 100){
                    System.out.println("Invalid input! Please enter a value between 0 and 100");
                    System.out.print("Enter current charge(0-100)");
                    fuel = userInput.nextDouble();
                }
                userInput.nextLine();

                fleet.add(new ElectricBus(plate , fuel , 25));
                System.out.println("Bus added!");
            }

            else if(choice == 2){
                System.out.println("\n--- City traffic is moving ---");
                for(Vehicle v : fleet) {
                    v.drive();

                    System.out.print("Enter load(passengers/cargo) for " + v.licensePlate + ": ");
                    int amount = userInput.nextInt();
                    userInput.nextLine();

                    while(amount > v.maxCapacity || amount <= 0){
                        System.out.println("Invalid load! Max is: " + v.maxCapacity);
                        System.out.print("Try again for " + v.licensePlate + ": ");
                        amount = userInput.nextInt();
                        userInput.nextLine();
                    }

                    v.checkCapacity(amount);


                    if (v.fuelLevel < 25 && v instanceof EnergyConsumer energyconsumer) energyconsumer.refill();

                    System.out.println("------------------");
                }
            }

            else if(choice == 3){
                System.out.println("\n--- Current Fleet Status ---");
                if(fleet.isEmpty()) System.out.println("Hub is empty");
                else {
                    for(Vehicle v : fleet) System.out.println("Plate: " + v.licensePlate + " | Fuel: " + v.fuelLevel + "%");
                }
            }

            else if(choice == 4){
                System.out.print("Enter Bicycle Plate: ");
                String plate = userInput.nextLine();
                fleet.add(new Bicycle(plate, 100 , 1));
                System.out.println("Bicycle added to the hub");
            }


            else if (choice == 5){
                System.out.print("Enter Diesel Truck Plate: ");
                String plate = userInput.nextLine();
                System.out.print("Enter Diesel Truck Fuel Level: ");
                double fuel = userInput.nextDouble();
                while(fuel < 0 || fuel > 100){
                    System.out.println("Invalid! Enter Fuel (0-100)");
                    fuel = userInput.nextDouble();
                }
                System.out.print("Enter Cargo capacity: ");
                int maxCap = userInput.nextInt();

                while(maxCap <= 0){
                    System.out.println("Capacity must be a positive number!");
                    System.out.print("Enter max passenger capacity: ");
                    maxCap = userInput.nextInt();
                }
                userInput.nextLine();

                fleet.add(new DieselTruck(plate, fuel , maxCap));
                System.out.println("Truck added!");
            }

            else if (choice == 6){
                System.out.println("Shutting down the system. Goodbye");
            }
        }

    }
}