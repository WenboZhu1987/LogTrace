package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.LogEntity;

/**
 * Service class for tracing log data
 * @author wenbo
 *
 */
public class LogTraceService {

	/**
	 * 
	 * read the log data file then 
	 * convert and restore each json object into a String list
	 * 
	 * @param file
	 * @return 
	 */
	public List<String> readLogFile(File file) {
		
		StringBuilder sb = new StringBuilder();
		BufferedReader bufr = null;
		List<String> logs = new ArrayList<String>();
		try {
            FileReader fr = new FileReader(file);
			bufr = new BufferedReader(fr);
			String str;
			while ((str = bufr.readLine()) != null) {
				if (str.trim().equals("{")) {
					sb.delete(0, sb.length());
				} else if (str.trim().equals("},") || str.trim().equals("}")) {
					logs.add(sb.toString());
				} else {
					sb.append(str.trim());
				}
			}
			bufr.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException "+e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException "+ e.getMessage());
		}

		return logs;
	}

	/**
	 * complie each log data and set all the properties into a log entity
	 * @param log
	 * @return LogEntity
	 */
	public LogEntity setLog(String log) {

		LogEntity logEntity = new LogEntity();

		Matcher time = Pattern.compile("\"time\": \"([^\"]+)").matcher(log);
		Matcher app = Pattern.compile("\"app\": \"([^\"]+)").matcher(log);
		Matcher msg = Pattern.compile("\"msg\": \"([^\"]+)").matcher(log);
		Matcher component = Pattern.compile("\"component\": \"([^\"]+)")
				.matcher(log);
		Matcher spanId = Pattern.compile("\"span_id\": \"([^\"]+)")
				.matcher(log);
		Matcher parentSpanId = Pattern.compile("\"parent_span_id\": \"([^\"]+)")
				.matcher(log);
		Matcher level = Pattern.compile("\"level\": \"([^\"]+)").matcher(log);
		Matcher env = Pattern.compile("\"env\": \"([^\"]+)").matcher(log);
		Matcher traceId = Pattern.compile("\"trace_id\": \"([^\"]+)").matcher(log);

		logEntity.setTime(time.find() ? time.group(1) : "");
		logEntity.setApp(app.find() ? app.group(1) : "");
		logEntity.setMsg(msg.find() ? msg.group(1) : "");
		logEntity.setComponent(component.find() ? component.group(1) : "");
		logEntity.setSpanId(spanId.find() ? spanId.group(1) : "");
		logEntity.setParentSpanId(parentSpanId.find() ? parentSpanId.group(1) : "");
		logEntity.setLevel(level.find() ? level.group(1) : "");
		logEntity.setEnv(env.find() ? env.group(1) : "");
		logEntity.setTraceId(traceId.find() ? traceId.group(1) : "");
		logEntity.setSubLogEntity(new ArrayList<LogEntity>());

		return logEntity;
	}

	/**
	 * 
	 * nest the child log with parent log
	 * time complexity is  O(n)
	 * @param logs
	 * @param logEntity
	 * @return List<LogEntity>
	 */
	public List<LogEntity> nestLog(List<LogEntity> logs, LogEntity logEntity) {
		if (!logEntity.getParentSpanId().equals("")) {
			for (int i = logs.size() - 1; i >= 0; i--) {
				if (logs.get(i).getSpanId()
						.equals(logEntity.getParentSpanId())) {
					logs.get(i).getSubLogEntity().add(logEntity);
					break;
				} else {
					if (logs.get(i).getSubLogEntity().size() > 0) {
						logs.get(i).setSubLogEntity(
								nestLog(logs.get(i).getSubLogEntity(),
										logEntity));
					}
				}
			}
		} else {
			logs.add(logEntity);
		}
		return logs;
	}

	/**
	 * print the required information with level
	 * time complexity is O(n)
	 * @param logs
	 * @param level - the level of logEntity nest 
	 */
	public void printLogTrace(List<LogEntity> logs, int level) {
		StringBuilder sb = new StringBuilder();

		for (LogEntity logEntity : logs) {
			sb.delete(0, sb.length());
			for (int i = 0; i < level - 1; i++) {
				sb.append("\t");
			}
			sb.append("- ").append(logEntity.getTime() + " ")
					.append(logEntity.getApp() + " ")
					.append(logEntity.getComponent() + " ")
					.append(logEntity.getMsg());

			System.out.println(sb.toString());

			if (logEntity.getSubLogEntity().size() > 0)
				printLogTrace(logEntity.getSubLogEntity(), level + 1);
		}
	}

}
