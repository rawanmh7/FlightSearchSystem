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
import java.util.GregorianCalendar;
// store flights according to "from" city

public class CityTree {
    CityNode root2;
    
    
    public Flight min(){
        if(root2 != null){
            return root2.min();
        }
        return null;
    }
    public void insert(Flight newFlight){

         if(root2 != null){
             root2.insert(newFlight,newFlight.From, newFlight.date);
         }
         else{
             root2 = new CityNode(newFlight,newFlight.From);
         }
    }
    public void delete(Flight flight){
        root2 = delete(root2, flight);
    }
    // *
    private CityNode delete(CityNode node , Flight deletedFlight){
        if(node == null)
            return node;
        if(compareTo(deletedFlight.From,node.data.From) == -1 ||
                compareTo(deletedFlight.From,node.data.From) == 0){
            if(compareTo(deletedFlight.From,node.data.From) == -1) {
                node.left = (delete(node.left, deletedFlight));
            }
            else{
                if(deletedFlight.date.before(node.data.date)){
                    node.left = (delete(node.left, deletedFlight));
                }
            }
        }
        if(compareTo(deletedFlight.From,node.data.From) == 1 ||
                compareTo(deletedFlight.From,node.data.From) == 0){
            if(compareTo(deletedFlight.From,node.data.From) == 1) {
                node.right = (delete(node.right, deletedFlight));
            }
            else{
                if(deletedFlight.date.after(node.data.date)){
                    node.right = (delete(node.right, deletedFlight));
                }
            }
        }
        else{
           // node with 0 or 1 child(ren)
            if(node.left == null){
                return node.right;
            }
            else if(node.right == null){
                return node.left;
            }

            // node with 2 children
            node.data = (node.right.min());
            node.right = (delete(node.right,node.data));
        }
        return node;
    }
    public int compareTo(String x , String y) {

        if(y.charAt(0) < x.charAt(0))
            return -1;
        else if(y.charAt(0) > x.charAt(0))
            return 1;
        else
            return 0;
    }


    public void searchFromCity(String city){
        Flight flight = root2.searchFromCity(city);
        if(flight != null){
            System.out.println(flight.toString());
        }
        else
            System.out.println("No flights available");
    }
    public void traverseInOrder(){
        root2.traverseInOrder();
    }
    


}
class CityNode {
    Flight data;
    String key;
    CityNode left, right;


    public CityNode(Flight flight, String key){
        this.key = key;
        data = flight;
        left = right = null;
    }
    public void traverseInOrder(){
        if(left != null){
            left.traverseInOrder();
        }
        System.out.println(data);
        if(right!= null){
            right.traverseInOrder();
        }
    }
    public Flight min(){
        if(left == null){
            return data;
        }
        else{
            return left.min();
        }
    }
    public void insert(Flight flight, String key, GregorianCalendar d){
        if(data.equals(flight)){
            return  ;
        }
        if((compareTo(key,data.From) == -1 ||(compareTo(key,data.From) == 0) ) && !(data.date.equals(d)) ){
            if(compareTo(key,data.From) == -1){
                if (left == null) {
                     left = new CityNode(flight, key);
                } else {
                      left.insert(flight, key,d);
                }
            }
            else {
                if ((d.before(data.date))) {
                    if (left == null) {
                        left = new CityNode(flight, key);
                    } else {
                        left.insert(flight, key,d);
                    }
                }
            }
        }
        if(((compareTo(key,data.From) == 1 || compareTo(key,data.From) == 0 ) ) && !(data.date.equals(d)) ){
            if((compareTo(key,data.From) == 1)){
                if (right == null) {
                     right = new CityNode(flight, key);
                } else {
                     right.insert(flight, key,d);
                }
            }
            else {
                if ((d.after(data.date))) {
                    if (right == null) {
                        right = new CityNode(flight, key);
                    } else {
                       right.insert(flight, key,d);
                    }
                }
            }
        }
    }
    public Flight searchFromCity(String key){
        if(key.equals(data.From)){
            return this.data;
        }
        if(compareTo(key,data.From) == -1){
            if(left != null){
                return left.searchFromCity(key);
            }
        }
        if(compareTo(key,data.From) == 1){
            if(right != null){
                return right.searchFromCity(key);
            }
        }
        return null;
    }

    public int compareTo(String x , String y) {

        if(y.charAt(0) < x.charAt(0))
            return -1;
        else if(y.charAt(0) > x.charAt(0))
            return 1;
        else
            return 0;
    }


}
