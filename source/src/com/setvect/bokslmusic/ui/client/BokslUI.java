package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Text;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	public void onModuleLoad() {
		VerticalPanel warp = new VerticalPanel();
		warp.add(headerPannel());
		warp.add(syncPannel());
		warp.add(playPannel());
		warp.add(listPannel());
		warp.setSize("100%", "");
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
		sync.setHeading("동길화 설정");

		HorizontalPanel h = new HorizontalPanel();
		h.setTableWidth("100%");
		VerticalPanel v1 = new VerticalPanel();
		v1.add(new HTML("동기화목록"));
		v1.add(new HTML("동기화목록 내용"));
		h.add(v1);

		VerticalPanel v2 = new VerticalPanel();
		v2.add(new HTML("메시지로그"));
		v2.add(new HTML("메시지로그 내용"));
		h.add(v2);
		sync.add(h);
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
