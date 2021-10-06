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

package org.bspfsystems.simplejson;

import java.util.Iterator;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that contains key-value pairings in JSON object format
 * and can be serialized according to RFC 7159 JSON specification.
 */
public interface JSONObject extends JSONSerializable, Iterable<Map.Entry<String, Object>> {
    
    /**
     * Gets the size of this {@link JSONObject}.
     * 
     * @return The size of this {@link JSONObject}.
     */
    int size();
    
    /**
     * Gets an {@link Iterator} over the entries in this {@link JSONObject}.
     *
     * @return An {@link Iterator} over the entries in this {@link JSONObject}.
     */
    @NotNull
    Iterator<Map.Entry<String, Object>> iterator();
    
    /**
     * Associates the given key to the given value.
     * <p>
     * If the given key was already associated with another value, including
     * {@code null}, that value will be overwritten.
     * 
     * @param key The key to set.
     * @param value The value to set.
     */
    void set(@NotNull final String key, @Nullable final Object value);
    
    /**
     * Removes the given key and its associated value (including a {@code null}
     * value).
     * <p>
     * If the key is not present, no action will be taken.
     * 
     * @param key The key to remove.
     */
    void unset(@NotNull final String key);
    
    /**
     * Checks if there is a null value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key-value entry for the key and the value is not {@code null}.
     * <p>
     * This will return {@code true} if the value associated with the key is
     * {@code null} (the key must be present), regardless of what type of
     * {@link Object} the value is supposed to be.
     * 
     * @param key The key to check.
     * @return {@code true} if there is a {@code null} value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isNull(@NotNull final String key);
    
    /**
     * Checks if there is a boolean value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * boolean.
     *
     * @param key The key to check.
     * @return {@code true} if there is a boolean value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isBoolean(@NotNull final String key);
    
    /**
     * Gets the boolean value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a boolean, {@code false} will be returned.
     *
     * @param key The key of the boolean to retrieve.
     * @return The associated boolean value.
     * @see JSONObject#getBoolean(String, boolean)
     */
    boolean getBoolean(@NotNull final String key);
    
    /**
     * Gets the boolean value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a boolean, the default value will be returned.
     *
     * @param key The key of the boolean to retrieve.
     * @param def The default value.
     * @return The associated boolean value.
     */
    boolean getBoolean(@NotNull final String key, final boolean def);
    
    /**
     * Checks if there is a byte value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * byte.
     *
     * @param key The key to check.
     * @return {@code true} if there is a byte value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isByte(@NotNull final String key);
    
    /**
     * Gets the byte value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a byte, {@code (byte) 0} will be returned.
     *
     * @param key The key of the byte to retrieve.
     * @return The associated byte value.
     * @see JSONObject#getByte(String, byte)
     */
    byte getByte(@NotNull final String key);
    
    /**
     * Gets the byte value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a byte, the default value will be returned.
     *
     * @param key The key of the byte to retrieve.
     * @param def The default value.
     * @return The associated byte value.
     */
    byte getByte(@NotNull final String key, final byte def);
    
    /**
     * Checks if there is a short value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * short.
     *
     * @param key The key to check.
     * @return {@code true} if there is a short value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isShort(@NotNull final String key);
    
    /**
     * Gets the short value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a short, {@code (short) 0} will be returned.
     *
     * @param key The key of the short to retrieve.
     * @return The associated short value.
     * @see JSONObject#getShort(String, short)
     */
    short getShort(@NotNull final String key);
    
    /**
     * Gets the short value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a short, the default value will be returned.
     *
     * @param key The key of the short to retrieve.
     * @param def The default value.
     * @return The associated short value.
     */
    short getShort(@NotNull final String key, final short def);
    
    /**
     * Checks if there is an integer value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not an
     * integer.
     *
     * @param key The key to check.
     * @return {@code true} if there is an integer value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isInteger(@NotNull final String key);
    
    /**
     * Gets the integer value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not an integer, {@code 0} will be returned.
     *
     * @param key The key of the integer to retrieve.
     * @return The associated integer value.
     * @see JSONObject#getInteger(String, int)
     */
    int getInteger(@NotNull final String key);
    
    /**
     * Gets the integer value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not an integer, the default value will be returned.
     *
     * @param key The key of the integer to retrieve.
     * @param def The default value.
     * @return The associated integer value.
     */
    int getInteger(@NotNull final String key, final int def);
    
    /**
     * Checks if there is a long value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * long.
     *
     * @param key The key to check.
     * @return {@code true} if there is a long value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isLong(@NotNull final String key);
    
    /**
     * Gets the long value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a long, {@code 0L} will be returned.
     *
     * @param key The key of the long to retrieve.
     * @return The associated long value.
     * @see JSONObject#getLong(String, long)
     */
    long getLong(@NotNull final String key);
    
    /**
     * Gets the long value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a long, the default value will be returned.
     *
     * @param key The key of the long to retrieve.
     * @param def The default value.
     * @return The associated long value.
     */
    long getLong(@NotNull final String key, final long def);
    
    /**
     * Checks if there is a float value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * float.
     *
     * @param key The key to check.
     * @return {@code true} if there is a float value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isFloat(@NotNull final String key);
    
    /**
     * Gets the float value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a float, {@code 0.0F} will be returned.
     *
     * @param key The key of the float to retrieve.
     * @return The associated float value.
     * @see JSONObject#getFloat(String, float)
     */
    float getFloat(@NotNull final String key);
    
    /**
     * Gets the float value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a float, the default value will be returned.
     *
     * @param key The key of the float to retrieve.
     * @param def The default value.
     * @return The associated float value.
     */
    float getFloat(@NotNull final String key, final float def);
    
    /**
     * Checks if there is a double value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * double.
     *
     * @param key The key to check.
     * @return {@code true} if there is a double value associated with the
     *         given key, {@code false} otherwise.
     */
    boolean isDouble(@NotNull final String key);
    
    /**
     * Gets the double value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a double, {@code 0.0D} will be returned.
     *
     * @param key The key of the double to retrieve.
     * @return The associated double value.
     * @see JSONObject#getDouble(String, double)
     */
    double getDouble(@NotNull final String key);
    
    /**
     * Gets the double value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a double, the default value will be returned.
     *
     * @param key The key of the double to retrieve.
     * @param def The default value.
     * @return The associated double value.
     */
    double getDouble(@NotNull final String key, final double def);
    
    /**
     * Checks if there is a {@link String} value associated with the given key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * {@link String}.
     * 
     * @param key The key to check
     * @return {@code true} if there is a {@link String} value associated with
     *         the given key, {@code false} otherwise.
     */
    boolean isString(@NotNull final String key);
    
    /**
     * Gets the {@link String} value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a {@link String}, {@code null} will be returned.
     * 
     * @param key The key of the {@link String} to retrieve.
     * @return The associated {@link String} value.
     * @see JSONObject#getString(String, String)
     */
    @Nullable
    String getString(@NotNull final String key);
    
    /**
     * Gets the {@link String} value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a {@link String}, the default value will be
     * returned.
     * 
     * @param key The key of the {@link String} to retrieve.
     * @param def The default value.
     * @return The associated {@link String} value.
     */
    @Contract("_, !null -> !null")
    @Nullable
    String getString(@NotNull final String key, @Nullable final String def);
    
    /**
     * Checks if there is a {@link JSONArray} value associated with the given
     * key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * {@link JSONArray}.
     *
     * @param key The key to check
     * @return {@code true} if there is a {@link JSONArray} value associated
     *         with the given key, {@code false} otherwise.
     */
    boolean isArray(@NotNull final String key);
    
    /**
     * Gets the {@link JSONArray} value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a {@link JSONArray}, {@code null} will be
     * returned.
     *
     * @param key The key of the {@link JSONArray} to retrieve.
     * @return The associated {@link JSONArray} value.
     * @see JSONObject#getArray(String, JSONArray)
     */
    @Nullable
    JSONArray getArray(@NotNull final String key);
    
    /**
     * Gets the {@link JSONArray} value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a {@link JSONArray}, the default value will be
     * returned.
     *
     * @param key The key of the {@link JSONArray} to retrieve.
     * @param def The default value.
     * @return The associated {@link JSONArray} value.
     */
    @Contract("_, !null -> !null")
    @Nullable
    JSONArray getArray(@NotNull final String key, @Nullable final JSONArray def);
    
    /**
     * Checks if there is a {@link JSONObject} value associated with the given
     * key.
     * <p>
     * This will return {@code false} if there is no matching key, or if there
     * is a key but no value set ({@code null}), or if the value is not a
     * {@link JSONObject}.
     *
     * @param key The key to check
     * @return {@code true} if there is a {@link JSONObject} value associated
     *         with the given key, {@code false} otherwise.
     */
    boolean isObject(@NotNull final String key);
    
    /**
     * Gets the {@link JSONObject} value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a {@link JSONObject}, {@code null} will be
     * returned.
     *
     * @param key The key of the {@link JSONObject} to retrieve.
     * @return The associated {@link JSONObject} value.
     * @see JSONObject#getObject(String, JSONObject)
     */
    @Nullable
    JSONObject getObject(@NotNull final String key);
    
    /**
     * Gets the {@link JSONObject} value associated with the given key.
     * <p>
     * If there is no such key present, no value present ({@code null}), or the
     * associated value is not a {@link JSONObject}, the default value will be
     * returned.
     *
     * @param key The key of the {@link JSONObject} to retrieve.
     * @param def The default value.
     * @return The associated {@link JSONObject} value.
     */
    @Contract("_, !null -> !null")
    @Nullable
    JSONObject getObject(@NotNull final String key, @Nullable final JSONObject def);
}
