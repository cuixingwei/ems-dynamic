if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_AcceptDescriptHung]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_AcceptDescriptHung]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_AcceptDescriptSecond]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_AcceptDescriptSecond]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_AcceptMark]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_AcceptMark]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_Accident]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_Accident]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_AnswerAlarm]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_AnswerAlarm]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_CarStateChange]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_CarStateChange]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_PatientCase]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_PatientCase]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_SaveCenterTask]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_SaveCenterTask]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_StationTaskCount]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_StationTaskCount]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_TeleRecord]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_TeleRecord]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_PersonSign]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_PersonSign]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_RecordPauseReason]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_RecordPauseReason]
GO



CREATE view AuSp120.View_PersonSign
as
select  
--row_number() OVER (PARTITION BY a.调度员编码 ORDER BY a.id) as id,
p.id, 
m.姓名, 
s.分站名称,
a.车牌号码,
p.UserID,
s.分站编码,
CONVERT(VARCHAR(19), p.上班时刻, 20) AS 上班时刻, 
CONVERT(VARCHAR(19), p.下班时刻, 20) AS 下班时刻, 
p.上班操作人, 
p.下班操作人, 
p.上班方式,  
p.下班方式 
FROM AuSp120.tb_PersonSign AS p
left join AuSp120.tb_MrUser AS m ON p.UserID = m.工号
left join AuSp120.tb_Ambulance as a on a.ID = p.AmbulanceID
left join AuSp120.tb_Station as s on s.分站编码 = a.分站编码


--inner join AuSp120.tb_Task t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
go






CREATE view AuSp120.View_RecordPauseReason
as 
select 
r.ID,
r.车辆编码,
r.调度员编码,
r.暂停调用原因,
r.结束时刻,
datediff(ss,r.操作时刻,r.结束时刻) as 暂停时长,
r.司机,
--r.结束时刻,
--datediff(ss,r.操作时刻,r.结束时刻) as 暂停时长,
convert(varchar(19),r.操作时刻,20) as 操作时刻,
r.Flag,
a.实际标识,
a.分站编码,
s.分站名称,
m.姓名 as 调度员
 from Ausp120.tb_RecordPauseReason r 
left join Ausp120.tb_Ambulance a on r.车辆编码=a.车辆编码
left join Ausp120.tb_Station s on a.分站编码=s.分站编码
left join Ausp120.tb_MrUser m on r.调度员编码=m.工号
go





CREATE view AuSp120.View_AcceptDescriptHung
as 
SELECT   
a.ID, 
e.事件名称,
dadt.NameM as 受理类型, 
a.调度员编码,
m.姓名 as 调度员, 
CONVERT(varchar(19),a.开始受理时刻,20) as 开始受理时刻, 
CONVERT(varchar(19),a.结束受理时刻,20) as 结束受理时刻,
DATEDIFF(ss, a.开始受理时刻,a.结束受理时刻) AS 挂起时长, 
a.挂起原因编码, 
dhr.NameM AS 挂起原因,
e.事件类型编码,
et.NameM as 事件类型
FROM  AuSp120.tb_AcceptDescriptV a 
INNER JOIN AuSp120.tb_MrUser m ON a.调度员编码 = m.工号 
INNER JOIN AuSp120.tb_EventV e ON a.事件编码 = e.事件编码 and  e.事件性质编码 = 1
left JOIN AuSp120.tb_DAcceptDescriptType dadt ON a.类型编码 = dadt.Code 
left  JOIN AuSp120.tb_DHangReason dhr ON a.挂起原因编码 = dhr.Code
left join AuSp120.tb_DEventType et on e.事件类型编码=et.Code
go





CREATE view AuSp120.View_AcceptDescriptSecond
as
select  
--row_number() OVER (PARTITION BY a.调度员编码 ORDER BY a.id) as id,
a.id, 
m.姓名, 
CONVERT(VARCHAR(19), a.电话振铃时刻, 20) AS 电话振铃时刻, 
CONVERT(VARCHAR(19), a.开始受理时刻, 20) AS 开始受理时刻, 
a.备注, 
a.受理台号, 
DATEDIFF(ss, a.电话振铃时刻, a.开始受理时刻) AS 振铃到受理间隔, 
m.人员类型, 
m.ID AS uID, 
CONVERT(VARCHAR(19), a.结束受理时刻, 20) AS 结束受理时刻, 
d .NameM AS 受理类型, 
DATEDIFF(ss, a.开始受理时刻, a.派车时刻) AS 开始受理到派车间隔, 
CONVERT(VARCHAR(19), a.派车时刻, 20) AS 派车时刻, 
a.呼救电话, 
DATEDIFF(ss, a.结束受理时刻, a.派车时刻) AS 结束受理到派车间隔, 
a.调度员编码, 
DATEDIFF(ss, a.开始受理时刻, a.结束受理时刻) AS 受理时长, 
a.定位标志,
--t.生成任务时刻,--与受理表中派车时刻相等
--t.出车时刻,
--DATEDIFF(s,t.生成任务时刻,t.出车时刻) as 出车时长
convert(varchar(19),(select top 1 t.出车时刻 from Ausp120.tb_TaskV t where a.事件编码=t.事件编码 and a.受理序号=t.受理序号 and t.出车时刻 is not null),20) as 出车时刻,
DATEDIFF(s,a.派车时刻,(select top 1 t.出车时刻 from Ausp120.tb_TaskV t where a.事件编码=t.事件编码 and a.受理序号=t.受理序号 and t.出车时刻 is not null)) as 出车时长

FROM      AuSp120.tb_AcceptDescriptV AS a 
left join AuSp120.tb_MrUser AS m ON a.调度员编码 = m.工号 
left join AuSp120.tb_DAcceptDescriptType AS d ON a.类型编码 = d .ID 
--inner join AuSp120.tb_Task t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
go







CREATE view AuSp120.View_AcceptMark
as 
SELECT   a.ID,
m.姓名, 
m.人员类型, 
m.ID as uID,
e.调度员编码,
e.呼救电话, 
CONVERT(varchar(19), e.受理时刻,20) as 首次受理时刻, 
e.受理次数, 
a.现场地址, 
a.备注,
(select top 1 t.备注 from Ausp120.tb_taskV t where t.备注 is not null and  t.事件编码=a.事件编码 and t.受理序号=a.受理序号) as 问题备注
FROM  AuSp120.tb_AcceptDescriptV a 
INNER JOIN AuSp120.tb_EventV e ON a.事件编码 = e.事件编码  and e.事件性质编码 = 1
INNER JOIN AuSp120.tb_MrUser m ON e.调度员编码 = m.工号
go





CREATE VIEW AuSp120.View_Accident
AS
SELECT 
e.ID,
e.事件编码,
e.事件名称, 
e.受理时刻,
--e.录音文件名 as 录音号,
e.呼救电话  as 主叫号码,
--dgal.NameM as 事故等级,
dgat.NameM AS 事故种类,
det.NameM AS 事件类型,
--e.分站编码,
--a.危重人数,
--a.轻伤人数,
--a.重伤人数,
--a.院内抢救后死亡数,
--a.转运途中死亡数,
--(select count(p.ID) from AuSp120.tb_PatientCase as p left join AuSp120.tb_TaskV as t on p.任务编码 = t.任务编码 and (p.转归编码 = '5'   or  p.转归编码 = '6') left join AuSp120.tb_EventV as e on e.事件性质编码 = 1 and e.事件编码 = t.事件编码 where e.是否重大事故 = 1 ) as 现场死亡数,
--a.住院数,
--a.留院观察,
--a.治疗后离院,
--a.未治疗自行离院,
--a.派出司机数,
--a.派出医生数,
--a.派出护士数,
--a.派出担架员数,
--a.派出其他人员数,
m.姓名 as 调度员
from AuSp120.tb_EventV as e
left join AuSp120.tb_AccidentEventLink as ael on e.事件编码 = ael.事件编码
right join AuSp120.tb_Accident as a on a.事故编码 =  ael.事故编码 
--left join AuSp120.tb_AccidentEventLink as ael on  e.事件性质编码 = 1
--left  join AuSp120.tb_Accident as a   on  a.事故编码 = ael.事故编码  and ael.事件编码 = e.事件编码 
--left join AuSp120.tb_DGroAccidentLevel as dgal on dgal.Code = a.事故等级编码 
left join AuSp120.tb_DEventType as det on det.Code = e.事件类型编码
left join AuSp120.tb_DGroAccidentType as dgat on dgat.Code = e.事故种类编码
left join AuSp120.tb_MrUser as m on m.工号  = e.调度员编码  
go





CREATE view AuSp120.View_AnswerAlarm
as 
select 
a.ID,
a.事件编码,
a.受理序号,
convert(varchar(19),a.开始受理时刻,20) as 接警时间,
convert(varchar(19),a.派车时刻,20) as 派车时间,
convert(varchar(19),(select top 1 t.到达医院时刻 from AuSp120.tb_TaskV t where t.事件编码 = a.事件编码 and t.到达医院时刻 is not null ),20) as 返院时间,
a.现场地址 as 报警地点,
a.初步判断 as 电话判断,
s.分站名称 as 出车单位,
a.呼救电话 as 报警电话,
a.联系电话 as 相关电话,
a.调度员编码,
m.姓名 as 值班人员
from Ausp120.tb_AcceptDescriptV a
--left join Ausp120.tb_EventTeleOther e on a.事件编码=e.事件编码 
left join Ausp120.tb_Ambulance am on a.车辆编码=am.车辆编码
left join Ausp120.tb_Station s on am.分站编码=s.分站编码
left join Ausp120.tb_MrUser m on a.调度员编码=m.工号
--left join Ausp120.tb_TaskV  t on a.事件编码=t.事件编码
go





CREATE view AuSp120.View_CarStateChange
as
select  

td.id, 
t.事件编码,
e.事件名称,

td.车辆编码,
d.NameM  as  车辆状态,
td.时刻值,
td.记录方式,
td.台号 as 坐席台, 
m.姓名 as  调度员姓名
FROM AuSp120.tb_TaskDT AS td 
left join AuSp120.tb_TaskV as t ON t.任务编码 = td.任务编码
left join AuSp120.tb_EventV as e ON t.事件编码 = e.事件编码  and  e.事件性质编码 = 1
left join AuSp120.tb_MrUser AS m ON td.调度员编码 = m.工号 
left join AuSp120.tb_DAmbulanceState AS d ON td.车辆状态编码 = d.Code

--inner join AuSp120.tb_Task t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
go







CREATE view AuSp120.View_PatientCase
as 
select
(select top 1 t.生成任务时刻 from Ausp120.tb_taskV t where t.任务编码=p.任务编码) as 记录时间,
(select top 1 t.结果编码 from Ausp120.tb_taskV t where t.任务编码=p.任务编码) as 出车结果编码,
(case p.送往地点类型编码
when 1 then s.分站名称
when 2 then '其他医院'
when 3 then '未送院'
else '其他'
end
) as 送往地点名称,
--s.分站名称,
p.*
from Ausp120.tb_PatientCase p 
left outer join AuSp120.tb_Station s on p.分站编码=s.分站编码
go





CREATE view AuSp120.View_SaveCenterTask
as 
SELECT   TOP 100 PERCENT 
t.ID,
--a.患者姓名,
(select top 1 pc.姓名 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码) as 患者姓名,
(select top 1 pc.病人主诉 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 ) as 主诉病症,
(select top 1 pc.家庭住址 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 ) as 家庭住址,
(select count(pc.ID) from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码  and  t.结果编码 = 4  and (pc.转归编码 = 1 or pc.转归编码 = 2 or pc.转归编码 = 3 or pc.转归编码 = 4 )) as 病员数,
(select top 1 pc.科室  from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and  t.结果编码 = 4  and (pc.转归编码 = 1 or pc.转归编码 = 2 or pc.转归编码 = 3 or pc.转归编码 = 4 )) as 送往科室,
a.现场地址,
a.初步判断,
a.呼救电话,
a.联系电话,
convert(varchar(19),t.生成任务时刻,20) as 生成任务时刻,
convert(varchar(19),a.开始受理时刻,20) as 开始受理时刻,
convert(varchar(19),a.派车时刻,20) as 派车时刻,
convert(varchar(19),t.出车时刻,20) as 出车时刻,
convert(varchar(19),t.到达现场时刻,20) as 到达现场时刻,
convert(varchar(19),t.到达医院时刻,20) as 到达医院时刻,
a.等车地址,
a.送往地点,
t.分站编码,
s.分站名称,
t.车辆编码,
am.实际标识,
(select top 1 随车医生 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and 随车医生<>'') as 出诊人员,
(select top 1 随车护士 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and 随车护士<>'') as 出诊护士,
(select top 1 司机  from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and  司机<>'' ) as 出诊司机,
a.调度员编码,
m.姓名 as 调度员,
'未知调时' as 调时 ,
'未知天气' as 天气,
'未知道路' as 道路,
'未知班次' as 班次,
dr.NameM as 出车结果,
convert(varchar(19),t.返回站中时刻,20) as 中止时刻,
datediff(ss,t.出车时刻,t.完成时刻) as 空跑时间,
t.中止任务原因编码,
dpr.NameM as 中止原因,
t.放空车原因编码,
der.NameM as 空车原因,
a.备注 as 受理备注,
t.备注
FROM  AuSp120.tb_TaskV  t  
left JOIN   AuSp120.tb_AcceptDescriptV  a  ON a.事件编码 = t.事件编码 AND a.受理序号 = t.受理序号 
left JOIN   AuSp120.tb_EventV  e ON t.事件编码  = e.事件编码  and e.事件性质编码 = 1
left join AuSp120.tb_DTaskResult dr on t.结果编码=dr.ID
left join AuSp120.tb_DStopReason dpr on t.中止任务原因编码=dpr.ID
left join AuSp120.tb_DEmptyReason der on t.放空车原因编码=der.Code
LEFT JOIN AuSp120.tb_MrUser m ON a.调度员编码 = m.工号 
LEFT JOIN AuSp120.tb_Ambulance am ON a.车辆编码 = am.车辆编码 
LEFT JOIN AuSp120.tb_Station s ON t.分站编码 = s.分站编码
ORDER BY a.开始受理时刻 asc
go




CREATE view AuSp120.View_StationTaskCount
as 
select 
t.ID,
convert(varchar(19),t.生成任务时刻,20) as 生成任务时刻,
t.分站编码,
s.分站名称,
t.结果编码,
dtr.NameM as 任务结果,
--pc.随车医生,
--pc.司机,
--pc. 随车护士,
(select top 1 随车医生 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and 随车医生<>'') as 随车医生,
(select top 1 司机 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and 司机<>'') as 司机,
(select top 1 随车护士 from Ausp120.tb_patientcase pc where pc.任务编码=t.任务编码 and 随车护士<>'') as 随车护士,
e.事件类型编码,
det.NameM as 事件类型,
convert(varchar(19),a.开始受理时刻,20) as 首次受理时刻,
t.调度员编码,
m.姓名 as 调度员,
convert(varchar(19),t.出车时刻,20) as 出车时刻,
datediff(ss,t.生成任务时刻,t.出车时刻) as 出诊时长,
convert(varchar(19),t.接收命令时刻,20) as 接收命令时刻,
convert(varchar(19),t.到达现场时刻,20) as 到达现场时刻,
datediff(ss,t.出车时刻,t.到达现场时刻) as 到达现场时长,
convert(varchar(19),t.完成时刻,20) as 完成时刻,
datediff(ss,t.生成任务时刻,t.完成时刻) as 救治时长,
datediff(ss,t.离开现场时刻,t.返回站中时刻) as 返回时长,
datediff(ss,a.开始受理时刻,a.派车时刻) as 派车时长,
t.车辆编码,
am.实际标识,
a.呼救电话,
a.现场地址,
t.救治人数,
t.备注,
t.暂停调用原因编码,
--a.受理类型,
d.NameM as 受理类型
from AuSp120.tb_TaskV t
--left join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 
left join AuSp120.tb_EventV e on t.事件编码=e.事件编码  and  e.事件性质编码 = 1
left join AuSp120.tb_AcceptDescriptV a on t.事件编码=a.事件编码 and t.受理序号=a. 受理序号   and  (a.类型编码 = 1 or a.类型编码 = 3 or a.类型编码 = 5 or a.类型编码 = 6  or a.类型编码 = 8) 
left join AuSp120.tb_Station s on t.分站编码=s.分站编码
left join AuSp120.tb_MrUser m on t.调度员编码=m.工号
left join AuSp120.tb_Ambulance am on t.车辆编码=am.车辆编码
left join AuSp120.tb_DEventType det on e.事件类型编码=det.Code
left join AuSp120.tb_DTaskResult dtr on t.结果编码=dtr.Code
left join AuSp120.tb_DAcceptDescriptType AS d ON a.类型编码 = d .ID 
go




CREATE view AuSp120.View_TeleRecord
as
select  
--row_number() OVER (PARTITION BY a.调度员编码 ORDER BY a.id) as id,
t.id, 
m.姓名, 
d.Code,
CONVERT(VARCHAR(19), t.振铃时刻, 20) AS 电话振铃时刻, 
CONVERT(VARCHAR(19), t.通话开始时刻, 20) AS 开始受理时刻, 
t.备注, 
t.通话坐席 as 坐席台, 
DATEDIFF(ss, t.振铃时刻,t.通话开始时刻) AS 振铃到受理间隔, 
m.人员类型, 
m.ID AS uID, 
CONVERT(VARCHAR(19), t.挂断时刻, 20) AS 结束受理时刻, 
t.电话号码,  
datediff(mi,t.通话开始时刻,getDate()) as 未受理间隔,
t.调度员编码 
FROM AuSp120.tb_TeleRecordV AS t 
left join AuSp120.tb_DTeleRecordType as d  on  t.记录类型编码 = d.Code
left join AuSp120.tb_MrUser AS m ON t.调度员编码 = m.工号 

--inner join AuSp120.tb_Task t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号
go

