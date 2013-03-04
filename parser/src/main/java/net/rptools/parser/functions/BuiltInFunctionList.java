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
package net.rptools.parser.functions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.rptools.parser.functions.dictionary.DictFunction;
import net.rptools.parser.functions.dictionary.DictGetFunction;
import net.rptools.parser.functions.dictionary.DictRemoveFunction;
import net.rptools.parser.functions.dictionary.DictSetFunction;
import net.rptools.parser.functions.list.*;

/**
 * Contains a list of the functions that should be loaded as built in functions.
 *
 */
public class BuiltInFunctionList {
	
	/** Singleton instance. */
	private static final BuiltInFunctionList INSTANCE = new BuiltInFunctionList();
	
	/** List of built in script functions. */
	private final Set<ScriptFunction> scriptFunctions = new HashSet<>();
	
	/**
	 * Returns an instance of BuiltInFunctionList.
	 * 
	 * @return the built in function list.
	 */
	public static BuiltInFunctionList getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Private construction so that an instance of the class can not be created.
	 */
	private BuiltInFunctionList() {
	}

    /**
     * Initialize the built in functions.
     */
    private void lazyInit() {
        addBuiltInFunction(ListFunction .getListFunction());
        addBuiltInFunction(new FunctionAlias("list.create", ListFunction.getListFunction()));
        addBuiltInFunction(ListUnionFunction.getListFunction());
        addBuiltInFunction(ListIntersectionFunction.getListFunction());
        addBuiltInFunction(ListMinusFunction.getListFunction());
        addBuiltInFunction(ListShuffleFunction.getListFunction());
        addBuiltInFunction(DictFunction.getDictFunction());
        addBuiltInFunction(new FunctionAlias("dict.create", DictFunction.getDictFunction()));
        addBuiltInFunction(DictSetFunction.getDictSetFunction());
        addBuiltInFunction(DictGetFunction.getDictGetFunction());
        addBuiltInFunction(DictRemoveFunction.getDictRemoveFunction());
        addBuiltInFunction(ListCountFunction.listCountFunction());
        addBuiltInFunction(new FunctionAlias("list.count", ListCountFunction.listCountFunction()));

    }
	/**
	 * Adds a function to the list of the built in functions.
	 * 
	 * @param func The function to add.
	 * 
	 * @throws NullPointerException if func is null.
	 * @throws IllegalArgumentException if the function has already been added. 
	 */
	private void addBuiltInFunction(ScriptFunction func) {		
		if (scriptFunctions.contains(func)) {
			throw new IllegalArgumentException("Built In function list already contains function " + func.getDefinition().name());
		}
		
		if (func == null) {
			throw new NullPointerException("Function to add can not be null");
		}
		
		scriptFunctions.add(func);
	}
	
	/**
	 * Gets the built in functions that have been added to the list.
	 * 
	 * @return the list of functions.
	 */
	public  Collection<ScriptFunction> getBuiltInFunctions() {
        if (scriptFunctions.size() == 0) {
            lazyInit();
        }
		return Collections.unmodifiableCollection(scriptFunctions);
	}
}
