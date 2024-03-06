package com.aionemu.gameserver;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.ServerPacketsOpcodes;
import com.aionemu.gameserver.network.factories.AionPacketHandlerFactory;

import java.util.Map;

public class main
{
	public static void main(String[] args) {


		System.out.println("<packetfamilly switchtype=\"h\" way=\"ServerPackets\" name=\"Server Messages\">");
		for (Map.Entry<Class<? extends AionServerPacket>, Integer> entry : ServerPacketsOpcodes.opcodes.entrySet()) {
			System.out.println("\t<packet id=\"" + String.format("0x%X", entry.getValue()) + "\" name=\"" + entry.getKey().getSimpleName() + "\">\n\t</packet>");
		}
		System.out.println("</packetfamilly>");



		System.out.println("<packetfamilly switchtype=\"h\" way=\"ClientPackets\">");
		for (Map.Entry<Integer, AionClientPacket> entry : AionPacketHandlerFactory.getInstance().getPacketHandler().packetsPrototypes.entrySet()) {
			System.out.println("\t<packet id=\"" + String.format("0x%X", entry.getKey()) + "\" name=\"" + entry.getValue().getClass().getSimpleName() + "\">\n\t</packet>");
		}
		System.out.println("</packetfamilly>");

	}
}