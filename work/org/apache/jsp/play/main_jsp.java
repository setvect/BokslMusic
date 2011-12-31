package org.apache.jsp.play;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.setvect.bokslmusic.web.play.MusicListController;
import com.setvect.bokslmusic.util.CommonUtil;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \r\n");
      out.write("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<!-- ");
      out.print(request.getRequestURI());
      out.write(" -->\r\n");
      out.write("<title>Boksl Music</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar BASE_URL = \"");
      out.print( CommonUtil.getContextUrl(request));
      out.write("\";\t\r\n");
      out.write("</script>\r\n");
      out.write("<base href=\"");
      out.print( CommonUtil.getContextUrl(request));
      out.write("/\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/jquery-ui-1.8.16.custom.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/play.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"tree/jquery.treeview.css\"/>\r\n");
      out.write("\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/play/common/script.inc.jsp", out, false);
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"tree/jquery.treeview.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/jquery.cookie.js\"></script>\r\n");
      out.write("<script type='text/javascript' src='dwr/engine.js'></script>\r\n");
      out.write("<script type='text/javascript' src='dwr/util.js'></script>\r\n");
      out.write("<script type='text/javascript' src='dwr/interface/musicDwr.js'></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"play/music_control.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("\t.file:hover{\r\n");
      out.write("\t\tcolor: red; \r\n");
      out.write("\t}\r\n");
      out.write("\t.toolbar {\r\n");
      out.write("\t\tpadding: 5px;\r\n");
      out.write("\t\toverflow: hidden;\r\n");
      out.write("\t\tclear: both;\r\n");
      out.write("\t\ttext-align: center;\r\n");
      out.write("\t}\t\r\n");
      out.write("\t.toolbar .ui-button-text{\r\n");
      out.write("\t\tline-height:1;\r\n");
      out.write("\t}\t\r\n");
      out.write("\t.playListRemove .ui-button-text{\r\n");
      out.write("\t\tdisplay: inline;\r\n");
      out.write("\t\tline-height: 1.1;\r\n");
      out.write("\t}\t\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\t\t$( \"#tabs\" ).tabs({\r\n");
      out.write("\t\t\tajaxOptions: {\r\n");
      out.write("\t\t\t\terror: function( xhr, status, index, anchor ) {\r\n");
      out.write("\t\t\t\t\t$( anchor.hash ).html(\r\n");
      out.write("\t\t\t\t\t\t\"Couldn't load this tab. We'll try to fix this as soon as possible. \" +\r\n");
      out.write("\t\t\t\t\t\t\"If this wouldn't be a demo.\" );\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div id=\"wrap\">\r\n");
      out.write("\t<div id=\"tabs\">\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t<li><a href=\"play/music_list.do?mode=");
      out.print(MusicListController.Mode.PLAY_LIST_FORM);
      out.write("\">Play List</a></li>\r\n");
      out.write("\t\t\t<li><a href=\"play/music_list.do?mode=");
      out.print(MusicListController.Mode.ALL_LIST_FORM);
      out.write("\">All List</a></li>\t\t\t\r\n");
      out.write("\t\t\t<li><a href=\"play/music_list.do?mode=");
      out.print(MusicListController.Mode.SETTING_FORM);
      out.write("\">Setting</a></li>\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
