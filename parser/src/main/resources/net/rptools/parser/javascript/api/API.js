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

var f1 = new ExportedFunction("listSum", ExportedFunction.DATA_TYPE_DOUBLE, "doListSum");
f1.addParameter("nums", ExportedFunction.DATA_TYPE_LIST_VARARGS);
f1.export();
delete f1;

var f2 = new ExportedFunction("listSumR", ExportedFunction.DATA_TYPE_RESULT, "doListSumR");
f2.addParameter("nums", ExportedFunction.DATA_TYPE_LIST_VARARGS);
f2.export();
delete f2;

var f3 = new ExportedFunction("showResult", ExportedFunction.DATA_TYPE_STRING, "doShowResult");
f3.addParameter("res", ExportedFunction.DATA_TYPE_RESULT);
f3.export();
//delete f3;

function doListSumR(args) {
    var val = 0;
    var vals = [];
    var details = "";
    for (x in args.nums) {
        vals.push(args.nums[x]);

        val += args.nums[x];

        if (details != "") {
            details += (" + " +  args.nums[x]);
        } else {
            details = args.nums[x];
        }


    }
    var res = new Result().setValue(val).setDetails(details).setIndividualValues(vals);
    return res;
    //return { value: val, details: details, individual: vals}
}

function doListSum(args) {
    var val = 0;
    for (x in args.nums) {
        val += args.nums[x];
    }
    return val;
}

function doShowResult(args) {
    return "Result found: value = " + args.res.getValue()  +
           ", details = " + args.res.getDetails() +
           ", individual = " + args.res.getIndividualValues();
}
