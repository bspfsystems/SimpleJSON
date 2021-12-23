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
import java.math.BigDecimal;

final class JSONReader {
    
    /**
     * Represents the end of file.
     */
    static final int END_OF_FILE = -1;
    
    /**
     * Initial size of the look-ahead buffer.
     */
    private static final int BUFFER_SIZE = 16384;
    
    // Lexical states.
    static final int INITIAL = 0;
    static final int STRING_BEGIN = 2;
    
    /**
     * {@code LEXSTATE[1]} is the state in the DFA for the lexical state 1.
     * {@code LEXSTATE[l+1]} is the state in the DFA for the lexical state 1 at
     * the beginning of a line. {@code l} is of the form {@code l = 2*k}, where
     * {@code k} is a non-negative integer.
     */
    private static final int[] LEXSTATE = { 0 , 0 , 1 , 1 };
    
    /**
     * Top-level table for translating characters to character classes.
     */
    private static final int[] CHARACTER_MAP_TOP = JSONReader.unpackCharacterMapTop();
    
    private static int[] unpackCharacterMapTop() {
        final int[] result = new int[4352];
        final String characterMapTopPacked = "\1\0\327\u0100\10\u0200\u1020\u0100";
        int packedIndex = 0;
        int unpackedIndex = 0;
        while (packedIndex < characterMapTopPacked.length()) {
            int count = characterMapTopPacked.charAt(packedIndex++);
            final int value = characterMapTopPacked.charAt(packedIndex++);
            do {
                result[unpackedIndex++] = value;
            } while (--count > 0);
        }
        return result;
    }
    
    /**
     * Second-level tables for translating characters into character classes.
     */
    private static final int[] CHARACTER_MAP_BLOCKS = JSONReader.unpackCharacterMapBlocks();
    
    private static int[] unpackCharacterMapBlocks() {
        final int[] result = new int[768];
        int packedIndex = 0;
        int unpackedIndex = 0;
        final String characterMapBlocksPacked = "\11\0\2\1\2\0\1\1\22\0\1\1\1\0\1\2\10\0\1\3\1\4\1\5\1\6\1\7\12\10\1\11\6\0\4\12\1\13\1\12\24\0\1\14\1\15\1\16\3\0\1\17\1\20\2\12\1\21\1\22\5\0\1\23\1\0\1\24\3\0\1\25\1\26\1\27\1\30\5\0\1\31\1\0\1\32\u0182\0\u0100\33";
        while (packedIndex < characterMapBlocksPacked.length()) {
            int count = characterMapBlocksPacked.charAt(packedIndex++);
            final int value = characterMapBlocksPacked.charAt(packedIndex++);
            do {
                result[unpackedIndex++] = value;
            } while (--count > 0);
        }
        return result;
    }
    
    /**
     * Translates DFA states to action switch labels.
     */
    private static final int[] ACTION = JSONReader.unpackAction();
    
    private static int[] unpackAction() {
        final int[] result = new int[45];
        int packedIndex = 0;
        int unpackedIndex = 0;
        final String packedAction = "\2\0\1\1\1\2\1\3\1\4\1\1\1\5\1\6\1\7\1\10\3\1\1\11\1\12\1\13\1\14\1\15\5\0\1\16\1\17\1\15\1\20\1\21\1\22\1\23\1\24\1\0\1\5\1\0\1\5\4\0\1\25\1\26\2\0\1\27";
        while (packedIndex < packedAction.length()) {
            int count = packedAction.charAt(packedIndex++);
            final int value = packedAction.charAt(packedIndex++);
            do {
                result[unpackedIndex++] = value;
            } while (--count > 0);
        }
        return result;
    }
    
    /**
     * Translates a state to a row index in the transition table.
     */
    private static final int[] ROW_MAP = JSONReader.unpackRowMap();
    
    private static int[] unpackRowMap() {
        final int[] result = new int[45];
        int packedIndex = 0;
        int unpackedIndex = 0;
        final String packedRowMap = "\0\0\0\34\0\70\0\124\0\70\0\70\0\160\0\214\0\70\0\70\0\70\0\250\0\304\0\340\0\70\0\70\0\374\0\70\0\u0118\0\u0134\0\u0150\0\u016c\0\u0188\0\u01a4\0\70\0\70\0\70\0\70\0\70\0\70\0\70\0\70\0\u01c0\0\u01dc\0\u01f8\0\u01f8\0\u0214\0\u0230\0\u024c\0\u0268\0\70\0\70\0\u0284\0\u02a0\0\70";
        while (packedIndex < packedRowMap.length()) {
            final int high = packedRowMap.charAt(packedIndex++) << 16;
            result[unpackedIndex++] = high | packedRowMap.charAt(packedIndex++);
        }
        return result;
    }
    
    /**
     * The transition table of the DFA.
     */
    private static final int[] TRANS = unpackTrans();
    
    private static int[] unpackTrans() {
        final int[] result = new int[700];
        int packedIndex = 0;
        int unpackedIndex = 0;
        final String transPacked = "\1\3\1\4\1\5\1\3\1\6\1\7\2\3\1\10\1\11\2\3\1\12\1\3\1\13\3\3\1\14\1\3\1\15\2\3\1\16\1\3\1\17\1\20\1\0\2\21\1\22\12\21\1\23\16\21\35\0\1\4\42\0\1\10\31\0\1\24\1\0\1\10\2\0\1\25\5\0\1\25\31\0\1\26\44\0\1\27\30\0\1\30\6\0\2\21\1\0\12\21\1\0\16\21\2\0\1\31\4\0\1\32\5\0\1\33\2\0\1\34\1\0\1\35\1\0\1\36\1\37\1\0\1\40\1\41\13\0\1\42\26\0\1\43\1\0\1\43\2\0\1\44\46\0\1\45\33\0\1\46\40\0\1\47\13\0\1\50\1\0\2\50\3\0\4\50\21\0\1\42\2\0\1\25\5\0\1\25\22\0\1\44\51\0\1\47\30\0\1\51\31\0\1\52\22\0\1\53\1\0\2\53\3\0\4\53\21\0\1\54\1\0\2\54\3\0\4\54\21\0\1\55\1\0\2\55\3\0\4\55\11\0";
        while (packedIndex < transPacked.length()) {
            int count = transPacked.charAt(packedIndex++);
            int value = transPacked.charAt(packedIndex++);
            value--;
            do {
                result[unpackedIndex++] = value;
            } while (--count > 0);
        }
        return result;
    }
    
    /**
     * {@code Lex.ATTRIBUTE[aState]} contains the attributes of {@code aState}.
     */
    private static final int[] ATTRIBUTE = unpackAttribute();
    
    private static int[] unpackAttribute() {
        final int[] result = new int[45];
        int packedIndex = 0;
        int unpackedIndex = 0;
        final String attributePacked = "\2\0\1\11\1\1\2\11\2\1\3\11\3\1\2\11\1\1\1\11\1\1\5\0\10\11\1\0\1\1\1\0\1\1\4\0\2\11\2\0\1\11";
        while (packedIndex < attributePacked.length()) {
            int count = attributePacked.charAt(packedIndex++);
            int value = attributePacked.charAt(packedIndex++);
            do {
                result[unpackedIndex++] = value;
            } while (--count > 0);
        }
        return result;
    }
    
    private final Reader reader;
    private int lexicalState;
    private char[] buffer;
    private int markedPosition;
    private int currentPosition;
    private int startRead;
    private int endRead;
    private boolean atEndOfFile;
    private int finalHighSurrogate;
    private long position;
    
    private StringBuilder builder;
    
    /**
     * Constructs a new {@link JSONReader}.
     * 
     * @param reader The {@link Reader} to read input from.
     */
    JSONReader(Reader reader) {
        this.reader = reader;
        this.lexicalState = JSONReader.INITIAL;
        this.buffer = new char[JSONReader.BUFFER_SIZE];
        this.finalHighSurrogate = 0;
        this.builder = new StringBuilder();
    }
    
    /**
     * Gets the current position.
     *
     * @return The current position.
     */
    long getPosition() {
        return this.position;
    }
    
    /**
     * Translates raw input code points to DFA table row.
     */
    private int translateCharacterMap(int input) {
        int offset = input & 255;
        return offset == input ? JSONReader.CHARACTER_MAP_BLOCKS[offset] : JSONReader.CHARACTER_MAP_BLOCKS[JSONReader.CHARACTER_MAP_TOP[input >> 8] | offset];
    }
    
    /**
     * Refills the input buffer.
     * 
     * @return {@code false} if there was new input.
     * @throws IOException If any I/O error occurs.
     */
    private boolean refill() throws IOException {
        
        if (this.startRead > 0) {
            this.endRead += this.finalHighSurrogate;
            this.finalHighSurrogate = 0;
            System.arraycopy(this.buffer, this.startRead, this.buffer, 0, this.endRead - this.startRead);
            
            this.endRead -= this.startRead;
            this.currentPosition -= this.startRead;
            this.markedPosition -= this.startRead;
            this.startRead = 0;
        }
        
        if (this.currentPosition >= this.buffer.length - this.finalHighSurrogate) {
            char[] newBuffer = new char[this.buffer.length * 2];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.buffer.length);
            this.buffer = newBuffer;
            this.endRead += this.finalHighSurrogate;
            this.finalHighSurrogate = 0;
        }
        
        int requested = this.buffer.length - this.endRead;
        int numberRead = this.reader.read(this.buffer, this.endRead, requested);
        
        if (numberRead == 0) {
            throw new IOException("Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
        }
        
        if (numberRead > 0) {
            this.endRead += numberRead;
            if (Character.isHighSurrogate(this.buffer[this.endRead - 1])) {
                if (numberRead == requested) {
                    this.endRead--;
                    this.finalHighSurrogate = 1;
                } else {
                    int c = this.reader.read();
                    if (c == -1) {
                        return true;
                    } else {
                        this.buffer[this.endRead++] = (char) c;
                    }
                }
            }
            
            return false;
        }
        
        return true;
    }
    
    /**
     * Enters a new lexical state.
     * 
     * @param newState the new lexical state.
     */
    private void begin(int newState) {
        this.lexicalState = newState;
    }
    
    /**
     * Gets the text matched by the current regular expression.
     * 
     * @return The matched text.
     */
    private String text() {
        return new String(this.buffer, this.startRead, this.markedPosition - this.startRead);
    }
    
    /**
     * Resumes scanning until the next regular expression is matched, the end of
     * the input is encountered, or an I/O error occurs.
     * 
     * @return The next token.
     * @throws IOException If an I/O error occurs.
     * @throws JSONException If a JSON error occurs.
     */
    JSONToken read() throws IOException, JSONException {
        int input;
        int action;
        
        int currentPosition;
        int markedPosition;
        int endRead = this.endRead;
        char[] buffer = this.buffer;
    
        int[] attribute = JSONReader.ATTRIBUTE;
        
        while (true) {
            markedPosition = this.markedPosition;
            this.position += markedPosition - this.startRead;
            action = -1;
            currentPosition = this.currentPosition = this.startRead = markedPosition;
            int state = JSONReader.LEXSTATE[this.lexicalState];
            int attributes = attribute[state];
            if ((attributes & 1) == 1) {
                action = state;
            }
            
            forAction: {
                while (true) {
                    
                    if (currentPosition < endRead) {
                        input = Character.codePointAt(buffer, currentPosition, endRead);
                        currentPosition += Character.charCount(input);
                    } else if (this.atEndOfFile) {
                        input = JSONReader.END_OF_FILE;
                        break forAction;
                    } else {
                        this.currentPosition = currentPosition;
                        this.markedPosition = markedPosition;
                        boolean endOfFile = this.refill();
                        currentPosition = this.currentPosition;
                        markedPosition = this.markedPosition;
                        buffer = this.buffer;
                        endRead = this.endRead;
                        
                        if (endOfFile) {
                            input = JSONReader.END_OF_FILE;
                            break forAction;
                        } else {
                            input = Character.codePointAt(buffer, currentPosition, endRead);
                            currentPosition = Character.charCount(input);
                        }
                    }
                    
                    int next = JSONReader.TRANS[JSONReader.ROW_MAP[state] + this.translateCharacterMap(input)];
                    if (next == -1) {
                        break forAction;
                    }
                    state = next;
                    
                    attributes = attribute[state];
                    if ((attributes & 1) == 1) {
                        action = state;
                        markedPosition = currentPosition;
                        if ((attributes & 8) == 8) {
                            break forAction;
                        }
                    }
                }
            }
            
            this.markedPosition = markedPosition;
            if (input == JSONReader.END_OF_FILE && this.startRead == this.currentPosition) {
                this.atEndOfFile = true;
                return null;
            } else {
                switch(action < 0 ? action : JSONReader.ACTION[action]) {
                    case 1:
                        throw new JSONException("Unexpected character \"" + this.buffer[this.startRead] + "\" at position " + this.position + ". Please fix the String and try to parse again.");
                    case 2:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                        break;
                    case 3:
                        this.builder = new StringBuilder();
                        this.begin(JSONReader.STRING_BEGIN);
                        break;
                    case 4:
                        return new JSONToken(JSONToken.Type.COMMA, null);
                    case 5:
                        BigDecimal bigDecimalValue = new BigDecimal(this.text());
                        return new JSONToken(JSONToken.Type.DATUM, bigDecimalValue);
                    case 6:
                        return new JSONToken(JSONToken.Type.COLON, null);
                    case 7:
                        return new JSONToken(JSONToken.Type.LEFT_SQUARE, null);
                    case 8:
                        return new JSONToken(JSONToken.Type.RIGHT_SQUARE, null);
                    case 9:
                        return new JSONToken(JSONToken.Type.LEFT_BRACE, null);
                    case 10:
                        return new JSONToken(JSONToken.Type.RIGHT_BRACE, null);
                    case 11:
                        this.builder.append(this.text());
                        break;
                    case 12:
                        this.begin(JSONReader.INITIAL);
                        return new JSONToken(JSONToken.Type.DATUM, this.builder.toString());
                    case 13:
                        this.builder.append('\\');
                        break;
                    case 14:
                        this.builder.append('"');
                        break;
                    case 15:
                        this.builder.append('/');
                        break;
                    case 16:
                        this.builder.append('\b');
                        break;
                    case 17:
                        this.builder.append('\f');
                        break;
                    case 18:
                        this.builder.append('\n');
                        break;
                    case 19:
                        this.builder.append('\r');
                        break;
                    case 20:
                        this.builder.append('\t');
                        break;
                    case 21:
                        return new JSONToken(JSONToken.Type.DATUM, null);
                    case 22:
                        Boolean booleanValue = Boolean.valueOf(this.text());
                        return new JSONToken(JSONToken.Type.DATUM, booleanValue);
                    case 23:
                        try {
                            int character = Integer.parseInt(this.text().substring(2), 16);
                            this.builder.append((char) character);
                            break;
                        } catch (Exception e) {
                            throw new JSONException("An unexpected exception was caught at position " + this.position + ". Please report this to the library's maintainer, as it will need to be addressed before trying to parse again.", e);
                        }
                    default:
                        throw new Error("Error: could not match input");
                }
            }
        }
    }
}
