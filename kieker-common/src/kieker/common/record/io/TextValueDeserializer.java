/***************************************************************************
 * Copyright 2017 Kieker Project (http://kieker-monitoring.net)
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
 ***************************************************************************/
package kieker.common.record.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Text value deserializer implementation.
 *
 * @author Reiner Jung
 * @since 1.13
 *
 */
public class TextValueDeserializer implements IValueDeserializer {

	private final CharBuffer buffer;

	protected TextValueDeserializer(final CharBuffer buffer) {
		this.buffer = buffer;
	}

	public static TextValueDeserializer create(final CharBuffer buffer) {
		return new TextValueDeserializer(buffer);
	}

	@Override
	public boolean getBoolean() { // NOPMD
		return Boolean.parseBoolean(this.readValue());
	}

	@Override
	public byte getByte() {
		return Byte.parseByte(this.readValue());
	}

	@Override
	public char getChar() {
		final char ch = this.buffer.get();
		this.buffer.get(); /** reading semicolon. */
		return ch;
	}

	@Override
	public short getShort() { // NOPMD
		return Short.parseShort(this.readValue());
	}

	@Override
	public int getInt() {
		return Integer.parseInt(this.readValue());
	}

	@Override
	public long getLong() {
		return Long.parseLong(this.readValue());
	}

	@Override
	public float getFloat() {
		return Float.parseFloat(this.readValue());
	}

	@Override
	public double getDouble() {
		return Double.parseDouble(this.readValue());
	}

	@Override
	public String getString() {
		return this.readValue();
	}

	@Override
	public byte[] getBytes(final byte[] target) {
		final char[] charTarget = new char[target.length];
		this.buffer.get(charTarget);
		ByteBuffer.wrap(target).asCharBuffer().put(charTarget);
		return target;
	}

	private String readValue() {
		final char[] charArray = new char[this.buffer.capacity()];
		char ch;
		boolean escape = false;
		int i = 0;
		do {
			escape = false;
			ch = this.buffer.get();
			if ((ch == '\\') && !escape) {
				escape = true;
			}
			if (escape || (ch != ';')) {
				charArray[i++] = ch;
			}
		} while (escape || (ch != ';'));
		return new String(charArray);
	}

}
