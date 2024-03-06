package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;


/**
 * player moveto command
 * 
 * @author Exe
 */
public class Relics extends PlayerCommand {

	public Relics() {
		super("relics");
	}

	@Override
	public void execute(final Player player, String... params) {
		
		Storage bag = player.getInventory();
		
		
		long ApByRelics = 0;
		long RelicsAmount = 0;
		
		for(int i=0; i<3; i++) {
		
		//Seals -------------------------------------------------------------------------
		long LASeal = bag.getItemCountByItemId(186000062);
		if (LASeal >= 1) {
		Item LASealItem = bag.getFirstItemByItemId(186000062);
		LASeal = LASealItem.getItemCount();
		ApByRelics = ApByRelics + (LASealItem.getItemCount())*600;	
		bag.decreaseByObjectId(LASealItem.getObjectId(), LASeal);
	//	PacketSendUtility.sendMessage(player, LASeal + " Count for .62");
		RelicsAmount = RelicsAmount + LASeal;
	}		
		long ASeal = bag.getItemCountByItemId(186000061);
		if (ASeal >= 1) {
		Item ASealItem = bag.getFirstItemByItemId(186000061);
		ASeal = ASealItem.getItemCount();
		ApByRelics = ApByRelics + (ASealItem.getItemCount())*1200;	
		bag.decreaseByObjectId(ASealItem.getObjectId(), ASeal);
	//	PacketSendUtility.sendMessage(player, ASeal + " Count for .61");
		RelicsAmount = RelicsAmount + ASeal;
	}		
		long GASeal = bag.getItemCountByItemId(186000060);
		if (GASeal >= 1) {
		Item GASealItem = bag.getFirstItemByItemId(186000060);
		GASeal = GASealItem.getItemCount();
		ApByRelics = ApByRelics + (GASealItem.getItemCount())*1800;	
		bag.decreaseByObjectId(GASealItem.getObjectId(), GASeal);
	//	PacketSendUtility.sendMessage(player, GASeal + " Count for .60");
		RelicsAmount = RelicsAmount + GASeal;
	}	
		long MASeal = bag.getItemCountByItemId(186000059);
		if (MASeal >= 1) {
		Item MASealItem = bag.getFirstItemByItemId(186000059);
		MASeal = MASealItem.getItemCount();
		ApByRelics = ApByRelics + (MASealItem.getItemCount())*2400;	
		bag.decreaseByObjectId(MASealItem.getObjectId(), MASeal);
	//	PacketSendUtility.sendMessage(player, MASeal + " Count for .59");
		RelicsAmount = RelicsAmount + MASeal;
	}
		
		//Icons --------------------------------------------------------------------------
		long LAIcon = bag.getItemCountByItemId(186000066);
		if (LAIcon >= 1) {
		Item LAIconItem = bag.getFirstItemByItemId(186000066);
		LAIcon = LAIconItem.getItemCount();
		ApByRelics = ApByRelics + (LAIconItem.getItemCount())*300;	
		bag.decreaseByObjectId(LAIconItem.getObjectId(), LAIcon);
	//	PacketSendUtility.sendMessage(player, LAIcon + " Count for .66");
		RelicsAmount = RelicsAmount + LAIcon;
	}	
		long AIcon = bag.getItemCountByItemId(186000065);
		if (AIcon >= 1) {
		Item AIconItem = bag.getFirstItemByItemId(186000065);
		AIcon = AIconItem.getItemCount();
		ApByRelics = ApByRelics + (AIconItem.getItemCount())*600;	
		bag.decreaseByObjectId(AIconItem.getObjectId(), AIcon);
	//	PacketSendUtility.sendMessage(player, AIcon + " Count for .65");
		RelicsAmount = RelicsAmount + AIcon;
	}
		long GAIcon = bag.getItemCountByItemId(186000064);
		if (GAIcon >= 1) {
		Item GAIconItem = bag.getFirstItemByItemId(186000064);
		GAIcon = GAIconItem.getItemCount();
		ApByRelics = ApByRelics + (GAIconItem.getItemCount())*900;	
		bag.decreaseByObjectId(GAIconItem.getObjectId(), GAIcon);
	//	PacketSendUtility.sendMessage(player, GAIcon + " Count for .64");
		RelicsAmount = RelicsAmount + GAIcon;
	}
		long MAIcon = bag.getItemCountByItemId(186000063);
		if (MAIcon >= 1) {
		Item MAIconItem = bag.getFirstItemByItemId(186000063);
		MAIcon = MAIconItem.getItemCount();
		ApByRelics = ApByRelics + (MAIconItem.getItemCount())*1200;	
		bag.decreaseByObjectId(MAIconItem.getObjectId(), MAIcon);
	//	PacketSendUtility.sendMessage(player, MAIcon + " Count for .63");
		RelicsAmount = RelicsAmount + MAIcon;
	}
		
		//Goblets ------------------------------------------------------------------------
		long LAGoblet = bag.getItemCountByItemId(186000058);
		if (LAGoblet >= 1) {
		Item LAGobletItem = bag.getFirstItemByItemId(186000058);
		LAGoblet = LAGobletItem.getItemCount();
		ApByRelics = ApByRelics + (LAGobletItem.getItemCount())*1200;	
		bag.decreaseByObjectId(LAGobletItem.getObjectId(), LAGoblet);
	//	PacketSendUtility.sendMessage(player, LAGoblet + " Count for .58");
		RelicsAmount = RelicsAmount + LAGoblet;
	}
		long AGoblet = bag.getItemCountByItemId(186000057);
		if (AGoblet >= 1) {
		Item AGobletItem = bag.getFirstItemByItemId(186000057);
		AGoblet = AGobletItem.getItemCount();
		ApByRelics = ApByRelics + (AGobletItem.getItemCount())*2400;	
		bag.decreaseByObjectId(AGobletItem.getObjectId(), AGoblet);
	//	PacketSendUtility.sendMessage(player, AGoblet + " Count for .57");
		RelicsAmount = RelicsAmount + AGoblet;
	}
		long GAGoblet = bag.getItemCountByItemId(186000056);
		if (GAGoblet >= 1) {
		Item GAGobletItem = bag.getFirstItemByItemId(186000056);
		GAGoblet = GAGobletItem.getItemCount();
		ApByRelics = ApByRelics + (GAGobletItem.getItemCount())*3600;	
		bag.decreaseByObjectId(GAGobletItem.getObjectId(), GAGoblet);
	//	PacketSendUtility.sendMessage(player, GAGoblet + " Count for .56");
		RelicsAmount = RelicsAmount + GAGoblet;
	}
		long MAGoblet = bag.getItemCountByItemId(186000055);
		if (MAGoblet >= 1) {
		Item MAGobletItem = bag.getFirstItemByItemId(186000055);
		MAGoblet = MAGobletItem.getItemCount();
		ApByRelics = ApByRelics + (MAGobletItem.getItemCount())*4800;	
		bag.decreaseByObjectId(MAGobletItem.getObjectId(), MAGoblet);
	//	PacketSendUtility.sendMessage(player, MAGoblet + " Count for .55");
		RelicsAmount = RelicsAmount + MAGoblet;
	}			
		//Crowns --------------------------------------------------------------------------
		long LACrown = bag.getItemCountByItemId(186000054);
		if (LACrown >= 1) {
		Item LACrownItem = bag.getFirstItemByItemId(186000054);
		LACrown = LACrownItem.getItemCount();
		ApByRelics = ApByRelics + (LACrownItem.getItemCount())*2400;	
		bag.decreaseByObjectId(LACrownItem.getObjectId(), LACrown);
	//	PacketSendUtility.sendMessage(player, LACrown + " Count for .54");
		RelicsAmount = RelicsAmount + LACrown;
	}
		long ACrown = bag.getItemCountByItemId(186000053);
		if (ACrown >= 1) {
		Item ACrownItem = bag.getFirstItemByItemId(186000053);
		ACrown = ACrownItem.getItemCount();
		ApByRelics = ApByRelics + (ACrownItem.getItemCount())*4800;	
		bag.decreaseByObjectId(ACrownItem.getObjectId(), ACrown);
	//	PacketSendUtility.sendMessage(player, ACrown + " Count for .53");
		RelicsAmount = RelicsAmount + ACrown;
	}
		long GACrown = bag.getItemCountByItemId(186000052);
		if (GACrown >= 1) {
		Item GACrownItem = bag.getFirstItemByItemId(186000052);
		GACrown = GACrownItem.getItemCount();
		ApByRelics = ApByRelics + (GACrownItem.getItemCount())*7200;	
		bag.decreaseByObjectId(GACrownItem.getObjectId(), GACrown);
	//	PacketSendUtility.sendMessage(player, GACrown + " Count for .52");
		RelicsAmount = RelicsAmount + GACrown;
	}
		long MACrown = bag.getItemCountByItemId(186000051);
		if (MACrown >= 1) {
		Item MaCrownItem = bag.getFirstItemByItemId(186000051);
		MACrown = MaCrownItem.getItemCount();
		ApByRelics = ApByRelics + (MaCrownItem.getItemCount())*9600;	
		bag.decreaseByObjectId(MaCrownItem.getObjectId(), MACrown);
	//	PacketSendUtility.sendMessage(player, MACrown + " Count for .51");
		RelicsAmount = RelicsAmount + MACrown;
	}}
		
//		long MACrown = bag.getItemCountByItemId(186000051);
//		if (MACrown >= 1) {
//		ApByRelics = ApByRelics + (MACrown*19200);
//		Item MaCrownItem = bag.getFirstItemByItemId(186000051);
//		bag.decreaseByObjectId(MaCrownItem.getObjectId(), MACrown);}
//		
		int ApCount = (int) ApByRelics;
		
	//	ItemService.addItem(player, derived, derived_q);
        AbyssPointsService.addAp(player, ApCount);
    //  PacketSendUtility.sendMessage(player, "Set your Abyss Points to " + ApCount + ".");
        PacketSendUtility.sendMessage(player, "You're supposed to get " + ApByRelics + " atleast.");
        PacketSendUtility.sendMessage(player, "You've successfully exchanged " + RelicsAmount + " Relics.");
		
	}}