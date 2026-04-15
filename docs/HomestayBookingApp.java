import java.util.*;
import java.time.format.*;
import java.time.LocalDate;  // class represents a date without time
import java.time.temporal.ChronoUnit;   //used to calculate difference between dates
// import java.time.format.DateTimeFormatter;


interface DateManager{
    public void addAvailableDate(String newDate);
    public void updateAvailableDates(int index);
    public void removeAvailableDates(int index);
    public void displayAvailableDates();
    public int getTotalAvailableDates();
    public String getDate(int index);
}

class Booking{
    private String bookingID, customerName, customerPhoneNo, checkIn, checkOut, homestayName, location;
    private double pricePerNight;
    private static int currentIDnum = 1;

    public Booking(String custName, String customerPhoneNo, String checkIn, String checkOut, String homestayName, String location, double price){
        this.bookingID = "B" + String.format("%03d", currentIDnum++);
        this.customerName = custName;
        this.customerPhoneNo = customerPhoneNo;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.homestayName = homestayName;
        this.location = location;
        this.pricePerNight = price;
    }

    public String getBookingID(){return bookingID;}
    public String getCustomerName(){return customerName;}
    public String getCheckIn(){return checkIn;}
    public String getCheckOut(){return checkOut;}

    public int getTotalDays(){

        // format for date 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // convert string into LocalDate object
        LocalDate inDate = LocalDate.parse(checkIn, formatter);
        LocalDate outDate = LocalDate.parse(checkOut, formatter);

        // get difference between two days
        return (int) ChronoUnit.DAYS.between(inDate, outDate);  
    }

    public double getTotalPrice(){
        return pricePerNight * getTotalDays();
    }

    public String getHomestayName(){return homestayName;}

    public void setHomestayName(String newName){
        this.homestayName = newName;
    }

    public void displayBooking(){
        System.out.println("Booking ID    : " + bookingID);
        System.out.println("Customer Name : " + customerName);
        System.out.println("Customer Phone: " + customerPhoneNo);
        System.out.println("Homestay Name : " + homestayName);
        System.out.println("Location      : " + location);
        System.out.println("Check-In Date : " + checkIn);
        System.out.println("Check-Out Date: " + checkOut);
        System.out.println("Total Days    : " + getTotalDays());
        System.out.println("Total Price   : RM " + getTotalPrice() + " (RM " + pricePerNight + "/night)");
    }
}

class Homestay implements DateManager{
    private String homestayID, name, location, ownerName, ownerNumber;
    private double pricePerNight;
    private ArrayList<String> availableDates;
    private ArrayList<Booking> bookings;
    private static int currentIDnum = 1;
    Scanner input = new Scanner(System.in);

    public Homestay(String name, String location, double pricePerNight, String ownerName, String ownerNumber){
        this.homestayID = "H" + String.format("%03d", currentIDnum++);
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.ownerName = ownerName;
        this.ownerNumber = ownerNumber;
        availableDates = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void setPricePerNight(double newPrice){
        this.pricePerNight = newPrice;
    }

    public String getID(){return homestayID;}
    public String getName() {return name;}
    public String getLocation(){return location;}
    public double getPricePerNight(){return pricePerNight;}
    public String getOwnerName(){return ownerName;}
    public String getOwnerNumber(){return ownerNumber;}
    public int getTotalAvailableDates(){return availableDates.size();}
    public String getDate(int i){return availableDates.get(i);}
    

    public void addBooking(Booking newBooking){
        bookings.add(newBooking);
    }

    public Booking getBooking(int i){return bookings.get(i);}
    public int getTotalBooking(){return bookings.size();}

    public void viewAllBooking(){
        if (bookings.isEmpty()) {
            System.out.println("No Booking Currently :(");
            // System.out.print("\nPlease press Enter to continue...");
            // input.nextLine();
            System.out.println();
            return ;
        } 
        else{
            for(int i=0; i<bookings.size(); i++){
                System.out.println("------------ Booking " + (i + 1) + " ------------");
                Booking temp = bookings.get(i);
                temp.displayBooking();
                System.out.println();
            }
        }
    }
    
    public String toString(){
        return "Name           : " + name + "\n" +
               "Location       : " + location + "\n" +
               "Price Per Night: RM " + pricePerNight + "\n";
    }

    //interface methods
    public void addAvailableDate(String newDate){
        availableDates.add(newDate);
    }

    public void updateAvailableDates(int index){
        String originalDate = availableDates.get(index);

        availableDates.set(index, originalDate + " (Booked)");
    }

    public void removeAvailableDates(int index){
        availableDates.remove(index);
    }

    public void displayAvailableDates(){
        
        if (availableDates.isEmpty()) {
            System.out.println("No Booking Currently :(");
            System.out.print("\nPlease press Enter to continue...");
            input.nextLine();
            System.out.println("\n");
            return ;
        } 
        else{
            System.out.println("Available Dates:");
            for (int i = 0; i < availableDates.size(); i++) {
                System.out.println((i + 1) + ". " + availableDates.get(i));
            }
        }
    }
}

class HomestayManager{
    private ArrayList<Homestay> AllHomestayList;
    Scanner input = new Scanner(System.in);

    public HomestayManager(){
        AllHomestayList = new ArrayList<>();
    }

    public void addHomestay(Homestay newHomestay){
        AllHomestayList.add(newHomestay);
    }

    public void removeHomestay(String homestayID){
        for(int i=0; i<AllHomestayList.size(); i++){
            Homestay temp = AllHomestayList.get(i);

            if(temp.getID().equals(homestayID)){
                AllHomestayList.remove(i);
                return;
            }
        }
    }

    public void displayAllHomestay(){
        if (AllHomestayList.isEmpty()) {
            System.out.println("No Homestay Currently :(");
            System.out.print("\nPlease press Enter to continue...");
            input.nextLine();
            System.out.println("\n");
            return ;
        } 
        else {
            for (int i=0; i<AllHomestayList.size(); i++) {
                Homestay temp = AllHomestayList.get(i);
                System.out.println("------------ Homestay " + (i + 1) + " ------------");
                System.out.print(temp);
                System.out.println("Owner          : " + temp.getOwnerName());
                System.out.println("Contact No     : " + temp.getOwnerNumber());
                System.out.println();
            }
            System.out.print("Please press Enter to continue...");
            input.nextLine();
        }
    }

    public Homestay getHomestay(int i){
        return AllHomestayList.get(i);
    }

    public int getTotalHomestays(){return AllHomestayList.size();}
}

abstract class User{
    protected String name, phoneNo;

    public User(String name, String phoneNo){
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public String getName(){return name;}
    public String getPhoneNo(){return phoneNo;}
    abstract void displayMenu();
}

class Customer extends User{
    private ArrayList<Booking> bookings;
    private HomestayManager manager;
    Scanner input = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  //set the expected date format  

    public Customer(String name, String phoneNo, HomestayManager manager){
        super(name, phoneNo);
        this.manager = manager;
        bookings = new ArrayList<>();
    }

    // override abstract
    public void displayMenu(){
        System.out.println("======================================");
        System.out.println("             CUSTOMER MENU            ");
        System.out.println("======================================");
        System.out.println("1. View All Homestays");
        System.out.println("2. Make Booking");
        System.out.println("3. View My Booking");
    }

    //association
    public void viewAllHomestays(){
        manager.displayAllHomestay();
        return ;
    }

    //composition
    public void makeBooking(){
        int index, checkIn, checkOut;
        manager.displayAllHomestay();
        if(manager.getTotalHomestays() == 0){
            return ;
        }

        System.err.println();

        //exception handling
        do{
            try {
                System.out.print("Enter no. on the list to select homestay (press 0 to cancel): ");
                index = input.nextInt();
                input.nextLine(); 

                if (index == 0) {
                    return; 
                } else if (index > manager.getTotalHomestays() || index <= 0) {
                    System.out.println("\nInvalid input. Please enter a number from the list.");
                    index = -1; 
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number.");
                input.nextLine();
                index = -1;
            }
        } while (index == -1);

        index--;
        Homestay selectedHomestay = manager.getHomestay(index);

        System.out.println();

        boolean isContinuousBlock;
        do{
            isContinuousBlock = true;
            System.out.println("======================================");
            System.out.println("          SELECTED HOMESTAY           ");
            System.out.println("======================================");
            System.out.println(selectedHomestay);

            selectedHomestay.displayAvailableDates();
            System.out.println();
    
            //exception handling
            do {
                try {
                    System.out.print("Enter no. on the list for Check In date (press 0 to cancel): ");
                    checkIn = input.nextInt();
                    input.nextLine(); 

                    if(checkIn == 0){
                        return ;
                    }
    
                    if (checkIn <= 0 || checkIn > selectedHomestay.getTotalAvailableDates()) {
                        System.out.println("\nInvalid input. Please enter again.");
                        checkIn = -1; 
                    }
                    else if (selectedHomestay.getDate(checkIn - 1).contains("(Booked)")) {
                        System.out.println("\nDate is already booked. Please choose another.");
                        checkIn = -1;
                    }
    
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a number.");
                    input.nextLine(); 
                    checkIn = -1; 
                }
            }while (checkIn == -1);
    
            do {
                try {
                    System.out.print("Enter no. on the list for Check Out date (press 0 to cancel): ");
                    checkOut = input.nextInt();
                    input.nextLine();

                    if(checkOut == 0){
                        return ;
                    }
    
                    if (checkOut > selectedHomestay.getTotalAvailableDates() || checkOut == checkIn || checkOut <= 0) {
                        System.out.println("\nInvalid input. Please enter a different and valid number.");
                        checkOut = -1; 
                    }
                    else if (selectedHomestay.getDate(checkOut - 1).contains("(Booked)")) {
                        System.out.println("\nDate is already booked. Please choose another date.");
                        checkIn = -1;
                    }
    
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid number.");
                    input.nextLine(); 
                    checkOut = -1; 
                }
            }while (checkOut == -1);
    
            checkIn--;
            checkOut--;

            //validating to ensure must always checkOut > checkIn
            if(checkOut < checkIn){
                isContinuousBlock = false;
            }
            else{
                for (int i = checkIn; i < checkOut; i++) {
                    String dateStr1 = selectedHomestay.getDate(i);
                    String dateStr2 = selectedHomestay.getDate(i + 1);

                    // Reject if any of the dates are marked as booked
                    if (dateStr1.contains("(Booked)") || dateStr2.contains("(Booked)")) {
                        isContinuousBlock = false;
                        break;
                    }

                    LocalDate date1 = LocalDate.parse(selectedHomestay.getDate(i), formatter);
                    LocalDate date2 = LocalDate.parse(selectedHomestay.getDate(i + 1), formatter);
        
                    if (!date1.plusDays(1).equals(date2)) {  //checks that each date in the range is exactly one day apart from the next. //if not, dates are not continuous and reject
                        isContinuousBlock = false;
                        break;
                    }
                }
            }
            
            if(isContinuousBlock == true){
                //composition
                Booking newBooking = new Booking(name, phoneNo, selectedHomestay.getDate(checkIn), selectedHomestay.getDate(checkOut), selectedHomestay.getName(), selectedHomestay.getLocation(), selectedHomestay.getPricePerNight());
                
                bookings.add(newBooking);
                selectedHomestay.addBooking(newBooking);
                
                //remove booked date from database
                for(int i=checkOut; i >= checkIn; i--){
                    selectedHomestay.updateAvailableDates(i);
                }

                System.out.println("\nYour booking has been made!\n");
                System.out.print("Please press Enter to continue...");
                input.nextLine();
            }
            else{
                System.out.println("\n**Invalid input.**");
                System.out.println("**1) Check for any booked dates in between**");
                System.out.println("**2) Check out date must be greater than Check in date**");
                System.out.println("**Kindly try again or contact the homestay owner for further assistance**");
                System.out.print("\nPlease press Enter to continue...");
                input.nextLine();
                System.out.println("\n");
            }

        }while(isContinuousBlock == false);
    }

    public void viewMyBooking(){
        if(bookings.isEmpty()){
            System.out.println("No Booking Yet:(");
            System.out.print("\nPlease press Enter to continue...");
            input.nextLine();
            System.out.println("\n");
            return ;
        }
        else{
            for(int i=0; i<bookings.size(); i++){
                Booking temp = bookings.get(i);
                System.out.println("------------ Booking " + (i + 1) + " ------------");
                temp.displayBooking();
                System.out.println();
            }
            System.out.print("Please press Enter to continue...");
            input.nextLine();
        }
    }
}

class Owner extends User{
    private String ownerID, password;
    private ArrayList<Homestay> homestayList;
    private HomestayManager manager;
    Scanner input = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //set the expected date format 
    

    public Owner(String name, String phoneNo, String ownerID, String password, HomestayManager manager){
        super(name, phoneNo);
        this.ownerID = ownerID;
        this.password = password;
        this.manager = manager;
        homestayList = new ArrayList<>();
    }

    //overide abstract 
    public void displayMenu(){
        System.out.println("======================================");
        System.out.println("              OWNER MENU              ");
        System.out.println("======================================");
        System.out.println("1. Add Homestay");
        System.out.println("2. Remove Homestay");
        System.out.println("3. View My Homestay");
        System.out.println("4. View All Booking");
    }

    //composition 
    public void addHomestay(){ 
        int stopper = 1;
        double price;
        int numDates;

        do{
            System.out.println();
            System.out.print("Enter Homestay Name (press 0 to cancel): ");
            String homestayName = input.nextLine();

            if(homestayName.equals("0")){
                return ;
            }
    
            System.out.print("Enter Location: ");
            String location = input.nextLine();
            
            // exception handling
            do{
                try{
                    System.out.print("Enter Price Per Night (RM): ");
                    price = input.nextDouble();
                    input.nextLine(); 

                    if (price <= 0) {
                        System.out.println("\nPrice must be a positive number.");
                        
                        price = -1;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid number.");
                    input.nextLine();
                    price = -1;
                }

            }while(price == -1);
            
            //create new homestay object //composition
            Homestay newHomestay = new Homestay(homestayName, location, price, name, phoneNo);
            
            do{
                try{
                    System.out.print("Enter Number of Available Dates: ");
                    numDates = input.nextInt();
                    input.nextLine();

                    if (price <= 0) {
                        System.out.println("\nPrice must be a positive number.");
    
                        numDates = -1;
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid number.");
                    input.nextLine();
                    numDates = -1;
                }

            }while(numDates == -1);
    
            System.out.println();
            System.out.println("**Date Format DD/MM/YYYY**");
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  //set the expected date format 

            for (int i = 0; i < numDates; i++) {
                String date;
                boolean valid;
                do{
                    System.out.print("Date " + (i + 1) + ": ");
                    date = input.nextLine();

                    try {
                        LocalDate.parse(date, formatter); // Check if the date the user typed matches the format and throw error if wrong format
                        valid = true; 
                    } 
                    catch (DateTimeParseException e) {
                        System.out.println("\nInvalid format! Please enter date as DD/MM/YYYY.");
                        valid = false;
                    }
                }while(!valid);

                newHomestay.addAvailableDate(date);
            }
            
            //add homestay to Owner and HomestayManager 
            homestayList.add(newHomestay);
            manager.addHomestay(newHomestay);

    
            System.out.println();
            System.out.println("Homestay Added!");
            System.out.println("======================================");
            System.out.println("         ADDING NEW HOMESTAY          ");
            System.out.println("======================================\n");
            viewMyHomestays();

            System.out.print("\nadd more? (Y/N): ");
            String choice = input.nextLine();

            if(choice.equals("N") || choice.equals("n")){
                stopper = 0;
            }

        }while(stopper != 0);
    }

    public void removeHomestay(){
        int stopper = 1;
        int index;

        do{
            viewMyHomestays();
            if(homestayList.isEmpty()){
                System.out.print("\nPlease press Enter to continue...");
                input.nextLine();
                System.out.println("\n");
                return ;
            }
            System.out.println();

            //exception handling
            do {
                try {
                    System.out.print("Enter no. on the list to remove a homestay (press 0 to cancel): ");
                    index = input.nextInt();
                    input.nextLine(); // clear the buffer

                    if(index == 0){
                        return ;
                    }

                    if (index > homestayList.size() || index <= 0) {
                        System.out.println("\nInvalid input. Please enter a valid number from the list.");
                        index = -1;
                    }

                } 
                catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a number.");
                    input.nextLine(); // clear buffer
                    index = -1;
                }
            }while (index == -1);
            
            index--;

            Homestay temp = homestayList.get(index);

            for(int i=0; i<temp.getTotalBooking(); i++){
                temp.getBooking(i).setHomestayName("Homestay unavailable. Full refund issued.");
            }
            
            //remove homestay from Owner and HomestayManager 
            manager.removeHomestay(homestayList.get(index).getID());
            homestayList.remove(index);

    
            System.out.println();
            System.out.println("Homestay Removed!");
            System.out.println("======================================");
            System.out.println("           REMOVE HOMESTAY            ");
            System.out.println("======================================\n");
            viewMyHomestays();
    
            System.out.print("\nremove more? (Y/N): ");
            String choice = input.nextLine();
    
            if(choice.equals("N") || choice.equals("n")){
                stopper = 0;
            } 
        }while(stopper != 0);

    }

    public void viewMyHomestays(){
        if (homestayList.isEmpty()) {
            System.out.println("No Homestay Added Yet :(");
        } 
        else {
            for (int i=0; i<homestayList.size(); i++) {
                Homestay temp = homestayList.get(i);
                System.out.println((i + 1) + ") " + temp.getName());
            }
        }
    }

    public void viewMyHomestaysDetails(){
        int editChoice;
        int index;

        if (homestayList.isEmpty()) {
            System.out.println("No Homestay Added Yet :(");
            System.out.print("\nPlease press Enter to continue...");
            input.nextLine();
            System.out.println("\n");
            return ;
        }
        else{
            for (int i=0; i<homestayList.size(); i++) {
                Homestay temp = homestayList.get(i);
                System.out.println("------------ Homestay " + (i + 1) + " ------------");
                System.out.println("Homestay ID    : " + temp.getID());
                System.out.println(temp);
                temp.displayAvailableDates();
                System.out.println();
            }
        }
        
        System.out.print("Edit Homestay? (Y/N): ");
        String choice = input.nextLine();

        if(choice.equals("Y") || choice.equals("y")){

            //exception handling
            do {
                try {
                    System.out.print("Enter Homestay No. (e.g : 1): ");
                    index = input.nextInt();
                    input.nextLine();

                    if (index > homestayList.size() || index <= 0) {
                        System.out.println("\nInvalid input. Please enter a number from the list.");
                        index = -1; 
                    }

                }catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a number.");
                    input.nextLine();
                    index = -1;
                }
            } while (index == -1);

            index--;
            Homestay selectedHomestay = homestayList.get(index);

            do{
                do {
                    try {
                        System.out.println("\n======================================");
                        System.out.println("          EDIT HOMESTAY " + selectedHomestay.getID());
                        System.out.println("======================================");
                        System.out.println("1. Change price per night");
                        System.out.println("2. Add available date");
                        System.out.println("3. Remove available date");
                        System.out.print("\nChoose an option (press 0 to cancel): ");

                        editChoice = input.nextInt();
                        input.nextLine(); 

                        if (editChoice < 0 || editChoice > 3) {
                            System.out.println("Invalid choice. Please choose between 0 and 3.");
                            editChoice = -1; 
                        }

                    } 
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine(); 
                        editChoice = -1;
                    }

                }while (editChoice == -1);

                switch(editChoice){
                    case 1: 
                        System.out.print("Enter new price per night: RM ");
                        double newPrice = input.nextDouble();
                        input.nextLine(); // clear newline

                        selectedHomestay.setPricePerNight(newPrice);
                        System.out.println("\nPrice updated successfully.");
                        System.out.println(selectedHomestay);
                        System.out.println();
                        break;

                    case 2:
                    System.out.println();
                        selectedHomestay.displayAvailableDates();
                        System.out.println();
                        System.out.print("How many to add?: ");
                        int numDates = input.nextInt();
                        input.nextLine();
                
                        System.out.println();
                        System.out.println("**Date Format DD/MM/YYYY**");
                        for (int i = 0; i < numDates; i++) {
                            String date;
                            boolean valid;
                            
                            //exception handling
                            do {
                                System.out.print("Date " + (i + 1) + ": ");
                                date = input.nextLine();

                                try {
                                    LocalDate.parse(date, formatter); // Try to parse date
                                    valid = true;
                                } 
                                catch (DateTimeParseException e) {
                                    System.out.println("\nInvalid format! Please enter the date as DD/MM/YYYY.");
                                    valid = false;
                                }
                            } while (!valid);

                            selectedHomestay.addAvailableDate(date); // only add if valid
                        }


                        System.out.println("\nDate added successfully.\n");
                        selectedHomestay.displayAvailableDates();
                        System.out.println();
                        break;

                    case 3:
                        System.out.println();
                        selectedHomestay.displayAvailableDates();

                        do {
                            try {
                                System.out.print("\nEnter no. on the list to remove a date: ");
                                index = input.nextInt();
                                input.nextLine();

                                if (index > selectedHomestay.getTotalAvailableDates() || index <= 0) {
                                    System.out.print("\nInvalid input. Please enter a valid number from the list.");
                                    index = -1; 
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("\nInvalid input. Please enter a whole number.");
                                input.nextLine(); 
                                index = -1; 
                            }
                        } while (index == -1);

                        index--;

                        selectedHomestay.removeAvailableDates(index);
                        System.out.println("\nDate removed successfully.");
                        selectedHomestay.displayAvailableDates();
                        System.out.println();
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }

                System.out.print("Please press Enter to continue...");
                input.nextLine();

            }while(editChoice != 0);

        }
    }

    public void viewAllBooking(){
        if(homestayList.isEmpty()){
            System.out.println("No Booking Currently :(");
            System.out.print("\nPlease press Enter to continue...");
            input.nextLine();
            System.out.println("\n");
            return ;
        }

        for(int i=0; i<homestayList.size(); i++){
            Homestay temp = homestayList.get(i);
            System.out.println("======================================");
            System.out.println("         HOMESTAY: " + temp.getName());
            System.out.println("======================================\n");

            // System.out.println("------------ Booking " + (j + 1) + " ------------");
            temp.viewAllBooking();
            System.out.println();
        
        }
        System.out.print("Please press Enter to continue...");
        input.nextLine();
    }
}

public class HomestayBookingApp{
    public static User loginChecker(String[] passwordList, String[] ownerIDList, String password, String ownerID, ArrayList<User> list) {
        for (int i = 0; i < ownerIDList.length; i++) {
            if (ownerIDList[i].equals(ownerID) && passwordList[i].equals(password)) {
                return list.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args){

        HomestayManager manager = new HomestayManager();
        User user1 = new Customer("Ahmad", "0123456789", manager);
        User user2 = new Owner("Labu", "0198765432", "O001", "owner1", manager);
        User user3 = new Owner("Labi", "0137894562", "O002", "owner2", manager);

        String[] passwordList = { "owner1", "owner2"};
        String[] ownerIDList = { "O001", "O002"};
        ArrayList<User> ownerList = new ArrayList<>();
        ownerList.add(user2);
        ownerList.add(user3);

        Scanner input = new Scanner(System.in);
        int category;

        do{
            //exception handling
            do{
                try {
                    System.out.println("======================================");
                    System.out.println("      TAMAN RIA HOMESTAY BOOKING     ");
                    System.out.println("======================================");
                    System.out.println();
                    System.out.print("1. Owner \n2. Customer \n3. exit \n \nchoose category: ");
                    category = input.nextInt();
                    input.nextLine(); // clear buffer
                }
                catch(InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a number.");
                    input.nextLine(); // clear buffer
                    category = -1; 
                }
            }while(category == -1);
    
            System.out.println();
            switch (category)
            {
                case 1:
                    User currentUser;
                    int attempleft = 3;
    
                    do{ 
                        System.out.print("Admin ID: ");
                        String ownerID = input.nextLine();
        
                        System.out.print("Password: ");
                        String password = input.nextLine();
        
                        currentUser = loginChecker(passwordList, ownerIDList, password, ownerID, ownerList);
                        
                        if (currentUser != null){
                            System.out.println("\n");
                            System.out.println("Login Sucessfull!");
                        } 
                        else{
                            System.out.println("======================================");
                            System.out.println("Login failed. Invalid ID or password. Attempt left: " + --attempleft);
                            
                        }
  
                    }while(currentUser == null && attempleft > 0);
                    
                    if(attempleft == 0){
                        System.out.println();
                        break;
                    }
                    
                    int option_owner;
                    do {
                        //polymorphism 
                        currentUser.displayMenu(); 
                        Owner currentOwner = (Owner) currentUser;

                        //exception handling
                        do{
                            try {
                                System.out.print("Choose option (0 to Main Menu): ");
                                option_owner = input.nextInt();
                                input.nextLine();
                            } 
                            catch(InputMismatchException e) {
                                System.out.println("\nInvalid input. Please enter a number.");
                                input.nextLine(); // clear bad input
                                option_owner = -1;
                                continue;
                            }
                        }while(option_owner == -1);
                        
                        System.out.println("\n");

                        switch (option_owner) {
                            case 1:
                                System.out.println("======================================");
                                System.out.println("         ADDING NEW HOMESTAY          ");
                                System.out.println("======================================\n");
                                currentOwner.addHomestay();
                                break;

                            case 2:
                                System.out.println("======================================");
                                System.out.println("           REMOVE HOMESTAY            ");
                                System.out.println("======================================\n");
                                currentOwner.removeHomestay();
                                break;

                            case 3:
                                System.out.println("======================================");
                                System.out.println("         LIST OF OWNED HOMESTAY       ");
                                System.out.println("======================================\n");
                                currentOwner.viewMyHomestaysDetails();
                                break;

                            case 4:
                                System.out.println("======================================");
                                System.out.println("           VIEW ALL BOOKINGS          ");
                                System.out.println("======================================\n");
                                currentOwner.viewAllBooking();
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
    
                    } while (option_owner != 0); // back to main menu when option is 0
                    break;
                    
                case 2:
                    int option_cust;

                    do{
                        //polymorphism
                        user1.displayMenu();
                        Customer customer = (Customer) user1;

                        //exception handling
                        do{
                            try {
                                System.out.print("Choose option (0 to Main Menu): ");
                                option_cust = input.nextInt();
                                input.nextLine();
                            } 
                            catch(InputMismatchException e) {
                                System.out.println("\nInvalid input. Please enter a number.");
                                input.nextLine(); // clear bad input
                                option_cust = -1;
                                continue;
                            }
                        }while(option_cust == -1);

                        System.out.println("\n");

                        switch(option_cust){
                            case 1:
                                System.out.println("======================================");
                                System.out.println("          VIEW ALL HOMESTAYS          ");
                                System.out.println("======================================\n");
                                customer.viewAllHomestays();
                                break;

                            case 2: 
                                System.out.println("======================================");
                                System.out.println("             MAKE BOOKING             ");
                                System.out.println("======================================\n");
                                customer.makeBooking();
                                break;
                            
                            case 3: 
                                System.out.println("======================================");
                                System.out.println("           VIEW MY BOOKINGS           ");
                                System.out.println("======================================\n");
                                customer.viewMyBooking();
                                break;
                            
                            default:   
                                System.out.println("Invalid choice.");
                                break;
                        }

                    }while(option_cust != 0);
                    break;
                    
                case 3: 
                    System.out.println("Thank you for using our app! please come again :>");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

        }while(category != 3);

        System.out.print("\nPlease press Enter to continue...");
        input.nextLine();
        input.close();
    }
}