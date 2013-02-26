/*
w * Licensed under the Apache License, Version 2.0 (the "License");
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
package net.rptools.parser;

import net.rptools.lib.permissions.PlayerPermissions;
import net.rptools.parser.symboltable.SymbolTable;

/**
 * Represents the context information that the script runs with.
 *
 */
public class ScriptContext {

	/** The player permissions for this context. */
	private final PlayerPermissions playerPermissions;
	
	/** The symbol table used for execution. */
	private final SymbolTable symbolTable;

	
	/** 
	 * Any user data that is passed to the script engine.
	 * This data will be made available to functions that
	 * are run via an API.
	 */
	private final Object userData;
	
	/**
	 * Is the script being run in debug mode.
	 */
	private final boolean debug;
	
	/**
	 * Creates a new ScriptContext object. 
	 * 
	 * @param permissions The player permissions that the script will execute with.
	 * @param symTable The symbol table used to resolve symbols.
	 * @param uData Any data you need made available to functions.
	 */
	ScriptContext(PlayerPermissions permissions, SymbolTable symTable, Object uData, boolean debugFlag) {
		playerPermissions = permissions;
		symbolTable = symTable;
		userData = uData;
		debug = debugFlag;
	}

	/**
	 * Returns the player permissions used for the evaluation of the script.
	 * 
	 * @return the player permission used.
	 */
	public PlayerPermissions getPlayerPermissions() {
		return playerPermissions;
	}

	/**
	 * Returns the symbol table that will be used for the evaluation of the script.
	 * 
	 * @return the symbol table used.
	 */
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	/**
	 * Returns the user data that will be available via API in the evaluation of the script.
	 * 
	 * @return the user data.
	 */
	public Object getUserData() {
		return userData;
	}

	/**
	 * Checks if the script will be evaluated in debug mode.
	 * 
	 * @return true if debug mode is on.
	 */
	public boolean isDebug() {
		return debug;
	}
	
	
}
