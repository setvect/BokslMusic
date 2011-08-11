package com.setvect.bokslmusic.ui.client.grid;

import java.io.Serializable;
import java.util.Arrays;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.DragEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.setvect.bokslmusic.ui.client.BokslUI;
import com.setvect.bokslmusic.ui.client.event.CometMessageListener;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerServiceAsync;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.PlayArticleModel;
import com.setvect.bokslmusic.ui.shared.model.PlayTimeRateComet;

public class PlayListGrid extends ContentPanel {

	private static final int DEFAULT_VOLUME = 50;
	private ColumnModel cm;
	private final MusicManagerServiceAsync service = GWT.create(MusicManagerService.class);
	/** 그리드 데이터 */
	private ListStore<MusicArticleModel> store = new ListStore<MusicArticleModel>();

	/** 현재 재생 항목 */
	private PlayArticleModel currentPlayArticle;

	/** 플레이어 상태 */
	private Status status = Status.STOP;

	/**
	 * 현재 진행 상태
	 */
	enum Status {
		STOP, PLAY, PAUSE
	}

	/** 위치 이동 */
	private Slider positionSlider = new Slider() {
		protected String onFormatValue(int value) {
			if (currentPlayArticle == null) {
				return "0";
			}
			if (currentPlayArticle.getRunningTime() >= 60 * 60) {
				return ClientUtil.getHourMinuteSec(value);
			}
			else {
				return ClientUtil.getMinuteSec(value);
			}
		}

		protected void onDragEnd(DragEvent de) {
			super.onDragEnd(de);
			if (status != Status.STOP) {
				double rate = (double) this.getValue() / this.getMaxValue();
				service.seek(rate, seekCallback());
			}
		}

		private AsyncCallback<Void> seekCallback() {
			AsyncCallback<Void> aa = new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				public void onSuccess(Void result) {
				}
			};
			return aa;
		}

	};

	/** 볼륨 조정 */
	private Slider volumeSlider = new Slider() {
		protected void onValueChange(int value) {
			super.onValueChange(value);
			double volume = value / 100.0;
			service.setVolume(volume, volumeCallback());
		}

		private AsyncCallback<Void> volumeCallback() {
			AsyncCallback<Void> aa = new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				public void onSuccess(Void result) {
				}
			};
			return aa;
		}
	};

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		getAriaSupport().setPresentation(true);
		RowNumberer numberer = new RowNumberer();

		ColumnConfig colTitle = new ColumnConfig("name", "Name", 200);
		colTitle.setRowHeader(true);
		colTitle.setSortable(false);

		ColumnConfig colDate = new ColumnConfig("runningTime", "Time", 100);
		// 초 단위 숫자를 => "2:23" 이런 형식으로 변경
		colDate.setRenderer(ClientUtil.TIME_RENDERER);

		cm = new ColumnModel(Arrays.asList(numberer, colTitle, colDate));

		final Grid<MusicArticleModel> grid = new Grid<MusicArticleModel>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("basePath");
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.addPlugin(numberer);

		ToolBar toolBar = new ToolBar();
		final Button playAndPauseBtn = new Button("Play");
		playAndPauseBtn.addListener(Events.OnClick, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent be) {
				if (status == Status.PLAY) {
					service.pause(pauseCallback());
					return;
				}
				else if (status == Status.PAUSE) {
					service.resume(resumeCallback());
				}

				ListStore<MusicArticleModel> list = grid.getStore();
				if (list.getCount() == 0) {
					return;
				}
				MusicArticleModel m = list.getAt(0);
				if (status == Status.STOP) {
					service.play(m.getId(), playCallback());

					BokslUI.getCometListener().addEventListener(PlayTimeRateComet.class, new CometMessageListener() {
						public void processMessage(Serializable message) {
							PlayTimeRateComet object = (PlayTimeRateComet) message;
							positionSlider.setValue((int) (object.getRate() * 100));
						}
					});

					m.getRunningTime();
				}
			}

			/**
			 * @return 재생 처리 콜백
			 */
			private AsyncCallback<PlayArticleModel> playCallback() {
				AsyncCallback<PlayArticleModel> callback = new AsyncCallback<PlayArticleModel>() {
					public void onFailure(Throwable caught) {
						status = Status.STOP;
						caught.printStackTrace();
					}

					public void onSuccess(PlayArticleModel result) {
						status = Status.PLAY;
						playAndPauseBtn.setText("Pause");

						currentPlayArticle = result;
						// 위치 이동 max 값은 현재 음악의 재생 시간(초 단위)
						positionSlider.setMaxValue(currentPlayArticle.getRunningTime());
					}
				};
				return callback;
			}

			/**
			 * @return 일시정지 콜백
			 */
			private AsyncCallback<Void> pauseCallback() {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						status = Status.STOP;
						caught.printStackTrace();
					}

					public void onSuccess(Void result) {
						status = Status.PAUSE;
						playAndPauseBtn.setText("Play");
					}
				};
				return callback;
			}

			private AsyncCallback<Void> resumeCallback() {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						status = Status.STOP;
						caught.printStackTrace();
					}

					public void onSuccess(Void result) {
						status = Status.PLAY;
						playAndPauseBtn.setText("Pause");
					}
				};
				return callback;
			}
		});

		Button stopButton = new Button("Stop");
		stopButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent be) {
				service.stop(stopCallback());
			}

			private AsyncCallback<Void> stopCallback() {
				AsyncCallback<Void> aa = new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						status = Status.STOP;
						caught.printStackTrace();
					}

					public void onSuccess(Void result) {
						status = Status.STOP;
						playAndPauseBtn.setText("Play");
					}
				};
				return aa;
			}
		});

		toolBar.add(playAndPauseBtn);
		toolBar.add(stopButton);

		// toolBar.add(addButton);
		toolBar.add(new SeparatorToolItem());

		volumeSlider.setMessage("Volume:{0}");
		volumeSlider.setIncrement(5);
		volumeSlider.setMaxValue(100);
		volumeSlider.setValue(DEFAULT_VOLUME);
		volumeSlider.setClickToChange(false);

		positionSlider.setIncrement(1);
		positionSlider.setMaxValue(1);
		positionSlider.setClickToChange(false);

		final HorizontalPanel silder = new HorizontalPanel();
		silder.add(volumeSlider);
		silder.add(positionSlider);

		ContentPanel content = new ContentPanel();
		content.setLayout(new RowLayout(Orientation.VERTICAL));

		// content.add(silderPanel, new RowData(1, -1, new Margins(4)));
		content.add(silder, new RowData(1, -1));
		content.add(grid);

		content.setScrollMode(Scroll.AUTOY);
		content.setHeaderVisible(false);
		content.setTopComponent(toolBar);

		// 데이터의 표시 영역이 레이아웃을 벗어 날때 스크롤 생김
		setLayout(new FitLayout());
		setHeading("재생 목록");
		add(content);
	}

	protected void onExpand() {
		super.onExpand();
		// oneRender에 아래 항목을 넣으면 스라이더 항목이 움직이지 않는다.
		// 그래서 onExpand 이벤트에 집어 넣었다.
		volumeSlider.setValue(volumeSlider.getValue(), true);
	}

	/**
	 * @param item
	 *            항목 추가
	 */
	public void addItem(MusicArticleModel item) {
		store.add(item);
	}
}