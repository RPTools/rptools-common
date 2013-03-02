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

import java.util.*;


import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.permissions.PermissionLevel;
import net.rptools.parser.EvaluationPermissionException;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * FunctionDispatcher performs the task of mapping the arguments to parameters
 * for a script function then executing the function. There are two name spaces
 * for functions, built in functions and user defined functions. When trying to
 * match a function the user defined functions are checked first before the 
 * built in functions so that additions to built in functions will not cause
 * user scripts and libraries to stop working. 
 * 
 * If the name of the function to execute begins with {@link #BUILT_IN_PREFIX}
 * then the attempt to bypass user defined functions is bypassed. 
 *
 * Since function names can contain '.' they should be used to provide name
 * space like functionality.
 */
public final class FunctionDispatcher {
	/** The mapping between function name and FunctionDefinition for built in functions. */
	// TODO: private final Map<String, FunctionDefinition> builtinFunctionNames = new HashMap<>();
	
	/** The mapping between FunctionDefinition and the Function object for built in functions. */
	// TODO: private final Map<FunctionDefinition, ScriptFunction> builtinFunctions = new HashMap<>();
	
	/** The mapping between function name and FunctionDefinition for user defined functions. */
	// TODO: private final Map<String, FunctionDefinition> definedFunctionNames = new HashMap<>();
	
	/** The mapping between function name and FunctionDefinition for user defined functions. */
	// TODO: private final Map<FunctionDefinition, ScriptFunction> definedFunctions = new HashMap<>();
	
	/** The singleton instance for the FunctionDispatcher. */
	private static final FunctionDispatcher INSTANCE = new FunctionDispatcher();
	
	/** 
	 * The "namespace" prefix used to specify the function call should not attempt to match
	 * user defined functions.
	 */
	public static final String BUILT_IN_PREFIX = "net.rptools.";
	
	/**
	 * Creates a FunctionDispatcher object.
	 */
	// Private to stop instantiation.
	private FunctionDispatcher() {
	}
	
	/**
	 * Returns the function object for the specified function name. This method will search the 
	 * list of user defined functions before searching the built in functions, so if it is defined
	 * in both places then the user defined function will be found first, unless the prefix is 
	 * "net.rptools." in which case it will search only built in functions.
	 * 
	 * @param functionName The name of the to get the function.
	 * 
	 * @return The function object.
	 */
	private  ScriptFunction getFunction(String  functionName) {
		assert functionName != null : "Function name can not be null";
		ScriptFunction func = null;
		
		if (functionName.startsWith(BUILT_IN_PREFIX)) {
			func = FunctionManager.getBuiltinFunction(functionName.substring(BUILT_IN_PREFIX.length()));
		} else if (FunctionManager.containsUserFunction(functionName)){
			func = FunctionManager.getUserFunction(functionName);
		} else {
			func = FunctionManager.getBuiltinFunction(functionName);
		}
		
		return func;
	}

	/**
	 * Gets an instance of the FunctionDispatcher class.
	 * @return an instance of the FuncitonDispatcher class.
	 */
	public static FunctionDispatcher getFunctionDispatcher() {
		return INSTANCE;
	}


	/**
	 * Calls the named function with the specified arguments. 
	 * 
	 * @param functionName The name of the function to call.
	 * @param args The arguments to call the function with.
	 * @param context The script context to evaluate with.
	 * 
	 * @return The result of calling the function.
	 * 
	 * @throws ExpressionEvaluatorException if an error occurs during the function call or the 
	 * 		   function doesn't exist or the arguments don't match the function parameters.
	 * 
	 * @throws EvaluationPermissionException if the context does not contain the required
	 * 		   permission to call the function.
	 * 
	 * @throws NullPointerException if any of the arguments are null.
	 */
	public DataValue call(String functionName, ArgumentList args, ScriptContext context)
                throws ExpressionEvaluatorException {
		if (functionName == null) {
			throw new NullPointerException("Function name can not be null in function call.");
		}
		
		if (args == null) {
			throw new NullPointerException("Argument list can not be null in function call.");
		}
		
		if (context == null) {
			throw new NullPointerException("Context can not be null in function call.");
		}
		
		ScriptFunction function = getFunction(functionName);
		if (function == null) {
			throw new ExpressionEvaluatorException("Unknown function " + functionName);
		}
		
		PermissionLevel plevel = context.getPlayerPermissions().getPermissionLevel();
		PermissionLevel requiredLevel = FunctionManager.getFunctionPermission(function);
		
		if (plevel.hasAtLeastPermission(requiredLevel) == false) {
			throw new EvaluationPermissionException("You do not have permission to call " + functionName);
		}
		
		
		Map<String, DataValue> argMap = resolveArguments(function.getDefinition(), args);
		
		DataValue res;
		try {
			res = function.call(context, argMap);
            res = function.getDefinition().getReturnType().coerce(res);
        } catch (ScriptFunctionException es) {
			throw new ExpressionEvaluatorException(es.getMessage(), es);
		} catch (Exception e) {
            e.printStackTrace();
			throw new ExpressionEvaluatorException(e.getMessage(), e);
		}
		
		return res;
		
	}
	
	/**
	 * Returns a map containing the positional arguments in the argument list for the function
	 * mapped to their parameter names. 
	 * 
	 * @param def The function definition to match the arguments and parameters for.
	 * @param args The arguments to attempt to match to the parameter list.
	 * 
	 * @return The mapping between the arguments and the parameters.
	 * 
	 * @throws IllegalArgumentException if there are too many arguments to match.
	 */
	private Map<String, DataValue> resolvePositionalArguments(FunctionDefinition def, ArgumentList args) {
		assert def != null : "Function definiotn is null";
		assert args != null : "Argument list is null";
			
		final Map<String, DataValue> argMap = new HashMap<>();
		final Iterator<FunctionParameter> iter = def.parameters().iterator();	
		
		List<DataValue> consumerList = new ArrayList<>();
		for (DataValue dv : args.getPositionalArguments()) {
			if (iter.hasNext() == false) {
				if (def.hasPositionalArgumentConsumer()) {
					consumerList.add(dv);
				} else {
					throw new IllegalArgumentException("Too many parameters for function " + def.name());
				}
			} else {
				FunctionParameter param = iter.next();
				if (param.consumesRemaining()) {
					consumerList.add(dv);
				} else {
					argMap.put(param.name(), param.dataType().coerce(dv));
				}
			}
		}

        if (def.hasPositionalArgumentConsumer()) {
		    argMap.put(def.getPositionalArgumentConsumer().name(), DataValueFactory.listValue(consumerList));
        }

		return argMap;
		
	}
	
	/**
	 * Returns the mapping between function arguments and parameters. 
	 * 
	 * @param def The function definition to try map the arguments to parameters for.
	 * @param args The arguments to try map to the parameter list.
	 * @return the mapping between arguments and parameters.
	 * 
	 * @throws IllegalArgumentException if the arguments do not map to the parameters.
	 */
	private Map<String, DataValue> resolveArguments(FunctionDefinition def, ArgumentList args) {
				
		if (def.parameters().isEmpty()) {
			if (args.getPositionalArguments().size() > 0 || args.getNamedArguments().size() > 0) {
				throw new IllegalArgumentException("Function " + def.name() + " does not accept any parameters.");
			}
		}
		
		// First turn positional parameters into named parameters
		Map<String, DataValue> argMap = resolvePositionalArguments(def, args);

        // Unknown parementers that will be assigned to a named consumer if one exists.
        Map<String, DataValue> unknownArgs = new LinkedHashMap<>();

        // Next step is to add any named parameters.
		for (String namep : args.getNamedArguments().keySet()) {
			if (argMap.containsKey(namep)) {
				throw new IllegalArgumentException("Call to function " + def.name() + " defines argument " + namep +
                        " more than once.");
			}
			
			if (def.isValidParameterName(namep) == false) {
				throw new IllegalArgumentException("Invalid parameter name " + namep + " for function " + def.name());
			}

			DataValue dv = args.getNamedArguments().get(namep);
			
			// If the parameter exists (i.e. its not a wild card) then we have to coerce the value to 
			// what the function expects.
			FunctionParameter fp = def.getParameter(namep);
			if (fp != null) {
				dv = fp.dataType().coerce(dv);
                argMap.put(namep, dv);
            } else {  // Add to unknown parameter list
                unknownArgs.put(namep, dv);
            }
		}

        // If there is a named parameter consumer then consume any unknown arguments.
        if (def.hasNamedArgumentConsumer()) {
            argMap.put(def.getNamedArgumentConsumer().name(), DataValueFactory.dictionaryValue(unknownArgs));
        }
		
		// Finally add any parameters with default arguments.
		for (FunctionParameter p : def.parameters()) {
			if (p.hasDefaultValue()) {
				if (argMap.containsKey(p.name()) == false) {
					argMap.put(p.name(), p.getDefaultValue());
				}
			}
		}
	
		// At this point the arguments map should contain all the defined parameters for the function.
		for (FunctionParameter p : def.parameters()) {
			if (argMap.containsKey(p.name()) == false) {
				throw new IllegalArgumentException("Paramenter " + p.name() + " missing from call to function " +
                        def.name());
			}
		}
		return argMap;
	}
}
