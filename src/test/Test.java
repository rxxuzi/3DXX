package test;

import shot.Json;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Json  json = new Json();
        boolean bol = true;

        ArrayList <A> list= new ArrayList<>();

        list.add(new A(1));
        list.add(new A(2));
        list.add(new A(3));
        list.add(new A(4));
        list.add(new A(5));


        json.write("{");
        for (int i = 0; i < list.size(); i++) {
            if(i != 0) {
                json.write(",");
            }
            json.write("Cube" , i , 1);
            json.write(list.get(i).data  , 2);
            if (i == list.size() - 1) {
                json.write("\n");
            }
        }
        json.write("}\n");

    }

}
class A{
    public String[] data = new String[7];
    boolean bol = true;
    public A(int n){
        data[0] = "\"x \" : " + (n+1) +", \"y \" : " + (n+1) +", \"z \" : " + (n+1) +",\n";
        data[1] = "\"dx \": " + (n*2) +", \"dy \": " + (n*2) +", \"dz \": " + (n*2) +",\n";
        data[2] = "\"rotation \" : " + 2+",\n";
        data[3] = "\"isDisplay \" : \"" + bol + "\""+",\n";
        data[4] = "\"move  \" : " + "\"" + bol + "\""+",\n";
        data[5] = "\"Polys \" : " + 6 +",\n";
        data[6] = "\"Color \" : [" + 255 + ", " + 255 + ", " + 255 + "]";
    }
}