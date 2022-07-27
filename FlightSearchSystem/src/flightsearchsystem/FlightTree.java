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
import static java.lang.Math.max;

public class FlightTree {
//avl 
    TreeNode root;

    public FlightTree(){
        root = null;
    }

    public int height(TreeNode d) {
        if (d == null) {
            return 0;
        } else {
            return d.height;
        }
    }
    int getBalance(TreeNode focus) {
        if (focus == null) {
            return 0;
        }
        return height(focus.left) - height(focus.right);
    }

    public TreeNode rotateMyLeft(TreeNode focus) {
        TreeNode k = focus.left;
        focus.left = k.right;
        k.right = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }
    public TreeNode rotateMyRight(TreeNode focus) {
        TreeNode k = focus.right;
        focus.right = k.left;
        k.left = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }
    public TreeNode doubleRotateLeftSide(TreeNode focus) { //LR
        focus.left = rotateMyRight(focus.left);
        return rotateMyLeft(focus);
    }

    public TreeNode doubleRotateRightSide(TreeNode focus) { //RL
        focus.right = rotateMyLeft(focus.right);
        return rotateMyRight(focus);
    }
    public void traverseLevelOrder(TreeNode focus) {
        if (focus == null) {
            focus = root; 
        }
        java.util.LinkedList<TreeNode> list = new java.util.LinkedList<TreeNode>();
        list.add(focus);
        while (!list.isEmpty()) {
            TreeNode d = list.removeFirst();
            System.out.println("");
            if (d.left != null) {
                list.addLast(d.left);
            }
            if (d.right != null) {
                list.addLast(d.right);
            }
            System.out.print(d.data+" ");
        }
    }
    public Flight min(){
       if(root != null){
           return root.min();
       }
       else
           return null;
    }
    private TreeNode delete(TreeNode focus , GregorianCalendar key){
        if (focus == null)
            return root;
        if (key.before(focus.key))
            focus.left = delete(focus.left, key);
        else if (key.after(focus.key))
            focus.right = delete(focus.right, key);
        else{
            if(focus.left == null){
                return focus.right;
            }
            if(focus.right == null){
                return focus.left;
            }
            focus.data = focus.right.min();
            focus.right = delete(focus.right,focus.data.date);

            focus.height = max(height(focus.left), height(focus.right)) + 1;
            int balanceFactor = getBalance(focus);
            if (balanceFactor > 1) {
                if (getBalance(focus.left) >= 0) {
                    return rotateMyLeft(focus);
                } else {

                    return doubleRotateLeftSide(focus);
                }
            }
            if (balanceFactor < -1) {
                if (getBalance(focus.right) <= 0) {
                    return rotateMyRight(focus);
                } else {

                    return doubleRotateRightSide(focus);
                }
            }
        }
        return focus;
    }
    public void delete(GregorianCalendar key){
        root = delete(root,key);
    }

    private TreeNode insert(TreeNode node, GregorianCalendar key, Flight data) {

        if (node == null)
            return (new TreeNode(data,key));
        if (key.before(node.data.date))
            node.left = insert(node.left, key, data);
        else if (key.after(node.data.date))
            node.right = insert(node.right, key, data);
        else // Duplicate keys not allowed
            return node; 

        node.height = 1 + max(height(node.left),
                height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key.before(node.left.key))
            return rotateMyLeft(node); //R


        if (balance < -1 && key.after(node.right.key))
            return rotateMyRight(node); //L

        if (balance > 1 && key.after(node.left.key)) {
            node.left = rotateMyRight(node.left); //LR
            return rotateMyLeft(node);
        }

        if (balance < -1 && key.before(node.right.key)) {
            node.right = rotateMyLeft(node.right); //RL
            return rotateMyRight(node);
        }
        return node;
    }
    public void insert(GregorianCalendar date , Flight newFlight){
        root = insert(root,date,newFlight);
    }
    public Flight searchDate(GregorianCalendar value){
        if(root != null){
            return root.searchDate(value);
        }
        return null;
    }
    public Flight searchFromDate(String city , GregorianCalendar date){
        if(root != null){
            return root.searchFromDate(city, date);
        }
        return null;
    }
    public Flight searchPriceDate(GregorianCalendar date , double price){
        return root.searchPriceDate(date, price);
    }
    public Flight floor(GregorianCalendar date){
        return root.floor(root, date);
    }
    public Flight ceil(GregorianCalendar date){
        return root.Ceil(root, date);
    }
    public void search2Dates(GregorianCalendar lo, GregorianCalendar hi){
        search2Dates(root, lo, hi);
    }
    private void search2Dates(TreeNode focus, GregorianCalendar date1, GregorianCalendar date2) {

        if (focus == null) {
            return;
        }
        if (date1.before(focus.data.date)) {
            search2Dates(focus.left, date1, date2);
        }
        if ((date1.before(focus.data.date) || date1.equals(focus.data.date)) && (focus.data.date.before(date2) || focus.data.date.equals(date2))  ) {
            System.out.println(focus.data+ " ");

        }
        if (date2.after(focus.data.date)) {
            search2Dates(focus.right, date1, date2);
        }

    }

}

