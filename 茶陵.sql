--����ע��ѯ
select  convert(varchar(20),a.��ʼ����ʱ��,120) AcceptTime,a.���ȵ绰 CallPhone,m.���� dispatcher,a.�ֳ���ַ SpotAddress,t.��ע TaskRemark,a.��ע acceptRemark
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_Event e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_MrUser m on m.����=t.����Ա����
	where (a.��ע<>'' or t.��ע<>'') and e.�¼����ʱ���=1 and t.����Ա����='' and a.��ʼ����ʱ�� between '' and '' 
--�¼�����ͳ��
select 	m.���� dispatcher,sum(case when a.�绰����ʱ�� is not null then 1 else 0 end) numbersOfPhone,sum(case when t.������� is not null then 1 else 0 end) numbersOfSendCar,
	sum(case when a.���ͱ���=1 then 1 else 0 end) numbersOfNormalSendCar,sum(case when a.���ͱ���=2 then 1 else 0 end) numbersOfNormalHangUp,
	sum(case when a.���ͱ���=3 then 1 else 0 end) numbersOfReinforceSendCar,sum(case when a.���ͱ���=4 then 1 else 0 end) numbersOfReinforceHangUp,
	sum(case when a.���ͱ���=5 then 1 else 0 end) numbersOfStopTask,sum(case when a.���ͱ���=6 then 1 else 0 end) specialEvent,
	sum(case when a.���ͱ���=7 then 1 else 0 end) noCar,sum(case when a.���ͱ���=8 then 1 else 0 end) transmitCenter,
	sum(case when a.���ͱ���=9 then 1 else 0 end) refuseSendCar,sum(case when a.���ͱ���=10 then 1 else 0 end) wakeSendCar,
	sum(case when t.�������=2 then 1 else 0 end) stopTask,'' ratioStopTask,
	sum(case when t.�������=3 then 1 else 0 end) emptyCar,'' ratioEmptyCar,
	sum(case when t.�������=4 then 1 else 0 end) nomalComplete,'' ratioComplete
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_Event e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_MrUser m on m.����=t.����Ա����
	where e.�¼����ʱ���=1 where a.��ʼ����ʱ�� between '' and ''
	group by m.����
--����ʱ��ͳ��
select 	m.���� dispatcher,(select avg(datediff(Second,tr.����ʱ��,tr.ͨ����ʼʱ��)) from AuSp120.tb_TeleRecord tr where  m.����=tr.����Ա���� group by m.����) averageOffhookTime,avg(datediff(Second, a.��ʼ����ʱ��, a.�ɳ�ʱ��)) averageOffSendCar,
	avg(datediff(Second, a.��ʼ����ʱ��, a.��������ʱ��)) averageAccept,(select avg(datediff(Second,sl.��ʼʱ��,sl.����ʱ��)) from AuSp120.tb_SlinoLog sl where sl.��ϯ״̬='����' and m.����=sl.����Ա���� group by m.����) readyTime,
	(select avg(datediff(Second,sl.��ʼʱ��,sl.����ʱ��)) from AuSp120.tb_SlinoLog sl where sl.��ϯ״̬='��ϯ' and m.����=sl.����Ա���� group by m.����) leaveTime
	from  AuSp120.tb_AcceptDescriptV a
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_Event e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_MrUser m on m.����=t.����Ա����
	where e.�¼����ʱ���=1 
	group by m.����,m.����
--ͨѶ¼
select (select �������� from AuSp120.tb_TeleBook where tb.�ϼ�����=����) department,tb.�������� ownerName,tb.��ϵ�绰 contactPhone,tb.�̶��绰 fixedPhone,tb.�ֻ� extension,
	tb.�ƶ��绰 mobilePhone,tb.С��ͨ littleSmart,tb.��ע remark
	from AuSp120.tb_TeleBook tb
	where tb.�ϼ�����<>0 order by tb.�ϼ�����

--����վ���������ѯ
select ��վ����,count( p.��������) as ��ͣ����
into #temp1 
from AuSp120.tb_RecordPauseReason p 
left join AuSp120.tb_Ambulance a on p.��������=a.��������
where p.����ʱ��  between '2014-01-01 00:00:00' and '2017-01-01 00:00:00'
group by (��վ����)

select s.��վ����,COUNT(*) as ��Ժ���� into #temp4 from AuSp120.tb_AcceptDescript a 
	left outer join AuSp120.tb_Station s on a.��Ժ�������=s.ID
	where a.��Ժ������� <> 0 and a.��ʼ����ʱ�� between '2014-01-01 00:00:00' and '2017-01-01 00:00:00'
	group by s.��վ����
	
select �������,��վ����,������� 
into #temp2
from AuSp120.tb_TaskV t left outer join AuSp120.tb_Event e on t.�¼�����=e.�¼�����
where e.�¼����ʱ���=1 and t.��������ʱ�� between '2014-01-01 00:00:00' and '2017-01-01 00:00:00'

select ��վ����,count(*) as ��������
into #temp3 
from AuSp120.tb_PatientCase
where ������� in (select ������� from  #temp2)
group by ��վ����

select s.��վ���� station,sum(case when t2.������� is not null then 1 else 0 end) sendNumbers,
	sum(case when t2.�������=4 then 1 else 0 end) nomalNumbers,'' nomalRate,
	sum(case when t2.�������=2 then 1 else 0 end) stopNumbers, '' stopRate,
	sum(case when t2.�������=3 then 1 else 0 end) emptyNumbers, '' emptyRate,
	sum(case when t2.�������=5 then 1 else 0 end) refuseNumbers, '' refuseRate,
	isnull(t1.��ͣ����,0) as pauseNumbers,
	isnull(t3.��������,0) as treatNumbers,
	isnull(t4.��Ժ����,0) as choiseHosNumbers
from AuSp120.tb_Station s
left outer join #temp2 t2 on t2.��վ����=s.��վ����
left outer join	#temp1 t1 on t1.��վ����=s.��վ����
left outer join	#temp3 t3 on t3.��վ����=s.��վ����
left outer join #temp4 t4 on t4.��վ����=s.��վ����
group by s.��վ����,t1.��ͣ����,t3.��������,t4.��Ժ����,s.��ʾ˳��
order by ��ʾ˳��

drop table #temp1, #temp2,#temp3,#temp4
--�ش��¹�
select 	e.�¼����� eventCode,ac.�·�ʱ�� eventTime,e.�¼����� eventName,e.���ȵ绰 callPhone,m.���� dispatcher
	from AuSp120.tb_AccidentEventLink ael
	left outer join AuSp120.tb_Accident ac on ac.�¹ʱ���=ael.�¹ʱ���
	left outer join AuSp120.tb_Event e on e.�¼�����=ael.�¼�����
	left outer join AuSp120.tb_MrUser m on e.����Ա����=m.����
	where e.�¼����ʱ���=1 and ac.�·�ʱ�� between '' and ''
--״̬�仯ͳ��
select  sl.��ϯ�� seatCode,m.���� dispatcher,sl.��ϯ״̬ seatState,sl.��ʼʱ�� startTime,sl.����ʱ�� endTime
	from AuSp120.tb_SlinoLog sl
	left outer join AuSp120.tb_MrUser m on sl.����Ա����=m.���� 
	where sl.��ʼʱ�� between '' and ''
--����������Ϣͳ��
select  pc.���� name,�������� sickDescription,�����ص� toAddress,�泵ҽ�� doctor,�泵��ʿ nurse,˾�� driver,�¼�����,�������,��ͥסַ sickAddress
	into #temp1
	from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_MrUser on ��վ����Ա����=���� where ��Ա����<>1

select  e.���ȵ绰 phone,convert(varchar(20),e.����ʱ��,120) acceptTime,convert(varchar(20),t.��������ʱ��,120) sendCarTime,
	convert(varchar(20),t.����ʱ��,120) drivingTime,convert(varchar(20),t.�����ֳ�ʱ��,120) arrivalTime,convert(varchar(20),t.����ҽԺʱ��,120) returnHospitalTime,
	am.ʵ�ʱ�ʶ carCode,m.���� dispatcher,dtr.NameM taskResult,t.�������,e.�¼�����
	into #temp2
	from  AuSp120.tb_TaskV t left outer join AuSp120.tb_Event e on t.�¼�����=e.�¼�����
	left outer join AuSp120.tb_MrUser m on t.����Ա����=m.����
	left outer join AuSp120.tb_DTaskResult dtr on t.�������=dtr.Code
	left outer join AuSp120.tb_Ambulance am on t.��������=am.��������
	where e.�¼����ʱ���=1  and m.��Ա����<>1

select  name,sickAddress,sickDescription,phone,acceptTime,sendCarTime,drivingTime,arrivalTime,returnHospitalTime,toAddress,carCode,doctor,nurse,driver,dispatcher,taskResult
	from #temp1 a left outer join  #temp2 b on a.�¼�����=b.�¼����� and a.�������=b.�������
union 
select  name,sickAddress,sickDescription,phone,acceptTime,sendCarTime,drivingTime,arrivalTime,returnHospitalTime,toAddress,carCode,doctor,nurse,driver,dispatcher,taskResult
	from #temp2 b left outer join #temp1 a on a.�¼�����=b.�¼����� and a.�������=b.�������	
order by acceptTime

drop table #temp1,#temp2

--����վԺǰ���ȹ���ͳ�Ʊ�

select �������,��վ����,�������,�¹ʱ��� 
into #temp2
from AuSp120.tb_TaskV t left outer join AuSp120.tb_Event e on t.�¼�����=e.�¼����� 
where e.�¼����ʱ���=1 and t.��������ʱ�� between '2015-01-19 00:00:00' and '2015-03-20 00:00:00' 

select ��վ����,count(�������) as ��������,sum(case when ���ν������ in (5,6,9,13) then 1 else 0 end) ������������,
sum(case when ����ͳ�Ʊ���=1 then 1 else 0 end) ��ͨ�¹�����,sum(case when ����ͳ�Ʊ���=2 then 1 else 0 end) ����������,
sum(case when ����ͳ�Ʊ���=3 then 1 else 0 end) ����,sum(case when ����ͳ�Ʊ���=4 then 1 else 0 end) �������ˮ,
sum(case when ����ͳ�Ʊ���=5 then 1 else 0 end) ������Ƽ���,sum(case when ����ͳ�Ʊ���=6 then 1 else 0 end) ��Ѫ��ϵͳ����,
sum(case when ����ͳ�Ʊ���=7 then 1 else 0 end) ��Ѫ��ϵͳ����,sum(case when ����ͳ�Ʊ���=8 then 1 else 0 end) ������ϵͳ����,
sum(case when ����ͳ�Ʊ���=9 then 1 else 0 end) ʳ���ж�,sum(case when ����ͳ�Ʊ���=10 then 1 else 0 end) ҩ���ж�,
sum(case when ����ͳ�Ʊ���=11 then 1 else 0 end) �ƾ��ж�,sum(case when ����ͳ�Ʊ���=12 then 1 else 0 end) һ����̼�ж�,
sum(case when ����ͳ�Ʊ���=13 then 1 else 0 end) �����ڿƼ���,sum(case when ����ͳ�Ʊ���=14 then 1 else 0 end) ���Ʋ���,
sum(case when ����ͳ�Ʊ���=15 then 1 else 0 end) ����,sum(case when ����ͳ�Ʊ���=16 then 1 else 0 end) ��������,
sum(case when ����ͳ�Ʊ���=17 then 1 else 0 end) ������ٿ�,sum(case when ����ͳ�Ʊ���=18 then 1 else 0 end) ��Ⱦ��,
sum(case when ����ͳ�Ʊ���=19 then 1 else 0 end) ����ǰ����,sum(case when ����ͳ�Ʊ���=20 then 1 else 0 end) ���Ⱥ�����,
sum(case when ����ͳ�Ʊ���=21 then 1 else 0 end) ����
into #temp3 
from AuSp120.tb_PatientCase
where ������� in (select ������� from  #temp2) and ������� is not null
group by ��վ����

select s.��վ����,sum(case when t2.������� is not null then 1 else 0 end) ��������,
	sum(case when t2.�������=4 then 1 else 0 end) �������,
	sum(case when t2.�������=2 then 1 else 0 end) ��ֹ����, 
	sum(case when t2.�������=3 then 1 else 0 end) �ճ�,
	sum(case when �¹ʱ��� is not null then 1 else 0 end) �����ش�ͻ���¼�,
	isnull(t3.������������,0) as ������������,	isnull(t3.��������,0) as ����������,
	isnull(t3.��ͨ�¹�����,0) as ��ͨ�¹�����,	isnull(t3.����������,0) as ����������,
	isnull(t3.����,0) as ����,	isnull(t3.�������ˮ,0) as �������ˮ,
	isnull(t3.������Ƽ���,0) as ������Ƽ���,	isnull(t3.��Ѫ��ϵͳ����,0) as ��Ѫ��ϵͳ����,
	isnull(t3.��Ѫ��ϵͳ����,0) as ��Ѫ��ϵͳ����,	isnull(t3.������ϵͳ����,0) as ������ϵͳ����,
	isnull(t3.ʳ���ж�,0) as ʳ���ж�,	isnull(t3.ҩ���ж�,0) as ҩ���ж�,
	isnull(t3.�ƾ��ж�,0) as �ƾ��ж�,	isnull(t3.һ����̼�ж�,0) as һ����̼�ж�,
	isnull(t3.�����ڿƼ���,0) as �����ڿƼ���,	isnull(t3.���Ʋ���,0) as ���Ʋ���,
	isnull(t3.����,0) as ����,	isnull(t3.��������,0) as ��������,
	isnull(t3.������ٿ�,0) as ������ٿ�,	isnull(t3.��Ⱦ��,0) as ��Ⱦ��,
	isnull(t3.����ǰ����,0) as ����ǰ����,	isnull(t3.���Ⱥ�����,0) as ���Ⱥ�����,
	isnull(t3.����,0) as ����
from AuSp120.tb_Station s
left outer join #temp2 t2 on t2.��վ����=s.��վ����
left outer join	#temp3 t3 on t3.��վ����=s.��վ����
group by s.��վ����,t3.������������,t3.��������,t3.��ͨ�¹�����,t3.����������,t3.����,t3.�������ˮ,t3.������Ƽ���,t3.��Ѫ��ϵͳ����,t3.��Ѫ��ϵͳ����,t3.������ϵͳ����,t3.ʳ���ж�,t3.ҩ���ж�,t3.�ƾ��ж�,t3.һ����̼�ж�,t3.�����ڿƼ���,
t3.���Ʋ���,t3.����,t3.��������,t3.������ٿ�,t3.��Ⱦ��,t3.����ǰ����,t3.���Ⱥ�����,t3.����,��ʾ˳��
order by ��ʾ˳��

drop table #temp2,#temp3
--���ĽӾ�ͳ��
select distinct e.�¼����� eventCode,e.��վ���� station,m.���� dispatcher into #temp1
	from AuSp120.tb_EventV e 
	left outer join AuSp120.tb_MrUser m on m.����=e.����Ա����
	where e.�¼����ʱ���=1 and m.��Ա����=0
select a.ID id,convert(varchar(20),a.��ʼ����ʱ��,120) answerAlarmTime,a.���ȵ绰 alarmPhone,a.��ϵ�绰 relatedPhone,a.�ֳ���ַ siteAddress,
	a.�����ж� judgementOnPhone, station,convert(varchar(20),a.�ɳ�ʱ��,120) sendCarTime, dispatcher
	from #temp1 t 
	left outer join AuSp120.tb_AcceptDescriptV a on t.eventCode=a.�¼�����
	where a.��ʼ����ʱ�� between '2014-12-01 00:00:00' and '2015-05-01 00:00:00' 
	and a.����Ա����='00103' and a.���ȵ绰
drop table #temp1

--����վ�����ͳ��	
select a.�¼����� eventCode,a.�ֳ���ַ siteAddress,a.��ʼ����ʱ�� acceptTime into #temp1
	from AuSp120.tb_AcceptDescriptV a

select t.�¼����� eventCode,det.NameM eventType,am.ʵ�ʱ�ʶ carCode,CONVERT(varchar(20),t.��������ʱ��,120) createTaskTime,
	CONVERT(varchar(20),t.����ʱ��,120) outCarTime,DATEDIFF(SECOND,t.��������ʱ��,t.����ʱ��) outCarTimes,
	dtr.NameM taskResult,t.��ע remark,m.���� dispatcher
	into #temp2
	from AuSp120.tb_TaskV t 
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and t.�������=a.������� 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_DEventType det on det.Code=e.�¼����ͱ���
	left outer join AuSp120.tb_MrUser m on m.����=t.����Ա����
	left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.�������
	left outer join AuSp120.tb_Ambulance am on am.��������=t.��������
	where e.�¼����ʱ���=1 and m.��Ա����=0 and t.����ʱ�� is not null 
	and t.��������ʱ�� between '2014-01-19 00:00:00' and '2015-03-20 00:00:00' 
select t1.siteAddress,t2.eventType,t2.carCode,t1.acceptTime,t2.createTaskTime,t2.outCarTime,t2.outCarTimes,t2.taskResult,t2.remark,t2.dispatcher
	from #temp2 t2 left outer join #temp1 t1 on t1.eventCode=t2.eventCode
	
drop table #temp1,#temp2

--�����������ͳ��
select ��վ���� station,ʵ�ʱ�ʶ carCode,count( p.��������) as pauseNumbers into #temp1 
	from AuSp120.tb_RecordPauseReason p 
	left join AuSp120.tb_Ambulance a on p.��������=a.��������
	where p.����ʱ�� between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	group by (��վ����),(ʵ�ʱ�ʶ)
	
select t.��վ���� station,am.ʵ�ʱ�ʶ carCode,t.����ʱ�� ,t.�����ֳ�ʱ��,t.��������ʱ��,������� into #temp2
	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_Ambulance am on am.��������=t.��������
	where e.�¼����ʱ���=1 and t.��������ʱ�� between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
select station,carCode,AVG(DATEDIFF(Second,��������ʱ��,����ʱ��)) averageOutCarTimes into #temp3 
	from #temp2 where ��������ʱ��<����ʱ�� group by station,carCode
	
select station,carCode,AVG(DATEDIFF(Second,����ʱ��,�����ֳ�ʱ��)) averageArriveSpotTimes into #temp4 
	from #temp2 where ����ʱ��<�����ֳ�ʱ�� and ����ʱ�� is not null group by station,carCode
	
select t.station,t.carCode,sum(case when t.����ʱ�� is not null then 1 else 0 end) outCarNumbers,
	sum(case when t.�����ֳ�ʱ�� is not null then 1 else 0 end) arriveSpotNumbers into #temp5
	from #temp2 t group by t.station,t.carCode	
	
select s.��վ���� station,t5.carCode,outCarNumbers,averageOutCarTimes,arriveSpotNumbers,averageArriveSpotTimes,pauseNumbers
	from AuSp120.tb_Station s left outer join #temp1 t1  on t1.station=s.��վ���� 
	left outer join #temp5 t5 on t1.station=t5.station and t1.carCode=t5.carCode
	left outer join #temp3 t3  on t3.station=t5.station and t3.carCode=t5.carCode
	left outer join #temp4 t4  on t3.station=t4.station and t3.carCode=t4.carCode
	where t5.carCode is not null
	order by s.��ʾ˳��
	
drop table #temp1,#temp2,#temp3,#temp4,#temp5

--˾���������ͳ��
select t.��������ʱ��,t.����ʱ��,t.�����ֳ�ʱ��,�������,t.˾��,t.��վ���� into #temp1
	from AuSp120.tb_TaskV t
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and t.˾��<>'' 
	and t.��������ʱ�� between '2014-01-19 00:00:00' and '2015-03-20 00:00:00' 

select ��վ����,p.˾��,count(p.˾��) as pauseNumbers into #temp2
	from AuSp120.tb_RecordPauseReason p 
	left join AuSp120.tb_Ambulance a on p.��������=a.��������
	where p.����ʱ�� between '2014-01-19 00:00:00' and '2015-03-20 00:00:00' and p.˾��<>''
	group by (��վ����),(p.˾��)

select t.��վ����,t.˾�� driver,SUM(case when t.��������ʱ�� is not null then 1 else 0 end) outCarNumbers,
	SUM(case when t.�������=4 then 1 else 0 end) nomalNumbers,
	SUM(case when t.�������=3 then 1 else 0 end) emptyNumbers,
	SUM(case when t.�������=2 then 1 else 0 end) stopNumbers,
	SUM(case when t.�������=5 then 1 else 0 end) refuseNumbers into #temp3
	from #temp1 t group by t.��վ����,t.˾��
select t.��վ����,t.˾�� driver,AVG(DATEDIFF(Second,t.��������ʱ��,t.����ʱ��)) averageOutCarTimes 
	into #temp4
	from #temp1 t where t.��������ʱ��<t.����ʱ�� group by t.��վ����,t.˾��	
	
select t.��վ����,t.˾�� driver,isnull(AVG(DATEDIFF(Second,t.����ʱ��,t.�����ֳ�ʱ��)),0) averageArriveSpotTimes 
	into #temp5
	from #temp1 t where t.����ʱ��<t.�����ֳ�ʱ�� and t.����ʱ�� is not null group by t.��վ����,t.˾��

select s.��վ���� station,t3.driver,outCarNumbers,nomalNumbers,stopNumbers,emptyNumbers,refuseNumbers,
	pauseNumbers,averageOutCarTimes,isnull(averageArriveSpotTimes,0) averageArriveSpotTimes
	from AuSp120.tb_Station s left outer join #temp2 t2 on t2.��վ����=s.��վ����
	left outer join #temp3 t3 on t3.��վ����=t2.��վ���� and t3.driver=t2.˾��
	left outer join #temp4 t4 on t3.��վ����=t4.��վ���� and t3.driver=t4.driver
	left outer join #temp5 t5 on t3.��վ����=t5.��վ���� and t3.driver=t5.driver
	where t3.driver<>''
	order by s.��ʾ˳��
	
drop table #temp1,#temp2,#temp3,#temp4,#temp5

--����ԭ��ͳ��
select ddr.NameM patientReason,COUNT(*) receivePeopleNumbers,'' rate
	from AuSp120.tb_DDiseaseReason ddr
	left outer join AuSp120.tb_PatientCase pc  on ddr.Code=pc.�������
	group by  ddr.NameM 
	
--��������ͳ��
select ddcs.NameM patientClass,COUNT(*) receivePeopleNumbers,'' rate
	from AuSp120.tb_DDiseaseClassState ddcs
	left outer join AuSp120.tb_PatientCase pc  on ddcs.Code=pc.����ͳ�Ʊ���
	where pc.����ʱ��  between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	group by  ddcs.NameM 
	
--����ʱ���ͳ��
select  DATENAME(HOUR,pc.����ʱ��) span,pc.����ͳ�Ʊ��� code into #temp1
	from  AuSp120.tb_PatientCase pc 
	where pc.����ʱ��  between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	
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
--����������ͳ��
select distinct pc.��վ����,pc.������� into #temp1
	from AuSp120.tb_PatientCase pc 
	where pc.��վ����<>''
select s.��վ���� station,COUNT(*) sendNumbers,COUNT(tt.�������) fillNumbers,'' rate
	from AuSp120.tb_TaskV t left outer join #temp1 tt on  t.��վ����=tt.��վ���� and t.�������=tt.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_Station s on s.��վ����=t.��վ����
	where e.�¼����ʱ���=1 and t.�������=4 and t.��������ʱ�� between '2014-01-19 00:00:00' and '2015-03-20 00:00:00'
	group by s.��վ����,s.��ʾ˳��
	order by s.��ʾ˳��
drop table #temp1
--ҽ����ʿ�������ͳ�� 
select  pc.�������,pc.�泵ҽ��,pc.�泵��ʿ,pc.��վ���� into #temp1 
	from AuSp120.tb_PatientCase pc where pc.�������<>'' and pc.����ʱ�� between '' and ''
	
select distinct pc.�������,pc.�泵ҽ��,pc.�泵��ʿ,pc.��վ���� into #temp2 
	from AuSp120.tb_PatientCase pc where pc.�������<>''
	
select tt.��վ����,tt.�泵ҽ��,COUNT(*) doctorCureNumbers into #temp3 from #temp1 tt 
	where tt.�泵ҽ��<>'' and tt.�������<>'' group by tt.��վ����,tt.�泵ҽ��
select tt.��վ����,tt.�泵��ʿ,COUNT(*) nurseCureNumbers into #temp3 from #temp1 tt 
	where tt.�泵��ʿ<>'' and tt.�������<>'' group by tt.��վ����,tt.�泵��ʿ
	
select t.��վ���� ,tt.�泵ҽ�� name,SUM(case when t.�������<>5 then 1 else 0 end) outCarNumbers,
	SUM(case when t.�������=4 then 1 else 0 end) validOutCarNumbers,
	SUM(case when t.������� in (2,3) then 1 else 0 end) stopNumbers,
	isnull(AVG(DATEDIFF(Second,t.�����ֳ�ʱ��,t.���ʱ��)),0) averateCureTimes into #temp4
	from AuSp120.tb_TaskV t left outer join #temp2 tt on t.�������=tt.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	where e.�¼����ʱ���=1 and t.������� is not null and tt.�泵ҽ��<>'' 
	group by  t.��վ���� ,tt.�泵ҽ��
	order by  t.��վ���� ,tt.�泵ҽ��
	
select s.��վ���� station,tt4.name,tt4.outCarNumbers,tt4.validOutCarNumbers,tt4.stopNumbers,
	tt3.doctorCureNumbers curePeopleNumbers,tt4.averateCureTimes
	from #temp3 tt3 left outer join #temp4 tt4 on tt3.��վ����=tt4.��վ���� and tt3.�泵ҽ��=tt4.name	
	left outer join AuSp120.tb_Station s on s.��վ����=tt4.��վ����
	
drop table #temp1,#temp2,#temp3,#temp4

--��ʼ�����ɳ�����X��
select a.ID id,m.����,CONVERT(varchar(20),a.��ʼ����ʱ��,120) ��ʼ����ʱ��,CONVERT(varchar(20),a.�ɳ�ʱ��,120) �ɳ�ʱ��,
	 dat.NameM,a.���ȵ绰,DATEDIFF(SECOND,a.��ʼ����ʱ��,a.�ɳ�ʱ��),a.��ע
	from AuSp120.tb_Task t 
	left outer join AuSp120.tb_AcceptDescriptV a on a.�¼�����=t.�¼����� and a.�������=t.�������
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_MrUser m on t.����Ա����=m.����
	left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.���ͱ���
	where e.�¼����ʱ���=1 and a.���ͱ��� not in(3,10)  and m.��Ա����=0
	order by m.����
	
select * from AuSp120.tb_DAcceptDescriptType
--����ʱ������
select det.NameM eventType,den.NameM eventNature,e.���ȵ绰 callPhone,a.�ֳ���ַ callAddress,a.�������� patientNeed,
	a.�����ж� preJudgment,dis.NameM sickCondition,a.����Ҫ�� specialNeeds,a.�������� humanNumbers,
	a.�������� sickName,	a.���� age,a.�Ա� gender,di.NameM identitys,a.��ϵ�� contactMan,a.��ϵ�绰 contactPhone,
	a.�ֻ� extension,m.���� thisDispatcher,	a.��ע remark,a.�Ƿ�Ҫ���� isOrNoLitter,a.������� acceptNumber,
	convert(varchar(20),a.��ʼ����ʱ��,120) acceptStartTime,dat.NameM acceptType,	dhr.NameM toBeSentReason,
	convert(varchar(20),a.��������ʱ��,120) endAcceptTime,convert(varchar(20),a.�ɳ�ʱ��,120) sendCarTime,
	drr.NameM cancelReason,s.��վ���� sendStation,	am.ʵ�ʱ�ʶ carIndentiy,dts.NameM states,convert(varchar(20),
	t.��������ʱ��,120) receiveOrderTime,dtr.NameM taskResult,
	isnull(dsr.NameM,'')+isnull(dtrr.NameM,'')+ISNULL(der.NameM,'') reason,	
	CONVERT(varchar(20),t.����ʱ��,120) outCarTime,t.��ע taskRemark,
	convert(varchar(20),t.�����ֳ�ʱ��,120) arriveSpotTime,
	t.�������� takeHumanNumbers,	isnull(a.��Ժ����,0) toHospitalNumbers,
	convert(varchar(20),t.�뿪�ֳ�ʱ��,120) leaveSpotTime,ISNULL(a.��������,0) deathNumbers,	
	ISNULL(a.��������,0) stayHospitalNumbers,'' backHospitalNumbers,convert(varchar(20),t.���ʱ��,120) completeTime,
	m.���� stationDispatcher,t.������� outCarNumbers	
	from AuSp120.tb_AcceptDescriptV a 
	left outer  join AuSp120.tb_EventV e on a.�¼�����=e.�¼�����	
	left outer join AuSp120.tb_TaskV t on a.�¼�����=t.�¼����� and a.�������=t.�������	
	left outer join AuSp120.tb_DEventType det on det.Code=e.�¼����ͱ���	
	left outer join AuSp120.tb_DEventNature den on den.Code=e.�¼����ʱ���	
	left outer join AuSp120.tb_DILLState dis on dis.Code=a.�������	
	left outer join AuSp120.tb_DIdentity di on di.Code=a.��ݱ���	
	left outer join AuSp120.tb_MrUser m on m.����=a.����Ա���� or m.����=t.��վ����Ա����	
	left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.���ͱ���	
	left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.����ԭ�����	
	left outer join AuSp120.tb_DRepealReason drr on drr.Code=a.����ԭ�����	
	left outer join AuSp120.tb_Station s on s.��վ����=t.��վ����	
	left outer join AuSp120.tb_Ambulance am on am.��������=t.��������	
	left outer join AuSp120.tb_DTaskState dts on dts.Code=t.״̬����	
	left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.�������	
	left outer join AuSp120.tb_DEmptyReason der on der.Code=t.�ſճ�ԭ�����	
	left outer join AuSp120.tb_DRefuseReason dtrr on dtrr.Code=t.�ܾ�����ԭ�����	
	left outer join AuSp120.tb_DStopReason dsr on dsr.Code=t.��ֹ����ԭ�����	where a.ID=112
--�������ͳ��
select s.��վ���� station,SUM(case when pc.����ͳ�Ʊ���=1 then 1 else 0 end) type1,
	SUM(case when pc.����ͳ�Ʊ���=2 then 1 else 0 end) type2,SUM(case when pc.����ͳ�Ʊ���=3 then 1 else 0 end) type3,
	SUM(case when pc.����ͳ�Ʊ���=4 then 1 else 0 end) type4,SUM(case when pc.����ͳ�Ʊ���=5 then 1 else 0 end) type5,
	SUM(case when pc.����ͳ�Ʊ���=6 then 1 else 0 end) type6,SUM(case when pc.����ͳ�Ʊ���=7 then 1 else 0 end) type7,
	SUM(case when pc.����ͳ�Ʊ���=8 then 1 else 0 end) type8,SUM(case when pc.����ͳ�Ʊ���=9 then 1 else 0 end) type9,
	SUM(case when pc.����ͳ�Ʊ���=10 then 1 else 0 end) type10,SUM(case when pc.����ͳ�Ʊ���=11 then 1 else 0 end) type11,
	SUM(case when pc.����ͳ�Ʊ���=12 then 1 else 0 end) type12,SUM(case when pc.����ͳ�Ʊ���=13 then 1 else 0 end) type13,
	SUM(case when pc.����ͳ�Ʊ���=14 then 1 else 0 end) type14,SUM(case when pc.����ͳ�Ʊ���=15 then 1 else 0 end) type15,
	SUM(case when pc.����ͳ�Ʊ���=16 then 1 else 0 end) type16,SUM(case when pc.����ͳ�Ʊ���=17 then 1 else 0 end) type17,
	SUM(case when pc.����ͳ�Ʊ���=18 then 1 else 0 end) type18,SUM(case when pc.����ͳ�Ʊ���=19 then 1 else 0 end) type19,
	SUM(case when pc.����ͳ�Ʊ���=20 then 1 else 0 end) type20,SUM(case when pc.����ͳ�Ʊ���=21 then 1 else 0 end) type21,
	COUNT(*) total
	from AuSp120.tb_PatientCase pc 
	left outer join AuSp120.tb_TaskV t on pc.��������=t.�������� and pc.�������=t.�������
	left outer join AuSp120.tb_DDiseaseClassState ddcs on ddcs.Code=pc.����ͳ�Ʊ���
	left outer join AuSp120.tb_Station s on s.��վ����=t.��վ����	
	where t.��������ʱ�� between '2015-04-04 00:00:00' and '2016-04-04 00:00:00'
	group by s.��վ����
	
	