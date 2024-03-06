/*
 * This file is part of Encom. **ENCOM FUCK OTHER SVN**
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.chatserver.model.channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.chatserver.configs.Config;
import com.aionemu.chatserver.model.Gender;
import com.aionemu.chatserver.model.PlayerClass;
import com.aionemu.chatserver.model.Race;
import com.aionemu.chatserver.service.GameServerService;

public class ChatChannels
{
    private static final Logger log = LoggerFactory.getLogger(ChatChannels.class);
    private static final List<Channel> channels = new ArrayList<Channel>();

    static {
		//LFG CHANNELS
		addGroupChannel("partyFind_PF");
		
		//TRADE
		addTradeChannel("trade_LC1");
		addTradeChannel("trade_LC2");
		addTradeChannel("trade_DC1");
		addTradeChannel("trade_DC2");
		addTradeChannel("trade_Arena_L_Lobby");
		addTradeChannel("trade_Arena_D_Lobby");
		addTradeChannel("trade_LF1");
		addTradeChannel("trade_LF2");
		addTradeChannel("trade_LF1A");
		addTradeChannel("trade_LF3");
		addTradeChannel("trade_LF4");
		addTradeChannel("trade_LF2A");
		addTradeChannel("trade_LDF1");
		addTradeChannel("trade_DF1");
		addTradeChannel("trade_DF2");
		addTradeChannel("trade_DF1A");
		addTradeChannel("trade_DF3");
		addTradeChannel("trade_DF2A");
		addTradeChannel("trade_DF4");
		addTradeChannel("trade_LDF1_D");
		
		//Instance.
		addTradeChannel("trade_IDAb1_MiniCastle");
		addTradeChannel("trade_IDLF1");
		addTradeChannel("trade_IDAbRe_Up_Asteria");
		addTradeChannel("trade_IDAbRe_Low_Divine");
		addTradeChannel("trade_IDAbRe_Up_Rhoo");
		addTradeChannel("trade_IDAbRe_Low_Wciel");
		addTradeChannel("trade_IDAbRe_Low_Eciel");
		addTradeChannel("trade_IDshulackShip");
		addTradeChannel("trade_IDAb1_Dreadgion");
		addTradeChannel("trade_IDTemple_Up");
		addTradeChannel("trade_IDTemple_UpQ");
		addTradeChannel("trade_IDTemple_Low");
		addTradeChannel("trade_IDTemple_LowQ");
		addTradeChannel("trade_IDCatacombs");
		addTradeChannel("trade_IDElim");
		addTradeChannel("trade_IDNovice");
		addTradeChannel("trade_IDDreadgion_02");
		addTradeChannel("trade_IDAbRe_Core");
		addTradeChannel("trade_IDCromede");
		addTradeChannel("trade_IDStation");
		addTradeChannel("trade_IDF4Re_Drana");
		addTradeChannel("trade_IDElemental_1");
		addTradeChannel("trade_IDElemental_2");
		addTradeChannel("trade_IDYun");
		addTradeChannel("trade_IDArena");
		addTradeChannel("trade_IDRaksha");
		addTradeChannel("trade_IDArena_Solo");
		addTradeChannel("trade_IDArena_pvp01");
		addTradeChannel("trade_IDArena_pvp02");
		addTradeChannel("trade_IDArena_pvp01_T");
		addTradeChannel("trade_IDArena_pvp02_T");
		addTradeChannel("trade_IDTiamatLab_War");
		addTradeChannel("trade_IDshulackShip_Q");
		addTradeChannel("trade_IDTiamatLab_Q");
		addTradeChannel("trade_IDArena_Glory");
		addTradeChannel("trade_IDDF3_Dragon_Boss");
		addTradeChannel("trade_IDF4Re_Solo");
		addTradeChannel("trade_IDF4Re_Solo_Q");
		addTradeChannel("trade_IDArena_Team01");
		addTradeChannel("trade_IDArena_Team01_T");
		addTradeChannel("trade_IDLDF1_Gate");
		addTradeChannel("trade_IDLDF1");
		addTradeChannel("trade_IDLDF1_H");
		addTradeChannel("trade_IDAbProL1");
		addTradeChannel("trade_IDAbProL2");
		addTradeChannel("trade_IDAbGateL1");
		addTradeChannel("trade_IDAbGateL2");
		addTradeChannel("trade_IDLF3Lp");
		addTradeChannel("trade_IDLF1B");
		addTradeChannel("trade_IDLF1B_Stigma");
		addTradeChannel("trade_IDLC1_arena");
		addTradeChannel("trade_IDLF3_Castle_indratoo");
		addTradeChannel("trade_IDLF3_Castle_Lehpar");
		addTradeChannel("trade_IDLF2a_Lab");
		addTradeChannel("trade_IDAbProL3");
		addTradeChannel("trade_IDAbProD1");
		addTradeChannel("trade_IDAbProD2");
		addTradeChannel("trade_IDAbGateD1");
		addTradeChannel("trade_IDAbGateD2");
		addTradeChannel("trade_IDDF2Flying");
		addTradeChannel("trade_IDDF1B");
		addTradeChannel("trade_IDSpace");
		addTradeChannel("trade_IDDF3_Dragon");
		addTradeChannel("trade_IDDC1_arena");
		addTradeChannel("trade_IDDF2_Dflame");
		addTradeChannel("trade_IDDF3_LP");
		addTradeChannel("trade_IDDC1_Arena_3F");
		addTradeChannel("trade_IDDf2a_Adma");
		addTradeChannel("trade_IDAbProD3");
		addTradeChannel("trade_IDDramata_01");
		addTradeChannel("trade_IDPogusgame");
		addTradeChannel("trade_Ab1");
		
        //REGION
		addRegionChannel(110010000, "public_LC1");
		addRegionChannel(110020000, "public_LC2");
		addRegionChannel(120010000, "public_DC1");
		addRegionChannel(120020000, "public_DC2");
		addRegionChannel(110070000, "public_Arena_L_Lobby");
		addRegionChannel(120080000, "public_Arena_D_Lobby");
		addRegionChannel(210010000, "public_LF1");
		addRegionChannel(210020000, "public_LF2");
		addRegionChannel(210030000, "public_LF1A");
		addRegionChannel(210040000, "public_LF3");
		addRegionChannel(210050000, "public_LF4");
		addRegionChannel(210060000, "public_LF2A");
		addRegionChannel(210070000, "public_LDF1");
		addRegionChannel(220010000, "public_DF1");
		addRegionChannel(220020000, "public_DF2");
		addRegionChannel(220030000, "public_DF1A");
		addRegionChannel(220040000, "public_DF3");
		addRegionChannel(220050000, "public_DF2A");
		addRegionChannel(220070000, "public_DF4");
		addRegionChannel(220080000, "public_LDF1_D");
		
		//Instance.
		addRegionChannel(300030000, "public_IDAb1_MiniCastle");
		addRegionChannel(300040000, "public_IDLF1");
		addRegionChannel(300050000, "public_IDAbRe_Up_Asteria");
		addRegionChannel(300060000, "public_IDAbRe_Low_Divine");
		addRegionChannel(300070000, "public_IDAbRe_Up_Rhoo");
		addRegionChannel(300080000, "public_IDAbRe_Low_Wciel");
		addRegionChannel(300090000, "public_IDAbRe_Low_Eciel");
		addRegionChannel(300100000, "public_IDshulackShip");
		addRegionChannel(300110000, "public_IDAb1_Dreadgion");
		addRegionChannel(300150000, "public_IDTemple_Up");
		addRegionChannel(300151000, "public_IDTemple_UpQ");
		addRegionChannel(300160000, "public_IDTemple_Low");
		addRegionChannel(300161000, "public_IDTemple_LowQ");
		addRegionChannel(300170000, "public_IDCatacombs");
		addRegionChannel(300190000, "public_IDElim");
		addRegionChannel(300200000, "public_IDNovice");
		addRegionChannel(300210000, "public_IDDreadgion_02");
		addRegionChannel(300220000, "public_IDAbRe_Core");
		addRegionChannel(300230000, "public_IDCromede");
		addRegionChannel(300240000, "public_IDStation");
		addRegionChannel(300250000, "public_IDF4Re_Drana");
		addRegionChannel(300260000, "public_IDElemental_1");
		addRegionChannel(300270000, "public_IDElemental_2");
		addRegionChannel(300280000, "public_IDYun");
		addRegionChannel(300300000, "public_IDArena");
		addRegionChannel(300310000, "public_IDRaksha");
		addRegionChannel(300320000, "public_IDArena_Solo");
		addRegionChannel(300350000, "public_IDArena_pvp01");
		addRegionChannel(300360000, "public_IDArena_pvp02");
		addRegionChannel(300420000, "public_IDArena_pvp01_T");
		addRegionChannel(300430000, "public_IDArena_pvp02_T");
		addRegionChannel(300440000, "public_IDTiamatLab_War");
		addRegionChannel(300450000, "public_IDshulackShip_Q");
		addRegionChannel(300460000, "public_IDTiamatLab_Q");
		addRegionChannel(300470000, "public_IDArena_Glory");
		addRegionChannel(300480000, "public_IDDF3_Dragon_Boss");
		addRegionChannel(300500000, "public_IDF4Re_Solo");
		addRegionChannel(300510000, "public_IDF4Re_Solo_Q");
		addRegionChannel(300520000, "public_IDArena_Team01");
		addRegionChannel(300530000, "public_IDArena_Team01_T");
		addRegionChannel(300540000, "public_IDLDF1_Gate");
		addRegionChannel(300550000, "public_IDLDF1");
		addRegionChannel(300560000, "public_IDLDF1_H");
		addRegionChannel(310010000, "public_IDAbProL1");
		addRegionChannel(310020000, "public_IDAbProL2");
		addRegionChannel(310030000, "public_IDAbGateL1");
		addRegionChannel(310040000, "public_IDAbGateL2");
		addRegionChannel(310050000, "public_IDLF3Lp");
		addRegionChannel(310060000, "public_IDLF1B");
		addRegionChannel(310070000, "public_IDLF1B_Stigma");
		addRegionChannel(310080000, "public_IDLC1_arena");
		addRegionChannel(310090000, "public_IDLF3_Castle_indratoo");
		addRegionChannel(310100000, "public_IDLF3_Castle_Lehpar");
		addRegionChannel(310110000, "public_IDLF2a_Lab");
		addRegionChannel(310120000, "public_IDAbProL3");
		addRegionChannel(320010000, "public_IDAbProD1");
		addRegionChannel(320020000, "public_IDAbProD2");
		addRegionChannel(320030000, "public_IDAbGateD1");
		addRegionChannel(320040000, "public_IDAbGateD2");
		addRegionChannel(320050000, "public_IDDF2Flying");
		addRegionChannel(320060000, "public_IDDF1B");
		addRegionChannel(320070000, "public_IDSpace");
		addRegionChannel(320080000, "public_IDDF3_Dragon");
		addRegionChannel(320090000, "public_IDDC1_arena");
		addRegionChannel(320100000, "public_IDDF2_Dflame");
		addRegionChannel(320110000, "public_IDDF3_LP");
		addRegionChannel(320120000, "public_IDDC1_Arena_3F");
		addRegionChannel(320130000, "public_IDDf2a_Adma");
		addRegionChannel(320140000, "public_IDAbProD3");
		addRegionChannel(320150000, "public_IDDramata_01");
		addRegionChannel(330010000, "public_IDPogusgame");
		addRegionChannel(400010000, "public_Ab1");
		
		//LANG & JOB
		if (Config.LANG_CHAT == 0) {
			//LANG
			addLangChannel("User_Kor");
			addLangChannel("gotcha_PF");
			//JOB: Male
			addJobChannel(Gender.MALE, PlayerClass.GLADIATOR, "job_Gladiator");
			addJobChannel(Gender.MALE, PlayerClass.TEMPLAR, "job_Templar");
			addJobChannel(Gender.MALE, PlayerClass.SORCERER, "job_Sorcerer");
			addJobChannel(Gender.MALE, PlayerClass.SPIRIT_MASTER, "job_Spiritmaster");
			addJobChannel(Gender.MALE, PlayerClass.CHANTER, "job_Chanter");
			addJobChannel(Gender.MALE, PlayerClass.RANGER, "job_Ranger");
			addJobChannel(Gender.MALE, PlayerClass.ASSASSIN, "job_Assassin");
			addJobChannel(Gender.MALE, PlayerClass.CLERIC, "job_Cleric");
			addJobChannel(Gender.MALE, PlayerClass.THUNDERER, "job_Thunderer");
			//Female
			addJobChannel(Gender.FEMALE, PlayerClass.GLADIATOR, "job_Gladiator");
			addJobChannel(Gender.FEMALE, PlayerClass.TEMPLAR, "job_Templar");
			addJobChannel(Gender.FEMALE, PlayerClass.SORCERER, "job_Sorcerer");
			addJobChannel(Gender.FEMALE, PlayerClass.SPIRIT_MASTER, "job_Spiritmaster");
			addJobChannel(Gender.FEMALE, PlayerClass.CHANTER, "job_Chanter");
			addJobChannel(Gender.FEMALE, PlayerClass.RANGER, "job_Ranger");
			addJobChannel(Gender.FEMALE, PlayerClass.ASSASSIN, "job_Assassin");
			addJobChannel(Gender.FEMALE, PlayerClass.CLERIC, "job_Cleric");
			addJobChannel(Gender.FEMALE, PlayerClass.THUNDERER, "job_Thunderer");
        } if (Config.LANG_CHAT == 1) {
			//LANG
			addLangChannel("User_English");
            addLangChannel("User_French");
			addLangChannel("gotcha_PF");
			//JOB: Male
			addJobChannel(Gender.MALE, PlayerClass.GLADIATOR, "job_Gladiator");
			addJobChannel(Gender.MALE, PlayerClass.TEMPLAR, "job_Templar");
			addJobChannel(Gender.MALE, PlayerClass.SORCERER, "job_Sorcerer");
			addJobChannel(Gender.MALE, PlayerClass.SPIRIT_MASTER, "job_Spiritmaster");
			addJobChannel(Gender.MALE, PlayerClass.CHANTER, "job_Chanter");
			addJobChannel(Gender.MALE, PlayerClass.RANGER, "job_Ranger");
			addJobChannel(Gender.MALE, PlayerClass.ASSASSIN, "job_Assassin");
			addJobChannel(Gender.MALE, PlayerClass.CLERIC, "job_Cleric");
			addJobChannel(Gender.MALE, PlayerClass.THUNDERER, "job_Thunderer");
			//Female
			addJobChannel(Gender.FEMALE, PlayerClass.GLADIATOR, "job_Gladiator");
			addJobChannel(Gender.FEMALE, PlayerClass.TEMPLAR, "job_Templar");
			addJobChannel(Gender.FEMALE, PlayerClass.SORCERER, "job_Sorcerer");
			addJobChannel(Gender.FEMALE, PlayerClass.SPIRIT_MASTER, "job_Spiritmaster");
			addJobChannel(Gender.FEMALE, PlayerClass.CHANTER, "job_Chanter");
			addJobChannel(Gender.FEMALE, PlayerClass.RANGER, "job_Ranger");
			addJobChannel(Gender.FEMALE, PlayerClass.ASSASSIN, "job_Assassin");
			addJobChannel(Gender.FEMALE, PlayerClass.CLERIC, "job_Cleric");
			addJobChannel(Gender.FEMALE, PlayerClass.THUNDERER, "job_Thunderer");
        } if (Config.LANG_CHAT == 2) {
			//LANG
			addLangChannel("User_Anglais");
			addLangChannel("User_Francais");
			addLangChannel("gotcha_PF");
			//JOB: Male
			addJobChannel(Gender.MALE, PlayerClass.GLADIATOR, "job_Gladiator");
			addJobChannel(Gender.MALE, PlayerClass.TEMPLAR, "job_Templar");
			addJobChannel(Gender.MALE, PlayerClass.SORCERER, "job_Sorcerer");
			addJobChannel(Gender.MALE, PlayerClass.SPIRIT_MASTER, "job_Spiritmaster");
			addJobChannel(Gender.MALE, PlayerClass.CHANTER, "job_Chanter");
			addJobChannel(Gender.MALE, PlayerClass.RANGER, "job_Ranger");
			addJobChannel(Gender.MALE, PlayerClass.ASSASSIN, "job_Assassin");
			addJobChannel(Gender.MALE, PlayerClass.CLERIC, "job_Cleric");
			addJobChannel(Gender.MALE, PlayerClass.THUNDERER, "job_Thunderer");
			//Female
			addJobChannel(Gender.FEMALE, PlayerClass.GLADIATOR, "job_Gladiator");
			addJobChannel(Gender.FEMALE, PlayerClass.TEMPLAR, "job_Templar");
			addJobChannel(Gender.FEMALE, PlayerClass.SORCERER, "job_Sorcerer");
			addJobChannel(Gender.FEMALE, PlayerClass.SPIRIT_MASTER, "job_Spiritmaster");
			addJobChannel(Gender.FEMALE, PlayerClass.CHANTER, "job_Chanter");
			addJobChannel(Gender.FEMALE, PlayerClass.RANGER, "job_Ranger");
			addJobChannel(Gender.FEMALE, PlayerClass.ASSASSIN, "job_Assassin");
			addJobChannel(Gender.FEMALE, PlayerClass.CLERIC, "job_Cleric");
			addJobChannel(Gender.FEMALE, PlayerClass.THUNDERER, "job_Thunderer");
        }
	}
	
	private static void addChannel(Channel Channel) {
		channels.add(Channel);
	}
	
	public static Channel getChannelById(int channelId) {
		for (Channel channel : channels) {
			if (channel.getChannelId() == channelId)
			return channel;
		} if (Config.LOG_CHANNEL_INVALID) {
			log.warn("No registered channel with id " + channelId);
		}
		throw new IllegalArgumentException("no channel provided for id " + channelId);
	}
	
	/**
	 * @param identifier
	 * @return
	 */
	public static Channel getChannelByIdentifier(byte[] identifier) {
		for (Channel channel : channels) {
			if (Arrays.equals(channel.getIdentifierBytes(), identifier))
			return channel;
		} if (Config.LOG_CHANNEL_INVALID) {
			log.warn("No registered channel with identifier " + identifier);
		}
		return null;
	}

    private static void addGroupChannel(String channelName) {
        addChannel(new LfgChannel(Race.ELYOS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".0.AION.KOR"));
        addChannel(new LfgChannel(Race.ASMODIANS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".1.AION.KOR"));
    }

    private static void addTradeChannel(String channelName) {
        addChannel(new TradeChannel(Race.ELYOS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".0.AION.KOR"));
        addChannel(new TradeChannel(Race.ASMODIANS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".1.AION.KOR"));
    }

    private static void addRegionChannel(int mapId, String channelName) {
        addChannel(new RegionChannel(mapId, Race.ELYOS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".0.AION.KOR"));
        addChannel(new RegionChannel(mapId, Race.ASMODIANS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".1.AION.KOR"));
    }

    private static void addJobChannel(Gender gender, PlayerClass playerClass, String channelName) {
        addChannel(new JobChannel(gender, playerClass, Race.ELYOS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".0.AION.KOR"));
        addChannel(new JobChannel(gender, playerClass, Race.ASMODIANS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".1.AION.KOR"));
    }

    private static void addLangChannel(String channelName) {
        addChannel(new LangChannel(Race.ELYOS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".0.AION.KOR"));
        addChannel(new LangChannel(Race.ASMODIANS, "@\u0001" + channelName + "\u0001" + GameServerService.GAMESERVER_ID + ".1.AION.KOR"));
    }
}