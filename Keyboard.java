/* Author: Aaron Cooley & Marco Rojas
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
	private String brand; // manufacturer of keyboard
	private String model; // model of keyboard
	private int stock; // how many we have in-store
	private double price; // how much each keyboard costs to purchase
	private String switchType; // the type of switch that the keyboard uses
	
	public Keyboard()
	{
		brand = "null";
		model = "null";
		stock = -1;
		price = -1;
		switchType = "null";
	}
	// constructor and toString
	public Keyboard(String b1, String m1, String st1, int s1, double p1)
	{
		brand = b1;
		model = m1;
		stock = s1;
		price = p1;
		switchType = st1;
	}
	public String toString()
	{
		return String.format("brand = %s\nmodel = %s\nswitchType = %s\nstock = %s\nprice = %.2f\n",
				    brand, model, switchType, stock, price);
	}
	// Marco : 19 APR 19
	public String toStringMenu()
	{
		String result = String.format("brand = %s\nmodel = %s\nswitchType = %s\nstock = %s\nprice = %.2f\n",
				    brand, model, switchType, stock, price);
		if (stock > 5)
		{
			result = String.format("brand = %s\nmodel = %s\nswitchType = %s\nIn stock\nprice = %.2f\n",
				    brand, model, switchType, price);
		}
		else if (stock == 0)
		{
			result = String.format("brand = %s\nmodel = %s\nswitchType = %s\nOut of stock!\nprice = %.2f\n",
				    brand, model, switchType, price);
		}
		return result;
	}
	
	public void removeStock(int num)
	{
		if(num <= stock)
		{
			stock -= num;
		}
	}
	public void addStock(int num)
	{
		stock += num;
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
	public String getSwitchType()
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
		stock += s2;
	}
	public void setSwitchType(String st2)
	{
		switchType = st2;
}
}
