package com.sebastien;
class Race
{
	String m_szName;
	Attributes m_Attributes;

	public String GetName()
	{
		return m_szName;
	}

	public Attributes GetAttributes()
	{
		return m_Attributes;
	}

	public static Race StringToRace(String str)
	{
		str = str.toLowerCase();
		switch (str)
		{
			case "human": return new Human();
			case "orc":
			case "orcish": return new Orcish();
			case "dark elf": return new DarkElf();
			case "wood elf": return new WoodElf();
		}
		return null;
	}
}