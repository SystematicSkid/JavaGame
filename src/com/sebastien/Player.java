package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Player extends GameObject
{
	Attributes m_Attributes;
  	private Race m_Race;
  	private List<NPC> Party;
  	private Location m_CurrentLocation;
	private Inventory m_Inventory;

  	public Location GetLocation() {return m_CurrentLocation; }
  	public void SetLocation(Location location) {m_CurrentLocation = location;}
  	public Inventory GetInventory() { return m_Inventory; }

  	public Race GetRace()
	{
		return m_Race;
	}
	public Attributes GetStats() { return m_Attributes; }

	public Player(String name, Race race) throws IOException, ParseException {
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mSkillpoints", 5));
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mCharisma", 0));
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mStrength", 0));
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mMagic", 0));
		m_Attributes = new Attributes(AdditionalAttributes);
		this.m_szName = name; /* m_szName inside of GameObject */
		m_Inventory = new Inventory(this);
		m_Inventory.AddItem(json.GetRandomItem(this));
		this.m_Race = race;
	}

	public void HandleDamage(Damage source)
	{
		float armor, magicresist;
		float magicpenalty, damage;
		armor = this.GetStats().FindAttribute("mArmor").GetFloat();

		float physicalpenalty = source.m_fPenetration / armor;
		damage = (source.m_fFlat * physicalpenalty) + source.m_fForce;

		if(source.m_bIsMagic)
		{
			magicresist = this.GetStats().FindAttribute("mMagicResist").GetFloat();
			magicpenalty = source.m_fMagicPenetration / magicresist;
			damage = (source.m_MagicDamage * magicpenalty) + source.m_fForce;
		}
		float health = this.GetStats().FindAttribute("mHealth").GetFloat();
		this.GetStats().FindAttribute("mHealth").SetFloat(health - damage);
	}

	public void OnKill()
	{
		this.GetStats().FindAttribute("mLevel").SetInt(this.GetStats().FindAttribute("mLevel").GetInt() + 1);
	}

}