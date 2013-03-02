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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.permissions.PermissionLevel;
import net.rptools.lib.permissions.PlayerPermissions;

/**
 * FunctionDefinitionBuilder is used to build an immutable class that implements the
 * {@link FunctionDefinition} interface.
 *
 */
public class FunctionDefinitionBuilder {

	/** The name of the function definition being built. */
	private String name;
	
	/** The parameters for the function definition being built. */
	private final List<FunctionParameter> functionParameters = new ArrayList<>();
	
	/** 
	 * Flag that indicated if the function definition being built contains a 
	 * a parameter that will consume positional arguments.
	 */
	private boolean hasListVarargs = false;
	
	/** 
	 * Flag that indicated if the function definition being built contains a 
	 * a parameter that will consume named arguments.
	 */
	private boolean hasDictionaryVarargs = false;

    /** The return type for the function. */
    private DataType returnType = null;
	
	
	/** The default permission level required to call function. */
	private PermissionLevel defaultPermsionLevel = 
			PlayerPermissions.getUnspecifiedPlayerPermissions().getPermissionLevel();
	
	
	/**
	 * Inner class which implements the {@link FunctionDefinition} interface to hold the function
	 * definition being built by the FunctionDefinitionBuilder class.
	 *
	 * Objects of this class are immutable.
	 */
	private static final class DefaultFunctionDefinition implements FunctionDefinition {
		/** The name of the function. */
		private final String name;
		
		/** The mapping between parameter names and details for this function. */
		private final Map<String, FunctionParameter> parameterMap;
		
		/** The parameters for the function. */
		private final Collection<FunctionParameter> parameters;
		
		/** The default permission level required to call function. */
		private final PermissionLevel defaultPermissionLevel;

        /** The return type of the function. */
        private final DataType returnType;
		
		/** 
		 * The name of the parameter that will consume extra positional arguments in 
		 * the function call. 
		 */
		private final String positionalArgumentConsumerName;

		/** 
		 * The name of the parameter that will consume extra named arguments in 
		 * the function call. 
		 */
		private final String namedArgumentConsumerName;
		
		/**
		 * Create a DefaultFunctionDefinition object.
		 *
		 * @param name The name of the function.
		 * @param params The parameter for the functions.
		 * 
		 * @throws IllegalArgumentException if you define more than one 
		 *         argument consumer.
		 *         
		 * @throws NullPointerException if any of the arguments are null.
		 */
		private DefaultFunctionDefinition(String name, List<FunctionParameter> params,
                                          PermissionLevel defaultPerm, DataType retType) {
			if (name == null) {
				throw new NullPointerException("Function name can not be null.");
			}
			
			if (params == null) {
				throw new NullPointerException("Function parameters can not be null.");
			}
			
			if (defaultPerm == null) {
				throw new NullPointerException("Default Permission can not be null.");
			}

            if (retType == null) {
                throw  new NullPointerException("Return type can not be null.");
            }
			
			this.name = name;
			defaultPermissionLevel = defaultPerm;
			
			Map<String, FunctionParameter> fparams = new LinkedHashMap<>(); 
			boolean foundConsumer = false;

			String namedConsumerName = null;
			String positionalConsumerName = null;
			for (FunctionParameter fp : params) {
				fparams.put(fp.name(), fp);
				if (fp.consumesRemaining()) {
					if (foundConsumer) {
						throw new IllegalArgumentException("Can only have one argument consumer.");
					}
					if (fp.dataType() == DataType.LIST) {
						positionalConsumerName = fp.name();
					} else {
						namedConsumerName = fp.name();
					}
				}
			}
			
			parameterMap = Collections.unmodifiableMap(fparams);
			parameters = Collections.unmodifiableCollection(fparams.values());
			
			positionalArgumentConsumerName = positionalConsumerName;
			namedArgumentConsumerName = namedConsumerName;
            returnType = retType;
			
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public Collection<FunctionParameter> parameters() {
			return parameters;
		}

		@Override
		public FunctionParameter getParameter(String paramName) {
			return parameterMap.get(paramName);
		}

		@Override
		public boolean hasParameters() {
			return parameters.size() > 0;
		}

		@Override
		public boolean hasPositionalArgumentConsumer() {
			return positionalArgumentConsumerName != null;
		}

		@Override
		public boolean hasNamedArgumentConsumer() {
			return namedArgumentConsumerName != null;
		}

		@Override
		public FunctionParameter getPositionalArgumentConsumer() {
			return parameterMap.get(positionalArgumentConsumerName);
		}

		@Override
		public boolean isValidParameterName(String name) {
			boolean valid = false;
			if (hasNamedArgumentConsumer()) {
				valid = true;
			} else if (parameterMap.containsKey(name)) {
				valid = true;
			}
			
			return valid;
		}

		@Override
		public FunctionParameter getNamedArgumentConsumer() {
			return parameterMap.get(namedArgumentConsumerName);		}

		@Override
		public PermissionLevel defaultRequiredPermissionLevel() {
			return defaultPermissionLevel;
		}

        @Override
        public DataType getReturnType() {
            return returnType;
        }
    }
	
	/**
	 * Sets the name for the {@link FunctionDefinition} that this will build.
	 * 
	 * @param n The name of the function.
	 * @return {@code this} so that methods can be chained.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public FunctionDefinitionBuilder setName(String n) {
		if (n == null) {
			throw new NullPointerException("Name for function definition can not be null.");
		}
		name = n;
		return this;
	}
	
	/**
	 * Adds a parameter to the list of parameters that the function accepts.
	 * 
	 * @param name The name of the parameter.
	 * @param dataType The type that this parameter accepts.
	 * 
	 * @return {@code this} so that methods can be chained.
	 * 
	 * @throws IllegalArgumentException if you try to add a parameter after a parameter
	 *         that consumes the remaining arguments.
	 * @throws NullPointerException if the parameter name or data type are null.
	 */
	public FunctionDefinitionBuilder addParameter(String name, DataType dataType) {
		if (name == null) {
			throw new NullPointerException("Parameter name can not be null.");
		}
		
		if (dataType == null) {
			throw new NullPointerException("Data type for parameter can not be null.");
		}
		
		if (hasListVarargs) {
			throw new IllegalArgumentException("list varargs parameter must be last in paramter list.");
		}
		
		if (hasDictionaryVarargs) {
			throw new IllegalArgumentException("dictionary varargs parameter must be last in paramter list.");
		}
		
		functionParameters.add(FunctionParameter.create(name, dataType));
		return this;
	}
	
	/**
	 * Adds a parameter to the list of parameters that the function accepts with a default value.
	 * 
	 * @param name The name of the parameter.
	 * @param dataType The type that this parameter sets.
	 * @param defaultValue The default value for the parameter.
	 * 
	 * @return {@code this} so that methods can be chained.
	 * 
	 * @throws IllegalArgumentException if you try to add a parameter after a parameter
	 *         that consumes the remaining arguments.
	 * @throws NullPointerException if the parameter name, data type or default value are null.
	 */
	public FunctionDefinitionBuilder addParameter(String name, DataType dataType, DataValue defaultValue) {
		if (name == null) {
			throw new NullPointerException("Parameter name can not be null.");
		}
		
		if (dataType == null) {
			throw new NullPointerException("Data type for parameter can not be null.");
		}
		
		if (defaultValue == null) {
			throw new NullPointerException("Default value for parameter can not be null.");
		}
		
		if (hasListVarargs) {
			throw new IllegalArgumentException("list varargs parameter must be last in paramter list.");
		}
		
		if (hasDictionaryVarargs) {
			throw new IllegalArgumentException("dictionary varargs parameter must be last in paramter list.");
		}
		
		functionParameters.add(FunctionParameter.createWithDefault(name, dataType, defaultValue));
		return this;
	}
	
	/**
	 * Adds a parameter that will consume extra positional arguments as a list.
	 * 
	 * @param name The name of the parameter.
	 * 
	 * @return {@code this} so that methods can be chained.
	 * 
	 * @throws IllegalArgumentException if a varargs parameter already exists
	 *    	   for the function.
	 *    
	 * @throws NullPointerException if the name is null.
	 */
	public FunctionDefinitionBuilder addListVarargsParameter(String name) {
		if (name == null) {
			throw new NullPointerException("Name for varargs parameter can't be null.");
		}
		
		if (hasListVarargs) {
			throw new IllegalArgumentException("can not have more than one varargs in paramter list.");
		}
		
		if (hasDictionaryVarargs) {
			throw new IllegalArgumentException("dcan not have more than one varargs in paramter list.");
		}
		
		functionParameters.add(FunctionParameter.createConsumeRemainingAsList(name));
		return this;
	}
	
	/**
	 * Adds a parameter that will consume extra names arguments as a dictionary.
	 * 
	 * @param name The name of the parameter.
	 * 
	 * @return {@code this} so that methods can be chained.
	 * 
	 * @throws IllegalArgumentException if a varargs parameter already exists
	 *    	   for the function.
	 * @throws NullPointerException if the name is null;
	 */	
	public FunctionDefinitionBuilder addDictionaryVarargsParameter(String name) {
		if (name == null) {
			throw new NullPointerException("Name for varargs parameter can't be null.");
		}

		if (hasListVarargs) {
			throw new IllegalArgumentException("can not have more than one varargs in parameter list.");
		}
		
		if (hasDictionaryVarargs) {
			throw new IllegalArgumentException("can not have more than one varargs in parameter list..");			
		}

		functionParameters.add(FunctionParameter.createConsumeRemainingAsDictionary(name));
		return this;
	}
	
	
	/**
	 * Sets the default permissions required to call the function.
	 * 
	 * @param defPerm the default permissions to set.
	 * 
	 * @return {@code this} so that methods can be chained.
	 */
	public FunctionDefinitionBuilder setDefaultPermission(PermissionLevel defPerm) {
		defaultPermsionLevel = defPerm;
		return this;
	}


    /**
     * Sets the return type of the function.
     *
     * @param retType The return type of the function.
     *
     * @return {@code this} so that the methods can be chained.
     *
     */
    public FunctionDefinitionBuilder setReturnType(DataType retType) {
        returnType = retType;
        return this;
    }
	
	/**
	 * Creates the {@link FunctionDefinition} from the values set using this builder.
	 * 
	 * @return a {@link FunctionDefinition}.
     *
     * @throws  NullPointerException if the name or return type for the function have
     *          not been set.
	 */
	public FunctionDefinition toFunctionDefinition() {
        if (name == null) {
            throw new NullPointerException("Name can not be null.");
        }

        if (returnType == null) {
            throw new NullPointerException("Return type can not be null.");
        }

		return new DefaultFunctionDefinition(name, functionParameters, defaultPermsionLevel, returnType);
	}


}
