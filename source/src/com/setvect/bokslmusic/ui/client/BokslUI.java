package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	public void onModuleLoad() {
		Viewport warp = new Viewport();
		warp.setLayout(new RowLayout());
		warp.add(headerPannel());
		warp.add(syncPannel());
		warp.add(playPannel());
		warp.add(listPannel());
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(warp);
	}

	private Widget headerPannel() {
		Label titleHeader = new Label("복슬 Music");
		return titleHeader;
	}

	private Widget syncPannel() {
		ContentPanel sync = new ContentPanel();
		sync.setCollapsible(true);
		sync.add(new Text("동기화 설정 패널"));
		return sync;
	}

	private Widget playPannel() {
		ContentPanel play = new ContentPanel();
		play.setCollapsible(true);
		play.setHeading("음악재생");
		play.add(new Text("재생 패널"));
		return play;
	}

	private Widget listPannel() {
		ContentPanel list = new ContentPanel();
		list.setCollapsible(true);
		list.add(new Text("리스트 패널"));
		return list;
	}

}
