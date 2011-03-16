package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
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
		warp.setWidth("100%");

		VerticalPanel body = new VerticalPanel();
		warp.add(body);
		body.setWidth("95%");
		body.add(headerPannel());
		body.add(syncPannel());
		body.add(playPannel());
		body.add(listPannel());

		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(warp);
	}

	private Widget headerPannel() {
		Label titleHeader = new Label("ABC");
		return titleHeader;
	}

	private Widget syncPannel() {
		ContentPanel sync = new ContentPanel();
		sync.setCollapsible(true);
		sync.setHeading("동길화 설정");

		HorizontalPanel syncHori = new HorizontalPanel();
		sync.add(syncHori);

		syncHori.setBorderWidth(1);
		syncHori.setWidth("100%");
		
		VerticalPanel syncHoriVerty1 = new VerticalPanel();
		syncHori.add(syncHoriVerty1);
		syncHori.setCellWidth(syncHoriVerty1, "420");

		syncHoriVerty1.add(new HTML("동기화목록"));

		FlowPanel syncHoriVerty1Top = new FlowPanel();
		Button syncHoriVerty1TopBtn1 = new Button("DB동기화");
		Button syncHoriVerty1TopBtn2 = new Button("전체 폴더 동기화");
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn1);
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn2);
		syncHoriVerty1.add(syncHoriVerty1Top);
		GridExample syncHoriVerty1Grid = new GridExample();
		syncHoriVerty1.add(syncHoriVerty1Grid);

		FlowPanel syncHoriVerty1Bottom = new FlowPanel();
		TextBox syncHoriVerty1BottomText = new TextBox();
		Button syncHoriVerty1BottomBtn = new Button("등록");
		syncHoriVerty1Bottom.add(syncHoriVerty1BottomText);
		syncHoriVerty1Bottom.add(syncHoriVerty1BottomBtn);
		syncHoriVerty1.add(syncHoriVerty1Bottom);

		VerticalPanel syncHoriVerty2 = new VerticalPanel();
		syncHori.add(syncHoriVerty2);
		syncHoriVerty2.setWidth("100%");
		syncHoriVerty2.add(new HTML("메시지로그"));

		ScrollPanel syncHoriVerty2Scroll = new ScrollPanel(new HTML(
				"메<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>시지로그 내용"));
		syncHoriVerty2Scroll.setHeight("200px");
		syncHoriVerty2.add(syncHoriVerty2Scroll);

		return sync;
	}

	private Widget playPannel() {
		ContentPanel play = new ContentPanel();
		play.setCollapsible(true);
		play.setHeading("음악재생");
		TextBox txt = new TextBox();
		play.add(txt);
		txt.setText("재생 패널");
		return play;
	}

	private Widget listPannel() {
		ContentPanel list = new ContentPanel();
		list.setCollapsible(true);
		TextBox txt = new TextBox();
		list.add(txt);
		txt.setText("재생 패널");
		return list;
	}
}
