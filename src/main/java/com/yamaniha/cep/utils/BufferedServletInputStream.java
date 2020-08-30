package com.yamaniha.cep.utils;

import java.io.ByteArrayInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class BufferedServletInputStream extends ServletInputStream {

	private final ByteArrayInputStream bais;

	public BufferedServletInputStream(final ByteArrayInputStream bais) {
		this.bais = bais;
	}

	@Override
	public int available() {
		return this.bais.available();
	}

	@Override
	public int read() {
		return this.bais.read();
	}

	@Override
	public int read(final byte[] buf, final int off, final int len) {
		return this.bais.read(buf, off, len);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(final ReadListener arg0) {
		/* */
	}

}