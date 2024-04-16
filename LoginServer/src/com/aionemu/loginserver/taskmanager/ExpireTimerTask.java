/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package com.aionemu.loginserver.taskmanager;


import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.model.AccountSielEnergy;
import javolution.util.FastMap;

import java.util.Iterator;
import java.util.Map;

/**
 * @author soulxj
 */
public class ExpireTimerTask extends AbstractPeriodicTaskManager {
    private FastMap<AccountSielEnergy, Account> expirables = new FastMap<>(2000);

    public ExpireTimerTask() {
        super(200);
    }

    public static ExpireTimerTask getInstance() {
        return SingletonHolder._instance;
    }

    public void addTask(AccountSielEnergy expirable, Account account) {
        writeLock();
        try {
            expirables.put(expirable, account);
        } finally {
            writeUnlock();
        }
    }

    public void removeAccount(Account account) {
        writeLock();
        try {
            for (Map.Entry<AccountSielEnergy, Account> entry : expirables.entrySet()) {
                if (entry.getValue() == account)
                    expirables.remove(entry.getKey());
            }
        } finally {
            writeUnlock();
        }
    }

    @Override
    public void run() {
        writeLock();
        try {
            long timeNow = System.currentTimeMillis();
            for (Iterator<Map.Entry<AccountSielEnergy, Account>> i = expirables.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry<AccountSielEnergy, Account> entry = i.next();
                AccountSielEnergy expirable = entry.getKey();
                expirable.onBeat(timeNow,entry.getValue());
            }
        } finally {
            writeUnlock();
        }
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final ExpireTimerTask _instance = new ExpireTimerTask();
    }
}