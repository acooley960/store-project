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
	public static Scanner input = new Scanner(System.in);
	public static void main( String[ ] args ) throws FileNotFoundException
	{
		// create and print toString of a keyboard
		Keyboard razer= new Keyboard ( "Razer", "Blackwidow Ultimate","Razer Mechanical Green", 6, 69.99 );
		System.out.println ( razer.toString ( ) );
		/*Keyboard keyboard1 = getKeyboard();
		System.out.println (  );
		Keyboard keyboard2 = getKeyboard();
		System.out.println (  );
		Keyboard keyboard3 = getKeyboard();*/
		// Aaron: 16 Apr
		Keyboard[] keyboards = inputFromFile();
		int index = findPlace(keyboards);
		//System.out.println ( "" + index );
		addMoreKeyboards ( keyboards, index );

		// Testing what's in the array, possibly turn this into a method later
		for (int j = 0; j < keyboards.length; j++)
		{
			try
			{
				System.out.printf ( "Index %s -\n%s\n", j, keyboards[j].toString ( ));
			} 
			catch (NullPointerException e)
			{// exits the loop after the keyboards have all been printed
				break;
			}
		}
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

	// Aaron : 18 APR 19
	// returns an array of Keyboard objects (current size 50)
	public static Keyboard[] inputFromFile() throws FileNotFoundException
	{
		Keyboard[] keyboards = new Keyboard[50];
		Scanner readFile = new Scanner(new File("keyboards.txt"));//may need to change the directory in order to work
		int i; // keep i for several parts without reseting value
		// loop to stick .txt file information into an array
		for (i = 0; readFile.hasNextLine ( ); i++)
		{
			String line = readFile.nextLine ( );
			Scanner scanLine = new Scanner(line);
			scanLine.useDelimiter ( "," );
			
			// because I know the layout of each line, I shouldn't need the .hasNext() boolean check (Maybe stick each of these into their own if blocks in the future)
			String brand = scanLine.next ( ).trim ( );
			String model = scanLine.next ( ).trim ( );
			String switchType = scanLine.next ( ).trim ( );
			String s = scanLine.next ( ).trim ( );// runtime error using .nextInt(); need to get rid of those tabs
			String p = scanLine.next ( ).trim ( );
			int stock = Integer.parseInt ( s );
			double price = Double.parseDouble ( p );
			// construct the keyboard into the array
			keyboards[i] = new Keyboard(brand, model, switchType, stock, price);
			scanLine.close ( );
		}
		readFile.close ( );
		return keyboards;
	}
	
	// Aaron : 18 APR 19
	// returns first index of nonexistent keyboard in array
	public static int findPlace(Keyboard[] kb)
	{
		/* 
		weird logic: set dummy variable to whichever keyboard stock value; 
		once keyboard doesn't exist, break out of loop and return that index value
		dummy has a purpose, even though the compiler doesn't think so
		I know no other way to do this 
		*/
		int i = 0;
		int dummy;
		for (; i < kb.length; i++)
		{
			try
			{
				dummy = kb[i].getStock ( );
			}
			catch (NullPointerException e)
			{
				return i;
			}
		}
		return -1;
	}

	// Aaron : 18 APR 19
	// adds user input into a Keyboard array
	public static void addMoreKeyboards(Keyboard[] kb, int index)
	{
		// couldn't figure out a good way to do this block as a do while loop, but it works
		System.out.println ( "Do you want to add another keyboard? Y/n" );
		int ans = input.next ( ).toLowerCase ( ).charAt ( 0 );
		for (; ans == 'y'; index++)
		{
			kb[index] = getKeyboard ( );
			
			System.out.println ( "Do you want to add another keyboard? Y/n" );
			ans = input.next ( ).toLowerCase ( ).charAt ( 0 );
		}
	}
	
	
}
/*
 * 17APR19MARCO: added getKeyboard method and added lines 21-26
 * 18APR19AARON: added way to read from file into an array, lines 76-102 inputFromFile method
 * 				added findPlace method to facilitate making new keyboards without deleting old ones, lines 105-129
 * 				added keyboard input into the same array for after file is read using addMoreKeyboards method: lines 121-135
 * 				added an output to see what is in Keyboard array, lines 32-43
 */
