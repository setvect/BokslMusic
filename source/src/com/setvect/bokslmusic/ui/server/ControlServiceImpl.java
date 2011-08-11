package com.setvect.bokslmusic.ui.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zschech.gwt.comet.server.CometServlet;
import net.zschech.gwt.comet.server.CometSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.setvect.bokslmusic.ui.client.service.ControlService;

@Service("ControlService")
public class ControlServiceImpl implements ControlService {
	public String echo(String message) {

		// Get or create the HTTP session for the browser
		ServletRequestAttributes sra = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
		HttpServletRequest threadLocalRequest = sra.getRequest();
		HttpSession httpSession = threadLocalRequest.getSession();
		final CometSession cometSession = CometServlet.getCometSession(httpSession);
		final String m = message;

		Thread t = new Thread() {
			public void run() {
				for (int i = 0; i < 3; i++) {
					cometSession.enqueue(m);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();

		return message;
	}
}