package com.sebastien;

class GameObject
{
    String m_szName;

    public String GetName() { return m_szName; }

    public void Update() {} // for virtual function

    public void Init() {}

    public GameObject() { Globals.GetObjectManager().Add(this); }
}
