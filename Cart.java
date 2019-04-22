package store;

public class Cart
{
	private int quantity;
	private Keyboard kb;
	public Cart(int quantity, Keyboard kb)
	{
		super ( );
		this.quantity = quantity;
		this.kb = kb;
	}
	public int getQuantity( )
	{
		return quantity;
	}
	public void setQuantity( int quantity )
	{
		this.quantity = quantity;
	}
	public Keyboard getKb( )
	{
		return kb;
	}
	public void setKb( Keyboard kb )
	{
		this.kb = kb;
	}
	@Override
	public String toString( )
	{
		return "Cart [quantity=" + quantity + ", kb=" + kb + "]";
	}
	public double getPrice()
	{
		return quantity * kb.getPrice ( );
	}
	
	
	
	
	
}
