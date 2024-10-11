USE [PRJ301_Project]
GO
/****** Object:  Table [dbo].[Department]    Script Date: 10/11/2024 2:07:42 PM ******/
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
/****** Object:  Table [dbo].[Employee]    Script Date: 10/11/2024 2:07:42 PM ******/
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
	[createby] [int] NOT NULL,
	[updateby] [int] NULL,
	[updatetime] [datetime] NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[eid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feature]    Script Date: 10/11/2024 2:07:42 PM ******/
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
/****** Object:  Table [dbo].[Role]    Script Date: 10/11/2024 2:07:42 PM ******/
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
/****** Object:  Table [dbo].[RoleFeature]    Script Date: 10/11/2024 2:07:42 PM ******/
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
/****** Object:  Table [dbo].[User]    Script Date: 10/11/2024 2:07:42 PM ******/
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
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[uid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 10/11/2024 2:07:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[uid] [int] NOT NULL,
	[rid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[uid] ASC,
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
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
INSERT [dbo].[UserRole] ([uid], [rid]) VALUES (1, 1)
INSERT [dbo].[UserRole] ([uid], [rid]) VALUES (2, 4)
INSERT [dbo].[UserRole] ([uid], [rid]) VALUES (3, 5)
INSERT [dbo].[UserRole] ([uid], [rid]) VALUES (4, 4)
INSERT [dbo].[UserRole] ([uid], [rid]) VALUES (4, 5)
INSERT [dbo].[UserRole] ([uid], [rid]) VALUES (6, 3)
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
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_User] FOREIGN KEY([createby])
REFERENCES [dbo].[User] ([uid])
GO
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_User]
GO
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_User1] FOREIGN KEY([updateby])
REFERENCES [dbo].[User] ([uid])
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
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD FOREIGN KEY([uid])
REFERENCES [dbo].[User] ([uid])
GO
