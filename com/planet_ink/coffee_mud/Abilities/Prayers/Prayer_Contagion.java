package com.planet_ink.coffee_mud.Abilities.Prayers;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Prayer_Contagion extends Prayer
{
	public Prayer_Contagion()
	{
		super();
		myID=this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.')+1);
		name="Contagion";
		displayText="(Contagion)";
		quality=Ability.MALICIOUS;
		baseEnvStats().setLevel(7);
		holyQuality=Prayer.HOLY_EVIL;

		recoverEnvStats();
	}

	public Environmental newInstance()
	{
		return new Prayer_Contagion();
	}

	public void unInvoke()
	{
		if(affected==null) return;
		MOB mob=(MOB)affected;
		mob.tell("The unholy contagion fades.");
		super.unInvoke();
	}
	
	public boolean tick(int tickID)
	{
		if(!super.tick(tickID))
			return false;
		
		if(affected==null) return false;
		if(!(affected instanceof MOB)) return false;
		MOB mob=(MOB)affected;
		if(mob.location().numInhabitants()==1)
			return true;
		Vector choices=new Vector();
		for(int a=0;a<mob.numAffects();a++)
		{
			Ability A=mob.fetchAffect(a);
			if((A!=null)
			   &&(A.canBeUninvoked())
			   &&(!A.ID().equals(ID()))
			   &&(A.quality()==Ability.MALICIOUS)
			   &&(((A.classificationCode()&Ability.ALL_CODES)==Ability.SPELL)
				  ||((A.classificationCode()&Ability.ALL_CODES)==Ability.PRAYER))
			   &&(!A.isAutoInvoked()))
				choices.addElement(A);
		}
		if(choices.size()==0) return true;
		MOB target=mob.location().fetchInhabitant(Dice.roll(1,mob.location().numInhabitants(),-1));
		Ability thisOne=(Ability)choices.elementAt(Dice.roll(1,choices.size(),-1));
		if((target==null)||(thisOne==null)||(target.fetchAffect(ID())!=null))
			return true;
		if(Dice.rollPercentage()>(target.charStats().getSave(CharStats.SAVE_DISEASE)))
		{
			((Ability)this.copyOf()).invoke(target,target,true);
			if(target.fetchAffect(ID())!=null)
				((Ability)thisOne.copyOf()).invoke(target,target,true);
		}
		return true;
	}

	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto)
	{
		MOB target=this.getTarget(mob,commands,givenTarget);
		if(target==null) return false;

		if(!super.invoke(mob,commands,givenTarget,auto))
			return false;

		boolean success=profficiencyCheck(0,auto);

		if(success)
		{
			// it worked, so build a copy of this ability,
			// and add it to the affects list of the
			// affected MOB.  Then tell everyone else
			// what happened.
			FullMsg msg=new FullMsg(mob,target,this,affectType|Affect.MASK_MALICIOUS,auto?"<T-NAME> become(s) contageous!":"<S-NAME> prays(s) at <T-NAMESELF> for unholy contagion.");
			if(mob.location().okAffect(msg))
			{
				mob.location().send(mob,msg);
				if(!msg.wasModified())
					success=maliciousAffect(mob,target,0,-1);
			}
		}
		else
			return maliciousFizzle(mob,target,"<S-NAME> pray(s) at <T-NAMESELF>, but nothing happens.");


		// return whether it worked
		return success;
	}
}
