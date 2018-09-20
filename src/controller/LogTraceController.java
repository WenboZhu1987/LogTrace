package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.LogEntity;
import service.LogTraceService;

/**
 * controller of the system
 * @author wenbo
 *
 */
public class LogTraceController {

	/**
	 * read the log data file and print the information required
	 */
	public static void logTrace() {

		final String FILE_PATH = "resource/log-data.json";
		try {
			File file = new File(FILE_PATH);
			LogTraceService logTraceService = new LogTraceService();
			List<String> rawlogs = logTraceService.readLogFile(file);
			
			// sort the log, time complexity is O(n log n)
			rawlogs.sort(String::compareToIgnoreCase);

			List<LogEntity> logs = new ArrayList<LogEntity>();
			LogEntity logEntity;
			for (String log : rawlogs) {
				logEntity = logTraceService.setLog(log);
				logs = logTraceService.nestLog(logs, logEntity);
			}
			logTraceService.printLogTrace(logs, 1);
		} catch (Exception e) {
			System.out.println("Error "+e.getMessage());;
		}
	}

	public static void main(String[] args) {
		logTrace();
	}

}
