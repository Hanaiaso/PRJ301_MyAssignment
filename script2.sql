USE [prj]
GO
/****** Object:  Table [dbo].[Attendence]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[Department]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[Employee]    Script Date: 10/18/2024 4:49:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[eid] [int] NOT NULL,
	[ename] [nvarchar](150) NOT NULL,
	[gender] [bit] NOT NULL,
	[address] [varchar](150) NOT NULL,
	[dob] [date] NOT NULL,
	[did] [int] NOT NULL,
	[salary] [decimal](18, 2) NOT NULL,
	[isWork] [bit] NOT NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[eid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feature]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[Plan]    Script Date: 10/18/2024 4:49:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Plan](
	[plid] [int] NOT NULL,
	[plname] [nvarchar](150) NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NOT NULL,
	[Quantity] [int] NOT NULL,
	[did] [int] NOT NULL,
	[isDone] [bit] NOT NULL,
 CONSTRAINT [PK_Plan] PRIMARY KEY CLUSTERED 
(
	[plid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PlanCampain]    Script Date: 10/18/2024 4:49:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PlanCampain](
	[plcid] [int] NOT NULL,
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
/****** Object:  Table [dbo].[Product]    Script Date: 10/18/2024 4:49:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[pid] [int] NOT NULL,
	[pname] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Product] PRIMARY KEY CLUSTERED 
(
	[pid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[RoleFeature]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[SchedualCampaign]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[SchedualEmployee]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[User]    Script Date: 10/18/2024 4:49:49 PM ******/
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
/****** Object:  Table [dbo].[UserRole]    Script Date: 10/18/2024 4:49:49 PM ******/
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
