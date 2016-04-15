--受理备注查询
select  convert(varchar(20),a.开始受理时刻,120) AcceptTime,a.呼救电话 CallPhone,m.姓名 dispatcher,a.现场地址 SpotAddress,t.备注 TaskRemark,a.备注 acceptRemark
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码
	where (a.备注<>'' or t.备注<>'') and e.事件性质编码=1 and t.调度员编码='' and a.开始受理时刻 between '' and '' 
--事件类型统计
select 	m.姓名 dispatcher,sum(case when a.电话振铃时刻 is not null then 1 else 0 end) numbersOfPhone,sum(case when t.任务编码 is not null then 1 else 0 end) numbersOfSendCar,
	sum(case when a.类型编码=1 then 1 else 0 end) numbersOfNormalSendCar,sum(case when a.类型编码=2 then 1 else 0 end) numbersOfNormalHangUp,
	sum(case when a.类型编码=3 then 1 else 0 end) numbersOfReinforceSendCar,sum(case when a.类型编码=4 then 1 else 0 end) numbersOfReinforceHangUp,
	sum(case when a.类型编码=5 then 1 else 0 end) numbersOfStopTask,sum(case when a.类型编码=6 then 1 else 0 end) specialEvent,
	sum(case when a.类型编码=7 then 1 else 0 end) noCar,sum(case when a.类型编码=8 then 1 else 0 end) transmitCenter,
	sum(case when a.类型编码=9 then 1 else 0 end) refuseSendCar,sum(case when a.类型编码=10 then 1 else 0 end) wakeSendCar,
	sum(case when t.结果编码=2 then 1 else 0 end) stopTask,'' ratioStopTask,
	sum(case when t.结果编码=3 then 1 else 0 end) emptyCar,'' ratioEmptyCar,
	sum(case when t.结果编码=4 then 1 else 0 end) nomalComplete,'' ratioComplete
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码
	where e.事件性质编码=1 where a.开始受理时刻 between '' and ''
	group by m.姓名
--受理时间统计
select 	m.姓名 dispatcher,(select avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻)) from AuSp120.tb_TeleRecord tr where  m.工号=tr.调度员编码 group by m.工号) averageOffhookTime,avg(datediff(Second, a.开始受理时刻, a.派车时刻)) averageOffSendCar,
	avg(datediff(Second, a.开始受理时刻, a.结束受理时刻)) averageAccept,(select avg(datediff(Second,sl.开始时刻,sl.结束时刻)) from AuSp120.tb_SlinoLog sl where sl.座席状态='就绪' and m.工号=sl.调度员编码 group by m.工号) readyTime,
	(select avg(datediff(Second,sl.开始时刻,sl.结束时刻)) from AuSp120.tb_SlinoLog sl where sl.座席状态='离席' and m.工号=sl.调度员编码 group by m.工号) leaveTime
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码
	where e.事件性质编码=1 
	group by m.姓名,m.工号
--通讯录
select (select 机主姓名 from AuSp120.tb_TeleBook where tb.上级编码=编码) department,tb.机主姓名 ownerName,tb.联系电话 contactPhone,tb.固定电话 fixedPhone,tb.分机 extension,
	tb.移动电话 mobilePhone,tb.小灵通 littleSmart,tb.备注 remark
	from AuSp120.tb_TeleBook tb
	where tb.上级编码<>0 order by tb.上级编码

--急救站出诊情况查询
select 分站编码,count( p.车辆编码) as 暂停次数
into #temp1 
from AuSp120.tb_RecordPauseReason p 
left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码
where p.操作时刻  between '2014-01-01 00:00:00' and '2017-01-01 00:00:00'
group by (分站编码)

select s.分站编码,COUNT(*) as 择院次数 into #temp4 from AuSp120.tb_AcceptDescript a 
	left outer join AuSp120.tb_Station s on a.择院需求编码=s.ID
	where a.择院需求编码 <> 0 and a.开始受理时刻 between '2014-01-01 00:00:00' and '2017-01-01 00:00:00'
	group by s.分站编码
	
select 任务编码,分站编码,结果编码 
into #temp2
from AuSp120.tb_TaskV t left outer join AuSp120.tb_Event e on t.事件编码=e.事件编码
where e.事件性质编码=1 and t.生成任务时刻 between '2014-01-01 00:00:00' and '2017-01-01 00:00:00'

select 分站编码,count(*) as 救治人数
into #temp3 
from AuSp120.tb_PatientCase
where 任务编码 in (select 任务编码 from  #temp2)
group by 分站编码

select s.分站名称 station,sum(case when t2.任务编码 is not null then 1 else 0 end) sendNumbers,
	sum(case when t2.结果编码=4 then 1 else 0 end) nomalNumbers,'' nomalRate,
	sum(case when t2.结果编码=2 then 1 else 0 end) stopNumbers, '' stopRate,
	sum(case when t2.结果编码=3 then 1 else 0 end) emptyNumbers, '' emptyRate,
	sum(case when t2.结果编码=5 then 1 else 0 end) refuseNumbers, '' refuseRate,
	isnull(t1.暂停次数,0) as pauseNumbers,
	isnull(t3.救治人数,0) as treatNumbers,
	isnull(t4.择院次数,0) as choiseHosNumbers
from AuSp120.tb_Station s
left outer join #temp2 t2 on t2.分站编码=s.分站编码
left outer join	#temp1 t1 on t1.分站编码=s.分站编码
left outer join	#temp3 t3 on t3.分站编码=s.分站编码
left outer join #temp4 t4 on t4.分站编码=s.分站编码
group by s.分站名称,t1.暂停次数,t3.救治人数,t4.择院次数,s.显示顺序
order by 显示顺序

drop table #temp1, #temp2,#temp3,#temp4
--重大事故
select 	e.事件编码 eventCode,ac.事发时间 eventTime,e.事件名称 eventName,e.呼救电话 callPhone,m.姓名 dispatcher
	from AuSp120.tb_AccidentEventLink ael
	left outer join AuSp120.tb_Accident ac on ac.事故编码=ael.事故编码
	left outer join AuSp120.tb_Event e on e.事件编码=ael.事件编码
	left outer join AuSp120.tb_MrUser m on e.调度员编码=m.工号
	where e.事件性质编码=1 and ac.事发时间 between '' and ''
--状态变化统计
select  sl.座席号 seatCode,m.姓名 dispatcher,sl.座席状态 seatState,sl.开始时刻 startTime,sl.结束时刻 endTime
	from AuSp120.tb_SlinoLog sl
	left outer join AuSp120.tb_MrUser m on sl.调度员编码=m.工号 
	where sl.开始时刻 between '' and ''
--中心任务信息统计
select  pc.姓名 name,病人主诉 sickDescription,送往地点 toAddress,随车医生 doctor,随车护士 nurse,司机 driver,事件编码,任务编码,家庭住址 sickAddress
	into #temp1
	from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_MrUser on 分站调度员编码=工号 where 人员类型<>1

select  e.呼救电话 phone,convert(varchar(20),e.受理时刻,120) acceptTime,convert(varchar(20),t.生成任务时刻,120) sendCarTime,
	convert(varchar(20),t.出车时刻,120) drivingTime,convert(varchar(20),t.到达现场时刻,120) arrivalTime,convert(varchar(20),t.到达医院时刻,120) returnHospitalTime,
	am.实际标识 carCode,m.姓名 dispatcher,dtr.NameM taskResult,t.任务编码,e.事件编码
	into #temp2
	from  AuSp120.tb_TaskV t left outer join AuSp120.tb_Event e on t.事件编码=e.事件编码
	left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号
	left outer join AuSp120.tb_DTaskResult dtr on t.结果编码=dtr.Code
	left outer join AuSp120.tb_Ambulance am on t.车辆编码=am.车辆编码
	where e.事件性质编码=1  and m.人员类型<>1

select  name,sickAddress,sickDescription,phone,acceptTime,sendCarTime,drivingTime,arrivalTime,returnHospitalTime,toAddress,carCode,doctor,nurse,driver,dispatcher,taskResult
	from #temp1 a left outer join  #temp2 b on a.事件编码=b.事件编码 and a.任务编码=b.任务编码
union 
select  name,sickAddress,sickDescription,phone,acceptTime,sendCarTime,drivingTime,arrivalTime,returnHospitalTime,toAddress,carCode,doctor,nurse,driver,dispatcher,taskResult
	from #temp2 b left outer join #temp1 a on a.事件编码=b.事件编码 and a.任务编码=b.任务编码	
order by acceptTime

drop table #temp1,#temp2

--急救站院前急救工作统计表

select 任务编码,分站编码,结果编码,事故编码 
into #temp2
from AuSp120.tb_TaskV t left outer join AuSp120.tb_Event e on t.事件编码=e.事件编码 
where e.事件性质编码=1 and t.生成任务时刻 between '2015-01-19 00:00:00' and '2015-03-20 00:00:00' 

select 分站编码,count(任务编码) as 救治人数,sum(case when 救治结果编码 in (5,6,9,13) then 1 else 0 end) 救治死亡人数,
sum(case when 分类统计编码=1 then 1 else 0 end) 交通事故外伤,sum(case when 分类统计编码=2 then 1 else 0 end) 其他类外伤,
sum(case when 分类统计编码=3 then 1 else 0 end) 烧伤,sum(case when 分类统计编码=4 then 1 else 0 end) 电击伤溺水,
sum(case when 分类统计编码=5 then 1 else 0 end) 其它外科疾病,sum(case when 分类统计编码=6 then 1 else 0 end) 心血管系统疾病,
sum(case when 分类统计编码=7 then 1 else 0 end) 脑血管系统疾病,sum(case when 分类统计编码=8 then 1 else 0 end) 呼吸道系统疾病,
sum(case when 分类统计编码=9 then 1 else 0 end) 食物中毒,sum(case when 分类统计编码=10 then 1 else 0 end) 药物中毒,
sum(case when 分类统计编码=11 then 1 else 0 end) 酒精中毒,sum(case when 分类统计编码=12 then 1 else 0 end) 一氧化碳中毒,
sum(case when 分类统计编码=13 then 1 else 0 end) 其它内科疾病,sum(case when 分类统计编码=14 then 1 else 0 end) 妇科产科,
sum(case when 分类统计编码=15 then 1 else 0 end) 儿科,sum(case when 分类统计编码=16 then 1 else 0 end) 气管异物,
sum(case when 分类统计编码=17 then 1 else 0 end) 其它五官科,sum(case when 分类统计编码=18 then 1 else 0 end) 传染病,
sum(case when 分类统计编码=19 then 1 else 0 end) 抢救前死亡,sum(case when 分类统计编码=20 then 1 else 0 end) 抢救后死亡,
sum(case when 分类统计编码=21 then 1 else 0 end) 其他
into #temp3 
from AuSp120.tb_PatientCase
where 任务编码 in (select 任务编码 from  #temp2) and 任务编码 is not null
group by 分站编码

select s.分站名称,sum(case when t2.任务编码 is not null then 1 else 0 end) 派诊总数,
	sum(case when t2.结果编码=4 then 1 else 0 end) 正常完成,
	sum(case when t2.结果编码=2 then 1 else 0 end) 中止任务, 
	sum(case when t2.结果编码=3 then 1 else 0 end) 空车,
	sum(case when 事故编码 is not null then 1 else 0 end) 本月重大突发事件,
	isnull(t3.救治死亡人数,0) as 救治死亡人数,	isnull(t3.救治人数,0) as 接诊总人数,
	isnull(t3.交通事故外伤,0) as 交通事故外伤,	isnull(t3.其他类外伤,0) as 其他类外伤,
	isnull(t3.烧伤,0) as 烧伤,	isnull(t3.电击伤溺水,0) as 电击伤溺水,
	isnull(t3.其它外科疾病,0) as 其它外科疾病,	isnull(t3.心血管系统疾病,0) as 心血管系统疾病,
	isnull(t3.脑血管系统疾病,0) as 脑血管系统疾病,	isnull(t3.呼吸道系统疾病,0) as 呼吸道系统疾病,
	isnull(t3.食物中毒,0) as 食物中毒,	isnull(t3.药物中毒,0) as 药物中毒,
	isnull(t3.酒精中毒,0) as 酒精中毒,	isnull(t3.一氧化碳中毒,0) as 一氧化碳中毒,
	isnull(t3.其它内科疾病,0) as 其它内科疾病,	isnull(t3.妇科产科,0) as 妇科产科,
	isnull(t3.儿科,0) as 儿科,	isnull(t3.气管异物,0) as 气管异物,
	isnull(t3.其它五官科,0) as 其它五官科,	isnull(t3.传染病,0) as 传染病,
	isnull(t3.抢救前死亡,0) as 抢救前死亡,	isnull(t3.抢救后死亡,0) as 抢救后死亡,
	isnull(t3.其他,0) as 其他
from AuSp120.tb_Station s
left outer join #temp2 t2 on t2.分站编码=s.分站编码
left outer join	#temp3 t3 on t3.分站编码=s.分站编码
group by s.分站名称,t3.救治死亡人数,t3.救治人数,t3.交通事故外伤,t3.其他类外伤,t3.烧伤,t3.电击伤溺水,t3.其它外科疾病,t3.心血管系统疾病,t3.脑血管系统疾病,t3.呼吸道系统疾病,t3.食物中毒,t3.药物中毒,t3.酒精中毒,t3.一氧化碳中毒,t3.其它内科疾病,
t3.妇科产科,t3.儿科,t3.气管异物,t3.其它五官科,t3.传染病,t3.抢救前死亡,t3.抢救后死亡,t3.其他,显示顺序
order by 显示顺序

drop table #temp2,#temp3
--中心接警统计
select distinct e.事件编码 eventCode,e.分站名称 station,m.姓名 dispatcher into #temp1
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码
	where e.事件性质编码=1 and m.人员类型=0
select a.ID id,convert(varchar(20),a.开始受理时刻,120) answerAlarmTime,a.呼救电话 alarmPhone,a.联系电话 relatedPhone,a.现场地址 siteAddress,
	a.初步判断 judgementOnPhone, station,convert(varchar(20),a.派车时刻,120) sendCarTime, dispatcher
	from #temp1 t 
	left outer join AuSp120.tb_AcceptDescriptV a on t.eventCode=a.事件编码
	where a.开始受理时刻 between '2014-12-01 00:00:00' and '2015-05-01 00:00:00' 
	and a.调度员编码='00103' and a.呼救电话
drop table #temp1

--急救站晚出诊统计	
select a.事件编码 eventCode,a.现场地址 siteAddress,a.开始受理时刻 acceptTime into #temp1
	from AuSp120.tb_AcceptDescriptV a

select t.事件编码 eventCode,det.NameM eventType,am.实际标识 carCode,CONVERT(varchar(20),t.生成任务时刻,120) createTaskTime,
	CONVERT(varchar(20),t.出车时刻,120) outCarTime,DATEDIFF(SECOND,t.生成任务时刻,t.出车时刻) outCarTimes,
	dtr.NameM taskResult,t.备注 remark,m.姓名 dispatcher
	into #temp2
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and t.受理序号=a.受理序号 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码
	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码
	left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码
	left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码
	where e.事件性质编码=1 and m.人员类型=0 and t.出车时刻 is not null 
	and t.生成任务时刻 between '2014-01-19 00:00:00' and '2015-03-20 00:00:00' 
select t1.siteAddress,t2.eventType,t2.carCode,t1.acceptTime,t2.createTaskTime,t2.outCarTime,t2.outCarTimes,t2.taskResult,t2.remark,t2.dispatcher
	from #temp2 t2 left outer join #temp1 t1 on t1.eventCode=t2.eventCode
	
drop table #temp1,#temp2

--车辆工作情况统计
select 分站编码 station,实际标识 carCode,count( p.车辆编码) as pauseNumbers into #temp1 
	from AuSp120.tb_RecordPauseReason p 
	left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码
	where p.操作时刻 between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	group by (分站编码),(实际标识)
	
select t.分站编码 station,am.实际标识 carCode,t.出车时刻 ,t.到达现场时刻,t.生成任务时刻,结果编码 into #temp2
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码
	where e.事件性质编码=1 and t.生成任务时刻 between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
select station,carCode,AVG(DATEDIFF(Second,生成任务时刻,出车时刻)) averageOutCarTimes into #temp3 
	from #temp2 where 生成任务时刻<出车时刻 group by station,carCode
	
select station,carCode,AVG(DATEDIFF(Second,出车时刻,到达现场时刻)) averageArriveSpotTimes into #temp4 
	from #temp2 where 出车时刻<到达现场时刻 and 出车时刻 is not null group by station,carCode
	
select t.station,t.carCode,sum(case when t.出车时刻 is not null then 1 else 0 end) outCarNumbers,
	sum(case when t.到达现场时刻 is not null then 1 else 0 end) arriveSpotNumbers into #temp5
	from #temp2 t group by t.station,t.carCode	
	
select s.分站名称 station,t5.carCode,outCarNumbers,averageOutCarTimes,arriveSpotNumbers,averageArriveSpotTimes,pauseNumbers
	from AuSp120.tb_Station s left outer join #temp1 t1  on t1.station=s.分站编码 
	left outer join #temp5 t5 on t1.station=t5.station and t1.carCode=t5.carCode
	left outer join #temp3 t3  on t3.station=t5.station and t3.carCode=t5.carCode
	left outer join #temp4 t4  on t3.station=t4.station and t3.carCode=t4.carCode
	where t5.carCode is not null
	order by s.显示顺序
	
drop table #temp1,#temp2,#temp3,#temp4,#temp5

--司机工作情况统计
select t.生成任务时刻,t.出车时刻,t.到达现场时刻,结果编码,t.司机,t.分站编码 into #temp1
	from AuSp120.tb_TaskV t
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and t.司机<>'' 
	and t.生成任务时刻 between '2014-01-19 00:00:00' and '2015-03-20 00:00:00' 

select 分站编码,p.司机,count(p.司机) as pauseNumbers into #temp2
	from AuSp120.tb_RecordPauseReason p 
	left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码
	where p.操作时刻 between '2014-01-19 00:00:00' and '2015-03-20 00:00:00' and p.司机<>''
	group by (分站编码),(p.司机)

select t.分站编码,t.司机 driver,SUM(case when t.生成任务时刻 is not null then 1 else 0 end) outCarNumbers,
	SUM(case when t.结果编码=4 then 1 else 0 end) nomalNumbers,
	SUM(case when t.结果编码=3 then 1 else 0 end) emptyNumbers,
	SUM(case when t.结果编码=2 then 1 else 0 end) stopNumbers,
	SUM(case when t.结果编码=5 then 1 else 0 end) refuseNumbers into #temp3
	from #temp1 t group by t.分站编码,t.司机
select t.分站编码,t.司机 driver,AVG(DATEDIFF(Second,t.生成任务时刻,t.出车时刻)) averageOutCarTimes 
	into #temp4
	from #temp1 t where t.生成任务时刻<t.出车时刻 group by t.分站编码,t.司机	
	
select t.分站编码,t.司机 driver,isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达现场时刻)),0) averageArriveSpotTimes 
	into #temp5
	from #temp1 t where t.出车时刻<t.到达现场时刻 and t.出车时刻 is not null group by t.分站编码,t.司机

select s.分站名称 station,t3.driver,outCarNumbers,nomalNumbers,stopNumbers,emptyNumbers,refuseNumbers,
	pauseNumbers,averageOutCarTimes,isnull(averageArriveSpotTimes,0) averageArriveSpotTimes
	from AuSp120.tb_Station s left outer join #temp2 t2 on t2.分站编码=s.分站编码
	left outer join #temp3 t3 on t3.分站编码=t2.分站编码 and t3.driver=t2.司机
	left outer join #temp4 t4 on t3.分站编码=t4.分站编码 and t3.driver=t4.driver
	left outer join #temp5 t5 on t3.分站编码=t5.分站编码 and t3.driver=t5.driver
	where t3.driver<>''
	order by s.显示顺序
	
drop table #temp1,#temp2,#temp3,#temp4,#temp5

--疾病原因统计
select ddr.NameM patientReason,COUNT(*) receivePeopleNumbers,'' rate
	from AuSp120.tb_DDiseaseReason ddr
	left outer join AuSp120.tb_PatientCase pc  on ddr.Code=pc.病因编码
	group by  ddr.NameM 
	
--疾病分类统计
select ddcs.NameM patientClass,COUNT(*) receivePeopleNumbers,'' rate
	from AuSp120.tb_DDiseaseClassState ddcs
	left outer join AuSp120.tb_PatientCase pc  on ddcs.Code=pc.分类统计编码
	where pc.任务时刻  between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	group by  ddcs.NameM 
	
--并发时间段统计
select  DATENAME(HOUR,pc.任务时刻) span,pc.分类统计编码 code into #temp1
	from  AuSp120.tb_PatientCase pc 
	where pc.任务时刻  between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	
select ddcs.NameM patientType,COUNT(*) summary,
	SUM(case when tt.span>=0 and tt.span<1 then 1 else 0 end) span0_1,
	SUM(case when tt.span>=1 and tt.span<2 then 1 else 0 end) span1_2,
	SUM(case when tt.span>=2 and tt.span<3 then 1 else 0 end) span2_3,
	SUM(case when tt.span>=3 and tt.span<4 then 1 else 0 end) span3_4,
	SUM(case when tt.span>=4 and tt.span<5 then 1 else 0 end) span4_5,
	SUM(case when tt.span>=5 and tt.span<6 then 1 else 0 end) span5_6,
	SUM(case when tt.span>=6 and tt.span<7 then 1 else 0 end) span6_7,
	SUM(case when tt.span>=7 and tt.span<8 then 1 else 0 end) span7_8,
	SUM(case when tt.span>=8 and tt.span<9 then 1 else 0 end) span8_9,
	SUM(case when tt.span>=9 and tt.span<10 then 1 else 0 end) span9_10,
	SUM(case when tt.span>=10 and tt.span<11 then 1 else 0 end) span10_11,
	SUM(case when tt.span>=11 and tt.span<12 then 1 else 0 end) span11_12,
	SUM(case when tt.span>=12 and tt.span<13 then 1 else 0 end) span12_13,
	SUM(case when tt.span>=13 and tt.span<14 then 1 else 0 end) span13_14,
	SUM(case when tt.span>=14 and tt.span<15 then 1 else 0 end) span14_15,
	SUM(case when tt.span>=15 and tt.span<16 then 1 else 0 end) span15_16,
	SUM(case when tt.span>=16 and tt.span<17 then 1 else 0 end) span16_17,
	SUM(case when tt.span>=17 and tt.span<18 then 1 else 0 end) span17_18,
	SUM(case when tt.span>=18 and tt.span<19 then 1 else 0 end) span18_19,
	SUM(case when tt.span>=19 and tt.span<20 then 1 else 0 end) span19_20,
	SUM(case when tt.span>=20 and tt.span<21 then 1 else 0 end) span20_21,
	SUM(case when tt.span>=21 and tt.span<22 then 1 else 0 end) span21_22,
	SUM(case when tt.span>=22 and tt.span<23 then 1 else 0 end) span22_23,
	SUM(case when tt.span>=23 and tt.span<24 then 1 else 0 end) span23_24
	from #temp1 tt left outer join AuSp120.tb_DDiseaseClassState ddcs on ddcs.Code=tt.code
	group by ddcs.NameM 

drop table #temp1
--病例回填率统计
select distinct pc.分站编码,pc.任务编码 into #temp1
	from AuSp120.tb_PatientCase pc 
	where pc.分站编码<>''
select s.分站名称 station,COUNT(*) sendNumbers,COUNT(tt.任务编码) fillNumbers,'' rate
	from AuSp120.tb_TaskV t left outer join #temp1 tt on  t.分站编码=tt.分站编码 and t.任务编码=tt.任务编码
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码
	where e.事件性质编码=1 and t.结果编码=4 and t.生成任务时刻 between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	group by s.分站名称,s.显示顺序
	order by s.显示顺序
drop table #temp1
--医生护士工作情况统计 
select  pc.任务编码,pc.随车医生,pc.随车护士,pc.分站编码 into #temp1 
	from AuSp120.tb_PatientCase pc where pc.任务编码<>'' and pc.任务时刻 between '' and ''
	
select distinct pc.任务编码,pc.随车医生,pc.随车护士,pc.分站编码 into #temp2 
	from AuSp120.tb_PatientCase pc where pc.任务编码<>''
	
select tt.分站编码,tt.随车医生,COUNT(*) doctorCureNumbers into #temp3 from #temp1 tt 
	where tt.随车医生<>'' and tt.任务编码<>'' group by tt.分站编码,tt.随车医生
select tt.分站编码,tt.随车护士,COUNT(*) nurseCureNumbers into #temp3 from #temp1 tt 
	where tt.随车护士<>'' and tt.任务编码<>'' group by tt.分站编码,tt.随车护士
	
select t.分站编码 ,tt.随车医生 name,SUM(case when t.结果编码<>5 then 1 else 0 end) outCarNumbers,
	SUM(case when t.结果编码=4 then 1 else 0 end) validOutCarNumbers,
	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) stopNumbers,
	isnull(AVG(DATEDIFF(Second,t.到达现场时刻,t.完成时刻)),0) averateCureTimes into #temp4
	from AuSp120.tb_TaskV t left outer join #temp2 tt on t.任务编码=tt.任务编码
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	where e.事件性质编码=1 and t.结果编码 is not null and tt.随车医生<>'' 
	group by  t.分站编码 ,tt.随车医生
	order by  t.分站编码 ,tt.随车医生
	
select s.分站名称 station,tt4.name,tt4.outCarNumbers,tt4.validOutCarNumbers,tt4.stopNumbers,
	tt3.doctorCureNumbers curePeopleNumbers,tt4.averateCureTimes
	from #temp3 tt3 left outer join #temp4 tt4 on tt3.分站编码=tt4.分站编码 and tt3.随车医生=tt4.name	
	left outer join AuSp120.tb_Station s on s.分站编码=tt4.分站编码
	
drop table #temp1,#temp2,#temp3,#temp4

--开始受理到派车大于X秒
select a.ID id,m.姓名,CONVERT(varchar(20),a.开始受理时刻,120) 开始受理时刻,CONVERT(varchar(20),a.派车时刻,120) 派车时刻,
	 dat.NameM,a.呼救电话,DATEDIFF(SECOND,a.开始受理时刻,a.派车时刻),a.备注
	from AuSp120.tb_Task t 
	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号
	left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码
	where e.事件性质编码=1 and a.类型编码 not in(3,10)  and m.人员类型=0
	order by m.姓名
	
select * from AuSp120.tb_DAcceptDescriptType
--受理时间详情
select det.NameM eventType,den.NameM eventNature,e.呼救电话 callPhone,a.现场地址 callAddress,a.病人需求 patientNeed,
	a.初步判断 preJudgment,dis.NameM sickCondition,a.特殊要求 specialNeeds,a.救治人数 humanNumbers,
	a.患者姓名 sickName,	a.年龄 age,a.性别 gender,di.NameM identitys,a.联系人 contactMan,a.联系电话 contactPhone,
	a.分机 extension,m.姓名 thisDispatcher,	a.备注 remark,a.是否要担架 isOrNoLitter,a.受理序号 acceptNumber,
	convert(varchar(20),a.开始受理时刻,120) acceptStartTime,dat.NameM acceptType,	dhr.NameM toBeSentReason,
	convert(varchar(20),a.结束受理时刻,120) endAcceptTime,convert(varchar(20),a.派车时刻,120) sendCarTime,
	drr.NameM cancelReason,s.分站名称 sendStation,	am.实际标识 carIndentiy,dts.NameM states,convert(varchar(20),
	t.接受命令时刻,120) receiveOrderTime,dtr.NameM taskResult,
	isnull(dsr.NameM,'')+isnull(dtrr.NameM,'')+ISNULL(der.NameM,'') reason,	
	CONVERT(varchar(20),t.出车时刻,120) outCarTime,t.备注 taskRemark,
	convert(varchar(20),t.到达现场时刻,120) arriveSpotTime,
	t.救治人数 takeHumanNumbers,	isnull(a.入院人数,0) toHospitalNumbers,
	convert(varchar(20),t.离开现场时刻,120) leaveSpotTime,ISNULL(a.死亡人数,0) deathNumbers,	
	ISNULL(a.留观人数,0) stayHospitalNumbers,'' backHospitalNumbers,convert(varchar(20),t.完成时刻,120) completeTime,
	m.姓名 stationDispatcher,t.任务序号 outCarNumbers	
	from AuSp120.tb_AcceptDescriptV a 
	left outer  join AuSp120.tb_EventV e on a.事件编码=e.事件编码	
	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	
	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	
	left outer join AuSp120.tb_DEventNature den on den.Code=e.事件性质编码	
	left outer join AuSp120.tb_DILLState dis on dis.Code=a.病情编码	
	left outer join AuSp120.tb_DIdentity di on di.Code=a.身份编码	
	left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码 or m.工号=t.分站调度员编码	
	left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	
	left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	
	left outer join AuSp120.tb_DRepealReason drr on drr.Code=a.撤消原因编码	
	left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码	
	left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	
	left outer join AuSp120.tb_DTaskState dts on dts.Code=t.状态编码	
	left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码	
	left outer join AuSp120.tb_DEmptyReason der on der.Code=t.放空车原因编码	
	left outer join AuSp120.tb_DRefuseReason dtrr on dtrr.Code=t.拒绝出车原因编码	
	left outer join AuSp120.tb_DStopReason dsr on dsr.Code=t.中止任务原因编码	where a.ID=112
--疾病类别统计
select s.分站名称 station,SUM(case when pc.分类统计编码=1 then 1 else 0 end) type1,
	SUM(case when pc.分类统计编码=2 then 1 else 0 end) type2,SUM(case when pc.分类统计编码=3 then 1 else 0 end) type3,
	SUM(case when pc.分类统计编码=4 then 1 else 0 end) type4,SUM(case when pc.分类统计编码=5 then 1 else 0 end) type5,
	SUM(case when pc.分类统计编码=6 then 1 else 0 end) type6,SUM(case when pc.分类统计编码=7 then 1 else 0 end) type7,
	SUM(case when pc.分类统计编码=8 then 1 else 0 end) type8,SUM(case when pc.分类统计编码=9 then 1 else 0 end) type9,
	SUM(case when pc.分类统计编码=10 then 1 else 0 end) type10,SUM(case when pc.分类统计编码=11 then 1 else 0 end) type11,
	SUM(case when pc.分类统计编码=12 then 1 else 0 end) type12,SUM(case when pc.分类统计编码=13 then 1 else 0 end) type13,
	SUM(case when pc.分类统计编码=14 then 1 else 0 end) type14,SUM(case when pc.分类统计编码=15 then 1 else 0 end) type15,
	SUM(case when pc.分类统计编码=16 then 1 else 0 end) type16,SUM(case when pc.分类统计编码=17 then 1 else 0 end) type17,
	SUM(case when pc.分类统计编码=18 then 1 else 0 end) type18,SUM(case when pc.分类统计编码=19 then 1 else 0 end) type19,
	SUM(case when pc.分类统计编码=20 then 1 else 0 end) type20,SUM(case when pc.分类统计编码=21 then 1 else 0 end) type21,
	COUNT(*) total
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.车辆编码=t.车辆编码 and pc.任务编码=t.任务编码
	left outer join AuSp120.tb_DDiseaseClassState ddcs on ddcs.Code=pc.分类统计编码
	left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码	
	where t.生成任务时刻 between '2015-04-04 00:00:00' and '2016-04-04 00:00:00'
	group by s.分站名称
	
	