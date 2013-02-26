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
 * Exception thrown when there is a permission problem when evaluating the script.
 *
 */
public class EvaluationPermissionException extends ExpressionEvaluatorException {


	/**
	 *  Serial UID.
	 */
	private static final long serialVersionUID = 7573257259696037605L;

	/**
	 * Creates a new EvaluationPermissionException.
	 * 
	 * @param msg A message describing the exception.
	 */
	public EvaluationPermissionException(String msg) {
		super(msg);
	}
	
	/**
	 * Creates a new EvaluationPermissionException with another exception as
	 * the cause.
	 * 
	 * @param msg A message describing the exception.
	 * @param cause The original exception.
	 */
	public EvaluationPermissionException(String msg, Throwable cause) {
		super(msg, cause);
	}


}
