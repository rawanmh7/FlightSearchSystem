/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightsearchsystem;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;


/**
 *
 * @author Rawan
 */
public class FlightSearchSystem {

    static Scanner input = new Scanner(System.in);
    static Flight myFlight = null;
    public static void main(String[] args) {
        
        System.out.println("Hi.");
        
        
        myFlight = readFirstFlight();
        readfromfile(); // reads all flights from text table
        
        while(true ){
            System.out.println(" ");
            menu();
            int option = input.nextInt();
            if(option == 1){
                GregorianCalendar gg = getDate();
                System.out.println("Enter flight price");
                double price = input.nextDouble();
                System.out.println("Enter flight from city");
                String city = input.next();
                System.out.println("Enter flight to city");
                String toCity = input.next();
                System.out.println("Enter flight carrier type");
                String carrier = input.next();
                Flight newFlight = new Flight(gg, price, city, toCity, carrier);    
                addFlight(newFlight);
            }
            if(option == 2){
                removeFlight();
            }
            if(option == 3){
                option3();
            }
            if(option == 4){
                option4();
            }
            if(option == 5){
                option5();
            }
            if(option == 6){
                option6();
            }
            if(option == 7){
                option7();
            }
            if(option == 8){
                option8();
            }
            if(option == 9){
                myFlight.createReport(); // update the flight text file
                System.out.println("Thank You.");
                break;
            }
//            if(option == 10){ // optional
//                dispalyFlights();
//            }
        }    
        
    }

    public static void menu(){
        System.out.println(" ");
        System.out.println("1)Add flight." +"\n2)Remove Flight" + "\n3)Search by date"+"\n4)Search by flexible dates"
                +"\n5)Search by From city" +"\n6)Search with from city and date" + "\n7)Search between two dates" +
                "\n8)Search with price and date"+"\n9)Exist");
        System.out.println("Choose an option:");

    }
    public static void addFlight(Flight flight){
        myFlight.addFlight(flight);
    }
    public static void removeFlight(){

        GregorianCalendar gg = getDate();
        System.out.println("Enter flight price:");
        double price = input.nextDouble();
        System.out.println("Enter flight from city:");
        String city = input.next();
        System.out.println("Enter flight to city:");
        String toCity = input.next();
        System.out.println("Enter flight carrier type:");
        String carrier = input.next();
        Flight deletedFlight = new Flight(gg, price, city, toCity, carrier);

        myFlight.removeFlight(deletedFlight);
    }
    public static void option3(){
        GregorianCalendar date = getDate();
        myFlight.searchDate(date);
    }

    public static void option4(){
        GregorianCalendar date = getDate();
        myFlight.searchFlexibleDates(date);

    }
    public static void option5(){
        System.out.println("Enter city name: ");
        String city = input.next();
        myFlight.searchFrom(city);
    }
    public static void option6(){
        System.out.println("Enter from city:");
        String city = input.next();
        GregorianCalendar date = getDate();
        myFlight.searchFromDate(city, date);
    }
    public static void option7(){
        GregorianCalendar date1 = getDate();
        GregorianCalendar date2 = getDate();
        myFlight.searchWithDates(date1, date2);
    }
    public static void option8(){
        GregorianCalendar date = getDate();
        System.out.println("Enter price: ");
        double price = input.nextDouble();
        myFlight.searchPriceDate(date, price);
    }
    public static void dispalyFlights(){ // optional
        myFlight.display();
    }
    public static GregorianCalendar getDate(){
        System.out.println("Enter flight day:");
        int day = input.nextInt();
        System.out.println("Enter flight month:");
        int month = input.nextInt();
        System.out.println("Enter flight year:");
        int year = input.nextInt();
        System.out.println("Date with specific time ? Yes/No"); 
        String answer = input.next();
        if(answer.equalsIgnoreCase("Yes")){
            System.out.println("Enter hour: ");
            int hour = input.nextInt();
            System.out.println("Enter minute:");
            int min = input.nextInt();
            GregorianCalendar g = new GregorianCalendar(year, month-1, day,hour,min);
            return g;
        }
        else{ // generates date with default time 00:00:00
            GregorianCalendar gg = new GregorianCalendar(year,month-1,day);
            return gg;
        }
    } 
    public static void readfromfile(){
        try {
                Scanner s = new Scanner(new File("flight.txt"));
                String x = s.nextLine(); // ignore first & second line
                String y = s.nextLine();
                while (s.hasNext()){
                    String a = s.next();
                    String b = s.next();
                    String c = s.next();
                    String d = s.next();
                    String e = s.next();
                    String f = s.next();
                    String z = a+" " + b;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = sdf.parse(z);
                    GregorianCalendar date2 = new GregorianCalendar();
                    date2.setTime(date);
                    double price = Double.parseDouble(f);
                    Flight readflight = new Flight(date2,price,c,d,e);
                    addFlight(readflight);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // reads and returns the first flight in the text file
    private static Flight readFirstFlight() {
        Flight flight;
        try { 
            Scanner s = new Scanner(new File("flight.txt"));
            String x = s.nextLine(); // ignore first line
            String a = s.next();
            String b = s.next();
            String c = s.next();
            String d = s.next();
            String e = s.next();
            String f = s.next();
            String z = a + " " + b;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = sdf.parse(z);
            GregorianCalendar date2 = new GregorianCalendar();
            date2.setTime(date);
            double price = Double.parseDouble(f);
            flight = new Flight(date2, price, c, d, e);
            return flight;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}
