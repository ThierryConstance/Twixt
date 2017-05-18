package NewClass;

public class PionValue {
	public int x;
	public int y;
	public int color;
	public int value;
	
	public PionValue(int x, int y,int color, int value)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.value= value;
	}
	
	public PionValue(int color)
	{
		this.color = color;
		this.value = 0;
	}
	
	public PionValue()
	{
		this.value = 0;
	}
}