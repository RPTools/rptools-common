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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Creates a new Result() object.
//
// Parameters:
//  value       The value of the result.
//  details     The detailed value of the result.
//  individual  The individual values that make up the result.
//
function Result(value, details, individual) {
    if (value) {
        this.value = value;
    }

    if (details) {
        this.details = details;
    }

    if (individual) {
        this.individual = individual;
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Sets the value of result.
//
// Parameters:
//      val     The value to set.
//
// Returns:
//  this so methods can be chained.
//
Result.prototype.setValue = function(val) {
    this.value = val;
    return this;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Sets the details of how the result is made up.
//
// Parameters:
//      val     The value to set.
//
// Returns:
//  this so methods can be chained.
//
Result.prototype.setDetails = function(val) {
    this.details = val;
    return this;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Sets the individual values that make up a result.
//
// Parameters:
//      vals     The values to set.
//
// Returns:
//  this so methods can be chained.
//
Result.prototype.setIndividualValues = function(vals) {
    this.individual = vals;
    return this;
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Returns the value of result.
//
// Parameters:
//
// Returns:
//  The value.
//
Result.prototype.getValue = function() {
    return this.value;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Returns the details of how the result is made up.
//
// Parameters:
//
// Returns:
//  The details.
//
Result.prototype.getDetails = function() {
    return this.details;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Returns the individual values that the result is made up of.
//
// Parameters:
//
// Returns:
//  The individual values.
//
Result.prototype.getIndividualValues = function() {
    return this.individual;
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// create the net.rptools namespace.
var rptools = {};


rptools.convertToDataValue = function(value, returnType) {
    var result;

/*    java.lang.System.out.println(returnType);

    if (returnType == "Long") {
        result = java.net.rptools.lib.datavalue.DataValueFactory.longValue(value);
    }  else if (returnType == "Double") {
        result = java.net.rptools.lib.datavalue.DataValueFactory.doubleValue(value);
    }

    return result;
    */

   return { test:1, blah: "yo", hey: [1, 2, 3, 3], test1: { a: 1, b: 2}};

    //return "blah";
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Function to convert a Java net.rptools.lib.datavalue.DataValue object to JavaScript
// object(s).
//
rptools.convertDataValue = function(dv) {
    if (dv.dataType() == net.rptools.lib.datavalue.DataType.LIST) {
        var val = [];
        var iter = dv.asList().iterator();
        while (iter.hasNext()) {
            val.push(rptools.convertDataValue(iter.next()));
        }
        return val;
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.DICTIONARY) {
        var obj = {};
        var dict = dv.asDictionary();
        var iter = dict.keySet().iterator();
        while (iter.hasNext()) {
            var name = iter.next();
            var val = dict.get(name);
            obj[name] = rptools.convertDataValue(val);
        }

        return obj;
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.RESULT) {
        var res = dv.asResult();
        var val = rptools.convertDataValue(res.getValue());
        var det = rptools.convertDataValue(res.getDetailedResult());
        var indiv = [];
        var iter = res.getValues().iterator();
        while (iter.hasNext()) {
            indiv.push(rptools.convertDataValue(iter.next()));
        }

       return new Result(val, det, indiv);

    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.BOOLEAN) {
        return dv.asBoolean();
    } else if (dv.dataType() == net.rptoold.lib.datavalue.DataType.NULL) {
        return null;
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.LONG) {
        return dv.asLong();
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.DOUBLE) {
        return dv.asDouble();
    } else {
        return dv.asString();
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Function to convert the RPTools script arguments from Java to JavaScript objects.
//
rptools.convertArgs = function(args) {
    var argsObj = {};
    var iter = args.keySet().iterator();
    while (iter.hasNext()) {
        var name  = iter.next();
        var val = rptools.convertDataValue(args.get(name));
        argsObj[name] = val;
    }
    return argsObj;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Class used to export functions to the RPTools script.
//
function ExportedFunction(name, returnType, jsFunctionName, permission) {
    if (!name) {
        throw "Function name is empty";
    }

    if (!returnType) {
        throw "Function return type is empty";
    }

    if (!jsFunctionName) {
        throw "Java Script Function name is empty";
    }

    var perm = permission;
    if (!permission) {
        perm = ExportedFunction.PERM_OBSERVER;
    }

    this.name = name;
    this.returnType = returnType;
    this.jsFunctionName = jsFunctionName;
    this.permission = perm;
    this.hasVarargs = false;
    this.parameterList = [];
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Add a parameter to the function.
//
// Parameters:
//      name        The name of the function.
//      paramType   The type of the parameter.
//
ExportedFunction.prototype.addParameter = function(name, paramType, defaultVal) {

    if (!name) {
        throw "Parameter name is empty.";
    }

    if (!paramType) {
        throw "Parameter type is empty.";
    }

    if (this.hasVarargs) {
        throw "Varargs parameter must be last parameter in parameter list.";
    }


    var pType = paramType;
    var varargFlag = false;

    if (paramType == ExportedFunction.DATA_TYPE_LIST_VARARGS) {
      pType = ExportedFunction.DATA_TYPE_LIST;
      varargFlag = true;
      this.hasVarargs = true;
    }

    if (paramType == ExportedFunction.DATA_TYPE_DICT_VARARGS) {
        pType = ExportedFunction.DATA_TYPE_DICT;
        varargFlag = true;
        this.hasVarargs = true;
    }


    var args = { name: name, paramType: pType, varargFlag: varargFlag, defaultVal: defaultVal };
    this.parameterList.push(args);
};

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Sets the function name.
//
// Parameters:
//      The name of the function.
//
ExportedFunction.prototype.setName = function(name) {
     if (!name) {
        throw "Function name is empty.";
     }
};


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Exports the function to the RPTools scripting language.
//
// Parameters:
//      None.
//
ExportedFunction.prototype.export = function() {
    net.rptools.parser.jsapi.ExportJS.exportFunction(this.name, this.parameterList,
                                                     this.returnType, this.jsFunctionName,
                                                     this.permission);
};


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Different parameter and return types types that a function can accept and return.

// Long integer type.
ExportedFunction.DATA_TYPE_LONG = net.rptools.lib.datavalue.DataType.LONG.toString();
// Synonym for DATA_TYPE_LONG.
ExportedFunction.DATA_TYPE_INT = net.rptools.lib.datavalue.DataType.LONG.toString();
// Double floating point type.
ExportedFunction.DATA_TYPE_DOUBLE = net.rptools.lib.datavalue.DataType.DOUBLE.toString();
// Synonym for DATA_TYPE_DOUBLE.
ExportedFunction.DATA_TYPE_FLOAT = ExportedFunction.DATA_TYPE_DOUBLE;
// String type.
ExportedFunction.DATA_TYPE_STRING = net.rptools.lib.datavalue.DataType.STRING.toString();
// List type.
ExportedFunction.DATA_TYPE_LIST = net.rptools.lib.datavalue.DataType.LIST.toString();
// Dictionary (maps values to a string) type.
ExportedFunction.DATA_TYPE_DICT = net.rptools.lib.datavalue.DataType.DICTIONARY.toString();
// Boolean data type.
ExportedFunction.DATA_TYPE_BOOLEAN = net.rptools.lib.datavalue.DataType.BOOLEAN.toString();
// Result type.
ExportedFunction.DATA_TYPE_RESULT = net.rptools.lib.datavalue.DataType.RESULT.toString();
// Null value.
ExportedFunction.DATA_TYPE_NULL = net.rptools.lib.datavalue.DataType.NULL.toString();
// Variable arguments list type. Used for a variable amount of positional parameters.
ExportedFunction.DATA_TYPE_LIST_VARARGS = "List*";
// Variable arguments dictionary type. Used for a variable amount of named parameters.
ExportedFunction.DATA_TYPE_DICT_VARARGS = "Dictionary*";


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Permissions required to execute the function.

// Player must be a GM to execute.
ExportedFunction.PERM_GM = net.rptools.lib.permissions.PermissionLevel.GM.toString();
// Must be a player or GM to execute.
ExportedFunction.PERM_PLAYER = net.rptools.lib.permissions.PermissionLevel.PLAYER.toString();
// Observer (or anyone) can execute.
ExportedFunction.PERM_OBSERVER = net.rptools.lib.permissions.PermissionLevel.OBSERVER.toString();

