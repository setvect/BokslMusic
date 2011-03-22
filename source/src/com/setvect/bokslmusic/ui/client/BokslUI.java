package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.setvect.bokslmusic.ui.client.grid.AlbumGrid;
import com.setvect.bokslmusic.ui.client.grid.PlayGrid;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid;

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

		ScrollPanel playHoriVerty2Scroll = new ScrollPanel(new HTML(
				"내<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>용"));
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
		list.setHeaderVisible(false);
		HorizontalPanel listHori = new HorizontalPanel();
		list.add(listHori);
		listHori.setStyleName("spliteHorizontal");

		// --------------------------
		VerticalPanel listHoriVerty1 = new VerticalPanel();
		listHori.add(listHoriVerty1);

		HorizontalPanel listHoriVerty1Header = new HorizontalPanel();
		listHoriVerty1.add(listHoriVerty1Header);
		listHoriVerty1Header.add(new Label("앨범"));
		Button listHoriVerty1HeaderBtn1 = new Button("바로재생");
		listHoriVerty1Header.add(listHoriVerty1HeaderBtn1);
		Button listHoriVerty1HeaderBtn2 = new Button("추가");
		listHoriVerty1Header.add(listHoriVerty1HeaderBtn2);

		FlowPanel listHoriVerty1Add = new FlowPanel();
		listHoriVerty1.add(listHoriVerty1Add);
		TextBox listHoriVerty1AddName = new TextBox();
		listHoriVerty1Add.add(listHoriVerty1AddName);
		Button listHoriVerty1AddBtn = new Button("앨범 추가");
		listHoriVerty1Add.add(listHoriVerty1AddBtn);
		AlbumGrid listHoriVerty1Grid = new AlbumGrid();
		listHoriVerty1.add(listHoriVerty1Grid);

		// --------------------------

		VerticalPanel listHoriVerty2 = new VerticalPanel();
		listHori.add(listHoriVerty2);

		VerticalPanel listHoriVerty2Movie = new VerticalPanel();
		listHoriVerty2.add(listHoriVerty2Movie);
		listHoriVerty2Movie.add(new Label("◀"));
		listHoriVerty2Movie.add(new Label("▶"));

		// --------------------------
		VerticalPanel listHoriVerty3 = new VerticalPanel();
		listHori.add(listHoriVerty3);

		HorizontalPanel listHoriVerty3Header = new HorizontalPanel();
		listHoriVerty3.add(listHoriVerty3Header);
		listHoriVerty3Header.add(new Label("전체목록"));
		Button listHoriVerty3HeaderBtn1 = new Button("바로재생");
		listHoriVerty3Header.add(listHoriVerty3HeaderBtn1);
		Button listHoriVerty3HeaderBtn2 = new Button("추가");
		listHoriVerty3Header.add(listHoriVerty3HeaderBtn2);

		VerticalPanel listHoriVerty3Search = new VerticalPanel();
		listHoriVerty3.add(listHoriVerty3Search);

		FlowPanel listHoriVerty3SearchSample = new FlowPanel();
		listHoriVerty3Search.add(listHoriVerty3SearchSample);

		TextBox listHoriVerty3SearchSampleText = new TextBox();
		listHoriVerty3SearchSample.add(listHoriVerty3SearchSampleText);

		Button listHoriVerty3SearchSampleBtn1 = new Button("검색");
		listHoriVerty3SearchSample.add(listHoriVerty3SearchSampleBtn1);
		Button listHoriVerty3SearchSampleBtn2 = new Button("상세 검색");
		listHoriVerty3SearchSample.add(listHoriVerty3SearchSampleBtn2);
		Button listHoriVerty3SearchSampleBtn3 = new Button("검색 취소");
		listHoriVerty3SearchSample.add(listHoriVerty3SearchSampleBtn3);

		Grid listHoriVerty3SearchDetail = new Grid(2, 3);
		listHoriVerty3Search.add(listHoriVerty3SearchDetail);

		FlowPanel listHoriVerty3SearchDetailFile = new FlowPanel();
		listHoriVerty3SearchDetail.setWidget(0, 0, listHoriVerty3SearchDetailFile);
		Label listHoriVerty3SearchDetailFileLab = new Label("파일명");
		TextBox listHoriVerty3SearchDetailFileTxt = new TextBox();
		listHoriVerty3SearchDetailFile.add(listHoriVerty3SearchDetailFileLab);
		listHoriVerty3SearchDetailFile.add(listHoriVerty3SearchDetailFileTxt);

		FlowPanel listHoriVerty3SearchDetailTitle = new FlowPanel();
		listHoriVerty3SearchDetail.setWidget(0, 1, listHoriVerty3SearchDetailTitle);
		Label listHoriVerty3SearchDetailTitleLab = new Label("제목");
		TextBox listHoriVerty3SearchDetailTitleTxt = new TextBox();
		listHoriVerty3SearchDetailTitle.add(listHoriVerty3SearchDetailTitleLab);
		listHoriVerty3SearchDetailTitle.add(listHoriVerty3SearchDetailTitleTxt);

		FlowPanel listHoriVerty3SearchDetailArtist = new FlowPanel();
		listHoriVerty3SearchDetail.setWidget(0, 2, listHoriVerty3SearchDetailArtist);
		Label listHoriVerty3SearchDetailArtistLab = new Label("아티스트");
		TextBox listHoriVerty3SearchDetailArtistTxt = new TextBox();
		listHoriVerty3SearchDetailArtist.add(listHoriVerty3SearchDetailArtistLab);
		listHoriVerty3SearchDetailArtist.add(listHoriVerty3SearchDetailArtistTxt);

		FlowPanel listHoriVerty3SearchDetailLyrics = new FlowPanel();
		listHoriVerty3SearchDetail.setWidget(1, 0, listHoriVerty3SearchDetailLyrics);
		Label listHoriVerty3SearchDetailLyricsLab = new Label("가사");
		TextBox listHoriVerty3SearchDetailLyricsTxt = new TextBox();
		listHoriVerty3SearchDetailLyrics.add(listHoriVerty3SearchDetailLyricsLab);
		listHoriVerty3SearchDetailLyrics.add(listHoriVerty3SearchDetailLyricsTxt);

		FlowPanel listHoriVerty3SearchDetailUnion = new FlowPanel();
		listHoriVerty3SearchDetail.setWidget(1, 1, listHoriVerty3SearchDetailUnion);
		Label listHoriVerty3SearchDetailUnionLab = new Label("결합");
		RadioButton listHoriVerty3SearchDetailUnionAnd = new RadioButton("and", "And");
		RadioButton listHoriVerty3SearchDetailUnionOr = new RadioButton("or", "Or");
		listHoriVerty3SearchDetailUnion.add(listHoriVerty3SearchDetailUnionLab);
		listHoriVerty3SearchDetailUnion.add(listHoriVerty3SearchDetailUnionAnd);
		listHoriVerty3SearchDetailUnion.add(listHoriVerty3SearchDetailUnionOr);

		Button listHoriVerty3SearchDetailBtn = new Button("상세검색");
		listHoriVerty3SearchDetail.setWidget(1, 2, listHoriVerty3SearchDetailBtn);

		return list;
	}
}