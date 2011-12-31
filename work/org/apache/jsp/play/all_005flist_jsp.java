package org.apache.jsp.play;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.setvect.bokslmusic.web.play.MusicListController;
import java.util.List;

public final class all_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\t$(function () {\r\n");
      out.write("\t\t$(\"#musicList\").treeview({\r\n");
      out.write("\t\t\tpersist: \"cookie\",\r\n");
      out.write("\t\t\tcookieId: \"treeview-black\",\t\t\t\r\n");
      out.write("\t\t\ttoggle: MusicControl.expandFolder\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$( \"#playlist-dialog\" ).dialog({\r\n");
      out.write("\t\t\tmodal: true,\r\n");
      out.write("\t\t\tautoOpen: false,\r\n");
      out.write("\t\t\tresizable: false,\r\n");
      out.write("\t\t\theight:350,\r\n");
      out.write("\t\t\twidth:350,\r\n");
      out.write("\t\t\tbuttons: {\r\n");
      out.write("\t\t\t\tOk: function() {\r\n");
      out.write("\t\t\t\t\t$( this ).dialog( \"close\" );\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tMusicControl.playListTableId = \"playListTable1\";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"button\").button();\r\n");
      out.write("\t\t$(\"#musicList button\").bind(\"click\", MusicControl.addFolder);\r\n");
      out.write("\t\t$(\"#openPlayListBtn\").bind(\"click\", MusicControl.openPlayList);\r\n");
      out.write("\t\t$(\"#clearPlayListBtn\").bind(\"click\", MusicControl.clearPlayList);\r\n");
      out.write("\t\t$(\"#deduplicationBtn\").bind(\"click\", MusicControl.deduplication);\r\n");
      out.write("\t\t$(\"#shuffleBtn\").bind(\"click\", MusicControl.shuffle);\r\n");
      out.write("\t\t$(\"#musicList .ui-button-text\").css(\"display\", \"inline\").css(\"line-height\", \"1.1\");\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<div class=\"toolbar ui-widget-header ui-corner-all\">\r\n");
      out.write("\t<button id=\"openPlayListBtn\">재생목록</button>\r\n");
      out.write("</div>\t\r\n");
      out.write("<div>\r\n");
      out.write("\t<ul id=\"musicList\" class=\"filetree\">\r\n");

	List<String> folder = (List<String>)request.getAttribute(MusicListController.AttributeKey.FOLDER.name());
	for(String f : folder){

      out.write("\r\n");
      out.write("\t\t<li class=\"closed\">\r\n");
      out.write("\t\t\t<span class=\"folder close\">");
      out.print(f);
      out.write("<button style=\"float: right\" value=\"");
      out.print(f);
      out.write("\">모두추가</button></span>\r\n");
      out.write("\t\t\t<ul title=\"");
      out.print(f);
      out.write("\"></ul>\r\n");
      out.write("\t\t</li>\r\n");

	}

      out.write("\t\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>\r\n");
      out.write("<div id=\"playlist-dialog\" title=\"재생 목록\">\r\n");
      out.write("\t<div class=\"toolbar ui-widget-header ui-corner-all\">\r\n");
      out.write("\t\t<button id=\"clearPlayListBtn\">전체제거</button>\r\n");
      out.write("\t\t<button id=\"deduplicationBtn\">중복제거</button>\r\n");
      out.write("\t\t<button id=\"shuffleBtn\">뒤죽박죽</button>\r\n");
      out.write("\t</div>\t\r\n");
      out.write("\t<table id=\"playListTable1\" class=\"playListTable\">\r\n");
      out.write("\t\t<tbody></tbody>\r\n");
      out.write("\t</table>\r\n");
      out.write("</div>\r\n");
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
