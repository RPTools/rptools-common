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
package net.rptools.parser.functions.javascript;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.ScriptFunction;
import net.rptools.parser.functions.ScriptFunctionException;
import net.rptools.parser.jsapi.ExportJS;
import org.mozilla.javascript.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class used to evaluate JavaScript function calls.
 */
public class JavaScriptFunctionEvaluator {


    /** The name of the JavaScript function that converts return to a {@link DataValue}. */
    private final static String DATAVALUE_CONVERSION_FUNCTION = "rptools.convertToDataValue";

    /** The singleton instance. */
	private final static JavaScriptFunctionEvaluator INSTANCE = new JavaScriptFunctionEvaluator();

    /** Contains the scope that each of the functions lives in. */
	private Map<ScriptFunction, Scriptable> functionScopes = new HashMap<>();

    /** The top level scope where all the JavaScript code that forms the base API lives. */
    private Scriptable sharedScope;




	/**
	 * Private constructor to stop instantiation.
	 */
	private JavaScriptFunctionEvaluator() {

        Context jsContext = Context.enter();
        try {
            URL url = this.getClass().getResource("/net/rptools/parser/javascript/api/BaseAPI.js");
            Path p = Paths.get(url.toURI());
            byte[] bytes = Files.readAllBytes(p);
            sharedScope = jsContext.initStandardObjects();
            jsContext.evaluateString(sharedScope, new String(bytes), "BaseAPI", 0, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            jsContext.exit();
        }
    }

    /**
     * Returns an instance of JavaScriptFunctionEvaluator.
     *
     * @return a JavaScriptFunctionEvaluator.
     */
	public static JavaScriptFunctionEvaluator getInstance() {
		return INSTANCE;
	}


    /**
     * Calls the required JavaScript function.
     *
     * @param context The script context to execute in.
     * @param function The function to call.
     * @param args The arguments to pass to the function.
     * @param returnType The expected return type.
     *
     * @return the value returned by the function.
     *
     * @throws ScriptFunctionException when an error occurs evaluating the JavaScript.
     * @throws NullPointerException if any of the arguments are null.
     */
    DataValue call(ScriptContext context, JavaScriptFunction function, Map<String, DataValue> args, DataType returnType)
            throws ScriptFunctionException {

        if (context == null) {
            throw new NullPointerException("Script Context is null.");
        }

        if (function == null) {
            throw new NullPointerException("Function to call is null.");
        }

        if (args == null) {
            throw new NullPointerException("Function argument list is null.");
        }

        if (returnType == null) {
            throw new NullPointerException("Return type of function call is null.");
        }

        if (functionScopes.containsKey(function) == false) {
            throw new ScriptFunctionException("Unable to find JavaScript call for " + function.getDefinition().name());
        }


        Scriptable scope = functionScopes.get(function);
        Context jsContext = Context.enter();
        DataValue result = null;
        try {

            Object rptoolsNS = sharedScope.get("rptools", scope);
            if (rptoolsNS == null) {
                throw new ScriptFunctionException("Can not find the rptools scope");
            }

            Object oArgsConv = ((Scriptable)rptoolsNS).get("convertArgs", sharedScope);
            if (!(oArgsConv instanceof Function)) {
                throw new ScriptFunctionException("Can not find argument conversion function.");
            }
            Function fArgsConv = (Function) oArgsConv;
            Object a1[] = { args };
            Object callArg = fArgsConv.call(jsContext, scope, scope, a1);
            Object callArgs[] = { callArg };

            Object fObject = scope.get(function.jsFunctionName(), scope);
            if (!(fObject instanceof Function)) {
                throw new ScriptFunctionException(function.jsFunctionName() + " not defined.");
            }


            Function callFunc = (Function)fObject;

            //Object functionArgs[] = toJSArgs(args);
            Object oresult = callFunc.call(jsContext, scope, scope, callArgs);


            // Now convert to DataValue
            switch(returnType) {
                case LONG:
                    result = convertToLongValue(oresult);
                    break;
                case DOUBLE:
                    result = convertToDoubleValue(oresult);
                    break;
                case STRING:
                    result = convertToStringValue(oresult);
                    break;
                case LIST:
                    result = convertToListValue(scope, oresult);
                    break;
                case DICTIONARY:
                    result = convertToDictionary(scope, oresult);
                    break;
                case RESULT:
                    result = convertToResult(scope, oresult);
                    break;
                default:
                    throw new ScriptFunctionException("Invalid return type for function " + returnType);
            }

        }  catch(Exception e) {
            jsContext.exit();
            throw new ScriptFunctionException(e.getLocalizedMessage(), e);
        }

        jsContext.exit();


        return result;
    }


    private Object toJSArg(DataValue val) {
        Object ret = null;
        switch(val.dataType()) {
            case LONG:
                ret = Long.valueOf(val.asLong());
                break;
            case DOUBLE:
                ret = Double.valueOf(val.asDouble());
                break;
            case STRING:
                ret = val.asString();
                break;
            case LIST:
                List<Object> lst = new ArrayList<>();
                for (DataValue dv : val.asList()) {
                    lst.add(toJSArg(dv));
                }
                //ret = new NativeArray(lst.toArray());
                ret = lst.toArray();
                break;
            case DICTIONARY:
                //NativeObject nobj = new NativeObject();
                Map<Object, Object> nobj = new HashMap<>();
                for (Map.Entry<String, DataValue> entry : val.asDictionary().entrySet()) {
                    nobj.put(entry.getKey(), toJSArg(entry.getValue()));
                }
                ret = nobj;
                break;
            case RESULT:
                // TODO:
                break;
            default:
                throw new UnsupportedOperationException("Can not convert " + val.dataType() + " to JavaScript type.");
        }

        return ret;
    }

    private Object[] toJSArgs(Map<String, DataValue> args) {
        Object val = toJSArg(DataValueFactory.dictionaryValue(args));
        return new Object[] { val };
    }

    /**
     * Returns the {@link DataValue} of type {@link DataType#LONG} representation of the object.
     *
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     */
    private DataValue convertToLongValue(Object o) {
        Long val = (Long)Context.jsToJava(o, long.class);
        return DataValueFactory.longValue(val);
    }

    /**
     * Returns the {@link DataValue} of type {@link DataType#DOUBLE} representation of the object.
     *
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     */
    private DataValue convertToDoubleValue(Object o) {
        Double val = (Double)Context.jsToJava(o, double.class);
        return DataValueFactory.doubleValue(val);
    }

    /**
     * Returns the {@link DataValue} of type {@link DataType#STRING} representation of the object.
     *
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     */
    private DataValue convertToStringValue(Object o) {
        if (o instanceof NativeArray) {
            throw new UnsupportedOperationException("Can't convert JavaScript array to string.");
        }

        if (o instanceof NativeObject) {
            throw new UnsupportedOperationException("Can't convert JavaScript object to string.");
        }

        return DataValueFactory.stringValue(o.toString());
    }

    /**
     * Returns the {@link DataValue} of type {@link DataType#LIST} representation of the object.
     *
     * @param scope The JavaScript scope that this object is defined in.
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     */
    private DataValue convertToListValue(Scriptable scope, Object o) {
        DataValue listVal = null;
        if (o instanceof NativeArray) {
            List<DataValue> lst = new ArrayList<>();
            NativeArray arr = (NativeArray)o;
            for (Object ele : arr.getAllIds()) {
                DataValue val = convertToDataValue(scope, ele);
                lst.add(val);
            }
            listVal = DataValueFactory.listValue(lst);
        } else {
            listVal = DataValueFactory.listValue(Collections.singletonList(convertToDataValue(scope, o)));
        }

        return listVal;
    }

    /**
     * Returns the {@link DataValue} of type {@link DataType#DICTIONARY} representation of the object.
     *
     * @param scope The JavaScript scope that this object is defined in.
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     * @throws UnsupportedOperationException if the object is not a type that can be converted into a
     *         dictionary.
     *
     */
    private DataValue convertToDictionary(Scriptable scope, Object o) {
        if (!(o instanceof NativeObject)) {
            throw new UnsupportedOperationException("Only JavaScript objects can be converted to dictionaries.");
        }

        Map<String, DataValue> dict = new HashMap<>();
        NativeObject no = (NativeObject) o;

        for (Object id : no.getIds()) {
            dict.put(id.toString(), convertToDataValue(scope, no.get(id.toString(), scope)));
        }
        return DataValueFactory.dictionaryValue(dict);
    }

    /**
     * Returns the {@link DataValue} of type {@link DataType#RESULT} representation of the object.
     *
     * @param scope The JavaScript scope that this object is defined in.
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     * @throws UnsupportedOperationException if the object is not a type that can be converted into a
     *         result.
     *
     */
    private DataValue convertToResult(Scriptable scope, Object o) {
        // TODO: need to do JavaScript result first.
        return null;
    }


    /**
     * Returns the best {@link DataValue} representation of the object.
     *
     * @param scope The JavaScript scope that this object is defined in.
     * @param o The object to convert.
     *
     * @return the {@link DataValue}.
     *
     */
    private DataValue convertToDataValue(Scriptable scope, Object o) {
        DataValue retVal = null;
        if (o instanceof NativeArray) {
            List<DataValue> lst = new ArrayList<>();
            NativeArray arr = (NativeArray)o;
            for (Object ele : arr.getAllIds()) {
                DataValue val = convertToDataValue(scope, ele);
                lst.add(val);
            }
            retVal = DataValueFactory.listValue(lst);
        } else if (o instanceof NativeObject) {
            retVal = convertToDictionary(scope, o);
        } else if (o instanceof String) {
            retVal = convertToStringValue(o);
        } else {
            DataValue num = convertToDoubleValue(0);
            if (Math.round(num.asDouble()) == num.asLong()) {
                num = num.asLongValue();
            }
            retVal = num;
        }

        return retVal;
    }

    /**
     * Resets the list of recently defined functions used during function definition.
     */
    private void resetRecentlyDefined() {
        ExportJS.clearExportedFunctions();
    }

    /**
     * Returns the list of recently defined functions.
     *
     * @return list of recently defined functions.
     */
    private Collection<JavaScriptFunction> getRecentlyDefined(Scriptable scope) {
        List<JavaScriptFunction> functions = new ArrayList<>();
        for (ExportedFunction ef : ExportJS.getExportedFunctions()) {
            FunctionDefinitionBuilder fdb = new FunctionDefinitionBuilder();
            fdb.setName(ef.getFunctionName());
            fdb.setReturnType(ef.getReturnType());
            fdb.setDefaultPermission(ef.getPermissionLevel());

            NativeArray narr = (NativeArray) ef.getParamList();
            for (Object o : narr.toArray()) {
                NativeObject nobj = (NativeObject) o;
                DataType paramType = DataType.valueOf(nobj.get("paramType").toString().toUpperCase());
                String paramName = nobj.get("name").toString();
                boolean varargFlag = (Boolean) nobj.get("varargFlag");
                if (varargFlag) {
                    if (paramType == DataType.LIST) {
                        fdb.addListVarargsParameter(paramName);
                    } else {
                        fdb.addDictionaryVarargsParameter(paramName);
                    }
                } else {
                    Object defVal = nobj.get("defaultVal");
                    if (defVal != null) {
                        fdb.addParameter(paramName, paramType, convertToDataValue(scope, defVal));
                    } else {
                        fdb.addParameter(paramName, paramType);
                    }
                }

                JavaScriptFunction jsf = new JavaScriptFunction(ef.getJsFunctionName(), fdb.toFunctionDefinition());
                functions.add(jsf);
            }
        }

        return functions;
    }

    /**
     * Evaluates the passed in JavaScript scripts and returns any functions that are exported to the
     * RPTools scripting language. If the scope that is passed in is null then a new scope will be created.
     *
     * 
     * @param scope The scope to evaluate the JavaScript scripts in.
     * @param scripts The scripts to evaluate.
     *
     * @return the exported functions.
     *
     * @throws NullPointerException if scripts is null, any of the script names are null or any of
     *         the script bodies are null.
     */
    private Collection<JavaScriptFunction> evaluateJavaScript(Scriptable scope, Map<String, String> scripts) {

        if (scripts == null) {
            throw new NullPointerException("List of scripts to add can not be null.");
        }

        List<JavaScriptFunction> definedFunctions = new ArrayList<>();

        Context jsConext = Context.enter();

        try {
            Scriptable scriptScope;
            if (scope != null) {
                scriptScope = scope;
            } else  {
                scriptScope = jsConext.newObject(sharedScope);
                scriptScope.setPrototype(sharedScope);
                scriptScope.setParentScope(null);
            }

            for (Map.Entry<String, String> script : scripts.entrySet()) {
                if (script.getKey() == null) {
                    throw new NullPointerException("Name of script to add can not be null.");
                }

                if (script.getValue() == null) {
                    throw new NullPointerException("Script body can not be null");
                }

                Object result = jsConext.evaluateString(scriptScope, script.getValue(), script.getKey(), 0, null);
            }


            for (JavaScriptFunction function : getRecentlyDefined(scope)) {
                functionScopes.put(function, scriptScope);
                definedFunctions.add(function);
            }

        } catch (Exception e) {
            jsConext.exit();
            throw e;
        }

        return definedFunctions;

    }

    /**
     * Adds a JavaScript file to the list of available script files and returns any functions
     * that were defined for the RPTools scripting language. All scripts added with this function
     * are added in the same scope.
     *
     * @param scripts Then name of the scripts and the script body's to add.
     *
     * @return any functions that were defined.
     *
     * @throws NullPointerException if scripts is null, or any of the script names or body's are null.
     *
     */
    public Collection<JavaScriptFunction> addJavaScript(Map<String, String> scripts) {

        if (scripts == null) {
            throw new NullPointerException("List of scripts to add can not be null.");
        }

        return evaluateJavaScript(null, scripts);
    }

    /**
     * Adds a JavaScript file to the list od available script files and returns any functions defined
     * for the RPTools scripting language.
     *
     * @param name The name of the script.
     * @param js The body of the script.
     *
     * @return any functions that were defined.
     *
     * @throws  NullPointerException if either argument is null.
     */
    Collection<JavaScriptFunction> addJavaScript(String name, String js) {
        return addJavaScript(Collections.singletonMap(name, js));
    }


    // TODO: temporary
    public void test() {
        Context jsContext = Context.enter();
        try {
            Object convFuncObject = sharedScope.get("rptools" /*DATAVALUE_CONVERSION_FUNCTION*/, sharedScope);
            convFuncObject = ((Scriptable)convFuncObject).get("convertToDataValue", sharedScope) ;
            if (!(convFuncObject instanceof Function)) {
                throw new ScriptFunctionException("Data value conversion function undefined.");
            }
            Function convFunction = (Function) convFuncObject;

            Object convFunctionArgs[] = { 1, DataType.LONG.toString() };
            Object convResult = convFunction.call(jsContext, sharedScope, sharedScope, convFunctionArgs);


            NativeObject obj = (NativeObject) convResult;
            for (Object o : obj.getIds()) {
                System.out.println(o + " = " + obj.get(o.toString(), sharedScope));
            }
        }  catch(Exception e) {
            jsContext.exit();
            e.printStackTrace();
        }

        jsContext.exit();

    }

	
}
