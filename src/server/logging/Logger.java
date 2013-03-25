package server.logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Logger {
	private static Map<String, Logger> loggers = new HashMap<>();
	
	public static Logger getLogger(String name) {
		Logger logger = loggers.get(name);
		if (logger == null) {
			logger = new Logger();
			loggers.put(name, logger);
			if (name.toLowerCase().contains("error")) {
				logger.setPrintStream(System.err);
			} else {
				logger.setPrintStream(System.out);
			}
		}
		return logger;
	}
	
	public PrintStream getPrintStream() {
		return printStream;
	}

	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	
	public void log(String message) {
		Date date = new Date();
		String time = format.format(date.getTime());
		printStream.println("[" + time + "] " + message);
	}

	private PrintStream printStream;
	
}
