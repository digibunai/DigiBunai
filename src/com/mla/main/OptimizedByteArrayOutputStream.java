/*
 * Copyright (C) 2017 HP
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mla.main;

/**
 *
 * @author Amit Kumar Singh
 */
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * ByteArrayOutputStream implementation that doesn't synchronize methods
 * and doesn't copy the data on toByteArray().
 */
public class OptimizedByteArrayOutputStream extends OutputStream {
    /**
     * Buffer and size
     */
    protected byte[] buffer = null;
    protected int size = 0;

    /**
     * Constructs a stream with buffer capacity size 5K 
     */
    public OptimizedByteArrayOutputStream() {
        this(5 * 1024);
    }

    /**
     * Constructs a stream with the given initial size
     */
    public OptimizedByteArrayOutputStream(int initSize) {
        this.size = 0;
        this.buffer = new byte[initSize];
    }

    /**
     * Ensures that we have a large enough buffer for the given size.
     */
    private void verifyBufferSize(int sz) {
        if (sz > buffer.length) {
            byte[] old = buffer;
            buffer = new byte[Math.max(sz, 2 * buffer.length )];
            System.arraycopy(old, 0, buffer, 0, old.length);
            old = null;
        }
    }

    public int getSize() {
        return size;
    }

    /**
     * Returns the byte array containing the written data. Note that this
     * array will almost always be larger than the amount of data actually
     * written.
     */
    public byte[] getByteArray() {
        return buffer;
    }

    public final void write(byte b[]) {
        verifyBufferSize(size + b.length);
        System.arraycopy(b, 0, buffer, size, b.length);
        size += b.length;
    }

    public final void write(byte b[], int off, int len) {
        verifyBufferSize(size + len);
        System.arraycopy(b, off, buffer, size, len);
        size += len;
    }

    public final void write(int b) {
        verifyBufferSize(size + 1);
        buffer[size++] = (byte) b;
    }

    public void reset() {
        size = 0;
    }

    /**
     * Returns a ByteArrayInputStream for reading back the written data
     */
    public InputStream getInputStream() {
        return new OptimizedByteArrayInputStream(buffer, size);
    }

}