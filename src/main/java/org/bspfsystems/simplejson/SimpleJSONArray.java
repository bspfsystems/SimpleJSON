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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.bspfsystems.simplejson.parser.JSONException;
import org.bspfsystems.simplejson.parser.JSONParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a simple implementation of a {@link JSONArray}, which is a
 * common, non-thread-safe data format for a collection of {@link Object}s.
 * <p>
 * The contents of the {@link JSONArray} are only validated as JSON values upon
 * serialization (see {@link JSONSerializable#serialize()}). This means that all
 * values added to a {@link JSONArray} must be recognized by the
 * {@link JSONParser} for it to be considered a true {@link JSONArray}. Until
 * such a recognition has been reached, the item should be considered a
 * {@link JSONSerializable} {@link ArrayList}.
 */
public final class SimpleJSONArray extends ArrayList<Object> implements JSONArray {
    
    /**
     * Constructs a new {@link JSONArray}.
     * 
     * @see ArrayList#ArrayList()
     */
    public SimpleJSONArray() {
        super();
    }
    
    /**
     * Constructs a new {@link JSONArray} with the specified initial capacity.
     * 
     * @param initialCapacity The initial capacity that the underlying
     *                        {@link ArrayList} should allocate for.
     * @see ArrayList#ArrayList(int)
     */
    public SimpleJSONArray(final int initialCapacity) {
        super(initialCapacity);
    }
    
    /**
     * Constructs a new {@link JSONArray} with the specified {@link Collection}
     * of data loaded into the underlying {@link ArrayList} to start.
     * 
     * @param data The {@link Collection} whose data will be placed into the
     *             {@link JSONArray} to start.
     * @see ArrayList#ArrayList(Collection)
     */
    public SimpleJSONArray(@NotNull final Collection<?> data) {
        super(data);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return super.size();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public Iterator<Object> iterator() {
        return super.iterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntry(@Nullable final Object value) {
        super.add(value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEntry(final int index, @Nullable final Object value) throws IndexOutOfBoundsException {
        super.add(index, value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setEntry(final int index, @Nullable final Object value) throws IndexOutOfBoundsException {
        super.set(index, value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unsetEntry(final int index) throws IndexOutOfBoundsException {
        super.remove(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNull(final int index) {
        return index >= 0 && index < this.size() && this.get(index) == null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBoolean(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Boolean;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBoolean(final int index) {
        return this.getBoolean(index, false);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBoolean(final int index, final boolean def) {
        return this.isBoolean(index) ? (Boolean) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isByte(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Byte;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public byte getByte(final int index) {
        return this.getByte(index, (byte) 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public byte getByte(final int index, final byte def) {
        return this.isByte(index) ? (Byte) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShort(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Short;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public short getShort(final int index) {
        return this.getShort(index, (short) 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public short getShort(final int index, final short def) {
        return this.isShort(index) ? (Short) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInteger(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Integer;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getInteger(final int index) {
        return this.getInteger(index, 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getInteger(final int index, final int def) {
        return this.isInteger(index) ? (Integer) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLong(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Long;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(final int index) {
        return this.getLong(index, 0L);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(final int index, final long def) {
        return this.isLong(index) ? (Long) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFloat(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Float;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getFloat(final int index) {
        return this.getFloat(index, 0.0F);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getFloat(final int index, final float def) {
        return this.isFloat(index) ? (Float) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDouble(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof Double;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getDouble(final int index) {
        return this.getDouble(index, 0.0D);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getDouble(final int index, final double def) {
        return this.isDouble(index) ? (Double) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isString(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof String;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public String getString(final int index) {
        return this.getString(index, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_, !null -> !null")
    @Nullable
    public String getString(final int index, @Nullable final String def) {
        return this.isString(index) ? (String) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArray(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof JSONArray;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public JSONArray getArray(final int index) {
        return this.getArray(index, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_, !null -> !null")
    @Nullable
    public JSONArray getArray(final int index, @Nullable final JSONArray def) {
        return this.isArray(index) ? (JSONArray) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObject(final int index) {
        return index >= 0 && index < this.size() && this.get(index) instanceof JSONObject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public JSONObject getObject(final int index) {
        return this.getObject(index, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_, !null -> !null")
    @Nullable
    public JSONObject getObject(final int index, @Nullable final JSONObject def) {
        return this.isObject(index) ? (JSONObject) this.get(index) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String serialize() throws JSONException {
    
        final StringBuilder builder = new StringBuilder();
        final Iterator<Object> iterator = this.iterator();
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
    
        return builder.toString();
    }
}
