package com.setvect.bokslmusic.ui.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

public class SyncPannel extends SimplePanel {
	private final SyncServiceAsync syncService = GWT.create(SyncService.class);
	private SyncGrid syncHoriVerty1Grid = new SyncGrid();

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
		syncHoriVerty1.add(syncHoriVerty1Top);
		Button syncHoriVerty1TopBtn1 = new Button("DB동기화");
		Button syncHoriVerty1TopBtn2 = new Button("전체 폴더 동기화");
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn1);
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn2);
		TextBox syncHoriVerty1TopText = new TextBox();
		Button syncHoriVerty1TopRegBtn = new Button("등록");
		syncHoriVerty1Top.add(syncHoriVerty1TopText);
		syncHoriVerty1Top.add(syncHoriVerty1TopRegBtn);

		syncHoriVerty1.add(syncHoriVerty1Grid);
		syncHoriVerty1Grid.setGridHeight(130);
		syncHoriVerty1Grid.setWidth("100%");
		syncHoriVerty1Grid.setStyleName("listTable");
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

		syncService.getSyncList(new AsyncCallback<List<MusicDirectoryModel>>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(List<MusicDirectoryModel> result) {
				List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
				for (MusicDirectoryModel n : result) {
					MusicDirectoryModel a = new MusicDirectoryModel(n.getBasePath(), new Date());
					list.add(a);
				}
				SyncPannel.this.syncHoriVerty1Grid.store.add(list);
			}
		});

	}
}
