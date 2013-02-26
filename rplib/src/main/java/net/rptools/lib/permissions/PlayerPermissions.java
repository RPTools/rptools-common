/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package net.rptools.lib.permissions;

/**
 * Represents the permission level that the player has.
 *
 */
public class PlayerPermissions {
	

	/**
	 * The permission to use when no permissions are specified.
	 */
	private static final PlayerPermissions UNSPECIFIED_PLAYER_PERMISSION = 
			new PlayerPermissions(PermissionLevel.OBSERVER);

	/** The permission level. */
	private final PermissionLevel permissionLevel;
	/** The name of the team. */
	private final String teamName;

	
	
	/**
	 * Creates a new PlayerPermission object.
	 * 
	 * @param level The permission level.
	 * @param team The team that the player belongs to.
	 * 
	 * @throws NullPointerException if level is null.
	 */
	public PlayerPermissions(PermissionLevel level, String team) {
		permissionLevel = level;
		teamName = team;
	}
	
	/**
	 * Creates a new PlayerPermission object.
	 * 
	 * @param level The permission level,
	 * 
	 * @throws NullPointerException if level is null.
	 */
	public PlayerPermissions(PermissionLevel level) {
		this(level, null);
	}
	
	/**
	 * Returns the PlayerPermission object to use when there is no specific 
	 * permission level.
	 * 
	 * @return the permission level to use. 
	 */
	public static PlayerPermissions getUnspecifiedPlayerPermissions() {
		return UNSPECIFIED_PLAYER_PERMISSION;
	}

	/**
	 * Returns the PermissionLevel for this PlayerPermission.
	 * 
	 * @return the permission level.
	 */
	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}

	/**
	 * Returns the team name for this permission.
	 * 
	 * @return the team name.
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * Checks to see if the player permissions are team based.
	 * 
	 * @return true if team based.
	 */
	public boolean isTeamBased() {
		return teamName != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((permissionLevel == null) ? 0 : permissionLevel.hashCode());
		result = prime * result
				+ ((teamName == null) ? 0 : teamName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerPermissions other = (PlayerPermissions) obj;
		if (permissionLevel != other.permissionLevel)
			return false;
		if (teamName == null) {
			if (other.teamName != null)
				return false;
		} else if (!teamName.equals(other.teamName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerPermissions [permissionLevel=" + permissionLevel
				+ ", teamName=" + teamName + "]";
	}

	
}
