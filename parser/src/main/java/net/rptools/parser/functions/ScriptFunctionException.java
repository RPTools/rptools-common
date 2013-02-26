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

/**
 * Exception that scripts throw when something goes wrong.
 *
 */
public class ScriptFunctionException extends Exception {

	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -8243440537538634208L;

	/**
	 * Creates a new ScriptFunctionException.
	 * 
	 * @param msg A message describing the exception.
	 */
	public ScriptFunctionException(String msg) {
		super(msg);
	}
	
	/**
	 * Creates a new ScriptFunctionException with another exception as
	 * the cause.
	 * 
	 * @param msg A message describing the exception.
	 * @param cause The original exception.
	 */
	public ScriptFunctionException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
