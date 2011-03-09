package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Text;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
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

		HorizontalPanel syncHori = new HorizontalPanel();
		syncHori.setTableWidth("100%");
		VerticalPanel syncHoriVerty1 = new VerticalPanel();
		syncHoriVerty1.add(new HTML("동기화목록"));
		FlowPanel syncHoriVerty1Top = new FlowPanel();
		Button syncHoriVerty1TopBtn1 = new Button("DB동기화");
		Button syncHoriVerty1TopBtn2 = new Button("전체 폴더 동기화");
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn1);
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn2);
		syncHoriVerty1.add(syncHoriVerty1Top);
		GridExample syncHoriVerty1Grid = new GridExample();
		syncHoriVerty1.add(syncHoriVerty1Grid);
		syncHori.add(syncHoriVerty1);

		VerticalPanel syncHoriVerty2 = new VerticalPanel();
		syncHoriVerty2.add(new HTML("메시지로그"));
		syncHoriVerty2.add(new HTML("메시지로그 내용"));
		syncHori.add(syncHoriVerty2);
		sync.add(syncHori);
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
