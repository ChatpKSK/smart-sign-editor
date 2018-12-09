/*
 *   SmartSignEditor - Edit your signs with style
 *   Copyright (C) WinX64 2013-2018
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
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.winx64.sse.handler.versions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.base.Charsets;

import io.github.winx64.sse.handler.VersionAdapter;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.ChatComponentText;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_8_R2.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import net.minecraft.server.v1_8_R2.TileEntitySign;
import net.minecraft.server.v1_8_R2.World;

public final class VersionAdapter_1_8_R2 implements VersionAdapter {

    @Override
    public void updateSignText(Player player, Sign sign, String[] text) {
        Location loc = sign.getLocation();
        ChatComponentText[] chatComponent = new ChatComponentText[4];
        PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;

        for (int i = 0; i < 4; i++) {
            chatComponent[i] = new ChatComponentText(text[i]);
        }
        conn.sendPacket(new PacketPlayOutUpdateSign(null, new BlockPosition(loc.getX(), loc.getY(), loc.getZ()),
                chatComponent));
    }

    @Override
    public void openSignEditor(Player player, Sign sign) {
        Location loc = sign.getLocation();
        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) nmsPlayer.world.getTileEntity(pos);
        PlayerConnection conn = nmsPlayer.playerConnection;

        tileEntitySign.isEditable = true;
        tileEntitySign.a(nmsPlayer);
        conn.sendPacket(new PacketPlayOutOpenSignEditor(pos));
    }

    @Override
    public boolean isSignBeingEdited(Sign sign) {
        Location loc = sign.getLocation();
        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        World world = ((CraftWorld) sign.getWorld()).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) world.getTileEntity(pos);

        return tileEntitySign.isEditable;
    }

    @Override
    public boolean shouldProcessEvent(PlayerInteractEvent event) {
        return true;
    }
}
