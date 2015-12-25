(select '1' type,'值班人员' keys,AuSp120.GetOnlinePeople('1') value,'1' orders 
union
select '1' type,'呼入电话总数' keys,cast(COUNT(*) as varchar) value,'2' orders from AuSp120.tb_TeleRecord 
where 记录类型编码 in (1,2,3,5,8) and 产生时刻 between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
union
select '1' type,'派车总数' keys,cast(COUNT(*) as varchar) value,'3' orders from AuSp120.tb_Task t 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	where e.事件性质编码=1 and t.生成任务时刻 between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
union
select '1' type,s.分站名称 keys,cast(COUNT(*) as varchar) value,'4' orders from AuSp120.tb_Task t 
	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码
	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码
	where e.事件性质编码=1 and t.生成任务时刻 between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
	group by s.分站名称
union
select '2' type,dc.NameM keys,cast(COUNT(*) as varchar) value,'5' orders 
	from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.分类统计编码
	left outer join AuSp120.tb_TaskV t on t.任务序号=pc.序号 and t.任务编码=pc.任务编码
	where dc.NameM is not null and t.生成任务时刻 between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
	group by dc.NameM) order by orders 

