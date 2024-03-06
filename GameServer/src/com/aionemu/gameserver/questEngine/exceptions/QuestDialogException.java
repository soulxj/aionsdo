/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.questEngine.exceptions;

import com.aionemu.gameserver.questEngine.model.QuestEnv;

/**
 * OnDialogEvent exception
 * 
 * @author vlog
 */
public class QuestDialogException extends RuntimeException {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -4323594385872762590L;

	public QuestDialogException(QuestEnv env) {
		super(new String("Info: QuestID: " + env.getQuestId() + ", DialogID: " + env.getDialogId()
			+ env.getVisibleObject().getObjectTemplate().getTemplateId() == null ? "0" : ", TargetID: "
			+ env.getVisibleObject().getObjectTemplate().getTemplateId() + "."
			+ env.getPlayer().getQuestStateList().getQuestState(env.getQuestId()) == null ? " QuestState not initialized."
			: " QuestState: " + env.getPlayer().getQuestStateList().getQuestState(env.getQuestId()).getStatus().toString()
				+ env.getPlayer().getQuestStateList().getQuestState(env.getQuestId()).getQuestVarById(0)));
	}
}
