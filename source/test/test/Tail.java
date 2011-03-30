package test;

// Import the Java classes
import java.io.File;

/**
 * Implements console-based log file tailing, or more specifically, tail
 * following: it is somewhat equivalent to the unix command "tail -f"
 */
public class Tail implements LogFileTailerListener {
	/**
	 * The log file tailer
	 */
	private LogFileTailer tailer;

	/**
	 * Creates a new Tail instance to follow the specified file
	 */
	public Tail(String filename) {
		tailer = new LogFileTailer(new File(filename), 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
	}

	/**
	 * A new line has been added to the tailed log file
	 * 
	 * @param line
	 *            The new line that has been added to the tailed log file
	 */
	public void newLogFileLine(String line) {
		System.out.println(line);
	}

	/**
	 * Command-line launcher
	 */
	public static void main(String[] args) {
		Tail tail = new Tail("D:\\workspace\\BokslMusic\\log\\music_sync.log");
	}
}