/* Author: Aaron Cooley
 * Date: 15 Apr. 2019
 * Class: CSC160-Combo
 * Assignment: Store Part 1
 * 
 * Class of keyboard items
 * Complete with constructor, toString, getters and setters!
 */


package store;

public class Keyboard
{
	private String brand;
	private String model;
	private int stock;
	private double price;
	private int switchType; // may change to String data type, or use 2D arrays
	
	// constructor and toString
	public Keyboard(String b1, String m1, int s1, double p1, int st1)
	{
		brand = b1;
		model = m1;
		stock = s1;
		price = p1;
		switchType = st1;
	}
	public String toString()
	{
		return String.format("brand = %s\nmodel = %s\nstock = %s\nprice = %.2f\nswitchType = %s\n",
				    brand, model, stock, price, switchType);
	}
	
	
	// getters and setters
	public String getBrand()
	{
		return brand;
	}
	public String getModel()
	{
		return model;
	}
	public double getPrice()
	{
		return price;
	}
	public int getStock()
	{
		return stock;
	}
	public int getSwitchType()
	{
		return switchType;
	}
	public void setBrand(String b2)
	{
		brand = b2;
	}
	public void setModel(String m2)
	{
		model = m2;
	}
	public void setPrice(double p2)
	{
		price = p2;
	}
	public void setStock(int s2)
	{
		stock = s2;
	}
	public void setSwitchType(int st2)
	{
		switchType = st2;
	}
}