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
package net.rptools.parser.tree;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * Script node class that performs binary mathematical operations.
 */
class BinaryMathOpNode implements ScriptTreeNode {

	/** The operation to perform. */
	private Operation operation;
	
	/** The child node left hand side of the operator. */
	private ScriptTreeNode leftChild;
	
	/** The child node on the right hand side of the operator. */
	private ScriptTreeNode rightChild;

	/**
	 * Enumeration used for determining the operator type. 
	 */
	public enum Operation {
		/** Add two values (+). */
		ADD,
		/** Subtract right value from left (-). */
		SUBTRACT,
		/** Multiply two values (*). */
		MULTIPLY,
		/** Divide the left value by the right (/). */
		DIVIDE,
		/** The remainder of dividing the left value by the right (%). */
		REMAINDER,
		/** Raise the left value by the right (^). */
		POWER
	}
	
	/**
	 * Returns a script node that will add two values. 
	 * 
	 * @param left The left child.
	 * @param right The right child.
	 * 
	 * @return the node.
	 * 
	 */
	public static BinaryMathOpNode getAddNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child null for add node.";
		assert right != null : "Left child null for add node.";

		return new BinaryMathOpNode(Operation.ADD, left, right);
	}

	/**
	 * Returns a script node that will subtract the right value from the left. 
	 * 
	 * @param left The left child.
	 * @param right The right child.
	 * 
	 * @return the node.
	 * 
	 */
	public static BinaryMathOpNode getSubtractNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child null for subtract node.";
		assert right != null : "Left child null for subtract node.";

		return new BinaryMathOpNode(Operation.SUBTRACT, left, right);
	}
	
	/**
	 * Returns a script node that will multiply two values. 
	 * 
	 * @param left The left child.
	 * @param right The right child.
	 * 
	 * @return the node.
	 * 
	 */
	public static BinaryMathOpNode getMultiplyNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child null for multiply node.";
		assert right != null : "Left child null for multiply node.";
		
		return new BinaryMathOpNode(Operation.MULTIPLY, left, right);
	}
	
	/**
	 * Returns a script node that will divide the left value by the right. 
	 * 
	 * @param left The left child.
	 * @param right The right child.
	 * 
	 * @return the node.
	 * 
	 */
	public static BinaryMathOpNode getDivideNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child null for divide node.";
		assert right != null : "Left child null for divide node.";

		return new BinaryMathOpNode(Operation.DIVIDE, left, right);
	}
	
	/**
	 * Returns a script node that will return the remainder of dividing the left value 
	 * by the right. 
	 * 
	 * @param left The left child.
	 * @param right The right child.
	 * 
	 * @return the node.
	 * 
	 */
	public static BinaryMathOpNode getRemainderNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child null for remainder node.";
		assert right != null : "Left child null for remainder node.";

		return new BinaryMathOpNode(Operation.REMAINDER, left, right);
	}
	
	/**
	 * Returns a script node that will raise the left value to the power of the right. 
	 * 
	 * @param left The left child.
	 * @param right The right child.
	 * 
	 * @return the node.
	 * 
	 * 
	 */
	public static BinaryMathOpNode getPowerNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child null for power node.";
		assert right != null : "Left child null for power node.";

		return new BinaryMathOpNode(Operation.POWER, left, right);
	}



	
	/**
	 * Creates a new BinaryMathOpNode.
	 * 
	 * @param op The mathematical operator.
	 * @param left The left child node.
	 * @param right The right child node.
	 * 
	 */
	BinaryMathOpNode(Operation op, ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left hand child node can not be null";
		assert right != null : "Right hand child node can not be null";
		assert op != null : "Operation can not be null";

		operation = op;
		leftChild = left;
		rightChild = right;
	}
	
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		DataValue leftVal = leftChild.evaluate(context);
		DataValue rightVal = rightChild.evaluate(context);
		
		DataValue val = null;
		
		switch (operation) {
			case ADD:
				val = leftVal.add(rightVal);
				break;
			case SUBTRACT:
				val = leftVal.subtract(rightVal);
				break;
			case MULTIPLY:
				val = leftVal.multiply(rightVal);
				break;
			case DIVIDE:
				val = leftVal.divide(rightVal);
				break;
			case REMAINDER:
				val = leftVal.remainder(rightVal);
				break;
			case POWER:
				val = leftVal.power(rightVal);
				break;
		}
		return val;
	}

}
