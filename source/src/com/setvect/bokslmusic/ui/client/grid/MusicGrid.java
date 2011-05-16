package com.setvect.bokslmusic.ui.client.grid;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Element;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;

public class MusicGrid extends LayoutContainer {

	private GroupingView view;
	private int gridHeight = 200;
	private String checkedStyle = "x-grid3-group-check";
	private String uncheckedStyle = "x-grid3-group-uncheck";
	private GroupingStore<MusicArticleModel> store = new GroupingStore<MusicArticleModel>();

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		store.setMonitorChanges(true);
		store.add(getCompanies());
		store.groupBy("path");

		final CheckBoxSelectionModel<MusicArticleModel> sm = new CheckBoxSelectionModel<MusicArticleModel>() {
			@Override
			public void deselectAll() {
				super.deselectAll();
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i).getFirstChildElement();
					setGroupChecked((Element) group, false);
				}
			}

			@Override
			public void selectAll() {
				super.selectAll();
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i).getFirstChildElement();
					setGroupChecked((Element) group, true);
				}
			}

			@Override
			protected void doDeselect(List<MusicArticleModel> models, boolean supressEvent) {
				super.doDeselect(models, supressEvent);
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				search: for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i);
					NodeList<Element> rows = El.fly(group).select(".x-grid3-row");
					for (int j = 0, len = rows.getLength(); j < len; j++) {
						Element r = rows.getItem(j);
						int idx = grid.getView().findRowIndex(r);
						MusicArticleModel m = grid.getStore().getAt(idx);
						if (!isSelected(m)) {
							setGroupChecked((Element) group, false);
							continue search;
						}
					}
				}

			}

			@Override
			protected void doSelect(List<MusicArticleModel> models, boolean keepExisting, boolean supressEvent) {
				super.doSelect(models, keepExisting, supressEvent);
				NodeList<com.google.gwt.dom.client.Element> groups = view.getGroups();
				search: for (int i = 0; i < groups.getLength(); i++) {
					com.google.gwt.dom.client.Element group = groups.getItem(i);
					NodeList<Element> rows = El.fly(group).select(".x-grid3-row");
					for (int j = 0, len = rows.getLength(); j < len; j++) {
						Element r = rows.getItem(j);
						int idx = grid.getView().findRowIndex(r);
						MusicArticleModel m = grid.getStore().getAt(idx);
						if (!isSelected(m)) {
							continue search;
						}
					}
					setGroupChecked((Element) group, true);

				}
			}
		};

		ColumnConfig company = new ColumnConfig("name", "이름", 200);
		ColumnConfig price = new ColumnConfig("runningTime", "시간", 100);
		price.setRenderer(ClientUtil.TIME_RENDERER);
		ColumnConfig path = new ColumnConfig("path", "경로", 0);

		List<ColumnConfig> config = new ArrayList<ColumnConfig>();

		config.add(company);
		config.add(price);
		config.add(path);
		config.add(sm.getColumn());

		final ColumnModel cm = new ColumnModel(config);
		view = new GroupingView() {
			@Override
			protected void onMouseDown(GridEvent<ModelData> ge) {
				El hd = ge.getTarget(".x-grid-group-hd", 10);
				El target = ge.getTargetEl();
				if (hd != null && target.hasStyleName(uncheckedStyle) || target.hasStyleName(checkedStyle)) {
					boolean checked = !ge.getTargetEl().hasStyleName(uncheckedStyle);
					checked = !checked;
					if (checked) {
						ge.getTargetEl().replaceStyleName(uncheckedStyle, checkedStyle);
					}
					else {
						ge.getTargetEl().replaceStyleName(checkedStyle, uncheckedStyle);
					}

					Element group = (Element) findGroup(ge.getTarget());
					if (group != null) {
						NodeList<Element> rows = El.fly(group).select(".x-grid3-row");
						List<ModelData> temp = new ArrayList<ModelData>();
						for (int i = 0; i < rows.getLength(); i++) {
							Element r = rows.getItem(i);
							int idx = findRowIndex(r);
							ModelData m = grid.getStore().getAt(idx);
							temp.add(m);
						}
						if (checked) {
							grid.getSelectionModel().select(temp, true);
						}
						else {
							grid.getSelectionModel().deselect(temp);
						}
					}
					return;
				}
				super.onMouseDown(ge);
			}

		};
		view.setShowGroupedColumn(false);
		view.setForceFit(true);
		view.setGroupRenderer(new GridGroupRenderer() {
			public String render(GroupColumnData data) {
				String f = cm.getColumnById(data.field).getHeader();
				String l = data.models.size() == 1 ? "Item" : "Items";
				return "<div class='x-grid3-group-checker'><div class='" + uncheckedStyle + "'> </div></div> " + f
						+ ": " + data.group + " (" + data.models.size() + " " + l + ")";
			}
		});

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.setLayout(new FitLayout());
		cp.setHeight(gridHeight);

		Grid<MusicArticleModel> grid = new Grid<MusicArticleModel>(store, cm);
		grid.setView(view);
		grid.setBorders(true);
		grid.addPlugin(sm);
		grid.setSelectionModel(sm);

		cp.add(grid);
		add(cp);
	}

	/**
	 * @return 그리드 높이
	 */
	public int getGridHeight() {
		return gridHeight;
	}

	/**
	 * @param gridHeight
	 *            그리드 높이
	 */
	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	private El findCheck(Element group) {
		return El.fly(group).selectNode(".x-grid3-group-checker").firstChild();
	}

	private void setGroupChecked(Element group, boolean checked) {
		findCheck(group).replaceStyleName(checked ? uncheckedStyle : checkedStyle,
				checked ? checkedStyle : uncheckedStyle);
	}

	public static List<MusicArticleModel> getCompanies() {
		List<MusicArticleModel> stocks = new ArrayList<MusicArticleModel>();
		stocks.add(new MusicArticleModel("3m Co", 1320, "Manufacturing"));
		stocks.add(new MusicArticleModel("Alcoa Inc", 120, "Manufacturing"));
		stocks.add(new MusicArticleModel("Altria Group Inc", 210, "Manufacturing"));
		stocks.add(new MusicArticleModel("American Express Company", 101, "Finance"));
		stocks.add(new MusicArticleModel("American International Group, Inc.", 120, "Services"));
		stocks.add(new MusicArticleModel("AT&T Inc.", 1021, "Services"));
		stocks.add(new MusicArticleModel("Intel Corporation", 210, "Computer"));
		stocks.add(new MusicArticleModel("International Business Machines", 410, "Computer"));
		return stocks;
	}
}