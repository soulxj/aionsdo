package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Metaverse
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcSPHealInstantEffect")
public class ProcSPHealInstantEffect extends EffectTemplate {

	@Override
	public void calculate(Effect effect) {
		super.calculate(effect);
	}

	@Override
	public void applyEffect(Effect effect) {
		Creature effector = effect.getEffector();
		
		int currentSp = ((Player) effector).getCommonData().getSp();
		int maxStatsSp = ((Player) effector).getGameStats().getMaxSp().getCurrent();
		
		int val = value;
		
		int finalSp = val;
		
		finalSp = maxStatsSp - currentSp < finalSp ? (maxStatsSp - currentSp) : finalSp;
		
		((Player)effector).getCommonData().addSp(finalSp);
	}
}
