package com.aionemu.gameserver.network.aion;

import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.network.aion.serverpackets.missing.SM_QUNA;
import com.aionemu.gameserver.network.aion.serverpackets.missing.SM_SELECT_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.missing.SM_SELECT_ITEM_ADD;
import com.aionemu.gameserver.network.aion.serverpackets.need.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerPacketsOpcodes {
    public static Map<Class<? extends AionServerPacket>, Integer> opcodes = new HashMap<Class<? extends AionServerPacket>, Integer>();

    static {
        Set<Integer> idSet = new HashSet<>();
        addPacketOpcode(S_VERSION_CHECK.class, 0x00, idSet);                                                    //2.8
        addPacketOpcode(S_STATUS.class, 0x01, idSet);
        //S_STATUS_OTHER 0x02
        addPacketOpcode(S_HIT_POINT.class, 0x03, idSet);
        addPacketOpcode(S_MANA_POINT.class, 0x04, idSet);
        addPacketOpcode(S_HIT_POINT_OTHER.class, 0x05, idSet);
        addPacketOpcode(S_DP.class, 0x06, idSet);
        addPacketOpcode(S_DP_USER.class, 0x07, idSet);
        addPacketOpcode(S_EXP.class, 0x08, idSet);
        //S_LOGIN_CHECK 0x09
        addPacketOpcode(S_CUTSCENE_NPC_INFO.class, 0x0A, idSet);
        addPacketOpcode(S_CHANGE_GUILD_MEMBER_NICKNAME.class, 0x0B, idSet);
        addPacketOpcode(S_GUILD_HISTORY.class, 0x0C, idSet);
        addPacketOpcode(S_ENTER_WORLD_CHECK.class, 0x0D, idSet);
        addPacketOpcode(S_PUT_NPC.class, 0x0E, idSet);                                                          //2.8
        addPacketOpcode(S_WORLD.class, 0x0F, idSet);
        //S_DUMMY_PACKET 0x10
        addPacketOpcode(S_PUT_OBJECT.class, 0x11, idSet);
        //S_PUT_VEHICLE 0x12
        //S_BUILDER_RESULT 0x13
        addPacketOpcode(S_REQUEST_TELEPORT.class, 0x14, idSet);
        addPacketOpcode(S_BLINK.class, 0x15, idSet);
        addPacketOpcode(S_REMOVE_OBJECT.class, 0x16, idSet);
        addPacketOpcode(S_WAIT_LIST.class, 0x17, idSet);
        addPacketOpcode(S_MESSAGE.class, 0x18, idSet);
        addPacketOpcode(S_MESSAGE_CODE.class, 0x19, idSet);
        addPacketOpcode(S_LOAD_INVENTORY.class, 0x1A, idSet);                                                   //2.8
        addPacketOpcode(S_ADD_INVENTORY.class, 0x1B, idSet);
        addPacketOpcode(S_REMOVE_INVENTORY.class, 0x1C, idSet);
        addPacketOpcode(S_CHANGE_ITEM_DESC.class, 0x1D, idSet);
        addPacketOpcode(S_LOAD_CLIENT_SETTINGS.class, 0x1E, idSet);
        addPacketOpcode(S_CHANGE_STANCE.class, 0x1F, idSet);
        addPacketOpcode(S_PUT_USER.class, 0x20, idSet);
        addPacketOpcode(S_USE_SKILL.class, 0x21, idSet);
        addPacketOpcode(S_GATHER_OTHER.class, 0x22, idSet);
        addPacketOpcode(S_GATHER.class, 0x23, idSet);
        addPacketOpcode(S_WIELD.class, 0x24, idSet);                                                            //2.8
        addPacketOpcode(S_ACTION.class, 0x25, idSet);
        addPacketOpcode(S_TIME.class, 0x26, idSet);
        addPacketOpcode(S_SYNC_TIME.class, 0x27, idSet);
        addPacketOpcode(S_NPC_CHANGED_TARGET.class, 0x28, idSet);
        addPacketOpcode(S_TARGET_INFO.class, 0x29, idSet);
        addPacketOpcode(S_SKILL_CANCELED.class, 0x2A, idSet);
        addPacketOpcode(S_SKILL_SUCCEDED.class, 0x2B, idSet);
        addPacketOpcode(S_ADD_SKILL.class, 0x2C, idSet);
        addPacketOpcode(S_DELETE_SKILL.class, 0x2D, idSet);
        addPacketOpcode(S_TOGGLE_SKILL_ON_OFF.class, 0x2E, idSet);
        //S_ADD_MAINTAIN_SKILL 0x2F
        //S_DELETE_MAINTAIN_SKILL 0x30
        addPacketOpcode(S_ABNORMAL_STATUS.class, 0x31, idSet);                                                  //2.8
        addPacketOpcode(S_ABNORMAL_STATUS_OTHER.class, 0x32, idSet);                                            //2.8
        addPacketOpcode(S_LOAD_SKILL_COOLTIME.class, 0x33, idSet);
        addPacketOpcode(S_ASK.class, 0x34, idSet);
        //S_CANCEL_ASK 0x35
        addPacketOpcode(S_ATTACK.class, 0x36, idSet);
        addPacketOpcode(S_MOVE_NEW.class, 0x37, idSet);
        //S_MOVE_OBJECT 0x38
        addPacketOpcode(S_CHANGE_DIRECTION.class, 0x39, idSet);
        addPacketOpcode(S_POLYMORPH.class, 0x3A, idSet);
        //S_SKILL_OTHER 0x3B
        addPacketOpcode(S_NPC_HTML_MESSAGE.class, 0x3C, idSet);
        //-1 START HERE
        addPacketOpcode(S_ITEM_LIST.class, 0x40, idSet);
        //S_GUILD_OTHER_MEMBER_INFO 0x41
        addPacketOpcode(S_WEATHER.class, 0x42, idSet);
        addPacketOpcode(S_INVISIBLE_LEVEL.class, 0x43, idSet);
        //S_RECALLED_BY_OTHER 0x44
        addPacketOpcode(S_EFFECT.class, 0x45, idSet);
        addPacketOpcode(S_LOAD_WORKINGQUEST.class, 0x46, idSet);                                                //2.8
        addPacketOpcode(S_KEY.class, 0x47, idSet);                                                              //2.8
        addPacketOpcode(S_RESET_SKILL_COOLING_TIME.class, 0x48, idSet);
        addPacketOpcode(S_XCHG_START.class, 0x49, idSet);
        addPacketOpcode(S_ADD_XCHG.class, 0x4A, idSet);
        //S_REMOVE_XCHG 0x4B
        addPacketOpcode(S_XCHG_GOLD.class, 0x4C, idSet);
        addPacketOpcode(S_XCHG_RESULT.class, 0x4D, idSet);
        addPacketOpcode(S_ADDREMOVE_SOCIAL.class, 0x4E, idSet);
        //S_CHECK_MESSAGE 0x4F
        addPacketOpcode(S_USER_CHANGED_TARGET.class, 0x50, idSet);
        addPacketOpcode(S_EDIT_CHARACTER.class, 0x52, idSet);
        addPacketOpcode(S_SERIAL_KILLER_LIST.class, 0x53, idSet);
        addPacketOpcode(S_ABYSS_NEXT_PVP_CHANGE_TIME.class, 0x54, idSet);
        addPacketOpcode(S_ABYSS_CHANGE_NEXT_PVP_STATUS.class, 0x55, idSet);
        addPacketOpcode(S_CAPTCHA.class, 0x56, idSet);
        addPacketOpcode(S_ADDED_SERVICE_CHANGE.class, 0x57, idSet);
        addPacketOpcode(S_FIND_NPC_POS_RESULT.class, 0x58, idSet);
        addPacketOpcode(S_PARTY_INFO.class, 0x59, idSet); // 2.8 ok
        addPacketOpcode(S_PARTY_MEMBER_INFO.class, 0x5A, idSet);
        //S_GGAUTH_CHECK_QUERY 0x60
        addPacketOpcode(S_BALAUREA_INFO.class, 0x60, idSet);
        addPacketOpcode(S_ASK_QUIT_RESULT.class, 0x61, idSet);
        addPacketOpcode(S_ASK_INFO_RESULT.class, 0x62, idSet);
        //S_FATIGUE_INFO 0x63
        addPacketOpcode(S_FUNCTIONAL_PET.class, 0x64, idSet);
        //S_QUERY_NUMBER 0x65
        addPacketOpcode(S_LOAD_ITEM_COOLTIME.class, 0x66, idSet);
        addPacketOpcode(S_TODAY_WORDS.class, 0x67, idSet);
        addPacketOpcode(S_PLAY_CUTSCENE.class, 0x68, idSet);
        //S_GET_ON_VEHICLE 0x69
        //S_GET_OFF_VEHICLE 0x6A
        //S_KICK 0x6C
        addPacketOpcode(S_GUILD_INFO.class, 0x6D, idSet);
        addPacketOpcode(S_ADD_GUILD_MEMBER.class, 0x6E, idSet);
        addPacketOpcode(S_DELETE_GUILD_MEMBER.class, 0x6F, idSet);
        addPacketOpcode(S_CHANGE_GUILD_MEMBER_INFO.class, 0x70, idSet);
        addPacketOpcode(S_CHANGE_GUILD_OTHER.class, 0x71, idSet);
        //S_ATTACK_RESULT 0x72
        //S_DYNCODE_DATA 0x74
        //S_SNDC_CHECK_MESSAGE 0x75
        addPacketOpcode(S_CHANGE_GUILD_MEMBER_INTRO.class, 0x76, idSet);
        //S_WANTED_LOGIN 0x77
        addPacketOpcode(S_INSTANT_DUNGEON_INFO.class, 0x78, idSet);
        addPacketOpcode(S_MATCHMAKER_INFO.class, 0x79, idSet);
        addPacketOpcode(S_LOAD_FINISHEDQUEST.class, 0x7A, idSet);                                               //2.8
        addPacketOpcode(S_QUEST.class, 0x7B, idSet);
        //S_NCGUARD 0x7C
        addPacketOpcode(S_UPDATE_ZONE_QUEST.class, 0x7E, idSet);
        addPacketOpcode(S_PING.class, 0x7F, idSet);
        //S_SHOP_RESULT 0x80
        addPacketOpcode(S_EVENT.class, 0x81, idSet);
        addPacketOpcode(S_BUDDY_LIST.class, 0x83, idSet);
        //S_BOOK_LIST 0x84
        addPacketOpcode(S_SHOP_SELL_LIST.class, 0x85, idSet);
        addPacketOpcode(S_GROUP_ITEM_DIST.class, 0x86, idSet);
        addPacketOpcode(S_ETC_STATUS.class, 0x87, idSet);
        addPacketOpcode(S_SA_ACCOUNT_ITEM_NOTI.class, 0x88, idSet);
        addPacketOpcode(S_ABYSS_RANKER_INFOS.class, 0x89, idSet);
        addPacketOpcode(S_ABYSS_GUILD_INFOS.class, 0x8A, idSet);
        addPacketOpcode(S_WORLD_SCENE_STATUS.class, 0x8B, idSet);
        addPacketOpcode(S_INSTANCE_DUNGEON_COOLTIMES.class, 0x8C, idSet);
        addPacketOpcode(S_ALIVE.class, 0x8D, idSet);
        //S_DEBUG_PUT_BEACON 0x8E
        addPacketOpcode(S_PLACEABLE_BINDSTONE_INFO.class, 0x8F, idSet);
        addPacketOpcode(S_PERSONAL_SHOP.class, 0x90, idSet);
        addPacketOpcode(S_VENDOR.class, 0x91, idSet);
        //S_ENTER_WORLD_NOTIFY 0x92
        addPacketOpcode(S_CUSTOM_ANIM.class, 0x93, idSet);
        //S_SHOPAGENT2 0x94
        addPacketOpcode(S_TRADE_IN.class, 0x96, idSet);
        addPacketOpcode(S_ADD_PET.class, 0x98, idSet);
        addPacketOpcode(S_REMOVE_PET.class, 0x99, idSet);
        addPacketOpcode(S_CHANGE_PET_STATUS.class, 0x9A, idSet);
        addPacketOpcode(S_CHANGE_MASTER.class, 0x9B, idSet);
        addPacketOpcode(S_GUILD_MEMBER_INFO.class, 0x9C, idSet);
        addPacketOpcode(S_CHANGE_GUILD_INFO.class, 0x9D, idSet);
        addPacketOpcode(S_SHOP_POINT_INFO.class, 0x9E, idSet); //TOLL INFO
        //S_CHANGE_NPC_STATUS 0x9F
        addPacketOpcode(S_MAIL.class, 0xA0, idSet);
        addPacketOpcode(S_ALLOW_PET_USE_SKILL.class, 0xA1, idSet);
        addPacketOpcode(S_WIND_PATH_RESULT.class, 0xA2, idSet);
        addPacketOpcode(S_WIND_STATE_INFO.class, 0xA3, idSet);
        //S_LOAD_GATHERCOMBINE_COOLTIME 0xA4
        addPacketOpcode(S_PARTY_MATCH.class, 0xA5, idSet);
        addPacketOpcode(S_USER_SELL_HISTORY_LIST.class, 0xA6, idSet);
        addPacketOpcode(S_LOAD_WAREHOUSE.class, 0xA7, idSet);
        addPacketOpcode(S_ADD_WAREHOUSE.class, 0xA8, idSet);
        addPacketOpcode(S_REMOVE_WAREHOUSE.class, 0xA9, idSet);
        addPacketOpcode(S_CHANGE_WAREHOUSE_ITEM_DESC.class, 0xAA, idSet);
        addPacketOpcode(S_SHOP_CATEGORY_INFO.class, 0xAB, idSet); // ingameshop
        addPacketOpcode(S_SHOP_GOODS_LIST.class, 0xAC, idSet); // ingameshop
        addPacketOpcode(S_SHOP_GOODS_INFO.class, 0xAD, idSet); // ingameshop
        addPacketOpcode(S_TITLE.class, 0xAF, idSet);
        addPacketOpcode(S_2ND_PASSWORD.class, 0xB0, idSet);
        //S_FATIGUE_KOREA 0xB2
        addPacketOpcode(S_COMBINE_OTHER.class, 0xB3, idSet);
        addPacketOpcode(S_COMBINE.class, 0xB4, idSet);
        addPacketOpcode(S_PLAY_MODE.class, 0xB5, idSet);
        addPacketOpcode(S_USE_ITEM.class, 0xB6, idSet);
        addPacketOpcode(S_CHANGE_FLAG.class, 0xB7, idSet);
        addPacketOpcode(S_DUEL.class, 0xB8, idSet);
        //S_CLIENTSIDE_NPC_BLINK 0xB9
        addPacketOpcode(S_FUNCTIONAL_PET_MOVE.class, 0xBA, idSet);
        //S_RECONNECT_OTHER_SERVER 0xBB
        //S_LOAD_PVP_ENV 0xBC
        //S_CHANGE_PVP_ENV 0xBD
        addPacketOpcode(S_POLL_CONTENTS.class, 0xBE, idSet);
        //S_GM_COMMENT 0xBF
        addPacketOpcode(S_RESURRECT_INFO.class, 0xC0, idSet);
        addPacketOpcode(S_RESURRECT_BY_OTHER.class, 0xC1, idSet);
        addPacketOpcode(S_MOVEBACK.class, 0xC2, idSet);
        addPacketOpcode(S_ROUTEMAP_INFO.class, 0xC3, idSet);
        addPacketOpcode(S_GAUGE.class, 0xC4, idSet);
        addPacketOpcode(S_SHOW_NPC_MOTION.class, 0xC5, idSet);
        addPacketOpcode(S_L2AUTH_LOGIN_CHECK.class, 0xC6, idSet);
        addPacketOpcode(S_CHARACTER_LIST.class, 0xC7, idSet);                                                   //2.8
        addPacketOpcode(S_CREATE_CHARACTER.class, 0xC8, idSet);
        addPacketOpcode(S_DELETE_CHARACTER.class, 0xC9, idSet);
        addPacketOpcode(S_RESTORE_CHARACTER.class, 0xCA, idSet);
        addPacketOpcode(S_FORCE_BLINK.class, 0xCB, idSet); //TARGET IMOBILIZE
        addPacketOpcode(S_LOOT.class, 0xCC, idSet);                                                             //2.8
        addPacketOpcode(S_LOOT_ITEMLIST.class, 0xCD, idSet);                                                    //2.8
        addPacketOpcode(S_RECIPE_LIST.class, 0x0CE, idSet);
        addPacketOpcode(S_SKILL_ACTIVATED.class, 0xCF, idSet);
        addPacketOpcode(S_ABYSS_INFO.class, 0xD0, idSet);
        addPacketOpcode(S_CHANGE_ABYSS_PVP_STATUS.class, 0xD1, idSet);
        addPacketOpcode(S_SEARCH_USER_RESULT.class, 0xD2, idSet);
        //S_GUILD_EMBLEM_UPLOAD_RESULT 0xD3
        addPacketOpcode(S_GUILD_EMBLEM_IMG_BEGIN.class, 0xD4, idSet);
        addPacketOpcode(S_GUILD_EMBLEM_IMG_DATA.class, 0xD5, idSet);
        addPacketOpcode(S_GUILD_EMBLEM_UPDATED.class, 0xD6, idSet);
        //S_SKILL_PENALTY_STATUS 0xD7
        //S_SKILL_PENALTY_STATUS_OTHER 0xD8
        addPacketOpcode(S_ABYSS_SHIELD_INFO.class, 0xD9, idSet);
        addPacketOpcode(S_ARTIFACT_INFO.class, 0xDC, idSet);
        //stop -1 here
        addPacketOpcode(S_BUDDY_RESULT.class, 0xDE, idSet);
        addPacketOpcode(S_BLOCK_RESULT.class, 0xDF, idSet);
        addPacketOpcode(S_BLOCK_LIST.class, 0xE0, idSet);
        addPacketOpcode(S_NOTIFY_BUDDY.class, 0xE1, idSet);
        addPacketOpcode(S_CUR_STATUS.class, 0xE3, idSet);
        //S_VIRTUAL_AUTH 0xE4
        addPacketOpcode(S_CHANGE_CHANNEL.class, 0xE5, idSet);
        addPacketOpcode(S_SIGN_CLIENT.class, 0xE6, idSet);
        addPacketOpcode(S_LOAD_MACRO.class, 0xE7, idSet);
        addPacketOpcode(S_MACRO_RESULT.class, 0xE8, idSet);
        addPacketOpcode(S_EXIST_RESULT.class, 0xE9, idSet);
        //S_EXTRA_ITEM_CHANGE_CONTEXT 0xEA
        addPacketOpcode(S_RESURRECT_LOC_INFO.class, 0xEB, idSet);
        addPacketOpcode(S_WORLD_INFO.class, 0xEC, idSet);
        addPacketOpcode(S_ABYSS_POINT.class, 0xED, idSet);
        addPacketOpcode(S_BUILDER_LEVEL.class, 0xEE, idSet);
        //addPacketOpcode(S_PETITION_STATUS.class, 0xEF, idSet);
        addPacketOpcode(S_BUDDY_DATA.class, 0xF0, idSet);
        addPacketOpcode(S_ADD_RECIPE.class, 0xF1, idSet);
        addPacketOpcode(S_REMOVE_RECIPE.class, 0xF2, idSet);
        addPacketOpcode(S_CHANGE_ABYSS_TELEPORTER_STATUS.class, 0xF3, idSet);
        addPacketOpcode(S_FLIGHT_POINT.class, 0xF4, idSet);
        addPacketOpcode(S_ALLIANCE_INFO.class, 0xF5, idSet);
        addPacketOpcode(S_ALLIANCE_MEMBER_INFO.class, 0xF6, idSet);
        addPacketOpcode(S_GROUP_INFO.class, 0xF7, idSet);
        //S_GROUP_MEMBER_INFO 0xF8
        addPacketOpcode(S_TACTICS_SIGN.class, 0xF9, idSet);
        addPacketOpcode(S_GROUP_READY.class, 0xFA, idSet);
        addPacketOpcode(S_TAX_INFO.class, 0xFC, idSet);
        addPacketOpcode(S_STORE_SALE_INFO.class, 0xFD, idSet);
        addPacketOpcode(S_INVINCIBLE_TIME.class, 0xFE, idSet);
        addPacketOpcode(S_RECONNECT_KEY.class, 0xFF, idSet);//1.8
        addPacketOpcode(S_WEB_NOTI.class, 0x100, idSet); //need to add
        addPacketOpcode(S_BM_PACK_LIST.class, 0x101, idSet);
        addPacketOpcode(SM_SELECT_ITEM.class, 0x103, idSet);//A TROUVER
        addPacketOpcode(SM_SELECT_ITEM_ADD.class, 0x104, idSet);//A TROUVER
        addPacketOpcode(S_REPLY_NP_LOGIN_GAMESVR.class, 0x105, idSet);
        addPacketOpcode(S_REPLY_NP_CONSUME_TOKEN_RESULT.class, 0x106, idSet); //need to add
        addPacketOpcode(S_REPLY_NP_AUTH_TOKEN.class, 0x107, idSet);
        addPacketOpcode(S_GPK_AUTH.class, 0x11E, idSet); //need to add
        addPacketOpcode(S_GPK_HEARTBEAT.class, 0x11F, idSet); //need to add
        addPacketOpcode(S_NPSHOP_GOODS_COUNT.class, 0x109, idSet);
        addPacketOpcode(S_NPSHOP_GOODS_CHANGE.class, 0x10A, idSet); //need to add
        addPacketOpcode(S_RESPONSE_NPSHOP_GOODS_LIST.class, 0x10B, idSet); //need to add
        addPacketOpcode(S_RESPONSE_NPSHOP_GOODS_RECV.class, 0x10C, idSet); //need to add
        addPacketOpcode(S_GAMEPASS_INFO.class, 0x118, idSet);
        addPacketOpcode(S_GAMEPASS_OTHER_UPDATED.class, 0x119, idSet); //need to add
        addPacketOpcode(S_RANK_LIST.class, 0x12F, idSet); //need to add
        addPacketOpcode(S_RANK_INFO.class, 0x130, idSet); //need to add
        addPacketOpcode(S_RANKING_BADGE.class, 0x137, idSet); //need to add
        addPacketOpcode(S_RANKING_BADGE_OTHER.class, 0x138, idSet); //need to add
        addPacketOpcode(S_RANKING_BADGE_LIST.class, 0x139, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_LIST.class, 0x13B, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_MYLIST.class, 0x13C, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_BUY.class, 0x13D, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_COMMIT.class, 0x13E, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_CANCEL.class, 0x13F, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_SALESLOG.class, 0x140, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_COLLECT.class, 0x141, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_AVG_PRICE.class, 0x142, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_STATE.class, 0x143, idSet); //need to add
        addPacketOpcode(S_GLOBAL_TRADE_HISTORY.class, 0x144, idSet); //need to add
        addPacketOpcode(S_SERVER_ENV.class, 0x10D, idSet);
        addPacketOpcode(S_READY_ENTER_WORLD.class, 0x11C, idSet);
        addPacketOpcode(S_RESULT_PASSPORT_FIRST.class, 0x131, idSet); //need to add
        addPacketOpcode(S_RESULT_PASSPORT.class, 0x132, idSet); //need to add
        addPacketOpcode(S_REWARD_ACHIEVEMENT_EVENT_RESULT.class, 0x114, idSet);
        addPacketOpcode(S_CLEAR_ACHIEVEMENT_EVENT.class, 0x115, idSet); //1.8 ok
        addPacketOpcode(S_BATTLEPASS_LIST.class, 0x116, idSet);
        addPacketOpcode(S_BATTLEPASS_UPDATED.class, 0x117, idSet);
        addPacketOpcode(S_CREATE_ACHIEVEMENT_EVENT.class, 0x111, idSet);
        addPacketOpcode(S_PROGRESS_ACHIEVEMENT.class, 0x10F, idSet); //A VOIR
        addPacketOpcode(S_USER_CLASSIC_WARDROBE_LOAD.class, 0x121, idSet);
        addPacketOpcode(S_USER_CLASSIC_WARDROBE_INFO_UPDATED.class, 0x122, idSet);
        addPacketOpcode(S_USER_CLASSIC_WARDROBE_DATA_UPDATED.class, 0x123, idSet);
        addPacketOpcode(S_LOAD_PROMOTION.class, 0x108, idSet);
        addPacketOpcode(S_LOAD_EQUIPMENT_CHANGE.class, 0x12E, idSet);//need to add
        addPacketOpcode(S_USER_BIND_STONE_INFO.class, 0x145, idSet);//need to add
        addPacketOpcode(S_CHAT_ACCUSE.class, 0x120, idSet);
        addPacketOpcode(S_SPAM_FILTER_FLAG.class, 0x125, idSet);
        addPacketOpcode(S_GLOBAL_EVENT_BOOST_LIST.class, 0x126, idSet);//need to add
        addPacketOpcode(S_CHANNEL_CHATTING_PERMISSION.class, 0x127, idSet);//need to add
        addPacketOpcode(S_LOAD_CHANNEL_CHATTING_BLACKLIST.class, 0x128, idSet);//need to add
        addPacketOpcode(S_RESPONSE_CHANNEL_CHATTING_TELLER.class, 0x129, idSet);//need to add
        addPacketOpcode(S_ADD_CHANNEL_CHATTING_BLACKLIST.class, 0x12A, idSet);//need to add
        addPacketOpcode(S_CHANNEL_CHATTING_BLACKLIST_SETTING.class, 0x12B, idSet);
        addPacketOpcode(S_REMOVE_CHANNEL_CHATTING_BLACKLIST.class, 0x12C, idSet);//need to add
        addPacketOpcode(S_GOTCHA_NOTIFY.class, 0x13A, idSet);//need to add
        addPacketOpcode(S_STORE_PURCHASE_INFO.class, 0x146, idSet);
        addPacketOpcode(S_USER_STORY_BOOK_FINISHED_LIST_LOAD.class, 0x14B, idSet);//need to add
        addPacketOpcode(S_USER_STORY_BOOK_REGISTERED_ITEM_LIST_.class, 0x14C, idSet);//need to add
        addPacketOpcode(S_USER_STORY_BOOK_UPDATE_RESULT.class, 0x14D, idSet);//need to add
        addPacketOpcode(S_USER_STORY_BOOK_RESET.class, 0x14E, idSet);//need to add
        addPacketOpcode(S_GUILD_LIST.class, 0x14F, idSet);
        addPacketOpcode(S_USER_APPLY_JOIN_GUILD_INFO.class, 0x150, idSet);
        addPacketOpcode(S_GUILD_JOIN_APPILCANT_LIST.class, 0x151, idSet);
        addPacketOpcode(S_ADD_GUILD_JOIN_APPLICANT.class, 0x152, idSet);
        addPacketOpcode(S_DELETE_GUILD_JOIN_APPLICANT.class, 0x153, idSet);
        addPacketOpcode(S_GUILD_DONATION_REWARD.class, 0x157, idSet);
        addPacketOpcode(S_GUILD_DONATION_LIST.class, 0x158, idSet);
        addPacketOpcode(S_GUILD_CRAFT_LIST.class, 0x159, idSet);
        addPacketOpcode(S_PLAYER_GUILD_COINS.class, 0x164, idSet);
        //S_PROTOCOL_MAX 0x166
        addPacketOpcode(SM_QUNA.class, 0x11B, idSet);
        addPacketOpcode(SM_CUSTOM_PACKET.class, 99999, idSet); // fake packet
    }

    static int getOpcode(Class<? extends AionServerPacket> packetClass) {
        Integer opcode = opcodes.get(packetClass);
        if (opcode == null) {
            throw new IllegalArgumentException("There is no opcode for " + packetClass + " defined.");
        }
        return opcode;
    }

    private static void addPacketOpcode(Class<? extends AionServerPacket> packetClass, int opcode, Set<Integer> idSet) {
        if (opcode < 0) {
            return;
        }
        if (idSet.contains(opcode)) {
            throw new IllegalArgumentException(String.format("There already exists another packet with id 0x%02X", opcode));
        }
        idSet.add(opcode);
        opcodes.put(packetClass, opcode);
    }
}