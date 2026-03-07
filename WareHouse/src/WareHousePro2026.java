import java.util.ArrayList;

interface Deliverable{
    void deliver(String address);
}

class Product{
    protected String name ;
    protected double price ;
    protected boolean isDelivered;

    public Product(String name , double price){
        this.name = name;
        this.price = price;
        this.isDelivered = false;
    }

    public void showInfo(){
        System.out.println("Name: " + name + "| Price: " + price + " | Delivered: " + isDelivered);
    }
}

class Electronics extends Product implements Deliverable{
    public Electronics(String name , double price ){
        super(name, price );
    }

    @Override
    public void deliver(String address){
        System.out.println("Shipping electronics to " + address + " .Be careful with batteries!");
        this.isDelivered = true;
    }
}

class Glassware extends Product implements Deliverable{
    public Glassware(String name,  double price){
        super(name , price);
    }

    @Override
    public void deliver(String address){
        System.out.println("Shipping glass to " + address + " .Packing in bubble wrap!");
        this.isDelivered = true;
    }
}

class Service extends Product{
    public Service(String name , double price){
        super(name, price);
        this.isDelivered = true;

    }

    @Override
    public void showInfo(){
        System.out.println("Service: " + name + " | Price: $" + price);
    }
}

class WareHouseApp{
    public static void main(String[] args) {
        ArrayList<Product> orderList = new ArrayList<>();
        orderList.add(new Electronics("SmartPhone" , 500.0));
        orderList.add(new Glassware("Set of Glasses" , 50.0));
        orderList.add(new Service("Windows setup" , 30.0));

        double totalSum = 0 ;

        for ( Product p : orderList){
            if (p instanceof Deliverable deliverable) deliverable.deliver("Main Street, 10");
        }

        if (!orderList.isEmpty() && orderList.get(0).isDelivered){
            System.out.println("\nRemoving finished order: " + orderList.get(0).name);
            orderList.remove(0);
        }

        System.out.println("Remaining items in warehouse: " + orderList.size());
    }
}