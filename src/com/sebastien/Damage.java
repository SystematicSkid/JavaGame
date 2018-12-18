package com.sebastien;

public class Damage
{
    float m_fFlat              =    0.f;
    float m_fPenetration       =    0.f;
    float m_fForce             =    0.f;
    float m_fMagicPenetration  =    0.f;
    float m_MagicDamage        =    0.f;

    boolean m_bIsMagic         =    false;

    String m_szOutput = "";

    public Damage(Item source)
    {
        Attributes stats = source.GetStats();
        m_fFlat = stats.FindAttribute("mPhysicalDamage").GetFloat();
        m_fPenetration = stats.FindAttribute("mPenetration").GetFloat();
        m_fForce = stats.FindAttribute("mForce").GetFloat();
        m_fMagicPenetration = stats.FindAttribute("mMagicPenetration").GetFloat();
        m_MagicDamage = stats.FindAttribute("mMagicDamage").GetFloat();
        if(m_MagicDamage != 0.f)
            m_bIsMagic = true;
        float crit = (float)Util.RandInt(1, 100);
        GameObject owner = source.GetOwner();
        if(owner == Globals.GetLocalPlayer()) {
            Player player = (Player) source.GetOwner();
            float chance = player.GetStats().FindAttribute("mLuck").GetFloat();
            if (crit <= chance) {
                m_fFlat = m_fFlat * 1.5f;
                m_szOutput = "Critical Strike!\n";
            }
        }
        else
        {
            NPC npc = (NPC)source.GetOwner();
            float chance = npc.GetStats().FindAttribute("mLuck").GetFloat();
            if (crit <= chance) {
                m_fFlat = m_fFlat * 1.5f;
                m_szOutput = "Critical Strike!\n";
            }
        }
    }

    public String GetOutput() { return m_szOutput; }

}
