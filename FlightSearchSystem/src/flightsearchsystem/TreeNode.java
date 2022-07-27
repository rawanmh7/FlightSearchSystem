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
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TreeNode {
    public GregorianCalendar key;
    public Flight data;
    public TreeNode left;
    public TreeNode right;
    public int height = 1;

    public TreeNode(Flight data, GregorianCalendar key){
        this.data= data;
        this.key = key;
        left = null;
        right = null;
        height = 1;
    }
    public Flight min(){
        if(left == null){
            return this.data;
        }
        else{
            return left.min();
        }
    }
    public Flight searchDate(GregorianCalendar date){
        // user can search with both date and time if time is not specified, search with date is done and time is assumed to be 00:00:00
        if(date.equals(data.date)){
            return this.data;
        }
        if((date.get(Calendar.YEAR) == data.date.get(Calendar.YEAR)) && ( date.get(Calendar.MONTH) == data.date.get(Calendar.MONTH) )
                && date.get(Calendar.DAY_OF_MONTH) == data.date.get(Calendar.DAY_OF_MONTH) &&
                (  date.get(Calendar.HOUR_OF_DAY) == 0)){
            //System.out.println("we are here");
            return this.data;
        }
        if(date.before(data.date)){
            if(left != null){
                return left.searchDate(date);
            }
        }
        else{
            if(right != null){
                return right.searchDate(date);
            }
        }
        return null;
    }
    public Flight  searchFromDate(String city , GregorianCalendar date){
        if((city.equals(data.From) && date.equals(data.date)) ||( city.equals(data.From) && (date.get(Calendar.YEAR) == data.date.get(Calendar.YEAR)) && ( date.get(Calendar.MONTH) == data.date.get(Calendar.MONTH) )
                && date.get(Calendar.DAY_OF_MONTH) == data.date.get(Calendar.DAY_OF_MONTH) &&
                ( date.get(Calendar.HOUR_OF_DAY) == 0))){
            return this.data;
        }
        if(date.before(data.date)){
            if(left != null){
                return left.searchFromDate(city, date);
            }
        }
        else{
            if(right != null){
                return right.searchFromDate(city, date);
            }
        }
        return null;
    }
    public Flight searchPriceDate(GregorianCalendar date , double price){

        if( (price > data.price && date.equals(data.date))|| ((price > data.price)&&(date.get(Calendar.YEAR) == data.date.get(Calendar.YEAR)) && ( date.get(Calendar.MONTH) == data.date.get(Calendar.MONTH) )
                && date.get(Calendar.DAY_OF_MONTH) == data.date.get(Calendar.DAY_OF_MONTH) && (date.get(Calendar.HOUR_OF_DAY) == 0))){
            return this.data;
        }
        if(date.before(data.date)){
            if(left != null){
                return left.searchPriceDate(date,price);
            }
        }
        else if(date.after(data.date)){
            if(right != null){
                return right.searchPriceDate(date, price);
            }
        }
        return null;

    }
    public Flight floor(TreeNode root, GregorianCalendar key) {
        if (root == null) {
            return null;
        }
        if (root.data.date.equals(key)) {
            return root.data;
        }
        if (root.data.date.after(key) ){
            return floor(root.left, key);
        }
        Flight floorValue = floor(root.right, key);

        if (floorValue != null) {
            return floorValue;
        } else {
            return root.data;
        }

    }
    public Flight Ceil(TreeNode root, GregorianCalendar key) {

 
        if (root == null) {
            return null;
        }
        if (root.data.date.equals(key)) {
            return root.data;
        }
        if (root.data.date.before(key)) {
            return Ceil(root.right, key);
        }
        Flight ceiledValue = Ceil(root.left, key);

        if (ceiledValue != null) {
            return ceiledValue;
        } else {
            return root.data;
        }
    }

    @Override
    public String toString() {
        return ("Node(k= " + key + ", name= " + data.toString() + ")");
    }

}

