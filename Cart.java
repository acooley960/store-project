/* Author: Aaron Cooley & Marco Rojas
 * Date: 15 Apr. 2019
 * Class: CSC160-Combo
 * Assignment: Store Part 1
 * 
 * Test the item class by creating an object and printing the toString
 */
package store;

public class Cart
{
	private int quantity;
	private Keyboard kb;
	// private Mouse m;
	// any other items we sell will be added as extra fields
	
	// constructor for Keyboards only; other fields, when added, will be set to null
	// other constructors will be used for each of the other fields
	public Cart(int quantity, Keyboard kb)
	{
		super ( ); // ?
		this.quantity = quantity;
		this.kb = kb;
	}
	
	// gets the price of the item in the cart, minding quantity
	public double getPrice()
	{
		double price = 0;
		try
		{
			price = kb.getPrice ( );
		} 
		catch(NullPointerException e)
		{
			/* embedded try/catch statements for other store items
			   (like key caps or mice) would go in here	*/
		}
		
		return quantity * price;
	}
	// same logic as this class's getPrice, but for the brand
	public String getBrand()
	{
		String result = "";
		try
		{
			result = kb.getBrand ( );
		} 
		catch(NullPointerException e)
		{
			/* embedded try/catch statements for other store items
			   (like key caps or mice) would go in here	*/
		}
		return result;
	}

	public int getUPC( )
	{
		return this.kb.getUPC ( );
		
	}
	// returns the item of the cart, would be overloaded
	public Keyboard getItem()
	{
		return kb;
	}
	public void removeQuantity(int num)
	{
		if(num <= quantity)
		{
			quantity -= num;
		}
	}
	public void addQuantity(int num)
	{
		quantity+=num;
	}
	
	
 	public String toString( )
	{
		return String.format ("Cart [quantity=%s, kb=%s]", quantity, kb);
	}
	public Keyboard getKb( )
	{
		return kb;
	}
	public int getQuantity( )
	{
		return quantity;
	}
	public void setKb( Keyboard kb )
	{
		this.kb = kb;
	}
	public void setQuantity( int quantity )
	{
		this.quantity = quantity;
	}

	

}
