package ai;

import java.util.*;

public class Factory
{

	Vector<String> vect_ai;

	public Factory()
	{
		vect_ai = new Vector<String>();

		/* List Classes and added them to the list */
		vect_ai.add("RandomAI");
		vect_ai.add("RandomConnectionAI");

		vect_ai.add("group_j.AI");
		
		
	}
	
	public AbstractAI getAI(String name)
	{
		try
		{
			name = "ai."+name;
			return (AbstractAI) Class.forName(name).newInstance();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(-1);
		}

		return null;
	}

	public Vector<String> list_AI()
	{
		return vect_ai;
	}
}
