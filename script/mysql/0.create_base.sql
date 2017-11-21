CREATE TABLE `bf_people` (
  `create_date` datetime NOT NULL,
  `create_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `id_bf_people` varchar(36) NOT NULL COMMENT '����',
  `code` varchar(32) NOT NULL COMMENT '�û�����',
  `name` varchar(32) NOT NULL COMMENT '�û�����',
  `password` varchar(32) NOT NULL COMMENT '����',
  `id_bf_department` varchar(36) NOT NULL COMMENT '��������',
  `status` varchar(2) NOT NULL COMMENT '��Ա״̬��0-��Ч��1-������2-������3-����ˣ�',
  `error_count` int(11) NOT NULL COMMENT '����������',
  PRIMARY KEY (`id_bf_people`),
  UNIQUE KEY `bf_people_co` (`code`),
  KEY `bf_people_de` (`id_bf_department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ա��';

CREATE TABLE `bf_department` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_department` varchar(36) NOT NULL COMMENT '����',
  `code` varchar(32) NOT NULL COMMENT '��������',
  `name` varchar(32) NOT NULL COMMENT '������',
  `level` int(3) NOT NULL COMMENT '�����ȼ���rootΪ0',
  `parent_dept_id` varchar(36) DEFAULT NULL COMMENT '������id',
  PRIMARY KEY (`id_bf_department`),
  UNIQUE KEY `bf_department_co` (`code`),
  KEY `bf_department_pi` (`parent_dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='������';

CREATE TABLE `bf_department_rel` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_department_rel` varchar(36) NOT NULL COMMENT '����',
  `id_parent_department` varchar(36) NOT NULL COMMENT '����������',
  `id_child_department` varchar(36) NOT NULL COMMENT '�ӻ�������',
  `parent_level` int(11) NOT NULL COMMENT '�������ȼ�',
  PRIMARY KEY (`id_bf_department_rel`),
  KEY `department_rel_pd` (`id_parent_department`),
  KEY `department_rel_cd` (`id_child_department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����������ϵ��';

CREATE TABLE `bf_role` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_role` varchar(36) NOT NULL COMMENT '����',
  `name` varchar(32) NOT NULL COMMENT '��ɫ��',
  `description` varchar(1024) DEFAULT NULL COMMENT '��ɫ����',
  PRIMARY KEY (`id_bf_role`),
  UNIQUE KEY `role_na` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɫ��';

CREATE TABLE `bf_people_role_rel` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_people_role_rel` varchar(36) NOT NULL COMMENT '����',
  `id_bf_people` varchar(36) NOT NULL COMMENT '��Աid',
  `id_bf_role` varchar(36) NOT NULL COMMENT '��ɫid',
  PRIMARY KEY (`id_bf_people_role_rel`),
  KEY `people_role_rel_ro` (`id_bf_role`),
  KEY `people_role_rel_pe` (`id_bf_people`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ա��ɫ������';

CREATE TABLE `bf_document` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_document` varchar(36) NOT NULL COMMENT '����',
  `name` varchar(128) NOT NULL COMMENT '�ļ�����',
  `extension` varchar(16) NOT NULL COMMENT '��չ��',
  `url` varchar(1024) NOT NULL COMMENT '�ļ��洢·��',
  `size` int(11) NOT NULL COMMENT '�ļ���С',
  `memo` varchar(1024) DEFAULT NULL COMMENT '��ע',
  `download_count` int(11) NOT NULL COMMENT '���ش���',
  `md5` varchar(32) NOT NULL COMMENT '�ļ�md5ֵ',
  `status` varchar(2) NOT NULL COMMENT '�ļ�״̬��0-��Ч��1-������',
  PRIMARY KEY (`id_bf_document`),
  KEY `document_md` (`md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ĵ���';

CREATE TABLE `bf_doc_group` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_doc_group` varchar(36) NOT NULL COMMENT '����',
  `group_no` varchar(36) NOT NULL COMMENT '�ĵ����',
  `id_bf_document` varchar(36) NOT NULL COMMENT '�ĵ�id',
  PRIMARY KEY (`id_bf_doc_group`),
  KEY `doc_group_gn` (`group_no`),
  KEY `doc_group_do` (`id_bf_document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ĵ����';

CREATE TABLE `bf_folder` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_folder` varchar(36) NOT NULL COMMENT '����',
  `name` varchar(128) NOT NULL COMMENT '�ļ�����',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '���ļ���id',
  PRIMARY KEY (`id_bf_folder`),
  KEY `folder_pi` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ļ��б�';

CREATE TABLE `bf_folder_doc_rel` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_folder_doc_rel` varchar(36) NOT NULL COMMENT '����',
  `id_bf_folder` varchar(36) NOT NULL COMMENT '�ļ���id',
  `id_bf_document` varchar(36) NOT NULL COMMENT '�ĵ�id',
  PRIMARY KEY (`id_bf_folder_doc_rel`),
  KEY `folder_doc_rel_fo` (`id_bf_folder`),
  KEY `folder_doc_rel_do` (`id_bf_document`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ļ������ĵ�������ϵ��';

CREATE TABLE `bf_role_folder_auth` (
  `create_by` varchar(32) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_by` varchar(32) NOT NULL,
  `update_date` datetime NOT NULL,
  `id_bf_role_folder_auth` varchar(36) NOT NULL COMMENT '����',
  `id_bf_role` varchar(36) NOT NULL COMMENT '��ɫid',
  `id_bf_folder` varchar(36) NOT NULL COMMENT '�ĵ�id',
  `authority` varchar(4) DEFAULT 'r' COMMENT 'Ȩ�ޣ�r-����w-д',
  PRIMARY KEY (`id_bf_role_folder_auth`),
  KEY `role_folder_auth_ro` (`id_bf_role`),
  KEY `role_folder_auth_fo` (`id_bf_folder`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɫ�ĵ���ϵ��';
