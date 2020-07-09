package io.github.winx64.sse.handler.versions;

import io.github.winx64.sse.data.SignData;
import io.github.winx64.sse.handler.VersionAdapter;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import net.minecraft.server.v1_11_R1.TileEntitySign;
import net.minecraft.server.v1_11_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Sign;

import java.util.Objects;

public final class VersionAdapter_1_11_R1 implements VersionAdapter {

    @Override
    public void updateSignText(Player player, Location location, String[] text) {
        BlockPosition pos = new BlockPosition(location.getX(), location.getY(), location.getZ());
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) Objects.requireNonNull(nmsPlayer.world.getTileEntity(pos));
        PlayerConnection conn = nmsPlayer.playerConnection;
        IChatBaseComponent[] oldSignText = new IChatBaseComponent[4];

        for (int i = 0; i < 4; i++) {
            oldSignText[i] = tileEntitySign.lines[i];
            tileEntitySign.lines[i] = new ChatComponentText(text[i]);
        }
        conn.sendPacket(tileEntitySign.getUpdatePacket());
        System.arraycopy(oldSignText, 0, tileEntitySign.lines, 0, 4);
    }

    @Override
    public void openSignEditor(Player player, Location location) {
        BlockPosition pos = new BlockPosition(location.getX(), location.getY(), location.getZ());
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) Objects.requireNonNull(nmsPlayer.world.getTileEntity(pos));
        PlayerConnection conn = nmsPlayer.playerConnection;

        tileEntitySign.isEditable = true;
        tileEntitySign.a(nmsPlayer);
        conn.sendPacket(new PacketPlayOutOpenSignEditor(pos));
    }

    @Override
    public boolean shouldProcessEvent(PlayerInteractEvent event) {
        return event.getHand() == EquipmentSlot.HAND;
    }

    @Override
    public boolean isSign(Block block) {
        return block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN;
    }

    @Override
    public SignData getSignData(Block block) {
        org.bukkit.block.Sign blockState = (org.bukkit.block.Sign) block.getState();
        Sign materialData = (Sign) block.getState().getData();
        return new SignData(blockState.getLines(), block.getLocation(), materialData.getFacing(),
                materialData.isWallSign());
    }

    @Override
    public boolean isSignBeingEdited(Block block) {
        Location loc = block.getLocation();
        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        World world = ((CraftWorld) block.getWorld()).getHandle();
        TileEntitySign tileEntitySign = (TileEntitySign) Objects.requireNonNull(world.getTileEntity(pos));

        return tileEntitySign.isEditable;
    }
}
