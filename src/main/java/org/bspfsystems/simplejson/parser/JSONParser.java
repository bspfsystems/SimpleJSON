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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import org.bspfsystems.simplejson.JSONArray;
import org.bspfsystems.simplejson.JSONObject;
import org.bspfsystems.simplejson.JSONSerializable;
import org.bspfsystems.simplejson.SimpleJSONArray;
import org.bspfsystems.simplejson.SimpleJSONObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides JSON utilities for serializing and deserializing various objects.
 */
public final class JSONParser {
    
    /**
     * The possible states of the {@link JSONParser}.
     */
    private enum ParserState {
    
        /**
         * Pre-parsing.
         */
        INITIAL,
    
        /**
         * Actively parsing (the key of) a {@link JSONObject}.
         */
        PARSING_OBJECT,
    
        /**
         * Actively parsing a {@link JSONArray}.
         */
        PARSING_ARRAY,
    
        /**
         * Parsing a key-value pair inside a {@link JSONObject}.
         */
        PARSING_ENTRY,
    
        /**
         * Error encountered while parsing, a {@link JSONException} should be
         * thrown.
         */
        PARSED_ERROR,
    
        /**
         * Post-parsing.
         */
        DONE;
    }
    
    /**
     * Constructs a new {@link JSONParser}.
     */
    private JSONParser() {
        // Utility class, do not allow instances to be created.
    }
    
    /**
     * Deserializes the given {@link String} data into an {@link Object}.
     * 
     * @param data The JSON data in {@link String} format.
     * @return An {@link Object} containing the deserialized JSON data.
     * @throws JSONException If an error occurs while deserializing the data.
     */
    @NotNull
    public static Object deserialize(@NotNull final String data) throws JSONException {
        return JSONParser.deserialize(new StringReader(data));
    }
    
    /**
     * Deserializes the data in the given {@link Reader} into an {@link Object}.
     * 
     * @param reader The {@link Reader} containing the JSON data.
     * @return An {@link Object} containing the deserialized JSON data.
     * @throws JSONException If an error occurs while deserializing the data.
     */
    @NotNull
    public static Object deserialize(@NotNull final Reader reader) throws JSONException {
        return JSONParser.performDeserialization(reader);
    }
    
    /**
     * Deserializes the given {@link String} data into a {@link JSONArray}.
     *
     * @param data The JSON data in {@link String} format.
     * @return A {@link JSONArray} containing the deserialized JSON data.
     * @throws JSONException If an error occurs while deserializing the data.
     */
    @Nullable
    public static JSONArray deserializeArray(@NotNull final String data) throws JSONException {
        return JSONParser.deserializeArray(new StringReader(data));
    }
    
    /**
     * Deserializes the data in the given {@link Reader} into a
     * {@link JSONArray}.
     *
     * @param reader The {@link Reader} containing the JSON data.
     * @return A {@link JSONArray} containing the deserialized JSON data.
     * @throws JSONException If an error occurs while deserializing the data.
     */
    @Nullable
    public static JSONArray deserializeArray(@NotNull final Reader reader) throws JSONException {
        final Object value = JSONParser.deserialize(reader);
        return value instanceof JSONArray ? (JSONArray) value : null;
    }
    
    /**
     * Deserializes the given {@link String} data into a {@link JSONObject}.
     *
     * @param data The JSON data in {@link String} format.
     * @return A {@link JSONObject} containing the deserialized JSON data.
     * @throws JSONException If an error occurs while deserializing the data.
     */
    @Nullable
    public static JSONObject deserializeObject(@NotNull final String data) throws JSONException {
        return JSONParser.deserializeObject(new StringReader(data));
    }
    
    /**
     * Deserializes the data in the given {@link Reader} into a
     * {@link JSONObject}.
     *
     * @param reader The {@link Reader} containing the JSON data.
     * @return A {@link JSONObject} containing the deserialized JSON data.
     * @throws JSONException If an error occurs while deserializing the data.
     */
    @Nullable
    public static JSONObject deserializeObject(@NotNull final Reader reader) throws JSONException {
        final Object value = JSONParser.deserialize(reader);
        return value instanceof JSONObject ? (JSONObject) value : null;
    }
    
    /**
     * Deserializes the data in the given {@link Reader} according to the RFC
     * 7159 JSON specification.
     * 
     * @param reader The {@link Reader} with the data to deserialize.
     * @return An {@link Object} containing the parsed data.
     * @throws JSONException If an unexpected token is encountered in the data.
     */
    @NotNull
    private static Object performDeserialization(@NotNull final Reader reader) throws JSONException {
    
        final JSONReader jsonReader = new JSONReader(reader);
        JSONToken jsonToken;
        JSONParser.ParserState currentState;
        int returnCount = 1;
        
        final Stack<Object> valueStack = new Stack<Object>();
        final Stack<ParserState> stateStack = new Stack<JSONParser.ParserState>();
    
        stateStack.push(JSONParser.ParserState.INITIAL);
    
        do {
    
            try {
                currentState = stateStack.pop();
            } catch (EmptyStackException e) {
                currentState = JSONParser.ParserState.PARSED_ERROR;
            }
            jsonToken = JSONParser.readNextToken(jsonReader);
        
            switch (currentState) {
    
                // Finished parsing
                case DONE:
                    if (JSONToken.Type.END.equals(jsonToken.getType())) {
                        break;
                    }
                    returnCount++;
    
                    // Start parsing
                case INITIAL:
                    switch (jsonToken.getType()) {
    
                        // Actual data
                        case DATUM:
                            valueStack.push(jsonToken.getValue());
                            stateStack.push(JSONParser.ParserState.DONE);
                            break;
    
                        // Beginning of a JSONObject
                        case LEFT_BRACE:
                            valueStack.push(new SimpleJSONObject());
                            stateStack.push(JSONParser.ParserState.PARSING_OBJECT);
                            break;
    
                        // Beginning of a List
                        case LEFT_SQUARE:
                            valueStack.push(new SimpleJSONArray());
                            stateStack.push(JSONParser.ParserState.PARSING_ARRAY);
                            break;
    
                        // Something unexpected
                        default:
                            throw new JSONException("Unexpected token \"" + jsonToken + "\" at position " + jsonReader.getPosition() + ". Please fix the String and try to parse again.");
                    }
                    break;
    
                // Error while parsing
                case PARSED_ERROR:
                    throw new JSONException("Unexpected token \"" + jsonToken + "\" at position " + jsonReader.getPosition() + ". Please fix the String and try to parse again.");
    
                // Parsing a List
                case PARSING_ARRAY:
                    switch (jsonToken.getType()) {
    
                        // Next item in a List
                        case COMMA:
                            stateStack.push(currentState);
                            break;
    
                        // Actual data in the List
                        case DATUM:
                            JSONArray parentJSONArray = (JSONArray) valueStack.pop();
                            parentJSONArray.addEntry(jsonToken.getValue());
                            valueStack.push(parentJSONArray);
                            stateStack.push(currentState);
                            break;
    
                        // New JSONObject in the List
                        case LEFT_BRACE:
                            parentJSONArray = (JSONArray) valueStack.pop();
                            final JSONObject jsonObject = new SimpleJSONObject();
                            parentJSONArray.addEntry(jsonObject);
                            valueStack.push(parentJSONArray);
                            valueStack.push(jsonObject);
                            stateStack.push(currentState);
                            stateStack.push(JSONParser.ParserState.PARSING_OBJECT);
                            break;
    
                        // New List in the List
                        case LEFT_SQUARE:
                            parentJSONArray = (JSONArray) valueStack.pop();
                            final JSONArray jsonArray = new SimpleJSONArray();
                            parentJSONArray.addEntry(jsonArray);
                            valueStack.push(parentJSONArray);
                            valueStack.push(jsonArray);
                            stateStack.push(currentState);
                            stateStack.push(JSONParser.ParserState.PARSING_ARRAY);
                            break;
    
                        // End of List
                        case RIGHT_SQUARE:
                            if (valueStack.size() > returnCount) {
                                valueStack.pop();
                            } else {
                                stateStack.push(JSONParser.ParserState.DONE);
                            }
                            break;
    
                        // Something unexpected
                        default:
                            throw new JSONException("Unexpected token \"" + jsonToken + "\" at position " + jsonReader.getPosition() + ". Please fix the String and try to parse again.");
                    }
                    break;
    
                // Parsing a JSONObject
                case PARSING_OBJECT:
                    switch (jsonToken.getType()) {
    
                        // Next item in the JSONObject
                        case COMMA:
                            stateStack.push(currentState);
                            break;
    
                        // Actual data in the JSONObject
                        case DATUM:
                            if (jsonToken.getValue() instanceof String) {
                                final String key = (String) jsonToken.getValue();
                                valueStack.push(key);
                                stateStack.push(currentState);
                                stateStack.push(JSONParser.ParserState.PARSING_ENTRY);
                            } else {
                                throw new JSONException("Unexpected token \"" + jsonToken + "\" at position " + jsonReader.getPosition() + ". Please fix the String and try to parse again.");
                            }
                            break;
    
                        // End of JSONObject
                        case RIGHT_BRACE:
                            if (valueStack.size() > returnCount) {
                                valueStack.pop();
                            } else {
                                stateStack.push(JSONParser.ParserState.DONE);
                            }
                            break;
    
                        // Something unexpected
                        default:
                            throw new JSONException("Unexpected token \"" + jsonToken + "\" at position " + jsonReader.getPosition() + ". Please fix the String and try to parse again.");
                    }
                    break;
    
                // Parsing something in the data
                case PARSING_ENTRY:
                    switch (jsonToken.getType()) {
    
                        // Continue parsing
                        case COLON:
                            stateStack.push(currentState);
                            break;
    
                        // Actual data
                        case DATUM:
                            String key = (String) valueStack.pop();
                            JSONObject parentJSONObject = (JSONObject) valueStack.pop();
                            parentJSONObject.set(key, jsonToken.getValue());
                            valueStack.push(parentJSONObject);
                            break;
    
                        // Start of a JSONObject
                        case LEFT_BRACE:
                            key = (String) valueStack.pop();
                            parentJSONObject = (JSONObject) valueStack.pop();
                            final JSONObject jsonObject = new SimpleJSONObject();
                            parentJSONObject.set(key, jsonObject);
                            valueStack.push(parentJSONObject);
                            valueStack.push(jsonObject);
                            stateStack.push(JSONParser.ParserState.PARSING_OBJECT);
                            break;
    
                        // Start of a List
                        case LEFT_SQUARE:
                            key = (String) valueStack.pop();
                            parentJSONObject = (JSONObject) valueStack.pop();
                            final JSONArray list = new SimpleJSONArray();
                            parentJSONObject.set(key, list);
                            valueStack.push(parentJSONObject);
                            valueStack.push(list);
                            stateStack.push(JSONParser.ParserState.PARSING_ARRAY);
                            break;
    
                        // Something unexpected
                        default:
                            throw new JSONException("Unexpected token \"" + jsonToken + "\" at position " + jsonReader.getPosition() + ". Please fix the String and try to parse again.");
                    }
                    break;
                default:
                    break;
            }
        } while (!(JSONParser.ParserState.DONE.equals(currentState) && JSONToken.Type.END.equals(jsonToken.getType())));
        
        return valueStack.pop();
    }
    
    /**
     * Serializes the given {@link Object} into {@link String} data.
     *
     * @param data The data as an {@link Object} to serialize into JSON format.
     * @return A {@link String} representation of the given data.
     * @throws JSONException If an error occurs while serializing the data.
     */
    @NotNull
    public static String serialize(@Nullable final Object data) throws JSONException {
        
        final StringBuilder builder = new StringBuilder();
        
        if (data == null) {
            builder.append("null");
        } else if (data instanceof JSONSerializable) {
            builder.append(((JSONSerializable) data).serialize());
        } else if (data instanceof String) {
            builder.append("\"");
            builder.append(JSONParser.escape((String) data));
            builder.append("\"");
        } else if (data instanceof Character) {
            builder.append(JSONParser.escape(data.toString()));
        } else if (data instanceof Double) {
            final Double doubleData = (Double) data;
            if (doubleData.isInfinite() || doubleData.isNaN()) {
                builder.append("null");
            } else {
                builder.append(doubleData);
            }
        } else if (data instanceof Float) {
            final Float floatData = (Float) data;
            if (floatData.isInfinite() || floatData.isNaN()) {
                builder.append("null");
            } else {
                builder.append(floatData);
            }
        } else if (data instanceof Number) {
            builder.append(data);
        } else if (data instanceof Boolean) {
            builder.append(data);
        } else if (data instanceof Map) {
            
            final Iterator<? extends Map.Entry<?, ?>> iterator = ((Map<?, ?>) data).entrySet().iterator();
            int index = 1;
            
            builder.append("{");
            while (iterator.hasNext()) {
                if (index > 1) {
                    builder.append(",");
                }
                final Map.Entry<?, ?> entry = iterator.next();
                builder.append(JSONParser.serialize(entry.getKey()));
                builder.append(":");
                builder.append(JSONParser.serialize(entry.getValue()));
                index++;
            }
    
            builder.append("}");
            
        } else if (data instanceof Collection) {
            
            final Iterator<?> iterator = ((Collection<?>) data).iterator();
            int index = 1;
            
            builder.append("[");
            while (iterator.hasNext()) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(iterator.next()));
                index++;
            }
    
            builder.append("]");
        } else if (data instanceof boolean[]) {
            
            int index = 1;
            builder.append("[");
            for (final boolean value : (boolean[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof byte[]) {
    
            int index = 1;
            builder.append("[");
            for (final byte value : (byte[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof short[]) {
    
            int index = 1;
            builder.append("[");
            for (final short value : (short[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof int[]) {
    
            int index = 1;
            builder.append("[");
            for (final int value : (int[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof long[]) {
    
            int index = 1;
            builder.append("[");
            for (final long value : (long[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof float[]) {
    
            int index = 1;
            builder.append("[");
            for (final float value : (float[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof double[]) {
    
            int index = 1;
            builder.append("[");
            for (final double value : (double[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof char[]) {
    
            int index = 1;
            builder.append("[");
            for (final char value : (char[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else if (data instanceof Object[]) {
    
            int index = 1;
            builder.append("[");
            for (final Object value : (Object[]) data) {
                if (index > 1) {
                    builder.append(",");
                }
                builder.append(JSONParser.serialize(value));
                index++;
            }
            builder.append("]");
            
        } else {
            throw new JSONException("Encountered a " + data.getClass().getName() + " as " + data + ", which is not serializable as JSON.");
        }
        
        return builder.toString();
    }
    
    /**
     * Formats the given {@link String} JSON data into a human-readable format
     * using indentation and newlines. The indentation character used is
     * {@code "\t"}, while the newline character used is {@code "\n"}.
     * 
     * @param data The JSON data as a {@link String}.
     * @return The formatted JSON data as a {@link String}.
     * @throws JSONException If an error occurs while formatting the data.
     */
    @NotNull
    public static String format(@NotNull final String data) throws JSONException {
        return JSONParser.format(new StringReader(data));
    }
    
    /**
     * Formats the JSON data in the given {@link Reader} into a human-readable
     * format using indentation and newlines. The indentation character used is
     * {@code "\t"}, while the newline character used is {@code "\n"}.
     * 
     * @param reader The {@link Reader} containing the JSON data.
     * @return The formatted JSON data as a {@link String}.
     * @throws JSONException If an error occurs while formatting the data.
     */
    @NotNull
    public static String format(@NotNull final Reader reader) throws JSONException {
        return JSONParser.format(reader, "\t", "\n");
    }
    
    /**
     * Formats the given {@link String} JSON data into a human-readable format
     * using indentation and newlines, both of the caller's choice. This means
     * the validity of the formatted JSON is dependent on the caller's choice of
     * indentation and newlines.
     * 
     * @param data The JSON data as a {@link String}.
     * @param indent The {@link String} to use as an indent.
     * @param newline The {@link String} to use as a newline.
     * @return The formatted JSON data as a {@link String}.
     * @throws JSONException If an error occurs while formatting the data.
     */
    @NotNull
    public static String format(@NotNull final String data, @NotNull final String indent, @NotNull final String newline) throws JSONException {
        return JSONParser.format(new StringReader(data), indent, newline);
    }
    
    /**
     * Formats the gSON data in the given {@link Reader} into a human-readable
     * format using indentation and newlines, both of the caller's choice. This
     * means the validity of the formatted JSON is dependent on the caller's
     * choice of indentation and newlines.
     *
     * @param reader The {@link Reader} containing the JSON data.
     * @param indent The {@link String} to use as an indent.
     * @param newline The {@link String} to use as a newline.
     * @return The formatted JSON data as a {@link String}.
     * @throws JSONException If an error occurs while formatting the data.
     */
    @NotNull
    public static String format(@NotNull final Reader reader, @NotNull final String indent, @NotNull final String newline) throws JSONException {
        
        final StringBuilder builder = new StringBuilder();
        final JSONReader jsonReader = new JSONReader(reader);
        JSONToken jsonToken;
        int level = 0;
        do {
            
            jsonToken = JSONParser.readNextToken(jsonReader);
            if (jsonToken.getValue() == null && jsonToken.getType() != JSONToken.Type.DATUM) {
                throw new JSONException("Invalid null JSON token of type " + jsonToken.getType().name());
            }
            
            switch (jsonToken.getType()) {
                case COLON:
                    builder.append(jsonToken.getValue());
                    break;
                case COMMA:
                    builder.append(jsonToken.getValue());
                    builder.append(newline);
                    for (int index = 0; index < level; index++) {
                        builder.append(indent);
                    }
                    break;
                case END:
                    break;
                case LEFT_BRACE:
                case LEFT_SQUARE:
                    builder.append(jsonToken.getValue());
                    builder.append(newline);
                    level++;
                    for (int index = 0; index < level; index++) {
                        builder.append(indent);
                    }
                    break;
                case RIGHT_BRACE:
                case RIGHT_SQUARE:
                    builder.append(newline);
                    level--;
                    for (int index = 0; index < level; index++) {
                        builder.append(indent);
                    }
                    builder.append(jsonToken.getValue());
                    break;
                default:
                    if (jsonToken.getValue() == null) {
                        builder.append("null");
                    } else if (jsonToken.getValue() instanceof String) {
                        builder.append("\"");
                        builder.append(JSONParser.escape((String) jsonToken.getValue()));
                        builder.append("\"");
                    } else {
                        builder.append(jsonToken.getValue());
                    }
                    break;
            }
        } while (!jsonToken.getType().equals(JSONToken.Type.END));
        
        return builder.toString();
    }
    
    /**
     * Escapes potentially confusing or important characters in the
     * {@link String} provided.
     *
     * @param rawData The raw data that needs to be parsed for escapable
     *                character.
     * @return The formatted {@link String} with the characters escaped as
     *         required.
     */
    private static String escape(@NotNull final String rawData) {
        
        final StringBuilder builder = new StringBuilder();
        final int length = rawData.length();
        for (int index = 0; index < length; index++) {
            
            final char character = rawData.charAt(index);
            switch (character) {
                case '"':
                    builder.append("\\\"");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                case '/':
                    builder.append("\\/");
                    break;
                default:
                    if (((character >= '\u0000') && (character <= '\u001F')) || ((character >= '\u007F') && (character <= '\u009F')) || ((character >= '\u2000') && (character <= '\u20FF'))) {
                        final String hexCode = Integer.toHexString(character);
                        builder.append("\\u");
                        for (int hexIndex = 0; hexIndex < (4 - hexCode.length()); hexIndex++) {
                            builder.append("0");
                        }
                        builder.append(hexCode.toUpperCase());
                    } else {
                        builder.append(character);
                    }
            }
        }
        
        return builder.toString();
    }
    
    /**
     * Processes the {@link JSONReader}'s next token.
     *
     * @param jsonReader The {@link JSONReader} text processor being used for the
     *              deserialization process.
     * @return A {@link JSONToken} representing the meaningful element encountered
     *         by the {@link JSONReader}.
     * @throws JSONException If an unexpected character is encountered.
     */
    private static JSONToken readNextToken(@NotNull final JSONReader jsonReader) throws JSONException {
        JSONToken value;
        try {
            value = jsonReader.read();
        } catch (IOException e) {
            throw new JSONException("An unexpected exception was caught. Please report this to the library's maintainer, as it will need to be addressed before trying to parse again.", e);
        }
        if (value == null) {
            value = new JSONToken(JSONToken.Type.END, null);
        }
        return value;
    }
}
