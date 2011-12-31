package org.apache.jsp.play;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.setvect.bokslmusic.web.play.MusicListController;
import java.util.List;

public final class play_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar playerObj = new Object();\r\n");
      out.write("\t// 현재 재생중인 음악 전체 길이(초)\r\n");
      out.write("\tplayerObj.playLength = 0;\r\n");
      out.write("\t\r\n");
      out.write("\t// 볼륨 슬라이더 조정 중 \r\n");
      out.write("\tplayerObj.volumeOver = false;\r\n");
      out.write("\t// 재생 슬라이더 조정 중 \r\n");
      out.write("\tplayerObj.seekOver = false;\r\n");
      out.write("\t\r\n");
      out.write("\t// 폴링 객체 \r\n");
      out.write("\tplayerObj.polling;\r\n");
      out.write("\t\r\n");
      out.write("\t// 현재 재생 중인 음악 하일라이트\r\n");
      out.write("\tplayerObj.tableHighlight = function(idx){\r\n");
      out.write("\t\t$(\"#playListTable2 tr\").css(\"background-color\", \"\");\r\n");
      out.write("\t\tif(idx >=0){\r\n");
      out.write("\t\t\t$(\"#playListTable2 tr\").eq(idx).css(\"background-color\", \"#ffeedd\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t};\r\n");
      out.write("\t\r\n");
      out.write("\t// 현재 플레이어 설정되로 화면 \r\n");
      out.write("\tplayerObj.initViewPage = function(){\r\n");
      out.write("\t\tmusicDwr.getPlayerStat(function(statInfo){\r\n");
      out.write("\t\t\tplayerObj.tableHighlight(statInfo.playIndex);\r\n");
      out.write("\t\t\tif(statInfo.repeat){\r\n");
      out.write("\t\t\t\t$(\"#repeatall\").attr(\"checked\", true);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse{\r\n");
      out.write("\t\t\t\t$(\"#repeat0\").attr(\"checked\", true);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$( \"#repeat\" ).buttonset();\r\n");
      out.write("\t\t\tvar options;\r\n");
      out.write("\t\t\tif(statInfo.playStatus == \"PLAY\"){\t\t\t\r\n");
      out.write("\t\t\t\t$(\"#play\").text(\"pause\");\r\n");
      out.write("\t\t\t\toptions = {\r\n");
      out.write("\t\t\t\t\tlabel: \"pause\",\r\n");
      out.write("\t\t\t\t\ticons: {\r\n");
      out.write("\t\t\t\t\t\tprimary: \"ui-icon-pause\"\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t};\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse{\r\n");
      out.write("\t\t\t\toptions = {\r\n");
      out.write("\t\t\t\t\tlabel: \"play\",\r\n");
      out.write("\t\t\t\t\ticons: {\r\n");
      out.write("\t\t\t\t\t\tprimary: \"ui-icon-play\"\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t};\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$( \"#play\" ).button( \"option\", options );\r\n");
      out.write("\t\t\tif(!playerObj.volumeOver){\r\n");
      out.write("\t\t\t\t$( \"#volumeSlider\" ).slider(\"value\", statInfo.volume);\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t\tif(!playerObj.seekOver){\r\n");
      out.write("\t\t\t\tif(statInfo.playArticle != null){\r\n");
      out.write("\t\t\t\t\tplayerObj.playLength = statInfo.playArticle.runningTime;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t$( \"#seekSlider\" ).slider(\"value\", statInfo.progressRate * 1000);\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t$(function () {\r\n");
      out.write("\t\t// ====== 컨트롤 버튼\r\n");
      out.write("\t\t$( \"#rewind\" ).button({\r\n");
      out.write("\t\t\ttext: false,\r\n");
      out.write("\t\t\ticons: {\r\n");
      out.write("\t\t\t\tprimary: \"ui-icon-seek-prev\"\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t})\r\n");
      out.write("\t\t.click(function() {\r\n");
      out.write("\t\t\tmusicDwr.previous(function(idx){\r\n");
      out.write("\t\t\t\tplayerObj.tableHighlight(idx);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$( \"#play\" ).button({\r\n");
      out.write("\t\t\ttext: false,\r\n");
      out.write("\t\t\ticons: {\r\n");
      out.write("\t\t\t\tprimary: \"ui-icon-play\"\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t})\r\n");
      out.write("\t\t.click(function() {\r\n");
      out.write("\t\t\tvar options;\r\n");
      out.write("\t\t\tif ( $( this ).text() === \"play\" ) {\r\n");
      out.write("\t\t\t\toptions = {\r\n");
      out.write("\t\t\t\t\tlabel: \"pause\",\r\n");
      out.write("\t\t\t\t\ticons: {\r\n");
      out.write("\t\t\t\t\t\tprimary: \"ui-icon-pause\"\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t};\r\n");
      out.write("\t\t\t\tmusicDwr.play(function(idx){\r\n");
      out.write("\t\t\t\t\tplayerObj.tableHighlight(idx);\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\telse {\r\n");
      out.write("\t\t\t\toptions = {\r\n");
      out.write("\t\t\t\t\tlabel: \"play\",\r\n");
      out.write("\t\t\t\t\ticons: {\r\n");
      out.write("\t\t\t\t\t\tprimary: \"ui-icon-play\"\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t};\r\n");
      out.write("\t\t\t\tmusicDwr.pause(function(){\r\n");
      out.write("\t\t\t\t\t// nothing\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$( this ).button( \"option\", options );\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$( \"#stop\" ).button({\r\n");
      out.write("\t\t\ttext: false,\r\n");
      out.write("\t\t\ticons: {\r\n");
      out.write("\t\t\t\tprimary: \"ui-icon-stop\"\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t})\r\n");
      out.write("\t\t.click(function() {\r\n");
      out.write("\t\t\t$( \"#play\" ).button( \"option\", {\r\n");
      out.write("\t\t\t\tlabel: \"play\",\r\n");
      out.write("\t\t\t\ticons: {\r\n");
      out.write("\t\t\t\t\tprimary: \"ui-icon-play\"\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t\tmusicDwr.stop(function(){\r\n");
      out.write("\t\t\t\t// nothing\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$( \"#forward\" ).button({\r\n");
      out.write("\t\t\ttext: false,\r\n");
      out.write("\t\t\ticons: {\r\n");
      out.write("\t\t\t\tprimary: \"ui-icon-seek-next\"\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t})\r\n");
      out.write("\t\t.click(function() {\r\n");
      out.write("\t\t\tmusicDwr.next(function(idx){\r\n");
      out.write("\t\t\t\tplayerObj.tableHighlight(idx);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t$( \"#shuffle\" ).button().click(function(){\r\n");
      out.write("\t\t\tMusicControl.shuffle();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$( \"#repeat\" ).buttonset();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$( \"#repeat0\" ).click(function(){\r\n");
      out.write("\t\t\tmusicDwr.repeat(false);\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t$( \"#repeatall\" ).click(function(){\r\n");
      out.write("\t\t\tmusicDwr.repeat(true);\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t// ====== 일반설정 \t\t\r\n");
      out.write("\t\tMusicControl.playListTableId = \"playListTable2\";\r\n");
      out.write("\t\tMusicControl.playListPrintAfter = playerObj.initViewPage;\r\n");
      out.write("\t\tMusicControl.playListPrint();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t// ====== 스라이더(볼륨, 진행)\r\n");
      out.write("\t\t$( \"#volumeSlider\" ).slider({\r\n");
      out.write("\t\t\trange: \"min\",\r\n");
      out.write("\t\t\tvalue: 0,\r\n");
      out.write("\t\t\tmin: 0,\r\n");
      out.write("\t\t\tmax: 100,\r\n");
      out.write("\t\t\tslide: function( event, ui ) {\r\n");
      out.write("\t\t\t\t$( \"#volume\" ).val( ui.value );\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tchange: function(event, ui) { \r\n");
      out.write("\t\t\t\t$( \"#volume\" ).val( ui.value );\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tstart: function(event, ui) {\r\n");
      out.write("\t\t\t\tplayerObj.volumeOver = true;\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tstop: function(event, ui) { \r\n");
      out.write("\t\t\t\tmusicDwr.setVolume(ui.value, function(){\r\n");
      out.write("\t\t\t\t\tplayerObj.volumeOver = false;\r\n");
      out.write("\t\t\t\t\tplayerObj.initViewPage();\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t$( \"#seekSlider\" ).slider({\r\n");
      out.write("\t\t\trange: \"min\",\r\n");
      out.write("\t\t\tvalue: 0,\r\n");
      out.write("\t\t\tmin: 0,\r\n");
      out.write("\t\t\tmax: 1000,\r\n");
      out.write("\t\t\tstep: 1,\r\n");
      out.write("\t\t\tslide: function( event, ui ) {\r\n");
      out.write("\t\t\t\tvar sec = parseInt(playerObj.playLength * (ui.value / 1000.0));\r\n");
      out.write("\t\t\t\t$( \"#time\" ).val( $u.DATE.toMinSec(sec) );\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tchange: function(event, ui) { \r\n");
      out.write("\t\t\t\tvar sec = parseInt(playerObj.playLength * (ui.value / 1000.0));\r\n");
      out.write("\t\t\t\t$( \"#time\" ).val( $u.DATE.toMinSec(sec) );\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tstart: function(event, ui) {\r\n");
      out.write("\t\t\t\tplayerObj.seekOver = true;\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tstop: function(event, ui) { \r\n");
      out.write("\t\t\t\tmusicDwr.setProgressRate(ui.value / 1000.0, function(){\r\n");
      out.write("\t\t\t\t\tplayerObj.seekOver  = false;\r\n");
      out.write("\t\t\t\t\tplayerObj.initViewPage();\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t// 1초마다 설정 정보 갱신\r\n");
      out.write("\t\tif(playerObj.polling == null){\r\n");
      out.write("\t\t\tplayerObj.polling = setInterval( playerObj.initViewPage, 1000 );\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("<div>\r\n");
      out.write("\t<div class=\"toolbar ui-widget-header ui-corner-all\">\r\n");
      out.write("\t\t<button id=\"rewind\">rewind</button>\r\n");
      out.write("\t\t<button id=\"play\">play</button>\r\n");
      out.write("\t\t<button id=\"stop\">stop</button>\r\n");
      out.write("\t\t<button id=\"forward\">fast forward</button>\r\n");
      out.write("\t\t<input type=\"checkbox\" id=\"shuffle\" /><label for=\"shuffle\">Shuffle</label>\r\n");
      out.write("\t\t<span id=\"repeat\">\r\n");
      out.write("\t\t\t<input type=\"radio\" id=\"repeat0\" name=\"repeat\" checked=\"checked\" /><label for=\"repeat0\">No Repeat</label>\r\n");
      out.write("\t\t\t<input type=\"radio\" id=\"repeatall\" name=\"repeat\"/><label for=\"repeatall\">All</label>\r\n");
      out.write("\t\t</span>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<ul style=\"list-style: none outside; padding-left: 0\">\r\n");
      out.write("\t\t<li>\r\n");
      out.write("\t\t\t<span>\r\n");
      out.write("\t\t\t\t<label for=\"amount\">Volume:</label>\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"volume\" style=\"border:0; color:#f6931f; font-weight:bold;\" />\r\n");
      out.write("\t\t\t</span>\t\t\r\n");
      out.write("\t\t\t<div id=\"volumeSlider\"></div>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li>\r\n");
      out.write("\t\t\t<span>\r\n");
      out.write("\t\t\t\t<label for=\"amount\">Time:</label>\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"time\" style=\"border:0; color:#f6931f; font-weight:bold;\" />\r\n");
      out.write("\t\t\t</span>\t\t\t\t\r\n");
      out.write("\t\t\t<div id=\"seekSlider\"></div>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("\t\r\n");
      out.write("</div>\r\n");
      out.write("<table id=\"playListTable2\" class=\"playListTable\">\r\n");
      out.write("\t<tbody></tbody>\r\n");
      out.write("</table>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
