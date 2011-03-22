package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.setvect.bokslmusic.ui.client.grid.AlbumGrid;
import com.setvect.bokslmusic.ui.client.grid.MusicGrid;

public class ListPannel extends SimplePanel {
	protected void onLoad() {
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
		listHoriVerty1Grid.setWidth("100%");

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
		Button listHoriVerty3SearchSampleBtn2 = new Button("검색 취소");
		listHoriVerty3SearchSample.add(listHoriVerty3SearchSampleBtn2);
		Button listHoriVerty3SearchSampleBtn3 = new Button("상세 검색");
		listHoriVerty3SearchSample.add(listHoriVerty3SearchSampleBtn3);

		Grid listHoriVerty3SearchDetail = new Grid(2, 3);
		listHoriVerty3Search.add(listHoriVerty3SearchDetail);
		listHoriVerty3SearchDetail.setVisible(false);

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
		RadioButton listHoriVerty3SearchDetailUnionAnd = new RadioButton("union", "And");
		RadioButton listHoriVerty3SearchDetailUnionOr = new RadioButton("union", "Or");
		listHoriVerty3SearchDetailUnion.add(listHoriVerty3SearchDetailUnionLab);
		listHoriVerty3SearchDetailUnion.add(listHoriVerty3SearchDetailUnionAnd);
		listHoriVerty3SearchDetailUnion.add(listHoriVerty3SearchDetailUnionOr);

		Button listHoriVerty3SearchDetailBtn = new Button("상세검색");
		listHoriVerty3SearchDetail.setWidget(1, 2, listHoriVerty3SearchDetailBtn);

		MusicGrid listHoriVerty3Grid = new MusicGrid();
		listHoriVerty3.add(listHoriVerty3Grid);
		listHoriVerty3Grid.setWidth("100%");
		add(list);
	}
}
