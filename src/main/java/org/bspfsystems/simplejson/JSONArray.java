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

import java.util.Iterator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that contains listed objects in JSON array format and
 * can be serialized according to the RFC 7159 JSON specification.
 */
public interface JSONArray extends JSONSerializable, Iterable<Object> {
    
    /**
     * Gets the size of this {@link JSONArray}.
     * 
     * @return The size of this {@link JSONArray}.
     */
    int size();
    
    /**
     * Gets an {@link Iterator} over the entries in this {@link JSONArray}.
     * 
     * @return An {@link Iterator} over the entries in this {@link JSONArray}.
     */
    @NotNull
    Iterator<Object> iterator();
    
    /**
     * Checks if the given index contains any value, including {@code null}.
     * <p>
     * This will return {@code true} for any index within the range of {@code 0}
     * to {@code #size() - 1}, and {@code false} for any other value.
     * 
     * @param index The index to check for any value, including {@code null}.
     * @return {@code true} if the index is within the size range of this
     *         {@link JSONArray}, {@code false} otherwise.
     */
    boolean isSet(final int index);
    
    /**
     * Adds the given value at the end of this {@link JSONArray}.
     * 
     * @param value The value to add.
     */
    void addEntry(@Nullable final Object value);
    
    /**
     * Inserts the given value at the given index, adding 1 to the index of
     * every entry after the given index.
     * <p>
     * If the given index is less than 0 or greater than the current size of
     * this {@link JSONArray} minus 1, an {@link IndexOutOfBoundsException} will
     * be thrown.
     * 
     * @param index The index to insert into.
     * @param value The value to insert.
     */
    void insertEntry(final int index, @Nullable final Object value) throws IndexOutOfBoundsException;
    
    /**
     * Sets the given index to the given value, overwriting any other value that
     * was previously set there.
     * <p>
     * If the given index is less than 0 or greater than the current size of
     * this {@link JSONArray} minus 1, an {@link IndexOutOfBoundsException} will
     * be thrown.
     * 
     * @param index The index to set at.
     * @param value The value to set.
     */
    void setEntry(final int index, @Nullable final Object value) throws IndexOutOfBoundsException;
    
    /**
     * Removes the entry at the given index, subtracting 1 from the index of
     * every entry after the given index.
     * <p>
     * If the given index is less than 0 or greater than the current size of
     * this {@link JSONArray} minus 1, an {@link IndexOutOfBoundsException} will
     * be thrown.
     * 
     * @param index The index of the entry to remove.
     */
    void unsetEntry(final int index) throws IndexOutOfBoundsException;
    
    /**
     * Checks if there is a null value at the given index.
     * <p>
     * This will return {@code false} if the index is outside this
     * {@link JSONArray}'s bounds, or if there is an entry at the index and the
     * value is not {@code null}.
     * <p>
     * This will return {@code true} if the data at the index is {@code null}
     * (the index must be within this {@link JSONArray}'s bounds), regardless of
     * what type of {@link Object} the entry is supposed to be.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a {@code null} entry at the given index,
     *         {@code false} otherwise.
     */
    boolean isNull(final int index);
    
    /**
     * Checks if there is a boolean value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a boolean.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a boolean at the given index,
     *         {@code false} otherwise.
     */
    boolean isBoolean(final int index);
    
    /**
     * Gets the boolean value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a boolean, {@code false}
     * will be returned.
     * 
     * @param index The index of the boolean to retrieve.
     * @return The boolean value at the index.
     * @see JSONArray#getBoolean(int, boolean)
     */
    boolean getBoolean(final int index);
    
    /**
     * Gets the boolean value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a boolean, the default
     * value will be returned.
     * 
     * @param index The index of the boolean to retrieve.
     * @param def The default value.
     * @return The boolean value at the index.
     */
    boolean getBoolean(final int index, final boolean def);
    
    /**
     * Checks if there is a byte value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a byte.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a byte at the given index,
     *         {@code false} otherwise.
     */
    boolean isByte(final int index);
    
    /**
     * Gets the byte value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a byte, {@code (byte) 0}
     * will be returned.
     * 
     * @param index The index of the byte to retrieve.
     * @return The byte value at the index.
     * @see JSONArray#getByte(int, byte)
     */
    byte getByte(final int index);
    
    /**
     * Gets the byte value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a byte, the default
     * value will be returned.
     * 
     * @param index The index of the byte to retrieve.
     * @param def The default value.
     * @return The byte value at the index.
     */
    byte getByte(final int index, final byte def);
    
    /**
     * Checks if there is a short value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a short.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a short at the given index,
     *         {@code false} otherwise.
     */
    boolean isShort(final int index);
    
    /**
     * Gets the short value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a short,
     * {@code (short) 0} will be returned.
     * 
     * @param index The index of the short to retrieve.
     * @return The short value at the index.
     * @see JSONArray#getShort(int, short)
     */
    short getShort(final int index);
    
    /**
     * Gets the short value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a short, the default
     * value will be returned.
     * 
     * @param index The index of the short to retrieve.
     * @param def The default value.
     * @return The short value at the index.
     */
    short getShort(final int index, final short def);
    
    /**
     * Checks if there is an integer value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not an integer.
     * 
     * @param index The index to check.
     * @return {@code true} if there is an integer at the given index,
     *         {@code false} otherwise.
     */
    boolean isInteger(final int index);
    
    /**
     * Gets the integer value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not an integer,
     * {@code 0} will be returned.
     * 
     * @param index The index of the integer to retrieve.
     * @return The integer value at the index.
     * @see JSONArray#getInteger(int, int)
     */
    int getInteger(final int index);
    
    /**
     * Gets the integer value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not an integer, the default
     * value will be returned.
     * 
     * @param index The index of the integer to retrieve.
     * @param def The default value.
     * @return The integer value at the index.
     */
    int getInteger(final int index, final int def);
    
    /**
     * Checks if there is a long value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a long.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a long at the given index,
     *         {@code false} otherwise.
     */
    boolean isLong(final int index);
    
    /**
     * Gets the long value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a long, {@code 0L}
     * will be returned.
     * 
     * @param index The index of the long to retrieve.
     * @return The long value at the index.
     * @see JSONArray#getLong(int, long)
     */
    long getLong(final int index);
    
    /**
     * Gets the long value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a long, the default
     * value will be returned.
     * 
     * @param index The index of the long to retrieve.
     * @param def The default value.
     * @return The long value at the index.
     */
    long getLong(final int index, final long def);
    
    /**
     * Checks if there is a float value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a float.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a float at the given index,
     *         {@code false} otherwise.
     */
    boolean isFloat(final int index);
    
    /**
     * Gets the float value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a float, {@code 0.0F}
     * will be returned.
     * 
     * @param index The index of the float to retrieve.
     * @return The float value at the index.
     * @see JSONArray#getFloat(int, float)
     */
    float getFloat(final int index);
    
    /**
     * Gets the float value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a float, the default
     * value will be returned.
     * 
     * @param index The index of the float to retrieve.
     * @param def The default value.
     * @return The float value at the index.
     */
    float getFloat(final int index, final float def);
    
    /**
     * Checks if there is a double value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a double.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a double at the given index,
     *         {@code false} otherwise.
     */
    boolean isDouble(final int index);
    
    /**
     * Gets the double value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a double, {@code 0.0D}
     * will be returned.
     * 
     * @param index The index of the double to retrieve.
     * @return The double value at the index.
     * @see JSONArray#getDouble(int, double)
     */
    double getDouble(final int index);
    
    /**
     * Gets the double value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a double, the default
     * value will be returned.
     * 
     * @param index The index of the double to retrieve.
     * @param def The default value.
     * @return The double value at the index.
     */
    double getDouble(final int index, final double def);
    
    /**
     * Checks if there is a {@link String} value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a {@link String}.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a {@link String} at the given index,
     *         {@code false} otherwise.
     */
    boolean isString(final int index);
    
    /**
     * Gets the {@link String} value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a {@link String},
     * {@code null} will be returned.
     * 
     * @param index The index of the {@link String} to retrieve.
     * @return The {@link String} value at the index.
     * @see JSONArray#getString(int, String)
     */
    @Nullable
    String getString(final int index);
    
    /**
     * Gets the {@link String} value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a {@link String},
     * the default value will be returned.
     * 
     * @param index The index of the {@link String} to retrieve.
     * @param def The default value.
     * @return The {@link String} value at the index.
     */
    @Contract("_, !null -> !null")
    @Nullable
    String getString(final int index, @Nullable final String def);
    
    /**
     * Checks if there is a {@link JSONArray} value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONArray}'s bounds, or if there is no entry ({@code null}), or if
     * the entry is not a {@link JSONArray}.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a {@link JSONArray} at the given index,
     *         {@code false} otherwise.
     */
    boolean isArray(final int index);
    
    /**
     * Gets the {@link JSONArray} value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a {@link JSONArray},
     * {@code null} will be returned.
     * 
     * @param index The index of the {@link JSONArray} to retrieve.
     * @return The {@link JSONArray} value at the index.
     * @see JSONArray#getArray(int, JSONArray)
     */
    @Nullable
    JSONArray getArray(final int index);
    
    /**
     * Gets the {@link JSONArray} value at the given index.
     * <p>
     * If the index is out of the {@link JSONArray}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a {@link JSONArray},
     * the default value will be returned.
     * 
     * @param index The index of the {@link JSONArray} to retrieve.
     * @param def The default value.
     * @return The {@link JSONArray} value at the index.
     */
    @Contract("_, !null -> !null")
    @Nullable
    JSONArray getArray(final int index, @Nullable final JSONArray def);
    
    /**
     * Checks if there is a {@link JSONObject} value at the given index.
     * <p>
     * This will return {@code false} if the index is out of this
     * {@link JSONObject}'s bounds, or if there is no entry ({@code null}), or
     * if the entry is not a {@link JSONObject}.
     * 
     * @param index The index to check.
     * @return {@code true} if there is a {@link JSONObject} at the given index,
     *         {@code false} otherwise.
     */
    boolean isObject(final int index);
    
    /**
     * Gets the {@link JSONObject} value at the given index.
     * <p>
     * If the index is out of the {@link JSONObject}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a {@link JSONObject},
     * {@code null}  will be returned.
     * 
     * @param index The index of the {@link JSONObject} to retrieve.
     * @return The {@link JSONObject} value at the index.
     * @see JSONArray#getObject(int, JSONObject)
     */
    @Nullable
    JSONObject getObject(final int index);
    
    /**
     * Gets the {@link JSONObject} value at the given index.
     * <p>
     * If the index is out of the {@link JSONObject}'s bounds, no entry set
     * ({@code null}), or the value at the index is not a {@link JSONObject},
     * the default value will be returned.
     * 
     * @param index The index of the {@link JSONObject} to retrieve.
     * @param def The default value.
     * @return The {@link JSONObject} value at the index.
     */
    @Contract("_, !null -> !null")
    @Nullable
    JSONObject getObject(final int index, @Nullable final JSONObject def);
}
