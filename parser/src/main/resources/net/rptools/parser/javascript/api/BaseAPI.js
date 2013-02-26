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



//
//
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
            obj[name] = val;
        }

        return obj;
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.RESULT) {
        // TOOD
        return {};
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.LONG) {
        return dv.asLong();
    } else if (dv.dataType() == net.rptools.lib.datavalue.DataType.DOUBLE) {
        return dv.asDouble();
    } else {
        return dv.asString();
    }
}

//
//
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

    if (paramType == ExportedFunction.PARAM_LIST_VARARGS) {
      pType = ExportedFunction.PARAM_LIST;
      varargFlag = true;
      this.hasVarargs = true;
    }

    if (paramType == ExportedFunction.PARAM_DICT_VARARGS) {
        pType = ExportedFunction.PARAM_DICT;
        varargFlag = true;
        this.hasVarargs = true;
    }


    var args = { name: name, paramType: pType, varargFlag: varargFlag, defaultVal: defaultVal };
    this.parameterList.push(args);
};


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


ExportedFunction.PARAM_INT = net.rptools.lib.datavalue.DataType.LONG.toString();
ExportedFunction.PARAM_FLOAT = net.rptools.lib.datavalue.DataType.DOUBLE.toString();
ExportedFunction.PARAM_STRING = net.rptools.lib.datavalue.DataType.STRING.toString();
ExportedFunction.PARAM_LIST = net.rptools.lib.datavalue.DataType.LIST.toString();
ExportedFunction.PARAM_DICT = net.rptools.lib.datavalue.DataType.DICTIONARY.toString();
ExportedFunction.PARAM_RESULT = net.rptools.lib.datavalue.DataType.RESULT.toString();
ExportedFunction.PARAM_LIST_VARARGS = "List*";
ExportedFunction.PARAM_DICT_VARARGS = "Dictionary*";


// TODO:
ExportedFunction.PERM_GM = "gm";
ExportedFunction.PERM_PLAYER = "player";
ExportedFunction.PERM_OBSERVER = "observer";


