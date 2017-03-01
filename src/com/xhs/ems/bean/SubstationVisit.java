package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午10:36:10
 * @category 急救站出诊情况统计
 */
public class SubstationVisit {
	/**
	 * 分站名称
	 */
	private String station;
	/**
	 * 120派诊数
	 */
	private String sendNumbers;
	/**
	 * 正常完成
	 */
	private String nomalNumbers;
	/**
	 * 正常完成比率
	 */
	private String nomalRate;
	/**
	 * 中止任务
	 */
	private String stopNumbers;
	/**
	 * 中止任务比率
	 */
	private String stopRate;
	/**
	 * 空车
	 */
	private String emptyNumbers;
	/**
	 * 空车比率
	 */
	private String emptyRate;
	/**
	 * 拒绝出车
	 */
	private String refuseNumbers;
	/**
	 * 拒绝出车比率
	 */
	private String refuseRate;
	/**
	 * 暂停调用
	 */
	private String pauseNumbers;
	/**
	 * 救治人数--合计
	 */
	private String treatNumbers;
	/**
	 * 病例结果 未治人数
	 */
	private String noTreat;
	/**
	 * 救后死亡(现场)
	 */
	private String afterDeathSpot;
	/**
	 * 有效
	 */
	private String effect;
	/**
	 * 无变化
	 */
	private String noChange;
	/**
	 * 稳定
	 */
	private String stable;
	/**
	 * 救后死亡(途中)
	 */
	private String afterDeathRoad;
	/**
	 * 救后死亡(医院)
	 */
	private String afterDeathHopital;
	/**
	 * 择院次数
	 */
	private String choiseHosNumbers;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSendNumbers() {
		return sendNumbers;
	}

	public void setSendNumbers(String sendNumbers) {
		this.sendNumbers = sendNumbers;
	}

	public String getNomalNumbers() {
		return nomalNumbers;
	}

	public void setNomalNumbers(String nomalNumbers) {
		this.nomalNumbers = nomalNumbers;
	}

	public String getStopNumbers() {
		return stopNumbers;
	}

	public void setStopNumbers(String stopNumbers) {
		this.stopNumbers = stopNumbers;
	}

	public String getStopRate() {
		return stopRate;
	}

	public void setStopRate(String stopRate) {
		this.stopRate = stopRate;
	}

	public String getEmptyNumbers() {
		return emptyNumbers;
	}

	public void setEmptyNumbers(String emptyNumbers) {
		this.emptyNumbers = emptyNumbers;
	}

	public String getEmptyRate() {
		return emptyRate;
	}

	public void setEmptyRate(String emptyRate) {
		this.emptyRate = emptyRate;
	}

	public String getRefuseNumbers() {
		return refuseNumbers;
	}

	public void setRefuseNumbers(String refuseNumbers) {
		this.refuseNumbers = refuseNumbers;
	}

	public String getRefuseRate() {
		return refuseRate;
	}

	public void setRefuseRate(String refuseRate) {
		this.refuseRate = refuseRate;
	}

	public String getPauseNumbers() {
		return pauseNumbers;
	}

	public void setPauseNumbers(String pauseNumbers) {
		this.pauseNumbers = pauseNumbers;
	}

	public String getTreatNumbers() {
		return treatNumbers;
	}

	public void setTreatNumbers(String treatNumbers) {
		this.treatNumbers = treatNumbers;
	}

	public String getNomalRate() {
		return nomalRate;
	}

	public void setNomalRate(String nomalRate) {
		this.nomalRate = nomalRate;
	}

	public String getChoiseHosNumbers() {
		return choiseHosNumbers;
	}

	public void setChoiseHosNumbers(String choiseHosNumbers) {
		this.choiseHosNumbers = choiseHosNumbers;
	}
	

	public String getNoTreat() {
		return noTreat;
	}

	public void setNoTreat(String noTreat) {
		this.noTreat = noTreat;
	}

	public String getAfterDeathSpot() {
		return afterDeathSpot;
	}

	public void setAfterDeathSpot(String afterDeathSpot) {
		this.afterDeathSpot = afterDeathSpot;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getNoChange() {
		return noChange;
	}

	public void setNoChange(String noChange) {
		this.noChange = noChange;
	}

	public String getStable() {
		return stable;
	}

	public void setStable(String stable) {
		this.stable = stable;
	}

	public String getAfterDeathRoad() {
		return afterDeathRoad;
	}

	public void setAfterDeathRoad(String afterDeathRoad) {
		this.afterDeathRoad = afterDeathRoad;
	}

	public String getAfterDeathHopital() {
		return afterDeathHopital;
	}

	public void setAfterDeathHopital(String afterDeathHopital) {
		this.afterDeathHopital = afterDeathHopital;
	}

	/**
	 * @param station
	 * @param sendNumbers
	 * @param nomalNumbers
	 * @param nomalRate
	 * @param stopNumbers
	 * @param stopRate
	 * @param emptyNumbers
	 * @param emptyRate
	 * @param refuseNumbers
	 * @param refuseRate
	 * @param pauseNumbers
	 * @param treatNumbers
	 * @param choiseHosNumbers
	 */
	public SubstationVisit(String station, String sendNumbers,
			String nomalNumbers, String nomalRate, String stopNumbers,
			String stopRate, String emptyNumbers, String emptyRate,
			String refuseNumbers, String refuseRate, String pauseNumbers,
			String treatNumbers, String choiseHosNumbers) {
		this.station = station;
		this.sendNumbers = sendNumbers;
		this.nomalNumbers = nomalNumbers;
		this.nomalRate = nomalRate;
		this.stopNumbers = stopNumbers;
		this.stopRate = stopRate;
		this.emptyNumbers = emptyNumbers;
		this.emptyRate = emptyRate;
		this.refuseNumbers = refuseNumbers;
		this.refuseRate = refuseRate;
		this.pauseNumbers = pauseNumbers;
		this.treatNumbers = treatNumbers;
		this.choiseHosNumbers = choiseHosNumbers;
	}
	

	public SubstationVisit(String station, String sendNumbers, String nomalNumbers, String nomalRate,
			String stopNumbers, String stopRate, String emptyNumbers, String emptyRate, String refuseNumbers,
			String refuseRate, String pauseNumbers, String treatNumbers, String noTreat, String afterDeathSpot,
			String effect, String noChange, String stable, String afterDeathRoad, String afterDeathHopital,
			String choiseHosNumbers) {
		this.station = station;
		this.sendNumbers = sendNumbers;
		this.nomalNumbers = nomalNumbers;
		this.nomalRate = nomalRate;
		this.stopNumbers = stopNumbers;
		this.stopRate = stopRate;
		this.emptyNumbers = emptyNumbers;
		this.emptyRate = emptyRate;
		this.refuseNumbers = refuseNumbers;
		this.refuseRate = refuseRate;
		this.pauseNumbers = pauseNumbers;
		this.treatNumbers = treatNumbers;
		this.noTreat = noTreat;
		this.afterDeathSpot = afterDeathSpot;
		this.effect = effect;
		this.noChange = noChange;
		this.stable = stable;
		this.afterDeathRoad = afterDeathRoad;
		this.afterDeathHopital = afterDeathHopital;
		this.choiseHosNumbers = choiseHosNumbers;
	}

	public SubstationVisit() {
		
	}
	


}
