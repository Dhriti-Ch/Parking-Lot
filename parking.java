import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

class slot {
    int type;
    boolean filled;
    boolean electric;
    boolean paid = false;
    LocalTime en_time, ex_time;
    private String cid;
    slot(int type, boolean filled, boolean electric) {
        this.type = type;
        this.filled = filled;
        this.electric = electric;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setElectric(boolean electric) {
        this.electric = electric;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

public class parking {

    public static int payamount(LocalTime in, LocalTime out, boolean elec) {
        long hours = ChronoUnit.HOURS.between(in, out);
        int sum = 0;

        for (int i = 0; hours > 0; i++) {
            if (i == 0) {
                if (elec) sum += 50;
                else sum += 30;
            } else if (i == 1) {
                if (elec) sum += 30;
                else sum += 20;
            } else {
                if (elec) sum += 15;
                else sum += 10;
            }
            hours--;
        }
        return sum;
    }

    public static int getint(String s) {
        System.out.print(s);
        Scanner scn = new Scanner(System.in);
        return scn.nextInt();
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int floornum = getint("Input the number of floors: ");
        int typenum = getint("Input the number of types of vehicles: ");


        String[] types = new String[typenum];
        System.out.println("Please enter the name of the type of vehicles: ");
        for (int i = 0; i < typenum; i++)
            types[i] = sc.nextLine();


        int[][] floors = new int[floornum][typenum];

        System.out.println("Enter the frequency of each type of vehicle per floor: ");

        for (int i = 0; i < floornum; i++) {
            for (int j = 0; j < typenum; j++)
                floors[i][j] = sc.nextInt();
        }
        int[] sum_per_floor = new int[floornum];

        int max = 0;
        for (int i = 0; i < floornum; i++) {
            int sum = 0;
            for (int j = 0; j < typenum; j++)
                sum += floors[i][j];
            sum_per_floor[i] = sum;
            if (sum >= max) max = sum;
        }
        slot[][] slots = new slot[floornum][max];

        for (int i = 0; i < floornum; i++) {
            int x = 0, j = 0;
            for (int l = 0; l < typenum; l++) {
                for (j = x; j < x + floors[i][l]; j++) {
                    slots[i][j] = new slot(l, false, false);
                }
                x = j;
            }
            for (int k = sum_per_floor[i]; k < max; k++)
                slots[i][k] = new slot(-1, false, false);
        }
        int floorn = 0;
        int slotn = 0;
        String ticket;
        String[] tic;
        boolean open = true;
        while (open) {
            int[] count = new int[typenum];

            for (int i = 0; i < floornum; i++) {
                for (int j = 0; j < max; j++) {
                    if (!slots[i][j].filled) {
                        if (slots[i][j].type != -1) count[slots[i][j].type]++;
                    }
                }
            }
            for (int i = 0; i < typenum; i++) {
                System.out.println(types[i] + ": " + count[i]);
            }

            int c = getint("1)Entry\n2)Exit\n3)Payment\n4)Search vehicle\n");
            switch (c) {
                case 1 -> {
                    System.out.println("What is your vehicle type?");
                    for (int i = 0; i < typenum; i++)
                        System.out.println(i + 1 + ")" + types[i]);
                    int t = sc.nextInt() - 1;
                    System.out.println("Please enter the license plate number: ");
                    sc.nextLine();
                    String license = sc.nextLine();
                    System.out.println("Is your vehicle electric?(y/n): ");
                    String ans = "";
                    while (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n")) {
                        ans = sc.nextLine();
                    }
                    boolean elec = ans.equals("y");
                    String ticketnum = "";
                    boolean found = false;
                    for (int i = 0; i < floornum && !found; i++) {
                        for (int j = 0; j < max && !found; j++) {
                            if (slots[i][j].type == t) {
                                if (!slots[i][j].filled) {
                                    ticketnum = "floor - " + (char) (i + '0') + " slot - " + (char) (j + '0');
                                    found = true;
                                    slots[i][j].setFilled(true);
                                    slots[i][j].setCid(license);
                                    slots[i][j].setElectric(elec);
                                    slots[i][j].en_time = LocalTime.now();
                                    break;
                                }
                            }
                        }
                    }
                    if (found) System.out.println("Your ticket number is: " + ticketnum);
                    else System.out.println("Sorry, we dont have a free slot for your vehicle type.");
                }
                case 2 -> {
                    System.out.println("Please enter you ticket number: ");
                    ticket = sc.nextLine();
                    tic = ticket.split(",");
                    floorn = Integer.parseInt(tic[0]);
                    slotn = Integer.parseInt(tic[1]);
                    System.out.println(floorn + " " + slotn);
                    slots[floorn][slotn].ex_time = LocalTime.now();
                    if (slots[floorn][slotn].paid) {
                        System.out.println("Thank you for using our services!");
                        slots[floorn][slotn].setFilled(false);
                        slots[floorn][slotn].setPaid(false);
                    } else {
                        System.out.println("Please pay the ticket fee");
                        int amount = payamount(slots[floorn][slotn].en_time, slots[floorn][slotn].ex_time, slots[floorn][slotn].paid);
                        payprint(sc, slots, floorn, slotn, amount);
                        slots[floorn][slotn].setFilled(false);
                        slots[floorn][slotn].setPaid(false);
                    }
                    System.out.println("Thank you for coming!");
                }
                case 3 -> {
                    System.out.println("Please enter you ticket number: ");
                    ticket = sc.nextLine();
                    tic = ticket.split(",");
                    floorn = Integer.parseInt(tic[0]);
                    slotn = Integer.parseInt(tic[1]);
                    if (!slots[floorn][slotn].paid) {
                        System.out.println("Please tell us your expected stay duration: ");
                        long hours = Math.round(sc.nextFloat());
                        slots[floorn][slotn].ex_time = slots[floorn][slotn].en_time.plusHours(hours);
                        int paymentam = payamount(slots[floorn][slotn].en_time, slots[floorn][slotn].ex_time, slots[floorn][slotn].electric);
                        payprint(sc, slots, floorn, slotn, paymentam);
                        slots[floorn][slotn].setFilled(false);
                        slots[floorn][slotn].setPaid(false);
                    } else {
                        System.out.println("You already paid your fee!");
                        slots[floorn][slotn].setFilled(false);
                        slots[floorn][slotn].setPaid(false);
                    }
                }
                case 4 -> {
                    System.out.println("Enter your license number: ");
                    String lp = sc.nextLine();
                    boolean f = false;
                    for (int i = 0; i < floornum && !f; i++) {
                        for (int j = 0; j < max && !f; j++) {
                            if (slots[i][j].getCid().equals(lp)) {
                                f = true;
                                System.out.println("Your slot number is floor - " + i + " slot - " + j);
                                break;
                            }
                        }
                    }
                    break;
                }
                case 3000 -> {
                    System.out.println("EXITING");
                    open = false;
                    break;
                }
                default -> System.out.println("Please choose a valid option: ");
            }
        }
    }

    public static void payprint(Scanner sc, slot[][] slots, int floorn, int slotn, int paymentam) {
        System.out.println("Please pay " + paymentam + ": ");
        System.out.println("1)UPI\n2)Cash\n");
        int c2 = sc.nextInt();
        switch (c2) {
            case 1 -> {
                onlinepay.online(paymentam, floorn, slotn, slots);
            }
            case 2 -> {
                offlinepay.offline(paymentam, floorn, slotn, slots);
            }
        }
    }

    interface pay {
        void online();

        void offline();
    }

    abstract class onlinepay implements pay {
        static void online(int amount, int floorn, int slotn, slot[][] slots) {
            System.out.println("The QR code is: \nNGGYU\nNGLYD\nNGRAADY\nThank you for the payment!\n");
            slots[floorn][slotn].setPaid(true);
        }
    }

    abstract class offlinepay implements pay {
        static void offline(int amount, int floorn, int slotn, slot[][] slots) {
            System.out.println("Thank you for coming!");
            slots[floorn][slotn].setPaid(true);
        }
    }
}
