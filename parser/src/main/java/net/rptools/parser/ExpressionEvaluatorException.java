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
package net.rptools.parser;

/**
 * ExpressionEvaluatorException is thrown when an exception occurs
 * while trying to evaluate an expressions (or script made up of 
 * expression).s
 *
 */
public class ExpressionEvaluatorException extends Exception {
	
	/** The serial version UID */
	private static final long serialVersionUID = -4948811949842408127L;

	/**
	 * Creates a new ExpressionEvaluatorException.
	 * 
	 * @param msg A message describing the exception.
	 */
	public ExpressionEvaluatorException(String msg) {
		super(msg);
	}
	
	/**
	 * Creates a new ExpressionEvaluatorException with another exception as
	 * the cause.
	 * 
	 * @param msg A message describing the exception.
	 * @param cause The original exception.
	 */
	public ExpressionEvaluatorException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
