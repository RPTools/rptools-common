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
 * Enumeration of permission levels.
 */
public enum PermissionLevel {
	/** 
	 * Player is a GM who should be able to do everything. If your tool does not
	 * have the concept of permissions you will want to use this level so that
	 * nothing is restricted.
	 */
	GM { public boolean hasAtLeastPermission(PermissionLevel level) { return true; } },
	/** Normal everyday player that is limited in what they can do. */
	PLAYER { public boolean hasAtLeastPermission(PermissionLevel level) { return  level != GM; } },
	/** Observer who generally can't control anything, */
	OBSERVER { public boolean hasAtLeastPermission(PermissionLevel level) { return level == OBSERVER; } };
	
	
	/**
	 * Checks if the permission level has a certain permission level or higher.
	 * 
	 * @param level The permission level to check for.
	 * 
	 * @return true if this permission level is the same or higher.
	 */
	public abstract boolean hasAtLeastPermission(PermissionLevel level);
}
