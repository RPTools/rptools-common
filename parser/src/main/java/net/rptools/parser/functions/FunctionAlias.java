package net.rptools.parser.functions;

import java.util.Collection;
import java.util.Map;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.permissions.PermissionLevel;
import net.rptools.parser.ScriptContext;

/**
 * Aliases a script function so that it can be used by calling it with a different name.
 */
public class FunctionAlias implements ScriptFunction {

	/** The function that is aliased. */
	private final ScriptFunction aliasedFunction;
	
	/** The definition of the alias. */
	private final FunctionDefinition definition;
	
	/**
	 * Creates a new FunctionAlias object.
	 * 
	 * @param aliasName The name of the alias.
	 * @param function The function to alias.
	 * 
	 * @throws NullPointerException if either argument is null.
	 */
	public FunctionAlias(final String aliasName, final ScriptFunction function) {
		
		if (aliasName == null) {
			throw new NullPointerException("Alias name can not be null");
		}
		
		if (function == null) {
			throw new NullPointerException("Function to be aliased can not be null");
		}
		
		// Decorate the aliased script function definition with an anonymous class which returns the aliased name
		definition = new FunctionDefinition() {
			
			@Override
			public Collection<FunctionParameter> parameters() {
				return function.getDefinition().parameters();
			}
			
			@Override
			public String name() {
				return aliasName;
			}
			
			@Override
			public boolean isValidParameterName(String name) {
				return function.getDefinition().isValidParameterName(name);
			}
			
			@Override
			public boolean hasPositionalArgumentConsumer() {
				return function.getDefinition().hasPositionalArgumentConsumer();
			}
			
			@Override
			public boolean hasParameters() {
				return function.getDefinition().hasParameters();
			}
			
			@Override
			public boolean hasNamedArgumentConsumer() {
				return function.getDefinition().hasNamedArgumentConsumer();
			}
			
			@Override
			public FunctionParameter getPositionalArgumentConsumer() {
				return function.getDefinition().getPositionalArgumentConsumer();
			}
			
			@Override
			public FunctionParameter getParameter(String paramName) {
				return function.getDefinition().getParameter(paramName);
			}
			
			@Override
			public FunctionParameter getNamedArgumentConsumer() {
				return function.getDefinition().getNamedArgumentConsumer();
			}

			@Override
			public PermissionLevel defaultRequiredPermissionLevel() {
				return function.getDefinition().defaultRequiredPermissionLevel();
			}

            @Override
            public DataType getReturnType() {
                return function.getDefinition().getReturnType();
            }
        };
		aliasedFunction = function;
	}
	
	@Override
	public FunctionDefinition getDefinition() {
		return definition;
	}

	@Override
	public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {
		return aliasedFunction.call(context, args);
	}

}
