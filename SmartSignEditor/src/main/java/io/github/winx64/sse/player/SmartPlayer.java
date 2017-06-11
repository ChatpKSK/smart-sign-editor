/*
 *   SmartSignEditor - Edit your signs with style
 *   Copyright (C) WinX64 2013-2017
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
package io.github.winx64.sse.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import io.github.winx64.sse.tool.ToolType;

/**
 * Data class to store the player information related to SmartSignEditor
 * 
 * @author Lucas
 *
 */
public final class SmartPlayer {

	private final Player player;

	private ToolType toolType;

	private String lineBuffer;
	private String[] signBuffer;

	public SmartPlayer(Player player) {
		this.player = player;
		this.toolType = ToolType.EDIT;

		this.lineBuffer = null;
		this.signBuffer = null;
	}

	/**
	 * Gets the Player linked to this SmartPlayer
	 * 
	 * @return The Player instance
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the unique id of this player
	 * 
	 * @return The UUID
	 */
	public UUID getUniqueId() {
		return player.getUniqueId();
	}

	/**
	 * Gets the name of this player
	 * 
	 * @return The name
	 */
	public String getName() {
		return player.getName();
	}

	/**
	 * Gets the current tool type this player is using
	 * 
	 * @return The current tool
	 */
	public ToolType getToolType() {
		return toolType;
	}

	/**
	 * Sets the current tool type this player is using
	 * 
	 * @param toolMode
	 *            The new tool
	 */
	public void setToolMode(ToolType toolMode) {
		this.toolType = toolMode;
	}

	/**
	 * Gets the last copied line
	 * 
	 * @return The line
	 */
	public String getLineBuffer() {
		return lineBuffer;
	}

	/**
	 * Sets the last copied line
	 * 
	 * @param lineBuffer
	 *            The new line
	 */
	public void setLineBuffer(String lineBuffer) {
		this.lineBuffer = lineBuffer;
	}

	/**
	 * Gets the last copied sign text
	 * 
	 * @return The entire sign text
	 */
	public String[] getSignBuffer() {
		return signBuffer;
	}

	/**
	 * Sets a specific line of the copied sign text
	 * 
	 * @param line
	 *            The line
	 * @param text
	 *            The new text
	 */
	public void setSignBuffer(int line, String text) {
		if (signBuffer == null) {
			this.signBuffer = new String[4];
		}
		this.signBuffer[line] = text;
	}

	/**
	 * Sets the last copied sign text
	 * 
	 * @param signBuffer
	 *            The new sign text
	 */
	public void setSignBuffer(String[] signBuffer) {
		this.signBuffer = signBuffer;
	}
}
