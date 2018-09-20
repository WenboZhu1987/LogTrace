package model;

import java.util.List;

/**
 * 
 * @Description the entity of log 
 * @author wenbo
 *
 */
public class LogEntity implements java.io.Serializable {

	private String time;
	private String app;
	private String msg;
	private String spanId;
	private String parentSpanId;
	private String component;
	private String level;
	private String env;
	private String traceId;
	private List<LogEntity> subLogEntity;

	@Override
	public String toString() {
		return "[time=" + time + ", app=" + app + ", msg=" + msg + ", spanId="
				+ spanId + ", parentSpanId=" + parentSpanId + ", component="
				+ component + ", level=" + level + ", env=" + env
				+ ", traceId=" + traceId + ", subLogEntity=" + subLogEntity
				+ "]";
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getParentSpanId() {
		return parentSpanId;
	}

	public void setParentSpanId(String parentSpanId) {
		this.parentSpanId = parentSpanId;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public List<LogEntity> getSubLogEntity() {
		return subLogEntity;
	}

	public void setSubLogEntity(List<LogEntity> subLogEntity) {
		this.subLogEntity = subLogEntity;
	}
}