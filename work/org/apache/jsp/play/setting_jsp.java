package org.apache.jsp.play;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.setvect.bokslmusic.web.play.MusicListController;
import java.util.List;

public final class setting_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\tfunction loadSyncList(){\r\n");
      out.write("\t\tmusicDwr.getSyncDiretory(function(syncDiretory){\r\n");
      out.write("\t\t\tvar tableObj = $(\"#syncListTable\").get(0);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tvar n = tableObj.rows.length;\r\n");
      out.write("\t\t\tfor(var i=n-1; i>=0; i--){\r\n");
      out.write("\t\t\t\ttableObj.deleteRow(i);\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tfor(var i=0; i<syncDiretory.length; i++){\r\n");
      out.write("\t\t\t\tvar row = tableObj.insertRow(i);\r\n");
      out.write("\t\t\t\tvar cell = row.insertCell(0);\r\n");
      out.write("\t\t\t\tcell.innerHTML= syncDiretory[i].basePath;\r\n");
      out.write("\t\t\t\tcell = row.insertCell(1);\r\n");
      out.write("\t\t\t\tif(syncDiretory[i].syncDate == null){\r\n");
      out.write("\t\t\t\t\tcell.innerHTML= syncDiretory[i].syncDate=\"&nbsp;\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\telse{\r\n");
      out.write("\t\t\t\t\tcell.innerHTML= syncDiretory[i].syncDate.format(\"yyyy-MM-dd HH:mm:ss\");\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t \r\n");
      out.write("\t\t\t\tcell = row.insertCell(2);\r\n");
      out.write("\t\t\t\tcell.innerHTML= \"<button class='deleteDirectoryBtn' value='\"+ syncDiretory[i].basePath +\"'>삭제</button>\";\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$(\"button\").button();\r\n");
      out.write("\t\t\t$(\".deleteDirectoryBtn\").bind(\"click\", function(e){\r\n");
      out.write("\t\t\t\tmusicDwr.removeSyncDirectory(e.delegateTarget.value, function(){\r\n");
      out.write("\t\t\t\t\tloadSyncList();\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$(function () {\r\n");
      out.write("\t\tloadSyncList();\r\n");
      out.write("\t\t$(\"#syncBtn\").bind(\"click\",function(){\r\n");
      out.write("\t\t\tmusicDwr.syncAll(function(result){\r\n");
      out.write("\t\t\t\tif(result){\r\n");
      out.write("\t\t\t\t\talert(\"동기화 수행 종료\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\telse{\r\n");
      out.write("\t\t\t\t\talert(\"동기화 수행중...\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t$(\"#syncRegBtn\").bind(\"click\", function(){\r\n");
      out.write("\t\t\tvar textForm = $(\"#directoryInput\").get(0);\r\n");
      out.write("\t\t\tif($u.FORM.isEmptyRtnMsg(textForm, \"경로를 입력하세요\")){\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tmusicDwr.addSyncDirectory(textForm.value, function(result){\r\n");
      out.write("\t\t\t\tif(result){\r\n");
      out.write("\t\t\t\t\tloadSyncList();\r\n");
      out.write("\t\t\t\t}\t\r\n");
      out.write("\t\t\t\telse{\r\n");
      out.write("\t\t\t\t\talert(\"동기화 디렉토리가 존재 하지 않습니다.\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("<table class=\"playListTable\" >\r\n");
      out.write("\t<thead>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>디렉토리명</th>\r\n");
      out.write("\t\t\t<th>마지막 동기화</th>\r\n");
      out.write("\t\t\t<th>삭제</th>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</thead>\r\n");
      out.write("\t<tbody id=\"syncListTable\">\r\n");
      out.write("\t</tbody>\r\n");
      out.write("</table>\r\n");
      out.write("<div style=\"padding-top: 10px;\">\r\n");
      out.write("\t<button id=\"syncBtn\">동기화하기</button>\r\n");
      out.write("\t<span style=\"padding-left: 20px;\">\r\n");
      out.write("\t\t<label for=\"directoryInput\">디렉토리:</label>\r\n");
      out.write("\t\t<input type=\"text\" value=\"\" id=\"directoryInput\" width=\"300px;\">\r\n");
      out.write("\t\t<button id=\"syncRegBtn\">동기화 디렉토리 추가</button>\r\n");
      out.write("\t</span>\r\n");
      out.write("</div>");
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
