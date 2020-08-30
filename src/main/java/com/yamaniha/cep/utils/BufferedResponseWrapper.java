package com.yamaniha.cep.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BufferedResponseWrapper implements HttpServletResponse {

	HttpServletResponse original;
	TeeServletOutputStream tee;
	ByteArrayOutputStream bos;

	public BufferedResponseWrapper(final HttpServletResponse response) {
		original = response;
	}

	public String getContent() {
		if (bos == null) {
			return "";
		} else {
			return bos.toString();
		}
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return original.getWriter();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (tee == null) {
			bos = new ByteArrayOutputStream();
			tee = new TeeServletOutputStream(original.getOutputStream(), bos);
		}
		return tee;

	}

	@Override
	public String getCharacterEncoding() {
		return original.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return original.getContentType();
	}

	@Override
	public void setCharacterEncoding(final String charset) {
		original.setCharacterEncoding(charset);
	}

	@Override
	public void setContentLength(final int len) {
		original.setContentLength(len);
	}

	@Override
	public void setContentType(final String type) {
		original.setContentType(type);
	}

	@Override
	public void setBufferSize(final int size) {
		original.setBufferSize(size);
	}

	@Override
	public int getBufferSize() {
		return original.getBufferSize();
	}

	@Override
	public void flushBuffer() throws IOException {
		tee.flush();
	}

	@Override
	public void resetBuffer() {
		original.resetBuffer();
	}

	@Override
	public boolean isCommitted() {
		return original.isCommitted();
	}

	@Override
	public void reset() {
		original.reset();
	}

	@Override
	public void setLocale(final Locale loc) {
		original.setLocale(loc);
	}

	@Override
	public Locale getLocale() {
		return original.getLocale();
	}

	@Override
	public void addCookie(final Cookie cookie) {
		original.addCookie(cookie);
	}

	@Override
	public boolean containsHeader(final String name) {
		return original.containsHeader(name);
	}

	@Override
	public String encodeURL(final String url) {
		return original.encodeURL(url);
	}

	@Override
	public String encodeRedirectURL(final String url) {
		return original.encodeRedirectURL(url);
	}

	@Override
	public String encodeUrl(final String url) {
		return original.encodeURL(url);		
	}

	@Override
	public String encodeRedirectUrl(final String url) {
		return original.encodeRedirectURL(url);
	}

	@Override
	public void sendError(final int sc, final String msg) throws IOException {
		original.sendError(sc, msg);
	}

	@Override
	public void sendError(final int sc) throws IOException {
		original.sendError(sc);
	}

	@Override
	public void sendRedirect(final String location) throws IOException {
		original.sendRedirect(location);
	}

	@Override
	public void setDateHeader(final String name, final long date) {
		original.setDateHeader(name, date);
	}

	@Override
	public void addDateHeader(final String name, final long date) {
		original.addDateHeader(name, date);
	}

	@Override
	public void setHeader(final String name, final String value) {
		original.setHeader(name, value);
	}

	@Override
	public void addHeader(final String name, final String value) {
		original.addHeader(name, value);
	}

	@Override
	public void setIntHeader(final String name, final int value) {
		original.setIntHeader(name, value);
	}

	@Override
	public void addIntHeader(final String name, final int value) {
		original.addIntHeader(name, value);
	}

	@Override
	public void setStatus(final int sc) {
		original.setStatus(sc);
	}

	@Override
	public void setStatus(final int sc, final String sm) {
		try {
			original.sendError(sc, sm);
		} catch (final IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public String getHeader(final String arg0) {
		return original.getHeader(arg0);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return original.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(final String arg0) {
		return original.getHeaders(arg0);
	}

	@Override
	public int getStatus() {
		return original.getStatus();
	}

	@Override
	public void setContentLengthLong(final long arg0) {
		/* */
	}

}