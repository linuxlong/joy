package com.kvc.joy.plugin.seqgen.support;

import com.kvc.joy.commons.lang.DateTool;
import com.kvc.joy.core.persistence.orm.jpa.JpaTool;
import com.kvc.joy.core.spring.utils.SpringBeanTool;
import com.kvc.joy.plugin.seqgen.model.po.TSysSeqNum;
import com.kvc.joy.plugin.seqgen.model.po.TSysSeqNumRule;
import com.kvc.joy.plugin.seqgen.model.po.TSysSeqNumStat;
import com.kvc.joy.plugin.seqgen.service.ISequenceGenerator;

/**
 * 统计序列号
 * 
 * @author <b>Kevice</b>
 */
public class SeqNumStat {
	
//	private Logger logger = Logger.getLogger(getClass());
	
	public void stat(SequenceCache seqCache) {
		TSysSeqNum seqNum = seqCache.getSeqNum();
		TSysSeqNumRule seqRule = seqNum.getSeqRule();
		long numCount = (seqCache.getRealSeqNum() - seqRule.getStartValue()) / seqRule.getIncrement() + 1;
		if (numCount > 0) {
			TSysSeqNumStat statSeqNum = new TSysSeqNumStat();
			statSeqNum.setNumId(seqNum.getId());
			statSeqNum.setPeriodType(seqRule.getPeriodType());
			statSeqNum.setPeriodCount(seqRule.getPeriodCount());
			statSeqNum.setMaxNum(seqCache.getRealSeqNum());
			ISequenceGenerator sequenceGenerator = (ISequenceGenerator) SpringBeanTool.getBean("sequenceGenerator");
			statSeqNum.setMaxFullNum(sequenceGenerator.fillPattern(seqCache.getRealSeqNum(), seqNum));
			statSeqNum.setNumCount(numCount);
			statSeqNum.setPeriodStartTime(seqNum.getCurPeriodStartTime());
			statSeqNum.setStatTime(DateTool.currentDate(DateTool.UNFMT_yyyyMMddHHmmss));
			
			save(statSeqNum);
		}
	}
	
	private void save(TSysSeqNumStat statSeqNum) {
		JpaTool.persist(statSeqNum);
	}

}
