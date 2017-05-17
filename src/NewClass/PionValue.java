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

	
	//Get and Set
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}