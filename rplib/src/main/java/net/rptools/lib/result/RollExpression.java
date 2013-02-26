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
package net.rptools.lib.result;


/**
 * RollExpression represents dice roll expressions in the scripting language.
 * The RollExpression contains the "roll string" which details which dice
 * and how many to roll as well as any flags record what the user expects
 * returned. 
 * 
 * If {@link #isVerbose()} returns true then the user is interested in 
 * the individual dice rolls.
 * 
 * If {@link #isSum()} returns true then the "result" of the dice roll
 * should be a single number so that it can be used in mathematical functions.
 * Even though a single value should be returned the user may still be
 * interested in seeing the individual values. 
 * 
 */
public class RollExpression {
	/** The dice to be rolled. */
	private final String rollString;
	/** Should the details of the rolls be kept. */
	private final boolean verbose;
	/** Should the "return value" of a detailed roll be the sum of the rolls. */
	private final boolean sum;
	

	/**
	 * Creates a RollExpression for the given roll string which should return
	 * only the result of the roll.
	 * 
	 * @param rollStr The string describing the roll
	 * @return a RollExpression.
	 */
	public static RollExpression getRollExpression(String rollStr) {
		return new RollExpression(rollStr, false, true);
	}
	/**
	 * Creates a RollExpression for the given roll string which should return
	 * the individual dice rolls as the result of the roll.	
	 * 
	 * @param rollStr The string describing the dice to roll.
	 * @return a Roll Expression.
	 */
	public static RollExpression getDetailedRollExpression(String rollStr) {
		return new RollExpression(rollStr, true, false);
	}

	/**
	 * Creates a RollExpression for the given roll string which should return 
	 * a single value as the result, but which the user is interested in the
	 * individual rolls.
	 * 
	 * @param rollStr The string describing the dice to roll.
	 * @return a Roll Expression.
	 */
	public static RollExpression getDetailedSummedRollExpression(String rollStr) {
		return new RollExpression(rollStr, true, true);
	}

	/**
	 * Creates a RollExpression.
	 * 
	 * @param rollStr The string that describes the dice to roll.
	 * @param verboseFlag Is the user interested in the individual rolls.
	 * @param sumFlag should a single result be returned even if user is interested 
	 *        in individual rolls.
	 */
	RollExpression(String rollStr, boolean verboseFlag, boolean sumFlag) {
		rollString = rollStr;
		verbose = verboseFlag;
		sum = sumFlag;
	}


	/**
	 * Returns the roll string that describes the dice to roll.
	 * 
	 * @return the roll string.
	 */
	public String getRollString() {
		return rollString;
	}

	/**
	 * Checks if the user is interested in the verbose details for the rolls.
	 * 
	 * @return true if the user is interested in the verbose details.
	 */
	public boolean isVerbose() {
		return verbose;
	}
	
	/**
	 * Checks if the roll should return a single result even though the user is
	 * interested in the detailed results.
	 * 
	 * @return true if the the roll should return a single result.
	 */
	public boolean isSum() {
		return sum;
	}
	
	
}
