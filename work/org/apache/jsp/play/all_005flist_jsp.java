package org.apache.jsp.play;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

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
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"tree/jquery.treeview.css\"/>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t$(function () {\r\n");
      out.write("\t\t$(\"#browser\").treeview({\r\n");
      out.write("\t\t\ttoggle: function(i,object) {\r\n");
      out.write("\t\t\t\talert(object.id);\r\n");
      out.write("\t\t\t\t$(object).html(\"<li><span class='folder'>Subfolder 2.1</span></li><li><span class='file'>File 2.2</span></li>\");\r\n");
      out.write("\t\t\t}\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("<ul id=\"browser\" class=\"filetree\">\r\n");
      out.write("\t<li class=\"closed\"><span class=\"folder\">Folder 1</span>\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t<li><span class=\"file\">Item 1.1</span></li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</li>\r\n");
      out.write("\t<li class=\"closed\"><span class=\"folder\">Folder 2</span>\r\n");
      out.write("\t\t<ul id=\"dirs0adf0a32490284a fas fsda fAAA\">\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</li>\r\n");
      out.write("\t<li><span class=\"file\">File 4</span></li>\r\n");
      out.write("</ul>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t");
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
