package com.sebastien;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

enum ITEMTYPE
{
    UNKNOWN,
    AXE,
    BOW,
    DAGGER,
    FLAIL,
    MAGIC,
    SPEAR,
    SWORD,
    HAMMER
}

class Item extends GameObject
{
    private GameObject m_Owner;
    private Attributes m_Stats;
    private ITEMTYPE m_Type;

    private void BuildStandardItemAttributes()
    {
        List<Attribute> ItemAttributes = new ArrayList<>(); // setting the default val on these doesnt even matter because we will be overriding them anyways
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mPhysicalDamage", 0.f));
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mMagicDamage", 0.f));
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mPenetration", 0.f));
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mMagicPenetration", 0.f));
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mForce", 0.f));
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mDurability", 0.f));
        ItemAttributes.add(new Attribute(TYPE.FLOAT, "mSpeed", 0.f));
        m_Stats = new Attributes(ItemAttributes);
    }

    ITEMTYPE GetType(){return m_Type;}
    public Attributes GetStats() { return  m_Stats; }
    GameObject GetOwner() { return m_Owner; }

    void SetOwner(GameObject owner) { m_Owner = owner; }

    public Item(GameObject owner, String name, ITEMTYPE type, JSONObject jsonobject, JSONObject stats)
    {
        super();
        m_Owner = owner;
        m_Type = type;
        BuildStandardItemAttributes();
        boolean isMagic = stats.get("magicdamage") != null;
        if(!isMagic) {
            m_Stats.FindAttribute("mPhysicalDamage").SetFloat(Float.parseFloat((String) stats.get("physicaldamage")));
            m_Stats.FindAttribute("mPenetration").SetFloat(Float.parseFloat((String) stats.get("penetration")));
        }

        m_Stats.FindAttribute("mForce").SetFloat(Float.parseFloat((String)stats.get("force")));
        m_Stats.FindAttribute("mDurability").SetFloat(Float.parseFloat((String)stats.get("durability")));
        m_Stats.FindAttribute("mSpeed").SetFloat(Float.parseFloat((String)stats.get("speed")));
        if(isMagic)
        {
            m_Stats.FindAttribute("mMagicPenetration").SetFloat(Float.parseFloat((String)stats.get("magicpenetration")));
            m_Stats.FindAttribute("mMagicDamage").SetFloat(Float.parseFloat((String)stats.get("magicdamage")));
        }
        m_szName = (String)jsonobject.get("name");
    }
}

class Inventory
{
    private GameObject m_Owner;
    List<Item> m_Items = new ArrayList<>();

    List<Item> GetItems() {return m_Items;}

    public Item FindItem(String name)
    {
        for(Item item : m_Items) // try to find the var inside of the base attributes
        {
            if(item.GetName().equals(name))
                return item;
        }
        return null;
    }

    public void AddItem(Item item)
    {
        item.SetOwner(m_Owner);
        m_Items.add(item);
    }

    Inventory(GameObject owner)
    {
        m_Owner = owner;
    }
}
