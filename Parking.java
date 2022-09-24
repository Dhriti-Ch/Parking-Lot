import java.util.*;

import java.time.*;

// interface display {
//     //System.out.println("Name?");
// }
class display {
    // String name,vehicle_type;
    // display(String name, String vehicle_type){
    //     this.name=name;
    //     this.vehicle_type=vehicle_type;
    // }
    static void Display(String name, String vehicle_type){
        System.out.println("Name: "+name);
        System.out.println("Vehicle: "+vehicle_type);
    }
}

class Customer {
    LocalTime entry_time;
}

public class Parking extends display {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        // Assuming each floor has the same number of parking spots
        // int k = 0; // Number of parking spots per floor
        // int num_floor = 0; // Number of floors per vehicle type
        // int num_types = 0; // Number of types of vehicles
        int num_E = 0; // Number of entry points
        // int[] park = new int[k * num_floor * num_types];
        // // Customer();
        // // System.out.println(myObj);
        boolean open = true;
        while (open) {
            System.out.println("Welcome some bs. You are at entry point " + (int) Math.random() * num_E);
            System.out.println("Exit?(y/n)");
            String E=sc.next();
            if(E.equals("n")){
                System.out.println("Name?");
                String name = sc.nextLine();
                //code=0000
                if(name!="0000"){
                    Customer obj=new Customer();
                    System.out.println("Vehicle Type?");
                    String vehicle_Type = sc.nextLine();
                    System.out.println("Collect ticket");
                    Display(name,vehicle_Type);
                    LocalTime time = LocalTime.now();
                    obj.entry_time=time;
                    System.out.println("Entry Time:"+time);
                }
                else{
                    boolean admin=true;
                    while(admin){
                        System.out.println("1.Change Code");
                        System.out.println("2.Change the number of slots on floor f");
                        System.out.println("3.Change the type of slot");
                        System.out.println("4.Find Car");
                        System.out.println("5.Exit");
                        int x=sc.nextInt();
                        switch(x){
                            case 1:

                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                admin=false;
                                break;
                            default:
                                System.out.println("Enter a valid number");
                                break;
                        }
                    }
                }
            }
            else{
                //pay();
            }
        }
        sc.close();
    }
}