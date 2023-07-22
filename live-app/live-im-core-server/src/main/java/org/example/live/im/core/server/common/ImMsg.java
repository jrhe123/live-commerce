package org.example.live.im.core.server.common;

import java.util.Arrays;

public class ImMsg {

	// validate
	private short magic;
	
	// business logic code, use to send different handler
	private int code;
	
	// body length
	private int len;
	
	// message data
	private byte[] body;
	
	
	
	

	public short getMagic() {
		return magic;
	}

	public void setMagic(short magic) {
		this.magic = magic;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	
	
	@Override
	public String toString() {
		return "ImMsg [magic=" + magic + ", len=" + len + ", code=" + code + ", body=" + Arrays.toString(body) + "]";
	}
	
	
	
	
}
