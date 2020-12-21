package com.planet_ink.coffee_mud.Items.CompTech;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.interfaces.BoundedObject.BoundedCube;
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
import com.planet_ink.coffee_mud.Items.interfaces.ShipDirComponent.ShipDir;
import com.planet_ink.coffee_mud.Libraries.interfaces.GenericBuilder;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/*
   Copyright 2020-2020 Bo Zimmerman

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
public class GenShipViewScreen extends GenElecCompSensor implements ShipDirComponent
{
	@Override
	public String ID()
	{
		return "GenShipViewScreen";
	}

	private ShipDir[]	allPossDirs		= ShipDir.values();
	private  int		numPermitDirs	= 1;

	public GenShipViewScreen()
	{
		super();
		setName("the view screen");
		setDisplayText("a large viewscreen is set into the hull");
		setDescription("");
		basePhyStats().setSensesMask(CMath.unsetb(basePhyStats().sensesMask(),PhyStats.SENSE_ITEMNOTGET));
		phyStats().setSensesMask(CMath.unsetb(phyStats().sensesMask(),PhyStats.SENSE_ITEMNOTGET));
	}

	@Override
	protected long getSensorMaxRange()
	{
		return SpaceObject.Distance.Parsec.dm;
	}

	@Override
	protected Converter<Environmental, Environmental> getSensedObjectConverter()
	{
		return new Converter<Environmental, Environmental>()
		{
			@Override
			public Environmental convert(final Environmental obj)
			{
				return new SpaceObject()
				{
					@Override
					public String ID()
					{
						return ""+obj;
					}

					@Override
					public String Name()
					{
						return obj.name();
					}

					@Override
					public void setName(final String newName)
					{
					}

					@Override
					public String displayText()
					{
						return obj.displayText();
					}

					@Override
					public void setDisplayText(final String newDisplayText)
					{
					}

					@Override
					public String description()
					{
						return obj.description();
					}

					@Override
					public void setDescription(final String newDescription)
					{
					}

					@Override
					public String image()
					{
						return obj.image();
					}

					@Override
					public String rawImage()
					{
						return obj.rawImage();
					}

					@Override
					public void setImage(final String newImage)
					{
					}

					@Override
					public boolean isGeneric()
					{
						return false;
					}

					@Override
					public void setMiscText(final String newMiscText)
					{
					}

					@Override
					public String text()
					{
						return "";
					}

					@Override
					public String miscTextFormat()
					{
						return null;
					}

					@Override
					public boolean sameAs(final Environmental E)
					{
						return E==this || E==obj;
					}

					@Override
					public long expirationDate()
					{
						return 0;
					}

					@Override
					public void setExpirationDate(final long dateTime)
					{
					}

					@Override
					public int maxRange()
					{
						return 0;
					}

					@Override
					public int minRange()
					{
						return 0;
					}

					@Override
					public String L(final String str, final String... xs)
					{
						return str;
					}

					@Override
					public String name()
					{
						return Name();
					}

					@Override
					public int getTickStatus()
					{
						return 0;
					}

					@Override
					public boolean tick(final Tickable ticking, final int tickID)
					{
						return false;
					}

					@Override
					public CMObject newInstance()
					{
						return obj.newInstance();
					}

					@Override
					public CMObject copyOf()
					{
						return obj.copyOf();
					}

					@Override
					public void initializeClass()
					{
					}

					@Override
					public int compareTo(final CMObject o)
					{
						return obj.compareTo(o);
					}

					@Override
					public void affectPhyStats(final Physical affected, final PhyStats affectableStats)
					{
					}

					@Override
					public void affectCharStats(final MOB affectedMob, final CharStats affectableStats)
					{
					}

					@Override
					public void affectCharState(final MOB affectedMob, final CharState affectableMaxState)
					{
					}

					@Override
					public void executeMsg(final Environmental myHost, final CMMsg msg)
					{
					}

					@Override
					public boolean okMessage(final Environmental myHost, final CMMsg msg)
					{
						return true;
					}

					@Override
					public void destroy()
					{
						// Nope!
					}

					@Override
					public boolean isSavable()
					{
						return false;
					}

					@Override
					public boolean amDestroyed()
					{
						return obj.amDestroyed();
					}

					@Override
					public void setSavable(final boolean truefalse)
					{
					}

					@Override
					public String[] getStatCodes()
					{
						return new String[0];
					}

					@Override
					public int getSaveStatIndex()
					{
						return 0;
					}

					@Override
					public String getStat(final String code)
					{
						return "";
					}

					@Override
					public boolean isStat(final String code)
					{
						return false;
					}

					@Override
					public void setStat(final String code, final String val)
					{
					}

					@Override
					public BoundedCube getBounds()
					{
						if(obj instanceof SpaceObject)
							return ((SpaceObject)obj).getBounds();
						return smallCube;
					}

					@Override
					public long[] coordinates()
					{
						final SpaceObject sobj =CMLib.map().getSpaceObject(obj, false);
						if(sobj!=null)
							return Arrays.copyOf(sobj.coordinates(), sobj.coordinates().length);
						return emptyCoords;
					}

					@Override
					public void setCoords(final long[] coords)
					{
					}

					@Override
					public long radius()
					{
						final SpaceObject sobj =CMLib.map().getSpaceObject(obj, false);
						if(sobj!=null)
							return sobj.radius();
						return 1;
					}

					@Override
					public void setRadius(final long radius)
					{
					}

					@Override
					public double[] direction()
					{
						final SpaceObject sobj =CMLib.map().getSpaceObject(obj, false);
						if(sobj!=null)
							return sobj.direction();
						return  emptyDirection;
					}

					@Override
					public void setDirection(final double[] dir)
					{
					}

					@Override
					public double speed()
					{
						return 0;
					}

					@Override
					public void setSpeed(final double v)
					{
					}

					@Override
					public SpaceObject knownTarget()
					{
						return null;
					}

					@Override
					public void setKnownTarget(final SpaceObject O)
					{
					}

					@Override
					public SpaceObject knownSource()
					{
						final SpaceObject sobj=CMLib.map().getSpaceObject(obj, false);
						if(sobj!=null)
							return sobj;
						return null;
					}

					@Override
					public void setKnownSource(final SpaceObject O)
					{
					}

					@Override
					public long getMass()
					{
						return 1;
					}
				};
			}
		};
	}

	protected Room getLookAtRoom()
	{
		Room R=null;
		final Area A=CMLib.map().areaLocation(this);
		if(A instanceof BoardableShip)
		{
			final BoardableShip shipO = (BoardableShip)A;
			final Room dockR = shipO.getIsDocked();
			if(dockR != null)
				R=dockR;
			else
			{
				final Item shipI=shipO.getShipItem();
				if(shipI != null)
					R=CMLib.map().roomLocation(shipI);
			}
		}
		else
			R=CMLib.map().roomLocation(this);
		return R;
	}

	protected boolean isInSpace()
	{
		final SpaceObject O=CMLib.map().getSpaceObject(this, true);
		if(O != null)//&&(this.powerRemaining() > this.powerNeeds()))
			return CMLib.map().isObjectInSpace(O);
		return false;
	}

	@Override
	public void setPermittedDirections(final ShipDir[] newPossDirs)
	{
		this.allPossDirs = newPossDirs;
	}

	@Override
	public ShipDir[] getPermittedDirections()
	{
		return allPossDirs;
	}

	@Override
	public void setPermittedNumDirections(final int numDirs)
	{
		this.numPermitDirs = numDirs;
	}

	@Override
	public int getPermittedNumDirections()
	{
		return numPermitDirs;
	}

	@Override
	protected List<? extends Environmental> getAllSensibleObjects()
	{
		if(isInSpace())
			return super.getAllSensibleObjects();
		Room R=null;
		final Area A=CMLib.map().areaLocation(this);
		if(A instanceof BoardableShip)
		{
			final BoardableShip shipO = (BoardableShip)A;
			final Room dockR = shipO.getIsDocked();
			if(dockR != null)
				R=dockR;
			else
			{
				final Item shipI=shipO.getShipItem();
				if(shipI != null)
					R=CMLib.map().roomLocation(shipI);
			}
		}
		else
			R=CMLib.map().roomLocation(this);
		if(R==null)
			return empty;
		final List<Environmental> found = new LinkedList<Environmental>();
		found.add(R);
		for(final Enumeration<MOB> m=R.inhabitants();m.hasMoreElements();)
		{
			final MOB M=m.nextElement();
			if(CMLib.flags().isSeeable(M))
				found.add(M);
		}
		for(final Enumeration<Item> i=R.items();i.hasMoreElements();)
		{
			final Item I=i.nextElement();
			if(CMLib.flags().isSeeable(I)
			&&(I.displayText().length()>0))
				found.add(I);
		}
		return found;
	}

	@Override
	protected Filterer<Environmental> getSensedObjectFilter()
	{
		final GenShipViewScreen meScreen = this;
		return new Filterer<Environmental>()
		{
			final GenShipViewScreen	me		= meScreen;
			final SpaceObject		spaceMe	= CMLib.map().getSpaceObject(me, true);

			@Override
			public boolean passesFilter(final Environmental obj)
			{
				if((!(spaceMe instanceof SpaceShip))||(me == obj)||(spaceMe == obj))
					return false;
				if(obj instanceof SpaceObject)
				{
					final SpaceShip ship=(SpaceShip)spaceMe;
					final SpaceObject sobj = (SpaceObject)obj;
					final double[] proposedDirection=CMLib.map().getDirection(ship, sobj);
					final ShipDir dir = CMLib.map().getDirectionFromDir(ship.facing(), ship.roll(), proposedDirection);
					if (CMParms.contains(me.getPermittedDirections(), dir))
					{
						final double distanceDm = CMLib.map().getDistanceFrom(spaceMe.coordinates(), sobj.coordinates());
						final double objSize = sobj.radius();
						final double viewSize = Math.atan(objSize/distanceDm);
						return viewSize >= 0.0015;
					}
					return false;
				}
				return true;
			}
		};
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		if((msg.target()==this)
		&&((msg.targetMinor()==CMMsg.TYP_LOOK)||(msg.targetMinor()==CMMsg.TYP_EXAMINE)))
		{
			if(!activated())
				setDescription(L("The screen is deactivated."));
			else
			{
				if(!isInSpace())
				{
					final Room R=getLookAtRoom();
					final CMMsg msg2=CMClass.getMsg(msg.source(), R, msg.tool(), msg.targetCode(), null);
					CMLib.commands().handleBeingLookedAt(msg2);
					return;
				}
				final Converter<Environmental, Environmental> converter = this.getSensedObjectConverter();
				final List<Environmental> finalList = new LinkedList<Environmental>();
				final List<? extends Environmental> found= getSensedObjects();
				for(final Environmental E : found)
				{
					final Environmental E2=converter.convert(E);
					if(E2!=null)
						finalList.add(E2);
				}
				if(finalList.size()==0)
					setDescription(L("You see the the blackness of space."));
				else
				{
					final StringBuilder desc=new StringBuilder(L("^WYou see: %0D"));
					desc.append(
						CMLib.lister().lister(msg.source(), finalList, true, null, null, msg.targetMinor()==CMMsg.TYP_EXAMINE, false)
					);
					setDescription(desc.toString());
				}
			}
		}
		super.executeMsg(myHost, msg);
	}

	private final static String[] MYCODES={"SDIRNUMPORTS","SDIRPORTS"};

	@Override
	public String getStat(final String code)
	{
		switch(getInternalCodeNum(code))
		{
		case 0:
			return "" + getPermittedNumDirections();
		case 1:
			return CMParms.toListString(getPermittedDirections());
		default:
			return super.getStat(code);
		}
	}

	@Override
	public void setStat(final String code, final String val)
	{
		switch(getInternalCodeNum(code))
		{
		case 0:
			setPermittedNumDirections(CMath.s_int(val));
			break;
		case 1:
			this.setPermittedDirections(CMParms.parseEnumList(ShipDirComponent.ShipDir.class, val, ',').toArray(new ShipDirComponent.ShipDir[0]));
			break;
		default:
			super.setStat(code, val);
			break;
		}
	}

	private int getInternalCodeNum(final String code)
	{
		for(int i=0;i<MYCODES.length;i++)
		{
			if(code.equalsIgnoreCase(MYCODES[i]))
				return i;
		}
		return -1;
	}

	private static String[] codes=null;

	@Override
	public String[] getStatCodes()
	{
		if(codes!=null)
			return codes;
		final String[] MYCODES=CMProps.getStatCodesList(GenShipViewScreen.MYCODES,this);
		final String[] superCodes=super.getStatCodes();
		codes=new String[superCodes.length+MYCODES.length];
		int i=0;
		for(;i<superCodes.length;i++)
			codes[i]=superCodes[i];
		for(int x=0;x<MYCODES.length;i++,x++)
			codes[i]=MYCODES[x];
		return codes;
	}

	@Override
	public boolean sameAs(final Environmental E)
	{
		if(!(E instanceof GenShipViewScreen))
			return false;
		final String[] theCodes=getStatCodes();
		for(int i=0;i<theCodes.length;i++)
		{
			if(!E.getStat(theCodes[i]).equals(getStat(theCodes[i])))
				return false;
		}
		return true;
	}
	
}
