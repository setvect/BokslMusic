package com.setvect.bokslmusic.ui.client.event;

import java.io.Serializable;


/**
 * Comnet를 통해 받은 메시지를 처리 하는 이벤트 
 */
public interface CometMessageListener<D extends Serializable> {
	public void messageProcess(D d);
}
