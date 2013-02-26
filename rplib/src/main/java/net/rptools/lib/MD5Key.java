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
package net.rptools.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Represents the MD5 key for data.
 * The MD5 Key is used to uniquely identified data that may be stored in
 * multiple places or even multiple machines.
 */
final public class MD5Key implements Serializable {

    
	
	/** Serial version UID. */
	private static final long serialVersionUID = 5451535120110411802L;
	
	
	/** The id of of this instance. */
    final String id;
            
    /** 
     * Creates a new MD5Key from the specified String.
     * This takes the string as the id value of the key, it does not
     * calculate a new value of the key based on the contents of the
     * String.
     * 
     * @param md5 The id of the MD5Key.
     */
    private MD5Key(String md5) {
    	id = md5;
    }
    
    
    /**
     * Creates a new instance of <code>MD5Key</code> with the id based on the
     * data. 
     * 
     * @param data The data used to generate the key.
     */
    public MD5Key (byte[] data) {
    	id = DigestUtils.md5Hex(data);
    }
    
    /**
     * Creates a new instance of <code>MD5Key</code> with the id based on the
     * data read from the <code>InputStream</code>.
     * 
     * @param input the input stream to generate the id based on.
     * 
     * @throws IOException when an error occurs reading the 
     *                     <code>InputStream</code>.
     */
    public MD5Key (InputStream input) throws IOException {
    	id = DigestUtils.md5Hex(input);
    }
    
    /**
     * Gets the <code>String</code> id of the <code>MD5Key</code>.
     */
    public String toString() {
        return id;
    }
    
    
    
    /**
     * Creates and instance of <code>MD5Key</code> from the string 
     * representation as returned by {@link #toString()}.
     *
     * @param str The <codeString</code> representation of the 
     * <code>MD5Key</code>.
     * 
     */
    public static MD5Key fromString(String str) {
    	return new MD5Key(str);
    }
   
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MD5Key)) {
            return false;
        }
        
        return id.equals(((MD5Key) obj).id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
   
}
