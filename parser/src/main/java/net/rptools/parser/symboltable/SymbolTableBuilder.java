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

/**
 * Builder for a SymbolTable hiding the messy details.
 *
 */
public class SymbolTableBuilder {
	
	/** The variable resolver. */
	private VariableResolver variableResolver;
	
	/** The property resolver. */
	private PropertyResolver propertyResolver;
	
	/** The prompt resolver. */
	private PromptResolver promptResolver;
	
	/**
	 * Sets the variable resolver that will be used.
	 * 
	 * @param resolver the resolver to be used.
	 * 
	 * @return {@code this} so that the methods can be chained.
	 */
	public SymbolTableBuilder setVariableResolver(VariableResolver resolver) {
		variableResolver = resolver;
		return this;
	}
	
	/**
	 * Sets the variable resolver that will be used.
	 * 
	 * @param resolver the resolver to use.
	 * 
	 * @return {@code this} so that the methods can be chained.
	 */
	public SymbolTableBuilder setPropertyResolver(PropertyResolver resolver) {
		propertyResolver = resolver;
		return this;
	}
	
	/**
	 * Sets the prompt resolve that will be used. 
	 * 
	 * @param resolver The resolver to use.
	 * 
	 * @return {@code this} so that the methods can be chained.
	 */
	public SymbolTableBuilder setPromptResolver(PromptResolver resolver) {
		promptResolver = resolver;
		return this;
	}
	
	/**
	 * Returns a SynbolTable with the values from this builder.
	 * 
	 * @return a SymbolTable.
	 */
	public SymbolTable toSymbolTable() {
		if (variableResolver == null) {
			variableResolver = new DefaultVariableResolver();
		}
		
		if (propertyResolver == null) {
			propertyResolver = new DefaultPropertyResolver();
		}
		
		if (promptResolver == null) {
			promptResolver = new DefaultPromptResolver();
		}
		
		return new SymbolTableImpl(variableResolver, propertyResolver, promptResolver);
	}
}
