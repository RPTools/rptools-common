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
package net.rptools.parser.symboltable;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ExpressionEvaluatorException;

/**
 * The interface implemented by objects that will provide the functionality
 * for prompted values.
 */
public interface PromptResolver {
	
	/**
	 * Returns the data value after prompting.
	 * 
	 * @param name The name of the prompt.
	 * 
	 * @return the data value.
	 * 
	 * @throws NullPointerException if the name is null
	 * @throws ExpressionEvaluatorException if the prompt was canceled.
	 */
	public DataValue promptForValue(String name);
	
	/**
	 * Returns the data value after prompting.
	 * 
	 * @param name The name of the prompt.
	 * @param description the descriptive text for the prompt.
	 * 
	 * @return the data value.
	 * 
	 * @throws NullPointerException if the name is null
	 * @throws ExpressionEvaluatorException if the prompt was canceled.
	 */
	public DataValue promptForValue(String name, String description);

}
