/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.uniulm.omi.cloudiator.lance.application;

import java.io.Serializable;
import java.util.UUID;

public final class ApplicationId implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID uuid;
    
    public ApplicationId(){
        uuid = UUID.randomUUID();
    }
    
    private ApplicationId(String s){
        uuid = UUID.fromString(s);
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof ApplicationId)) 
            return false; // captures null
        ApplicationId that = (ApplicationId) o;
        return this.uuid.equals(that.uuid);
    }
    
    @Override 
    public int hashCode() {
        return uuid.hashCode();
    }
    
    @Override
    public String toString(){
        return uuid.toString();
    }
    
    public static ApplicationId fromString(String s){
        return new ApplicationId(s);
    }
}
