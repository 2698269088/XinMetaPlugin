/*
 *   Copyright (C) 2024-2026 huangdihd
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xin.bbtt.meta.listeners;

import org.geysermc.mcprotocollib.network.Session;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.title.ClientboundSetTitleTextPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundChatCommandPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xin.bbtt.mcbot.Bot;
import xin.bbtt.mcbot.LangManager;
import xin.bbtt.mcbot.events.LoginSuccessEvent;
import xin.bbtt.mcbot.events.SendLoginCommandEvent;
import xin.bbtt.mcbot.events.SendRegisterCommandEvent;

public class AutoLoginListener extends SessionAdapter {
    private static final Logger log = LoggerFactory.getLogger(AutoLoginListener.class.getSimpleName());
    public static int join_button_slot = 2;
    public static boolean login = false;
    public static Long last_login_time = System.currentTimeMillis();

    @Override
    public void packetReceived(Session session, Packet packet) {
        if (packet instanceof ClientboundSetTitleTextPacket titlePacket) login(titlePacket);
    }

    private void login(ClientboundSetTitleTextPacket titlePacket) {
        if (titlePacket.toString().contains("登陆成功")) {
            LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent();
            Bot.INSTANCE.getPluginManager().events().callEvent(loginSuccessEvent);
            log.info(LangManager.get("xinmeta.login.successful"));
            login = true;
            AutoJoinListener.last_action_time = System.currentTimeMillis();
            // 记录登录成功时间，用于10秒延迟检查
            AutoJoinListener.login_success_time = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - last_login_time < 2000) return;
        if (login) return;
        last_login_time = System.currentTimeMillis();
        if (titlePacket.toString().contains("注册")) {
            String registerCommand = "reg " + Bot.INSTANCE.getConfig().getConfigData().getAccount().getPassword() + " " + Bot.INSTANCE.getConfig().getConfigData().getAccount().getPassword();
            SendRegisterCommandEvent registerCommandEvent = new SendRegisterCommandEvent(registerCommand);
            Bot.INSTANCE.getPluginManager().events().callEvent(registerCommandEvent);
            log.info("reg");
            if (!registerCommandEvent.isDefaultActionCancelled())
                Bot.INSTANCE.getSession().send(new ServerboundChatCommandPacket(registerCommandEvent.getCommand()));
        }
        else {
            String loginCommand = "l " + Bot.INSTANCE.getConfig().getConfigData().getAccount().getPassword();
            SendLoginCommandEvent loginCommandEvent = new SendLoginCommandEvent(loginCommand);
            log.info("login");
            if (!loginCommandEvent.isDefaultActionCancelled())
                Bot.INSTANCE.getSession().send(new ServerboundChatCommandPacket(loginCommandEvent.getCommand()));
        }
    }


}
