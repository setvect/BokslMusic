package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid;

public class SyncPannel extends SimplePanel {
	protected void onLoad() {
		ContentPanel sync = new ContentPanel();

		// --------------------------
		sync.setId("syncPannel");
		sync.setCollapsible(true);
		sync.setHeading("동길화 설정");

		HorizontalPanel syncHori = new HorizontalPanel();
		sync.add(syncHori);
		syncHori.setStyleName("spliteHorizontal");

		VerticalPanel syncHoriVerty1 = new VerticalPanel();
		syncHori.add(syncHoriVerty1);

		Label syncHoriVerty1Label = new Label("동기화목록");
		syncHoriVerty1.add(syncHoriVerty1Label);
		syncHoriVerty1Label.setStyleName("subPannelTitle");

		FlowPanel syncHoriVerty1Top = new FlowPanel();
		Button syncHoriVerty1TopBtn1 = new Button("DB동기화");
		Button syncHoriVerty1TopBtn2 = new Button("전체 폴더 동기화");
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn1);
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn2);
		syncHoriVerty1.add(syncHoriVerty1Top);

		SyncGrid syncHoriVerty1Grid = new SyncGrid();
		syncHoriVerty1.add(syncHoriVerty1Grid);
		syncHoriVerty1Grid.setGridHeight(130);
		syncHoriVerty1Grid.setWidth("100%");
		syncHoriVerty1Grid.setStyleName("listTable");

		FlowPanel syncHoriVerty1Bottom = new FlowPanel();
		TextBox syncHoriVerty1BottomText = new TextBox();
		Button syncHoriVerty1BottomBtn = new Button("등록");
		syncHoriVerty1Bottom.add(syncHoriVerty1BottomText);
		syncHoriVerty1Bottom.add(syncHoriVerty1BottomBtn);
		syncHoriVerty1.add(syncHoriVerty1Bottom);

		// --------------------------
		VerticalPanel syncHoriVerty2 = new VerticalPanel();
		syncHori.add(syncHoriVerty2);
		syncHoriVerty2.setStyleName("log");

		HorizontalPanel syncHoriVerty2Header = new HorizontalPanel();
		syncHoriVerty2.add(syncHoriVerty2Header);
		syncHoriVerty2Header.setStyleName("subPannelHeader");

		Label syncHoriVerty2HeaderLabel = new Label("메시지 로그");
		syncHoriVerty2Header.add(syncHoriVerty2HeaderLabel);
		syncHoriVerty2HeaderLabel.setStyleName("subPannelTitle");

		Button syncHoriVerty2HeaderDel = new Button("지우기");
		syncHoriVerty2Header.add(syncHoriVerty2HeaderDel);

		ScrollPanel syncHoriVerty2Scroll = new ScrollPanel(new HTML("메시지로그 내용"));
		syncHoriVerty2.add(syncHoriVerty2Scroll);
		syncHoriVerty2Scroll.setStyleName("scroll");
		add(sync);
	}
}
