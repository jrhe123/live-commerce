package org.example.live.im.core.server.common;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

import org.example.live.im.constants.ImConstants;

public class ImMsg implements Serializable{

	@Serial
	private static final long serialVersionUID = 4788172126955698333L;

	// validate
	private short magic;
	
	// business logic code, use to send different handler
	private int code;
	
	// body length
	private int len;
	
	// message data
	private byte[] body;
	
	
	
	// static build
	public static ImMsg build(int code, String data) {
		ImMsg imMsg = new ImMsg();
		imMsg.setMagic(ImConstants.DEFAULT_MAGIC);
		imMsg.setCode(code);
		imMsg.setBody(data.getBytes());
		imMsg.setLen(imMsg.getBody().length);
		return imMsg;
	}
	
	
	
	
	

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
