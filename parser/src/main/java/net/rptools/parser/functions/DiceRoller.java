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
import java.util.List;
import java.util.Random;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.result.ResultBuilder;

// TODO: Temporary implementation of this class.
public class DiceRoller {
	
	private final Random random = new Random();
	
	
	public DataValue roll(String rollStr, boolean verbose) {
		String[] vals = rollStr.split("[dD]");
		
		int numberRolls;
		if (vals[0].length() == 0) {
			numberRolls = 1;
		} else {
			numberRolls = Integer.parseInt(vals[0]);
		}
		
		int sides = Integer.parseInt(vals[1]);
		
		List<Long> rolls = new ArrayList<>(sides);
		
		int total = 0;
		for (int i = 0; i < numberRolls; i++) {
			long r = roll(sides);
			total += r;
			rolls.add(r);
		}
		StringBuilder sb = new StringBuilder();
		if (verbose == false) {
			sb.append(total);
		} else {
			for (int i = 0; i < rolls.size(); i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(rolls.get(i));
			}
			sb.append(" = ");
			sb.append(total);
		}

		ResultBuilder res = new ResultBuilder().setLongValue(total);
		res = res.setDetailedResult(DataValueFactory.stringValue(sb.toString()));
		res = res.setIndividualValues(DataValueFactory.longListValue(rolls).asList());
		return DataValueFactory.resultValue(res.toResult());
		
	}
		
	
	private int roll(int sides) {
		return random.nextInt(sides) + 1;
	}
}
