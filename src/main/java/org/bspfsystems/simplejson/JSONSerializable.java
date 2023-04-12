/* 
 * This file is part of the SimpleJSON Java library.
 * 
 * It is based on Clifton Labs' version
 * (https://github.com/cliftonlabs/json-simple/), which is a fork of
 * the original by Yidong Fang (https://github.com/fangyidong/json-simple/).
 * Other authors contributions remain the copyright of the respective
 * owner, with the major ones of this derivative listed below.
 * 
 * Copyright 2008-2009,2012-2014,2021 Yidong Fang
 * Copyright 2008-2009,2012-2014,2016-2022 Clifton Labs
 * Copyright 2021-2023 BSPF Systems, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bspfsystems.simplejson;

import org.bspfsystems.simplejson.parser.JSONException;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object that can be serialized according to the RFC 7159 JSON
 * specification.
 */
public interface JSONSerializable {
    
    /**
     * Serializes this {@link JSONSerializable} into a string.
     * 
     * @return The serialized JSON data as a {@link String}.
     * @throws JSONException If an error occurs while serializing the data.
     */
    @NotNull
    String serialize() throws JSONException;
}
