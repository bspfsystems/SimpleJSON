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

package org.bspfsystems.simplejson.parser;

/**
 * Represents an {@link Exception} that is JSON-specific.
 */
public final class JSONException extends Exception {
    
    /**
     * Constructs a new {@link JSONException}.
     * 
     * @see Exception#Exception()
     */
    public JSONException() {
        super();
    }
    
    /**
     * Constructs a new {@link JSONException} with the given error message.
     * 
     * @param message The error message to display as part of the
     *                {@link JSONException}.
     * @see Exception#Exception(String)
     */
    public JSONException(final String message) {
        super(message);
    }
    
    /**
     * Constructs a new {@link JSONException} with the given {@link Throwable}
     * as the cause of the {@link JSONException}.
     * 
     * @param cause The {@link Throwable} to use as the cause.
     * @see Exception#Exception(Throwable)
     */
    public JSONException(final Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs a new {@link JSONException} with the given error message and
     * {@link Throwable} as the cause of the {@link JSONException}.
     * 
     * @param message The error message to display as part of the
     *                {@link JSONException}.
     * @param cause The {@link Throwable} to use as the cause.
     * @see Exception#Exception(String, Throwable)
     */
    public JSONException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
