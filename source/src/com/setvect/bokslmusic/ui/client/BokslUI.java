package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	public void onModuleLoad() {

		HorizontalPanel warp = new HorizontalPanel();
		warp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		warp.getElement().setId("warp");

		VerticalPanel contentBody = new VerticalPanel();
		warp.add(contentBody);
		contentBody.getElement().setId("contentBody");
		contentBody.add(headerPannel());
		contentBody.add(syncPannel());
		contentBody.add(playPannel());
		contentBody.add(listPannel());

		RootPanel rootPanel = RootPanel.get();
		// rootPanel에 추가는 맨 나중에 해야지 포함된 Element가 나타남
		rootPanel.add(warp);
	}

	private Widget headerPannel() {
		Label titleHeader = new Label("ABC");
		return titleHeader;
	}

	private Widget syncPannel() {
		ContentPanel sync = new ContentPanel();
		sync.setId("syncPannel");
		sync.setCollapsible(true);
		sync.setHeading("동길화 설정");

		HorizontalPanel syncHori = new HorizontalPanel();
		sync.add(syncHori);
		syncHori.setStyleName("spliteHorizontal");

		VerticalPanel syncHoriVerty1 = new VerticalPanel();
		syncHori.add(syncHoriVerty1);
		syncHori.setCellWidth(syncHoriVerty1, "420");

		Label syncHoriVerty1Label = new Label("동기화목록");
		syncHoriVerty1.add(syncHoriVerty1Label);
		syncHoriVerty1Label.setStyleName("elementTitle");

		FlowPanel syncHoriVerty1Top = new FlowPanel();
		Button syncHoriVerty1TopBtn1 = new Button("DB동기화");
		Button syncHoriVerty1TopBtn2 = new Button("전체 폴더 동기화");
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn1);
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn2);
		syncHoriVerty1.add(syncHoriVerty1Top);

		GridExample syncHoriVerty1Grid = new GridExample();
		syncHoriVerty1.add(syncHoriVerty1Grid);
		syncHoriVerty1Grid.setGridHeight(130);
		syncHoriVerty1Grid.setStyleName("listTable");

		FlowPanel syncHoriVerty1Bottom = new FlowPanel();
		TextBox syncHoriVerty1BottomText = new TextBox();
		Button syncHoriVerty1BottomBtn = new Button("등록");
		syncHoriVerty1Bottom.add(syncHoriVerty1BottomText);
		syncHoriVerty1Bottom.add(syncHoriVerty1BottomBtn);
		syncHoriVerty1.add(syncHoriVerty1Bottom);

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

		ScrollPanel syncHoriVerty2Scroll = new ScrollPanel(new HTML(
				"메<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>시지로그 내용"));
		syncHoriVerty2Scroll.setStyleName("scroll");
		syncHoriVerty2.add(syncHoriVerty2Scroll);

		return sync;
	}

	private Widget playPannel() {
		ContentPanel play = new ContentPanel();
		play.setId("playPannel");
		play.setCollapsible(true);
		play.setHeading("음악재생");
		TextBox txt = new TextBox();
		play.add(txt);
		txt.setText("재생 패널");
		return play;
	}

	private Widget listPannel() {
		ContentPanel list = new ContentPanel();
		list.setId("listPannel");
		list.setCollapsible(true);
		TextBox txt = new TextBox();
		list.add(txt);
		txt.setText("재생 패널");
		return list;
	}
}
