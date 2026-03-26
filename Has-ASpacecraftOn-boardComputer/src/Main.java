class EngineUnit{
    private double fuelLevel;
    private boolean isEngineReady;

    public EngineUnit(double fuelLevel, boolean isEngineReady){
        this.fuelLevel = fuelLevel;
        this.isEngineReady = isEngineReady;
    }

    public void checkStatus(){
        System.out.printf("Fuel Level: %.2f | Is Engine Ready: %b\n", fuelLevel);
    }

    public void consumeFuel(double amount){
        this.fuelLevel -= amount;
    }

    public void setFuelLevel(double fuelLevel) {this.fuelLevel = fuelLevel;}
    public void setEngineReady(boolean isEngineReady){this.isEngineReady = isEngineReady;}

    public double getFuelLevel() {return fuelLevel;}
    public boolean isEngineReady() {return isEngineReady;}
}

class LifeSupport{
    private int oxygenLevel;
    public LifeSupport(int oxygenLevel){
        if (oxygenLevel >= 0 && oxygenLevel <= 100) this.oxygenLevel = oxygenLevel;
        else {
            System.out.println("Error. Enter from 0 to 100.");
            this.oxygenLevel = 0 ;
        }
    }

    public int getOxygenLevel() {return oxygenLevel;}

    public void getOxygenStatus(){
        System.out.printf("Oxygen Status: %d" , getOxygenLevel());
    }
}

class NavigationSystem{
    private String destination;
    public NavigationSystem(String destination){
        this.destination = destination;
    }

    public String getDestination() {return destination;}

    public void getRouteInfo(){
        System.out.printf("Course to the planet: %s", destination);
    }
}

class Spaceship{
    private String shipName;
    private EngineUnit engineUnit;
    private LifeSupport lifeSupport;
    private NavigationSystem navigationSystem;

    public Spaceship(String shipName , EngineUnit engineUnit , LifeSupport lifeSupport, NavigationSystem navigationSystem){
        this.shipName = shipName;
        this.engineUnit = engineUnit;
        this.lifeSupport = lifeSupport;
        this.navigationSystem = navigationSystem;
    }

    public void launchSequence(){
        System.out.printf("Launch of the ship's systems: %s\n" , shipName);
        if (engineUnit.getFuelLevel() > 20 && engineUnit.isEngineReady() && lifeSupport.getOxygenLevel() > 50){
            System.out.printf("All systems are normal. Take-off is allowed! Course to: %s\n" , navigationSystem.getDestination());
        }else{
            System.out.println("Takeoff is cancelled! Maintenance is required\n");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        EngineUnit engineUnit = new EngineUnit(22, true);
        LifeSupport lifeSupport = new LifeSupport(51);
        NavigationSystem navigationSystem = new NavigationSystem("Saturn");

        Spaceship spaceship = new Spaceship("Suka", engineUnit , lifeSupport, navigationSystem);
        spaceship.launchSequence();
    }
}