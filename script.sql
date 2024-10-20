USE [NNN]
GO
/****** Object:  Table [dbo].[Attendence]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Attendence](
	[aid] [int] NOT NULL,
	[seid] [int] NOT NULL,
	[Quantity] [decimal](18, 2) NOT NULL,
	[Alpha] [float] NOT NULL,
 CONSTRAINT [PK_Attendence] PRIMARY KEY CLUSTERED 
(
	[aid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Department]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Department](
	[did] [int] NOT NULL,
	[dname] [nvarchar](150) NOT NULL,
	[type] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[did] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[eid] [int] IDENTITY(1,1) NOT NULL,
	[ename] [nvarchar](150) NOT NULL,
	[gender] [bit] NOT NULL,
	[address] [nvarchar](150) NOT NULL,
	[dob] [date] NOT NULL,
	[did] [int] NOT NULL,
	[salary] [decimal](18, 2) NOT NULL,
	[isWork] [bit] NOT NULL,
	[updatedtime] [datetime] NULL,
	[createdby] [nvarchar](150) NOT NULL,
	[updatedby] [nvarchar](150) NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[eid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feature]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feature](
	[fid] [int] IDENTITY(1,1) NOT NULL,
	[fname] [nvarchar](150) NOT NULL,
	[url] [varchar](max) NOT NULL,
 CONSTRAINT [PK_Feature] PRIMARY KEY CLUSTERED 
(
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Plan]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Plan](
	[plid] [int] IDENTITY(1,1) NOT NULL,
	[plname] [nvarchar](150) NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NOT NULL,
	[did] [int] NOT NULL,
	[isDone] [bit] NOT NULL,
 CONSTRAINT [PK_Plan] PRIMARY KEY CLUSTERED 
(
	[plid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PlanCampain]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PlanCampain](
	[plcid] [int] IDENTITY(1,1) NOT NULL,
	[plid] [int] NOT NULL,
	[pid] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Estimate] [int] NOT NULL,
 CONSTRAINT [PK_PlanCampain] PRIMARY KEY CLUSTERED 
(
	[plcid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[pid] [int] IDENTITY(1,1) NOT NULL,
	[pname] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[pid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[rid] [int] IDENTITY(1,1) NOT NULL,
	[rname] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoleFeature]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoleFeature](
	[fid] [int] NOT NULL,
	[rid] [int] NOT NULL,
 CONSTRAINT [PK_RoleFeature] PRIMARY KEY CLUSTERED 
(
	[fid] ASC,
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SchedualCampaign]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SchedualCampaign](
	[scid] [int] NOT NULL,
	[plcid] [int] NOT NULL,
	[Date] [date] NOT NULL,
	[Shift] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
 CONSTRAINT [PK_SchedualCampaign] PRIMARY KEY CLUSTERED 
(
	[scid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SchedualEmployee]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SchedualEmployee](
	[seid] [int] NOT NULL,
	[scid] [int] NOT NULL,
	[eid] [int] NOT NULL,
	[Quantity] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_SchedualEmployee] PRIMARY KEY CLUSTERED 
(
	[seid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[username] [nvarchar](150) NOT NULL,
	[password] [varchar](150) NOT NULL,
	[displayname] [nvarchar](150) NOT NULL,
	[isWork] [bit] NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 10/21/2024 8:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[username] [nvarchar](150) NOT NULL,
	[rid] [int] NOT NULL,
 CONSTRAINT [PK_UserRole] PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Department] ([did], [dname], [type]) VALUES (1, N'Human Resource ', N'HO')
INSERT [dbo].[Department] ([did], [dname], [type]) VALUES (2, N'Accounting', N'HO')
INSERT [dbo].[Department] ([did], [dname], [type]) VALUES (3, N'Information Technology', N'WS')
INSERT [dbo].[Department] ([did], [dname], [type]) VALUES (4, N'Production Planning ', N'HO')
INSERT [dbo].[Department] ([did], [dname], [type]) VALUES (5, N'Administration', N'WS')
INSERT [dbo].[Department] ([did], [dname], [type]) VALUES (6, N'Mechanical and Transport ', N'WS')
GO
SET IDENTITY_INSERT [dbo].[Employee] ON 

INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (45, N'Nguyễn Văn A', 1, N'Hà Nội', CAST(N'2000-05-15' AS Date), 1, CAST(7500000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (46, N'Trần Thị B', 0, N'Hồ Chí Minh', CAST(N'2001-03-22' AS Date), 2, CAST(7300000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (47, N'Lê Văn C', 1, N'Đà Nẵng', CAST(N'1999-12-30' AS Date), 3, CAST(7200000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (48, N'Phạm Thị D', 0, N'Hải Phòng', CAST(N'2004-08-19' AS Date), 4, CAST(6800000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (49, N'Ngô Văn E', 1, N'Cần Thơ', CAST(N'2002-11-25' AS Date), 5, CAST(7000000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (50, N'Vũ Thị F', 0, N'Nha Trang', CAST(N'2003-01-05' AS Date), 6, CAST(6500000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (51, N'Bùi Văn G', 1, N'Đà Lạt', CAST(N'2000-06-12' AS Date), 1, CAST(7800000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (52, N'Đặng Thị H', 0, N'Vũng Tàu', CAST(N'2004-07-09' AS Date), 2, CAST(7600000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (53, N'Nguyễn Văn I', 1, N'Nam Định', CAST(N'2000-09-21' AS Date), 3, CAST(7400000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (54, N'Trần Thị J', 0, N'Quy Nhơn', CAST(N'2002-04-15' AS Date), 4, CAST(7100000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (55, N'Lê Văn K', 1, N'Hà Giang', CAST(N'2001-05-03' AS Date), 5, CAST(7600000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (56, N'Phạm Thị L', 0, N'Hưng Yên', CAST(N'2000-02-14' AS Date), 6, CAST(7800000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (57, N'Ngô Văn M', 1, N'Thanh Hóa', CAST(N'2003-03-11' AS Date), 1, CAST(6900000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (58, N'Vũ Thị N', 0, N'Thái Bình', CAST(N'2001-10-28' AS Date), 2, CAST(7800000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (59, N'Bùi Văn O', 1, N'Ninh Bình', CAST(N'2004-12-07' AS Date), 3, CAST(7400000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (60, N'Đặng Thị P', 0, N'Hải Dương', CAST(N'2002-01-29' AS Date), 4, CAST(7800000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (61, N'Nguyễn Văn Q', 1, N'Hà Tĩnh', CAST(N'1999-08-16' AS Date), 5, CAST(7200000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (62, N'Trần Thị R', 0, N'Vĩnh Phúc', CAST(N'2000-09-05' AS Date), 6, CAST(7600000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (63, N'Lê Văn S', 1, N'Bắc Ninh', CAST(N'2003-07-20' AS Date), 1, CAST(7800000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (64, N'Phạm Thị T', 0, N'Lạng Sơn', CAST(N'2002-03-09' AS Date), 2, CAST(7600000.00 AS Decimal(18, 2)), 1, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (65, N'Ngô Văn U', 1, N'Đắk Lắk', CAST(N'2001-11-18' AS Date), 3, CAST(7500000.00 AS Decimal(18, 2)), 1, NULL, N'mrd', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (66, N'Vũ Thị V', 0, N'Kon Tum', CAST(N'2000-06-26' AS Date), 4, CAST(7300000.00 AS Decimal(18, 2)), 1, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (67, N'Bùi Văn W', 1, N'Bình Phước', CAST(N'2001-12-15' AS Date), 5, CAST(7800000.00 AS Decimal(18, 2)), 0, NULL, N'mrb', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (71, N'qe', 0, N'qèq', CAST(N'2024-10-16' AS Date), 1, CAST(2000000.00 AS Decimal(18, 2)), 0, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (72, N'abcde', 1, N'grre', CAST(N'2024-10-24' AS Date), 1, CAST(23.00 AS Decimal(18, 2)), 0, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (73, N'AAAAAAAAAAA', 0, N'hoa', CAST(N'2024-11-01' AS Date), 3, CAST(2000000.00 AS Decimal(18, 2)), 0, NULL, N'mra', NULL)
INSERT [dbo].[Employee] ([eid], [ename], [gender], [address], [dob], [did], [salary], [isWork], [updatedtime], [createdby], [updatedby]) VALUES (74, N'XXXXXXXXXX', 1, N'fpt', CAST(N'2024-10-10' AS Date), 2, CAST(1211212.00 AS Decimal(18, 2)), 0, NULL, N'mra', NULL)
SET IDENTITY_INSERT [dbo].[Employee] OFF
GO
SET IDENTITY_INSERT [dbo].[Feature] ON 

INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (1, N'Create Employee', N'/employee/create')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (2, N'Update Employee', N'/employee/update')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (3, N'Delete Employee', N'/employee/delete')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (4, N'List all Employees', N'/employee/list')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (5, N'Search Employees', N'/employee/search')
INSERT [dbo].[Feature] ([fid], [fname], [url]) VALUES (6, N'View Employee', N'/employee/detail')
SET IDENTITY_INSERT [dbo].[Feature] OFF
GO
SET IDENTITY_INSERT [dbo].[Plan] ON 

INSERT [dbo].[Plan] ([plid], [plname], [StartDate], [EndDate], [did], [isDone]) VALUES (1, N'p11112', CAST(N'2024-10-10' AS Date), CAST(N'2024-10-20' AS Date), 2, 0)
INSERT [dbo].[Plan] ([plid], [plname], [StartDate], [EndDate], [did], [isDone]) VALUES (2, N'abcde', CAST(N'2024-10-12' AS Date), CAST(N'2024-10-18' AS Date), 5, 0)
SET IDENTITY_INSERT [dbo].[Plan] OFF
GO
SET IDENTITY_INSERT [dbo].[PlanCampain] ON 

INSERT [dbo].[PlanCampain] ([plcid], [plid], [pid], [Quantity], [Estimate]) VALUES (1, 1, 6, 10, 1)
INSERT [dbo].[PlanCampain] ([plcid], [plid], [pid], [Quantity], [Estimate]) VALUES (2, 1, 7, 10, 2)
INSERT [dbo].[PlanCampain] ([plcid], [plid], [pid], [Quantity], [Estimate]) VALUES (3, 2, 6, 3, 1)
SET IDENTITY_INSERT [dbo].[PlanCampain] OFF
GO
SET IDENTITY_INSERT [dbo].[Product] ON 

INSERT [dbo].[Product] ([pid], [pname]) VALUES (6, N'Giỏ')
INSERT [dbo].[Product] ([pid], [pname]) VALUES (7, N'Thúng')
INSERT [dbo].[Product] ([pid], [pname]) VALUES (8, N'Rổ')
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
SET IDENTITY_INSERT [dbo].[Role] ON 

INSERT [dbo].[Role] ([rid], [rname]) VALUES (1, N'Head of HRD')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (2, N'Production Planning Manager')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (3, N'Workshop Manager')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (4, N'Recruitment Officer')
INSERT [dbo].[Role] ([rid], [rname]) VALUES (5, N'Profile Officer')
SET IDENTITY_INSERT [dbo].[Role] OFF
GO
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (1, 1)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (1, 4)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (2, 1)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (2, 5)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (3, 1)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (3, 5)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (4, 1)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (4, 3)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (4, 4)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (4, 5)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (5, 1)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (5, 3)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (5, 5)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (6, 1)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (6, 4)
INSERT [dbo].[RoleFeature] ([fid], [rid]) VALUES (6, 5)
GO
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mra', N'123', N'MRA', 1)
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mrb', N'123', N'MRB', 1)
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mrc', N'123', N'MRC', 1)
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mrd', N'123', N'MRD', 1)
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mre', N'123', N'MRE', 1)
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mrf', N'123', N'MRF', 1)
INSERT [dbo].[User] ([username], [password], [displayname], [isWork]) VALUES (N'mrg', N'123', N'MRG', 0)
GO
INSERT [dbo].[UserRole] ([username], [rid]) VALUES (N'mra', 1)
INSERT [dbo].[UserRole] ([username], [rid]) VALUES (N'mrb', 4)
INSERT [dbo].[UserRole] ([username], [rid]) VALUES (N'mrc', 5)
INSERT [dbo].[UserRole] ([username], [rid]) VALUES (N'mrd', 4)
INSERT [dbo].[UserRole] ([username], [rid]) VALUES (N'mrd', 5)
INSERT [dbo].[UserRole] ([username], [rid]) VALUES (N'mrf', 3)
GO
ALTER TABLE [dbo].[Employee] ADD  CONSTRAINT [DF_Employee_isWork]  DEFAULT ((1)) FOR [isWork]
GO
ALTER TABLE [dbo].[Plan] ADD  CONSTRAINT [DF_Plan_isDone]  DEFAULT ((0)) FOR [isDone]
GO
ALTER TABLE [dbo].[User] ADD  CONSTRAINT [DF_User_isWork]  DEFAULT ((1)) FOR [isWork]
GO
ALTER TABLE [dbo].[Attendence]  WITH CHECK ADD  CONSTRAINT [FK_Attendence_SchedualEmployee1] FOREIGN KEY([seid])
REFERENCES [dbo].[SchedualEmployee] ([seid])
GO
ALTER TABLE [dbo].[Attendence] CHECK CONSTRAINT [FK_Attendence_SchedualEmployee1]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Department1] FOREIGN KEY([did])
REFERENCES [dbo].[Department] ([did])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Department1]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_User] FOREIGN KEY([createdby])
REFERENCES [dbo].[User] ([username])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_User]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_User1] FOREIGN KEY([updatedby])
REFERENCES [dbo].[User] ([username])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_User1]
GO
ALTER TABLE [dbo].[Plan]  WITH CHECK ADD  CONSTRAINT [FK_Plan_Department] FOREIGN KEY([did])
REFERENCES [dbo].[Department] ([did])
GO
ALTER TABLE [dbo].[Plan] CHECK CONSTRAINT [FK_Plan_Department]
GO
ALTER TABLE [dbo].[PlanCampain]  WITH CHECK ADD  CONSTRAINT [FK_PlanCampain_Plan1] FOREIGN KEY([plid])
REFERENCES [dbo].[Plan] ([plid])
GO
ALTER TABLE [dbo].[PlanCampain] CHECK CONSTRAINT [FK_PlanCampain_Plan1]
GO
ALTER TABLE [dbo].[PlanCampain]  WITH CHECK ADD  CONSTRAINT [FK_PlanCampain_Product1] FOREIGN KEY([pid])
REFERENCES [dbo].[Product] ([pid])
GO
ALTER TABLE [dbo].[PlanCampain] CHECK CONSTRAINT [FK_PlanCampain_Product1]
GO
ALTER TABLE [dbo].[RoleFeature]  WITH CHECK ADD  CONSTRAINT [FK_RoleFeature_Feature1] FOREIGN KEY([fid])
REFERENCES [dbo].[Feature] ([fid])
GO
ALTER TABLE [dbo].[RoleFeature] CHECK CONSTRAINT [FK_RoleFeature_Feature1]
GO
ALTER TABLE [dbo].[RoleFeature]  WITH CHECK ADD  CONSTRAINT [FK_RoleFeature_Role1] FOREIGN KEY([rid])
REFERENCES [dbo].[Role] ([rid])
GO
ALTER TABLE [dbo].[RoleFeature] CHECK CONSTRAINT [FK_RoleFeature_Role1]
GO
ALTER TABLE [dbo].[SchedualCampaign]  WITH CHECK ADD  CONSTRAINT [FK_SchedualCampaign_PlanCampain1] FOREIGN KEY([plcid])
REFERENCES [dbo].[PlanCampain] ([plcid])
GO
ALTER TABLE [dbo].[SchedualCampaign] CHECK CONSTRAINT [FK_SchedualCampaign_PlanCampain1]
GO
ALTER TABLE [dbo].[SchedualEmployee]  WITH CHECK ADD  CONSTRAINT [FK_SchedualEmployee_Employee1] FOREIGN KEY([eid])
REFERENCES [dbo].[Employee] ([eid])
GO
ALTER TABLE [dbo].[SchedualEmployee] CHECK CONSTRAINT [FK_SchedualEmployee_Employee1]
GO
ALTER TABLE [dbo].[SchedualEmployee]  WITH CHECK ADD  CONSTRAINT [FK_SchedualEmployee_SchedualCampaign] FOREIGN KEY([scid])
REFERENCES [dbo].[SchedualCampaign] ([scid])
GO
ALTER TABLE [dbo].[SchedualEmployee] CHECK CONSTRAINT [FK_SchedualEmployee_SchedualCampaign]
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD  CONSTRAINT [FK_UserRole_Role1] FOREIGN KEY([rid])
REFERENCES [dbo].[Role] ([rid])
GO
ALTER TABLE [dbo].[UserRole] CHECK CONSTRAINT [FK_UserRole_Role1]
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD  CONSTRAINT [FK_UserRole_User1] FOREIGN KEY([username])
REFERENCES [dbo].[User] ([username])
GO
ALTER TABLE [dbo].[UserRole] CHECK CONSTRAINT [FK_UserRole_User1]
GO
