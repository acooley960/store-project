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

	public static void main( String[ ] args ) throws FileNotFoundException
	{
		// create and print toString of a keyboard
		Keyboard keyboard1 = new Keyboard ( "Razer", "Blackwidow Ultimate", 6, 69.99, "Razer Mechanical" );
		System.out.println ( keyboard1.toString ( ) );
		
		
		// Aaron: 16 Apr
		Scanner readFile = new Scanner(new File("keyboards.txt"));
		
		
		readFile.close ( );
	}

}