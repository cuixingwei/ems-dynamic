if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_EventAcceptTaskStation]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_EventAcceptTaskStation]
GO

CREATE VIEW [AuSp120].[View_EventAcceptTaskStation]
AS
SELECT     a.初步判断, a.受理台号, a.受理序号, a.回车原因编码, a.备注 AS 受理备注, a.开始受理时刻, a.择院需求, a.择院需求编码, a.挂起原因编码, a.撤消原因编码, 
                      a.结束受理时刻, a.等车地址, e.事件名称, e.事件性质编码, e.事件来源, e.事件类型编码, e.事故种类编码, e.事故编码, e.呼救电话, e.受理时刻, t.任务编码, 
                      t.到达医院时刻, t.出车时刻, t.分站编码, t.分站调度员编码, t.到达现场时刻, t.返回站中时刻, t.结果编码, t.调度员编码, t.车辆ID, t.车辆标识, t.车辆编码, 
                      t.暂停调用原因编码, t.中止任务原因编码, t.备注, s.分站名称, 
                      CASE WHEN t .备注 LIKE '%市中心医院%' THEN '市中心医院' WHEN t .备注 LIKE '%第一人民医院%' THEN '第一人民医院' WHEN t .备注 LIKE '%第二人民医院%' THEN
                       '第二人民医院' WHEN t .备注 LIKE '%第四人民医院%' THEN '第四人民医院' WHEN t .备注 LIKE '%第三七一医院%' THEN '第三七一医院' WHEN t .备注 LIKE '%第三附属医院%'
                       THEN '第三附属医院' WHEN t .备注 LIKE '%河南荣军医院%' THEN '河南荣军医院' WHEN t .备注 LIKE '%新乡公立医院%' THEN '新乡公立医院' WHEN t .备注 LIKE '%新乡市中医院%'
                       THEN '新乡市中医院' ELSE '其他医院' END AS byRobHospital, t.救治人数, a.类型编码
FROM         AuSp120.tb_EventV AS e LEFT OUTER JOIN
                      AuSp120.tb_AcceptDescriptV AS a ON a.事件编码 = e.事件编码 LEFT OUTER JOIN
                      AuSp120.tb_TaskV AS t ON a.事件编码 = t.事件编码 AND a.受理序号 = t.受理序号 LEFT OUTER JOIN
                      AuSp120.tb_Station AS s ON s.分站编码 = t.分站编码

GO