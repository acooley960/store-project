/* Author: Aaron Cooley
 * Date: 15 Apr. 2019
 * Class: CSC160-Combo
 * Assignment: Store Part 1
 * 
 * Test the item class by creating an object and printing the toString
 */

package store;
import java.util.Scanner;
import java.io.*;

public class TestingStuff
{
	public static Scanner input;
	public static void main( String[ ] args ) throws FileNotFoundException
	{
		input = new Scanner ( System.in );
		// create and print toString of a keyboard
		Keyboard razer= new Keyboard ( "Razer", "Blackwidow Ultimate","Razer Mechanical Green", 6, 69.99 );
		System.out.println ( razer.toString ( ) );
		Keyboard keyboard1 = getKeyboard();
		System.out.println (  );
		Keyboard keyboard2 = getKeyboard();
		System.out.println (  );
		Keyboard keyboard3 = getKeyboard();
		// Aaron: 16 Apr
		Scanner readFile = new Scanner(new File("src/store/keyboards.txt"));//may need to change the directory in order to work
		readFile.close ( );
	}
	// Marco : 17 APR 19
	public static Keyboard getKeyboard()
	{
		
		System.out.println ( "What is the brand of keyboard?" );
			String brand = input.next ( );
			System.out.println ( "What is the model of keyboard?" );
			String model = input.next ( );
			System.out.println ( "What are the types of switches?" );
			String switchType = input.next( );
			System.out.println ( "How many do you have in stock?" );
			int stock = input.nextInt( );
			System.out.println ( "What is the price?" );
			double price = input.nextDouble( );
			Keyboard kb1 = new Keyboard(brand, model, switchType, stock, price);

		return kb1;
		
	}

}
/*
 * 17APR19MARCO: added getKeyboard method and added lines 21-26
 */
