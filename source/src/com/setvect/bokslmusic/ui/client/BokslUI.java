package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.form.SliderField;
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

		return sync;
	}

	private Widget playPannel() {
		ContentPanel play = new ContentPanel();

		// --------------------------
		play.setId("playPannel");
		play.setCollapsible(true);
		play.setHeading("재생");
		HorizontalPanel playHori = new HorizontalPanel();
		play.add(playHori);
		playHori.setStyleName("spliteHorizontal");

		VerticalPanel playHoriVerty1 = new VerticalPanel();
		playHori.add(playHoriVerty1);

		Label playHoriVerty1Label = new Label("제어판");
		playHoriVerty1.add(playHoriVerty1Label);
		playHoriVerty1Label.setStyleName("subPannelTitle");

		VerticalPanel playHoriVerty1Control = new VerticalPanel();
		playHoriVerty1.add(playHoriVerty1Control);

		HorizontalPanel playHoriVerty1ControlH1 = new HorizontalPanel();
		playHoriVerty1Control.add(playHoriVerty1ControlH1);

		Label playHoriVerty1ControlH1Title = new Label("재생파일명()");
		playHoriVerty1ControlH1.add(playHoriVerty1ControlH1Title);

		Label playHoriVerty1ControlH1Time = new Label("00:20");
		playHoriVerty1ControlH1.add(playHoriVerty1ControlH1Time);

		HorizontalPanel playHoriVerty1ControlH2 = new HorizontalPanel();
		playHoriVerty1Control.add(playHoriVerty1ControlH2);

		Slider playHoriVerty1ControlH2PosSlider = new Slider();
		playHoriVerty1ControlH2PosSlider.setMinValue(40);
		playHoriVerty1ControlH2PosSlider.setMaxValue(90);
		SliderField playHoriVerty1ControlH2PosSliderField = new SliderField(playHoriVerty1ControlH2PosSlider);
		playHoriVerty1ControlH2.add(playHoriVerty1ControlH2PosSliderField);

		playHoriVerty1ControlH2PosSliderField.setFieldLabel("Size");
		playHoriVerty1ControlH2PosSliderField.setWidth(120);

		// TODO Icon으로 변경
		Label playHoriVerty1ControlH2VolumIcon = new Label(">>	");
		playHoriVerty1ControlH2.add(playHoriVerty1ControlH2VolumIcon);

		Slider playHoriVerty1ControlH2VolumSlider = new Slider();
		playHoriVerty1ControlH2VolumSlider.setMinValue(0);
		playHoriVerty1ControlH2VolumSlider.setMaxValue(100);

		SliderField playHoriVerty1ControlH2VolumSliderField = new SliderField(playHoriVerty1ControlH2VolumSlider);
		playHoriVerty1ControlH2.add(playHoriVerty1ControlH2VolumSliderField);
		playHoriVerty1ControlH2VolumSliderField.setFieldLabel("Size");
		playHoriVerty1ControlH2VolumSliderField.setWidth(50);

		HorizontalPanel playHoriVerty1ControlH3 = new HorizontalPanel();
		playHoriVerty1Control.add(playHoriVerty1ControlH3);

		Label playHoriVerty1ControlH3Bit = new Label("192KB");
		playHoriVerty1ControlH3.add(playHoriVerty1ControlH3Bit);

		Label playHoriVerty1ControlH3Sampling = new Label("44KH");
		playHoriVerty1ControlH3.add(playHoriVerty1ControlH3Sampling);

		FlowPanel playHoriVerty1ControlH3Flow = new FlowPanel();
		playHoriVerty1ControlH3.add(playHoriVerty1ControlH3Flow);

		Button playHoriVerty1ControlH3FlowPrevious = new Button("<<");
		playHoriVerty1ControlH3Flow.add(playHoriVerty1ControlH3FlowPrevious);
		Button playHoriVerty1ControlH3FlowPause = new Button("||");
		playHoriVerty1ControlH3Flow.add(playHoriVerty1ControlH3FlowPause);
		Button playHoriVerty1ControlH3FlowStop = new Button("■");
		playHoriVerty1ControlH3Flow.add(playHoriVerty1ControlH3FlowStop);
		Button playHoriVerty1ControlH3FlowNext = new Button(">>");
		playHoriVerty1ControlH3Flow.add(playHoriVerty1ControlH3FlowNext);

		// --------------------------

		VerticalPanel playHoriVerty2 = new VerticalPanel();
		playHori.add(playHoriVerty2);
		playHoriVerty2.setStyleName("lyric");

		Label playHoriVerty2Label = new Label("가사");
		playHoriVerty2.add(playHoriVerty2Label);
		playHoriVerty2Label.setStyleName("subPannelTitle");

		ScrollPanel playHoriVerty2Scroll = new ScrollPanel(new HTML("내<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>용"));
		playHoriVerty2.add(playHoriVerty2Scroll);
		playHoriVerty2Scroll.setStyleName("scroll");

		// --------------------------

		VerticalPanel playHoriVerty3 = new VerticalPanel();
		playHori.add(playHoriVerty3);

		HorizontalPanel playHoriVerty3Header = new HorizontalPanel();
		playHoriVerty3.add(playHoriVerty3Header);
		playHoriVerty3Header.setStyleName("subPannelHeader");

		Label playHoriVerty3HeaderLabel = new Label("재생목록");
		playHoriVerty3Header.add(playHoriVerty3HeaderLabel);
		playHoriVerty3HeaderLabel.setStyleName("subPannelTitle");

		Label playHoriVerty3HeaderTime = new Label("[0:00:10]");
		playHoriVerty3Header.add(playHoriVerty3HeaderTime);

		Button playHoriVerty3HeaderSort = new Button("정렬");
		playHoriVerty3Header.add(playHoriVerty3HeaderSort);

		PlayGrid playHoriVerty3Grid = new PlayGrid();
		playHoriVerty3.add(playHoriVerty3Grid);
		playHoriVerty3Grid.setGridHeight(130);
		playHoriVerty3Grid.setStyleName("listTable");

		FlowPanel playHoriVerty3Bottom = new FlowPanel();
		TextBox playHoriVerty3BottomText = new TextBox();
		Button playHoriVerty3BottomBtn = new Button("앨범저장");
		playHoriVerty3Bottom.add(playHoriVerty3BottomText);
		playHoriVerty3Bottom.add(playHoriVerty3BottomBtn);
		playHoriVerty3.add(playHoriVerty3Bottom);

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
