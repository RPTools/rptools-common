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

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.permissions.PermissionLevel;

/**
 * Interface describing the classes that are used to provide
 * the definition of functions available in the scripting language. 
 *
 */
public interface FunctionDefinition {

	/** The name of the function. */
	public String name();

	/**
	 * Returns parameters that the function accepts.
	 * 
	 * @return the parameters that are defined for the function.
	 */
	public Collection<FunctionParameter> parameters();

	/** 
	 * Returns the FunctionParameter that describes the specified parameter
	 * for the function.
	 * 
	 * @param paramName the name of the parameter.
	 * @return the FunctionParameter parameter description.
	 */
	public FunctionParameter getParameter(String paramName);
	
	/**
	 * Checks to see if the function has parameters.
	 * 
	 * @return true if the function has parameters.
	 */
	public boolean hasParameters();
	
	/**
	 * Checks to see if function has a parameter that will consume
	 * any extra positional parameters at the end of the argument
	 * list of the function call. 
	 * 
	 * @return true if the function has a parameter that will consume
	 *         any extra positional arguments.
	 */
	public boolean hasPositionalArgumentConsumer();

	/**
	 * Checks to see if the function has a parameter that will consume
	 * any extra named parameters in the argument list of the function 
	 * call. 
	 * 
	 * @return true if the function has a parameter that will consume
	 *  	   any extra named arguments.
	 */
	public boolean hasNamedArgumentConsumer();
	
	/**
	 * Returns the parameter that will consume any extra positional 
	 * parameters at the end of the argument list of the function call.
	 * This is the equivalent of a varargs parameter in many programming 
	 * languages. All extra positional parameters will be passed to the
	 * function in the named parameter as a list.
	 * 
	 * There can only be one positional or named argument consumer defined
	 * for a function.
	 * 
	 * @return the name of the parameter the consumes the extra positional
	 *         parameters.
	 */
	public FunctionParameter getPositionalArgumentConsumer();
	
	/**
	 * Returns the parameter that will consume any extra named parameters
	 * in the argument list of the function call. All extra named parameters
	 * are assembled into a dictionary value and passed to the function in
	 * this parameter.
	 * 
	 * There can only be one positional or named argument consumer defined
	 * for a function.
	 * 
	 * @return the name of the parameter that consumes the extra named
	 *        parameters.
	 */
	public FunctionParameter getNamedArgumentConsumer();

	/**
	 * Checks to see if the function has the specified parameter in its list.
	 * 
	 * @param name The name of the parameter to check for.
	 * 
	 * @return true if the function has the parameter.
	 */
	public boolean isValidParameterName(String name);
	
	
	/**
	 * Returns the default permission level required to call this function.
	 * 
	 * @return the default permission level required.
	 */
	public PermissionLevel defaultRequiredPermissionLevel();


    /**
     * Returns the return type expected from the function. A function will
     * always return a result.
     *
     * @return The type of DataValue returned.
     */
    public DataType getReturnType();
}
