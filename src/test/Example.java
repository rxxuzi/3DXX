package test;

import write.Error;

public class Example {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Error el = new Error();

        try{
            System.out.println(100/0);
        }catch (Exception e){
            Error.write(e);
        }

        Error.write("Test");
    }
}
