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
package net.rptools.parser.tree;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.result.RollExpression;
import net.rptools.parser.ScriptContext;

/**
 * Represents the ScriptTreeNode that performs dice rolls.
 */
class RollNode implements ScriptTreeNode {

	/** The name of the dice roll in the symbol table. */
	private final String rollName;
	
	/**
	 * Creates a new RollNode.
	 * 
	 * @param name The name of the dice roll in the symbol table.
	 */
	public RollNode(String name) {
		assert name != null : "Roll name can not be null.";
		
		rollName = name;
	}
	
	
	@Override
	public DataValue evaluate(ScriptContext context) {
        DataValue rollRes = context.getSymbolTable().resolveRoll(rollName);
        context.getSymbolTable().addRollResult(rollName, rollRes);
        		
        DataValue result = null;
        
        // TODO 
        RollExpression rollExpr = context.getSymbolTable().getRollExpression(rollName);
        
        if (rollExpr.isVerbose()) {
        	if (rollExpr.isSum()) {
        		result = rollRes;
        	} else {
        		result = rollRes.asListValue();
        	}
        } else {
        	result = rollRes;
        }
                
        return result;
	}

}
