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
		super();
		List<Attribute> AdditionalAttributes = new ArrayList<>();
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mSkillpoints", 5));
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mCharisma", 0));
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mStrength", 0));
		AdditionalAttributes.add(new Attribute(TYPE.INT, "mMagic", 0));
		AdditionalAttributes.add(new Attribute(TYPE.FLOAT, "mRegen", 2.5f));
		m_Attributes = new Attributes(AdditionalAttributes);
		this.m_szName = name; /* m_szName inside of GameObject */
		m_Inventory = new Inventory(this);
		m_Inventory.AddItem(json.GetRandomItem(this));
		this.m_Race = race;
		this.m_Attributes.FindAttribute("mArmor").SetFloat(20.f);
		this.m_Attributes.FindAttribute("mMagicResist").SetFloat(20.f);
		this.m_Attributes.FindAttribute("mMaxHealth").SetFloat(150.f);
		this.m_Attributes.FindAttribute("mHealth").SetFloat(150.f);
	}

	public void LevelUp()
	{
		int level = m_Attributes.FindAttribute("mLevel").GetInt();
		int skillpoints = m_Attributes.FindAttribute("mSkillpoints").GetInt();
		System.out.printf("\n%s has leveled up to level %d!\n", GetName(), level + 1);
		m_Attributes.FindAttribute("mLevel").SetInt(level + 1);
		m_Attributes.FindAttribute("mSkillpoints").SetInt(skillpoints + 1);
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

	void Kill() throws IOException {
		System.out.print("\nYou have died!\nYour journey ends here...\n");
		Util.Wait();
		System.exit(0);
	}

	public void Update() throws IOException {
		if(this.GetStats().FindAttribute("mHealth").GetFloat() <= 0) {
			Kill();
		}
		float health = this.GetStats().FindAttribute("mHealth").GetFloat();
		float regen = this.GetStats().FindAttribute("mRegen").GetFloat();
		this.GetStats().FindAttribute("mHealth").SetFloat(health + regen);
		health = this.GetStats().FindAttribute("mHealth").GetFloat();
		float maxhealth = this.GetStats().FindAttribute("mMaxHealth").GetFloat();
		if(health > maxhealth)
			this.GetStats().FindAttribute("mHealth").SetFloat(maxhealth);		// clamp
		float mana = this.GetStats().FindAttribute("mMana").GetFloat();
		float maxmana = this.GetStats().FindAttribute("mMaxMana").GetFloat();
		this.GetStats().FindAttribute("mMana").SetFloat(mana + regen);
		mana = this.GetStats().FindAttribute("mMana").GetFloat();
		if(mana > maxmana)
			this.GetStats().FindAttribute("mMana").SetFloat(maxmana);		// clamp
	}

}