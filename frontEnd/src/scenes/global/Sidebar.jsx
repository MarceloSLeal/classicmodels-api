import { useState } from "react";
import { ProSidebar, Menu, MenuItem } from "react-pro-sidebar";
import { Link } from "react-router-dom";
import 'react-pro-sidebar/dist/css/styles.css';
import CustomTooltip from "../../components/SideBarTooltips";

import { Box, IconButton, Typography, useTheme } from '@mui/material';
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import CalendarTodayOutLinedIcon from "@mui/icons-material/CalendarTodayOutlined";
import BarChartOutLinedIcon from "@mui/icons-material/BarChartOutlined";
import PieChartOutlineOutlinedicon from "@mui/icons-material/PieChartOutlineOutlined";
import TimelineOutLinedIcon from "@mui/icons-material/TimelineOutlined";
import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";
import MapOutLinedIcon from "@mui/icons-material/MapOutlined";

import PeopleAltOutlinedIcon from '@mui/icons-material/PeopleAltOutlined';
import Person4OutlinedIcon from '@mui/icons-material/Person4Outlined';
import ApartmentOutlinedIcon from '@mui/icons-material/ApartmentOutlined';
import ViewListOutlinedIcon from '@mui/icons-material/ViewListOutlined';
import BorderColorOutlinedIcon from '@mui/icons-material/BorderColorOutlined';
import PaymentOutlinedIcon from '@mui/icons-material/PaymentOutlined';
import CategoryOutlinedIcon from '@mui/icons-material/CategoryOutlined';
import ToysOutlinedIcon from '@mui/icons-material/ToysOutlined';

import { tokens } from "../../theme";

const Item = ({ title, to, icon, selected, setSelected }) => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  return (
    <MenuItem
      active={selected === title}
      style={{ color: colors.grey[100] }}
      onClick={() => setSelected(title)}
      icon={icon}
    >
      <Typography>{title}</Typography>
      <Link to={to} />
    </MenuItem>
  )
}

const Sidebar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [selected, setSelected] = useState("Dashboard");
  const [user, setUser] = useState(() => localStorage.getItem("user") || "");
  const [role, setRole] = useState(() => localStorage.getItem("role") || "");

  return (
    <Box
      sx={{
        "& .pro-sidebar-inner": {
          background: `${colors.primary[400]} !important`,
        },
        "& .pro-icon-wrapper": {
          backgroundColor: "transparent !important",
        },
        "& .pro-inner-item": {
          padding: "5px 35px 5px 20px !important",
        },
        "& .pro-inner-item:hover": {
          color: "#868dfb !important",
        },
        "& .pro-menu-item.active": {
          color: "#6870fa !important",
        },
      }}
    >

      <ProSidebar collapsed={isCollapsed}>
        <Menu iconShape="square">
          {/* LOGO AND MENU ICON */}
          <MenuItem
            onClick={() => setIsCollapsed(!isCollapsed)}
            icon={isCollapsed ? <MenuOutlinedIcon /> : undefined}
            style={{
              margin: "10px 0 20px 0",
              color: colors.grey[100],
            }}
          >
            {!isCollapsed && (
              <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                ml="15px"
              >
                <Typography variant="h3" color={colors.grey[100]}>
                  ADMINIS
                </Typography>
                <IconButton onClick={() => setIsCollapsed(!isCollapsed)}>
                  <MenuOutlinedIcon />
                </IconButton>
              </Box>
            )}
          </MenuItem>

          {/* USER */}
          {!isCollapsed && (
            <Box mb="25px">
              <Box display="flex" justifyContent="center" alignItems="center">
                <img
                  alt="profile-user"
                  width="100px"
                  height="100px"
                  src={`../../assets/user.png`}
                  style={{ cursor: "pointer", borderradius: "50%" }}
                />
              </Box>

              <Box textAlign="center">
                <Typography
                  variant="h2"
                  color={colors.grey[100]}
                  fontWeight="bold"
                  sx={{ m: "10px 0 0 0" }}
                >
                  {user}
                </Typography>
                <Typography variant="h5" color={colors.greenAccent[500]}>
                  {role}
                </Typography>
              </Box>
            </Box>
          )}

          {/* MENU ITEMS */}
          <Box paddingLeft={isCollapsed ? undefined : "10%"}>

            <CustomTooltip title="Dashboard" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Dashboard"
                to="/"
                icon={<HomeOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>

            <Typography
              variant="h6"
              color={colors.grey[300]}
              sx={{ m: "15px 0 5px 20px" }}
            >
              Data
            </Typography>
            <CustomTooltip title="Customers" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Customers"
                to="/customers"
                icon={<PeopleAltOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Employees" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Employees"
                to="/employees"
                icon={<Person4OutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Offices" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Offices"
                to="/offices"
                icon={<ApartmentOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Order Details" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Order Details"
                to="/orderdetails"
                icon={<ViewListOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Orders" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Orders"
                to="/orders"
                icon={<BorderColorOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Payments" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Payments"
                to="/payments"
                icon={<PaymentOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Product Line" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Product Line"
                to="/productlines"
                icon={<CategoryOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Products" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Products"
                to="/products"
                icon={<ToysOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>

            <Typography
              variant="h6"
              color={colors.grey[300]}
              sx={{ m: "15px 0 5px 20px" }}
            >
              Pages
            </Typography>
            <CustomTooltip title="Profile Form" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Profile Form"
                to="/form"
                icon={<PersonOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Calendar" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Calendar"
                to="/calendar"
                icon={<CalendarTodayOutLinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>

            <Typography
              variant="h6"
              color={colors.grey[300]}
              sx={{ m: "15px 0 5px 20px" }}
            >
              Charts
            </Typography>
            <CustomTooltip title="Bar Chart" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Bar Chart"
                to="/bar"
                icon={<BarChartOutLinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Pie Chart" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Pie Chart"
                to="/pie"
                icon={<PieChartOutlineOutlinedicon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Line Chart" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Line Chart"
                to="/line"
                icon={<TimelineOutLinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
            <CustomTooltip title="Geography Chart" isCollapsed={isCollapsed} X={60} Y={-50}>
              <Item
                title="Geography Chart"
                to="/geography"
                icon={<MapOutLinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            </CustomTooltip>
          </Box>
        </Menu>
      </ProSidebar>
    </Box>
  );
}

export default Sidebar;
