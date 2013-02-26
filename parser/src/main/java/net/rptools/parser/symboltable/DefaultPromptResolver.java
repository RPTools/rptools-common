package net.rptools.parser.symboltable;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;

/**
 * The default (and pretty useless) implementation of a PropertyResolver.
 * You really don't want this implementation as it always returns DataValue
 * of 1 (unless you don't want prompting, then maybe you do want this.
 *
 */
public class DefaultPromptResolver implements PromptResolver {

	@Override
	public DataValue promptForValue(String name) {
		return DataValueFactory.longValue(1); // ho hum how boring!
	}

	@Override
	public DataValue promptForValue(String name, String description) {
		return DataValueFactory.longValue(1); // ho hum how boring!
	}

}
