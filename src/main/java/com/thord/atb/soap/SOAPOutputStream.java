package com.thord.atb.soap;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;

public class SOAPOutputStream extends OutputStream {
	private List<Byte> data = Lists.newArrayList();

	@Override
	public void write(int x) throws IOException {
		this.data.add((byte) x);
	}

	public List<Byte> getData() {
		return data;
	}

	@Override
	public String toString() {
		return new String(Bytes.toArray(data), Charsets.UTF_8);
	}
}
