package com.planet_ink.coffee_mud.Items.ClanItems;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import com.planet_ink.coffee_mud.Items.StdItem;
import java.util.*;


public class StdClanFlag extends StdItem implements ClanItem
{
	public String ID(){	return "StdClanFlag";}
	public Environmental newInstance(){ return new StdClanFlag();}
	protected String myClan="";
	protected int ciType=0;
	public int ciType(){return ciType;}
	public void setCIType(int type){ ciType=type;}	
	public StdClanFlag()
	{
		super();

		setName("a clan flag");
		baseEnvStats.setWeight(1);
		setDisplayText("an flag belonging to a clan is here.");
		setDescription("");
		secretIdentity="";
		baseGoldValue=1;
		setCIType(ClanItem.CI_FLAG);
		material=EnvResource.RESOURCE_COTTON;
		recoverEnvStats();
	}
	
	public String clanID(){return myClan;}
	public void setClanID(String ID){myClan=ID;}
	
	public void executeMsg(Environmental myHost, CMMsg msg)
	{
		if(StdClanItem.stdExecuteMsg(this,msg))
		{
			super.executeMsg(myHost,msg);
			if((msg.amITarget(this))
			&&(clanID().length()>0)
			&&(msg.source().getClanID().equals(clanID())))
			{
				if((msg.targetMinor()==CMMsg.TYP_DROP)&&(msg.trailerMsgs()==null))
					msg.addTrailerMsg(new FullMsg(msg.source(),this,CMMsg.MSG_EXAMINESOMETHING,null));
				else
				if(msg.targetMinor()==CMMsg.TYP_EXAMINESOMETHING)
				{
					Room R=msg.source().location();
					Area A=null;
					Behavior B=null;
					Vector V=null;
					if(R!=null)	A=R.getArea();
					if(A!=null) V=Sense.flaggedBehaviors(A,Behavior.FLAG_LEGALBEHAVIOR);
					if((V!=null)&&(V.size()>0)) B=(Behavior)V.firstElement();
					if(B!=null)
					{
						V.clear();
						V.addElement(new Integer(Law.MOD_WARINFO));
						if((B.modifyBehavior(A,msg.source(),V))
						&&(V.size()>0)
						&&(V.firstElement() instanceof String))
							msg.source().tell((String)V.firstElement());
						else
							msg.source().tell("This area is under the control of the Archons.");
					}
					else
						msg.source().tell("This area is under the control of the Archons.");
					return;
				}
			}
		}
	}
	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if((clanID().length()>0)&&(msg.amITarget(myHost)))
		{
			if(!msg.source().getClanID().equals(clanID()))
			{
				if(msg.targetMinor()==CMMsg.TYP_GET)
				{
					Room R=CoffeeUtensils.roomLocation(this);
					if(msg.source().getClanID().length()==0)
					{
						msg.source().tell("You must belong to a clan to take a clan item.");
						return false;
					}
					else
					if(R!=null)
					{
						for(int i=0;i<R.numInhabitants();i++)
						{
							MOB M=R.fetchInhabitant(i);
							if((M!=null)
							&&(M.isMonster())
							&&(M.getClanID().equals(clanID())
							&&(Sense.aliveAwakeMobile(M,true))
							&&(Sense.canBeSeenBy(this,M))
							&&(!Sense.isAnimalIntelligence(M))))
							{
								R.show(M,null,CMMsg.MSG_QUIETMOVEMENT,"<S-NAME> guard(s) "+name()+" closely.");
								return false;
							}
						}
					}
				}
				else
				if(msg.targetMinor()==CMMsg.TYP_DROP)
				{
					Room R=msg.source().location();
					LandTitle T=null;
					Area A=null;
					Behavior B=null;
					Vector V=null;
					if(R!=null)
					{
						A=R.getArea();
						for(int r=0;r<R.numEffects();r++)
							if(R.fetchEffect(r) instanceof LandTitle)
								T=(LandTitle)R.fetchEffect(r);
					}
					if((T==null)
					||((!T.landOwner().equals(clanID()))&&(!T.landOwner().equals(msg.source().Name()))))
					{
						if(A!=null) V=Sense.flaggedBehaviors(A,Behavior.FLAG_LEGALBEHAVIOR);
						if((V!=null)&&(V.size()>0)) B=(Behavior)V.firstElement();
						boolean ok=false;
						if(B!=null)
						{
							V.clear();
							V.addElement(new Integer(Law.MOD_RULINGCLAN));
							String rulingClan="";
							if((B.modifyBehavior(A,msg.source(),V))
							&&(V.size()>0)
							&&(V.firstElement() instanceof String))
								ok=true;
						}
						if(!ok)
						{
							msg.source().tell("You can not place a flag here, this place is controlled by the Archons.");
							return false;
						}
					}
				}
			}
			else
			if((msg.targetMinor()==CMMsg.TYP_GET)
			&&(msg.source().location()!=null)
			&&(msg.source().isMonster()))
			{
				boolean foundOne=false;
				for(int i=0;i<msg.source().location().numInhabitants();i++)
				{
					MOB M=msg.source().location().fetchInhabitant(i);
					if((M!=null)
					&&(!M.isMonster())
					&&(M.getClanID().equals(clanID())))
					{ foundOne=true; break;}
				}
				if(!foundOne)
				{
					msg.source().tell("You are guarding "+name()+" too closely.");
					return false;
				}
			}
		}
		
		if(StdClanItem.stdOkMessage(this,msg))
		{
			if((clanID().length()>0)
			&&(msg.amITarget(myHost))
			&&(msg.targetMinor()==CMMsg.TYP_DROP))
			{
				Room R=msg.source().location();
				Area A=null;
				Behavior B=null;
				Vector V=null;
				if(R!=null)	A=R.getArea();
				if(A!=null) V=Sense.flaggedBehaviors(A,Behavior.FLAG_LEGALBEHAVIOR);
				if((V!=null)&&(V.size()>0)) B=(Behavior)V.firstElement();
				if(B!=null)
				{
					V.clear();
					V.addElement(new Integer(Law.MOD_RULINGCLAN));
					String rulingClan="";
					if((B.modifyBehavior(A,msg.source(),V))
					&&(V.size()>0)
					&&(V.firstElement() instanceof String))
					{
						rulingClan=(String)V.firstElement();
						if(rulingClan.length()==0)
							msg.source().tell("This area is presently neutral.");
						else
						{
							msg.source().tell("This area is presently controlled by "+rulingClan+".");
							int relation=Clan.REL_WAR;
							Clan C=Clans.getClan(clanID());
							if(C!=null) 
								relation=C.getClanRelations(rulingClan);
							else
							{
								C=Clans.getClan(rulingClan);
								if(C!=null)
									relation=C.getClanRelations(clanID());
							}
							if(relation!=Clan.REL_WAR)
							{
								msg.source().tell("You must be at war with this clan to put down your flag on their area.");
								return false;
							}
						}
					}
					else
						msg.source().tell("This area is under the control of the Archons.");
				}
				else
					msg.source().tell("This area is under the control of the Archons.");
			}
			return super.okMessage(myHost,msg);
		}
		return false;
	}
	
	public boolean tick(Tickable ticking, int tickID)
	{
		if(!StdClanItem.standardTick(this,tickID))
			return false;
		return super.tick(ticking,tickID);
	}
}