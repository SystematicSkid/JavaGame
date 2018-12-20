package com.sebastien;

import java.io.IOException;

class GameObject
{
    String m_szName;

    public String GetName() { return m_szName; }

    public void Update() throws IOException {} // for virtual function

    public void Init() {}

    GameObject() { Globals.GetObjectManager().Add(this); }
}
