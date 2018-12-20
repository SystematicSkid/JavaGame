package com.sebastien;

class Damage
{
    float m_fFlat;
    float m_fPenetration;
    float m_fForce;
    float m_fMagicPenetration;
    float m_MagicDamage;

    boolean m_bIsMagic         =    false;

    private String m_szOutput = "";

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
            m_fFlat *= 2;
            Player player = (Player) source.GetOwner();
            float chance = player.GetStats().FindAttribute("mLuck").GetFloat();
            if (crit <= chance) {
                m_fFlat = m_fFlat * 1.5f;
                m_szOutput = "Critical Strike!\n";
            }
            if(m_bIsMagic)
            {
                float mana = player.GetStats().FindAttribute("mMana").GetFloat();
                mana -= 5.f;
                player.GetStats().FindAttribute("mMana").SetFloat(mana);
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

    String GetOutput() { return m_szOutput; }

}
