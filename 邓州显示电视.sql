(select '1' type,'ֵ����Ա' keys,AuSp120.GetOnlinePeople('1') value,'1' orders 
union
select '1' type,'����绰����' keys,cast(COUNT(*) as varchar) value,'2' orders from AuSp120.tb_TeleRecord 
where ��¼���ͱ��� in (1,2,3,5,8) and ����ʱ�� between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
union
select '1' type,'�ɳ�����' keys,cast(COUNT(*) as varchar) value,'3' orders from AuSp120.tb_Task t 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	where e.�¼����ʱ���=1 and t.��������ʱ�� between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
union
select '1' type,s.��վ���� keys,cast(COUNT(*) as varchar) value,'4' orders from AuSp120.tb_Task t 
	left outer join AuSp120.tb_EventV e on e.�¼�����=t.�¼�����
	left outer join AuSp120.tb_Station s on t.��վ����=s.��վ����
	where e.�¼����ʱ���=1 and t.��������ʱ�� between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
	group by s.��վ����
union
select '2' type,dc.NameM keys,cast(COUNT(*) as varchar) value,'5' orders 
	from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.����ͳ�Ʊ���
	left outer join AuSp120.tb_TaskV t on t.�������=pc.��� and t.�������=pc.�������
	where dc.NameM is not null and t.��������ʱ�� between '2014-01-01 00:00:00' and '2016-01-01 00:00:00'
	group by dc.NameM) order by orders 

