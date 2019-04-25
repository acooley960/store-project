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
	public static List<Cart> cart = new LinkedList<>();//linked list for cart to keep track of items in cart.
	public static List<Cart> order = new LinkedList<>();//linked list for the managers ordering cart.
	public static Keyboard[] keyboards;
	public static Keyboard[] warehouse;
	public static int totalMissed = 0;
	public static Scanner input = new Scanner(System.in);
	public static void main( String[ ] args ) throws FileNotFoundException
	{
		
		// create and print toString of a keyboard
		/*Keyboard razer= new Keyboard ( "Razer", "Blackwidow Ultimate","Razer Mechanical Green", 6, 69.99 );
		System.out.println ( razer.toString ( ) );
		Keyboard keyboard1 = getKeyboard();
		System.out.println (  );
		Keyboard keyboard2 = getKeyboard();
		System.out.println (  );
		Keyboard keyboard3 = getKeyboard();*/
		// Aaron: 16 Apr
		//int index = findPlace();
		//System.out.println ( "" + index );
		//addMoreKeyboards ( index );
		keyboards = inputFromFile();
		warehouse = warehouseFile();
		makeSale();
		
		/* Notes:
		 *  - Need to have conditional statements for when we would sell something with no stock
		 *    possibly have a "we don't have that many" printout for a quantity that exceeds stock -- Done on 22APR19MARCO
		 *    
		 *  - Must return checkout price (Cart.getPrice()) -- Done on 22APR19MARCO
		 */
		
	}
	public static void makeSale()
	{
		showKeyboardMenu();
		char anotherKeyboard = 'y';
		int totalMissed = 0;
		double totalCost=0.0;
		int quantity=0;
		int cartIndex = -1; // instantly increments inside the do-while loop, functionally starts at 0
		do
		{
			
			cartIndex++;
			String password = "passWord4";
			System.out.printf("The manager's password is %s\n", password);
			System.out.print ( "What would you like to buy? Please type in the listing number: " );
			int item = -1;
			
			try
			{
				item = input.nextInt ( );
			}
			catch(InputMismatchException e)
			{
				String attempt = input.next();
				if (attempt.equals(password))
				{
					// call manager's report
					System.out.println("Howdy!");
					break;
				}
				else
				{
					continue;
				}
			}
			
			Keyboard kb = keyboards[item - 1];
			int maxBuy = kb.getStock ( );
			System.out.printf ( "Sweet! How many are you buying?\nWe have %s in stock.", maxBuy );
			quantity = input.nextInt ( );
			int missedBuy = 0;
			while ( quantity > maxBuy )/*NEED TO FIX: Currently it will replace 
												the quantity from the first keyboard chosen to the quantity 
												of the second keyboard chosen and so on.
												FIXED! line 68, quantity -> ( (LinkedList<Cart>) cart ).get(i).getQuantity()
												*/
			{
				missedBuy = 0;
				System.out.printf ( "We only have %s in stock! Please enter a new quantity.", maxBuy );
				quantity = input.nextInt ( );
				missedBuy = quantity - maxBuy;
			}
			cart.add ( new Cart(quantity, kb) );
			totalMissed += missedBuy;
			
			// reset available stock of item
			( (LinkedList<Cart>) cart ).get ( cartIndex ).getItem().removeStock ( quantity );
			totalCost += ( (LinkedList<Cart>) cart ).get(cartIndex).getPrice();
			for(int i = 0 ; i < cart.size( ) ; i++)
			{
				if (( (LinkedList<Cart>) cart ).get(i).getQuantity() == 1)
				{
					System.out.printf ( "\nYou chose %s %s's \nThe price for that keyboard is $%.2f",
							( (LinkedList<Cart>) cart ).get(i).getQuantity(),( (LinkedList<Cart>) cart ).get(i).getBrand() ,  ( (LinkedList<Cart>) cart ).get(i).getPrice() );
				}
				else
				{
					System.out.printf ( "\nYou chose %s %s's \nThe price for those keyboards is $%.2f",
							( (LinkedList<Cart>) cart ).get(i).getQuantity(),( (LinkedList<Cart>) cart ).get(i).getBrand() ,  ( (LinkedList<Cart>) cart ).get(i).getPrice() );
				}
			}
			System.out.println ( "\nDo you want another keyboard? y/n" );
			anotherKeyboard = input.next ( ).toLowerCase ( ).charAt ( 0 );
			//old code commented out 22APR19Marco
			/*
			 Cart[] itemsAtCheckout = new Cart[20];
			 itemsAtCheckout[0] = new Cart(1, keyboards[item-1]);
			 System.out.printf ( "You owe $%.2f.", itemsAtCheckout[0].getPrice ( ) );
			*/
		} while ( anotherKeyboard == 'y' );
		System.out.printf ( "The total was %.2f", totalCost );
	}
	public static void managersReport()
	{
		
	}
	
	// Marco : 17 APR 19
	// polls the (employee) user for the informations of a new keyboard being sold
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
	public static Keyboard[] warehouseFile() throws FileNotFoundException
	{
		Keyboard[] warehouse = new Keyboard[50];
		Scanner readFile = new Scanner(new File("warehouse.txt"));//may need to change the directory in order to work
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
			warehouse[i] = new Keyboard(brand, model, switchType, stock, price);
			scanLine.close ( );
		}
		readFile.close ( );
		return warehouse;
	}
	
	// Aaron : 18 APR 19
	// returns first index of nonexistent keyboard in array
	// to be used only between inputFromFile and addMoreKeyboards
	public static int findPlace()
	{
		/* 
		weird logic: set dummy variable to whichever keyboard stock value; 
		once keyboard doesn't exist, break out of loop and return that index value
		dummy has a purpose, even though the compiler doesn't think so
		I know no other way to do this 
		*/
		
		for (int i = 0; i < keyboards.length; i++)
		{
			try
			{
				keyboards[i].getStock ( );
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
	public static void addMoreKeyboards( int index )
	{
		// couldn't figure out a good way to do this block as a do while loop, but it works
		System.out.println ( "Do you want to add another keyboard? Y/n" );
		int ans = input.next ( ).toLowerCase ( ).charAt ( 0 );
		for (; ans == 'y'; index++)
		{
			keyboards[index] = getKeyboard ( );
			
			System.out.println ( "Do you want to add another keyboard? Y/n" );
			ans = input.next ( ).toLowerCase ( ).charAt ( 0 );
		}
	}
	
	// Marco : 19 APR 19
	// displays the menu
	public static void showKeyboardMenu()
	{
		// Testing what's in the array, possibly turn this into a method later
		for (int j = 0; j < keyboards.length; j++)
		{
			try
			{
				System.out.printf ( "Listing %s -\n%s\n", j+1, keyboards[j].toStringMenu ( ));
			} 
			catch (NullPointerException e)
			{// exits the loop after the keyboards have all been printed
				break;
			}
		}
	}
	public static void showWarehouse()
	{
		// Testing what's in the array, possibly turn this into a method later
		for (int j = 0; j < warehouse.length; j++)
		{
			try
			{
				System.out.printf ( "Listing %s -\n%s\n", j+1, warehouse[j].toStringMenu ( ));
			} 
			catch (NullPointerException e)
			{// exits the loop after the keyboards have all been printed
				break;
			}
		}
	}
	public static void orderStock()
	{
		
			showWarehouse();
			char anotherKeyboard = 'y';
			double totalCost=0.0;
			int quantity=0;
			int cartIndex = -1; // instantly increments inside the do-while loop, functionally starts at 0
			do
			{
				
				cartIndex++;
				String password = "passWord4";
				System.out.printf("The manager's password is %s\n", password);
				System.out.print ( "What would you like to buy? Please type in the listing number: " );
				int item = -1;
				
				try
				{
					item = input.nextInt ( );
				}
				catch(InputMismatchException e)
				{
					String attempt = input.next();
					if (attempt.equals(password))
					{
						// call manager's report
						System.out.println("Howdy!");
						break;
					}
					else
					{
						continue;
					}
				}
				
				Keyboard kb = warehouse[item - 1];
				int maxBuy = kb.getStock ( );
				System.out.printf ( "Sweet! How many are you buying?\nWe have %s in stock.", maxBuy );
				quantity = input.nextInt ( );
				while ( quantity > maxBuy )/*NEED TO FIX: Currently it will replace 
													the quantity from the first keyboard chosen to the quantity 
													of the second keyboard chosen and so on.
													FIXED! line 68, quantity -> ( (LinkedList<Cart>) cart ).get(i).getQuantity()
													*/
				{
					System.out.printf ( "We only have %s in stock! Please enter a new quantity.", maxBuy );
					quantity = input.nextInt ( );
				}
				order.add ( new Cart(quantity, kb) );
				
				// reset available stock of item
				( (LinkedList<Cart>) cart ).get ( cartIndex ).getItem().removeStock ( quantity );
				totalCost += ( (LinkedList<Cart>) cart ).get(cartIndex).getPrice();
				for(int i = 0 ; i < cart.size( ) ; i++)
				{
					if (( (LinkedList<Cart>) cart ).get(i).getQuantity() == 1)
					{
						System.out.printf ( "\nYou chose %s %s's \nThe price for that keyboard is $%.2f",
								( (LinkedList<Cart>) cart ).get(i).getQuantity(),( (LinkedList<Cart>) cart ).get(i).getBrand() ,  ( (LinkedList<Cart>) cart ).get(i).getPrice() );
					}
					else
					{
						System.out.printf ( "\nYou chose %s %s's \nThe price for those keyboards is $%.2f",
								( (LinkedList<Cart>) cart ).get(i).getQuantity(),( (LinkedList<Cart>) cart ).get(i).getBrand() ,  ( (LinkedList<Cart>) cart ).get(i).getPrice() );
					}
				}
				totalSold+=quantity;
				System.out.println ( "\nDo you want another keyboard? y/n" );
				anotherKeyboard = input.next ( ).toLowerCase ( ).charAt ( 0 );
				//old code commented out 22APR19Marco
				/*
				 Cart[] itemsAtCheckout = new Cart[20];
				 itemsAtCheckout[0] = new Cart(1, keyboards[item-1]);
				 System.out.printf ( "You owe $%.2f.", itemsAtCheckout[0].getPrice ( ) );
				*/
			} while ( anotherKeyboard == 'y' );
			
			System.out.printf ( "The total was %.2f", totalCost );
		}
	
	// Aaron : 21 APR 19
	//public static void cart(Keyboard kb, int quantity)
	
}
/*
 * 17APR19MARCO: added getKeyboard method and added lines 21-26
 * 18APR19AARON: added way to read from file into an array, lines 76-102 inputFromFile method
 * 				added findPlace method to facilitate making new keyboards without deleting old ones, lines 105-129
 * 				added keyboard input into the same array for after file is read using addMoreKeyboards method: lines 121-135
 * 				added an output to see what is in Keyboard array, lines 32-43
 * 19APR19MARCO: moved console output for testing Keyboard array into showMenu method
 * 22APR19MARCO: 
 */
