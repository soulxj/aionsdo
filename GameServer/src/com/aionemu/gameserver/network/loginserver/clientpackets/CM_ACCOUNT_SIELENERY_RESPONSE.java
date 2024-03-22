/**
 * This file is part of aion-emu <aion-emu.com>.
 * <p>
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.model.account.AccountSielEnergy;
import com.aionemu.gameserver.model.account.SielEnergyType;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;

import java.sql.Timestamp;

/**
 * @author -soulxj-
 */
public class CM_ACCOUNT_SIELENERY_RESPONSE extends LsClientPacket {


    private int accountId;
    private int type;
    private long chargeTime;
    private long end;

    private long remain;

    private boolean isPush;

    public CM_ACCOUNT_SIELENERY_RESPONSE(int opCode) {
        super(opCode);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readImpl() {
        isPush = readD() == 1;
        accountId = readD();
        type = readD();
        chargeTime = readQ();
        end = readQ();
        remain = readQ();

    }

    @Override
    public String toString() {
        return "CM_ACCOUNT_SIELENERY_NOTITY{" +
                "accountId=" + accountId +
                ", type=" + type +
                ", chargeTime=" + chargeTime +
                ", end=" + end +
                ", remain=" + remain +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runImpl() {
        //System.out.println(this.toString());
        AccountSielEnergy accountSielEnergy = new AccountSielEnergy(SielEnergyType.getSielTypeById(type), new Timestamp(chargeTime), end == 0 ? null : new Timestamp(end), remain);
        LoginServer.getInstance().updateAccountSielEnergy(accountId, isPush,accountSielEnergy);
    }
}
