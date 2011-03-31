package com.setvect.bokslmusic.ui.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
import com.setvect.bokslmusic.ui.client.grid.SyncGrid.SyncGridButtonEvent;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid.SyncGridButtonEvent.BehaviorType;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

public class SyncPannel extends SimplePanel {
	private final SyncServiceAsync syncService = GWT.create(SyncService.class);
	private SyncGrid syncHoriVerty1Grid = new SyncGrid();
	private TextBox syncHoriVerty1TopText = new TextBox();
	private HTML syncHoriVerty2ScrollContent;
	private ScrollPanel syncHoriVerty2Scroll;

	/** 음악 파일 동기화 진행중이면 true */
	private boolean synchronizing = false;

	/** 동기화시 주기적으로 서버측으로 부터 동기화 로그를 가져옴 */
	private Timer syncMessageGetter = new Timer() {
		public void run() {
			syncService.getSyncLog(new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				public void onSuccess(String load) {
					String log = syncHoriVerty2ScrollContent.getHTML() + "\n" + load.replace("\n", "<br>");
					syncHoriVerty2ScrollContent.setHTML(log);
					// 맨 끝으로 지정
					syncHoriVerty2Scroll.setHorizontalScrollPosition(10000);
				}
			});
			if(!synchronizing){
				this.cancel();
			}
		}
	};

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

		Button syncHoriVerty1TopRegBtn = new Button("등록");
		syncHoriVerty1Top.add(syncHoriVerty1TopText);
		syncHoriVerty1Top.add(syncHoriVerty1TopRegBtn);

		syncHoriVerty1.add(syncHoriVerty1Grid);
		syncHoriVerty1Grid.setGridHeight(130);
		syncHoriVerty1Grid.setWidth("100%");
		syncHoriVerty1Grid.setStyleName("listTable");
		syncHoriVerty1Grid.addSyncButtonListener(new BokslMusicEventListenerImpl());
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

		Button syncHoriVerty2HeaderLoad = new Button("불러오기");
		syncHoriVerty2Header.add(syncHoriVerty2HeaderLoad);

		Button syncHoriVerty2HeaderDel = new Button("지우기");
		syncHoriVerty2Header.add(syncHoriVerty2HeaderDel);

		syncHoriVerty2ScrollContent = new HTML("메시지로그 내용");
		syncHoriVerty2Scroll = new ScrollPanel(syncHoriVerty2ScrollContent);
		syncHoriVerty2.add(syncHoriVerty2Scroll);
		syncHoriVerty2Scroll.setStyleName("scroll");
		add(sync);

		// ------------ 이벤트 핸들러 등록
		// 동기화 목록을 가져옴
		syncList();

		syncHoriVerty1TopRegBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String dir = syncHoriVerty1TopText.getText();
				syncService.addSyncPath(dir, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean session) {
						syncHoriVerty1TopText.setText("");
						syncList();
					}
				});
			}
		});

		syncHoriVerty2HeaderDel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				syncHoriVerty2ScrollContent.setText("");
			}
		});

	}

	/**
	 * 동기화 디렉토리 목록 처리 Callback
	 */
	private void syncList() {
		syncService.getSyncList(new AsyncCallback<List<MusicDirectoryModel>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<MusicDirectoryModel> result) {
				List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
				for (MusicDirectoryModel n : result) {
					MusicDirectoryModel a = new MusicDirectoryModel(n.getBasePath(), new Date());
					list.add(a);
				}
				syncHoriVerty1Grid.store.removeAll();
				syncHoriVerty1Grid.store.add(list);
			}
		});
	}

	/**
	 * 동기화 버튼 이벤트
	 */
	class BokslMusicEventListenerImpl implements BokslMusicEventListener {
		public void onClick(Object eventObject) {
			SyncGridButtonEvent syncEventObject = (SyncGridButtonEvent) eventObject;
			if (syncEventObject.getBehaviorType() == BehaviorType.DELETE) {
				syncService.removeMusicPath(syncEventObject.getPath(), new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						syncList();
					}
				});
			}
			else if (syncEventObject.getBehaviorType() == BehaviorType.SYNC) {
				synchronizing = true;
				syncMessageGetter.scheduleRepeating(2000);
				syncService.syncDirectory(syncEventObject.getPath(), new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						synchronizing = false;
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						synchronizing = false;
					}
				});
			}
		}
	}
}
