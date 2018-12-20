package com.sebastien;
import java.util.ArrayList;
import java.util.List;

class Orcish extends Race
{
	public Orcish()
	{
		m_szName = "Orc";
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mRage", 0.f));
		AdditionalAttributes.add(new Attribute(TYPE.BOOL, "mIsEnraged", false));
		m_Attributes = new Attributes(AdditionalAttributes);
	}
}

class Human extends Race
{
	public Human()
	{
		m_szName = "Human";
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mHumanity", 100.f));
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mMorale", 50.f));
		m_Attributes = new Attributes(AdditionalAttributes);
	}
}

class DarkElf extends Race
{
	public DarkElf()
	{
		m_szName = "Dark Elf";
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mLifeSteal", 0.2f));
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mFocus", 10.f));
		m_Attributes = new Attributes(AdditionalAttributes);
	}
}

class WoodElf extends Race
{
	public WoodElf()
	{
		m_szName = "Wood Elf";
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mCharisma", 25.f));
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mFocus", 10.f));
		m_Attributes = new Attributes(AdditionalAttributes);
	}
}

class Fairy extends Race
{
	public Fairy()
	{
		m_szName = "Fairy";
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mAgility", 25.f));
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mDodge", 0.2f));
		m_Attributes = new Attributes(AdditionalAttributes);
	}
}
