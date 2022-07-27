/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightsearchsystem;

/**
 *
 * @author Rawan
 */

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Flight {
    GregorianCalendar date;
    double price;
    String time;
    String From;
    String to;
    String carrier;
    FlightTree flightTree = new FlightTree();
    CityTree cityTree = new CityTree();

    public Flight(GregorianCalendar date, double price, String From, String to, String carrier) {
        this.date = date;
        this.price = price;
        this.From = From;
        this.to = to;
        this.carrier = carrier;
        time = gettime();

        flightTree.insert(this.date,this);
        cityTree.insert(this);
    }
    public String gettime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(date.getTime());
        setTime(time);
        return time;
    }
    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(this.date.getTime());
        return date;
    }
    public String getPrice(){
        String str = price+"";
        return str;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    @Override
    public boolean equals(Object o){
        Flight fl = (Flight) o;
        if(date.equals(fl.date) && price == fl.price && time.equals(fl.time) && From.equals(fl.From) && to.equals(fl.to) &&
                carrier.equals(fl.carrier))
            return true;
        else
            return false;
    }

    public void addFlight(Flight flight){
       flightTree.insert(flight.date,flight);
       cityTree.insert(flight);
    }
    public void removeFlight(Flight flight){
        flightTree.delete(flight.date);
        //cityTree.delete(flight);
        System.out.println("Flight was removed successfully");
    }
    public void display(){
        flightTree.traverseLevelOrder(flightTree.root);
    }
    public void searchDate(GregorianCalendar date){
        Flight flight =  flightTree.searchDate(date);
        if(flight != null){
            System.out.println(flight.toString());
        }
        else{
            System.out.println("No flights availabe");
        }

    }
    public void searchFrom(String city){
        cityTree.searchFromCity(city);
    }
    public void searchFromDate(String city , GregorianCalendar date){
        Flight flight = flightTree.searchFromDate(city, date);
        if(flight != null)
            System.out.println(flight.toString());
        else
            System.out.println("No flights availabe");
    }
    
    public void searchWithDates(GregorianCalendar date1, GregorianCalendar date2){
        flightTree.search2Dates(date1, date2);
    }
    public void searchFlexibleDates(GregorianCalendar date) {
        Flight before = flightTree.floor(date);
        Flight after = flightTree.ceil(date);
        System.out.println("Dates available: ");
        if (before != null) {
            System.out.println(before);
        }
        if (after != null) {
            if (before != null) {
                if (!before.equals(after)) {
                    System.out.println(after);
                }
            }
            else{
                System.out.println(after);
            }
        }

        if (before == null && after == null) {
            System.out.println("No flights available");
        }
    }
    public void searchPriceDate(GregorianCalendar date, double price){
        Flight flight =  flightTree.searchPriceDate(date, price);
        if(flight != null){
            System.out.println(flight.toString());
        }
        else{
            System.out.println("No flights available");
        }
    }

    @Override
    public String toString() {
        return "Flight{" + "date= " + date.getTime() + ", price= " + price + ", time= " + time + ", From= " + From + ", to= " + to + ", carrier= " + carrier + '}';
    }
    // write to file
    public  void createReport(){
        File file = new File("flight.txt");
        try{
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            pw.println("Date "+ "     Time"+"  From " +" To "+"Carrier "+"Price ");
            java.util.LinkedList<TreeNode> list = new java.util.LinkedList<TreeNode>();
            list.add(flightTree.root);
            while (!list.isEmpty()) {
                TreeNode d = list.removeFirst();
                //System.out.println("");
                if (d.left != null) {
                    list.addLast(d.left);
                }
                if (d.right != null) {
                    list.addLast(d.right);
                }
                insertInFile(pw,d.data.getDate(),d.data.gettime(),d.data.From,d.data.to,d.data.carrier,d.data.getPrice());
            }
            pw.close();

        }
        catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }
    private void insertInFile(PrintWriter pw, String a , String b, String c, String d, String e, String f){
        pw.println(a+" " + b+" "+ c+" " + d +" " + e+"    "+ f+" ");
    }
    

}

