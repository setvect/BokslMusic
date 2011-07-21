package com.setvect.bokslmusic.ui.client.event;

import java.util.List;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;

/**
 * 전체 목록 이벤트
 * 
 * @version $Id$
 */
public interface AllListGridEventListener<E extends BaseEvent> extends Listener<E> {
	public void addMusicEvent(List<MusicArticleModel> items);
}
