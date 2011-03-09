/* 
 * Ext GWT 2.2.1 - Ext for GWT 
 * Copyright(c) 2007-2010, Ext JS, LLC. 
 * licensing@extjs.com 
 *  
 * http://extjs.com/license 
 */
package com.setvect.bokslmusic.ui.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.grid.CellSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class GridExample extends LayoutContainer {

	private ColumnModel cm;

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setLayout(new FlowLayout(10));
		getAriaSupport().setPresentation(true);

		final NumberFormat currency = NumberFormat.getCurrencyFormat();
		final NumberFormat number = NumberFormat.getFormat("0.00");
		final NumberCellRenderer<Grid<Stock>> numberRenderer = new NumberCellRenderer<Grid<Stock>>(currency);

		GridCellRenderer<Stock> change = new GridCellRenderer<Stock>() {
			public String render(Stock model, String property, ColumnData config, int rowIndex, int colIndex,
					ListStore<Stock> store, Grid<Stock> grid) {
				double val = (Double) model.get(property);
				String style = val < 0 ? "red" : GXT.isHighContrastMode ? "#00ff5a" : "green";
				String v = number.format(val);

				return "<span qtitle='" + cm.getColumnById(property).getHeader() + "' qtip='" + v
						+ "' style='font-weight: bold;color:" + style + "'>" + v + "</span>";
			}
		};

		GridCellRenderer<Stock> gridNumber = new GridCellRenderer<Stock>() {
			public String render(Stock model, String property, ColumnData config, int rowIndex, int colIndex,
					ListStore<Stock> store, Grid<Stock> grid) {
				return numberRenderer.render(null, property, model.get(property));
			}
		};

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig();
		column.setId("name");
		column.setHeader("Company");
		column.setWidth(200);
		column.setRowHeader(true);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("symbol");
		column.setHeader("Symbol");
		column.setWidth(100);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("last");
		column.setHeader("Last");
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setWidth(75);
		column.setRenderer(gridNumber);
		configs.add(column);

		column = new ColumnConfig("change", "Change", 100);
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setRenderer(change);
		configs.add(column);

		column = new ColumnConfig("date", "Last Updated", 100);
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setDateTimeFormat(DateTimeFormat.getShortDateFormat());
		configs.add(column);

		ListStore<Stock> store = new ListStore<Stock>();
		store.add(getStocks());

		cm = new ColumnModel(configs);

		ContentPanel cp = new ContentPanel();
		cp.setBodyBorder(true);
		// cp.setIcon(Resources.ICONS.table());
		cp.setHeading("Basic Grid");
		cp.setButtonAlign(HorizontalAlignment.CENTER);
		cp.setLayout(new FitLayout());
		cp.getHeader().setIconAltText("Grid Icon");
		cp.setSize(600, 300);

		final Grid<Stock> grid = new Grid<Stock>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("name");
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.getAriaSupport().setLabelledBy(cp.getHeader().getId() + "-label");
		cp.add(grid);

		ToolBar toolBar = new ToolBar();
		toolBar.getAriaSupport().setLabel("Grid Options");

		toolBar.add(new LabelToolItem("Selection Mode: "));
		final SimpleComboBox<String> type = new SimpleComboBox<String>();
		type.getAriaSupport().setLabelledBy(toolBar.getItem(0).getId());
		type.setTriggerAction(TriggerAction.ALL);
		type.setEditable(false);
		type.setFireChangeEventOnSetValue(true);
		type.setWidth(100);
		type.add("Row");
		type.add("Cell");
		type.setSimpleValue("Row");
		type.addListener(Events.Change, new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent be) {
				boolean cell = type.getSimpleValue().equals("Cell");
				grid.getSelectionModel().deselectAll();
				if (cell) {
					grid.setSelectionModel(new CellSelectionModel<Stock>());
				}
				else {
					grid.setSelectionModel(new GridSelectionModel<Stock>());
				}
			}
		});
		toolBar.add(type);

		cp.setTopComponent(toolBar);

		add(cp);

		// needed to enable quicktips (qtitle for the heading and qtip for the
		// content) that are setup in the change GridCellRenderer
		new QuickTip(grid);
	}

	private List<Stock> getStocks() {
		List<Stock> stocks = new ArrayList<Stock>();

		stocks.add(new Stock("Apple Inc.", "AAPL", 125.64, 123.43));
		stocks.add(new Stock("Cisco Systems, Inc.", "CSCO", 25.84, 26.3));
		stocks.add(new Stock("Google Inc.", "GOOG", 516.2, 512.6));
		stocks.add(new Stock("Intel Corporation", "INTC", 21.36, 21.53));
		stocks.add(new Stock("Level 3 Communications, Inc.", "LVLT", 5.55, 5.54));
		stocks.add(new Stock("Microsoft Corporation", "MSFT", 29.56, 29.72));
		stocks.add(new Stock("Nokia Corporation (ADR)", "NOK", 27.83, 27.93));
		stocks.add(new Stock("Oracle Corporation", "ORCL", 18.73, 18.98));
		stocks.add(new Stock("Starbucks Corporation", "SBUX", 27.33, 27.36));
		stocks.add(new Stock("Yahoo! Inc.", "YHOO", 26.97, 27.29));
		stocks.add(new Stock("Applied Materials, Inc.", "AMAT", 18.4, 18.66));
		stocks.add(new Stock("Comcast Corporation", "CMCSA", 25.9, 26.4));
		stocks.add(new Stock("Sirius Satellite", "SIRI", 2.77, 2.74));

		stocks.add(new Stock("Tellabs, Inc.", "TLAB", 10.64, 10.75));
		stocks.add(new Stock("eBay Inc.", "EBAY", 30.43, 31.21));
		stocks.add(new Stock("Broadcom Corporation", "BRCM", 30.88, 30.48));
		stocks.add(new Stock("CMGI Inc.", "CMGI", 2.14, 2.13));
		stocks.add(new Stock("Amgen, Inc.", "AMGN", 56.22, 57.02));
		stocks.add(new Stock("Limelight Networks", "LLNW", 23, 22.11));
		stocks.add(new Stock("Amazon.com, Inc.", "AMZN", 72.47, 72.23));

		stocks.add(new Stock("E TRADE Financial Corporation", "ETFC", 24.32, 24.58));
		stocks.add(new Stock("AVANIR Pharmaceuticals", "AVNR", 3.7, 3.52));
		stocks.add(new Stock("Gemstar-TV Guide, Inc.", "GMST", 4.41, 4.55));
		stocks.add(new Stock("Akamai Technologies, Inc.", "AKAM", 43.08, 45.32));
		stocks.add(new Stock("Motorola, Inc.", "MOT", 17.74, 17.69));
		stocks.add(new Stock("Advanced Micro Devices, Inc.", "AMD", 13.77, 13.98));
		stocks.add(new Stock("General Electric Company", "GE", 36.8, 36.91));
		stocks.add(new Stock("Texas Instruments Incorporated", "TXN", 35.02, 35.7));
		stocks.add(new Stock("Qwest Communications", "Q", 9.9, 10.03));
		stocks.add(new Stock("Tyco International Ltd.", "TYC", 33.48, 33.26));
		stocks.add(new Stock("Pfizer Inc.", "PFE", 26.21, 26.19));
		stocks.add(new Stock("Time Warner Inc.", "TWX", 20.3, 20.45));
		stocks.add(new Stock("Sprint Nextel Corporation", "S", 21.85, 21.76));
		stocks.add(new Stock("Bank of America Corporation", "BAC", 49.92, 49.73));
		stocks.add(new Stock("Taiwan Semiconductor", "TSM", 10.4, 10.52));
		stocks.add(new Stock("AT&T Inc.", "T", 39.7, 39.66));
		stocks.add(new Stock("United States Steel Corporation", "X", 115.81, 114.62));
		stocks.add(new Stock("Exxon Mobil Corporation", "XOM", 81.77, 81.86));
		stocks.add(new Stock("Valero Energy Corporation", "VLO", 72.46, 72.6));
		stocks.add(new Stock("Micron Technology, Inc.", "MU", 12.02, 12.27));
		stocks.add(new Stock("Verizon Communications Inc.", "VZ", 42.5, 42.61));
		stocks.add(new Stock("Avaya Inc.", "AV", 16.96, 16.96));
		stocks.add(new Stock("The Home Depot, Inc.", "HD", 37.66, 37.79));

		stocks.add(new Stock("First Data Corporation", "FDC", 32.7, 32.65));
		return stocks;
	}

}