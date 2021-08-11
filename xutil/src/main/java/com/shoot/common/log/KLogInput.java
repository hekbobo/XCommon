package com.shoot.common.log;

public class KLogInput {
	private KLogBuffer mBuffer;

	public KLogInput(KLogBuffer buffer) {
		mBuffer = buffer;
	}

	/**
	 * 打印debug级别的日记
	 * 
	 * @param tag
	 *            标记
	 * @param text
	 *            日志内容
	 * @throws InterruptedException
	 */
	public void debug(String tag, String text) throws InterruptedException {
		writeToBuffer(KLogDef.LEVEL_DEBUG, tag, text);
	}

	/**
	 * 打印info级别的日记
	 * 
	 * @param tag
	 *            标记
	 * @param text
	 *            日志内容
	 * @throws InterruptedException
	 */
	public void info(String tag, String text) throws InterruptedException {
		writeToBuffer(KLogDef.LEVEL_INFO, tag, text);
	}

	public void error(String tag, String text) throws InterruptedException {
		writeToBuffer(KLogDef.LEVEL_ERROR, tag, text);
	}

	private void writeToBuffer(int level, String tag, String text) throws InterruptedException {
		if (mBuffer == null) {
			return;
		}
		String dateNowStr = KLogOutput.timeFormat(0);
		KLogBean bean = new KLogBean(level, tag, dateNowStr + text);
		mBuffer.put(bean);
	}

	public void enable() {
	}

	public void disable() {
	}

	public void startDebugMode() {
	}

	public void stopDebugMode() {
	}
}
