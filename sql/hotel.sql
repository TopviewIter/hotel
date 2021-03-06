USE [hotel]
GO
/****** Object:  Table [dbo].[t_hotel]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_hotel](
	[id] [nvarchar](32) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[address] [nvarchar](255) NOT NULL,
	[managerId] [nvarchar](32) NOT NULL,
	[phone] [nchar](11) NOT NULL,
	[rank] [nchar](10) NOT NULL,
 CONSTRAINT [PK_t_hotel] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[t_order]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_order](
	[id] [nvarchar](32) NOT NULL,
	[userId] [nvarchar](32) NOT NULL,
	[roomId] [nvarchar](32) NOT NULL,
	[momey] [money] NOT NULL,
	[isPay] [bit] NOT NULL,
	[state] [nvarchar](10) NOT NULL,
	[startTime] [datetime] NOT NULL,
	[endTime] [datetime] NOT NULL,
	[createTime] [datetime] NOT NULL,
 CONSTRAINT [PK_t_order] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[t_role]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_role](
	[id] [nvarchar](32) NOT NULL,
	[name] [nvarchar](16) NOT NULL,
	[remark] [nvarchar](50) NULL,
 CONSTRAINT [PK_t_role] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[t_room]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_room](
	[id] [nvarchar](32) NOT NULL,
	[roomNum] [nvarchar](10) NOT NULL,
	[state] [nvarchar](10) NOT NULL,
	[type] [nvarchar](10) NOT NULL,
	[price] [money] NOT NULL,
	[remark] [nvarchar](255) NULL,
 CONSTRAINT [PK_t_room] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[t_staff]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_staff](
	[id] [nvarchar](32) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[password] [nvarchar](16) NOT NULL,
	[identify] [nchar](18) NOT NULL,
	[phone] [nchar](11) NOT NULL,
	[sex] [nchar](1) NOT NULL,
	[salary] [nvarchar](10) NOT NULL,
	[job] [nvarchar](16) NOT NULL,
 CONSTRAINT [PK_t_staff] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[t_user]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_user](
	[id] [nvarchar](32) NOT NULL,
	[name] [nvarchar](16) NOT NULL,
	[password] [nvarchar](32) NOT NULL,
	[identify] [nchar](18) NOT NULL,
	[phone] [nchar](11) NOT NULL,
	[sex] [nchar](1) NOT NULL,
	[isVip] [bit] NOT NULL,
	[rank] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_t_user] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  View [dbo].[v_hotel]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[v_hotel] as
	select [id], [name], [address], [managerId], [phone], [rank] from t_hotel
GO
/****** Object:  View [dbo].[v_role]    Script Date: 2017/4/17 13:53:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create view [dbo].[v_role] as
	select [id], [name], [remark] from t_role
GO
INSERT [dbo].[t_hotel] ([id], [name], [address], [managerId], [phone], [rank]) VALUES (N'1', N'广财丽枫酒店', N'广财', N'1', N'13342543532', N'V         ')
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'1', N'455a3ad7d3a44876a374ce47dcdd6b12', N'1', 100.0000, 1, N'完结', CAST(N'2017-01-03 00:00:00.000' AS DateTime), CAST(N'2017-01-06 00:00:00.000' AS DateTime), CAST(N'2017-01-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'10', N'455a3ad7d3a44876a374ce47dcdd6b12', N'10', 100.0000, 0, N'取消', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'11', N'455a3ad7d3a44876a374ce47dcdd6b12', N'11', 100.0000, 0, N'预定', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'12', N'455a3ad7d3a44876a374ce47dcdd6b12', N'12', 100.0000, 1, N'完结', CAST(N'2017-02-03 00:00:00.000' AS DateTime), CAST(N'2017-02-05 00:00:00.000' AS DateTime), CAST(N'2017-02-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'13', N'455a3ad7d3a44876a374ce47dcdd6b12', N'1', 100.0000, 1, N'完结', CAST(N'2017-01-03 00:00:00.000' AS DateTime), CAST(N'2017-01-06 00:00:00.000' AS DateTime), CAST(N'2017-01-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'14', N'455a3ad7d3a44876a374ce47dcdd6b12', N'1', 100.0000, 1, N'完结', CAST(N'2017-01-03 00:00:00.000' AS DateTime), CAST(N'2017-01-06 00:00:00.000' AS DateTime), CAST(N'2017-01-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'16', N'455a3ad7d3a44876a374ce47dcdd6b12', N'2', 100.0000, 1, N'完结', CAST(N'2017-01-03 00:00:00.000' AS DateTime), CAST(N'2017-01-06 00:00:00.000' AS DateTime), CAST(N'2017-01-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'2', N'455a3ad7d3a44876a374ce47dcdd6b12', N'2', 100.0000, 1, N'完结', CAST(N'2017-03-24 07:32:50.293' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'3', N'455a3ad7d3a44876a374ce47dcdd6b12', N'1', 100.0000, 0, N'入住', CAST(N'2017-03-24 12:23:51.907' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'4', N'455a3ad7d3a44876a374ce47dcdd6b12', N'4', 100.0000, 1, N'完结', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'5', N'455a3ad7d3a44876a374ce47dcdd6b12', N'5', 100.0000, 0, N'预定', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'6', N'455a3ad7d3a44876a374ce47dcdd6b12', N'6', 100.0000, 0, N'预定', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'7', N'455a3ad7d3a44876a374ce47dcdd6b12', N'7', 100.0000, 1, N'完结', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'8', N'455a3ad7d3a44876a374ce47dcdd6b12', N'8', 100.0000, 1, N'完结', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'9', N'455a3ad7d3a44876a374ce47dcdd6b12', N'9', 100.0000, 0, N'预定', CAST(N'2017-03-03 00:00:00.000' AS DateTime), CAST(N'2017-03-05 00:00:00.000' AS DateTime), CAST(N'2017-03-02 00:00:00.000' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'ac72d53d1a61414d9076eb349b1cf7b0', N'455a3ad7d3a44876a374ce47dcdd6b12', N'3', 100.0000, 1, N'入住', CAST(N'2017-03-24 14:58:06.760' AS DateTime), CAST(N'2016-03-29 12:00:00.000' AS DateTime), CAST(N'2017-03-24 14:53:59.483' AS DateTime))
INSERT [dbo].[t_order] ([id], [userId], [roomId], [momey], [isPay], [state], [startTime], [endTime], [createTime]) VALUES (N'c2c960bf96014b2483b7a594de1fd55b', N'455a3ad7d3a44876a374ce47dcdd6b12', N'2', 100.0000, 1, N'预定', CAST(N'2017-01-03 00:00:00.000' AS DateTime), CAST(N'2017-01-04 00:00:00.000' AS DateTime), CAST(N'2017-03-25 07:37:44.320' AS DateTime))
INSERT [dbo].[t_role] ([id], [name], [remark]) VALUES (N'1', N'管理员', N'管理员')
INSERT [dbo].[t_role] ([id], [name], [remark]) VALUES (N'2', N'前台', N'前台')
INSERT [dbo].[t_role] ([id], [name], [remark]) VALUES (N'3', N'招待', NULL)
INSERT [dbo].[t_role] ([id], [name], [remark]) VALUES (N'4', N'清洁', NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'1', N'1001', N'入住', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'10', N'1010', N'空闲', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'11', N'1011', N'预定', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'12', N'1012', N'空闲', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'13', N'1013', N'预定', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'14', N'1014', N'入住', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'15', N'1015', N'预定', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'16', N'1016', N'空闲', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'17', N'1017', N'预定', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'18', N'1018', N'入住', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'19', N'1001', N'入住', N'单人房', 100.0000, N'NULL')
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'2', N'1002', N'预定', N'双人房', 100.0000, N'')
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'3', N'1003', N'空闲', N'单人房', 100.0000, N'')
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'4', N'1004', N'空闲', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'5', N'1005', N'空闲', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'6', N'1006', N'入住', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'7', N'1007', N'空闲', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'8', N'1008', N'预定', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'9', N'1009', N'预定', N'单人房', 100.0000, NULL)
INSERT [dbo].[t_room] ([id], [roomNum], [state], [type], [price], [remark]) VALUES (N'c3428e3bce434ad89d0f49e057fb8fd4', N'1019', N'空闲', N'双人房', 100.0000, N'好房')
INSERT [dbo].[t_staff] ([id], [name], [password], [identify], [phone], [sex], [salary], [job]) VALUES (N'1', N'宝宝', N'123', N'998324566703248594', N'18823450934', N'女', N'10000', N'管理员')
INSERT [dbo].[t_staff] ([id], [name], [password], [identify], [phone], [sex], [salary], [job]) VALUES (N'2', N'宝宝', N'123', N'998324566703248594', N'18823450931', N'女', N'10060', N'前台')
INSERT [dbo].[t_user] ([id], [name], [password], [identify], [phone], [sex], [isVip], [rank]) VALUES (N'229593367df24ad29248eb2828691841', N'小明', N'123', N'009748366784392837', N'18834759374', N'男', 1, N'I')
INSERT [dbo].[t_user] ([id], [name], [password], [identify], [phone], [sex], [isVip], [rank]) VALUES (N'229593367df24ad29248eb2828691845', N'小明', N'123', N'009748366784392837', N'18834759370', N'男', 1, N'I')
INSERT [dbo].[t_user] ([id], [name], [password], [identify], [phone], [sex], [isVip], [rank]) VALUES (N'455a3ad7d3a44876a374ce47dcdd6b12', N'宝宝', N'123', N'228940399057385432', N'13384828445', N'女', 1, N'I')
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_t_order_state]    Script Date: 2017/4/17 13:53:26 ******/
CREATE NONCLUSTERED INDEX [IX_t_order_state] ON [dbo].[t_order]
(
	[state] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UNI_t_role_name]    Script Date: 2017/4/17 13:53:26 ******/
ALTER TABLE [dbo].[t_role] ADD  CONSTRAINT [UNI_t_role_name] UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_t_room_state]    Script Date: 2017/4/17 13:53:26 ******/
CREATE NONCLUSTERED INDEX [IX_t_room_state] ON [dbo].[t_room]
(
	[state] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_t_staff_phone]    Script Date: 2017/4/17 13:53:26 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_t_staff_phone] ON [dbo].[t_staff]
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_t_staff_phone_pwd]    Script Date: 2017/4/17 13:53:26 ******/
CREATE NONCLUSTERED INDEX [IX_t_staff_phone_pwd] ON [dbo].[t_staff]
(
	[phone] ASC,
	[password] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_t_user_phone]    Script Date: 2017/4/17 13:53:26 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_t_user_phone] ON [dbo].[t_user]
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_t_user_phone_pwd]    Script Date: 2017/4/17 13:53:26 ******/
CREATE NONCLUSTERED INDEX [IX_t_user_phone_pwd] ON [dbo].[t_user]
(
	[phone] ASC,
	[password] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[t_order]  WITH CHECK ADD  CONSTRAINT [FK_t_order_t_staff] FOREIGN KEY([roomId])
REFERENCES [dbo].[t_room] ([id])
GO
ALTER TABLE [dbo].[t_order] CHECK CONSTRAINT [FK_t_order_t_staff]
GO
ALTER TABLE [dbo].[t_order]  WITH CHECK ADD  CONSTRAINT [FK_t_order_t_user] FOREIGN KEY([userId])
REFERENCES [dbo].[t_user] ([id])
GO
ALTER TABLE [dbo].[t_order] CHECK CONSTRAINT [FK_t_order_t_user]
GO
ALTER TABLE [dbo].[t_staff]  WITH CHECK ADD  CONSTRAINT [FK_t_staff_job] FOREIGN KEY([job])
REFERENCES [dbo].[t_role] ([name])
GO
ALTER TABLE [dbo].[t_staff] CHECK CONSTRAINT [FK_t_staff_job]
GO
ALTER TABLE [dbo].[t_user]  WITH CHECK ADD  CONSTRAINT [CK_t_user] CHECK  (([sex]='男' OR [sex]='女'))
GO
ALTER TABLE [dbo].[t_user] CHECK CONSTRAINT [CK_t_user]
GO
/****** Object:  StoredProcedure [dbo].[proc_staff_exist]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create proc [dbo].[proc_staff_exist]  
	@phone varchar(11),
	@password varchar(32),
	@result int output 
as 
	select @result = count(*) from t_staff where phone = @phone and password = @password
GO
/****** Object:  StoredProcedure [dbo].[proc_staff_salary]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[proc_staff_salary]  
	@money int
AS 
	DECLARE @id varchar(32), @salary varchar(10)
	DECLARE salary_cursor CURSOR FOR
	SELECT id, salary FROM t_staff
	WHERE job != '管理员'
	OPEN salary_cursor
	FETCH NEXT FROM salary_cursor INTO @id, @salary
	WHILE @@FETCH_STATUS = 0
	BEGIN
	   UPDATE t_staff SET salary = convert(int, @salary) + @money where id = @id 
	   FETCH NEXT FROM salary_cursor INTO @id, @salary
	END
	CLOSE salary_cursor
	DEALLOCATE salary_cursor

GO
/****** Object:  StoredProcedure [dbo].[proc_user_exist]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create proc [dbo].[proc_user_exist]  
	@phone varchar(11),
	@password varchar(32),
	@result int output 
as 
	select @result = count(*) from t_user where phone = @phone and password = @password
GO
/****** Object:  Trigger [dbo].[trigger_hotel_phone_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_hotel_phone_check] on [dbo].[t_hotel] for insert as 
begin transaction
declare @len int
select @len = LEN(phone) from inserted
if(@len = 11)      
	commit transaction 
else 
	rollback transaction 
GO
/****** Object:  Trigger [dbo].[trigger_order_state_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_order_state_check] on [dbo].[t_order] for insert as 
begin transaction
declare @state nvarchar(10)
select @state = [state] from inserted
print @state
if(@state = '预定' or @state = '完结' or @state = '入住' or @state = '取消')      
	commit transaction 
else 
	rollback transaction 
GO
/****** Object:  Trigger [dbo].[trigger_room_state_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_room_state_check] on [dbo].[t_room] for insert as 
begin transaction
declare @state nvarchar(10)
select @state = [state] from inserted
if(@state = '预定' or @state = '空闲' or @state = '入住')      
	commit transaction 
else 
	rollback transaction 
GO
/****** Object:  Trigger [dbo].[trigger_staff_identify_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_staff_identify_check] on [dbo].[t_staff] for insert as 
begin transaction
declare @len int
select @len = LEN(identify) from inserted
if(@len = 18)      
	commit transaction 
else 
	rollback transaction 
GO
/****** Object:  Trigger [dbo].[trigger_staff_phone_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_staff_phone_check] on [dbo].[t_staff] for insert as 
begin transaction
declare @len int
select @len = LEN(phone) from inserted
if(@len = 11)      
	commit transaction 
else 
	rollback transaction 
GO
/****** Object:  Trigger [dbo].[trigger_phone_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_phone_check] on [dbo].[t_user] for insert as 
begin transaction
declare @len int
select @len = LEN(phone) from inserted
if(@len = 11)      
	commit transaction 
else 
	rollback transaction 
GO
/****** Object:  Trigger [dbo].[trigger_user_identify_check]    Script Date: 2017/4/17 13:53:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[trigger_user_identify_check] on [dbo].[t_user] for insert as 
begin transaction
declare @len int
select @len = LEN(identify) from inserted
if(@len = 18)      
	commit transaction 
else 
	rollback transaction 
GO
