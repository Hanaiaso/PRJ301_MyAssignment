USE [PRJ301_Project]
GO
/****** Object:  Table [dbo].[Department]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Department](
	[did] [int] NOT NULL,
	[dname] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[did] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[eid] [int] NOT NULL,
	[ename] [nvarchar](150) NOT NULL,
	[did] [int] NOT NULL,
	[gender] [bit] NOT NULL,
	[address] [nvarchar](150) NOT NULL,
	[dob] [date] NOT NULL,
	[iswork] [bit] NOT NULL,
	[updatedtime] [datetime] NULL,
	[createdby] [nvarchar](150) NOT NULL,
	[updatedby] [nvarchar](150) NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[eid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feature]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feature](
	[fid] [int] NOT NULL,
	[fname] [nvarchar](150) NOT NULL,
	[url] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Feature] PRIMARY KEY CLUSTERED 
(
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[rid] [int] NOT NULL,
	[rname] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoleFeature]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoleFeature](
	[rid] [int] NOT NULL,
	[fid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[rid] ASC,
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[uid] [int] NOT NULL,
	[uname] [nvarchar](150) NOT NULL,
	[uusername] [nvarchar](150) NOT NULL,
	[upassword] [nvarchar](150) NOT NULL,
	[udisplayname] [nvarchar](150) NOT NULL,
	[isactive] [bit] NOT NULL,
 CONSTRAINT [PK_User_1] PRIMARY KEY CLUSTERED 
(
	[uusername] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 10/14/2024 12:58:45 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[uusername] [nvarchar](150) NOT NULL,
	[rid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[uusername] ASC,
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Department] ([did], [dname]) VALUES (1, N'Human Resource ')
INSERT [dbo].[Department] ([did], [dname]) VALUES (2, N'Accounting')
INSERT [dbo].[Department] ([did], [dname]) VALUES (3, N'Information Technology')
INSERT [dbo].[Department] ([did], [dname]) VALUES (4, N'Production Planning ')
INSERT [dbo].[Department] ([did], [dname]) VALUES (5, N'Administration')
INSERT [dbo].[Department] ([did], [dname]) VALUES (6, N'Mechanical and Transport ')
GO
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (1, N'Nguyễn Văn A', 1, 1, N'Hà Nội', CAST(N'2000-05-15' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (2, N'Trần Thị B', 2, 0, N'Hồ Chí Minh', CAST(N'2001-03-22' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (3, N'Lê Văn C', 3, 1, N'Đà Nẵng', CAST(N'1999-12-30' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (4, N'Phạm Thị D', 4, 0, N'Hải Phòng', CAST(N'2004-08-19' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (5, N'Ngô Văn E', 5, 1, N'Cần Thơ', CAST(N'2002-11-25' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (6, N'Vũ Thị F', 6, 0, N'Nha Trang', CAST(N'2003-01-05' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (7, N'Bùi Văn G', 1, 1, N'Đà Lạt', CAST(N'2000-06-12' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (8, N'Đặng Thị H', 2, 0, N'Vũng Tàu', CAST(N'2004-07-09' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (9, N'Nguyễn Văn I', 3, 1, N'Nam Định', CAST(N'2000-09-21' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (10, N'Trần Thị J', 4, 0, N'Quy Nhơn', CAST(N'2002-04-15' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (11, N'Lê Văn K', 5, 1, N'Hà Giang', CAST(N'2001-05-03' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (12, N'Phạm Thị L', 6, 0, N'Hưng Yên', CAST(N'2000-02-14' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (13, N'Ngô Văn M', 1, 1, N'Thanh Hóa', CAST(N'2003-03-11' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (14, N'Vũ Thị N', 2, 0, N'Thái Bình', CAST(N'2001-10-28' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (15, N'Bùi Văn O', 3, 1, N'Ninh Bình', CAST(N'2004-12-07' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (16, N'Đặng Thị P', 4, 0, N'Hải Dương', CAST(N'2002-01-29' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (17, N'Nguyễn Văn Q', 5, 1, N'Hà Tĩnh', CAST(N'1999-08-16' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (18, N'Trần Thị R', 6, 0, N'Vĩnh Phúc', CAST(N'2000-09-05' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (19, N'Lê Văn S', 1, 1, N'Bắc Ninh', CAST(N'2003-07-20' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (20, N'Phạm Thị T', 2, 0, N'Lạng Sơn', CAST(N'2002-03-09' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (21, N'Ngô Văn U', 3, 1, N'Đắk Lắk', CAST(N'2001-11-18' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (22, N'Vũ Thị V', 4, 0, N'Kon Tum', CAST(N'2000-06-26' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (23, N'Bùi Văn W', 5, 1, N'Quảng Ninh', CAST(N'2002-10-14' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (24, N'Đặng Thị X', 6, 0, N'Kiên Giang', CAST(N'2003-04-08' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (25, N'Nguyễn Văn Y', 1, 1, N'Cà Mau', CAST(N'2000-12-02' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (26, N'Trần Thị Z', 2, 0, N'Sóc Trăng', CAST(N'2004-05-23' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (27, N'Lê Văn AA', 3, 1, N'Bình Dương', CAST(N'2001-08-30' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (28, N'Phạm Thị AB', 4, 0, N'Thừa Thiên Huế', CAST(N'2003-02-04' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (29, N'Ngô Văn AC', 5, 1, N'Hà Nam', CAST(N'2002-07-12' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (30, N'Vũ Thị AD', 6, 0, N'Hà Tĩnh', CAST(N'2000-09-25' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (31, N'Bùi Văn AE', 1, 1, N'Tây Ninh', CAST(N'2003-11-01' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (32, N'Đặng Thị AF', 2, 0, N'Bà Rịa - Vũng Tàu', CAST(N'2002-06-18' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (33, N'Nguyễn Văn AG', 3, 1, N'Bắc Giang', CAST(N'2000-05-27' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (34, N'Trần Thị AH', 4, 0, N'Hải Phòng', CAST(N'2004-03-17' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (35, N'Lê Văn AI', 5, 1, N'Ninh Thuận', CAST(N'2001-04-09' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (36, N'Phạm Thị AJ', 6, 0, N'Nam Định', CAST(N'2002-12-20' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (37, N'Ngô Văn AK', 1, 1, N'Hà Giang', CAST(N'2000-01-14' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (38, N'Vũ Thị AL', 2, 0, N'Gia Lai', CAST(N'2004-08-05' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (39, N'Bùi Văn AM', 3, 1, N'Hưng Yên', CAST(N'2001-10-23' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (40, N'Đặng Thị AN', 4, 0, N'Hà Nội', CAST(N'2000-02-15' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (41, N'Nguyễn Văn AO', 5, 1, N'Quảng Trị', CAST(N'2003-09-30' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (42, N'Trần Thị AP', 6, 0, N'Hà Tĩnh', CAST(N'2002-07-11' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (43, N'Lê Văn AQ', 1, 1, N'Lâm Đồng', CAST(N'2001-11-22' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (44, N'Phạm Thị AR', 2, 0, N'Hòa Bình', CAST(N'2000-06-30' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (45, N'Ngô Văn AS', 3, 1, N'Đắk Nông', CAST(N'2004-05-05' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (46, N'Vũ Thị AT', 4, 0, N'Diện Biên', CAST(N'2002-08-12' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (47, N'Bùi Văn AU', 5, 1, N'Quảng Nam', CAST(N'2003-01-23' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (48, N'Đặng Thị AV', 6, 0, N'Phú Yên', CAST(N'2000-10-06' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (49, N'Nguyễn Văn AW', 1, 1, N'Thái Nguyên', CAST(N'2003-02-20' AS Date), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (50, N'Trần Thị AX', 2, 0, N'Quảng Bình', CAST(N'2002-03-26' AS Date), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (51, N'Lê Văn AY', 3, 1, N'Hà Tĩnh', CAST(N'2001-04-01' AS Date), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [did], [gender], [address], [dob], [iswork], [updatedtime], [createdby], [updatedby]) VALUES (52, N'Phạm Thị AZ', 4, 0, N'Bến Tre', CAST(N'2004-11-19' AS Date), 1, NULL, N'mra', NULL)
GO
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (1, N'Create Employee', N'/employee/create')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (2, N'Update Employee', N'/employee/update')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (3, N'Delete Employee', N'/employee/delete')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (4, N'List all Employees', N'/employee/list')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (5, N'Search Employees', N'/employee/search')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (6, N'View Employee', N'/employee/detail')
GO
INSERT [dbo].[Role] ([rid], [rname]) VALUES (1, N'Head of HRD')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (2, N'Production Planning Manager')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (3, N'Workshop Manager')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (4, N'Recruitment Officer')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (5, N'Profile Officer')
GO
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 1)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 2)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 3)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 4)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 5)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (1, 6)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (3, 4)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (3, 5)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (4, 1)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (4, 4)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (4, 6)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (5, 2)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (5, 3)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (5, 4)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (5, 5)
INSERT [dbo].[RoleFeature] ([rid], [fid]) VALUES (5, 6)
GO
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (1, N'Nguyễn Văn A', N'mra', N'123', N'MRA', 1)
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (2, N'Nguyễn Văn B', N'mrb', N'123', N'MRB', 1)
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (3, N'Nguyễn Văn C', N'mrc', N'123', N'MRC', 1)
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (4, N'Nguyễn Văn D', N'mrd', N'123', N'MRD', 1)
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (5, N'Nguyễn Văn E', N'mre', N'123', N'MRE', 1)
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (6, N'Nguyễn Văn F', N'mrf', N'123', N'MRF', 1)
INSERT [dbo].[User] ([uid], [uname], [uusername], [upassword], [udisplayname], [isactive]) VALUES (7, N'Nguyễn Văn G', N'mrg', N'123', N'MRG', 0)
GO
INSERT [dbo].[UserRole] ([uusername], [rid]) VALUES (N'mra', 1)
INSERT [dbo].[UserRole] ([uusername], [rid]) VALUES (N'mrb', 4)
INSERT [dbo].[UserRole] ([uusername], [rid]) VALUES (N'mrc', 5)
INSERT [dbo].[UserRole] ([uusername], [rid]) VALUES (N'mrd', 4)
INSERT [dbo].[UserRole] ([uusername], [rid]) VALUES (N'mrd', 5)
INSERT [dbo].[UserRole] ([uusername], [rid]) VALUES (N'mrf', 3)
GO
ALTER TABLE [dbo].[Employee] ADD  CONSTRAINT [DF_Employee_iswork]  DEFAULT ((1)) FOR [iswork]
GO
ALTER TABLE [dbo].[User] ADD  CONSTRAINT [DF_User_isactive]  DEFAULT ((1)) FOR [isactive]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Department] FOREIGN KEY([did])
REFERENCES [dbo].[Department] ([did])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Department]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_User] FOREIGN KEY([createdby])
REFERENCES [dbo].[User] ([uusername])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_User]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_User1] FOREIGN KEY([updatedby])
REFERENCES [dbo].[User] ([uusername])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_User1]
GO
ALTER TABLE [dbo].[RoleFeature]  WITH CHECK ADD FOREIGN KEY([fid])
REFERENCES [dbo].[Feature] ([fid])
GO
ALTER TABLE [dbo].[RoleFeature]  WITH CHECK ADD FOREIGN KEY([rid])
REFERENCES [dbo].[Role] ([rid])
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD FOREIGN KEY([rid])
REFERENCES [dbo].[Role] ([rid])
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD FOREIGN KEY([uusername])
REFERENCES [dbo].[User] ([uusername])
GO
