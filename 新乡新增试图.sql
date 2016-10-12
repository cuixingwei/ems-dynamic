if exists (select * from dbo.sysobjects where id = object_id(N'[AuSp120].[View_EventAcceptTaskStation]') and OBJECTPROPERTY(id, N'IsView') = 1)
drop view [AuSp120].[View_EventAcceptTaskStation]
GO

CREATE VIEW [AuSp120].[View_EventAcceptTaskStation]
AS
SELECT     a.�����ж�, a.����̨��, a.�������, a.�س�ԭ�����, a.��ע AS ����ע, a.��ʼ����ʱ��, a.��Ժ����, a.��Ժ�������, a.����ԭ�����, a.����ԭ�����, 
                      a.��������ʱ��, a.�ȳ���ַ, e.�¼�����, e.�¼����ʱ���, e.�¼���Դ, e.�¼����ͱ���, e.�¹��������, e.�¹ʱ���, e.���ȵ绰, e.����ʱ��, t.�������, 
                      t.����ҽԺʱ��, t.����ʱ��, t.��վ����, t.��վ����Ա����, t.�����ֳ�ʱ��, t.����վ��ʱ��, t.�������, t.����Ա����, t.����ID, t.������ʶ, t.��������, 
                      t.��ͣ����ԭ�����, t.��ֹ����ԭ�����, t.��ע, s.��վ����, 
                      CASE WHEN t .��ע LIKE '%������ҽԺ%' THEN '������ҽԺ' WHEN t .��ע LIKE '%��һ����ҽԺ%' THEN '��һ����ҽԺ' WHEN t .��ע LIKE '%�ڶ�����ҽԺ%' THEN
                       '�ڶ�����ҽԺ' WHEN t .��ע LIKE '%��������ҽԺ%' THEN '��������ҽԺ' WHEN t .��ע LIKE '%������һҽԺ%' THEN '������һҽԺ' WHEN t .��ע LIKE '%��������ҽԺ%'
                       THEN '��������ҽԺ' WHEN t .��ע LIKE '%�����پ�ҽԺ%' THEN '�����پ�ҽԺ' WHEN t .��ע LIKE '%���繫��ҽԺ%' THEN '���繫��ҽԺ' WHEN t .��ע LIKE '%��������ҽԺ%'
                       THEN '��������ҽԺ' ELSE '����ҽԺ' END AS byRobHospital, t.��������, a.���ͱ���
FROM         AuSp120.tb_EventV AS e LEFT OUTER JOIN
                      AuSp120.tb_AcceptDescriptV AS a ON a.�¼����� = e.�¼����� LEFT OUTER JOIN
                      AuSp120.tb_TaskV AS t ON a.�¼����� = t.�¼����� AND a.������� = t.������� LEFT OUTER JOIN
                      AuSp120.tb_Station AS s ON s.��վ���� = t.��վ����

GO