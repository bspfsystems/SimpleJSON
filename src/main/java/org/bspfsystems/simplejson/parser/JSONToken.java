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
 * Copyright 2008-2009,2012-2014,2016-2021 Clifton Labs
 * Copyright 2021 BSPF Systems, LLC
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents structural entities in JSON.
 */
final class JSONToken {
    
    /**
     * Represents various types of tokens.
     */
    enum Type {
        
        COLON(":"),
        COMMA(","),
        DATUM(null),
        END(""),
        LEFT_BRACE("{"),
        LEFT_SQUARE("["),
        RIGHT_BRACE("}"),
        RIGHT_SQUARE("]");
        
        private final String string;
        
        /**
         * Constructs a new {@link JSONToken.Type} with the given {@link String}
         * value.
         * 
         * @param string The {@link String} representation of the
         *               {@link JSONToken.Type}.
         */
        private Type(@Nullable final String string) {
            this.string = string;
        }
    
        /**
         * Gets the {@link String} representation of this {@link JSONToken.Type}.
         * 
         * @return The {@link String} representation of this {@link JSONToken.Type}.
         */
        @Override
        @Nullable
        public String toString() {
            return this.string;
        }
    }
    
    private final Type type;
    private final Object value;
    
    /**
     * Constructs a new {@link JSONToken} with the given {@link Type} and default
     * value.
     * 
     * @param type The {@link Type} to assign to the {@link JSONToken}.
     * @param value The value {@link Object} to assign to the {@link JSONToken}.
     */
    JSONToken(@NotNull final Type type, @Nullable final Object value) {
    
        this.type = type;
        switch (this.type) {
            case COLON:
            case COMMA:
            case END:
            case LEFT_BRACE:
            case LEFT_SQUARE:
            case RIGHT_BRACE:
            case RIGHT_SQUARE:
                this.value = this.type.toString();
                break;
            case DATUM:
            default:
                this.value = value;
                break;
        }
    }
    
    /**
     * Gets the {@link Type} of this {@link JSONToken}.
     * 
     * @return The {@link Type} of this {@link JSONToken}.
     */
    @NotNull
    Type getType() {
        return this.type;
    }
    
    /**
     * Gets the value {@link Object} for this {@link JSONToken}.
     * 
     * @return The value {@link Object} for this {@link JSONToken}.
     */
    @Nullable
    Object getValue() {
        return this.value;
    }
    
    /**
     * Gets the {@link String} representation of this {@link JSONToken}.
     * 
     * @return The {@link String} representation of this {@link JSONToken}.
     */
    @Override
    @NotNull
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.type.toString()).append("(").append(this.value.toString()).append(")");
        return builder.toString();
    }
}
