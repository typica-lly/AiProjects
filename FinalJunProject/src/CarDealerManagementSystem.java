import java.util.Scanner;
import java.util.ArrayList;

interface Discountable{
    void applyDiscount(double percentage);
}

abstract class Vehicle implements Discountable{
    private String brand;
    private String model;
    private double price;
    private static int totalVehicles = 0;

    public Vehicle(String brand , String model , double price){
        this.brand = brand;
        this.model = model;
        this.price = price;
        totalVehicles++;
    }

    @Override
    public void applyDiscount(double percentage){
        this.price -= (this.price * percentage / 100);
        System.out.printf("New price for %s: %.2f$ (Dicount: %.0f%% applied)\n", getBrand(), this.price , percentage);
    }

    public static int totalVehicles(){
        return totalVehicles;
    }

    public String getBrand(){return brand;}
    public String getModel(){return model;}
    public double getPrice(){return price;}

    public abstract void displayInfo();
}

class Car extends Vehicle{
    public Car(String brand, String model , double price ){
        super(brand, model, price);
    }

    @Override
    public void displayInfo(){
        System.out.printf("Brand: %s | Model: %s | Price: %f \n", getBrand() , getModel() , getPrice());
    }
}

public class CarDealerManagementSystem{
    public static void main(String[] args){
        Scanner userInput = new Scanner(System.in);
        ArrayList<Vehicle> inventory = new ArrayList<>();
        int choice = 0;

        System.out.println("--- Welcome to Car Dealership Management System ---");

        while(choice != 4){
            System.out.println("\n1. Add new Car.");
            System.out.println("2. Show inventory report.");
            System.out.println("3. Black Friday(10% Discount on everything).");
            System.out.println("4. Exit.");
            System.out.print("Your choice: ");

            try{
                choice = Integer.parseInt(userInput.nextLine());
            } catch(NumberFormatException e){
                System.out.println("Error: Please enter a number(1, 2 or 3)");
                continue;
            }

            if(choice == 1){
                boolean success  = false ;
                while(!success){
                    try{
                        System.out.print("Enter Brand: ");
                        String brand = userInput.nextLine();
                        System.out.print("Enter Model: ");
                        String model = userInput.nextLine();
                        System.out.print("Enter Price: ");
                        double price = Double.parseDouble(userInput.nextLine());

                        inventory.add(new Car(brand, model, price));
                        System.out.println("Car added successfully!");
                        success = true;
                    } catch(Exception e){
                        System.out.println("Invalid price! Please use numbers!(e.g. 12222.22)");
                    }
                }

            }else if (choice == 2){
                if(inventory.isEmpty()){
                    System.out.println("Inventory is empty");
                }else{
                    StringBuilder reportHeader = new StringBuilder();
                    reportHeader.append("\n--- Current Inventory ---\n");
                    System.out.print(reportHeader.toString());
                    for(Vehicle v : inventory){
                        v.displayInfo();
                    }

                    System.out.println("---------------------");
                    System.out.println("Total vehicles created in system: " + Vehicle.totalVehicles());
                }
            }else if(choice ==3 ){
                for(Vehicle disc : inventory){
                    disc.applyDiscount(10);
                }
            }
        }
        System.out.println("Shutting down. Goodbye!");
    }
}

