import java.util.ArrayList;

class Engine{
    private String type;
    private int horsePower;
    private double price;

    public Engine(String type, int horsePower, double price){
        this.type = type;
        this.horsePower = horsePower;
        this.price = price;
    }

    public double getPrice() {return price;}
    public String getType() {return type;}
}

class Wheel{
    private int size;
    private String season;
    private double price;
    public Wheel(int size, String season ,double price){
        this.size = size;
        this.season = season;
        this.price = price;
    }

    public double getPrice() { return price; }
}

class Transmission {
    private String type;
    private double price;

    public Transmission(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public double getPrice() {return price;}
    public String getType() {return type;}
}

class Car{
    private String model;
    private Engine engine;
    private Transmission transmission;
    private ArrayList<Wheel> wheels = new ArrayList<>();

    public Car(String model , Engine engine , Transmission transmission){
        this.model = model;
        this.engine = engine;
        this.transmission = transmission;
    }

    public void addWheel(Wheel wheel){
        if (wheels.size() < 4) wheels.add(wheel);
        else System.out.println("The car already has 4 wheels");
    }

    public void setEngine(Engine engine){this.engine = engine;}

    public double calculateTotalPrice(){
        double total = engine.getPrice() + transmission.getPrice();
        for(Wheel w : wheels){total += w.getPrice();}
        return total;
    }

    public void displayInfo(){
        System.out.println("=== Car Info ===");
        System.out.printf("Model: %s\n", model);
        System.out.printf("Engine: %s\n" , engine.getType());
        System.out.printf("Transmission: %s\n", transmission.getType());
        System.out.printf("Total price: %.2f\n" , calculateTotalPrice());
    }
}

public class Main{
    public static void main(String[] args) {
        Engine v6 = new Engine("V6 Petrol", 300 , 5000.0);
        Transmission auto = new Transmission("Automatic", 2500.0);

        Car myCar = new Car ("Sport Sedan" , v6 , auto);

        for (int i = 0; i < 4; i++) {
            myCar.addWheel(new Wheel(18 , "summer", 200.0));
        }

        myCar.displayInfo();

        System.out.println("=== Instaling Electric Engine Upgrade ===");
        Engine electric = new Engine("Tesla Electric ", 500 , 12000);
        myCar.setEngine(electric);

        myCar.displayInfo();
    }
}