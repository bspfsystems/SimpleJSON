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
import java.util.LinkedHashMap;
import java.util.Map;
import org.bspfsystems.simplejson.parser.JSONException;
import org.bspfsystems.simplejson.parser.JSONParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a simple implementation of a {@link JSONObject}, which is a
 * common, non-thread-safe data format for {@link String}-to-{@link Object}
 * data mappings.
 * <p>
 * The contents of the {@link JSONObject} are only validated as JSON values upon
 * serialization (see {@link JSONSerializable#serialize()}). This means that all
 * values added to a {@link JSONObject} must be recognized by the
 * {@link JSONParser} for it to be considered a true {@link JSONObject}. Until
 * such a recognition has been reached, the item should be considered a
 * {@link JSONSerializable} {@link LinkedHashMap}.
 */
public final class SimpleJSONObject extends LinkedHashMap<String, Object> implements JSONObject {
    
    /**
     * Constructs a new {@link JSONObject}.
     * 
     * @see LinkedHashMap#LinkedHashMap()
     */
    public SimpleJSONObject() {
        super();
    }
    
    /**
     * Constructs a new {@link JSONObject} with the specified initial capacity.
     * 
     * @param initialCapacity The initial capacity the underlying
     *                        {@link LinkedHashMap} should allocate for.
     * @see LinkedHashMap#LinkedHashMap(int)
     */
    public SimpleJSONObject(final int initialCapacity) {
        super(initialCapacity);
    }
    
    /**
     * Constructs a new {@link JSONObject} with the specified initial capacity
     * and load factor.
     * 
     * @param initialCapacity The initial capacity the underlying
     *                        {@link LinkedHashMap} should allocate for.
     * @param loadFactor The ratio from 0 to 1 of how full the underlying
     *                   {@link LinkedHashMap} should be until it adds capacity.
     * @see LinkedHashMap#LinkedHashMap(int, float)
     */
    public SimpleJSONObject(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * Constructs a new {@link JSONObject} with the specified initial capacity,
     * load factor, and ordering mode.
     *
     * @param initialCapacity The initial capacity the underlying
     *                        {@link LinkedHashMap} should allocate for.
     * @param loadFactor The ratio from 0 to 1 of how full the underlying
     *                   {@link LinkedHashMap} should be until it adds capacity.
     * @param accessOrder {@code true} if the ordering mode should be for
     *                    access-order, {@code false} if it should be for
     *                    insertion-order.
     * @see LinkedHashMap#LinkedHashMap(int, float, boolean)
     */
    public SimpleJSONObject(final int initialCapacity, final float loadFactor, final boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }
    
    /**
     * Constructs a new {@link SimpleJSONObject} with the specified {@link Map} of
     * data loaded into the underlying {@link LinkedHashMap} to start.
     * 
     * @param data The {@link Map} whose data will be placed into the
     *             {@link SimpleJSONObject} to start.
     * @see LinkedHashMap#LinkedHashMap(Map)
     */
    public SimpleJSONObject(@NotNull final Map<? extends String, ?> data) {
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
    public Iterator<Map.Entry<String, Object>> iterator() {
        return super.entrySet().iterator();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void set(@NotNull final String key, @Nullable final Object value) {
        this.put(key, value);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void unset(@NotNull final String key) {
        this.remove(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNull(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) == null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBoolean(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Boolean;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBoolean(@NotNull final String key) {
        return this.getBoolean(key, false);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getBoolean(@NotNull final String key, final boolean def) {
        return this.isBoolean(key) ? (Boolean) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isByte(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Byte;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public byte getByte(@NotNull final String key) {
        return this.getByte(key, (byte) 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public byte getByte(@NotNull final String key, final byte def) {
        return this.isByte(key) ? (Byte) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShort(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Short;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public short getShort(@NotNull final String key) {
        return this.getShort(key, (short) 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public short getShort(@NotNull final String key, final short def) {
        return this.isShort(key) ? (Short) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInteger(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Integer;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getInteger(@NotNull final String key) {
        return this.getInteger(key, 0);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getInteger(@NotNull final String key, final int def) {
        return this.isInteger(key) ? (Integer) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLong(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Long;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(@NotNull final String key) {
        return this.getLong(key, 0L);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(@NotNull final String key, final long def) {
        return this.isLong(key) ? (Long) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFloat(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Float;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getFloat(@NotNull final String key) {
        return this.getFloat(key, 0.0F);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public float getFloat(@NotNull final String key, final float def) {
        return this.isFloat(key) ? (Float) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDouble(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof Double;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getDouble(@NotNull final String key) {
        return this.getDouble(key, 0.0D);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getDouble(@NotNull final String key, final double def) {
        return this.isDouble(key) ? (Double) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isString(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof String;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public String getString(@NotNull final String key) {
        return this.getString(key, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_, !null -> !null")
    @Nullable
    public String getString(@NotNull final String key, @Nullable final String def) {
        return this.isString(key) ? (String) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArray(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof JSONArray;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public JSONArray getArray(@NotNull final String key) {
        return this.getArray(key, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_, !null -> !null")
    @Nullable
    public JSONArray getArray(@NotNull final String key, @Nullable final JSONArray def) {
        return this.isArray(key) ? (JSONArray) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObject(@NotNull final String key) {
        return this.containsKey(key) && this.get(key) instanceof JSONObject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public JSONObject getObject(@NotNull final String key) {
        return this.getObject(key, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Contract("_, !null -> !null")
    @Nullable
    public JSONObject getObject(@NotNull final String key, @Nullable final JSONObject def) {
        return this.isObject(key) ? (JSONObject) this.get(key) : def;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String serialize() throws JSONException {
        
        final StringBuilder builder = new StringBuilder();
        final Iterator<Map.Entry<String, Object>> iterator = this.entrySet().iterator();
        int index = 1;
        
        builder.append("{");
        while (iterator.hasNext()) {
            if (index > 1) {
                builder.append(",");
            }
            final Map.Entry<String, Object> entry = iterator.next();
            builder.append(JSONParser.serialize(entry.getKey()));
            builder.append(":");
            builder.append(JSONParser.serialize(entry.getValue()));
            index++;
        }
        
        builder.append("}");
        
        return builder.toString();
    }
}
