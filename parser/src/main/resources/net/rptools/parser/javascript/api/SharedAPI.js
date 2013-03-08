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
// Creates a new ExportedJSLibrary() object which is used to hold exported JavaScript libraries. JavaScript macros
// will not create this object directly rather they will use the rptools.jslibrary instance of the object.
//
function ExportedJSLibrary() {
    this.exported = {};
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Method to add a library to the exported library list.
//
// Parameters:
//      name            The name of the library
//      version         The version of the library (numeric)
//      libObject       The JavaScript object to export as the public API of the library.
//
ExportedJSLibrary.prototype.exportLibrary = function(name, version, libObject) {
    if (!this.exported["name"]) {
        this.exported["name"] = [];
    }

    var libList = this.exported["name"];

    var found = false;

    for (var i in libList) {
        if (libList[i].version == version) {
            libList[i].lib = libObject;
            found = true;
            break;
        } else if (linList[i].version > version) {
            break;
        }
    }

    if (!found) {
        var lib = { version: version, lib: libObject };
        this.exported["name"].push(lib);
        this.exported["name"].sort(function(a, b) { return a.version - b.version; });
    }

}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Method to retrieve a library from the exported library list. This method will return the lowest versioned library
// that is >= the version you specify.
//
// Parameters:
//      name            The name of the library to get.
//      version         The version of the library to get.
//
ExportedJSLibrary.prototype.getLibrary = function(name, version) {
    if (!this.exported["name"]) {
        this.exported["name"] = [];
    }

    var libList = this.exported["name"];


    for (var i in libList) {
        if (libList[i].version >= version) {
            return libList[i].lib;
        }
    }

    return undefined;
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Create the rptool.jslibrary instance.

rptools.jslibrary = new ExportedJSLibrary();
