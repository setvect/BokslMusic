package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
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
import com.setvect.bokslmusic.ui.client.service.SyncService;
import com.setvect.bokslmusic.ui.client.service.SyncServiceAsync;
import com.setvect.bokslmusic.ui.shared.verify.SyncVerifier;

public class SyncPannel extends SimplePanel {
	private final SyncServiceAsync syncService = GWT.create(SyncService.class);
	private SyncGrid syncHoriVerty1Grid = new SyncGrid();
	private HTML syncHoriVerty2ScrollContent;
	private ScrollPanel syncHoriVerty2Scroll;

	/** 음악 파일 동기화 진행중이면 true */
	private boolean synchronizing = false;

	private Label syncHoriVerty1Label;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		ContentPanel sync = new ContentPanel();

		// --------------------------
		sync.setId("syncPannel");
		sync.setCollapsible(true);
		sync.setExpanded(false);
		sync.setHeading("동길화 설정");

		HorizontalPanel syncHori = new HorizontalPanel();
		sync.add(syncHori);
		syncHori.setStyleName("spliteHorizontal");

		VerticalPanel syncHoriVerty1 = new VerticalPanel();
		syncHori.add(syncHoriVerty1);

		syncHoriVerty1Label = new Label("동기화목록");
		syncHoriVerty1.add(syncHoriVerty1Label);
		syncHoriVerty1Label.setStyleName("subPannelTitle");

		FlowPanel syncHoriVerty1Top = new FlowPanel();
		syncHoriVerty1.add(syncHoriVerty1Top);
		Button syncHoriVerty1TopBtn1 = new Button("DB동기화");
		Button syncHoriVerty1TopBtn2 = new Button("전체 폴더 동기화");
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn1);
		syncHoriVerty1Top.add(syncHoriVerty1TopBtn2);

		Button syncHoriVerty1TopRegBtn = new Button("등록");
		final TextBox syncHoriVerty1TopText = new TextBox();
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

		Button syncHoriVerty2HeaderDel = new Button("지우기");
		syncHoriVerty2Header.add(syncHoriVerty2HeaderDel);
		syncHoriVerty2ScrollContent = new HTML();
		syncHoriVerty2ScrollContent.addStyleName("logContent");
		syncHoriVerty2Scroll = new ScrollPanel(syncHoriVerty2ScrollContent);
		syncHoriVerty2.add(syncHoriVerty2Scroll);
		syncHoriVerty2Scroll.setStyleName("scroll");
		add(sync);

		// ------------ 이벤트 핸들러 등록
		// 동기화 목록을 가져옴
		syncHoriVerty1Grid.reloadSyncList();

		sync.addListener(Events.Expand, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				syncHoriVerty1Grid.fitLayout();
			}
		});

		syncHoriVerty1TopRegBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String dir = syncHoriVerty1TopText.getText();
				if (!SyncVerifier.isVaildSyncDir(dir)) {
					Window.alert("디렉토리 경로를 제대로 입력 ");
					return;
				}

				syncService.addSyncPath(dir, new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						syncHoriVerty1TopText.setText("");
						if (result) {
							syncHoriVerty1Grid.reloadSyncList();
						}
						else {
							Window.alert("올바른 시스템 경로가 입력되지 않았습니다.");
						}
					}
				});
			}
		});

		syncHoriVerty2HeaderDel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				syncHoriVerty2ScrollContent.setText("");
			}
		});

		// Sync 로그 영역 자동 조절 사용하지 않음
		//
		// Window.addResizeHandler(new ResizeHandler() {
		// public void onResize(ResizeEvent event) {
		// repositionSyncLoadArea();
		// }
		// });
		// repositionSyncLoadArea();
	}

	/**
	 * Sync 로그 창에 가로 스크롤을 생기게 하기 위해 브라우저 넓이와 로그영을 포함한 테이블의 동기화 목록의 넓이를 이용해서
	 * 로그영역의 넓이를 계산
	 */
	@Deprecated
	private void repositionSyncLoadArea() {
		int clientWidth = Window.getClientWidth();
		int offsetWidth = syncHoriVerty1Label.getOffsetWidth();
		int width = clientWidth - offsetWidth - 30;
		String width2 = width + "px";
		syncHoriVerty2Scroll.setWidth(width2);
	}


	/**
	 * 동기화 버튼 이벤트
	 */
	class BokslMusicEventListenerImpl implements BokslMusicEventListener {
		/** 동기화시 주기적으로 서버측으로 부터 동기화 로그를 가져옴 */
		Timer syncMessageGetter = new SyncLogLoadTimer();

		public void onClick(Object eventObject) {
			SyncGridButtonEvent syncEventObject = (SyncGridButtonEvent) eventObject;
			if (syncEventObject.getBehaviorType() == BehaviorType.DELETE) {
				syncService.removeMusicPath(syncEventObject.getPath(), new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						syncHoriVerty1Grid.reloadSyncList();
					}
				});
			}
			else if (syncEventObject.getBehaviorType() == BehaviorType.SYNC) {
				syncHoriVerty1Grid.disableSyncButton();

				synchronizing = true;
				syncMessageGetter.scheduleRepeating(2000);
				syncService.syncDirectory(syncEventObject.getPath(), new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						synchronizing = false;
						syncHoriVerty1Grid.enableSyncButton();
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						syncHoriVerty1Grid.enableSyncButton();
						synchronizing = false;
					}
				});
			}
		}
	}

	/**
	 * 동기화시 주기적으로 서버측으로 부터 동기화 로그를 가져옴
	 */
	class SyncLogLoadTimer extends Timer {
		@Override
		public void run() {
			syncService.getSyncLog(new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				public void onSuccess(String load) {
					String log = load.replace("\n", "<br>");
					syncHoriVerty2ScrollContent.setHTML(log);
					// 맨 끝으로 지정
					syncHoriVerty2Scroll.scrollToBottom();
					syncHoriVerty2Scroll.scrollToLeft();
				}
			});
			if (!synchronizing) {
				this.cancel();
			}
		}
	}
}
