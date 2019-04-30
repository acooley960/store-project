/* Author: Aaron Cooley & Marco Rojas
 * Date: 15 Apr. 2019
 * Class: CSC160-Combo
 * Assignment: Store Part 1
 * 
 * Test the item class by creating an object and printing the toString
 */
package store;

import java.util.*;
import java.io.*;

public class TestingStuff
{
	public static List<Cart> cart = new LinkedList<> ( );// linked list for cart to keep track of items in cart.
	public static List<Cart> order = new LinkedList<> ( );// linked list for the managers ordering cart.
	public static Keyboard[ ] keyboards;
	public static Keyboard[ ] warehouse;
	public static double groupNum[];
	public static String password = "passWord4";
	public static int totalMissed = 0;
	public static int totalSold = 0;
	public static char anotherSale;
	public static Scanner input = new Scanner ( System.in );
	// need ways to not let people grab from a keeb thats OOS
	// also need to add the number of keebs ordered to our inventory.

	public static void main( String[ ] args ) throws FileNotFoundException
	{

		// create and print toString of a keyboard
		/*
		 * Keyboard razer= new Keyboard ( "Razer", "Blackwidow Ultimate","Razer Mechanical Green", 6, 69.99 );
		 * System.out.println ( razer.toString ( ) ); Keyboard keyboard1 = getKeyboard(); System.out.println ( ); Keyboard
		 * keyboard2 = getKeyboard(); System.out.println ( ); Keyboard keyboard3 = getKeyboard(); Aaron: 16 Apr int index
		 * = findPlace(); System.out.println ( "" + index ); addMoreKeyboards ( index );
		 */
		keyboards = inputFromFile ( );
		warehouse = warehouseFile ( );
		char returnHome = 'y';
		while ( returnHome == 'y' )
		{
			welcomeScreen ( );
			while ( anotherSale == 'y' )
			{
				makeSale ( );
				System.out.println ( "\nDo you have another group/customer?\nY/N?" );
				anotherSale = input.next ( ).toLowerCase ( ).charAt ( 0 );
			}
			System.out.println ( "Return home?\nY/N?" );
			returnHome = input.next().toLowerCase().charAt(0);
		}
		/*
		 * Notes: - Need to have conditional statements for when we would sell something with no stock possibly have a
		 * "we don't have that many" printout for a quantity that exceeds stock -- Done on 22APR19MARCO
		 * 
		 * - Must return checkout price (Cart.getPrice()) -- Done on 22APR19MARCO
		 */

	}

	public static void makeSale( )
	{
		showKeyboardMenu ( );
		char anotherKeyboard = 'y';
		int quantity = 0;
		int cartIndex = 0; // instantly increments inside the do-while loop, functionally starts at 0
		System.out.println ( "How many people are in your group?" );
		int groupNum = input.nextInt ( );
		int n = 0;
		int missedBuy = 0;
		while ( n < groupNum )
		{
			do
			{

				if ( n == 0 )
				{
					System.out.print ( "\nWhat would you like to buy?" + " Please type in the listing number: " );
				}
				else if ( n > 0 )

				{
					showKeyboardMenu ( );
					System.out
							.print ( "\nWhat would the next person like to buy?" + " Please type in the listing number: " );

				}
				int item = input.nextInt ( );
				Keyboard kb = keyboards[item - 1];
				int maxBuy = kb.getStock ( );
				if ( maxBuy == 0 )
				{
					System.out.println ( "We currently don't have any in stock.\nPlease choose another item." );
					continue;
				}
				System.out.printf ( "Sweet! How many are you buying?\nWe have %s in stock.", maxBuy );
				quantity = input.nextInt ( );
				while ( quantity > maxBuy )
				{
					missedBuy = quantity - maxBuy;
					System.out.printf ( "We only have %s in stock! Please enter a new quantity.", maxBuy );
					quantity = input.nextInt ( );
					
				}
				cart.add ( new Cart ( quantity, kb ) );
				totalMissed += missedBuy;
				missedBuy = 0;
				// reset available stock of item
				( (LinkedList<Cart>) cart ).get ( cartIndex ).getItem ( ).removeStock ( quantity );
				showCart ( );
				totalSold += quantity;
				System.out.println ( "\nDo you want another keyboard? y/n" );
				anotherKeyboard = input.next ( ).toLowerCase ( ).charAt ( 0 );
				cartIndex++;

			} while ( anotherKeyboard == 'y' );
			System.out.println ( "Would you like to remove an item from your individual total?" );
			char removeItem = input.next ( ).toLowerCase ( ).charAt ( 0 );
			if ( removeItem == 'y' )
			{
				System.out.println ( "What item would you like to remove?" );
				int item = input.nextInt ( );
				removeFromCart ( item );
			}
			n++;
		}
		totalCost ( cart );

	}

	// 26APR2019MARCO
	public static void managersReport( )
	{
		makeSolidLine ( 50 );
		System.out.println ( "Total Missed Sales\t Total Completed Sales\n" );
		System.out.printf ( "\t%s\t\t\t\t%s\n", totalMissed, totalSold );
		makeSolidLine ( 50 );
		orderStock ( );
		// need to add way to check the stock of all keyboards in our store.
	}

	// Marco : 17 APR 19
	// polls the (employee) user for the informations of a new keyboard being sold
	public static Keyboard getKeyboard( )
	{

		System.out.println ( "What is the brand of keyboard?" );
		String brand = input.next ( );
		System.out.println ( "What is the model of keyboard?" );
		String model = input.next ( );
		System.out.println ( "What are the types of switches?" );
		String switchType = input.next ( );
		System.out.println ( "How many do you have in stock?" );
		int stock = input.nextInt ( );
		System.out.println ( "What is the price?" );
		double price = input.nextDouble ( );
		int upc = findPlace ( );
		Keyboard kb1 = new Keyboard ( brand, model, switchType, stock, price, upc );

		return kb1;

	}

	// Aaron : 18 APR 19
	// returns an array of Keyboard objects (current size 50)
	public static Keyboard[ ] inputFromFile( ) throws FileNotFoundException
	{
		Keyboard[ ] keyboards = new Keyboard[50];
		Scanner readFile = new Scanner ( new File ( "keyboards.txt" ) );// may need to change the directory in order to
																								// work
		int i; // keep i for several parts without reseting value
		// loop to stick .txt file information into an array
		for ( i = 0; readFile.hasNextLine ( ); i++ )
		{
			String line = readFile.nextLine ( );
			Scanner scanLine = new Scanner ( line );
			scanLine.useDelimiter ( "," );

			// because I know the layout of each line, I shouldn't need the .hasNext() boolean check (Maybe stick each of
			// these into their own if blocks in the future)
			String brand = scanLine.next ( ).trim ( );
			String model = scanLine.next ( ).trim ( );
			String switchType = scanLine.next ( ).trim ( );
			String s = scanLine.next ( ).trim ( );// runtime error using .nextInt(); need to get rid of those tabs
			String p = scanLine.next ( ).trim ( );
			String u = scanLine.next ( ).trim ( );
			int stock = Integer.parseInt ( s );
			double price = Double.parseDouble ( p );
			int upc = Integer.parseInt ( u );
			// construct the keyboard into the array
			keyboards[i] = new Keyboard ( brand, model, switchType, stock, price, upc );
			scanLine.close ( );
		}
		readFile.close ( );
		return keyboards;
	}

	public static Keyboard[ ] warehouseFile( ) throws FileNotFoundException
	{
		Keyboard[ ] warehouse = new Keyboard[50];
		Scanner readFile = new Scanner ( new File ( "warehouse.txt" ) );// may need to change the directory in order to
																								// work
		int i; // keep i for several parts without reseting value
		// loop to stick .txt file information into an array
		for ( i = 0; readFile.hasNextLine ( ); i++ )
		{
			String line = readFile.nextLine ( );
			Scanner scanLine = new Scanner ( line );
			scanLine.useDelimiter ( "," );

			// because I know the layout of each line, I shouldn't need the .hasNext() boolean check (Maybe stick each of
			// these into their own if blocks in the future)
			String brand = scanLine.next ( ).trim ( );
			String model = scanLine.next ( ).trim ( );
			String switchType = scanLine.next ( ).trim ( );
			String s = scanLine.next ( ).trim ( );// runtime error using .nextInt(); need to get rid of those tabs
			String p = scanLine.next ( ).trim ( );
			String u = scanLine.next ( ).trim ( );
			int stock = Integer.parseInt ( s );
			double price = Double.parseDouble ( p );
			int upc = Integer.parseInt ( u );
			// construct the keyboard into the array
			warehouse[i] = new Keyboard ( brand, model, switchType, stock, price, upc );
			scanLine.close ( );
		}
		readFile.close ( );
		return warehouse;
	}

	// Aaron : 18 APR 19
	// returns first index of nonexistent keyboard in array
	// to be used only between inputFromFile and addMoreKeyboards
	public static int findPlace( )
	{
		/*
		 * weird logic: set dummy variable to whichever keyboard stock value; once keyboard doesn't exist, break out of
		 * loop and return that index value dummy has a purpose, even though the compiler doesn't think so I know no other
		 * way to do this
		 */

		for ( int i = 0; i < keyboards.length; i++ )
		{
			try
			{
				keyboards[i].getStock ( );
			} catch ( NullPointerException e )
			{
				return i;
			}
		}
		return -1;
	}

	// Aaron : 18 APR 19
	// adds user input into a Keyboard array
	public static void addMoreKeyboards( int index )
	{
		// couldn't figure out a good way to do this block as a do while loop, but it works
		System.out.println ( "Do you want to add another keyboard? Y/n" );
		int ans = input.next ( ).toLowerCase ( ).charAt ( 0 );
		for ( ; ans == 'y'; index++ )
		{
			keyboards[index] = getKeyboard ( );

			System.out.println ( "Do you want to add another keyboard? Y/n" );
			ans = input.next ( ).toLowerCase ( ).charAt ( 0 );
		}
	}

	// Marco : 19 APR 19
	// displays the menu
	public static void showKeyboardMenu( )
	{
		// Testing what's in the array, possibly turn this into a method later
		for ( int j = 0; j < keyboards.length; j++ )
		{
			try
			{
				System.out.printf ( "Listing %s -\n%s\n", j + 1, keyboards[j].toStringMenu ( ) );
			} catch ( NullPointerException e )
			{// exits the loop after the keyboards have all been printed
				break;
			}
		}
	}

	public static void showWarehouse( )
	{
		// Testing what's in the array, possibly turn this into a method later
		for ( int j = 0; j < warehouse.length; j++ )
		{
			try
			{
				System.out.printf ( "Listing %s -\n%s\n", j + 1, warehouse[j].toStringWarehouse ( ) );
			} catch ( NullPointerException e )
			{// exits the loop after the keyboards have all been printed
				break;
			}
		}
	}

	public static void orderStock( )
	{// This method allows the manager to add more stock from the "warehouse" file. It reads in a modified version of the
		// text file using warehouseFile()

		showWarehouse ( );
		char anotherKeyboard = 'y';
		int quantity = 0;
		int cartIndex = -1; // instantly increments inside the do-while loop, functionally starts at 0
		do
		{

			cartIndex++;
			System.out.print ( "What would you like to buy? Please type in the listing number: " );
			int item = input.nextInt ( );
			while ( item > findPlace ( ) )
			{
				System.out.println ( "You chose an invalid number. Please try again." );
				item = input.nextInt ( );
			}
			Keyboard kb = warehouse[item - 1];
			int maxBuy = kb.getStock ( );
			System.out.printf ( "Sweet! How many are you buying?\nWe have %s in stock.", maxBuy );
			quantity = input.nextInt ( );
			if ( quantity > 0 )
			{
				while ( quantity > maxBuy )/*
													 * NEED TO FIX: Currently it will replace the quantity from the first keyboard
													 * chosen to the quantity of the second keyboard chosen and so on. FIXED! line
													 * 68, quantity -> ( (LinkedList<Cart>) cart ).get(i).getQuantity()
													 */
				{
					System.out.printf ( "We only have %s in stock! Please enter a new quantity.", maxBuy );
					quantity = input.nextInt ( );
				}
				order.add ( new Cart ( quantity, kb ) );
			}
			else
			{
				continue;
			}
			// reset available stock of item
			( (LinkedList<Cart>) order ).get ( cartIndex ).getItem ( ).removeStock ( quantity );
			for ( int i = 0; i < order.size ( ); i++ )
			{
				if ( ( (LinkedList<Cart>) order ).get ( i ).getQuantity ( ) == 1 )// if there's only one keyboard it will
																										// print this
				{
					System.out.printf ( "\nYou chose %s %s's \nThe price for that keyboard is $%.2f",
							( (LinkedList<Cart>) order ).get ( i ).getQuantity ( ),
							( (LinkedList<Cart>) order ).get ( i ).getBrand ( ),
							( (LinkedList<Cart>) order ).get ( i ).getPrice ( ) );
				}
				else // else it will print this
				{
					System.out.printf ( "\nYou chose %s %s's \nThe price for those keyboards is $%.2f",
							( (LinkedList<Cart>) order ).get ( i ).getQuantity ( ),
							( (LinkedList<Cart>) order ).get ( i ).getBrand ( ),
							( (LinkedList<Cart>) order ).get ( i ).getPrice ( ) );
				}
			}
			System.out.println ( "\nDo you have more keyboards to order? y/n" );
			anotherKeyboard = input.next ( ).toLowerCase ( ).charAt ( 0 );
		} while ( anotherKeyboard == 'y' );
		totalCost ( order );
	}

	public static void makeSolidLine( int stars )
	{
		for ( int num2 = 0; num2 < stars; num2++ )
		{
			System.out.print ( "*" );

		}
		System.out.println ( );
	}

	public static void welcomeScreen( )
	{
		System.out.println ( "Welcome to Aaron and Marco's Keyboard store!\nWould you like to make a sale?\nY/N?" );
		anotherSale = input.next ( ).toLowerCase ( ).charAt ( 0 );

		if ( anotherSale == 'n' )
		{
			System.out.println ( "Please enter the managers password(passWord4)" );
			String attempt = input.next ( );
			if ( attempt.equals ( password ) )
			{
				managersReport ( );
			}
			else
			{
				boolean correctAttempt = false;
				while ( correctAttempt == false )
				{
					System.out
							.println ( "You've entered the password incorrect.\nPlease re-enter the password.(passWord4)" );
					attempt = input.next ( );
					if ( attempt.equals ( password ) )
					{
						correctAttempt = true;
					}
					else
					{
						continue;
					}
				}
				managersReport ( );
			}
		}
	}

	public static void showCart( )
	{
		makeSolidLine ( 50 );
		for ( int i = 0; i < cart.size ( ); i++ )
		{
			if ( ( (LinkedList<Cart>) cart ).get ( i ).getQuantity ( ) == 1 )
			{// If there is only one keyboard
				System.out.printf ( "\n%s)\nYou chose %s %s \nThe price for that keyboard is $%.2f", i + 1,
						( (LinkedList<Cart>) cart ).get ( i ).getQuantity ( ),
						( (LinkedList<Cart>) cart ).get ( i ).getBrand ( ),
						( (LinkedList<Cart>) cart ).get ( i ).getPrice ( ) );
			}
			else
			{// If there are multiple keyboards
				System.out.printf ( "\n%s)\nYou chose %s %s's \nThe price for those keyboards is $%.2f", i + 1,
						( (LinkedList<Cart>) cart ).get ( i ).getQuantity ( ),
						( (LinkedList<Cart>) cart ).get ( i ).getBrand ( ),
						( (LinkedList<Cart>) cart ).get ( i ).getPrice ( ) );
			}
		}
		System.out.println ( );
		makeSolidLine ( 50 );
	}

	public static void removeFromCart( int num )
	{// this method allows the customer to remove items from their cart
		char anotherRemove = 'y';
		int removeNum = 1;
		do
		{
			int quantity = cart.get ( num - 1 ).getQuantity ( );
			if ( quantity > 1 )
			{
				System.out.println ( "How many would you like to remove?" );
				removeNum = input.nextInt ( );
				while ( removeNum > quantity )
				{
					System.out.println ( "You can't remove more than in your cart.\nPlease enter a valid number." );
					removeNum = input.nextInt ( );
				}
				totalSold-=removeNum;
				cart.get ( num - 1 ).removeQuantity ( removeNum );
			}
			int removalUPC = cart.get ( num - 1 ).getUPC ( );
			int addUPC = matchUPC ( removalUPC );
			keyboards[addUPC].addStock ( removeNum );
			showCart ( );
			System.out.println ( "Would you like to remove another item" );
			anotherRemove = input.next ( ).toLowerCase ( ).charAt ( 0 );

		} while ( anotherRemove == 'y' );
	}

	public static int matchUPC( int num )
	{// allows us to match the keyboard in cart to the keyboard in our store for tracking purposes.
		for ( int i = 0; i < keyboards.length; i++ )
		{
			try
			{
				if ( num == keyboards[i].getUPC ( ) )
				{
					return i;
				}
			} catch ( NullPointerException e )
			{
				break;
			}
		}
		return -1;
	}
	// Aaron : 21 APR 19
	// public static void cart(Keyboard kb, int quantity)

	public static void totalCost( List<Cart> list )
	{// prints out the total cost of the cart or order depending on if you are the manager or customer.
		double totalCost = 0;
		for ( Cart c : cart )
		{
			totalCost += c.getPrice ( );
		}
		System.out.printf ( "The total cost is %.2f", totalCost );
	}

}

/*
 * 17APR19MARCO: added getKeyboard method and added lines 21-26 18APR19AARON: added way to read from file into an array,
 * lines 76-102 inputFromFile method added findPlace method to facilitate making new keyboards without deleting old
 * ones, lines 105-129 added keyboard input into the same array for after file is read using addMoreKeyboards method:
 * lines 121-135 added an output to see what is in Keyboard array, lines 32-43 19APR19MARCO: moved console output for
 * testing Keyboard array into showMenu method 22APR19MARCO:
 */
