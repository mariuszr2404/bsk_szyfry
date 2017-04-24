package LSFR;

import java.util.Scanner;

public class GeneratorTest {
    
    public static void main(String args[]) {
    	
    	Scanner scaner = new Scanner(System.in);
    	System.out.println("Podaj seed: ");
    	String seed = scaner.nextLine();
    	System.out.println("Podaj wielomian: ");
    	String poly = scaner.nextLine();
    	System.out.println("Podaj ilosc itertacji");
    	int iteration = scaner.nextInt();
    	
    	if (!seed.matches("[0-9]+") || !poly.matches("[0-9]+")) {
			System.err.println("Podaj tylko cyfry");
			System.exit(0);
		}
    	
    	System.out.println("");

    	Generator g = new Generator(iteration, seed);
    	for(int i = 0; i < iteration; i++) {
    		System.out.println("Iteracja: "  + (i+1) + " wartosc: " + g.generuj(poly));
    		g.displaySeed();
        	//g.display();
    	}
	}
}
