package com.sebastien;

import org.json.simple.parser.ParseException;

import java.io.IOException;

class LocationInfo
{
    Location m_Location;
    String m_szAction;
    String m_szActionDescription;
    Encounter m_Encounter;

    public String GetAction() { return m_szAction; }
    public String GetDescription() { return m_szActionDescription; }
    public Encounter GetEncounter() { return m_Encounter; }
    public Location GetLocation() { return m_Location; }

    LocationInfo(Location location) throws IOException, ParseException {
        m_szAction = "Action";
        m_Location = location;
        m_Encounter = new Encounter(this);
    }
}

class Location extends GameObject
{
    Race m_Owner;
    LocationInfo m_Info;

    public LocationInfo GetInfo() { return m_Info; }
    public Race GetOwner() { return m_Owner; }

    public Location(String name) throws IOException, ParseException {
        m_Owner = Util.GetRandomRace();
        m_szName = name;
        m_Info = new LocationInfo(this);
    }
}
