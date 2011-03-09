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
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
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
		configs.add(column);

		ListStore<Stock> store = new ListStore<Stock>();
		store.add(getStocks());

		cm = new ColumnModel(configs);

		ContentPanel cp = new ContentPanel();
		// cp.setIcon(Resources.ICONS.table());
		cp.setHeaderVisible(false);
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
		cp.add(grid);

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

		stocks.add(new Stock("The Home Depot, Inc.", "HD", 37.66, 37.79));

		stocks.add(new Stock("First Data Corporation", "FDC", 32.7, 32.65));
		return stocks;
	}

}