package com.sebastien;

import java.util.ArrayList;
import java.util.List;

enum TYPE
{
	INT,
	FLOAT,
	BOOL
}

class Attribute
{
	TYPE m_Type;
	String m_szName;
	int m_iValue;
	float m_fValue;
	boolean m_bValue;

	public TYPE GetType()
	{
		return m_Type;
	}

	public String GetName()
	{
		return m_szName;
	}

	public int GetInt()
	{
		return m_iValue;
	}

	public float GetFloat()
	{
		return m_fValue;
	}

	public boolean GetBool()
	{
		return m_bValue;
	}

	public void SetInt(int val) { m_iValue = val;}
	public void SetFloat(float val) {m_fValue = val;}
	public void SetBool(boolean val) { m_bValue = val; }

	public Attribute(TYPE type, String name, int value)
	{
		m_Type = type;
		m_szName = name;
		m_iValue = value;
	}

	public Attribute(TYPE type, String name, float value)
	{
		m_Type = type;
		m_szName = name;
		m_fValue = value;
	}

	public Attribute(TYPE type, String name, boolean value)
	{
		m_Type = type;
		m_szName = name;
		m_bValue = value;
	}

}

class Attributes
{
	List<Attribute> BaseAttributes = new ArrayList<>();
	List<Attribute> AdditionalAttributes = new ArrayList<Attribute>();

	void BuildDefaultAttributes()
	{
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mHealth", 100.f));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mMaxHealth", 100.f));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mSpeed", 10.f));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mArmor", 10.f));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mMagicResist", 5.f));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mAttackSpeed", 1.f));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mLuck", 5.f));
		BaseAttributes.add(new Attribute(TYPE.INT,   "mLevel", 1));
		BaseAttributes.add(new Attribute(TYPE.FLOAT, "mMana", 25.f));
	}

	public Attribute FindAttribute(String name)
	{
		for(Attribute attribute : BaseAttributes) // try to find the var inside of the base attributes
		{
			if(attribute.GetName().equals(name))
				return attribute;
		}

		for(Attribute attribute : AdditionalAttributes) // try to find the var inside of the additional attributes
		{
			if(attribute.GetName().equals(name))
				return attribute;
		}

		return null;
	}

	public Attributes(List<Attribute> attributes)
	{
		BuildDefaultAttributes();
		AdditionalAttributes = attributes;
	}

	public Attributes()
	{
		BuildDefaultAttributes();
	}
}