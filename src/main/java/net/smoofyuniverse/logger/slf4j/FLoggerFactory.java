/*
 * Copyright (c) 2017-2021 Hugo Dupanloup (Yeregorix)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.smoofyuniverse.logger.slf4j;

import net.smoofyuniverse.logger.core.DefaultImpl;
import net.smoofyuniverse.logger.core.LoggerFactory;
import org.slf4j.ILoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FLoggerFactory implements ILoggerFactory {
	public static FLoggerFactory DEFAULT_IMPL = new FLoggerFactory(DefaultImpl.FACTORY);

	private final Map<String, FLogger> loggers = new ConcurrentHashMap<>();
	private final LoggerFactory delegate;

	public FLoggerFactory(LoggerFactory delegate) {
		if (delegate == null)
			throw new IllegalArgumentException("delegate");
		this.delegate = delegate;
	}

	public LoggerFactory getDelegate() {
		return this.delegate;
	}

	@Override
	public FLogger getLogger(String name) {
		return this.loggers.computeIfAbsent(name, this::createLogger);
	}

	protected FLogger createLogger(String name) {
		return new FLogger(this.delegate.provideLogger(name));
	}
}
