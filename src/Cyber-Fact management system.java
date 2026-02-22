import java.util.ArrayList;

interface Workable{
    void doWork();
}

interface Chargeable{
    void charge();
}

class Robot{
    protected String serialNumber;
    protected int batteryLevel;

    public Robot(String serialNumber, int batteryLevel){
        this.serialNumber = serialNumber;
        this.batteryLevel = batteryLevel;
    }

    public void showStatus(){
        System.out.println("Serial Number:" + serialNumber + " | Battery level: " + batteryLevel);
    }
}

class WelderRobot extends Robot implements Workable , Chargeable{
    public WelderRobot(String serialNumber , int batteryLevel){
        super(serialNumber, batteryLevel);
    }

    @Override
    public void doWork(){
        System.out.println("Robot: " + serialNumber + " cooks steel sheets. The charge is falling!");
        batteryLevel -= 10;
    }

    @Override
    public void charge(){
        System.out.println("Charging... The charge is full!");
        batteryLevel = 100;
    }
}

class Drone extends Robot implements Workable{
    public Drone(String serialNumber , int batteryLevel){
        super(serialNumber , batteryLevel);
    }

    @Override
    public void doWork(){
        System.out.println("Drone:" + serialNumber + " flies and scan the territory");
    }
}

class FactoryApp{
    public static void main(String[] args) {
        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(new WelderRobot("W-11" , 15));
        robots.add(new WelderRobot("W-21" , 80));
        robots.add(new Drone("D-12" , 10));
        robots.add(new WelderRobot("W-123" , 50));

        for (Robot r : robots){


            if (r instanceof Workable workable) workable.doWork();
            if (r.batteryLevel < 20 && r instanceof Chargeable chargeable) chargeable.charge();
        }
    }
}
