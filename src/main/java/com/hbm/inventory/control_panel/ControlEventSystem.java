package com.hbm.inventory.control_panel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hbm.lib.RefStrings;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public class ControlEventSystem {

	private static Map<World, ControlEventSystem> systems = new HashMap<>();
	
	private Set<IControllable> allControllables = new HashSet<>();
	private Set<IControllable> tickables = new HashSet<>();
	private Map<String, Map<BlockPos, IControllable>> controllablesByEventName = new HashMap<>();
	private Map<BlockPos, Set<IControllable>> positionSubscriptions = new HashMap<>();
	
	public void addControllable(IControllable c){
		for(String s : c.getInEvents()){
			if(s.equals("tick")){
				tickables.add(c);
				continue;
			}
			if(!controllablesByEventName.containsKey(s)){
				controllablesByEventName.put(s, new HashMap<>());
			}
			controllablesByEventName.get(s).put(c.getPos(), c);
		}
		allControllables.add(c);
	}
	
	public void removeControllable(IControllable c){
		positionSubscriptions.remove(c.getPos());
		for(String s : c.getInEvents()){
			if(s.equals("tick")){
				tickables.remove(c);
				continue;
			}
			controllablesByEventName.get(s).remove(c);
		}
		allControllables.remove(c);
	}
	
	public boolean isValid(IControllable c){
		return allControllables.contains(c);
	}
	
	public void subscribeTo(IControllable subscriber, IControllable target){
		if(!positionSubscriptions.containsKey(target.getPos())){
			positionSubscriptions.put(target.getPos(), new HashSet<>());
		}
		positionSubscriptions.get(target.getPos()).add(subscriber);
	}
	
	public void unsubscribeFrom(IControllable subscriber, IControllable target){
		if(positionSubscriptions.containsKey(target.getPos())){
			positionSubscriptions.get(target.getPos()).remove(subscriber);
		}
	}
	
	public void broadcastEvent(BlockPos from, ControlEvent evt, BlockPos pos){
		Map<BlockPos, IControllable> map = controllablesByEventName.get(evt.name);
		if(map == null)
			return;
		IControllable c = map.get(pos);
		if(c != null)
			c.receiveEvent(from, evt);
	}
	
	public void broadcastEvent(BlockPos from, ControlEvent evt, Collection<BlockPos> positions){
		Map<BlockPos, IControllable> map = controllablesByEventName.get(evt.name);
		if(map == null)
			return;
		if(positions == null){
			for(IControllable c : map.values()){
				c.receiveEvent(from, evt);
			}
		} else {
			for(BlockPos pos : positions){
				IControllable c = map.get(pos);
				if(c != null){
					c.receiveEvent(from, evt);
				}
			}
		}
	}
	
	public void broadcastEvent(BlockPos from, ControlEvent c){
		broadcastEvent(from, c, (Collection<BlockPos>)null);
	}
	
	public void broadcastToSubscribed(IControllable ctrl, ControlEvent evt){
		Set<IControllable> subscribed = positionSubscriptions.get(ctrl);
		if(subscribed == null)
			return;
		for(IControllable sub : subscribed){
			sub.receiveEvent(ctrl.getPos(), evt);
		}
	}
	
	public static ControlEventSystem get(World w){
		if(!systems.containsKey(w))
			systems.put(w, new ControlEventSystem());
		return systems.get(w);
	}
	
	@SubscribeEvent
	public static void tick(WorldTickEvent evt){
		if(systems.containsKey(evt.world)){
			ControlEventSystem s = systems.get(evt.world);
			for(IControllable c : s.tickables){
				c.receiveEvent(c.getPos(), ControlEvent.newEvent("tick").setVar("time", evt.world.getWorldTime()));
			}
		}
	}
	
	@SubscribeEvent
	public static void worldUnload(WorldEvent.Unload evt){
		systems.remove(evt.getWorld());
	}
}
