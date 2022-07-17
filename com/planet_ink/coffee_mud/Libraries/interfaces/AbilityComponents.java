package com.planet_ink.coffee_mud.Libraries.interfaces;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.AbilityComponents.AbleTrigger;
import com.planet_ink.coffee_mud.Libraries.interfaces.AbilityComponents.AbleTriggerCode;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;
/*
   Copyright 2015-2022 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
 * Library for configureing and managing the rules for what resource
 * components (magic dust, tools, etc) are required every time a
 * particular skill is used, or what actions are required.  
 * Normally this would be part of the skill itself, but since 
 * this feature was added so late, it's separate.
 *
 *  Also here are common skill limit utilities, for determining
 *  how many common skills a player can learn.
 *
 * @author Bo Zimmerman
 */
public interface AbilityComponents extends CMLibrary
{
	/**
	 * Checks whether the given mob has the given components
	 * required to use a skill available to him/her, and if found,
	 * returns them as a FoundComponents list.
	 * @see AbilityComponents#getAbilityComponents(String)
	 * @param mob the mob whose inventory or room or both to check
	 * @param req the ability components rules definition
	 * @param mithrilOK true to allow mithril as a metal substitute
	 * @return null if missing components, or the list of found components
	 */
	public List<Object> componentCheck(MOB mob, List<AbilityComponent> req, boolean mithrilOK);

	/**
	 * Returns a very dirty approximate of a sample of what components appear
	 * to be required.  Named items are not required, but raw material components
	 * are created, and most rules are ignored in order to generate as many as
	 * possible.
	 * @see AbilityComponents#getAbilityComponents(String)
	 * @param req the ability components rules definition
	 * @param mithrilOK true to allow mithril as a metal substitute
	 * @return a list of sample items
	 */
	public List<Item> makeComponentsSample(List<AbilityComponent> req, boolean mithrilOK);

	/**
	 * Returns a dirty approximation of the minimal resources in the component
	 * requirement that match the given mob.  Named items are skipped.
	 *
	 * @param mob the mob trying to meet the requirements
	 * @param req the requirements to be met
	 * @return null if something goes wrong, or the minimal required resource Items
	 */
	public List<Item> makeComponents(final MOB mob, final List<AbilityComponent> req);

	/**
	 * If the ability component recipe used to build the list of found
	 * components needed to use a skill requires that any of the componenets
	 * are destroyed.
	 * @see AbilityComponents#componentCheck(MOB, List, boolean)
	 * @see MaterialLibrary.DeadResourceRecord
	 * @param found the components found with componentCheck
	 * @return the record of the components destroyed
	 */
	public MaterialLibrary.DeadResourceRecord destroyAbilityComponents(List<Object> found);

	/**
	 * Returns a friendly readable form of the component requirements
	 * of the given Ability/Skill ID(), or null if that ability has
	 * no requirements.  Since requirements may differ by player
	 * mask, the player mob is also required.
	 * @see AbilityComponents#getAbilityComponentDesc(MOB, List)
	 * @see AbilityComponents#getAbilityComponentDesc(MOB, AbilityComponent, boolean)
	 * @param mob the player mob who wants to know
	 * @param AID the Ability ID() of the skill whose components to check
	 * @return a friendly readable form of the component requirements
	 */
	public String getAbilityComponentDesc(MOB mob, String AID);

	/**
	 * Returns a friendly readable form of the component requirements
	 * of the given Ability/Skill Component List, or null if it has
	 * no requirements.  Since requirements may differ by player
	 * mask, the player mob is also required.
	 * @see AbilityComponents#getAbilityComponentDesc(MOB, AbilityComponent, boolean)
	 * @see AbilityComponents#getAbilityComponentDesc(MOB, String)
	 * @param mob the player mob who wants to know
	 * @param req the coded requirements list
	 * @return a friendly readable form of the component requirements
	 */
	public String getAbilityComponentDesc(MOB mob, List<AbilityComponent> req);

	/**
	 * Returns a friendly readable description of a specific component in
	 * the given decoded ability components definition list.  If the
	 * component does not refer to the given mob, "" is returned.
	 * @see AbilityComponents#getAbilityComponentDesc(MOB, List)
	 * @see AbilityComponents#getAbilityComponentDesc(MOB, String)
	 * @param mob the mob to check this components applicability to.
	 * @param comp the complete ability component decoded
	 * @param useConnector true to use a connector AND/OR, false otherwise
	 * @return a friendly readable description of a specific component
	 */
	public String getAbilityComponentDesc(MOB mob, AbilityComponent comp, boolean useConnector);

	/**
	 * Returns the master ability component map, keyed by the Ability ID.
	 * @return the master ability component map, keyed by the Ability ID.
	 */
	public Map<String, List<AbilityComponent>> getAbilityComponentMap();

	/**
	 * Adds a new coded ability component to the given component map.
	 * The component is coded as found in components.txt, with ID=parms.
	 * @param s the new coded ability component string
	 * @param H the map to add the new component to
	 * @return an error string, or null if everything went well.
	 */
	public String addAbilityComponent(String s, Map<String, List<AbilityComponent>> H);

	/**
	 * Gets the decoded ability component definition for a given Ability ID().
	 * This is then used by other methods to determine whether a user has the
	 * necessary components, or for manipulation of the definition.
	 * @param AID the Ability ID()
	 * @return the decoded ability component definition for a given Ability ID()
	 */
	public List<AbilityComponent> getAbilityComponents(String AID);

	/**
	 * Breaks an ability component decoded objects into a series of key/value pairs,
	 * where the first is always the connector, and the keys are as follows:
	 * ANDOR, DISPOSITION, FATE, AMOUNT, COMPONENTID, SUBTYPE, MASK. In that order.
	 * This is primarily for simplifying editors.
	 * @see AbilityComponents#setAbilityComponentCodedFromCodedPairs(PairList, AbilityComponent)
	 * @param comp the decoded ability component to produce fields from
	 * @return the key/value pairs of the ability component values.
	 */
	public PairList<String, String> getAbilityComponentCoded(AbilityComponent comp);

	/**
	 * Copies the key/value pairs from a PairList of specific abilitycomponent fields
	 * into the given AbilityComponent object.	 The first pairlist entry is always the
	 * connector, and the keys are as follows: ANDOR, DISPOSITION, FATE, AMOUNT,
	 * COMPONENTID, SUBTYPE, MASK. In that order.
	 * This is primarily for simplifying editors.
	 * @see AbilityComponents#getAbilityComponentCoded(AbilityComponent)
	 * @param decodedDV  the key/value pairs of the ability component values.
	 * @param comp the decoded ability component to copy field data into
	 */
	public void setAbilityComponentCodedFromCodedPairs(PairList<String, String> decodedDV, AbilityComponent comp);

	/**
	 * Reconstructs the coded ability component definition string (ID=parms)
	 * from the internal cached structures, given a particular Ability ID.
	 * @see AbilityComponents#getAbilityComponentCodedString(List)
	 * @param AID the Ability ID()
	 * @return the coded ability component definition string (ID=parms)
	 */
	public String getAbilityComponentCodedString(String AID);

	/**
	 * Reconstructs the coded ability component definition string (ID=parms)
	 * from the given cached decoded structures list.
	 * @see AbilityComponents#getAbilityComponentCodedString(String)
	 * @param comps the decoded ability components definition list
	 * @return the coded ability component definition string (ID=parms)
	 */
	public String getAbilityComponentCodedString(List<AbilityComponent> comps);

	/**
	 * Creates a new blank ability component object
	 * @return a new blank ability component object
	 */
	public AbilityComponent createBlankAbilityComponent();

	/**
	 * Alters and saved the ability components definition to on the
	 * filesystem (components.txt).
	 * @param compID the ID of the component being altered
	 * @param delete true to delete, false to add or modify
	 */
	public void alterAbilityComponentFile(String compID, boolean delete);

	/**
	 * Returns the character-class based common skill ability limits
	 * object applicable to the given mob, or zeroes if there's
	 * a problem.
	 * @see AbilityComponents#getSpecialSkillLimit(MOB, Ability)
	 * @see AbilityComponents#getSpecialSkillRemainder(MOB, Ability)
	 * @see AbilityComponents#getSpecialSkillRemainders(MOB)
	 * @see AbilityLimits
	 * @param studentM the mob to find limits for
	 * @return the character-class based common skill ability limits
	 */
	public AbilityLimits getSpecialSkillLimit(MOB studentM);

	/**
	 * Returns the character-class based common skill ability limits
	 * object applicable to the given mob and the given ability.
	 * @see AbilityComponents#getSpecialSkillLimit(MOB)
	 * @see AbilityComponents#getSpecialSkillRemainder(MOB, Ability)
	 * @see AbilityComponents#getSpecialSkillRemainders(MOB)
	 * @see AbilityLimits
	 * @see AbilityLimits#specificSkillLimit()
	 * @param studentM the mob to find limits for
	 * @param A the ability object to find limits for
	 * @return the character-class based common skill ability limits
	 */
	public AbilityLimits getSpecialSkillLimit(MOB studentM, Ability A);

	/**
	 * Returns the character-class based common skill ability limits
	 * object applicable to the given mob and the given ability, and
	 * then subtracts the number of each common skill already learned
	 * to derive a remaining number of each type.
	 * @see AbilityComponents#getSpecialSkillLimit(MOB, Ability)
	 * @see AbilityComponents#getSpecialSkillLimit(MOB)
	 * @see AbilityComponents#getSpecialSkillRemainders(MOB)
	 * @see AbilityLimits
	 * @see AbilityLimits#specificSkillLimit()
	 * @param studentM the mob to find limits for
	 * @param A the ability object to find limits for
	 * @return the character-class based common skill ability remainders
	 */
	public AbilityLimits getSpecialSkillRemainder(MOB studentM, Ability A);

	/**
	 * Returns the character-class based common skill ability limits
	 * object applicable to the given mob, and
	 * then subtracts the number of each common skill already learned
	 * to derive a remaining number of each type.
	 * @see AbilityComponents#getSpecialSkillLimit(MOB, Ability)
	 * @see AbilityComponents#getSpecialSkillLimit(MOB)
	 * @see AbilityComponents#getSpecialSkillRemainder(MOB, Ability)
	 * @see AbilityLimits
	 * @param studentM the mob to find limits for
	 * @return the character-class based common skill ability limits
	 */
	public AbilityLimits getSpecialSkillRemainders(MOB studentM);

	/**
	 * Given a ritual definition string, this will parse it into
	 * its constituent trigger steps, for more easy execution
	 * later.
	 *
	 * The format is: a trigger code, followed by one or
	 * more parms (space delimited), followed by amp or pipe
	 * and either an alternative step, or the next step.
	 *
	 * @see AbilityComponents.AbleTriggerCode
	 * @see AbilityComponents.AbleTriggerConnector
	 * @see AbilityComponents#genNextAbleTrigger(AbleTrigger[], AbleTriggState)
	 * @see AbilityComponents#getAbleTriggerDesc(AbleTrigger[])
	 * @see AbilityComponents#ableTriggCheck(CMMsg, AbleTriggState, AbleTrigger[])
	 *
	 * @param trigger the encoded ritual string
	 * @param errors null, or a list to put parsing errors into
	 * @return the list of parsed triggers, or null if something went wrong.
	 */
	public AbleTrigger[] parseAbleTriggers(String trigger, List<String> errors);

	/**
	 * The engine of the ritual system, this method checks if the message
	 * represents a step in the given ritual, and if so, sets one of the
	 * trigParts and/or trigTimes values.  If it might cause a recursive
	 * message loop, it will set the ignoreOf to the source of the message,
	 * and if it requires a wait, will add him to the waitingFor list.  The
	 * holyName is required for certain triggers with certain parameters.
	 * It returns true if the entire ritual has been completed.
	 *
	 * @see AbilityComponents.AbleTriggerCode
	 * @see AbilityComponents.AbleTriggerConnector
	 * @see AbilityComponents#parseAbleTriggers(String, List)
	 * @see AbilityComponents#genNextAbleTrigger(AbleTrigger[], AbleTriggState)
	 * @see AbilityComponents#getAbleTriggerDesc(AbleTrigger[])
	 *
	 * @param msg the event that occurred, and might be part of this ritual
	 * @param state the trigger state for a given player
	 * @param triggers the actual ritual itself
	 * @return true if the entire ritual is completed
	 */
	public boolean ableTriggCheck(final CMMsg msg,
								  final AbleTriggState state,
								  final AbleTrigger[] triggers);

	/**
	 * Generates an a message, if necessary, for the given mob, which is part
	 * of a ritual
	 * @param triggers the service trigger list
	 * @param trigState the global booleans showing which parts have been done
	 * @see AbilityComponents.AbleTriggerCode
	 * @see AbilityComponents.AbleTriggerConnector
	 * @see AbilityComponents#parseAbleTriggers(String, List)
	 * @see AbilityComponents#getAbleTriggerDesc(AbleTrigger[])
	 * @see AbilityComponents#ableTriggCheck(CMMsg, AbleTriggState, AbleTrigger[])
	 *
	 * @return null, or a message that needs doing
	 */
	public CMMsg genNextAbleTrigger(final AbleTrigger[] triggers,
									final AbleTriggState trigState);

	/**
	 * Returns a readable description of the given ritual
	 *
	 * @see AbilityComponents.AbleTriggerCode
	 * @see AbilityComponents.AbleTriggerConnector
	 * @see AbilityComponents#parseAbleTriggers(String, List)
	 * @see AbilityComponents#genNextAbleTrigger(AbleTrigger[], AbleTriggState)
	 * @see AbilityComponents#ableTriggCheck(CMMsg, AbleTriggState, AbleTrigger[])
	 *
	 * @param triggers the ritual
	 * @return the description
	 */
	public String getAbleTriggerDesc(final AbleTrigger[] triggers);

	/**
	 * The definition of the key words in the ritual definitions.
	 * Most of these require a parameter of one sort or another,
	 * depending on the code.  The command phrases
	 * are separated by &amp; (for and) or | for or.
	 * @author Bo Zimmerman
	 *
	 */
	public enum AbleTriggerCode
	{
		SAY,
		TIME,
		PUTTHING,
		BURNMATERIAL,
		BURNTHING,
		EAT,
		DRINK,
		INROOM,
		RIDING,
		CAST,
		EMOTE,
		PUTVALUE,
		PUTMATERIAL,
		BURNVALUE,
		SITTING,
		STANDING,
		SLEEPING,
		READING,
		RANDOM,
		CHECK,
		WAIT,
		YOUSAY,
		OTHERSAY,
		ALLSAY,
		SOCIAL,
		;
	}

	/**
	 * An interface representing a trigger step in
	 * a ritual, whether for deities or magic.
	 *
	 * @author Bo Zimmerman
	 *
	 */
	public interface AbleTrigger
	{
		/**
		 * The connector to an alternative trigger to this one
		 * @see AbilityComponents.AbleTriggerConnector
		 * @return the connector to another trigger, if it exists
		 */
		public AbleTrigger or();

		/**
		 * Returns the trigger code for
		 * @return the trigger code for
		 */
		public AbleTriggerCode code();

		/**
		 * Returns the CMMsg source minor code, or -999
		 * for this trigger.
		 * @return the CMMsg source minor code, or -999
		 */
		public int msgCode();

		/**
		 * Returns the parameters for the trigger code
		 * as a 2-dimensional array.  Entries may be null.
		 *
		 * @return the parameters for the trigger code
		 */
		public String[] parms();
	}

	/**
	 * A state for a particular user in the course of triggering
	 * a ritual, service, form magic, etc..
	 *
	 * @author Bo Zimmerman
	 *
	 */
	public interface AbleTriggState
	{
		/**
		 * Returns the actor of this trigger.  If null, the
		 * trigger should be deleted.
		 *
		 * @return the actor of this trigger, or null
		 */
		public MOB mob();

		/**
		 * Returns the current completed state for this ritual.
		 *
		 * @return -1, or the index into the trigger in 0
		 */
		public int completed();

		/**
		 * Sets the current completed state for this ritual.
		 */
		public void setCompleted();

		/**
		 * Returns a  when the last trigger was done,
		 * helping show when waiting triggers should complete.
		 *
		 * @return the time of last completion
		 */
		public long time();

		/**
		 * Sets whether actions by this user should be
		 * ignored for the purposes of triggers.
		 * This changes quickly and often.
		 *
		 * @param truefalse true to ignore, false to allow
		 */
		public void setIgnore(boolean truefalse);

		/**
		 * Some triggers use placeholders for the names
		 * of deities.  This is that name for this ritual.
		 *
		 * @return "", or the name of a deity
		 */
		public String getHolyName();

		/**
		 * Sets whether this user is waiting for
		 * something to happen.
		 *
		 * @param truefalse true if waiting, false otherwise
		 */
		public void setWait(boolean truefalse);
	}

	/**
	 * Ability Limits object, denoting how many of different types
	 * of common skills and langs that a player can learn, including an
	 * entry for a specific skill.
	 * @author Bo Zimmerman
	 */
	public static interface AbilityLimits
	{
		/**
		 * Returns number of common skills
		 * @return number of common skills
		 */
		public int commonSkills();

		/**
		 * Returns max number of common skills
		 * @return max number of common skills
		 */
		public int maxCommonSkills();

		/**
		 * Sets number of common skills
		 * @param newVal number of common skills
		 * @return this
		 */
		public AbilityLimits commonSkills(int newVal);

		/**
		 * Returns number of crafting skills
		 * @return number of crafting skills
		 */
		public int craftingSkills();

		/**
		 * Returns max number of crafting skills
		 * @return max number of crafting skills
		 */
		public int maxCraftingSkills();

		/**
		 * Sets number of crafting skills
		 * @param newVal number of crafting skills
		 * @return this
		 */
		public AbilityLimits craftingSkills(int newVal);

		/**
		 * Returns number of non-crafting skills
		 * @return number of non-crafting skills
		 */
		public int nonCraftingSkills();

		/**
		 * Returns max number of non-crafting skills
		 * @return max number of non-crafting skills
		 */
		public int maxNonCraftingSkills();

		/**
		 * Sets number of non-crafting skills
		 * @param newVal number of non-crafting skills
		 * @return this
		 */
		public AbilityLimits nonCraftingSkills(int newVal);

		/**
		 * Returns max number of language skills
		 * @return max number of language skills
		 */
		public int maxLanguageSkills();

		/**
		 * Sets number of language skills
		 * @param newVal number of language skills
		 * @return this
		 */
		public AbilityLimits languageSkills(int newVal);

		/**
		 * Returns number of language skills
		 * @return number of language skills
		 */
		public int languageSkills();

		/**
		 * Returns number of given specific ability type
		 * limit.
		 * @return i don't know how to say it
		 */
		public int specificSkillLimit();

		/**
		 * Sets number of given specific ability type
		 * limit.
		 * @param newVal a new number
		 * @return this
		 */
		public AbilityLimits specificSkillLimit(int newVal);
	}
}
